package com.cloudant.spring.framework.test.unit;

import com.ibm.cloud.cloudant.internal.CloudantFactory;
import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloudant.spring.common.CloudantConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(TestConfig.class)
// only minimal config is needed (no auth method etc) since we are using a mack cloudant service
@TestPropertySource(properties = {"cloudant.url = http://localhost"})
public class NoDbCreationConfigurationTest {
    @Autowired
    CloudantConfiguration cloudantConfiguration;

    @Autowired
    CloudantFactory serviceFactory;

    @Mock
    Cloudant cloudant;

    @Test
    public void testNoDbCreation() {
        // given
        when(serviceFactory.cloudant()).thenReturn(cloudant);

        // when
        Cloudant c = cloudantConfiguration.cloudant(serviceFactory);

        // then
        assertNotNull(c);
        assertEquals(cloudant, c);
        verify(cloudant).setServiceUrl("http://localhost");
        verify(cloudant, never()).putDatabase(any());
    }
}
