# Contributing

## Issues

Please [read these guidelines](http://ibm.biz/cdt-issue-guide) before opening an issue.
If you still need to open an issue then we ask that you complete the template as
fully as possible.

## Pull requests

We welcome pull requests, but ask contributors to keep in mind the following:

* Only PRs with the template completed will be accepted
* We will not accept PRs for user specific functionality

### Developer Certificate of Origin

In order for us to accept pull-requests, the contributor must sign-off a
[Developer Certificate of Origin (DCO)](DCO1.1.txt). This clarifies the
intellectual property license granted with any contribution. It is for your
protection as a Contributor as well as the protection of IBM and its customers;
it does not change your rights to use your own Contributions for any other purpose.

Please read the agreement and acknowledge it by signing-off your commit.

### AI-generated code policy

Before submitting your pull request, please ensure you've reviewed and adhere to our [AI policy](AI_CODE_POLICY.md).

## General information

Cloudant-spring is written in Java and uses Gradle as its build tool.

### Projects
There are two sub-projects in cloudant-spring each of which produces an artifact for publishing.

#### cloudant-spring-boot-starter
The cloudant-spring-boot-starter jar provides a custom [Spring autoconfiguration](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-auto-configuration.html) for a `com.cloudant.client.api.CloudantClient` bean, `com.cloudant.client.api.ClientBuilder` bean and `com.cloudant.client.api.Database` bean.

#### cloudant-spring-framework
The cloudant-spring-framework jar provides a custom annotation called `com.cloudant.spring.framework.EnableCloudant` which when applied provides a `com.cloudant.client.api.CloudantClient` bean and `com.cloudant.client.api.ClientBuilder` bean.

## Requirements

- gradle
- Java 1.8

### Installing requirements

#### Java

Follow the instructions for your platform.

#### Gradle

Gradle 8.1 or higher is required.

## Building

The project should build out of the box with:

```bash
$ gradle build
```

## Testing

### Running the tests

The tests run using [Mockito](http://site.mockito.org/) to mock the Cloudant client:

```bash
$ gradle test
```
