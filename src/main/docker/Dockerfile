FROM tomcat:8-jre8
MAINTAINER RGM

EXPOSE 8080

ENV TZ=America/Sao_Paulo

ADD projeto-querydsl-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
ENTRYPOINT ["java", "-jar", "projeto-querydsl-1.0-SNAPSHOT.jar"]