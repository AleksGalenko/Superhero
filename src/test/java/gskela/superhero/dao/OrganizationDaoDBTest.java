/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gskela.superhero.dao;

import gskela.superhero.dto.Hero;
import gskela.superhero.dto.Location;
import gskela.superhero.dto.Organization;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author gskela
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationDaoDBTest {

    @Autowired
    OrganizationDAO orgDAO;

    @Autowired
    LocationDAO locationDAO;

    @Autowired
    HeroDAO heroDAO;

    @Autowired
    JdbcTemplate jdbc;

    private Hero testHero, testHero2;
    private Location testLocation, testLocation2;
    private Organization testOrganization, testOrganization2;
    private List<Hero> heroes, heroes2;

    @BeforeEach
    public void setUp() {
        testHero = new Hero();
        testHero.setHeroName("Superman");
        testHero.setHeroDescription("Man of Steel");
        testHero.setVillain(false);
        testHero = heroDAO.addHero(testHero);

        testHero2 = new Hero();
        testHero2.setHeroName("Batman");
        testHero2.setHeroDescription("Dark vigilante fighting crime in Gotham City");
        testHero2.setVillain(false);
        testHero2 = heroDAO.addHero(testHero2);

        heroes = new ArrayList<>();
        heroes.add(testHero);
        heroes.add(testHero2);

        heroes2 = new ArrayList<>();
        heroes2.add(testHero);

        testLocation = new Location();
        testLocation.setLocationName("Metropolis");
        testLocation.setLocationDescription("The big city");
        testLocation.setLocationAddress("123 Main St, Metropolis, USA");
        testLocation.setLocationLatitude("40.712008");
        testLocation.setLocationLongitude("-74.006000");
        testLocation = locationDAO.addLocation(testLocation);

        testLocation2 = new Location();
        testLocation2.setLocationName("Metropolis");
        testLocation2.setLocationDescription("The big city");
        testLocation2.setLocationAddress("123 Main St, Metropolis, USA");
        testLocation2.setLocationLatitude("41.712008");
        testLocation2.setLocationLongitude("54.006000");
        testLocation2 = locationDAO.addLocation(testLocation2);

        testOrganization = new Organization();
        testOrganization.setOrgName("Test Organization Name");
        testOrganization.setOrgDescription("Test Organization Description");
        testOrganization.setOrgPhone("111-222-3333");
        testOrganization.setOrgEmail("test@emal.com");
        testOrganization.setOrgOfVillains(true);
        testOrganization.setOrgAddress("Address of Org");
        testOrganization.setHeroes(heroes);
        testOrganization = orgDAO.addOrganization(testOrganization);

        testOrganization2 = new Organization();
        testOrganization2.setOrgName("Test Organization Name2");
        testOrganization2.setOrgDescription("Test Organization Description2");
        testOrganization2.setOrgPhone("333-111-3333");
        testOrganization2.setOrgEmail("test2@emal.com");
        testOrganization2.setOrgOfVillains(false);
        testOrganization2.setOrgAddress("Address of Org2");
        testOrganization2.setHeroes(heroes2);
        testOrganization2 = orgDAO.addOrganization(testOrganization2);

    }

    @AfterEach
    public void tearDown() {
        List<Organization> organizations = orgDAO.getAllOrganizations();
        for (Organization organization : organizations) {
            orgDAO.deleteOrganization(organization.getOrgID());
        }

        List<Hero> heroes = heroDAO.getAllHeroes();
        for (Hero hero : heroes) {
            heroDAO.deleteHero(hero.getHeroID());
        }
    }

    @Test
    public void testAddAndGetOrganization() {

        Organization organization = new Organization();
        organization.setOrgName("Test Organization Name");
        organization.setOrgDescription("Test Organization Description");
        organization.setOrgPhone("111-222-3333");
        organization.setOrgEmail("test@emal.com");
        organization.setOrgOfVillains(true);
        organization.setOrgAddress("Address of Org");
        organization.setHeroes(heroes);

        organization = orgDAO.addOrganization(organization);

        Organization fromDao = orgDAO.getOrganizationById(organization.getOrgID());
        assertEquals(organization, fromDao);
    }

    @Test
    public void testGetAllOrganizations() {

        List<Organization> orgs = orgDAO.getAllOrganizations();

        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(testOrganization));
        assertTrue(orgs.contains(testOrganization2));
    }

    @Test
    public void testUpdateOrganization() {

        Organization fromDAO = orgDAO.getOrganizationById(testOrganization.getOrgID());
        assertEquals(testOrganization, fromDAO);

        testOrganization.setOrgName("Test Organization Name999");
        orgDAO.updateOrganization(testOrganization);

        assertNotEquals(testOrganization, fromDAO);

        fromDAO = orgDAO.getOrganizationById(testOrganization.getOrgID());
        assertEquals(testOrganization, fromDAO);
    }

    @Test
    public void testDeleteOrganization() {

        Organization fromDAO = orgDAO.getOrganizationById(testOrganization.getOrgID());
        assertEquals(testOrganization, fromDAO);

        orgDAO.deleteOrganization(testOrganization.getOrgID());
        fromDAO = orgDAO.getOrganizationById(testOrganization.getOrgID());
        assertNull(fromDAO);

        final String GET_ALL_ORGS = "SELECT COUNT(*) FROM Members WHERE orgID=?";
        int count = jdbc.queryForObject(GET_ALL_ORGS, new Object[]{testOrganization.getOrgID()}, Integer.class);
        assertEquals(0, count);
    }

    @Test
    public void testGetOrganizationsByHero() {

        Organization fromDAO = orgDAO.getOrganizationById(testOrganization.getOrgID());
        assertEquals(testOrganization, fromDAO);

        Organization fromDAO1 = orgDAO.getOrganizationById(testOrganization2.getOrgID());
        assertEquals(testOrganization2, fromDAO1);

        List<Organization> organizations1 = orgDAO.getOrganizationsByHero(testHero);
        assertTrue(organizations1.contains(testOrganization));
        assertTrue(organizations1.contains(testOrganization2));

        List<Organization> organizations2 = orgDAO.getOrganizationsByHero(testHero2);
        assertTrue(organizations2.contains(testOrganization));
        assertFalse(organizations2.contains(testOrganization2));
    }

}
