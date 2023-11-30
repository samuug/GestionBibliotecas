package com.example.gestion_biblioteca.model;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LibroDTO {

    @Size(max = 255)
    private String isbn;

    @Size(max = 255)
    private String titulo;

    @Size(max = 255)
    private String autor;

    private Integer anoPublicacion;

    private Boolean disponibilidad;

    private List<Long> prestamo;

}
