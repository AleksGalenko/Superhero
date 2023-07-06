/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gskela.superhero.dao;

import gskela.superhero.dto.Superpower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gskela
 */
@Repository
public class SuperpowerDaoDB implements SuperpowerDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superpower getSuperpowerById(int id) {
        try {
            final String SELECT_SUPERPOWER = "SELECT * FROM Superpower WHERE SuperpowerId = ?";
            return jdbc.queryForObject(SELECT_SUPERPOWER, new SuperpowerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        final String SELECT_ALL_SUPERPOWERS = "SELECT * FROM Superpower";
        return jdbc.query(SELECT_ALL_SUPERPOWERS, new SuperpowerMapper());
    }

    @Override
    @Transactional
    public Superpower addSuperpower(Superpower superpower) {
        final String ADD_SUPERPOWER = "INSERT INTO Superpower(superpowerName, superpowerDescription) "
                + "VALUES(?,?)";
        jdbc.update(ADD_SUPERPOWER, superpower.getSuperpowerName(), superpower.getSuperpowerDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superpower.setSuperpowerID(newId);
        return superpower;
    }

    @Override
    public void updateSuperpower(Superpower superpower) {
        final String UPDATE_SUPERPOWER = "UPDATE Superpower SET superpowerName = ?, superpowerDescription = ?"
                + "WHERE SuperpowerId = ?";
        jdbc.update(UPDATE_SUPERPOWER, superpower.getSuperpowerName(), superpower.getSuperpowerDescription(), superpower.getSuperpowerID());
    }

    @Override
    @Transactional
    public void deleteSuperpowerById(int id) {
        final String DELETE_SUPERPOWER_FROM_HEROSUPERPOWER = "DELETE FROM HeroSuperpowers WHERE superpowerId = ?";
        jdbc.update(DELETE_SUPERPOWER_FROM_HEROSUPERPOWER, id);

        final String DELETE_SUPERPOWER = "DELETE FROM Superpower WHERE superpowerId = ?";
        jdbc.update(DELETE_SUPERPOWER, id);
    }

    public static final class SuperpowerMapper implements RowMapper<Superpower> {

        @Override
        public Superpower mapRow(ResultSet rs, int index) throws SQLException {
            Superpower superpower = new Superpower();
            superpower.setSuperpowerID(rs.getInt("superpowerID"));
            superpower.setSuperpowerName(rs.getString("superpowerName"));
            superpower.setSuperpowerDescription(rs.getString("superpowerDescription"));
            return superpower;
        }
    }

}
