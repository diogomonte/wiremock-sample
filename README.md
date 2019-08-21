### Wiremock
http://wiremock.org/
### Run wiremock container
docker run -it --rm -p 8080:8080 rodolpheche/wiremock
### Run tests on DocumentationTest to stub requests
 ./gradlew test --tests com.dom.wiremocksample.stub.DocumentationTests
### Run tests on MyApiConsumerTests to simulate a contract test
./gradlew test --tests com.dom.wiremocksample.consumer.MyApiConsumerTests
