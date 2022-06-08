package com.ersted.repository.impl;

import com.ersted.model.Specialty;
import com.ersted.repository.SpecialtyRepository;
import com.ersted.util.JDBCUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SpecialtyRepositoryImpl implements SpecialtyRepository {
    private final String CREATE_SQL = "INSERT INTO specialty(name) VALUES (?);";

    private final String UPDATE_SQL = "UPDATE specialty SET name = ? WHERE id = ?";

    private final String DELETE_BY_ID_SQL = "DELETE FROM specialty WHERE id=?";

    private final String GET_BY_NAME_SQL = "SELECT * FROM specialty WHERE name = ?";
    private final String GET_BY_ID_SQL = "SELECT * FROM specialty WHERE id = ?";
    private final String GET_ALL = "SELECT * FROM specialty;";

    @Override
    public Specialty create(Specialty specialty) {
        Specialty newSpecialty = getSpecialty(specialty.getSpecialty());

        if (newSpecialty != null) {
            return newSpecialty;
        }

        try (PreparedStatement statement = JDBCUtil.getPreparedStatement(CREATE_SQL)) {

            statement.setString(1, specialty.getSpecialty());
            statement.execute();
            newSpecialty = getSpecialty(specialty.getSpecialty());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newSpecialty;
    }

    @Override
    public Specialty getById(Long id) {
        return getSpecialty(id);
    }

    @Override
    public Specialty update(Specialty specialty) {
        Specialty oldSpecialty = getSpecialty(specialty.getSpecialty());

        if (oldSpecialty != null) {
            return null;
        }

        oldSpecialty = getById(specialty.getId());

        try (PreparedStatement statement = JDBCUtil.getPreparedStatement(UPDATE_SQL)) {

            statement.setString(1, specialty.getSpecialty());
            statement.setLong(2, oldSpecialty.getId());
            statement.executeUpdate();

            oldSpecialty = getSpecialty(specialty.getSpecialty());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return oldSpecialty;
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
    public List<Specialty> getAll() {
        ArrayList<Specialty> specialty = new ArrayList<>();

        try (Statement statement = JDBCUtil.getStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL);

            while (resultSet.next()) {
                Specialty skill = new Specialty(
                        resultSet.getLong("id"),
                        resultSet.getString("name"));
                specialty.add(skill);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return specialty;
    }

    @Override
    public Specialty getByName(String name) {
        return getSpecialty(name);
    }

    private Specialty getSpecialty(Long id) {
        Specialty specialty = null;

        try (PreparedStatement statement = JDBCUtil.getPreparedStatement(GET_BY_ID_SQL)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            specialty = buildSpecialty(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialty;
    }

    private Specialty getSpecialty(String name) {
        Specialty specialty = null;

        try (PreparedStatement statement = JDBCUtil.getPreparedStatement(GET_BY_NAME_SQL)) {

            statement.setString(1, name.toLowerCase());
            ResultSet resultSet = statement.executeQuery();
            specialty = buildSpecialty(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialty;
    }

    private Specialty buildSpecialty(ResultSet resultSet) throws SQLException {
        Specialty specialty = null;
        if (resultSet.next()) {
            specialty = new Specialty(
                    resultSet.getLong("id"),
                    resultSet.getString("name"));
        }
        return specialty;
    }
}
