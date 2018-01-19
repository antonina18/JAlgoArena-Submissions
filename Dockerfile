FROM anapsix/alpine-java:8_jdk
COPY /build/libs/jalgoarena-submissions-*.jar /app/jalgoarena-submissions.jar
WORKDIR /app
ENTRYPOINT java -jar /app/jalgoarena-submissions.jar
