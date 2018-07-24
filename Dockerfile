FROM java:8
ADD target\member-service-0.0.1-SNAPSHOT.jar member-service-0.0.1-SNAPSHOT.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "member-service-0.0.1-SNAPSHOT.jar"]