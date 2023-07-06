/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gskela.superhero.dao;

import gskela.superhero.dto.Location;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author gskela
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationDaoDBTest {

    @Autowired
    LocationDAO dao;

    private Location location1, location2;

    @BeforeEach
    public void setUp() {


        location1 = new Location();
        location1.setLocationName("Test Location 1");
        location1.setLocationDescription("Test Location 1 Description");
        location1.setLocationAddress("123 Test St");
        location1.setLocationLatitude("1.2345");
        location1.setLocationLongitude("4.563423");
        location1 = dao.addLocation(location1);

        location2 = new Location();
        location2.setLocationName("Test Location 2");
        location2.setLocationDescription("Test Location 2 Description");
        location2.setLocationAddress("456 Test St");
        location2.setLocationLatitude("7.891234");
        location2.setLocationLongitude("0.124");
        location2 = dao.addLocation(location2);

    }

    @AfterEach
    public void tearDown() {
        List<Location> locations = dao.getAllLocations();
        for (Location location : locations) {
            dao.deleteLocation(location.getLocationID());
        }
    }

    @Test
    public void testAddLocation() {
        Location location = new Location();
        location.setLocationName("Test Location");
        location.setLocationDescription("Test Location Description");
        location.setLocationAddress("123 Test St");
        location.setLocationLatitude("1.23");
        location.setLocationLongitude("4.56");

        Location addedLocation = dao.addLocation(location);

        assertNotNull(addedLocation);
        assertNotNull(addedLocation.getLocationID());
        assertEquals(location.getLocationName(), addedLocation.getLocationName());
        assertEquals(location.getLocationDescription(), addedLocation.getLocationDescription());
        assertEquals(location.getLocationAddress(), addedLocation.getLocationAddress());
        assertEquals(location.getLocationLatitude(), addedLocation.getLocationLatitude());
        assertEquals(location.getLocationLongitude(), addedLocation.getLocationLongitude());
    }

    @Test
    public void testGetLocationById() {

        Location fromDao = dao.getLocationById(location1.getLocationID());

        assertNotNull(fromDao);
        assertEquals(fromDao, location1);

    }

    @Test
    public void testGetAllLocations() {

        List<Location> locations = dao.getAllLocations();

        assertNotNull(locations);
        assertEquals(2, locations.size());
        assertTrue(locations.contains(location1));
    }

    @Test
    public void testUpdateLocation() {

        Location fromDAO = dao.getLocationById(location1.getLocationID());

        assertEquals(location1, fromDAO);

        location1.setLocationName("New Test Location");
        location1.setLocationDescription("New Test Location Description");
        location1.setLocationAddress("123 Test New St");
        location1.setLocationLatitude("48.446862");
        location1.setLocationLongitude("56.494906");
        dao.updateLocation(location1);

        assertNotEquals(location1, fromDAO);

        fromDAO = dao.getLocationById(location1.getLocationID());

        assertEquals(location1, fromDAO);
    }

    @Test
    public void testDeleteLocation() {

        Location addedLocation = dao.addLocation(location1);
        Location fromDAO = dao.getLocationById(addedLocation.getLocationID());
        assertEquals(addedLocation, fromDAO);

        dao.deleteLocation(addedLocation.getLocationID());
        Location deletedLocation = dao.getLocationById(addedLocation.getLocationID());

        assertNull(deletedLocation);
    }

}
