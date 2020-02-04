# HeyCar Service

### Build
To build the project

```
mvn package
```

### Run
To run the project

```
 mvn spring-boot:run
```

### Profiles
Run the project for different environments and load their respective properties file
```
 mvn spring-boot:run -Dspring.profiles.active="stage"
 ```


#### Java Docs
To Generate the java docs run following command

```
mvn javadoc:javadoc
``` 
Java docs will be generated on the location {project_directory}/target/site/apidocs


#### Swagger
To generate the REST api docs using swagger, run following command:

```
mvn package
```

REST api doc which is swagger.json file be generated on the location {project_directory}/target/generated/swagger-ui


#### Swagger UI

To access the UI provided by swagger hit the url

http://localhost:8083/swagger-ui.html

and to get the above generated json using url, hit the url

http://localhost:8083/v2/api-docs


### Health Check

To check the application health, endpoints are exposed by spring boot actuator. Here is the url

http://localhost:8083/actuator/health


### Metrics
Various metrics are exposed by spring boot actuator. Here is the url to list all available metrics

http://localhost:8082/actuator/metrics

To access any specific metrics, pick one from above list and hit this url

http://localhost:8082/actuator/metrics/jvm.memory.max