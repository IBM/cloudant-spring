/*
 * Copyright © 2023, 2025 IBM Corp. All rights reserved.
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

package integration;

import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.cloud.cloudant.spring.boot.CloudantAutoConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest()
@SpringBootApplication // needed for autoconfiguration
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = { "cloudant.url = http://localhost", "cloudant.auth.type = iam", "cloudant.apikey = zzz"})
class ConfigurationTest {

    @Autowired
    Cloudant cloudant;

    @Autowired
    CloudantAutoConfiguration configuration;

    @Test
    void testWiring() {
        Assertions.assertNotNull(cloudant);
        Assertions.assertEquals( "http://localhost", cloudant.getServiceUrl());
        Assertions.assertTrue(cloudant.getAuthenticator() instanceof IamAuthenticator);
    }

}
