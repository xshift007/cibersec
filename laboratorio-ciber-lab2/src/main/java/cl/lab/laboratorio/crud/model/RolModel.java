package cl.lab.laboratorio.crud.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolModel {

    private Long id;
    private String descripcion;

}
