package com.cloudant.spring.boot.test.unit;

import com.ibm.cloud.cloudant.internal.CloudantFactory;
import com.ibm.cloudant.spring.common.CloudantConfiguration;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

// spring configuration providing:
// - a mock service factory (so we can return mock cloudant service instances)
// - real concrete cloudant configuration (so we can ensure the cloudant config mechanism is working)
@Configuration
public class TestConfig {

    @Bean
    @Primary
    CloudantFactory serviceFactory() {
        return Mockito.mock(CloudantFactory.class);
    }

    @Bean
    @Primary
    CloudantConfiguration cloudantConfiguration() {
        return new CloudantConfiguration();
    }

}
