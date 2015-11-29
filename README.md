## What this is

A little example for testing out the latest version of Dropwizard (0.9.0) and Swagger together. The
project is divided in 3 modules in accordance with the [Dropwizard recommendations on how to organize 
your project](http://www.dropwizard.io/0.9.1/docs/manual/core.html#organizing-your-project):

- `asset-application`: the Asset application, including resources provideing RESTful service endpoints.
- `asset-api`: the resources used by the Asset application. These should/may be used by clients
- `asset-client`: client(s) consuming the service endpoints provided by the Asset application. 

## Build the `asset-application`

First do a `mvn clean install` from the project root dir, and then from the `asset-application` root dir: 

    mvn package

## Run the `asset-application` 

From the `asset-application` root dir:

    java -jar target/asset-service-1.0-SNAPSHOT.jar server asset-service.yml

