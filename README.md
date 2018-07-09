# Cliente Service

This is a microservice wrote in Java. I'm using [Apache Camel](http://camel.apache.org/) to create the flux of the data creating routes. 
To test this service I used a Test-Driven Development approach, so [Cucumber](https://cucumber.io/) was the tool that i choose to do it.


## Usage

It's quite simple:

* Clone this repository 
* Build the service with:
```
./gradlew clean build     --> Linux 

OR

gradlew.bat clean build   --> Windows
```
* Run the project with your IDE or with: 
```
java -jar build/libs/cliente-service-0.0.1-SNAPSHOT.jar
```
* Go to [Swagger](http://localhost:9000/cliente-service/api/swagger-ui.html) to see the service running with an UI
* This the base url of the application: 
>
http://localhost:9000/cliente-service/api/swagger-ui.html

## Tests
* Run the project with your IDE or with: 
```
./gradlew clean test     --> Linux 

OR

gradlew.bat clean test   --> Windows
```
* Check the features and the stepdefs files
