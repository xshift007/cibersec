package cl.lab.laboratorio.crud.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private String sid;
    private String username;
    private String nombres;
    private String apellidos;
    private String correo;
}
