package com.ersted.service;

import com.ersted.model.Skill;
import com.ersted.repository.SkillRepository;

import java.util.List;

public class SkillService implements BaseService<Skill, Long> {
    private final SkillRepository repository;

    public SkillService(SkillRepository repository) {
        this.repository = repository;
    }

    @Override
    public Skill create(Skill skill) {
        return repository.create(skill);
    }

    @Override
    public Skill getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public Skill update(Skill skill) {
        return repository.update(skill);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Skill> getAll() {
        return repository.getAll();
    }
}
