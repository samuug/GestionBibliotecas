package com.example.gestion_biblioteca.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PrestamoDTO {

    private Long id;
    private LocalDateTime fechaVencimiento;
    private Boolean activo;
    private Long lector;
    private Long bibliotecario;

}
