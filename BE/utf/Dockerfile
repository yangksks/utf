FROM java:8
EXPOSE 8081
ATG JAR_FILE = build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]