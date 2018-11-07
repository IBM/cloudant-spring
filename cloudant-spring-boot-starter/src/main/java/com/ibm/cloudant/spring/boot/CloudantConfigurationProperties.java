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

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;

@ConfigurationProperties(prefix="cloudant")
public class CloudantConfigurationProperties {

    /** URL for cloudant service. This URL should not include the username or password. **/
    private URL url;
    
    /** Cloudant username */
    private String username;

    /** Cloudant password */ 
    private String password;

    /** Cloudant database */
    private String db;

    /** Create the named Cloudant database if it doesn't exist.
     * Must be false if using a legacy API key.
     */
    private boolean createDb = true;

    public void setUrl(URL url) {
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

    public void setCreateDb(boolean createDb) {
        this.createDb = createDb;
    }

    public URL getUrl() {
        return this.url;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getDb() {
        return this.db;
    }

    public boolean getCreateDb() {
        return this.createDb;
    }

}
