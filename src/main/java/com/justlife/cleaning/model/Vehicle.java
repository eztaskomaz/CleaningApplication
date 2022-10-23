package com.justlife.cleaning.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "VEHICLE")
public class Vehicle extends AbstractAuditedEntity {

    @Column(name = "DRIVER_NAME")
    private String driverName;

    @Column(name = "LICENCE_PLATE")
    private String licencePlate;

    @OneToMany(mappedBy = "vehicle")
    private Set<Staff> staffSet;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public Set<Staff> getStaffSet() {
        return staffSet;
    }

    public void setStaffSet(Set<Staff> staffSet) {
        this.staffSet = staffSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(vehicle, vehicle.getLicencePlate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(licencePlate);
    }

}
