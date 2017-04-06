FROM risksense/mulval
MAINTAINER R <r@tianmaying.com>

CMD /run.sh &&  /usr/bin/java -jar /app/app.jar

ADD run.sh /run.sh
RUN chmod +x /run.sh
ADD runner/target/*.jar /app/app.jar
