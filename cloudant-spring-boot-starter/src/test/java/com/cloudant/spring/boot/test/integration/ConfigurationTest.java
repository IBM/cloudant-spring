package com.cloudant.spring.boot.test.integration;

import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest()
@SpringBootApplication // needed for autoconfiguration
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = { "cloudant.url = http://localhost", "cloudant.auth.type = iam", "cloudant.apikey = zzz"})
public class ConfigurationTest {

    @Autowired
    Cloudant cloudant;

    @Test
    public void testWiring() {
        Assertions.assertNotNull(cloudant);
        Assertions.assertEquals(cloudant.getServiceUrl(), "http://localhost");
        Assertions.assertTrue(cloudant.getAuthenticator() instanceof IamAuthenticator);
    }

}
