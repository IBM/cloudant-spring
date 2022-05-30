package com.ibm.cloudant.spring.common;

import com.ibm.cloud.cloudant.internal.CloudantFactory;
import com.ibm.cloud.cloudant.v1.Cloudant;
import com.ibm.cloud.cloudant.v1.model.PutDatabaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

public class CloudantConfiguration {
    // The following configuration is explicitly injected, whilst any other "cloudant." values are picked up in
    // `CloudantFactory#getSpringProperties` and forwarded to `DelegatingAuthenticatorFactory.getAuthenticator`.
    // This means that we can deal with any current and future authentication options without having to explicitly
    // name them here.

    @Value("${cloudant.url}")
    private String cloudantUrl;

    // TODO this can't be null when createdb is true
    @Value("${cloudant.db:#{null}}")
    private String cloudantDb;

    @Value("${cloudant.createDb:false}")
    private boolean createDb;

    public Cloudant cloudant(CloudantFactory serviceFactory) {
        Cloudant cloudant = serviceFactory.cloudant();
        cloudant.setServiceUrl(cloudantUrl);
        if (createDb) {
            cloudant.putDatabase(new PutDatabaseOptions.Builder().db(cloudantDb).build());
        }
        return cloudant;
    }

    public CloudantFactory cloudantFactory(Environment env) {
        return new CloudantFactory(env);
    }

}
