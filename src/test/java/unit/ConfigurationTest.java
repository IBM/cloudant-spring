/*
 * Copyright © 2023 IBM Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package unit;

import com.ibm.cloud.cloudant.internal.CloudantFactory;
import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.cloudant.spring.boot.CloudantAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(ConfigurationTest.TestConfig.class)
public class ConfigurationTest {

    // spring configuration providing:
    // - a mock service factory (so we can return mock cloudant service instances)
    // - real concrete cloudant configuration (so we can ensure the cloudant config mechanism is working)
    @Configuration
    static class TestConfig {

        @Bean
        @Primary
        CloudantFactory serviceFactory() {
            return Mockito.mock(CloudantFactory.class);
        }

        @Bean
        @Primary
        CloudantAutoConfiguration cloudantConfiguration() {
            return new CloudantAutoConfiguration();
        }

    }

    @Autowired
    CloudantAutoConfiguration cloudantConfiguration;

    @Autowired
    CloudantFactory serviceFactory;

    @Mock
    Cloudant cloudant;

    @Test
    public void testSetUrl() {
        // given
        when(serviceFactory.cloudant()).thenReturn(cloudant);
        cloudantConfiguration.setUrl("http://localhost");
        // when
        Cloudant c = cloudantConfiguration.cloudant(serviceFactory);

        // then
        assertNotNull(c);
        assertEquals(cloudant, c);
        verify(cloudant).setServiceUrl("http://localhost");
    }

}
