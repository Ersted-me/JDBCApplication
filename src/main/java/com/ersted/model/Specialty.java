package com.ersted.model;

public class Specialty extends BaseEntity {
    protected String specialty;

    public Specialty(Long id, String specialty) {
        super(id);
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "Specialty{" +
                "id=" + id +
                ", specialty='" + specialty + '\'' +
                '}';
    }
}
