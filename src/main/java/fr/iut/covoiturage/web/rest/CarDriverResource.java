package fr.iut.covoiturage.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.iut.covoiturage.domain.CarDriver;
import fr.iut.covoiturage.repository.CarDriverRepository;
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
 * REST controller for managing CarDriver.
 */
@RestController
@RequestMapping("/api")
public class CarDriverResource {

    private final Logger log = LoggerFactory.getLogger(CarDriverResource.class);
        
    @Inject
    private CarDriverRepository carDriverRepository;
    
    /**
     * POST  /carDrivers -> Create a new carDriver.
     */
    @RequestMapping(value = "/carDrivers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CarDriver> createCarDriver(@RequestBody CarDriver carDriver) throws URISyntaxException {
        log.debug("REST request to save CarDriver : {}", carDriver);
        if (carDriver.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("carDriver", "idexists", "A new carDriver cannot already have an ID")).body(null);
        }
        CarDriver result = carDriverRepository.save(carDriver);
        return ResponseEntity.created(new URI("/api/carDrivers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("carDriver", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carDrivers -> Updates an existing carDriver.
     */
    @RequestMapping(value = "/carDrivers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CarDriver> updateCarDriver(@RequestBody CarDriver carDriver) throws URISyntaxException {
        log.debug("REST request to update CarDriver : {}", carDriver);
        if (carDriver.getId() == null) {
            return createCarDriver(carDriver);
        }
        CarDriver result = carDriverRepository.save(carDriver);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("carDriver", carDriver.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carDrivers -> get all the carDrivers.
     */
    @RequestMapping(value = "/carDrivers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CarDriver> getAllCarDrivers() {
        log.debug("REST request to get all CarDrivers");
        return carDriverRepository.findAll();
            }

    /**
     * GET  /carDrivers/:id -> get the "id" carDriver.
     */
    @RequestMapping(value = "/carDrivers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CarDriver> getCarDriver(@PathVariable Long id) {
        log.debug("REST request to get CarDriver : {}", id);
        CarDriver carDriver = carDriverRepository.findOne(id);
        return Optional.ofNullable(carDriver)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /carDrivers/:id -> delete the "id" carDriver.
     */
    @RequestMapping(value = "/carDrivers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCarDriver(@PathVariable Long id) {
        log.debug("REST request to delete CarDriver : {}", id);
        carDriverRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("carDriver", id.toString())).build();
    }
}
