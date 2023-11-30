package com.example.gestion_biblioteca.repos;

import com.example.gestion_biblioteca.domain.Libro;
import com.example.gestion_biblioteca.domain.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LibroRepository extends JpaRepository<Libro, String> {

    Libro findFirstByPrestamo(Prestamo prestamo);

    List<Libro> findAllByPrestamo(Prestamo prestamo);

    boolean existsByIsbnIgnoreCase(String isbn);

}
