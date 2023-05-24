package puj.quickparked.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EstadoRegistroParqueaderoDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String estado;

}
