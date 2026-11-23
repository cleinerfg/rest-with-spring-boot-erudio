package br.com.erudio.restwithspringbooterudio.modules.file.exporter.adapter;

import br.com.erudio.restwithspringbooterudio.modules.file.exporter.port.FileExporter;
import br.com.erudio.restwithspringbooterudio.modules.person.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvExporter implements FileExporter {

    private static final String[] HEADERS = {
            "id",
            "first_name",
            "last_name",
            "address",
            "enabled"
    };

    @Override
    public Resource export(List<Person> persons) {
        var outputStream = new ByteArrayOutputStream();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader(HEADERS)
                .get();

        try (var writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
             var csvPrinter = new CSVPrinter(writer, csvFormat)) {

            for (Person person : persons) {
                csvPrinter.printRecord(
                        person.getId(),
                        person.getFirstName(),
                        person.getLastName(),
                        person.getAddress(),
                        person.getEnabled()
                );
            }

        } catch (IOException exception) {
            throw new IllegalStateException("Failed to export persons to CSV", exception);
        }

        return new ByteArrayResource(outputStream.toByteArray());
    }

    @Override
    public boolean supports(String acceptHeader) {
        return "text/plain".equalsIgnoreCase(acceptHeader)
                || "text/csv".equalsIgnoreCase(acceptHeader);
    }
}
