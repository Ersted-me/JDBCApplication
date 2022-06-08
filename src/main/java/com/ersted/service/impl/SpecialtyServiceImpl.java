package com.ersted.service.impl;

import com.ersted.exception.AlreadyExistException;
import com.ersted.exception.NotFoundException;
import com.ersted.model.Specialty;
import com.ersted.repository.SpecialtyRepository;
import com.ersted.service.SpecialtyService;

import java.util.List;

public class SpecialtyServiceImpl implements SpecialtyService {
    private final SpecialtyRepository repository;

    public SpecialtyServiceImpl(SpecialtyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Specialty create(Specialty specialty){
        return repository.create(specialty);
    }

    @Override
    public Specialty getById(Long id) throws NotFoundException {
        Specialty specialty = repository.getById(id);

        if (specialty == null) {
            throw new NotFoundException("Specialty not found by ID: " + id);
        }

        return repository.getById(id);
    }

    @Override
    public Specialty getByName(String name) throws NotFoundException {
        Specialty specialty = repository.getByName(name);

        if(specialty == null)
            throw new NotFoundException(
                    String.format("Specialty with name: %s not found.", name));

        return specialty;
    }

    @Override
    public Specialty update(Specialty specialty) throws AlreadyExistException {
        Specialty updatedSpecialty = repository.update(specialty);

        if(updatedSpecialty == null)
            throw new AlreadyExistException(
                    String.format("Skill with name: %s already exists", specialty.getSpecialty()));

        return updatedSpecialty;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Specialty> getAll() {
        return repository.getAll();
    }
}
