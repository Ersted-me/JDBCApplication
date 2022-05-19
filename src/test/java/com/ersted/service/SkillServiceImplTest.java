package com.ersted.service;

import com.ersted.exception.SkillAlreadyExistException;
import com.ersted.exception.SkillNotFoundException;
import com.ersted.model.Skill;
import com.ersted.repository.SkillRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SkillServiceImplTest {
    @Mock
    private SkillRepository repository;
    private SkillServiceImpl skillServiceImpl;

    @Before
    public void setUp(){
        skillServiceImpl = new SkillServiceImpl(repository);
    }

    @Test
    public void whenCreateUserWithoutIdThenReturnWithId(){
        Skill skill = new Skill("test");
        Skill expected = new Skill(1L, "test");

        when(repository.create(skill))
                .thenReturn(expected);

        Skill actual = skillServiceImpl.create(skill);

        assertEquals(expected, actual);
    }

    @Test
    public void whenGetSkillByExistNameThenReturnSkill(){
        Skill expected = new Skill(1L, "test");

        when(repository.getByName("test"))
                .thenReturn(expected);

        Skill actual = repository.getByName("test");

        assertEquals(expected, actual);
    }

    @Test
    public void whenSkillWithIdExistThenReturnSkill(){
        Skill expected = new Skill(1L, "test");

        when(repository.getById(1L)).thenReturn(expected);
        Skill actual = null;
        try {
            actual = skillServiceImpl.getById(1L);
        } catch (SkillNotFoundException e) {
            fail("expected: " + expected);
        }

        assertEquals(expected, actual);
    }

    @Test(expected = SkillNotFoundException.class)
    public void whenSkillNotExistById1ThenTrowException() throws SkillNotFoundException {
        when(repository.getById(any())).thenReturn(null);
        skillServiceImpl.getById(1L);
    }

    @Test
    public void whenUpdateSkillThenReturnSkillWithNewNameAndSameId() {
        Skill skill = new Skill(1L,"updated");
        Skill expected = new Skill(1L, "updated");
        when(repository.update(skill)).thenReturn(skill);

        Skill actual = null;
        try {
            actual = skillServiceImpl.update(skill);
        } catch (SkillAlreadyExistException e) {
            fail("expected: " + expected);
        }
        assertEquals(expected, actual);
    }

    @Test(expected = SkillAlreadyExistException.class)
    public void whenSkillNameAlreadyExistThenThrowException() throws SkillAlreadyExistException {
        when(repository.update(any())).thenReturn(null);
        Skill skill = new Skill(1L, "test");
        skillServiceImpl.update(skill);
    }

    @Test
    public void whenListEmptyThenReturnEmptyList(){
        when(repository.getAll()).thenReturn(Collections.emptyList());
        List<Skill> expected = Collections.emptyList();
        List<Skill> actual = skillServiceImpl.getAll();

        assertEquals(expected, actual);
    }

    @Test
    public void whenSkillAddedThenReturnListSize1(){
        Skill skill = new Skill(1L, "test");
        when(repository.getAll()).thenReturn(Collections.singletonList(skill));

        int expected = 1;
        int actual = skillServiceImpl.getAll().size();

        assertEquals(expected, actual);
    }
}