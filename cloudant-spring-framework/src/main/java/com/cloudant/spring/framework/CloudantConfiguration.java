/*
 * Copyright © 2015, 2017 IBM Corp. All rights reserved.
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

package com.cloudant.spring.framework;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudantConfiguration {

    @Value("${cloudant.url}")
    private String url;

    @Value("${cloudant.username}")
    private String username;

    @Value("${cloudant.password}")
    private String password;

    @Value("${cloudant.db}")
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
    public ClientBuilder builder() throws CloudantConfigurationException {
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
    public CloudantClient client(ClientBuilder builder) {
        return builder.build();
    }

    @Bean
    public Database database(CloudantClient client) {
        Database db = client.database(this.db, true);
        return db;
    }
}