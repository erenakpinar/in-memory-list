package com.example.inmemorylist.config.couchbase;

import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CouchbasePropertiesConfiguration {
    @Bean
    @ConfigurationProperties("spring.couchbase")
    public CouchbaseProperties couchbaseProperties() {
        return new CouchbaseProperties();
    }
}
