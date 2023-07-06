/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gskela.superhero.dao;

import gskela.superhero.dao.HeroDaoDB.HeroMapper;
import gskela.superhero.dao.LocationDaoDB.LocationMapper;
import gskela.superhero.dto.Hero;
import gskela.superhero.dto.Location;
import gskela.superhero.dto.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
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
public class SightingDaoDB implements SightingDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String ADD_SIGHTING = "INSERT INTO Sighting (sightingDate, heroID, locationID) VALUES (?, ?, ?)";
        jdbc.update(ADD_SIGHTING, sighting.getSightingDate(), sighting.getHero().getHeroID(), sighting.getLocation().getLocationID());
        int newID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setSightingID(newID);
        return sighting;
    }

    @Override
    public Sighting getSightingByID(int sightingID) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM Sighting WHERE sightingID = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), sightingID);
            sighting.setHero(getHeroForSighting(sightingID));
            sighting.setLocation(getLocationForSighting(sightingID));
            return sighting;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Hero getHeroForSighting(int sightingID) {
        final String SELECT_HERO_FOR_SIGHTING = "SELECT h.* FROM Hero h JOIN Sighting s ON s.heroID = h.heroID WHERE s.sightingID = ?";
        return jdbc.queryForObject(SELECT_HERO_FOR_SIGHTING, new HeroMapper(), sightingID);
    }

    private Location getLocationForSighting(int sightingID) {
        final String SELECT_LOC_FOR_SIGHTING = "SELECT l.* FROM Location l JOIN Sighting s ON s.locationID = l.locationID WHERE s.sightingID = ?";
        return jdbc.queryForObject(SELECT_LOC_FOR_SIGHTING, new LocationMapper(), sightingID);
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM Sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        matchHeroes(sightings);
        return sightings;
    }

    private void matchHeroes(List<Sighting> sightings) {
        for (Sighting sighting : sightings) {
            sighting.setHero(getHeroForSighting(sighting.getSightingID()));
            sighting.setLocation(getLocationForSighting(sighting.getSightingID()));
        }
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        try {
            final String SELECT_SIGHTINGS_BY_DATE = "SELECT * FROM Sighting WHERE sightingDate = ?";
            return jdbc.query(SELECT_SIGHTINGS_BY_DATE, new SightingMapper(), date);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getSightingsByLocation(Location location) {
        final String SELECT_SIGHTINGS_BY_LOCATION = "SELECT * FROM Sighting WHERE locationID = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_BY_LOCATION, new SightingMapper(), location.getLocationID());
        matchHeroes(sightings);
        return sightings;
    }

    @Override
    public List<Sighting> getSightingsByHero(Hero hero) {
        final String SELECT_SIGHTINGS_BY_HERO = "SELECT * FROM Sighting WHERE heroID = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_BY_HERO, new SightingMapper(), hero.getHeroID());
        matchHeroes(sightings);
        return sightings;
    }

    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE Sighting SET sightingDate = ?, heroID = ?, locationID = ? WHERE sightingID = ?";
        jdbc.update(UPDATE_SIGHTING, sighting.getSightingDate(), sighting.getHero().getHeroID(), sighting.getLocation().getLocationID(), sighting.getSightingID());
    }

    @Override
    public void deleteSighting(int sightingID) {
        final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE sightingID = ?";
        jdbc.update(DELETE_SIGHTING, sightingID);
    }

    @Override
    public List<Sighting> getLastSightings() {
        List<Sighting> lastSightings = getAllSightings();
        lastSightings.sort(Comparator.comparing(Sighting::getSightingDate).reversed());
        if (lastSightings.size() > 10) {
            return lastSightings.subList(0, 10);
        }
        return lastSightings;
    }

    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setSightingID(rs.getInt("sightingID"));
            sighting.setSightingDate(rs.getDate("sightingDate").toLocalDate());
            Hero hero = new Hero();
            hero.setHeroID(rs.getInt("heroID"));
            sighting.setHero(hero);
            Location location = new Location();
            location.setLocationID(rs.getInt("locationID"));
            sighting.setLocation(location);
            return sighting;
        }
    }

}
