package br.com.erudio.restwithspringbooterudio.modules.file.docs;

import br.com.erudio.restwithspringbooterudio.modules.file.UploadFileResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "File Endpoint")
public interface FileControllerDocs {

    UploadFileResponse upload(MultipartFile file);

    List<UploadFileResponse> uploadMultiple(List<MultipartFile> files);

    ResponseEntity<Resource> download(String fileName, HttpServletRequest request);
}
