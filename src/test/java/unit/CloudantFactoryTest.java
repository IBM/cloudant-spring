/*
 * Copyright © 2025 IBM Corp. All rights reserved.
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
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CloudantFactoryTest {

    @Test
    void testUserAgent() {
        // Mock the Spring environment
        MockEnvironment env = new MockEnvironment();
        env.setProperty("cloudant.auth.type", "NOAUTH");

        // Create an instance of CloudantFactory
        CloudantFactory factory = new CloudantFactory(env);

        // Verify that the Cloudant instance is not null
        assertNotNull(factory.cloudant());

        // Verify that the UA string is correctly formatted
        // Note this uses "unknown" for version because in test environment the jar manifest is not
        // available
        String expectedUserAgent = "cloudant-spring/unknown (java.version="
                + System.getProperty("java.version") + "; java.vendor="
                + System.getProperty("java.vendor") + "; os.name=" + System.getProperty("os.name")
                + "; os.version=" + System.getProperty("os.version") + "; os.arch="
                + System.getProperty("os.arch") + "; lang=java;)";
        assertEquals(expectedUserAgent, factory.cloudant().getDefaultHeaders().get("User-Agent"));

    }

}
