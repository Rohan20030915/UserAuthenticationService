# Start with a base image containing Java runtime
FROM openjdk

# Add Maintainer Info
LABEL maintainer="rohan.dollop@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 9100

# The application's jar file
ARG JAR_FILE=target/user-authentication-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
