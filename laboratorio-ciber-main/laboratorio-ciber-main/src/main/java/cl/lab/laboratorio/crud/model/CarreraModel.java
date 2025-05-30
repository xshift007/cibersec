package cl.lab.laboratorio.crud.model;

import cl.lab.laboratorio.crud.entities.Carrera;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarreraModel {

    private Long id;
    private Double codigo;
    private String facultad;
    private String nombre;

    public CarreraModel(Carrera p) {
        super();
        this.id = p.getId();
        this.codigo = p.getCodigo().doubleValue();
        this.facultad = p.getFacultad();
        this.nombre = p.getNombre();
    }

}
