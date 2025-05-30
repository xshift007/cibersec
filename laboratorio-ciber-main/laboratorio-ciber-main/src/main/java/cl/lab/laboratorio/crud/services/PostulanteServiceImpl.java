package cl.lab.laboratorio.crud.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.lab.laboratorio.crud.entities.Postulante;
import cl.lab.laboratorio.crud.entities.Usuario;
import cl.lab.laboratorio.crud.entities.Beneficio;
import cl.lab.laboratorio.crud.entities.Carrera;
import cl.lab.laboratorio.crud.entities.PostulanteBeneficio;
import cl.lab.laboratorio.crud.exceptions.CrudException;
import cl.lab.laboratorio.crud.model.PostulanteModel;
import cl.lab.laboratorio.crud.repository.BeneficioRepository;
import cl.lab.laboratorio.crud.repository.CarreraRepository;
import cl.lab.laboratorio.crud.repository.PostulanteBeneficioRepository;
import cl.lab.laboratorio.crud.repository.PostulanteRepository;
import cl.lab.laboratorio.crud.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de Postulante
 * 
 * <p>
 * Proporciona la lógica de negocio para las operaciones CRUD de un postulante, incluyendo la gestión de información de acuerdo elementos como el RUT de un postulante y sus beneficios
 * </p>
 * 
 * @author Mauricio Valdés
 * @version 1.1
 * @since 2025-03-30
 */
@Service
public class PostulanteServiceImpl implements PostulanteService{

    private final PostulanteRepository postulanteRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarreraRepository carreraRepository;
    private final PostulanteBeneficioRepository postulanteBeneficioRepository;
    private final BeneficioRepository beneficioRepository;

    /**
     * Constructor para la inyección de dependencias para el manejo interno (Beneficios, Carrera, Postulación, Postulante y Usuario)
     * 
     * @param postulacionRepository Repositorio de funcionalidades de postulaciones
     * @param carreraRepository Repositorio de funcionalidades de carreras
     * @param postulanteRepository Repositorio de funcionalidades de postulantes
     * @param beneficioRepository Repositorio de funcionalidades de beneficios
     * @param usuarioRepository Repositorio de funcionalidades de usuarios
     */
    @Autowired
    public PostulanteServiceImpl(PostulanteRepository postulanteRepository, UsuarioRepository usuarioRepository, CarreraRepository carreraRepository, PostulanteBeneficioRepository postulanteBeneficioRepository, BeneficioRepository beneficioRepository) {
        this.postulanteRepository = postulanteRepository;
        this.usuarioRepository = usuarioRepository;
        this.carreraRepository = carreraRepository;
        this.postulanteBeneficioRepository = postulanteBeneficioRepository;
        this.beneficioRepository = beneficioRepository;
    }

    /** 
     * {@inheritDoc}
     * Implementa la creación de un postulante, validando valores como la existencia de un Usuario y Carrera objetivos, la construcción de este y su almacenamiento interno
     * 
     * @throws CrudException si es que no existe el usuario al cual asociar la postulación
     * @throws CrudException si es que no se encuentra la carrera a la cual asociar al usuario
     */
    @Override
    public void crearPostulante(PostulanteModel model) {
        Usuario usuario = usuarioRepository.findById(model.getUsuarioId())
            .orElseThrow(() -> new CrudException("Usuario no encontrado"));
        
        Carrera carrera = carreraRepository.findById(model.getCarrera())
            .orElseThrow(() -> new CrudException("Carrera no encontrada"));

        Postulante postulante = Postulante.builder()
                .usuario(usuario)
                .nombre(model.getNombre())
                .rut(model.getRut())
                .carrera(carrera)
                .direccion(model.getDireccion())
                .nem(BigDecimal.valueOf(model.getNem()))
                .ranking(BigDecimal.valueOf(model.getRanking()))
                .build();

        PostulanteBeneficio postulanteBeneficio = postulanteBeneficioRepository.findByPostulanteId(postulante.getId());
        if (postulanteBeneficio == null) {
            postulanteBeneficio = new PostulanteBeneficio();
        }

        postulanteBeneficio.setPostulante(postulante);
        postulanteBeneficio.setBeneficio(null);
        
        this.postulanteRepository.save(postulante);
        this.postulanteBeneficioRepository.save(postulanteBeneficio);
        
    }

    /**
     * {@inheritDoc}
     * 
     * Implementa la obtención de un postulante mediante su ID validando la existencia de este y la lectura de este en el sistema
     * @throws CrudException si es que no existe el postulante
     */
    @Override
    public PostulanteModel obtenerPostulantePorId(Long id) {
        Optional<Postulante> postulanteOpt = this.postulanteRepository.findById(id);
        if (postulanteOpt.isEmpty()) {
            throw new CrudException("Postulante no encontrado.");
        }
        return new PostulanteModel(postulanteOpt.get());
    }

    /**
     * {@inheritDoc}
     * 
     * Implementa la modificación de un postulante validando la existencia de este en el sistema y verificando que la obtención de información se obtenga siempre que no haya valores vacíos
     * @throws CrudException si es que no existe el postulante
     */
    @Override
    public void modificarPostulante(PostulanteModel model, Long id) {

        Optional<Postulante> postulanteOpt = this.postulanteRepository.findById(id);
        if (postulanteOpt.isEmpty()) {
            throw new CrudException("Postulante no encontrado.");
        }

        Postulante postulante = postulanteOpt.get();
        if (model.getNombre() != null) {
            postulante.setNombre(model.getNombre());
        }
        if (model.getRut() != null) {
            postulante.setRut(model.getRut());
        }
        if (model.getCarrera() != null) {
            postulante.setCarrera(carreraRepository.findById(model.getCarrera())
                .orElseThrow(() -> new CrudException("Carrera no encontrada")));
        }
        if (model.getDireccion() != null) {
            postulante.setDireccion(model.getDireccion());
        }
        if (model.getNem() != null) {
            postulante.setNem(BigDecimal.valueOf(model.getNem()));
        }
        if (model.getRanking() != null) {
            postulante.setRanking(BigDecimal.valueOf(model.getRanking()));
        }

        this.postulanteRepository.save(postulante);

    }

    /**
     * {@inheritDoc}
     * Implementa la asignación de un beneficio a un postulante objetivo validando la existencia del postulante y del beneficio a asignar
     * 
     * @throws CrudException si no existe el postulante
     * @throws CrudException si no existe el beneficio
     */
    @Override
    @Transactional
    public void asignarBeneficio(Long idPostulante, Long idBeneficio) {
        // Verificar postulante
        Postulante postulante = postulanteRepository.findById(idPostulante)
            .orElseThrow(() -> new CrudException("Postulante no encontrado"));
        // Verificar beneficio
        Beneficio beneficio = beneficioRepository.findById(idBeneficio)
            .orElseThrow(() -> new CrudException("Beneficio no encontrado"));

        // Crear nueva asignación
        PostulanteBeneficio nuevaAsignacion = new PostulanteBeneficio();
        nuevaAsignacion.setPostulante(postulante);
        nuevaAsignacion.setBeneficio(beneficio);

        postulanteBeneficioRepository.save(nuevaAsignacion);
    }

    /**
     * {@inheritDoc}
     * Implementa la desasignación de un beneficio a un postulante objetivo validando la existencia del postulante y del beneficio a desasignar
     * 
     * @throws CrudException si no existe el postulante
     * @throws CrudException si no existe el beneficio
     */
    @Override
    @Transactional
    public void eliminarBeneficio(Long idPostulante, Long idBeneficio) {
    
        // Verificar postulante
        Postulante postulante = postulanteRepository.findById(idPostulante)
        .orElseThrow(() -> new CrudException("Postulante no encontrado"));

        // Verificar beneficio
        Beneficio beneficio = beneficioRepository.findById(idBeneficio)
            .orElseThrow(() -> new CrudException("Beneficio no encontrado"));

        // Eliminar asignaciones anteriores (opcional, según tu modelo de negocio)
        postulanteBeneficioRepository.deleteByPostulanteIdAndBeneficioId(idPostulante, idBeneficio);
    }


    /**
     * {@inheritDoc}
     * 
     * Implementa la obtención de un listado de beneficios que posee un postulante en el sistema, validando la existencia de este postulante objetivo
     * 
     * @throws CrudException si es que no existe el postulante en el sistema
     */
    @Override
    public List<Beneficio> listarBeneficiosPorPostulante(Long id) {
        Optional<Postulante> postulanteOpt = this.postulanteRepository.findById(id);
        if (postulanteOpt.isEmpty()) {
            throw new CrudException("Postulante no encontrado.");
        }
        Postulante postulante = postulanteOpt.get();
        return this.postulanteBeneficioRepository.findBeneficiosByPostulanteId(postulante.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PostulanteModel> listarPostulantes(String nombre) {
        return this.postulanteRepository.listarPostulantes(nombre);
    }

    /**
     * {@inheritDoc}
     * 
     * Implementa la obtención de un listado de postulantes dado un RUT específico (puede ser parcial), mientras internamente modifica valores problemáticos como los '.' y '-'
     * que posee un RUT en formato convencional
     */
    @Override
    public List<PostulanteModel> listarPostulantesPorRut(String rut) {
        // Limpia el RUT (opcional, para manejar formatos 12.345.678-9 vs 12345678-9)
        String rutLimpio = rut != null ? rut.replaceAll("[.-]", "") : null;
        return postulanteRepository.listarPostulantesPorRut(rutLimpio);
    }

    /**
     * {@inheritDoc}
     * 
     * Implementa la eliminación de un postulante del sistema siempre que este exista en este, validándolo desde la base de datos del sistema
     * 
     * @throws CrudException si el postulante no existe en el sistema
     */
    @Override
    public void eliminarPostulante(Long id) {
        Optional<Postulante> opt = this.postulanteRepository.findById(id);
        if (opt.isEmpty()) {
            throw new CrudException("Postulante no encontrado.");
        }
        opt.ifPresent(postulante -> {
            postulanteBeneficioRepository.deleteByPostulanteId(id);
            postulanteRepository.delete(postulante);
        });

    }
    
}
