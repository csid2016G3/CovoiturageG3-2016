package fr.iut.covoiturage.repository;

import fr.iut.covoiturage.domain.CarDriver;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CarDriver entity.
 */
public interface CarDriverRepository extends JpaRepository<CarDriver,Long> {

}
