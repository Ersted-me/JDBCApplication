package com.ersted.controller.impl;

import com.ersted.controller.SkillController;
import com.ersted.exception.AlreadyExistException;
import com.ersted.exception.NotFoundException;
import com.ersted.model.Skill;
import com.ersted.service.SkillService;

import java.util.List;

public class SkillControllerImpl implements SkillController {
    private final SkillService service;

    public SkillControllerImpl(SkillService service) {
        this.service = service;
    }

    @Override
    public Skill create(Skill obj) {
        return service.create(obj);
    }

    @Override
    public Skill getById(Long id) throws NotFoundException {
        return service.getById(id);
    }

    @Override
    public Skill update(Skill obj) throws AlreadyExistException {
        return service.update(obj);
    }

    @Override
    public void deleteById(Long id) {
        service.deleteById(id);
    }

    @Override
    public List<Skill> getAll() {
        return service.getAll();
    }
}
