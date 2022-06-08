package com.ersted.service;

import com.ersted.exception.NotFoundException;
import com.ersted.model.Specialty;

public interface SpecialtyService extends BaseService<Specialty, Long>{
    Specialty getByName(String name) throws NotFoundException;
}
