package br.com.erudio.restwithspringbooterudio.modules.file;

import lombok.Builder;

@Builder
public record UploadFileResponse(
        String fileName,
        String fileDownloadUrl,
        String fileType,
        Long size
) {
}
