FROM maven:3-amazoncorretto-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app/pom.xml

RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17
COPY --from=build /home/app/target/crud-0.0.1-SNAPSHOT.jar /usr/local/lib/crud.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/crud.jar"]