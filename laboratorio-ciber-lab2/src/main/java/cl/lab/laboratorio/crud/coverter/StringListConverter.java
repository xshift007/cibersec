package cl.lab.laboratorio.crud.coverter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Conversor JPA para transformar entre {@code List<String>} y {@code String}
 * 
 * <p>
 * Implementa {@link AttributeConverter} para convertir listas de strings en una representación de cadena concatenada, y viceversa para la lectura desde la base de datos.
 * </p>
 * 
 * <p>
 * Se utiliza para poder guardar varios elementos en un único campo
 * </p>
 *
 * @author William Zubarzo
 * @version 1.0
 * @since 2025-03-29
 */
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    /**
     * Separador utilizado para unir y dividir los valores de la lista.
     * 
     * <p>
     * La separación se hace mediante ','
     * </p>
     */
    private static final String SEPARATOR = ","; // Separador para los valores

    /**
     * Convierte una lista de strings a su representación interna como un único String.
     * 
     * <p>
     * Une los elementos de la lista usando el separador definido (',').
     * </p>
     * 
     * @param attribute Lista de strings a convertir
     * @return Cadena concatenada con los valores separados por {@link #SEPARATOR}
     */
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return (attribute == null || attribute.isEmpty()) ? null : String.join(SEPARATOR, attribute);
    }

    /**
     * Convierte una cadena de base de datos a lista de strings.
     * 
     * <p>
     * Divide la cadena usando el separador definido (',').
     * </p>
     * 
     * @param dbData Cadena almacenada en la base de datos
     * @return Lista de strings
     */
    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return (dbData == null || dbData.isEmpty()) ? List.of() : Arrays.asList(dbData.split(SEPARATOR));
    }
}
