/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gskela.superhero.dao;

import gskela.superhero.dto.Location;
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
public class LocationDaoDB implements LocationDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String ADD_LOCATION_BY_ID = "INSERT INTO Location (locationName, locationDescription, locationAddress, locationLatitude, locationLongitude) "
                + "VALUES (?, ?, ?, ?, ?)";
        jdbc.update(ADD_LOCATION_BY_ID, location.getLocationName(), location.getLocationDescription(), location.getLocationAddress(),
                location.getLocationLatitude(), location.getLocationLongitude());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setLocationID(newId);
        return location;
    }

    @Override
    public Location getLocationById(int locationID) {
        try {
            final String SELECT_LOCATION = "SELECT * FROM Location WHERE locationID = ?";
            return jdbc.queryForObject(SELECT_LOCATION, new LocationMapper(), locationID);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM Location";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE Location SET locationName = ?, locationDescription = ?, locationAddress = ?, "
                + "locationLatitude = ?, locationLongitude = ? WHERE locationID = ?";
        jdbc.update(UPDATE_LOCATION, location.getLocationName(), location.getLocationDescription(), location.getLocationAddress(),
                location.getLocationLatitude(), location.getLocationLongitude(), location.getLocationID());
    }
    

    @Override
    @Transactional
    public void deleteLocation(int locationID) {

        final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE locationID = ?";
        jdbc.update(DELETE_SIGHTING, locationID);

        final String DELETE_LOCATION = "DELETE FROM Location WHERE locationID = ?";
        jdbc.update(DELETE_LOCATION, locationID);
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setLocationID(rs.getInt("locationID"));
            location.setLocationName(rs.getString("locationName"));
            location.setLocationDescription(rs.getString("locationDescription"));
            location.setLocationAddress(rs.getString("locationAddress"));
            location.setLocationLatitude(rs.getString("locationLatitude"));
            location.setLocationLongitude(rs.getString("locationLongitude"));
            return location;
        }
    }
}
