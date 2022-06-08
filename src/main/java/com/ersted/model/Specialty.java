package com.ersted.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specialty specialty1 = (Specialty) o;
        return this.specialty.equals(specialty1.getSpecialty()) && this.id.equals(specialty1.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialty);
    }
}
