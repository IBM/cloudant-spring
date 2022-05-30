package com.cloudant.spring.boot.test.unit;

import com.ibm.cloud.cloudant.internal.CloudantFactory;
import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.cloudant.v1.model.PutDatabaseOptions;
import com.ibm.cloudant.spring.common.CloudantConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(TestConfig.class)
// only minimal config is needed (no auth method etc) since we are using a mack cloudant service
@TestPropertySource(properties = {"cloudant.url = http://localhost", "cloudant.db = foo", "cloudant.createDb = true"})
public class DbCreationConfigurationTest {
    @Autowired
    CloudantConfiguration cloudantConfiguration;

    @Autowired
    CloudantFactory serviceFactory;

    @Mock
    Cloudant cloudant;

    @Test
    public void testDbCreation() {
        // given
        when(serviceFactory.cloudant()).thenReturn(cloudant);

        // when
        Cloudant c = cloudantConfiguration.cloudant(serviceFactory);

        // then
        assertNotNull(c);
        assertEquals(cloudant, c);
        verify(cloudant).setServiceUrl("http://localhost");
        verify(cloudant).putDatabase(new PutDatabaseOptions.Builder().db("foo").build());
    }
}
