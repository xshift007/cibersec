package cl.lab.laboratorio.crud.services;

import cl.lab.laboratorio.crud.entities.Beneficio;
import cl.lab.laboratorio.crud.exceptions.CrudException;
import cl.lab.laboratorio.crud.model.BeneficioModel;
import cl.lab.laboratorio.crud.repository.BeneficioRepository;
import cl.lab.laboratorio.crud.repository.PostulacionBeneficioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de {@link BeneficioService} que gestiona las operaciones de los beneficios.
 * 
 * <p>
 * Esta implementación incluye validaciones de las funcionalidades y manejo de errores.
 * </p>
 * 
 * @author Benjamín Canales
 * @version 1.0
 * @since 2025-03-28
 */
@Service
public class BeneficioServiceImpl implements BeneficioService {


    private final BeneficioRepository beneficioRepository;

    private final PostulacionBeneficioRepository postulacionBeneficioRepository;

    /**
     * Constructor de la clase BeneficioService para crear un objeto Beneficio.
     * 
     * @param beneficioRepository Repositorio con las operaciones de un Beneficio
     * @param postulacionBeneficioRepository Repositorio con las operaciones de la entidad intermedia entre Postulación y Beneficio
     */
    @Autowired
    public BeneficioServiceImpl(BeneficioRepository beneficioRepository, PostulacionBeneficioRepository postulacionBeneficioRepository) {
        this.beneficioRepository = beneficioRepository;
        this.postulacionBeneficioRepository = postulacionBeneficioRepository;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Esta implementación crea un Beneficio validando que no existe uno igual previamente
     * </p>
     * 
     * @throws CrudException si es que ya existe un beneficio igual
     */
    @Override
    public void crearBeneficio(BeneficioModel model) {
        if (beneficioRepository.existsByCodigo(BigDecimal.valueOf(model.getCodigo()))) {
            throw new CrudException("beneficio con el mismo codigo ya existe.");
        }

        Beneficio beneficio = Beneficio.builder()
                .codigo(BigDecimal.valueOf(model.getCodigo()))
                .nombre(model.getNombre())
                .build();

        beneficioRepository.save(beneficio);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Esta implementación modifica un beneficio, verificando la existencia de este beneficio.
     * </p>
     * 
     * @throws CrudException si el beneficio no existe
     */
    @Override
    public void modificarBeneficio(BeneficioModel model, Long id) {
        Optional<Beneficio> beneficioOpt = this.beneficioRepository.findById(id);
        if (beneficioOpt.isEmpty()) {
            throw new CrudException("beneficio no encontrada.");
        }

        if (beneficioRepository.existsByCodigo(BigDecimal.valueOf(model.getCodigo()))) {
            throw new CrudException("beneficio con el mismo codigo ya existe.");
        }

        Beneficio beneficio = beneficioOpt.get();
        beneficio.setCodigo(BigDecimal.valueOf(model.getCodigo()));
        beneficio.setNombre(model.getNombre());
        this.beneficioRepository.save(beneficio);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Si el parámetro nombre es null o vacío, devuelve todos los beneficios.
     * </p>
     */
    @Override
    public List<BeneficioModel> listarBeneficios(String nombre) {
        return this.beneficioRepository.listarBeneficiosPorNombre(nombre);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Esta implementación también elimina todas las relaciones con postulaciones antes de borrar el beneficio.
     * </p>
     */
    @Override
    public void eliminarBeneficio(Long id) {
        Optional<Beneficio> opt = this.beneficioRepository.findById(id);
        opt.ifPresent(beneficio -> {
            postulacionBeneficioRepository.deleteByBeneficioId(id);
            beneficioRepository.delete(beneficio);
        });
    }
}
