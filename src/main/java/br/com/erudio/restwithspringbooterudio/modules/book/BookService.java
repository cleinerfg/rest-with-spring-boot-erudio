package br.com.erudio.restwithspringbooterudio.modules.book;

import br.com.erudio.restwithspringbooterudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.restwithspringbooterudio.exceptions.ResourceNotFoundException;
import br.com.erudio.restwithspringbooterudio.modules.book.data.BookMapper;
import br.com.erudio.restwithspringbooterudio.modules.book.data.dto.BookRequest;
import br.com.erudio.restwithspringbooterudio.modules.book.data.dto.BookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private final Logger logger = LoggerFactory.getLogger(BookService.class.getName());

    @Autowired
    private BookRepository repository;

    public BookResponse findById(Long id) {
        logger.info("Finding book by id!");
        Book entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found with id: " + id)
        );
        var dto = BookMapper.toResponse(entity);
        addHateosLinks(dto);
        return dto;
    }

    public List<BookResponse> findAll() {
        logger.info("Finding all books!");
        var entities = repository.findAll();
        var dtos = entities.stream()
                .map(BookMapper::toResponse)
                .toList();

        dtos.forEach(BookService::addHateosLinks);
        return dtos;
    }

    public BookResponse create(BookRequest request) {

        if (request == null) throw new RequiredObjectIsNullException();

        logger.info("Creating book!");
        Book entity = BookMapper.toEntity(request);
        Book savedEntity = repository.save(entity);
        var dto = BookMapper.toResponse(savedEntity);
        addHateosLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting book!");

        if (!repository.existsById(id))
            throw new ResourceNotFoundException("No records found with id: " + id);
        repository.deleteById(id);
    }

    public BookResponse update(Long id, BookRequest request) {
        if (request == null) throw new RequiredObjectIsNullException();

        logger.info("Updating book!");
        Book entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No records found with id: " + id)
        );

        entity.setAuthor(request.author());
        entity.setLaunchDate(request.launchDate());
        entity.setPrice(request.price());
        entity.setTitle(request.title());

        Book updatedEntity = repository.save(entity);
        var dto = BookMapper.toResponse(updatedEntity);
        addHateosLinks(dto);
        return dto;
    }

    private static void addHateosLinks(BookResponse dto) {
        Long id = dto.getId();

        dto.add(linkTo(methodOn(BookController.class).getById(id))
                .withSelfRel()
                .withType("GET")
        );

        dto.add(linkTo(methodOn(BookController.class).getAll())
                .withRel("getAll")
                .withType("GET")
        );

        dto.add(linkTo(methodOn(BookController.class).create(null))
                .withRel("create")
                .withType("POST")
        );

        dto.add(linkTo(methodOn(BookController.class).update(id, null))
                .withRel("update")
                .withType("PUT")
        );

        dto.add(linkTo(methodOn(BookController.class).delete(id))
                .withRel("delete")
                .withType("DELETE")
        );
    }
}
