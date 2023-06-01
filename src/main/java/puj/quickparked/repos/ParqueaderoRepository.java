package puj.quickparked.repos;

import org.springframework.data.jpa.repository.Query;
import puj.quickparked.domain.Parqueadero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ParqueaderoRepository extends JpaRepository<Parqueadero, Integer> {
    @Query(value = "SELECT * FROM parqueadero as p WHERE p.UsuarioPropietarioId = :id", nativeQuery = true)
    List<Parqueadero> findAllByUsuarioPropietarioId(long id);
}
