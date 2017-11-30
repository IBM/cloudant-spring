/*
 * Copyright © 2017 IBM Corp. All rights reserved.
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

package com.cloudant.spring.boot.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.cloudant.spring.boot.CloudantAutoConfiguration;

public class AutoConfigurationTest {


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
        when(mockBuilder.build()).thenReturn(mockClient);
        
        this.context.register(MockCloudantClientConfig.class, CloudantAutoConfiguration.class);
        EnvironmentTestUtils.addEnvironment(this.context, "cloudant.url=http://cloudant.com");
        this.context.refresh();
        ClientBuilder builder = this.context.getBean(ClientBuilder.class);
        assertNotNull(builder);
    }

    @Test
    public void clientBeanCreation() {
        when(mockBuilder.build()).thenReturn(mockClient);
        
        this.context.register(MockClientBuilderConfig.class, CloudantAutoConfiguration.class);
        this.context.refresh();
        CloudantClient client = this.context.getBean(CloudantClient.class);
        assertEquals(mockClient, client);
    }

    @Test
    public void databaseBeanCreation() {
        when(mockBuilder.build()).thenReturn(mockClient);
        
        this.context.register(MockCloudantClientConfig.class, MockClientBuilderConfig.class, CloudantAutoConfiguration.class);
        EnvironmentTestUtils.addEnvironment(this.context, "cloudant.db=testName");
        this.context.refresh();
        Database db = this.context.getBean(Database.class);
        assertNotNull(db);
    }



    @Configuration
    protected static class MockClientBuilderConfig {
        @Bean
        @Primary
        public ClientBuilder builder() {
            return mockBuilder;
        }
    }

    @Configuration
    protected static class MockCloudantClientConfig {
        @Bean
        @Primary
        public CloudantClient client() {
            return mockClient;
        }
    }

}