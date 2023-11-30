package com.example.gestion_biblioteca.repos;

import com.example.gestion_biblioteca.domain.Bibliotecario;
import com.example.gestion_biblioteca.domain.Lector;
import com.example.gestion_biblioteca.domain.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    Prestamo findFirstByLector(Lector lector);

    Prestamo findFirstByBibliotecario(Bibliotecario bibliotecario);

}
