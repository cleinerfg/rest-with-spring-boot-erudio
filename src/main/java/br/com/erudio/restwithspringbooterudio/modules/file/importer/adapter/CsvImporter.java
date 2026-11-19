package br.com.erudio.restwithspringbooterudio.modules.file.importer.adapter;

import br.com.erudio.restwithspringbooterudio.modules.file.importer.port.FileImporter;
import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonRequest;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvImporter implements FileImporter {

    @Override
    public List<PersonRequest> importFile(InputStream inputStream) throws IOException {
        CSVFormat format = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreEmptyLines(true)
                .setTrim(true)
                .get();

        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             CSVParser parser = format.parse(reader)) {
            return parseRecords(parser);
        }
    }

    @Override
    public boolean supports(String fileName) {
        return fileName != null && fileName.trim().toLowerCase().endsWith(".csv");
    }

    private List<PersonRequest> parseRecords(Iterable<CSVRecord> records) {
        List<PersonRequest> personRequests = new ArrayList<>();
        for (CSVRecord csvRecord : records) {
            PersonRequest request = PersonRequest.builder()
                    .firstName(csvRecord.get("first_name"))
                    .lastName(csvRecord.get("last_name"))
                    .address(csvRecord.get("address"))
                    .gender(csvRecord.get("gender"))
                    .enabled(true)
                    .build();
            personRequests.add(request);
        }
        return personRequests;
    }
}
