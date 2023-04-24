# Cloudant Spring Boot Starter

This is a rewrite of the "spring plugin", to provide a "starter project" which allows users to get up and running quickly,
as well as providing autoconfiguration via application.properties or another configuration source (environment, java properties, etc)

## Using

Here is a very quick run-through of what a project using this starter would look like.

In this example, we are writing a web app which provides a REST endpoint, `/all_dbs` which returns a list of all
databases for the Cloudant account.

Gradle:
```groovy
dependencies {
  implementation 'org.springframework.boot:spring-boot-starter-web'
  implementation 'com.cloudant:cloudant-spring-boot-starter:0.0.5-SNAPSHOT'
}
```

application.properties:
```properties
cloudant.auth.type=iam
cloudant.url=https://<uuid>-bluemix.cloudantnosqldb.appdomain.cloud
cloudant.apikey=my-iam-api-key
```

Application.java:
```java
@SpringBootApplication
@RestController
public class CloudantJavaSdkSpringSampleApplication {
    
	@Autowired
	private Cloudant cloudant;

	public static void main(String[] args) {
		SpringApplication.run(CloudantJavaSdkSpringSampleApplication.class, args);
	}

	@GetMapping("/all_dbs")
	public String allDbs() {
		return cloudant.getAllDbs().execute().getResult().stream().collect(Collectors.joining("\n"));
	}

}
```

Run application:

```shell
./gradlew bootRun
```

REST request:
```shell
test-db-04178485-8f15-4c3b-9138-d6ed68edb1d4
test-db-07b6c52a-e2e9-4da5-b641-6233367f8af9
test-db-0810edcf-fff6-4093-af01-f081fda77e74
test-db-08d3cef8-50db-42a5-af9f-991493cbce50
test-db-0c8255b0-ee10-4d4c-b104-def796305ebe
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
