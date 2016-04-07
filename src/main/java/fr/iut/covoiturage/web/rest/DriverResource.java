package fr.iut.covoiturage.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.iut.covoiturage.domain.Driver;
import fr.iut.covoiturage.repository.DriverRepository;
import fr.iut.covoiturage.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Driver.
 */
@RestController
@RequestMapping("/api")
public class DriverResource {

    private final Logger log = LoggerFactory.getLogger(DriverResource.class);
        
    @Inject
    private DriverRepository driverRepository;
    
    /**
     * POST  /drivers -> Create a new driver.
     */
    @RequestMapping(value = "/drivers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) throws URISyntaxException {
        log.debug("REST request to save Driver : {}", driver);
        if (driver.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("driver", "idexists", "A new driver cannot already have an ID")).body(null);
        }
        Driver result = driverRepository.save(driver);
        return ResponseEntity.created(new URI("/api/drivers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("driver", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drivers -> Updates an existing driver.
     */
    @RequestMapping(value = "/drivers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Driver> updateDriver(@RequestBody Driver driver) throws URISyntaxException {
        log.debug("REST request to update Driver : {}", driver);
        if (driver.getId() == null) {
            return createDriver(driver);
        }
        Driver result = driverRepository.save(driver);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("driver", driver.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drivers -> get all the drivers.
     */
    @RequestMapping(value = "/drivers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Driver> getAllDrivers() {
        log.debug("REST request to get all Drivers");
        return driverRepository.findAll();
            }

    /**
     * GET  /drivers/:id -> get the "id" driver.
     */
    @RequestMapping(value = "/drivers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Driver> getDriver(@PathVariable Long id) {
        log.debug("REST request to get Driver : {}", id);
        Driver driver = driverRepository.findOne(id);
        return Optional.ofNullable(driver)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /drivers/:id -> delete the "id" driver.
     */
    @RequestMapping(value = "/drivers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        log.debug("REST request to delete Driver : {}", id);
        driverRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("driver", id.toString())).build();
    }
}
