FROM centos

# install required softwares
RUN yum -y install java-1.8.0-openjdk.x86_64
RUN yum -y install java-1.8.0-openjdk-devel.x86_64

# copy required files
RUN mkdir /src
RUN mkdir /data
ADD Node.java /src/Node.java
ADD VulnerabilityNode.java /src/VulnerabilityNode.java
ADD Graph.java /src/Graph.java
ADD ReaderJSON.java /src/ReaderJSON.java
ADD Tarjan.java /src/Tarjan.java
ADD UnionFind.java /src/UnionFind.java
ADD ShortestPath.java /src/ShortestPath.java
ADD Test.java /src/Test.java
ADD gson-2.3.1.jar /src/gson-2.3.1.jar
ADD jsoup-1.7.2.jar /src/jsoup-1.7.2.jar

# build
WORKDIR /src

RUN javac -encoding utf-8 Node.java

RUN javac -encoding utf-8 VulnerabilityNode.java

RUN javac -encoding utf-8 -cp gson-2.3.1.jar ReaderJSON.java

RUN javac -encoding utf-8  -cp .:jsoup-1.7.2.jar:gson-2.3.1.jar Graph.java

RUN javac -encoding utf-8 Tarjan.java

RUN javac -encoding utf-8 UnionFind.java

RUN javac -encoding utf-8 ShortestPath.java

RUN javac -encoding utf-8 Test.java

# enrtypoint
CMD java -cp .:gson-2.3.1.jar:jsoup-1.7.2.jar Test "/data/input"
