package com.example.gestion_biblioteca.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Libroes")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Libro {

    @Id
    @Column(nullable = false, updatable = false)
    private String isbn;

    @Column
    private String titulo;

    @Column
    private String autor;

    @Column
    private Integer anoPublicacion;

    @Column
    private Boolean disponibilidad;

    @ManyToMany
    @JoinTable(
            name = "LibrosPrestamoses",
            joinColumns = @JoinColumn(name = "isbn"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private Set<Prestamo> prestamo;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
