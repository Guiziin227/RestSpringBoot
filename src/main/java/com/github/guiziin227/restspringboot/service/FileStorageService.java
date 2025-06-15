package com.github.guiziin227.restspringboot.service;

import com.github.guiziin227.restspringboot.config.FileStorageConfig;
import com.github.guiziin227.restspringboot.controller.FileController;
import com.github.guiziin227.restspringboot.exception.FileNotFoundException;
import com.github.guiziin227.restspringboot.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Path.of(fileStorageConfig.getUploadDir()).toAbsolutePath()
                .normalize();

        this.fileStorageLocation = path;
        try{
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            logger.error("Could not create directory for file storage: {}", path, e);
            throw new FileStorageException("Could not create directory for file storage", e);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            if(fileName.contains("..")) {
                throw new FileStorageException("Invalid file path: " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }catch (Exception e) {
            logger.error("Could not store file {}. Error: {}", fileName, e.getMessage(), e);
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {

            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                logger.error("File {} not found or not readable", fileName);
                throw new FileNotFoundException("File " + fileName + " not found or not readable");
            }

        } catch (Exception e) {
            logger.error("Could not load file {}. Error: {}", fileName, e.getMessage(), e);
            throw new FileNotFoundException("Could not load file " + fileName + ". Please try again!", e);
        }
    }
}



