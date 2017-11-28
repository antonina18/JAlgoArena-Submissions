FROM anapsix/alpine-java:8_jdk
COPY /build/libs/jalgoarena-submissions-*.jar /app/jalgoarena-submissions.jar
WORKDIR /app
RUN ls -lt && java $JAVA_OPTS -jar jalgoarena-submissions.jar