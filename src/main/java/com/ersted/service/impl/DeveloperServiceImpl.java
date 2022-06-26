package com.ersted.service.impl;

import com.ersted.exception.AlreadyExistException;
import com.ersted.exception.NotFoundException;
import com.ersted.model.Developer;
import com.ersted.model.Status;
import com.ersted.repository.DeveloperRepository;
import com.ersted.service.DeveloperService;

import java.util.List;
import java.util.stream.Collectors;

public class DeveloperServiceImpl implements DeveloperService {
    private final DeveloperRepository repository;

    public DeveloperServiceImpl(DeveloperRepository repository) {
        this.repository = repository;
    }

    @Override
    public Developer create(Developer developer) {
        return repository.create(developer);
    }

    @Override
    public Developer getById(Long id) throws NotFoundException {
        Developer dev = repository.getById(id);
        if(dev == null || dev.getStatus().equals(Status.DELETED))
            throw new NotFoundException("Developer with id:" + id + " not found.");
        return dev;
    }

    @Override
    public Developer update(Developer developer) throws AlreadyExistException {
        Developer dev = repository.update(developer);
        if(dev == null)
            throw new AlreadyExistException("Developer with lastname:"
                    + developer.getLastName() + " already exist.");
        return dev;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Developer> getAll() {
        return repository
                .getAll()
                .stream()
                .filter(e -> e.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());
    }
}
