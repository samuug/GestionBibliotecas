package com.example.gestion_biblioteca.rest;

import com.example.gestion_biblioteca.model.LibroDTO;
import com.example.gestion_biblioteca.service.LibroService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/libros", produces = MediaType.APPLICATION_JSON_VALUE)
public class LibroResource {

    private final LibroService libroService;

    public LibroResource(final LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public ResponseEntity<List<LibroDTO>> getAllLibros() {
        return ResponseEntity.ok(libroService.findAll());
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<LibroDTO> getLibro(@PathVariable(name = "isbn") final String isbn) {
        return ResponseEntity.ok(libroService.get(isbn));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createLibro(@RequestBody @Valid final LibroDTO libroDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("isbn") && libroDTO.getIsbn() == null) {
            bindingResult.rejectValue("isbn", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("isbn") && libroService.isbnExists(libroDTO.getIsbn())) {
            bindingResult.rejectValue("isbn", "Exists.libro.isbn");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        final String createdIsbn = libroService.create(libroDTO);
        return new ResponseEntity<>(createdIsbn, HttpStatus.CREATED);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<String> updateLibro(@PathVariable(name = "isbn") final String isbn,
            @RequestBody @Valid final LibroDTO libroDTO) {
        libroService.update(isbn, libroDTO);
        return ResponseEntity.ok(isbn);
    }

    @DeleteMapping("/{isbn}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteLibro(@PathVariable(name = "isbn") final String isbn) {
        libroService.delete(isbn);
        return ResponseEntity.noContent().build();
    }

}
