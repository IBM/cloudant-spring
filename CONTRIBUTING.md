Contributing
=======

Cloudant-spring is written in Java and uses Gradle as its build tool.

## Developer Certificate of Origin

In order for us to accept pull-requests, the contributor must sign-off a
[Developer Certificate of Origin (DCO)](DCO1.1.txt). This clarifies the
intellectual property license granted with any contribution. It is for your
protection as a Contributor as well as the protection of IBM and its customers;
it does not change your rights to use your own Contributions for any other purpose.

Please read the agreement and acknowledge it by ticking the appropriate box in the PR
 text, for example:

- [x] Tick to sign-off your agreement to the Developer Certificate of Origin (DCO) 1.1

## Requirements

- gradle
- Java 1.8

## Installing requirements

### Java

Follow the instructions for your platform.

### Gradle

The project uses the gradle wrapper to download the specified version of gradle.
The gradle wrapper is run by using the following command:

```bash
$ ./gradlew
```
Note: on windows the command to run is gradlew.bat rather than gradlew

# Building the library

The project should build out of the box with:

```bash
$ ./gradlew build
```

## Projects
There are two sub-projects in cloudant-spring each of which produces an artifact for publishing.

### cloudant-spring-boot-starter
The cloudant-spring-boot-starter jar provides a custom [Spring autoconfiguration](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html) for a `com.cloudant.client.api.CloudantClient` bean, `com.cloudant.client.api.ClientBuilder` bean and `com.cloudant.client.api.Database` bean.

### cloudant-spring-framework
The cloudant-spring-framework jar provides a custom annotation called `com.cloudant.spring.framework.EnableCloudant` which when applied provides a `com.cloudant.client.api.CloudantClient` bean and `com.cloudant.client.api.ClientBuilder` bean.


### Running the tests

The tests run using [Mockito](http://site.mockito.org/) to mock the Cloudant client:

```bash
$ ./gradlew test
```
