package com.ersted.service.impl;



import com.ersted.exception.AlreadyExistException;
import com.ersted.exception.NotFoundException;
import com.ersted.model.Specialty;
import com.ersted.repository.SpecialtyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpecialtyServiceImplTest {
    @Mock
    private SpecialtyRepository repository;
    private SpecialtyServiceImpl specialtyServiceImpl;

    @Before
    public void setUp(){
        specialtyServiceImpl = new SpecialtyServiceImpl(repository);
    }

    @Test
    public void whenCreateSpecialtyWithoutIdThenReturnWithId(){
        Specialty specialty = new Specialty(null, "test");
        Specialty expected = new Specialty(1L, "test");

        when(repository.create(specialty))
                .thenReturn(expected);

        Specialty actual = specialtyServiceImpl.create(specialty);

        assertEquals(expected, actual);
    }

    @Test
    public void whenGetSpecialtyByExistNameThenReturnSpecialty(){
        Specialty expected = new Specialty(1L, "test");

        when(repository.getByName("test"))
                .thenReturn(expected);

        Specialty actual = repository.getByName("test");

        assertEquals(expected, actual);
    }

    @Test
    public void whenSpecialtyWithIdExistThenReturnSpecialty() throws NotFoundException {
        Specialty expected = new Specialty(1L, "test");

        when(repository.getById(1L)).thenReturn(expected);
        Specialty actual = null;
        try {
            actual = specialtyServiceImpl.getById(1L);
        } catch (NotFoundException e) {
            fail("expected: " + expected);
        }

        assertEquals(expected, actual);
    }

    @Test(expected = NotFoundException.class)
    public void whenSpecialtyNotExistById1ThenTrowException() throws NotFoundException {
        when(repository.getById(any())).thenReturn(null);
        specialtyServiceImpl.getById(1L);
    }

    @Test
    public void whenUpdateSpecialtyThenReturnSpecialtyWithNewNameAndSameId() throws AlreadyExistException {
        Specialty specialty = new Specialty(1L,"updated");
        Specialty expected = new Specialty(1L, "updated");
        when(repository.update(specialty)).thenReturn(specialty);

        Specialty actual = null;
        try {
            actual = specialtyServiceImpl.update(specialty);
        } catch (AlreadyExistException e) {
            fail("expected: " + expected);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = AlreadyExistException.class)
    public void whenSpecialtyNameAlreadyExistThenThrowException() throws AlreadyExistException {
        when(repository.update(any())).thenReturn(null);
        Specialty specialty = new Specialty(1L, "test");
        specialtyServiceImpl.update(specialty);
    }

    @Test
    public void whenListEmptyThenReturnEmptyList(){
        when(repository.getAll()).thenReturn(Collections.emptyList());
        List<Specialty> expected = Collections.emptyList();
        List<Specialty> actual = specialtyServiceImpl.getAll();

        assertEquals(expected, actual);
    }

    @Test
    public void whenSpecialtyAddedThenReturnListSize1(){
        Specialty Specialty = new Specialty(1L, "test");
        when(repository.getAll()).thenReturn(Collections.singletonList(Specialty));

        int expected = 1;
        int actual = specialtyServiceImpl.getAll().size();

        assertEquals(expected, actual);
    }

}