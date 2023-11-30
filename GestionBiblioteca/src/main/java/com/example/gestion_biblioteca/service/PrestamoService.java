package com.example.gestion_biblioteca.service;

import com.example.gestion_biblioteca.domain.Bibliotecario;
import com.example.gestion_biblioteca.domain.Lector;
import com.example.gestion_biblioteca.domain.Libro;
import com.example.gestion_biblioteca.domain.Prestamo;
import com.example.gestion_biblioteca.model.PrestamoDTO;
import com.example.gestion_biblioteca.repos.BibliotecarioRepository;
import com.example.gestion_biblioteca.repos.LectorRepository;
import com.example.gestion_biblioteca.repos.LibroRepository;
import com.example.gestion_biblioteca.repos.PrestamoRepository;
import com.example.gestion_biblioteca.util.NotFoundException;
import com.example.gestion_biblioteca.util.WebUtils;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final LectorRepository lectorRepository;
    private final BibliotecarioRepository bibliotecarioRepository;
    private final LibroRepository libroRepository;

    public PrestamoService(final PrestamoRepository prestamoRepository,
            final LectorRepository lectorRepository,
            final BibliotecarioRepository bibliotecarioRepository,
            final LibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.lectorRepository = lectorRepository;
        this.bibliotecarioRepository = bibliotecarioRepository;
        this.libroRepository = libroRepository;
    }

    public List<PrestamoDTO> findAll() {
        final List<Prestamo> prestamoes = prestamoRepository.findAll(Sort.by("id"));
        return prestamoes.stream()
                .map(prestamo -> mapToDTO(prestamo, new PrestamoDTO()))
                .toList();
    }

    public PrestamoDTO get(final Long id) {
        return prestamoRepository.findById(id)
                .map(prestamo -> mapToDTO(prestamo, new PrestamoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PrestamoDTO prestamoDTO) {
        final Prestamo prestamo = new Prestamo();
        mapToEntity(prestamoDTO, prestamo);
        return prestamoRepository.save(prestamo).getId();
    }

    public void update(final Long id, final PrestamoDTO prestamoDTO) {
        final Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(prestamoDTO, prestamo);
        prestamoRepository.save(prestamo);
    }

    public void delete(final Long id) {
        final Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
        libroRepository.findAllByPrestamo(prestamo)
                .forEach(libro -> libro.getPrestamo().remove(prestamo));
        prestamoRepository.delete(prestamo);
    }

    private PrestamoDTO mapToDTO(final Prestamo prestamo, final PrestamoDTO prestamoDTO) {
        prestamoDTO.setId(prestamo.getId());
        prestamoDTO.setFechaVencimiento(prestamo.getFechaVencimiento());
        prestamoDTO.setActivo(prestamo.getActivo());
        prestamoDTO.setLector(prestamo.getLector() == null ? null : prestamo.getLector().getId());
        prestamoDTO.setBibliotecario(prestamo.getBibliotecario() == null ? null : prestamo.getBibliotecario().getId());
        return prestamoDTO;
    }

    private Prestamo mapToEntity(final PrestamoDTO prestamoDTO, final Prestamo prestamo) {
        prestamo.setFechaVencimiento(prestamoDTO.getFechaVencimiento());
        prestamo.setActivo(prestamoDTO.getActivo());
        final Lector lector = prestamoDTO.getLector() == null ? null : lectorRepository.findById(prestamoDTO.getLector())
                .orElseThrow(() -> new NotFoundException("lector not found"));
        prestamo.setLector(lector);
        final Bibliotecario bibliotecario = prestamoDTO.getBibliotecario() == null ? null : bibliotecarioRepository.findById(prestamoDTO.getBibliotecario())
                .orElseThrow(() -> new NotFoundException("bibliotecario not found"));
        prestamo.setBibliotecario(bibliotecario);
        return prestamo;
    }

    public String getReferencedWarning(final Long id) {
        final Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Libro prestamoLibro = libroRepository.findFirstByPrestamo(prestamo);
        if (prestamoLibro != null) {
            return WebUtils.getMessage("prestamo.libro.prestamo.referenced", prestamoLibro.getIsbn());
        }
        return null;
    }

}
