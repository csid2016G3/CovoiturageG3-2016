package fr.iut.covoiturage.repository;

import fr.iut.covoiturage.domain.Driver;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Driver entity.
 */
public interface DriverRepository extends JpaRepository<Driver,Long> {

}
