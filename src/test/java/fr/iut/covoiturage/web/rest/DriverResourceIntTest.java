package fr.iut.covoiturage.web.rest;

import fr.iut.covoiturage.Application;
import fr.iut.covoiturage.domain.Driver;
import fr.iut.covoiturage.repository.DriverRepository;

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
 * Test class for the DriverResource REST controller.
 *
 * @see DriverResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DriverResourceIntTest {

    private static final String DEFAULT_DRIVER_FIRST_NAME = "AAAAA";
    private static final String UPDATED_DRIVER_FIRST_NAME = "BBBBB";
    private static final String DEFAULT_DRIVER_LAST_NAME = "AAAAA";
    private static final String UPDATED_DRIVER_LAST_NAME = "BBBBB";
    private static final String DEFAULT_DRIVER_PHONE = "AAAAA";
    private static final String UPDATED_DRIVER_PHONE = "BBBBB";
    private static final String DEFAULT_DRIVER_ADDRESS = "AAAAA";
    private static final String UPDATED_DRIVER_ADDRESS = "BBBBB";
    private static final String DEFAULT_DRIVER_MAIL = "AAAAA";
    private static final String UPDATED_DRIVER_MAIL = "BBBBB";

    private static final Integer DEFAULT_DRIVER_AGE = 1;
    private static final Integer UPDATED_DRIVER_AGE = 2;

    private static final Integer DEFAULT_DRIVER_EXPERIENCE = 1;
    private static final Integer UPDATED_DRIVER_EXPERIENCE = 2;

    private static final Integer DEFAULT_DRIVER_EXP = 1;
    private static final Integer UPDATED_DRIVER_EXP = 2;

    @Inject
    private DriverRepository driverRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDriverMockMvc;

    private Driver driver;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DriverResource driverResource = new DriverResource();
        ReflectionTestUtils.setField(driverResource, "driverRepository", driverRepository);
        this.restDriverMockMvc = MockMvcBuilders.standaloneSetup(driverResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        driver = new Driver();
        driver.setDriverFirstName(DEFAULT_DRIVER_FIRST_NAME);
        driver.setDriverLastName(DEFAULT_DRIVER_LAST_NAME);
        driver.setDriverPhone(DEFAULT_DRIVER_PHONE);
        driver.setDriverAddress(DEFAULT_DRIVER_ADDRESS);
        driver.setDriverMail(DEFAULT_DRIVER_MAIL);
        driver.setDriverAge(DEFAULT_DRIVER_AGE);
        driver.setDriverExperience(DEFAULT_DRIVER_EXPERIENCE);
        driver.setDriverExp(DEFAULT_DRIVER_EXP);
    }

    @Test
    @Transactional
    public void createDriver() throws Exception {
        int databaseSizeBeforeCreate = driverRepository.findAll().size();

        // Create the Driver

        restDriverMockMvc.perform(post("/api/drivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(driver)))
                .andExpect(status().isCreated());

        // Validate the Driver in the database
        List<Driver> drivers = driverRepository.findAll();
        assertThat(drivers).hasSize(databaseSizeBeforeCreate + 1);
        Driver testDriver = drivers.get(drivers.size() - 1);
        assertThat(testDriver.getDriverFirstName()).isEqualTo(DEFAULT_DRIVER_FIRST_NAME);
        assertThat(testDriver.getDriverLastName()).isEqualTo(DEFAULT_DRIVER_LAST_NAME);
        assertThat(testDriver.getDriverPhone()).isEqualTo(DEFAULT_DRIVER_PHONE);
        assertThat(testDriver.getDriverAddress()).isEqualTo(DEFAULT_DRIVER_ADDRESS);
        assertThat(testDriver.getDriverMail()).isEqualTo(DEFAULT_DRIVER_MAIL);
        assertThat(testDriver.getDriverAge()).isEqualTo(DEFAULT_DRIVER_AGE);
        assertThat(testDriver.getDriverExperience()).isEqualTo(DEFAULT_DRIVER_EXPERIENCE);
        assertThat(testDriver.getDriverExp()).isEqualTo(DEFAULT_DRIVER_EXP);
    }

    @Test
    @Transactional
    public void getAllDrivers() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get all the drivers
        restDriverMockMvc.perform(get("/api/drivers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(driver.getId().intValue())))
                .andExpect(jsonPath("$.[*].driverFirstName").value(hasItem(DEFAULT_DRIVER_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].driverLastName").value(hasItem(DEFAULT_DRIVER_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].driverPhone").value(hasItem(DEFAULT_DRIVER_PHONE.toString())))
                .andExpect(jsonPath("$.[*].driverAddress").value(hasItem(DEFAULT_DRIVER_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].driverMail").value(hasItem(DEFAULT_DRIVER_MAIL.toString())))
                .andExpect(jsonPath("$.[*].driverAge").value(hasItem(DEFAULT_DRIVER_AGE)))
                .andExpect(jsonPath("$.[*].driverExperience").value(hasItem(DEFAULT_DRIVER_EXPERIENCE)))
                .andExpect(jsonPath("$.[*].driverExp").value(hasItem(DEFAULT_DRIVER_EXP)));
    }

    @Test
    @Transactional
    public void getDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

        // Get the driver
        restDriverMockMvc.perform(get("/api/drivers/{id}", driver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(driver.getId().intValue()))
            .andExpect(jsonPath("$.driverFirstName").value(DEFAULT_DRIVER_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.driverLastName").value(DEFAULT_DRIVER_LAST_NAME.toString()))
            .andExpect(jsonPath("$.driverPhone").value(DEFAULT_DRIVER_PHONE.toString()))
            .andExpect(jsonPath("$.driverAddress").value(DEFAULT_DRIVER_ADDRESS.toString()))
            .andExpect(jsonPath("$.driverMail").value(DEFAULT_DRIVER_MAIL.toString()))
            .andExpect(jsonPath("$.driverAge").value(DEFAULT_DRIVER_AGE))
            .andExpect(jsonPath("$.driverExperience").value(DEFAULT_DRIVER_EXPERIENCE))
            .andExpect(jsonPath("$.driverExp").value(DEFAULT_DRIVER_EXP));
    }

    @Test
    @Transactional
    public void getNonExistingDriver() throws Exception {
        // Get the driver
        restDriverMockMvc.perform(get("/api/drivers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

		int databaseSizeBeforeUpdate = driverRepository.findAll().size();

        // Update the driver
        driver.setDriverFirstName(UPDATED_DRIVER_FIRST_NAME);
        driver.setDriverLastName(UPDATED_DRIVER_LAST_NAME);
        driver.setDriverPhone(UPDATED_DRIVER_PHONE);
        driver.setDriverAddress(UPDATED_DRIVER_ADDRESS);
        driver.setDriverMail(UPDATED_DRIVER_MAIL);
        driver.setDriverAge(UPDATED_DRIVER_AGE);
        driver.setDriverExperience(UPDATED_DRIVER_EXPERIENCE);
        driver.setDriverExp(UPDATED_DRIVER_EXP);

        restDriverMockMvc.perform(put("/api/drivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(driver)))
                .andExpect(status().isOk());

        // Validate the Driver in the database
        List<Driver> drivers = driverRepository.findAll();
        assertThat(drivers).hasSize(databaseSizeBeforeUpdate);
        Driver testDriver = drivers.get(drivers.size() - 1);
        assertThat(testDriver.getDriverFirstName()).isEqualTo(UPDATED_DRIVER_FIRST_NAME);
        assertThat(testDriver.getDriverLastName()).isEqualTo(UPDATED_DRIVER_LAST_NAME);
        assertThat(testDriver.getDriverPhone()).isEqualTo(UPDATED_DRIVER_PHONE);
        assertThat(testDriver.getDriverAddress()).isEqualTo(UPDATED_DRIVER_ADDRESS);
        assertThat(testDriver.getDriverMail()).isEqualTo(UPDATED_DRIVER_MAIL);
        assertThat(testDriver.getDriverAge()).isEqualTo(UPDATED_DRIVER_AGE);
        assertThat(testDriver.getDriverExperience()).isEqualTo(UPDATED_DRIVER_EXPERIENCE);
        assertThat(testDriver.getDriverExp()).isEqualTo(UPDATED_DRIVER_EXP);
    }

    @Test
    @Transactional
    public void deleteDriver() throws Exception {
        // Initialize the database
        driverRepository.saveAndFlush(driver);

		int databaseSizeBeforeDelete = driverRepository.findAll().size();

        // Get the driver
        restDriverMockMvc.perform(delete("/api/drivers/{id}", driver.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Driver> drivers = driverRepository.findAll();
        assertThat(drivers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
