package br.com.erudio.restwithspringbooterudio.modules.file.exporter.factory;

import br.com.erudio.restwithspringbooterudio.modules.file.exporter.port.FileExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class FileExporterFactory {

    private final List<FileExporter> exporters;
    private final Logger logger = Logger.getLogger(FileExporterFactory.class.getName());

    public FileExporter getExporter(String acceptHeader) {
        logger.info("Using File Exporter factory to accept header: " + acceptHeader);
        return exporters.stream()
                .filter(exporter -> exporter.supports(acceptHeader))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Invalid accept header for '%s'", acceptHeader)));
    }
}
