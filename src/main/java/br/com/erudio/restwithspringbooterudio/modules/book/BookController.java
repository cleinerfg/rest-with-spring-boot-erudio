package br.com.erudio.restwithspringbooterudio.modules.book;

import br.com.erudio.restwithspringbooterudio.modules.book.data.dto.BookRequest;
import br.com.erudio.restwithspringbooterudio.modules.book.data.dto.BookResponse;
import br.com.erudio.restwithspringbooterudio.modules.book.docs.BookControllerDocs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
public class BookController implements BookControllerDocs {

    @Autowired
    private BookService service;

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE
    })
    @Override
    public ResponseEntity<List<BookResponse>> getAll() {
        List<BookResponse> responses = service.findAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping(
            value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            })
    @Override
    public ResponseEntity<BookResponse> getById(@PathVariable("id") Long id) {
        BookResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            })
    @Override
    public ResponseEntity<BookResponse> create(@RequestBody BookRequest request) {
        BookResponse createdBook = service.create(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBook.getId())
                .toUri();

        return ResponseEntity.created(uri).body(createdBook);
    }

    @PutMapping(
            value = "/{id}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE
            })
    @Override
    public ResponseEntity<BookResponse> update(
            @PathVariable("id") Long id,
            @RequestBody BookRequest request) {
        BookResponse updatedBook = service.update(id, request);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
