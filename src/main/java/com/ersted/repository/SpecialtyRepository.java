package com.ersted.repository;

import com.ersted.model.Specialty;

public interface SpecialtyRepository extends BaseRepository<Specialty, Long>{
    Specialty getByName(String name);
}
