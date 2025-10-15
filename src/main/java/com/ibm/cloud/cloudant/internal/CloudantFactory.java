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

package com.ibm.cloud.cloudant.internal;

import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.sdk.core.security.Authenticator;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * <p>
 * Factory for obtaining {@link Cloudant} client instance.
 * </p>
 */
public class CloudantFactory {

    static final String UA;
    private static final String UNKNOWN = "unknown";
    static {
        String version = Optional.ofNullable(CloudantFactory.class.getClassLoader()
                .getDefinedPackage("com.ibm.cloud.cloudant.spring.boot")
                .getImplementationVersion()).orElse(UNKNOWN);
        String lang = "java";
        String langVersion = System.getProperty("java.version", UNKNOWN);
        String langVendor = System.getProperty("java.vendor", UNKNOWN);
        String osName = System.getProperty("os.name", UNKNOWN);
        String osVersion = System.getProperty("os.version", UNKNOWN);
        String osArch = System.getProperty("os.arch", UNKNOWN);
        UA = String.format(
                "%s/%s (%s.version=%s; %s.vendor=%s; os.name=%s; os.version=%s; os.arch=%s; lang=%s;)",
                "cloudant-spring", version, lang, langVersion, lang, langVendor, osName, osVersion,
                osArch, lang);
    }

    private final Cloudant cloudant;

    public CloudantFactory(Environment env) {
        Map<String, String> springProperties = getSpringProperties(env);
        Authenticator authenticator =
                DelegatingAuthenticatorFactory.getAuthenticator(springProperties);
        cloudant = new Cloudant(Cloudant.DEFAULT_SERVICE_NAME, authenticator);
        cloudant.setDefaultHeaders(Collections.singletonMap("User-Agent", UA));
    }

    public Cloudant cloudant() {
        return cloudant;
    }

    // Get spring properties from all sources
    // NB the usual spring precedence order is respected since we use `env.getProperty()` at the end
    // of the pipeline
    private Map<String, String> getSpringProperties(Environment env) {
        return StreamSupport
                .stream(((AbstractEnvironment) env).getPropertySources().spliterator(), false)
                .filter(EnumerablePropertySource.class::isInstance)
                .map(ps -> ((EnumerablePropertySource<?>) ps).getPropertyNames())
                .flatMap(Arrays::stream).distinct()
                .filter(ps -> ps.toLowerCase(Locale.ROOT).startsWith("cloudant."))
                .collect(HashMap::new, (h, ps) -> h.put(ps, env.getProperty(ps)), HashMap::putAll);
    }

}
