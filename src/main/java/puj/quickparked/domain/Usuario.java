package puj.quickparked.domain;

import jakarta.persistence.*;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @Column(
        nullable = false,
        updatable = false
    )
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private Integer id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefono;

    @OneToMany(mappedBy = "usuarioPropietario")
    private Set<Parqueadero> usuarioPropietarioParqueaderos;

    @OneToMany(mappedBy = "usuarioTrabajador")
    private Set<RegistroParqueadero> usuarioTrabajadorRegistroParqueaderos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sedeParqueaderoId")
    private SedeParqueadero sedeParqueadero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoUsuarioId", nullable = false)
    private TipoUsuario tipoUsuario;

    @OneToOne(mappedBy = "usuario")
    private Vehiculo vehiculo;

}
