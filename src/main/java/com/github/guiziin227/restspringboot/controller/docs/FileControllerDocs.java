package com.github.guiziin227.restspringboot.controller.docs;


import com.github.guiziin227.restspringboot.dto.UploadFileResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.util.List;

public interface FileControllerDocs {

    @Operation(
        summary = "Upload de um arquivo",
        description = "Faz upload de um único arquivo.",
        tags = {"Files Endpoint"},
        responses = {
            @ApiResponse(description = "Sucesso",
                responseCode = "200",
                content = @Content(
                    schema = @Schema(implementation = UploadFileResponseDTO.class)
                )
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    UploadFileResponseDTO uploadFile(MultipartFile file);

    @Operation(
        summary = "Upload de múltiplos arquivos",
        description = "Faz upload de múltiplos arquivos.",
        tags = {"Files Endpoint"},
        responses = {
            @ApiResponse(description = "Sucesso",
                responseCode = "200",
                content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = UploadFileResponseDTO.class))
                )
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    List<UploadFileResponseDTO> uploadMultipartFiles(MultipartFile[] files);

    @Operation(
        summary = "Download de arquivo",
        description = "Faz o download de um arquivo pelo nome.",
        tags = {"Files Endpoint"},
        responses = {
            @ApiResponse(description = "Sucesso",
                responseCode = "200"
            ),
            @ApiResponse(description = "Arquivo não encontrado", responseCode = "404", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    ResponseEntity<ResponseEntity> downloadFile(String fileName, HttpServletRequest request );

}
