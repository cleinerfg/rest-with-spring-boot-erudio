package br.com.erudio.restwithspringbooterudio.modules.file.exporter.port;

import br.com.erudio.restwithspringbooterudio.modules.person.Person;
import org.springframework.core.io.Resource;

import java.util.List;

public interface FileExporter {

    Resource export(List<Person> persons);

    boolean supports(String acceptHeader);
}
