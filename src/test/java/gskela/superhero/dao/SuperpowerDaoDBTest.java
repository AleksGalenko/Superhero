/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package gskela.superhero.dao;

import gskela.superhero.dto.Hero;
import gskela.superhero.dto.Superpower;
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
public class SuperpowerDaoDBTest {

    @Autowired
    SuperpowerDAO spDAO;

    @Autowired
    HeroDAO heroDAO;

    @Autowired
    JdbcTemplate jdbc;

private Superpower superpower, superpower2;
    
    @BeforeEach
    public void setUp() {
        superpower = new Superpower();
        superpower.setSuperpowerName("Agility");
        superpower.setSuperpowerDescription("Superhero's exceptional balance, reflexes, and coordination allow effortless evasion, obstacle navigation, and swift combat maneuvers. It provides speed, precision, and evasiveness, making them formidable in any encounter");

        superpower = spDAO.addSuperpower(superpower);

        superpower2 = new Superpower();
        superpower2.setSuperpowerName("Flight");
        superpower2.setSuperpowerDescription("Soaring through skies, defying gravity effortlessly, superhero traverses vast distances with incredible speed and freedom");

        superpower2 = spDAO.addSuperpower(superpower);
    }

    @AfterEach
    public void tearDown() {
        List<Superpower> superpowers = spDAO.getAllSuperpowers();
        for (Superpower superpower : superpowers) {
            spDAO.deleteSuperpowerById(superpower.getSuperpowerID());
        }

        List<Hero> heroes = heroDAO.getAllHeroes();
        for (Hero hero : heroes) {
            heroDAO.deleteHero(hero.getHeroID());
        }
    }

    @Test
    public void testAddAndGetSuperpowers() {

        Superpower testsSuperpower = new Superpower();
        testsSuperpower.setSuperpowerName("Agility");
        testsSuperpower.setSuperpowerDescription("Superhero's exceptional balance, reflexes, and coordination allow effortless evasion, obstacle navigation, and swift combat maneuvers. It provides speed, precision, and evasiveness, making them formidable in any encounter");

        testsSuperpower = spDAO.addSuperpower(testsSuperpower);

        Superpower fromDAO = spDAO.getSuperpowerById(testsSuperpower.getSuperpowerID());
        assertEquals(testsSuperpower, fromDAO);
    }

    @Test
    public void testGetAllSuperpowers() {

        List<Superpower> superpowers = spDAO.getAllSuperpowers();
        assertEquals(2, superpowers.size());
        assertTrue(superpowers.contains(superpower));
        assertTrue(superpowers.contains(superpower2));
    }
    
    @Test
    public void testUpdateSuperpower() {
        
        Superpower fromDao = spDAO.getSuperpowerById(superpower.getSuperpowerID());       
        assertEquals(superpower,fromDao);
        
        superpower.setSuperpowerName("Intelligence");
        superpower.setSuperpowerDescription("Superhero possesses exceptional mental acuity, strategic thinking, problem-solving skills, and vast knowledge, making them a brilliant tactician and resourceful problem solver.");
        
        spDAO.updateSuperpower(superpower);
        assertNotEquals(superpower,fromDao);
        
        fromDao = spDAO.getSuperpowerById(superpower.getSuperpowerID());
        assertEquals(superpower,fromDao);
    }
    
    @Test
    public void testDeleteSuperpowerById() {
        
        Superpower fromDao = spDAO.getSuperpowerById(superpower.getSuperpowerID());       
        assertEquals(superpower,fromDao);
        
        spDAO.deleteSuperpowerById(superpower.getSuperpowerID());
        
        fromDao = spDAO.getSuperpowerById(superpower.getSuperpowerID());
        assertNull(fromDao);
    }

}