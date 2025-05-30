package cl.lab.laboratorio.crud.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "USUARIO", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}), @UniqueConstraint(columnNames = {"correo"})})
@SequenceGenerator(name = "usuario_sequence", sequenceName = "usuario_sequence", allocationSize = 1, initialValue = 5)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "sid")
    private String sid;

    @Column(name = "username")
    private String username;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "password")
    private String password;

    @Column(name = "correo")
    private String correo;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<UsuarioRol> usuarioRoles;

    @Column(name = "failedAttempts")
    private int failedAttempts = 0;

    private LocalDateTime lastFailedAttempt;

    private Boolean accountLocked;

    /**
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (usuarioRoles == null) {
            System.out.println("usuarioRoles es null");
        } else {
            System.out.println("Tamaño de usuarioRoles: " + usuarioRoles.size());
        }

        return usuarioRoles.stream()
                .map(usuarioRol -> new SimpleGrantedAuthority("REGISTRO_ACADEMICO"))
                .collect(Collectors.toList());
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
