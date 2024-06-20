#FROM openjdk:17
#RUN ln -snf /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime && echo Asia/Ho_Chi_Minh > /etc/timezone
#WORKDIR /app
#COPY target/musicmanager-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM openjdk:17-jdk-slim AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:17-jdk-slim
WORKDIR app
COPY --from=build target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "demo.jar"]
#FROM openjdk:17
##ENV PORT 8080
##EXPOSE $PORT
## Set the timezone to Asia/Ho_Chi_Minh
#RUN ln -snf /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime && echo Asia/Ho_Chi_Minh > /etc/timezone
#WORKDIR /app
#COPY target/restaurant-management-system-0.0.1-SNAPSHOT.jar app.jar
## Copy resources into the container
#COPY src/main/resources/db/migration/ /app/src/main/resources/db/migration/
#ENTRYPOINT ["java","-jar","app.jar"]