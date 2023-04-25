# Cloudant Spring Boot Starter

This project is a "Spring Boot starter" which allows users to get up and running quickly with the official
[Cloudant SDK for Java](https://github.com/IBM/cloudant-java-sdk) in Spring boot applications as well
as providing autoconfiguration via `application.properties` or another configuration source (environment, java properties, etc)

## Using

See the [sample](sample) directory which provides an example application with the following features:

- A Spring Boot MVC application
- Some REST endpoints
- Configuration properties (prefixed `cloudant.`) which are automatically loaded by the Cloudant client
- Other customer configuration properties (in this case named `myconfig.db`)

The sample has two rest endpoints, `/all_dbs` which returns a list of all databases for the Cloudant account, and
`/all_docs` which returns a list of all document ids for the database configured at `myconfig.db`.

To run the sample, you will need to edit the `application.properties` file, substituting your own settings as necessary:

```properties
cloudant.auth.type=iam
cloudant.url=https://<uuid>-bluemix.cloudantnosqldb.appdomain.cloud
cloudant.apikey=my-iam-api-key
myconfig.db=my-test-db
```

Run application:

```shell
gradle bootRun
```

REST request for `all_dbs`:
```shell
curl localhost:8080/all_dbs
test-db-04178485-8f15-4c3b-9138-d6ed68edb1d4
test-db-07b6c52a-e2e9-4da5-b641-6233367f8af9
test-db-0810edcf-fff6-4093-af01-f081fda77e74
test-db-08d3cef8-50db-42a5-af9f-991493cbce50
test-db-0c8255b0-ee10-4d4c-b104-def796305ebe
...
```

REST request for `all_docs`:
```shell
curl localhost:8080/all_docs
4826c5d0c7c9eba0babc7a17699405af
6070d2165fd43a19bd0c51858b4597cc
856599e6ffb52f47532e280de607146d
f28fa5b29ca5aed35ba28803401d17a7
fd6d29a324e4ac7d972d15d577a36524
...
```

## Properties metadata

The jar is built with `spring-configuration-metadata.json` which gives extra properties metadata and hints
when editing a properties file in some IDEs. See screenshots below for examples:

![](Screenshot%20from%202022-06-29%2012-26-27.png)

![](Screenshot%20from%202022-06-29%2012-27-38.png)

## Issues

Before opening a new issue please consider the following:
* Please try to reproduce the issue using the latest version.
* Please check the [existing issues](/issues)
to see if the problem has already been reported. Note that the default search
includes only open issues, but it may already have been closed.
* When opening a new issue [here in github](/issues) please complete the template fully.
