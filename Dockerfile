FROM openjdk:8-alpine

VOLUME /tmp

EXPOSE 8093

ARG DEPENDENCY=target

COPY ${DEPENDENCY}/share-everything-0.0.1-SNAPSHOT.jar  /home/share-everything.jar

ENTRYPOINT [ "sh", "-c", "java -jar /home/share-everything.jar" ]