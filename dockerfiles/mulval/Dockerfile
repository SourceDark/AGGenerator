FROM risksense/mulval
MAINTAINER R <r@tianmaying.com>

CMD /usr/bin/java -jar /app/app.jar

ADD cve_translate.sh /root/mulval/utils/cve_translate.sh
RUN chmod +x /root/mulval/utils/cve_translate.sh
ADD runner/target/*.jar /app/app.jar

