FROM openjdk:17
RUN MKDIR /database
WORKDIR /boot
COPY ./target/LoginManagement-1.0-SNAPSHOT.jar ./
EXPOSE 8081
CMD ["java", "-jar", "LoginManagement-1.0-SNAPSHOT.jar"]