package com.example.gestion_biblioteca.repos;

import com.example.gestion_biblioteca.domain.Lector;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LectorRepository extends JpaRepository<Lector, Long> {
}
