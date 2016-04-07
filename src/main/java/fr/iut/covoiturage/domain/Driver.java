package fr.iut.covoiturage.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Driver implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "driver_first_name")
    private String driverFirstName;
    
    @Column(name = "driver_last_name")
    private String driverLastName;
    
    @Column(name = "driver_phone")
    private String driverPhone;
    
    @Column(name = "driver_address")
    private String driverAddress;
    
    @Column(name = "driver_mail")
    private String driverMail;
    
    @Column(name = "driver_age")
    private Integer driverAge;
    
    @Column(name = "driver_experience")
    private Integer driverExperience;
    
    @Column(name = "driver_exp")
    private Integer driverExp;
    
    @OneToMany(mappedBy = "driver")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Path> driverPathLists = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriverFirstName() {
        return driverFirstName;
    }
    
    public void setDriverFirstName(String driverFirstName) {
        this.driverFirstName = driverFirstName;
    }

    public String getDriverLastName() {
        return driverLastName;
    }
    
    public void setDriverLastName(String driverLastName) {
        this.driverLastName = driverLastName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }
    
    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverAddress() {
        return driverAddress;
    }
    
    public void setDriverAddress(String driverAddress) {
        this.driverAddress = driverAddress;
    }

    public String getDriverMail() {
        return driverMail;
    }
    
    public void setDriverMail(String driverMail) {
        this.driverMail = driverMail;
    }

    public Integer getDriverAge() {
        return driverAge;
    }
    
    public void setDriverAge(Integer driverAge) {
        this.driverAge = driverAge;
    }

    public Integer getDriverExperience() {
        return driverExperience;
    }
    
    public void setDriverExperience(Integer driverExperience) {
        this.driverExperience = driverExperience;
    }

    public Integer getDriverExp() {
        return driverExp;
    }
    
    public void setDriverExp(Integer driverExp) {
        this.driverExp = driverExp;
    }

    public Set<Path> getDriverPathLists() {
        return driverPathLists;
    }

    public void setDriverPathLists(Set<Path> paths) {
        this.driverPathLists = paths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Driver driver = (Driver) o;
        if(driver.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, driver.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Driver{" +
            "id=" + id +
            ", driverFirstName='" + driverFirstName + "'" +
            ", driverLastName='" + driverLastName + "'" +
            ", driverPhone='" + driverPhone + "'" +
            ", driverAddress='" + driverAddress + "'" +
            ", driverMail='" + driverMail + "'" +
            ", driverAge='" + driverAge + "'" +
            ", driverExperience='" + driverExperience + "'" +
            ", driverExp='" + driverExp + "'" +
            '}';
    }
}
