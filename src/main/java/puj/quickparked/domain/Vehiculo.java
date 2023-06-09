package puj.quickparked.domain;

import jakarta.persistence.*;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Vehiculo {

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
    private String placa;

    @OneToMany(mappedBy = "vehiculo")
    private Set<RegistroParqueadero> vehiculoRegistroParqueaderos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipoVehiculoId", nullable = false)
    private TipoVehiculo tipoVehiculo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioId", nullable = false)
    private Usuario usuario;

}
