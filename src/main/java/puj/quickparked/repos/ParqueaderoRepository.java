package puj.quickparked.repos;

import org.springframework.data.jpa.repository.Query;
import puj.quickparked.domain.Parqueadero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ParqueaderoRepository extends JpaRepository<Parqueadero, Integer> {
    @Query(value = "SELECT p FROM Parqueadero as p WHERE p.usuarioPropietario =  :id")
    List<Parqueadero> findAllByUsuarioPropietarioId(long id);
}
