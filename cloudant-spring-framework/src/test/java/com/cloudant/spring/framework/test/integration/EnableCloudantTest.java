package com.cloudant.spring.framework.test.integration;

import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

// Test that @EnableCloudant annotation correctly wires up Cloudant client and picks up test properties
@SpringJUnitConfig(TestConfig.class)
@TestPropertySource(properties = { "cloudant.url = http://localhost", "cloudant.auth.type = iam", "cloudant.apikey = zzz"})
public class EnableCloudantTest {

    @Autowired
    Cloudant cloudant;

    @Test
    public void testWiring() {
        Assertions.assertNotNull(cloudant);
        Assertions.assertEquals(cloudant.getServiceUrl(), "http://localhost");
        Assertions.assertTrue(cloudant.getAuthenticator() instanceof IamAuthenticator);
    }

}
