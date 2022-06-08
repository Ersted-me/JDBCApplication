package com.ersted.model;

public class Specialty extends BaseEntity {
    protected String specialty;

    public Specialty(String specialty) {
        this.specialty = specialty;
    }

    public Specialty(Long id, String specialty) {
        this.id = id;
        this.specialty = specialty;
    }

    public Specialty() {
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
