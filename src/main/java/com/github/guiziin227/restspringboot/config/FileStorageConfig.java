package com.github.guiziin227.restspringboot.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {

    private String uploadir;

    public FileStorageConfig() {}

    public String getUploadir() {
        return uploadir;
    }

    public void setUploadir(String uploadir) {
        this.uploadir = uploadir;
    }
}
