package br.com.erudio.restwithspringbooterudio.modules.file;

import br.com.erudio.restwithspringbooterudio.modules.file.docs.FileControllerDocs;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/file/v1")
public class FileController implements FileControllerDocs {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService service;


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public UploadFileResponse upload(@RequestParam("file") MultipartFile file) {
        String filename = service.store(file);
        var fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/v1/download/")
                .path(filename)
                .toUriString();

        return UploadFileResponse.builder()
                .fileName(filename)
                .fileDownloadUrl(fileDownloadUri)
                .fileType(file.getContentType())
                .size(file.getSize())
                .build();
    }

    @PostMapping(value = "/upload-multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public List<UploadFileResponse> uploadMultiple(@RequestParam("files") List<MultipartFile> files) {
        return files.stream()
                .map(this::upload)
                .toList();
    }

    @GetMapping("/download/{fileName:.+}")
    @Override
    public ResponseEntity<Resource> download(
            @PathVariable("fileName") String fileName,
            HttpServletRequest request
    ) {
        Resource resource = service.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext()
                    .getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            logger.error("Could not determine file type for {}", fileName, e);
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
