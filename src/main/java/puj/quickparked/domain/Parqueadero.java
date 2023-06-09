package puj.quickparked.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Parqueadero {

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
    private String nombre;

    @Column(nullable = false)
    private String nit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioPropietarioId", nullable = false)
    private Usuario usuarioPropietario;

    @OneToMany(mappedBy = "parqueadero")
    private Set<SedeParqueadero> parqueaderoSedeParqueaderos;

}
