FROM tomcat:8-jre8
MAINTAINER RGMBrasil

EXPOSE 9000

ENV TZ=America/Sao_Paulo

COPY ./target/projeto-querydsl-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
ENTRYPOINT ["java", "-jar", "projeto-querydsl-1.0-SNAPSHOT.jar"]