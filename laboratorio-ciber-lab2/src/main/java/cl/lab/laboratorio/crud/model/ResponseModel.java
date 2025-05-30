package cl.lab.laboratorio.crud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que presenta las propiedades de
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ResponseModel<T> {

    private String message;
    private String error;
    private Boolean success;
    private T data;

}
