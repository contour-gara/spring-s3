FROM amazoncorretto:21.0.5-al2023 AS build

# CDS
WORKDIR /cds
ARG JAR_FILE=target/spring-s3-batch-server-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=tools -jar app.jar extract --destination app
RUN java -Dspring.context.exit=onRefresh -XX:ArchiveClassesAtExit=app/app.jsa -jar app/app.jar

FROM amazoncorretto:21.0.5-al2023

WORKDIR /deployments
ENV LANG=C.UTF-8
ARG JAR_FILE=/cds/app/app.jar
COPY --from=build ${JAR_FILE} app.jar
ARG JSA_FILE=/cds/app/app.jsa
COPY --from=build ${JSA_FILE} app.jsa
ARG LIB=/cds/app/lib
COPY --from=build ${LIB} lib

ENTRYPOINT ["java", "-XX:SharedArchiveFile=app.jsa", "-jar", "app.jar"]
