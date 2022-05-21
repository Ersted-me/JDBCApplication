package com.ersted.repository.impl;

import com.ersted.model.Skill;
import com.ersted.repository.SkillRepository;
import com.ersted.util.JDBCUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SkillRepositoryImpl implements SkillRepository {
    private final String CREATE_SQL = "INSERT INTO skill(name) VALUES (?);";

    private final String UPDATE_SQL = "UPDATE skill SET name = ? WHERE id = ?";

    private final String DELETE_BY_ID_SQL = "DELETE FROM skill WHERE id=?";

    private final String GET_BY_NAME_SQL = "SELECT * FROM skill WHERE name = ?";
    private final String GET_BY_ID_SQL = "SELECT * FROM skill WHERE id = ?";
    private final String GET_ALL = "SELECT * FROM skill;";

    @Override
    public Skill create(Skill skill) {
        Skill newSkill = getSkill(skill.getSkill());

        if (newSkill != null) {
            return newSkill;
        }

        try (PreparedStatement statement = JDBCUtil.getPreparedStatement(CREATE_SQL)) {

            statement.setString(1, skill.getSkill());
            statement.execute();
            newSkill = getSkill(skill.getSkill());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newSkill;
    }

    @Override
    public Skill getById(Long id) {
        return getSkill(id);
    }

    @Override
    public Skill update(Skill skill) {
        Skill oldSkill = getSkill(skill.getSkill());

        if (oldSkill != null) {
            return null;
        }

        oldSkill = getById(skill.getId());

        try (PreparedStatement statement = JDBCUtil.getPreparedStatement(UPDATE_SQL)) {

            statement.setString(1, skill.getSkill());
            statement.setLong(2, oldSkill.getId());
            statement.executeUpdate();

            oldSkill = getSkill(skill.getSkill());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return oldSkill;
    }

    @Override
    public void deleteById(Long id) {
        try (PreparedStatement statement = JDBCUtil.getPreparedStatement(DELETE_BY_ID_SQL)) {

            statement.setLong(1, id);
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Skill> getAll() {
        ArrayList<Skill> skills = new ArrayList<>();

        try (Statement statement = JDBCUtil.getStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL);

            while (resultSet.next()) {
                Skill skill = new Skill(
                        resultSet.getLong("id"),
                        resultSet.getString("name"));
                skills.add(skill);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return skills;
    }

    @Override
    public Skill getByName(String name) {
        return getSkill(name);
    }

    private Skill getSkill(Long id) {
        Skill skill = null;

        try (PreparedStatement statement = JDBCUtil.getPreparedStatement(GET_BY_ID_SQL)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            skill = buildSkill(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    private Skill getSkill(String name) {
        Skill skill = null;

        try (PreparedStatement statement = JDBCUtil.getPreparedStatement(GET_BY_NAME_SQL)) {

            statement.setString(1, name.toLowerCase());
            ResultSet resultSet = statement.executeQuery();
            skill = buildSkill(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    private Skill buildSkill(ResultSet resultSet) throws SQLException {
        Skill skill = null;
        if (resultSet.next()) {
            skill = new Skill(
                    resultSet.getLong("id"),
                    resultSet.getString("name"));
        }

        return skill;
    }
}
