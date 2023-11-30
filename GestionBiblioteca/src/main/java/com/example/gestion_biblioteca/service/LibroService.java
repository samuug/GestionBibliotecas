package com.example.gestion_biblioteca.service;

import com.example.gestion_biblioteca.domain.Libro;
import com.example.gestion_biblioteca.domain.Prestamo;
import com.example.gestion_biblioteca.model.LibroDTO;
import com.example.gestion_biblioteca.repos.LibroRepository;
import com.example.gestion_biblioteca.repos.PrestamoRepository;
import com.example.gestion_biblioteca.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class LibroService {

    private final LibroRepository libroRepository;
    private final PrestamoRepository prestamoRepository;

    public LibroService(final LibroRepository libroRepository,
            final PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public List<LibroDTO> findAll() {
        final List<Libro> libroes = libroRepository.findAll(Sort.by("isbn"));
        return libroes.stream()
                .map(libro -> mapToDTO(libro, new LibroDTO()))
                .toList();
    }

    public LibroDTO get(final String isbn) {
        return libroRepository.findById(isbn)
                .map(libro -> mapToDTO(libro, new LibroDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final LibroDTO libroDTO) {
        final Libro libro = new Libro();
        mapToEntity(libroDTO, libro);
        libro.setIsbn(libroDTO.getIsbn());
        return libroRepository.save(libro).getIsbn();
    }

    public void update(final String isbn, final LibroDTO libroDTO) {
        final Libro libro = libroRepository.findById(isbn)
                .orElseThrow(NotFoundException::new);
        mapToEntity(libroDTO, libro);
        libroRepository.save(libro);
    }

    public void delete(final String isbn) {
        libroRepository.deleteById(isbn);
    }

    private LibroDTO mapToDTO(final Libro libro, final LibroDTO libroDTO) {
        libroDTO.setIsbn(libro.getIsbn());
        libroDTO.setTitulo(libro.getTitulo());
        libroDTO.setAutor(libro.getAutor());
        libroDTO.setAnoPublicacion(libro.getAnoPublicacion());
        libroDTO.setDisponibilidad(libro.getDisponibilidad());
        libroDTO.setPrestamo(libro.getPrestamo().stream()
                .map(prestamo -> prestamo.getId())
                .toList());
        return libroDTO;
    }

    private Libro mapToEntity(final LibroDTO libroDTO, final Libro libro) {
        libro.setTitulo(libroDTO.getTitulo());
        libro.setAutor(libroDTO.getAutor());
        libro.setAnoPublicacion(libroDTO.getAnoPublicacion());
        libro.setDisponibilidad(libroDTO.getDisponibilidad());
        final List<Prestamo> prestamo = prestamoRepository.findAllById(
                libroDTO.getPrestamo() == null ? Collections.emptyList() : libroDTO.getPrestamo());
        if (prestamo.size() != (libroDTO.getPrestamo() == null ? 0 : libroDTO.getPrestamo().size())) {
            throw new NotFoundException("one of prestamo not found");
        }
        libro.setPrestamo(prestamo.stream().collect(Collectors.toSet()));
        return libro;
    }

    public boolean isbnExists(final String isbn) {
        return libroRepository.existsByIsbnIgnoreCase(isbn);
    }

}
