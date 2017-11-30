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

package com.cloudant.spring.framework.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.spring.framework.EnableCloudant;

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
        when(mockBuilder.build()).thenReturn(mockClient);
        
        this.context.register(MockApplicationConfig.class);
        EnvironmentTestUtils.addEnvironment(this.context, "cloudant.url=http://cloudant.com");
        this.context.refresh();
        ClientBuilder builder = this.context.getBean(ClientBuilder.class);
        assertNotNull(builder);
    }

	@EnableCloudant
    @Configuration
    protected static class MockApplicationConfig {}

}