/*
 * Copyright © 2017 IBM Corp. All rights reserved.
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

package com.cloudant.spring.boot;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Configuration
@ConfigurationProperties(prefix="cloudant")
public class CloudantAutoConfiguration {

    private String url;

    private String username;

    private String password;

    private String db;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDb(String db) {
        this.db = db;
    }

    @Bean
    @ConditionalOnMissingBean
    public ClientBuilder clientBuilder() throws CloudantConfigurationException {
        ClientBuilder builder;
        try {
            builder = ClientBuilder
                .url(new URL(this.url))
                .username(this.username)
                .password(this.password);
        } catch (MalformedURLException e) {
            if(this.url == null) {
                throw new CloudantConfigurationException("Cloudant url provided was null");
            }
            throw new CloudantConfigurationException("Url provided is not a valid url string");
        }
        return builder;
    }

    @Bean
    @ConditionalOnMissingBean
    public CloudantClient client(ClientBuilder builder) throws CloudantConfigurationException {
        return builder.build();
    }

    @Bean
    @ConditionalOnProperty(name = "cloudant.db")
    public Database database(CloudantClient client) throws CloudantConfigurationException {
        Database db = client.database(this.db, true);
        return db;
    }
}