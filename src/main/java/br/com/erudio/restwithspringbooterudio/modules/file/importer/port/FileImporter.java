package br.com.erudio.restwithspringbooterudio.modules.file.importer.port;

import br.com.erudio.restwithspringbooterudio.modules.person.data.dto.PersonRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<PersonRequest> importFile(InputStream inputStream) throws IOException;

    boolean supports(String fileName);

}
