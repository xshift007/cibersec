package cl.lab.laboratorio.crud.services;

import cl.lab.laboratorio.crud.entities.*;
import cl.lab.laboratorio.crud.exceptions.CrudException;
import cl.lab.laboratorio.crud.model.CarreraModel;
import cl.lab.laboratorio.crud.model.PostulacionModel;
import cl.lab.laboratorio.crud.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        // Buscar al postulante
        Postulante postulante = this.postulanteRepository.findById(model.getPostulante_id())
                .orElseThrow(() -> new RuntimeException("Postulante no encontrado"));

        // Obtener la carrera desde el postulante
        Carrera carrera = postulante.getCarrera();

        // Obtener carrera 1 de bd para pruebas
        // Carrera carrera = carreraRepository.findAll().get(0);

        // Construir la Postulación
        Postulacion postulacion = Postulacion.builder()
                .ano_ingreso(model.getAno_ingreso())
                .carrera(carrera) // Se asigna la carrera obtenida del postulante
                .postulante(postulante) // Se asigna el postulante
                .beneficios(model.getBeneficios()) // Se asignan los beneficios recibidos
                .build();

        // Guardar en la base de datos
        this.postulacionRepository.save(postulacion);

        // Crear postulacion_beneficios
        for (int i = 0;i < model.getBeneficios().size();i++) {
            PostulacionBeneficio pb = new PostulacionBeneficio();
            Beneficio beneficio = beneficioRepository.findByNombre(model.getBeneficios().get(i));
            pb.setPostulacion(postulacion);
            pb.setBeneficio(beneficio);
            this.postulacionBeneficioRepository.save(pb);
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
        Optional<Postulacion> postulacionOpt = this.postulacionRepository.findById(id);
        if (postulacionOpt.isEmpty()) {
            throw new CrudException("postulacion no encontrada.");
        }

        Postulacion postulacion = postulacionOpt.get();
        postulacion.setAno_ingreso(model.getAno_ingreso());
        postulacion.setBeneficios(model.getBeneficios());

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
