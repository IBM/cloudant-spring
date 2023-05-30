/*
 * Copyright © 2023 IBM Corp. All rights reserved.
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

package com.ibm.cloud.cloudant.spring.sample;

import java.util.stream.Collectors;

import com.ibm.cloud.cloudant.v1.Cloudant;

import com.ibm.cloud.cloudant.v1.model.DocsResultRow;
import com.ibm.cloud.cloudant.v1.model.PostAllDocsOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CloudantJavaSdkSpringSampleApplication {

    @Autowired
    private Cloudant cloudant;

    @Value("${myconfig.db}")
    private String db;

    public static void main(String[] args) {
        SpringApplication.run(CloudantJavaSdkSpringSampleApplication.class, args);
    }

    // display all databases
    @GetMapping("/all_dbs")
    public String allDbs() {
        return cloudant.getAllDbs()
                .execute()
                .getResult()
                .stream()
                .collect(Collectors.joining("\n")) + "\n";
    }

    // display all doc ids for database configured in `myconfig.db`
    @GetMapping("/all_docs")
    public String allDocs() {
        return cloudant.postAllDocs(new PostAllDocsOptions.Builder(db).build())
                .execute()
                .getResult()
                .getRows()
                .stream()
                .map(DocsResultRow::getId).collect(Collectors.joining("\n")) + "\n";
    }

}
