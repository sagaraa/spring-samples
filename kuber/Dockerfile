# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="arora.sagar14794@gmail.com"

# Add a volume pointing to /tmp
#VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 1000

# The application's jar file
ARG JAR_FILE=target/kuber-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} kuber.jar

# Run the jar file 
ENTRYPOINT ["java","-DskipTests","-jar","/kuber.jar"]