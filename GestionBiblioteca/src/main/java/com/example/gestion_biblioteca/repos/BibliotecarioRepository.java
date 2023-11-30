package com.example.gestion_biblioteca.repos;

import com.example.gestion_biblioteca.domain.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {
}
