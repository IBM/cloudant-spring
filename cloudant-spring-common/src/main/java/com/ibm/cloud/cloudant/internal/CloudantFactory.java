package com.ibm.cloud.cloudant.internal;

import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.sdk.core.security.Authenticator;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.StreamSupport;

/**
 * <p>Factory for obtaining {@link Cloudant} client instance.</p>
 */
public class CloudantFactory {

    private final Cloudant cloudant;

    public CloudantFactory(Environment env) {
        Map<String, String> springProperties = getSpringProperties(env);
        Authenticator authenticator = DelegatingAuthenticatorFactory.getAuthenticator(springProperties);
        cloudant = new Cloudant(Cloudant.DEFAULT_SERVICE_NAME, authenticator);
    }

    public Cloudant cloudant() {
        return cloudant;
    }

    // Get spring properties from all sources
    // NB the usual spring precedence order is respected since we use `env.getProperty()` at the end of the pipeline
    private Map<String, String> getSpringProperties(Environment env) {
        return StreamSupport.stream(((AbstractEnvironment)env).getPropertySources().spliterator(), false)
                .filter(ps -> ps instanceof EnumerablePropertySource)
                .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
                .flatMap(Arrays::stream)
                .distinct()
                .filter(ps -> ps.toLowerCase(Locale.ROOT).startsWith("cloudant."))
                .collect(HashMap::new, (h, ps) -> h.put(ps, env.getProperty(ps)), HashMap::putAll);
    }

}
