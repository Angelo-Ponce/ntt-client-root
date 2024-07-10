FROM eclipse-temurin:17-jdk

COPY target/ntt-client-root-0.0.1-SNAPSHOT.jar ntt-client-ws-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","ntt-client-ws-0.0.1-SNAPSHOT.jar"]