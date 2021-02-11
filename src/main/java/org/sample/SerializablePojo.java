package org.sample;

import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomUtils;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by kirill.marchuk on 11.02.2021
 */
@Data
@Builder
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
public class SerializablePojo {

    private static final FakeValuesService FAKER = new FakeValuesService(new Locale("en-US"), new RandomService());
    public static final String W_20_50 = "\\w{20,50}";

    private List<Element1> element1List = new ArrayList<>();
    private List<Element2> element2List = new ArrayList<>();
    @Builder.Default
    private String name = FAKER.regexify(W_20_50);

    @Builder.Default
    private Integer count = RandomUtils.nextInt();

    @Data
    @Builder
    public static class Element1 {
        @Builder.Default
        private String name = FAKER.regexify(W_20_50);
        @Builder.Default
        private String value = FAKER.regexify(W_20_50);
        @Builder.Default
        private Boolean enabled = RandomUtils.nextBoolean();
        @Builder.Default
        private Integer index = RandomUtils.nextInt();
    }

    @Data
    @Builder
    public static class Element2 {
        @Builder.Default
        private String name = FAKER.regexify("\\w{5,30}");
        private InnerElement21 innerElement21;

        @Data
        @Builder
        public static class InnerElement21 {
            @Builder.Default
            private String name = FAKER.regexify(W_20_50);
            @Builder.Default
            private Boolean result = RandomUtils.nextBoolean();
            @Builder.Default
            private String errorMessage = FAKER.regexify("\\w{10,150}");
        }
    }
}
