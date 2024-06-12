FROM openjdk:21-jdk

COPY build/libs/LibraryAPI-0.0.1-SNAPSHOT.jar  LibraryAPI-0.0.1-SNAPSHOT.jar

EXPOSE 80
ENTRYPOINT ["java", "-jar", "/LibraryAPI-0.0.1-SNAPSHOT.jar"]