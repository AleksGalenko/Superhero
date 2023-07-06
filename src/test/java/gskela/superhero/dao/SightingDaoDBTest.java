/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gskela.superhero.dao;

import gskela.superhero.dto.Hero;
import gskela.superhero.dto.Location;
import gskela.superhero.dto.Sighting;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
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
public class SightingDaoDBTest {

    @Autowired
    SightingDaoDB dao;

    @Autowired
    HeroDaoDB heroDAO;

    @Autowired
    LocationDaoDB locDAO;

    private Hero testHero, testHero2;
    private Location testLocation, testLocation2;
    private Sighting testSighting, testSighting2, testSighting3, testSighting4;
    LocalDate date1 = LocalDate.parse("2023-03-18");
    
    LocalDate date2 = LocalDate.parse("2023-03-11");

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() throws ParseException {

        testHero = new Hero();
        testHero.setHeroName("Superman");
        testHero.setHeroDescription("Man of Steel");
        testHero.setVillain(false);
        testHero = heroDAO.addHero(testHero);

        testHero2 = new Hero();
        testHero2.setHeroName("Superman");
        testHero2.setHeroDescription("Man of Steel");
        testHero2.setVillain(false);
        testHero2 = heroDAO.addHero(testHero2);

        testLocation = new Location();
        testLocation.setLocationName("Metropolis");
        testLocation.setLocationDescription("The big city");
        testLocation.setLocationAddress("123 Main St, Metropolis, USA");
        testLocation.setLocationLatitude("40.712008");
        testLocation.setLocationLongitude("-74.006000");
        testLocation = locDAO.addLocation(testLocation);

        testLocation2 = new Location();
        testLocation2.setLocationName("Metropolis");
        testLocation2.setLocationDescription("The big city");
        testLocation2.setLocationAddress("123 Main St, Metropolis, USA");
        testLocation2.setLocationLatitude("41.712008");
        testLocation2.setLocationLongitude("54.006000");
        testLocation2 = locDAO.addLocation(testLocation2);

        testSighting = new Sighting();
        testSighting.setHero(testHero);
        testSighting.setLocation(testLocation);
        testSighting.setSightingDate(date1);
        testSighting = dao.addSighting(testSighting);

        testSighting2 = new Sighting();
        testSighting2.setHero(testHero2);
        testSighting2.setLocation(testLocation2);
        testSighting2.setSightingDate(date1);
        testSighting2 = dao.addSighting(testSighting2);

        testSighting3 = new Sighting();
        testSighting3.setHero(testHero);
        testSighting3.setLocation(testLocation);
        testSighting3.setSightingDate(date2);
        testSighting3 = dao.addSighting(testSighting3);

        testSighting4 = new Sighting();
        testSighting4.setHero(testHero2);
        testSighting4.setLocation(testLocation2);
        testSighting4.setSightingDate(date2);
        testSighting4 = dao.addSighting(testSighting4);
    }

    @AfterEach
    public void tearDown() {
        List<Location> locations = locDAO.getAllLocations();
        for (Location location : locations) {
            locDAO.deleteLocation(location.getLocationID());
        }

        List<Hero> heroes = heroDAO.getAllHeroes();
        for (Hero hero : heroes) {
            heroDAO.deleteHero(hero.getHeroID());
        }

        List<Sighting> sightings = dao.getAllSightings();
        for (Sighting sighting : sightings) {
            dao.deleteSighting(sighting.getSightingID());
        }
    }

    @Test
    public void testAddAndGetSighting() {

        Sighting sighting = new Sighting();
        sighting.setHero(testHero);
        sighting.setLocation(testLocation);
        sighting.setSightingDate(date1);
        sighting = dao.addSighting(sighting);

        Sighting fromDAO = dao.getSightingByID(sighting.getSightingID());
        assertEquals(sighting, fromDAO);
    }

    @Test
    public void testGetAllSightings() {

        List<Sighting> sightings = dao.getAllSightings();

        assertEquals(4, sightings.size());
        assertTrue(sightings.contains(testSighting));
        assertTrue(sightings.contains(testSighting2));
        assertTrue(sightings.contains(testSighting3));
        assertTrue(sightings.contains(testSighting4));

    }

    @Test
    public void testGetSightingsByDate() {
        List<Sighting> sightings1 = dao.getSightingsByDate(date1);
        List<Sighting> sightings2 = dao.getSightingsByDate(date2);

        assertEquals(2, sightings1.size());
        assertEquals(2, sightings2.size());

    }

    @Test
    public void testGetSightingsByLocation() {
        List<Sighting> sightings = dao.getSightingsByLocation(testLocation);

        assertEquals(2, sightings.size());
    }

    @Test
    public void testGetSightingsByHero() {

        List<Sighting> sightings = dao.getSightingsByHero(testHero);

        assertEquals(2, sightings.size());

    }

    @Test
    public void testUpdateSighting() {

        Sighting fromDAO = dao.getSightingByID(testSighting.getSightingID());
        assertEquals(testSighting, fromDAO);

        testSighting.setHero(testHero2);
        dao.updateSighting(testSighting);

        assertNotEquals(testSighting, fromDAO);

        fromDAO = dao.getSightingByID(testSighting.getSightingID());
        assertEquals(testSighting, fromDAO);

    }

    @Test
    public void testDeleteSighting() {
        Sighting fromDAO = dao.getSightingByID(testSighting.getSightingID());
        assertEquals(testSighting, fromDAO);

        dao.deleteSighting(testSighting.getSightingID());

        fromDAO = dao.getSightingByID(testSighting.getSightingID());
        assertNull(fromDAO);

    }

}
