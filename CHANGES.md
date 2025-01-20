# 0.2.1 (2025-01-21)
- [UPGRADED] `com.ibm.cloud:cloudant` to `0.10.0`.

# 0.2.0 (2024-11-08)
- [UPGRADED] Spring Boot compilation version to `3.3.5`.
- [UPGRADED] `com.ibm.cloud:cloudant` to `0.9.3`.

# 0.1.5 (2024-10-25)
- [UPGRADED] `com.ibm.cloud:cloudant` to `0.9.2`.

# 0.1.4 (2024-02-19)
- [UPGRADED] Spring Boot compilation version to `2.7.18`.
- [UPGRADED] `com.ibm.cloud:cloudant` to `0.8.3`.

# 0.1.3 (2023-11-02)
- [UPGRADED] Spring Boot compilation version to `2.7.17`.
- [UPGRADED] `com.ibm.cloud:cloudant` to `0.8.0`.

# 0.1.2 (2023-09-26)
- [UPGRADED] Spring Boot compilation version to `2.7.16`.
- [UPGRADED] `com.ibm.cloud:cloudant` to `0.7.0`.

# 0.1.1 (2023-09-04)
- [UPGRADED] Spring Boot compilation version to `2.7.15`.
- [UPGRADED] `com.ibm.cloud:cloudant` to `0.6.0`.

# 0.1.0 (2023-06-02)
- [NEW] Added IAM authentication support (and config support for all authentication options supported by `cloudant-java-sdk`).
- [NEW] Created a sample project.
- [BREAKING CHANGE] Migrated cloudant client from [`java-cloudant` (`com.cloudant:cloudant-client`)](https://github.com/cloudant/java-cloudant) to [`cloudant-java-sdk` (`com.ibm.cloud:cloudant`)](https://github.com/IBM/cloudant-java-sdk/).
- [BREAKING CHANGE] Made only a `cloudant-spring-boot-starter` artifact and stopped making `cloudant-spring-framework` artifact.
- [BREAKING CHANGE] Changed Maven coordinates from `com.cloudant:cloudant-spring-boot-starter` to `com.ibm.cloud:cloudant-spring-boot-starter`.
- [BREAKING CHANGE] Removed `cloudant.db` and `cloudant.createDb` configuration options. Database names can be passed in user defined configuration.
- [UPGRADED] Spring Boot compilation version to 2.7.12.
- [NOTE] For application migration from `java-cloudant` to `cloudant-java-sdk` see the [migration guide](https://github.com/cloudant/java-cloudant/blob/6ea7fa2ff2a6245a05cc71d8856b3f89c2983d59/MIGRATION.md).
- [NOTE] Moved repository from [`cloudant-labs` org](https://github.com/cloudant-labs/cloudant-spring) to [`IBM` org](https://github.com/IBM/cloudant-spring).

# 0.0.4 (2018-11-08)
- [NEW] Configuration option `cloudant.createDb` for creating the database. Can be disabled to allow
 use of a legacy API key.

# 0.0.3 (2018-06-13)
- [IMPROVED] javadoc on configuration attributes (IDE tooltips).
- [IMPROVED] Added Spring configuration meta-data for improved IDE context help.
- [IMPROVED] Builds against Spring Boot 2.x
- [IMPROVED] Tests against Spring Boot 1.5.x
- [IMPROVED] Migrated from FindBugs (abandoned) to SpotBugs
- [UPGRADED] java-cloudant dependency to version 2.13.0.

# 0.0.2 (2017-12-01)
- [NEW] Initial release.
