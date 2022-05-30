package integration;

import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.cloudant.spring.boot.CloudantAutoConfiguration;
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
public class ConfigurationTest {

    @Autowired
    Cloudant cloudant;

    @Autowired
    CloudantAutoConfiguration configuration;

    @Test
    public void testWiring() {
        Assertions.assertNotNull(cloudant);
        Assertions.assertEquals( "http://localhost", cloudant.getServiceUrl());
        Assertions.assertTrue(cloudant.getAuthenticator() instanceof IamAuthenticator);
    }

}
