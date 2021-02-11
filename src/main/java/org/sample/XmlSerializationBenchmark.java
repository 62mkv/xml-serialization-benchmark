/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sample;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

@BenchmarkMode(Mode.Throughput)
@Fork(value = 1, jvmArgs = {"-Xms1G", "-Xmx1G"})
@State(Scope.Benchmark)
@Threads(value = 5)
/*
@Warmup(iterations = 0)
@Measurement(iterations = 1)
*/
public class XmlSerializationBenchmark {

    XMLOutputFactory factory = XMLOutputFactory.newInstance();
    @Param({"50"})
    private int N;
    private List<SerializablePojo> testingData;
    private XStream xStream = getxStream();
    private JAXBContext jaxbContext = getJaxbContext();

    @Setup
    public void setUp() {
        testingData = createTestingData();
    }

    @Benchmark
    public void testWithXStream(Blackhole blackhole) {
        runWithData(blackhole, i -> marshalWithXStream(testingData.get(i)));
    }

    @Benchmark
    public void testWithJaxb(Blackhole blackhole) {
        runWithData(blackhole, i -> marshalWithJaxb(testingData.get(i)));
    }

    @Benchmark
    public void testWithXmlStreamWriter(Blackhole blackhole) {
        runWithData(blackhole, i -> marshalWithXmlStreamWriter(testingData.get(i)));
    }

    @SneakyThrows
    private String marshalWithXmlStreamWriter(SerializablePojo pojo) {
        Writer stream = new StringWriter();
        XMLStreamWriter writer = factory.createXMLStreamWriter(stream);
        writer.writeStartElement("pojo");
        writer.writeStartElement("name");
        writer.writeCData(pojo.getName());
        writer.writeEndElement();


        writer.writeStartElement("element1list");
        for (SerializablePojo.Element1 element1 : pojo.getElement1List()) {
            writer.writeStartElement("element1");
            writer.writeStartElement("name");
            writer.writeCData(element1.getName());
            writer.writeEndElement();
            writer.writeStartElement("value");
            writer.writeCData(element1.getValue());
            writer.writeEndElement();
            writer.writeStartElement("enabled");
            writer.writeCharacters(element1.getEnabled().toString());
            writer.writeEndElement();
            writer.writeStartElement("index");
            writer.writeCharacters(element1.getIndex().toString());
            writer.writeEndElement();
            writer.writeEndElement(); // element1
        }
        writer.writeEndElement(); // element1list

        writer.writeStartElement("element2list");
        for (SerializablePojo.Element2 element2 : pojo.getElement2List()) {
            writer.writeStartElement("element2");
            writer.writeStartElement("name");
            writer.writeCData(element2.getName());
            writer.writeEndElement(); // name
            final SerializablePojo.Element2.InnerElement21 innerElement21 = element2.getInnerElement21();
            writer.writeStartElement("innerElement");
            writer.writeStartElement("name");
            writer.writeCData(innerElement21.getName());
            writer.writeEndElement(); // name
            writer.writeStartElement("errorMessage");
            writer.writeCData(innerElement21.getErrorMessage());
            writer.writeEndElement(); // name
            writer.writeStartElement("result");
            writer.writeCharacters(innerElement21.getResult().toString());
            writer.writeEndElement(); // name

            writer.writeEndElement(); // innerElement
            writer.writeEndElement(); // element2
        }
        writer.writeEndElement(); // element2list

        writer.writeEndElement(); // pojo
        writer.flush();
        return stream.toString();
    }

    private String marshalWithXStream(SerializablePojo step) {
        final StringWriter stringWriter = new StringWriter();
        CompactWriter compactWriter = new CompactWriter(stringWriter);
        xStream.marshal(step, compactWriter);
        return stringWriter.toString();
    }

    @SneakyThrows
    private String marshalWithJaxb(SerializablePojo serializablePojo) {
        Marshaller marshaller = jaxbContext.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(serializablePojo, writer);
        return writer.toString();
    }

    private void runWithData(Blackhole blackhole, IntFunction<String> producer) {
        for (int i = 0; i < N; i++) {
            blackhole.consume(producer.apply(i));
        }
    }

    private List<SerializablePojo> createTestingData() {
        List<SerializablePojo> result = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            List<SerializablePojo.Element1> element1List = createElement1List();
            List<SerializablePojo.Element2> element2List = createElement2List();

            result.add(SerializablePojo.builder()
                    .element1List(element1List)
                    .element2List(element2List)
                    .build());
        }
        return result;
    }

    private List<SerializablePojo.Element1> createElement1List() {
        List<SerializablePojo.Element1> result = new ArrayList<>();
        int n = RandomUtils.nextInt(10, 20);
        for (int i = 0; i < n; i++) {
            result.add(SerializablePojo.Element1.builder()
                    .build());
        }
        return result;
    }

    private List<SerializablePojo.Element2> createElement2List() {
        List<SerializablePojo.Element2> result = new ArrayList<>();
        int n = RandomUtils.nextInt(1, 100);

        for (int i = 0; i < n; i++) {
            result.add(SerializablePojo.Element2.builder()
                    .innerElement21(SerializablePojo.Element2.InnerElement21
                            .builder()
                            .build())
                    .build());
        }

        return result;
    }

    private XStream getxStream() {
        XStream result = new XStream();
        result.alias("pojo", SerializablePojo.class);
        result.alias("element1", SerializablePojo.Element1.class);
        result.alias("element2", SerializablePojo.Element2.class);
        return result;
    }

    @SneakyThrows
    private JAXBContext getJaxbContext() {
        return JAXBContext.newInstance(SerializablePojo.class);
    }
}
