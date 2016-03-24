package fr.iut.covoiturage.web.rest;

import fr.iut.covoiturage.Application;
import fr.iut.covoiturage.domain.CarDriver;
import fr.iut.covoiturage.repository.CarDriverRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CarDriverResource REST controller.
 *
 * @see CarDriverResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CarDriverResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";

    private static final Integer DEFAULT_EXP = 1;
    private static final Integer UPDATED_EXP = 2;

    @Inject
    private CarDriverRepository carDriverRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCarDriverMockMvc;

    private CarDriver carDriver;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CarDriverResource carDriverResource = new CarDriverResource();
        ReflectionTestUtils.setField(carDriverResource, "carDriverRepository", carDriverRepository);
        this.restCarDriverMockMvc = MockMvcBuilders.standaloneSetup(carDriverResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        carDriver = new CarDriver();
        carDriver.setFirstName(DEFAULT_FIRST_NAME);
        carDriver.setLastName(DEFAULT_LAST_NAME);
        carDriver.setExp(DEFAULT_EXP);
    }

    @Test
    @Transactional
    public void createCarDriver() throws Exception {
        int databaseSizeBeforeCreate = carDriverRepository.findAll().size();

        // Create the CarDriver

        restCarDriverMockMvc.perform(post("/api/carDrivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carDriver)))
                .andExpect(status().isCreated());

        // Validate the CarDriver in the database
        List<CarDriver> carDrivers = carDriverRepository.findAll();
        assertThat(carDrivers).hasSize(databaseSizeBeforeCreate + 1);
        CarDriver testCarDriver = carDrivers.get(carDrivers.size() - 1);
        assertThat(testCarDriver.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testCarDriver.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCarDriver.getExp()).isEqualTo(DEFAULT_EXP);
    }

    @Test
    @Transactional
    public void getAllCarDrivers() throws Exception {
        // Initialize the database
        carDriverRepository.saveAndFlush(carDriver);

        // Get all the carDrivers
        restCarDriverMockMvc.perform(get("/api/carDrivers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(carDriver.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].exp").value(hasItem(DEFAULT_EXP)));
    }

    @Test
    @Transactional
    public void getCarDriver() throws Exception {
        // Initialize the database
        carDriverRepository.saveAndFlush(carDriver);

        // Get the carDriver
        restCarDriverMockMvc.perform(get("/api/carDrivers/{id}", carDriver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(carDriver.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.exp").value(DEFAULT_EXP));
    }

    @Test
    @Transactional
    public void getNonExistingCarDriver() throws Exception {
        // Get the carDriver
        restCarDriverMockMvc.perform(get("/api/carDrivers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCarDriver() throws Exception {
        // Initialize the database
        carDriverRepository.saveAndFlush(carDriver);

		int databaseSizeBeforeUpdate = carDriverRepository.findAll().size();

        // Update the carDriver
        carDriver.setFirstName(UPDATED_FIRST_NAME);
        carDriver.setLastName(UPDATED_LAST_NAME);
        carDriver.setExp(UPDATED_EXP);

        restCarDriverMockMvc.perform(put("/api/carDrivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(carDriver)))
                .andExpect(status().isOk());

        // Validate the CarDriver in the database
        List<CarDriver> carDrivers = carDriverRepository.findAll();
        assertThat(carDrivers).hasSize(databaseSizeBeforeUpdate);
        CarDriver testCarDriver = carDrivers.get(carDrivers.size() - 1);
        assertThat(testCarDriver.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testCarDriver.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCarDriver.getExp()).isEqualTo(UPDATED_EXP);
    }

    @Test
    @Transactional
    public void deleteCarDriver() throws Exception {
        // Initialize the database
        carDriverRepository.saveAndFlush(carDriver);

		int databaseSizeBeforeDelete = carDriverRepository.findAll().size();

        // Get the carDriver
        restCarDriverMockMvc.perform(delete("/api/carDrivers/{id}", carDriver.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CarDriver> carDrivers = carDriverRepository.findAll();
        assertThat(carDrivers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
