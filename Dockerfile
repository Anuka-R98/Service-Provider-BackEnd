FROM openjdk:17
ADD target/springboot-security-mongo-docker.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]