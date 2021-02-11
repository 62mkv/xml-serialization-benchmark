```
# JMH version: 1.27
# VM version: JDK 1.8.0_275, OpenJDK Client VM, 25.275-b01
# VM invoker: C:\Program Files (x86)\AdoptOpenJDK\jdk-8.0.275.01-hotspot\jre\bin\java.exe
# VM options: -Xms1G -Xmx1G
# JMH blackhole mode: full blackhole + dont-inline hint
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 5 threads, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: org.sample.XmlSerializationBenchmark.testWithJaxb
# Parameters: (N = 50)

# Run progress: 0,00% complete, ETA 00:05:00
# Fork: 1 of 1
# Warmup Iteration   1: 235,622 ops/s
# Warmup Iteration   2: 236,170 ops/s
# Warmup Iteration   3: 236,302 ops/s
# Warmup Iteration   4: 228,066 ops/s
# Warmup Iteration   5: 226,041 ops/s
Iteration   1: 172,554 ops/s
Iteration   2: 214,217 ops/s
Iteration   3: 233,009 ops/s
Iteration   4: 233,663 ops/s
Iteration   5: 230,349 ops/s


Result "org.sample.XmlSerializationBenchmark.testWithJaxb":
  216,758 ±(99.9%) 99,951 ops/s [Average]
  (min, avg, max) = (172,554, 216,758, 233,663), stdev = 25,957
  CI (99.9%): [116,808, 316,709] (assumes normal distribution)


# JMH version: 1.27
# VM version: JDK 1.8.0_275, OpenJDK Client VM, 25.275-b01
# VM invoker: C:\Program Files (x86)\AdoptOpenJDK\jdk-8.0.275.01-hotspot\jre\bin\java.exe
# VM options: -Xms1G -Xmx1G
# JMH blackhole mode: full blackhole + dont-inline hint
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 5 threads, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: org.sample.XmlSerializationBenchmark.testWithXStream
# Parameters: (N = 50)

# Run progress: 33,33% complete, ETA 00:03:52
# Fork: 1 of 1
# Warmup Iteration   1: 41,644 ops/s
# Warmup Iteration   2: 41,605 ops/s
# Warmup Iteration   3: 41,619 ops/s
# Warmup Iteration   4: 41,287 ops/s
# Warmup Iteration   5: 41,243 ops/s
Iteration   1: 39,878 ops/s
Iteration   2: 40,250 ops/s
Iteration   3: 39,741 ops/s
Iteration   4: 40,096 ops/s
Iteration   5: 40,920 ops/s


Result "org.sample.XmlSerializationBenchmark.testWithXStream":
  40,177 ±(99.9%) 1,768 ops/s [Average]
  (min, avg, max) = (39,741, 40,177, 40,920), stdev = 0,459
  CI (99.9%): [38,408, 41,945] (assumes normal distribution)


# JMH version: 1.27
# VM version: JDK 1.8.0_275, OpenJDK Client VM, 25.275-b01
# VM invoker: C:\Program Files (x86)\AdoptOpenJDK\jdk-8.0.275.01-hotspot\jre\bin\java.exe
# VM options: -Xms1G -Xmx1G
# JMH blackhole mode: full blackhole + dont-inline hint
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 5 threads, will synchronize iterations
# Benchmark mode: Throughput, ops/time
# Benchmark: org.sample.XmlSerializationBenchmark.testWithXmlStreamWriter
# Parameters: (N = 50)

# Run progress: 66,67% complete, ETA 00:01:56
# Fork: 1 of 1
# Warmup Iteration   1: 519,949 ops/s
# Warmup Iteration   2: 531,657 ops/s
# Warmup Iteration   3: 532,601 ops/s
# Warmup Iteration   4: 528,901 ops/s
# Warmup Iteration   5: 524,137 ops/s
Iteration   1: 524,703 ops/s
Iteration   2: 517,410 ops/s
Iteration   3: 524,305 ops/s
Iteration   4: 518,494 ops/s
Iteration   5: 516,890 ops/s


Result "org.sample.XmlSerializationBenchmark.testWithXmlStreamWriter":
  520,360 ±(99.9%) 14,745 ops/s [Average]
  (min, avg, max) = (516,890, 520,360, 524,703), stdev = 3,829
  CI (99.9%): [505,615, 535,106] (assumes normal distribution)


# Run complete. Total time: 00:05:47

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

Benchmark                                          (N)   Mode  Cnt    Score    Error  Units
XmlSerializationBenchmark.testWithJaxb              50  thrpt    5  216,758 ± 99,951  ops/s
XmlSerializationBenchmark.testWithXStream           50  thrpt    5   40,177 ±  1,768  ops/s
XmlSerializationBenchmark.testWithXmlStreamWriter   50  thrpt    5  520,360 ± 14,745  ops/s
```