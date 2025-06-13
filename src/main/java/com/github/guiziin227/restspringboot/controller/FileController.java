package com.github.guiziin227.restspringboot.controller;

import com.github.guiziin227.restspringboot.controller.docs.FileControllerDocs;
import com.github.guiziin227.restspringboot.dto.UploadFileResponseDTO;
import com.github.guiziin227.restspringboot.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/file/v1")
@RequiredArgsConstructor
public class FileController implements FileControllerDocs {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private FileStorageService fileStorageService;

    @PostMapping(value = "/upload")
    @Override
    public UploadFileResponseDTO uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        // http://localhost:8080/api/file/v1/downloadFile/{fileName}.docx
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/v1/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponseDTO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    @Override
    public List<UploadFileResponseDTO> uploadMultipartFiles(MultipartFile[] files) {
        return List.of();
    }

    @Override
    public ResponseEntity<ResponseEntity> downloadFile(String fileName, HttpServletRequest request) {
        return null;
    }
}
