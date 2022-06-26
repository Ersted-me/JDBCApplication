package com.ersted.repository.impl;

import com.ersted.model.Developer;
import com.ersted.model.Skill;
import com.ersted.model.Specialty;
import com.ersted.model.Status;
import com.ersted.repository.DeveloperRepository;
import com.ersted.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperRepositoryImpl implements DeveloperRepository {

    private final String CREATE_DEVELOPER_SQL =
            "INSERT INTO developer(firstname, lastname, specialtyId, status) " +
                    "VALUES (?,?,?,?);";

    private final String ADD_DEVELOPER_SKILLS =
            "INSERT INTO developer_skill_link(developerId,skillId)" +
                    "VAlUES (?,?)";

    private final String GET_DEVELOPER_ID_BY_LASTNAME = "select id from developer where lastname = ?";

    private final String GET_DEVELOPER_BY_LASTNAME =
            "select d.id, d.firstname, d.lastname, s.id as specialtyid, s.name as specialtyname, status " +
                    "from developer d left join specialty s on d.specialtyid = s.id " +
                    "where d.lastname = ?";

    private final String GET_DEVELOPER_BY_ID =
            "select d.id, d.firstname, d.lastname, s.id as specialtyid, s.name as specialtyname, status " +
                    "from developer d " +
                    "left join specialty s on d.specialtyid = s.id " +
                    "where d.id = ?;";

    private final String GET_SKILL_OF_DEVELOPER_BY_DEVELOPER_ID =
            "select s.id, s.name from developer_skill_link dsl " +
                    "left join skill s on dsl.skillid = s.id " +
                    "where dsl.developerid = ?;";

    private final String GET_ALL_DEVELOPERS =
            "select d.id, d.firstname, d.lastname, s.id as specialtyid, s.name as specialtyname, status " +
                    "from developer d left join specialty s on d.specialtyid = s.id";

    private final String UPDATE_DEVELOPER =
            "update developer set firstname = ?, lastname = ?, specialtyid = ?, status = ? where id = ?";

    private final String DELETE_SKILLS_OF_DEVELOPER =
            "delete from developer_skill_link where developerid = ?";

    private final String DELETE_DEVELOPER_BY_ID =
            "update developer set status = ? where id = ?";


    @Override
    public Developer create(Developer developer) {

        Developer newDev = getDeveloperFromDB(developer.getLastName());
        if (newDev != null)
            return newDev;

        try (PreparedStatement statement = JDBCUtil.getPreparedStatement(CREATE_DEVELOPER_SQL)) {

            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());
            statement.setLong(3, developer.getSpecialty().getId());
            statement.setString(4, developer.getStatus().toString());
            statement.execute();

            developer.setId(getIdByLastName(developer.getLastName()));
            addSkillsToDeveloper(developer);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return getDeveloperFromDB(developer.getLastName());
    }

    @Override
    public Developer getById(Long id) {
        return getDeveloperFromDB(id);
    }

    @Override
    public Developer update(Developer developer) {
        Developer currently = getDeveloperFromDB(developer.getLastName());
        if (currently != null || !currently.getId().equals(developer.getId()))
            return null;

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement deleteSkills =
                     connection.prepareStatement(DELETE_SKILLS_OF_DEVELOPER);
             PreparedStatement updateDeveloper =
                     connection.prepareStatement(UPDATE_DEVELOPER)) {

            connection.setAutoCommit(false);

            updateDeveloper.setString(1, developer.getFirstName());
            updateDeveloper.setString(2, developer.getLastName());
            updateDeveloper.setLong(3, developer.getSpecialty().getId());
            updateDeveloper.setString(4, developer.getStatus().toString());
            updateDeveloper.setLong(5, developer.getId());
            updateDeveloper.execute();

            deleteSkills.setLong(1, developer.getId());
            deleteSkills.execute();

            connection.commit();

            addSkillsToDeveloper(developer);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return getDeveloperFromDB(developer.getId());
    }

    @Override
    public void deleteById(Long id) {

        try (PreparedStatement deleteDeveloper =
                     JDBCUtil.getPreparedStatement(DELETE_DEVELOPER_BY_ID)) {

            deleteDeveloper.setString(1, Status.DELETED.toString());
            deleteDeveloper.setLong(2, id);
            deleteDeveloper.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public List<Developer> getAll() {
        ArrayList<Developer> developers = new ArrayList<>();
        try (PreparedStatement statement = JDBCUtil.getPreparedStatement(GET_ALL_DEVELOPERS)) {

            developers.addAll(buildDevelopersList(statement.executeQuery()));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return developers;
    }

    private void addSkillsToDeveloper(Developer developer) {
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_DEVELOPER_SKILLS)) {
            connection.setAutoCommit(false);

            for (Skill skill : developer.getSkills()) {

                if (skill.getId() == null)
                    continue;

                statement.setLong(1, developer.getId());
                statement.setLong(2, skill.getId());
                statement.execute();
            }

            connection.commit();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Long getIdByLastName(String lastName) {

        Long id = null;
        try (PreparedStatement statement = JDBCUtil.getPreparedStatement(GET_DEVELOPER_ID_BY_LASTNAME)) {
            statement.setString(1, lastName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                id = resultSet.getLong("id");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private Developer getDeveloperFromDB(String lastname) {
        Developer dev = null;

        try (PreparedStatement statement = JDBCUtil.getPreparedStatement(GET_DEVELOPER_BY_LASTNAME)) {

            statement.setString(1, lastname);
            ResultSet resultSet = statement.executeQuery();
            dev = buildDeveloper(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dev;
    }

    private Developer getDeveloperFromDB(Long id) {
        Developer dev = null;

        try (PreparedStatement statement = JDBCUtil.getPreparedStatement(GET_DEVELOPER_BY_ID)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            dev = buildDeveloper(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dev;
    }

    private Developer buildDeveloper(ResultSet resultSet) throws SQLException {
        Developer developer = null;
        if (resultSet.next()) {
            developer = new Developer(
                    resultSet.getLong("id"),
                    resultSet.getString("firstname"),
                    resultSet.getString("lastname"),
                    getSkillOfDeveloperByDeveloperId(resultSet.getLong("id")),
                    new Specialty(
                            resultSet.getLong("specialtyid"),
                            resultSet.getString("specialtyname")),
                    Enum.valueOf(Status.class, resultSet.getString("status")));
        }

        return developer;
    }

    private List<Developer> buildDevelopersList(ResultSet resultSet) throws SQLException {
        ArrayList<Developer> developers = new ArrayList<>();
        while (resultSet.next()) {
            developers
                    .add(new Developer(
                            resultSet.getLong("id"),
                            resultSet.getString("firstname"),
                            resultSet.getString("lastname"),
                            getSkillOfDeveloperByDeveloperId(resultSet.getLong("id")),
                            new Specialty(
                                    resultSet.getLong("specialtyid"),
                                    resultSet.getString("specialtyname")),
                            Enum.valueOf(Status.class, resultSet.getString("status"))));
        }

        return developers;
    }

    private List<Skill> getSkillOfDeveloperByDeveloperId(Long id) {

        ArrayList<Skill> skills = new ArrayList<>();

        try (PreparedStatement statement = JDBCUtil.getPreparedStatement(GET_SKILL_OF_DEVELOPER_BY_DEVELOPER_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                skills.add(new Skill(
                        resultSet.getLong("id"),
                        resultSet.getString("name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

}
