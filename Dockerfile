FROM eclipse-temurin:23-jdk AS compiler

ARG COMPILE_DIR=/code_folder

WORKDIR ${COMPILE_DIR}


COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY src src
COPY .mvn .mvn


RUN chmod a+x ./mvnw && ./mvnw clean package -Dmaven.test.skip=true


ENTRYPOINT java -jar target/miniproject-0.0.1-SNAPSHOT.jar

#stage 2

FROM eclipse-temurin:23-jdk

ARG DEPLOY_DIR=/app

WORKDIR ${DEPLOY_DIR}

COPY --from=compiler /code_folder/target/miniproject-0.0.1-SNAPSHOT.jar miniproject.jar

ENV SERVER_PORT=8080

EXPOSE ${SERVER_PORT}




ENTRYPOINT ["java", "-jar", "miniproject.jar"]