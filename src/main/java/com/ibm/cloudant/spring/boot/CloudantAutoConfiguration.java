/*
 * Copyright © 2017, 2023 IBM Corp. All rights reserved.
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

package com.ibm.cloudant.spring.boot;

import com.ibm.cloud.cloudant.internal.CloudantFactory;
import com.ibm.cloud.cloudant.v1.Cloudant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@ConfigurationProperties(prefix = "cloudant")
public class CloudantAutoConfiguration {

    // The following configuration is explicitly injected, whilst any other "cloudant." values are picked up in
    // `CloudantFactory#getSpringProperties` and forwarded to `DelegatingAuthenticatorFactory.getAuthenticator`.
    // This means that we can deal with any current and future authentication options without having to explicitly
    // name them here.

    /**
     * Cloudant database connection URL (eg https://<uuid>.cloudantnosqldb.appdomain.cloud).
     */
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Bean
    @ConditionalOnMissingBean
    public Cloudant cloudant(@Autowired CloudantFactory serviceFactory) {
        Cloudant cloudant = serviceFactory.cloudant();
        cloudant.setServiceUrl(url);
        return cloudant;
    }

    @Bean
    public CloudantFactory cloudantFactory(@Autowired Environment env) {
        return new CloudantFactory(env);
    }

}
