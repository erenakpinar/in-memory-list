package com.example.inmemorylist.config.couchbase;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
@RequiredArgsConstructor
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {

    private final CouchbaseProperties couchbaseProperties;

    @Value("${spring.couchbase.categories-bucket}")
    private String bucketName;

    @Override
    public String getConnectionString() {
        return couchbaseProperties.getConnectionString();
    }

    @Override
    public String getUserName() {
        return couchbaseProperties.getUsername();
    }

    @Override
    public String getPassword() {
        return couchbaseProperties.getPassword();
    }

    @Override
    public String getBucketName() {
        return bucketName;
    }
}
