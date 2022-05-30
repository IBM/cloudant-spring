/*
 * Copyright © 2017, 2018 IBM Corp. All rights reserved.
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
import com.ibm.cloud.cloudant.v1.model.PutDatabaseOptions;
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

    /**
     * Cloudant database name.
     */
    private String db;

    /**
     * If true, try to create the database at startup. Requires `db` to be non-null.
     */
    private boolean createDb;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public boolean isCreateDb() {
        return createDb;
    }

    public void setCreateDb(boolean createDb) {
        this.createDb = createDb;
    }

    @Bean
    @ConditionalOnMissingBean
    public Cloudant cloudant(@Autowired CloudantFactory serviceFactory) {
        Cloudant cloudant = serviceFactory.cloudant();
        cloudant.setServiceUrl(url);
        if (createDb) {
            cloudant.putDatabase(new PutDatabaseOptions.Builder().db(db).build());
        }
        return cloudant;
    }

    @Bean
    public CloudantFactory cloudantFactory(@Autowired Environment env) {
        return new CloudantFactory(env);
    }

}
