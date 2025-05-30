package cl.lab.laboratorio.crud.services;

import cl.lab.laboratorio.crud.entities.*;
import cl.lab.laboratorio.crud.exceptions.CrudException;
import cl.lab.laboratorio.crud.utils.Validators;
import cl.lab.laboratorio.crud.model.PostulacionModel;
import cl.lab.laboratorio.crud.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Postulaciones
 * 
 * <p>
 * Proporciona la lógica de negocio para las operaciones CRUD de las postulaciones, incluyendo la gestión de información de acuerdo a postulantes, carreras y facultades
 * </p>
 * 
 * @author William Zubarzo
 * @version 1.3
 * @since 2025-03-31
 */
@Service
public class PostulacionServiceImpl implements PostulacionService {

    private final PostulacionRepository postulacionRepository;
    private final CarreraRepository carreraRepository;
    private final PostulanteRepository postulanteRepository;
    private final BeneficioRepository beneficioRepository;

    private final PostulacionBeneficioRepository postulacionBeneficioRepository;

    @Autowired
    public Validators validators;

    /**
     * Constructor para la inyección de dependencias para el manejo interno (Beneficios, Carrera, Postulación, Postulante y tabla intermedia de Postulación-Beneficio)
     * 
     * @param postulacionRepository Repositorio de funcionalidades de postulaciones
     * @param carreraRepository Repositorio de funcionalidades de carreras
     * @param postulanteRepository Repositorio de funcionalidades de postulantes
     * @param beneficioRepository Repositorio de funcionalidades de beneficios
     * @param postulacionBeneficioRepository Repositorio de funcionalidades de relación postulación-beneficio
     */
    @Autowired
    public PostulacionServiceImpl(PostulacionRepository postulacionRepository,
                                  CarreraRepository carreraRepository,
                                  PostulanteRepository postulanteRepository,
                                  BeneficioRepository beneficioRepository,
                                  PostulacionBeneficioRepository postulacionBeneficioRepository) {
        this.postulacionRepository = postulacionRepository;
        this.carreraRepository = carreraRepository;
        this.postulanteRepository = postulanteRepository;
        this.beneficioRepository = beneficioRepository;
        this.postulacionBeneficioRepository = postulacionBeneficioRepository;
    }

    /**
     * {@inheritDoc}
     * Implementa la creación de postulación validando valores como la existencia del Postulante asociado a la postulación, la construcción de esta y su almacenamiento interno 
     * 
     * @throws RuntimeException si no se encuentra el postulante especificado
     */
    @Override
    public void crearPostulacion(PostulacionModel model) {
        // Validaciones con Validators
        if (model.getBeneficios() == null || model.getBeneficios().isEmpty()) {
            throw new CrudException("La lista de beneficios no puede estar vacía.");
        }

        if (model.getAno_ingreso() == null || !model.getAno_ingreso().matches("\\d{4}")) {
            throw new CrudException("El año de ingreso debe tener exactamente 4 dígitos numéricos (por ejemplo: 2025).");
        }

        if (model.getPostulante_id() == null) {
            throw new CrudException("El ID del postulante es obligatorio.");
        }

        // Buscar postulante
        Postulante postulante = postulanteRepository.findById(model.getPostulante_id())
                .orElseThrow(() -> new CrudException("Postulante no encontrado"));

        // Obtener carrera desde postulante
        Carrera carrera = postulante.getCarrera();

        // Validar que todos los beneficios existen
        for (String nombreBeneficio : model.getBeneficios()) {
            if (!beneficioRepository.existsByNombre(nombreBeneficio)) {
                throw new CrudException("El beneficio '" + nombreBeneficio + "' no existe.");
            }
        }

        // Construir Postulacion
        Postulacion postulacion = Postulacion.builder()
                .ano_ingreso(model.getAno_ingreso())
                .carrera(carrera)
                .postulante(postulante)
                .beneficios(model.getBeneficios())
                .status("Pendiente")
                .build();

        // Guardar postulacion
        postulacionRepository.save(postulacion);

        // Crear registros en postulacion_beneficios
        for (String nombre : model.getBeneficios()) {
            Beneficio beneficio = beneficioRepository.findByNombre(nombre);
            PostulacionBeneficio pb = new PostulacionBeneficio();
            pb.setPostulacion(postulacion);
            pb.setBeneficio(beneficio);
            postulacionBeneficioRepository.save(pb);
        }
    }


    /**
     * {@inheritDoc}
     * Modifica la postulación validando que la postulación exista en el sistema previamente
     * 
     * @throws CrudException si no se encuentra la postulación con el ID especificado
     */
    @Override
    public void modificarPostulacion(PostulacionModel model, Long id) {
        // Obtener el usuario autenticado
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        // Validar rol del usuario
        if (!validators.isValidRole("REGISTRO_ACADEMICO", username)) {
            throw new CrudException("Debe ser parte del registro académico.");
        }

        // Buscar la postulación por ID
        Postulacion postulacion = this.postulacionRepository.findById(id)
                .orElseThrow(() -> new CrudException("Postulación no encontrada."));

        // Solo modificar campos si vienen en el JSON (no son null)
        if (model.getAno_ingreso() != null) {
            postulacion.setAno_ingreso(model.getAno_ingreso());
        }

        if (model.getBeneficios() != null) {
            postulacion.setBeneficios(model.getBeneficios());
        }

        if (model.getStatus() != null) {
            postulacion.setStatus(model.getStatus());
        }

        // Guardar los cambios
        this.postulacionRepository.save(postulacion);
    }

    /**
     * {@inheritDoc}
     * Busca la postulación en el sistema de acuerdo a la ID del postulante y la elimina del sistema
     * 
     */
    @Override
    public void eliminarPostulacion(Long id) {
        Optional<Postulacion> opt = this.postulacionRepository.findById(id);

        opt.ifPresent(postulacion -> {
            postulacionBeneficioRepository.deleteByPostulacionId(id);
            postulacionRepository.delete(postulacion);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PostulacionModel> listarPostulacionesPorPostulanteId(Long id) {
        List<Postulacion> postulaciones = postulacionRepository.findByIdPostulante(id);

        // Convertir las entidades Postulacion a PostulacionModel
        return postulaciones.stream()
                .map(PostulacionModel::new) // Usa el constructor del modelo
                .collect(Collectors.toList());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PostulacionModel> listarPostulacionesPorCarrera(String nombre_carrera) {
        List<Postulacion> postulaciones = postulacionRepository.findByCarrera(nombre_carrera);

        // Convertir las entidades Postulacion a PostulacionModel
        return postulaciones.stream()
                .map(PostulacionModel::new) // Usa el constructor del modelo
                .collect(Collectors.toList());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PostulacionModel> listarPostulacionesPorFacultad(String nombre_facultad) {
        List<Postulacion> postulaciones = postulacionRepository.findByFacultad(nombre_facultad);

        // Convertir las entidades Postulacion a PostulacionModel
        return postulaciones.stream()
                .map(PostulacionModel::new) // Usa el constructor del modelo
                .collect(Collectors.toList());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PostulacionModel> listarPostulaciones() {
        List<Postulacion> postulaciones = postulacionRepository.findAll();

        // Convertir las entidades Postulacion a PostulacionModel
        return postulaciones.stream()
                .map(PostulacionModel::new) // Usa el constructor del modelo
                .collect(Collectors.toList());
    }
}
