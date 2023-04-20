/*
 * Copyright © 2017, 2023 IBM Corp. All rights reserved.
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

package com.cloudant.spring.framework.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.ibm.cloudant.spring.framework.EnableCloudant;
import org.springframework.test.context.support.TestPropertySourceUtils;

public class EnableCloudantTest {

    private AnnotationConfigApplicationContext context;
    
    @Before
    public void setUp() {
        this.context = new AnnotationConfigApplicationContext();
    }

    @After
    public void cleanup() {
        if (this.context != null) {
            this.context.close();
        }
    }

    private static ClientBuilder mockBuilder = mock(ClientBuilder.class);
    private static CloudantClient mockClient = mock(CloudantClient.class);

    @Test
    public void builderBeanCreation() {
        
        this.context.register(MockCloudantClientConfig.class);
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.context, "cloudant.url=http://cloudant.com");
        this.context.refresh();
        ClientBuilder builder = this.context.getBean(ClientBuilder.class);
        assertNotNull(builder);
    }

    @Test
    public void clientBeanCreation() {
        when(mockBuilder.build()).thenReturn(mockClient);
        
        this.context.register(MockClientBuilderConfig.class);
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.context, "cloudant.url=http://cloudant.com");
        this.context.refresh();
        CloudantClient client = this.context.getBean(CloudantClient.class);
        assertEquals(mockClient, client);
    }

    @EnableCloudant
    @Configuration
    protected static class MockCloudantClientConfig {
        @Bean
        public CloudantClient client() {
            return mockClient;
        }
    }

    @EnableCloudant
    @Configuration
    protected static class MockClientBuilderConfig {
        @Bean
        public ClientBuilder builder() {
            return mockBuilder;
        }
    }

}
