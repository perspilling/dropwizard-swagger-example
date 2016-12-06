## What this is

A little example for testing out the latest version of Dropwizard (1.0.5) and Swagger together. The
project is divided in 3 modules in accordance with the [Dropwizard recommendations on how to organize 
your project](http://www.dropwizard.io/1.0.5/docs/manual/core.html#organizing-your-project):

- `asset-application`: the Asset application, including resources provideing RESTful service endpoints.
- `asset-api`: the resources used by the Asset application. These should/may be used by clients
- `asset-client`: client(s) consuming the service endpoints provided by the Asset application. 

## Requirements

- A PostgreSQL database. The asset-application will use it for persistence. A simple way to install a PostgreSQL 
database is to use the [Postgress.app](http://postgresapp.com). Other SQL databases can of course also be used. See 
the [Dropwizard configuration](http://www.dropwizard.io/1.0.5/docs/manual/configuration.html) on how to configure 
another database.

## Build the `asset-application`

First do a `mvn clean install` from the project root dir, and then from the `asset-application` root dir: 

    mvn package

## Run the `asset-application` 

From the `asset-application` root dir:

    java -jar target/asset-application-1.0-SNAPSHOT.jar server asset-application.yml

### Check the API via Swagger

Use to following url: [http://localhost:9000/api/swagger](http://localhost:9000/api/swagger)

As you can see there is a simple hello-world service, and an asset-service. Use the Swagger UI to explore them.

Note that the the database is recreated, and thus empty, every time the application is started.
  
Documentation for the Swagger annotations used to document the APIs can be found here: 
[https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X](https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X) 
 
## Notes

### Simpler Jackson annotation with Java 8

The @JsonCreator annotation-style is not required with jdk 1.8, see:
https://manosnikolaidis.wordpress.com/2015/08/25/jackson-without-annotations/

```
    @JsonCreator
    public Asset(@JsonProperty("serialNumber") String serialNumber,
                 @JsonProperty("modelName") String modelName, @JsonProperty("address") Address address) {
        this.serialNumber = serialNumber;
        this.modelName = modelName;
        this.address = address;
    }
```
