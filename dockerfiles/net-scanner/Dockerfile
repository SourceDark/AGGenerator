FROM maven:3.3.9-jdk-8
MAINTAINER R <r@tianmaying.com>

ADD m2 /root/.m2
ADD workspace /workspace

WORKDIR /workspace
CMD mvn spring-boot:run
