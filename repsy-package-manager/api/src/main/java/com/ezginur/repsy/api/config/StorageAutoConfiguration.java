package com.ezginur.repsy.api.config;

import com.ezginur.repsy.api.core.StorageService;
import com.ezginur.repsy.api.filesystem.FileSystemStorageService;
import com.ezginur.repsy.api.minio.MinioStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageAutoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "storage.type", havingValue = "filesystem")
    public StorageService fileSystemStorageService(FileSystemStorageService service) {
        return service;
    }

    @Bean
    @ConditionalOnProperty(name = "storage.type", havingValue = "minio")
    public StorageService minioStorageService(MinioStorageService service) {
        return service;
    }
}