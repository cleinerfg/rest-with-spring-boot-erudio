package br.com.erudio.restwithspringbooterudio.modules.file.importer.factory;

import br.com.erudio.restwithspringbooterudio.modules.file.importer.port.FileImporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class FileImporterFactory {

    private final List<FileImporter> importers;
    private final Logger logger = Logger.getLogger(FileImporterFactory.class.getName());

    public FileImporter getImporter(String fileName) {
        logger.info("Using File Importer factory to file: " + fileName);
        return importers.stream()
                .filter(importer -> importer.supports(fileName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Invalid file format for '%s'", fileName)));
    }
}
