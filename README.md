# Cloudant Spring Library

The is a library to provide Spring developers easy configuration of the [official Cloudant library for Java](https://github.com/cloudant/java-cloudant/).

The library is split into two parts:
* `cloudant-spring-boot-starter` for [Spring Boot](https://projects.spring.io/spring-boot/) applications

* `cloudant-spring-framework` for [Spring Framework](https://projects.spring.io/spring-framework/) applications


* [Installation and Usage](#installation-and-usage)
* [Getting Started](#getting-started)
* [Related Documentation](#related-documentation)
* [Development](#development)
    * [Contributing](CONTRIBUTING.md)
    * [Test Suite](CONTRIBUTING.md#running-the-tests)
    * [Using in Other Projects](#using-in-other-projects)
    * [License](#license)
    * [Issues](#issues)

## Installation and Usage

### Spring Boot Applications

Gradle:
```groovy
dependencies {
    compile group: 'com.cloudant', name: 'cloudant-spring-boot-starter', version: '0.0.3'
}
```

Maven:
~~~ xml
<dependency>
  <groupId>com.cloudant</groupId>
  <artifactId>cloudant-spring-boot-starter</artifactId>
  <version>0.0.3</version>
</dependency>
~~~

### Spring Framework Applications

Gradle:
```groovy
dependencies {
    compile group: 'com.cloudant', name: 'cloudant-spring-framework', version: '0.0.3'
}
```

Maven:
~~~ xml
<dependency>
  <groupId>com.cloudant</groupId>
  <artifactId>cloudant-spring-framework</artifactId>
  <version>0.0.3</version>
</dependency>
~~~

## Getting Started

This section contains simple examples of connecting to Cloudant using the two libraries.

### Spring Boot Applications

To enable auto-configuration you must provide the following properties to define the connection to your Cloudant instance:

* `cloudant.url`
* `cloudant.username`
* `cloudant.password`

For example in an `application.properties` file:

~~~
cloudant.url=http://cloudant.com
cloudant.username=myUsername
cloudant.password=myPassword
~~~

Spring Boot will create a `com.cloudant.client.api.CloudantClient` bean that can be used to interact with your Cloudant instance:

~~~ java
@Autowired
private CloudantClient client;

public List<String> getAllDbs() {
    return client.getAllDbs();
}
~~~

The Spring Boot starter will also provide a `com.cloudant.client.api.Database` bean if your provide a `cloudant.db` property. For example:

~~~
cloudant.url=http://cloudant.com
cloudant.username=myUsername
cloudant.password=myPassword
cloudant.db=myDb
~~~

~~~ java
@Autowired
private Database db;

public List<Greeting> getAllDocsAsGreetings() {
    return List<Greeting> allDocs = db.getAllDocsRequestBuilder().includeDocs(true).build().getResponse().getDocsAs(Greeting.class);
}
~~~

By default the database specified by `cloudant.db` will be created if it doesn't exist. You can
change this behaviour with the `cloudant.createDb` configuration property. This is useful in cases
where the supplied credentials don't have permission to create a database, such as when using a
legacy API key. For example:

~~~
cloudant.url=http://cloudant.com
cloudant.username=myCloudanLegacyAPIKey
cloudant.password=myKeyPassphrase
cloudant.db=myDb
cloudant.createDb=false
~~~

To provide custom connection options you can override the `com.cloudant.client.api.ClientBuilder` bean and provide your own properties:

~~~ java
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Value("${cloudant.account}")
    private String account;

    @Value("${cloudant.username}")
    private String username;

    @Value("${cloudant.password}")
    private String password

    @Bean
    public ClientBuilder builder() {
        ClientBuilder builder = ClientBuilder
            .account(this.account)
            .username(this.username)
            .password(this.password);
        return builder;
    }
}
~~~

application.properties:

~~~
cloudant.account=myAccount
cloudant.username=myUsername
cloudant.password=myPassword
~~~

### Spring Framework Applications

You must provide the following properties to define the connection to your Cloudant instance:

* `cloudant.url`
* `cloudant.username`
* `cloudant.password`

To enable the creation of the `com.cloudant.client.api.CloudantClient` bean you must add an `com.cloudant.spring.framework.EnableCloudant` annotation to your application configuration:

~~~ java
@Configuration
@EnableWebMvc
@EnableCloudant
@ComponentScan
public class SpringConfig {}
~~~

~~~ java
@Autowired
private CloudantClient client;

public List<String> getAllDbs() {
    return client.getAllDbs();
}
~~~

## Related documentation
* [Cloudant library for Java](https://github.com/cloudant/java-cloudant/)
* [Cloudant documentation](https://console.bluemix.net/docs/services/Cloudant/cloudant.html#overview)
* [Cloudant Learning Center](https://developer.ibm.com/clouddataservices/cloudant-learning-center/)
* [Spring Boot documentation](https://projects.spring.io/spring-boot/)
* [Spring Framework documentation](https://projects.spring.io/spring-framework/)

# Development

For information about contributing, building, and running tests see the [CONTRIBUTING.md](CONTRIBUTING.md).

### Using in Other Projects

The preferred approach for using cloudant-spring in other projects is to use the Gradle or Maven dependency as described above.

### License

Copyright © 2017, 2018 IBM Corp. All rights reserved.

Licensed under the apache license, version 2.0 (the "license"); you may not use this file except in compliance with the license.  you may obtain a copy of the license at

    http://www.apache.org/licenses/LICENSE-2.0.html

Unless required by applicable law or agreed to in writing, software distributed under the license is distributed on an "as is" basis, without warranties or conditions of any kind, either express or implied. See the license for the specific language governing permissions and limitations under the license.

### Issues

Before opening a new issue please consider the following:
* Please try to reproduce the issue using the latest version.
* Please check the [existing issues](https://github.com/cloudant/cloudant-spring/issues)
to see if the problem has already been reported. Note that the default search
includes only open issues, but it may already have been closed.
* When opening a new issue [here in github](../../issues) please complete the template fully.
