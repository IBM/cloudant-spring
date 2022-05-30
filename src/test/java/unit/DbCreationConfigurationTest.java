package unit;

import com.ibm.cloud.cloudant.internal.CloudantFactory;
import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.cloudant.v1.model.PutDatabaseOptions;
import com.ibm.cloudant.spring.boot.CloudantAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringJUnitConfig(DbCreationConfigurationTest.TestConfig.class)
public class DbCreationConfigurationTest {

    // spring configuration providing:
    // - a mock service factory (so we can return mock cloudant service instances)
    // - real concrete cloudant configuration (so we can ensure the cloudant config mechanism is working)
    @Configuration
    static class TestConfig {

        @Bean
        @Primary
        CloudantFactory serviceFactory() {
            return Mockito.mock(CloudantFactory.class);
        }

        @Bean
        @Primary
        CloudantAutoConfiguration cloudantConfiguration() {
            return new CloudantAutoConfiguration();
        }

    }

    @Autowired
    CloudantAutoConfiguration cloudantConfiguration;

    @Autowired
    CloudantFactory serviceFactory;

    @Mock
    Cloudant cloudant;

    @Test
    public void testDbCreation() {
        // given
        when(serviceFactory.cloudant()).thenReturn(cloudant);
        cloudantConfiguration.setUrl("http://localhost");
        cloudantConfiguration.setDb("foo");
        cloudantConfiguration.setCreateDb(true);
        // when
        Cloudant c = cloudantConfiguration.cloudant(serviceFactory);

        // then
        assertNotNull(c);
        assertEquals(cloudant, c);
        verify(cloudant).setServiceUrl("http://localhost");
        verify(cloudant).putDatabase(new PutDatabaseOptions.Builder().db("foo").build());
    }

    @Test
    public void testNoDbCreation() {
        // given
        when(serviceFactory.cloudant()).thenReturn(cloudant);
        cloudantConfiguration.setUrl("http://localhost");
        cloudantConfiguration.setCreateDb(false);

        // when
        Cloudant c = cloudantConfiguration.cloudant(serviceFactory);

        // then
        assertNotNull(c);
        assertEquals(cloudant, c);
        verify(cloudant).setServiceUrl("http://localhost");
        verify(cloudant, never()).putDatabase(any());
    }

}
