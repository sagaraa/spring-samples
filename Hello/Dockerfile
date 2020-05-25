# Start with a base image containing Java runtime
FROM java:8

# Add Maintainer Info
#LABEL maintainer="ayushpandey@fico.com"

# Add a volume pointing to /tmp
#VOLUME /tmp

# Make port 4200 available to the world outside this container
EXPOSE 1000

# The application's jar file
#ARG JAR_FILE=target/kycblk-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
#ADD ${JAR_FILE} ekyc.jar

# Run the jar file
ENTRYPOINT ["java","-jar","hello-kubernetes.jar"]


