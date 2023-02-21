FROM openjdk:17
ADD target/service-provider.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]