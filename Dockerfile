##build stage
#FROM maven:3.6.0-jdk-11-slim AS build
#COPY ./ ./app
#WORKDIR /app
#RUN mvn clean package -U

#run stage
FROM openjdk:11.0.4-jre-slim
RUN mkdir /app
WORKDIR /app
#COPY --from=build ./app/api/target/api-1.0-SNAPSHOT.jar /app
ADD ./api/target/api-1.0-SNAPSHOT.jar /app
EXPOSE 8085
CMD ["java", "-jar", "api-1.0-SNAPSHOT.jar"]