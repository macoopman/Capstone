FROM java:8
WORKDIR /
EXPOSE 8080
ADD target/capstone-0.0.1-SNAPSHOT.jar //
ENTRYPOINT ["java","-jar","/capstone-0.0.1-SNAPSHOT.jar"]


