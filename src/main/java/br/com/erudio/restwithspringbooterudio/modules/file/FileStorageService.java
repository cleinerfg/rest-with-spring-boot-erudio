package br.com.erudio.restwithspringbooterudio.modules.file;

import br.com.erudio.restwithspringbooterudio.config.FileStorageConfig;
import br.com.erudio.restwithspringbooterudio.exceptions.FileNotFoundException;
import br.com.erudio.restwithspringbooterudio.exceptions.FileStorageException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath()
                .normalize();

        fileStorageLocation = path;

        try {
            Files.createDirectories(path);
        } catch (Exception e) {
            throw new FileStorageException("Could not create directory!", e);
        }
    }

    public String store(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains(".."))
                throw new FileStorageException("Filename contains a invalid path Sequence" + fileName, null);

            Path targetLocation = fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            throw new FileStorageException("Could not store file " + fileName, e);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) return resource;
            throw new FileNotFoundException("Could not load file " + fileName, null);
        } catch (Exception e) {
            throw new FileNotFoundException("Could not load file " + fileName, e);
        }
    }
}
