/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package gskela.superhero.dao;

import gskela.superhero.dto.Hero;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author gskela
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HeroDaoDBTest {

    @Autowired
    HeroDAO dao;

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {
        List<Hero> heroes = dao.getAllHeroes();
        for (Hero hero : heroes) {
            dao.deleteHero(hero.getHeroID());
        }
    }

    @Test
    public void testAddAndGetHero() {
        Hero hero = new Hero();
        hero.setHeroName("Iron Man");
        hero.setHeroDescription("A genius inventor who uses a high-tech suit to fight crime.");
        hero.setVillain(false);
        byte[] image = {0x12, 0x34, 0x56, 0x78};
        hero.setHeroImage(image);

        dao.addHero(hero);

        Hero fromDAO = dao.getHeroById(hero.getHeroID());

        assertEquals(hero, fromDAO);
    }

    @Test
    public void testUpdateHero() {
        Hero hero = new Hero();
        hero.setHeroName("Hulk");
        hero.setHeroDescription("A scientist who transforms into a giant, super strong green monster when angry.");
        hero.setVillain(false);
        dao.addHero(hero);

        hero.setHeroName("The Incredible Hulk");
        hero.setHeroDescription("A superhero who transforms into a giant, super strong green monster when angry.");
        hero.setVillain(false);
        dao.updateHero(hero);

        Hero fromDAO = dao.getHeroById(hero.getHeroID());

        assertEquals(hero, fromDAO);
    }

    @Test
    public void testDeleteHero() {
        Hero hero = new Hero();
        hero.setHeroName("Captain America");
        hero.setHeroDescription("A soldier who was given a super-soldier serum and a shield to fight crime.");
        hero.setVillain(false);
        dao.addHero(hero);

        dao.deleteHero(hero.getHeroID());

        Hero fromDAO = dao.getHeroById(hero.getHeroID());

        assertNull(fromDAO);
    }

    @Test
    public void testGetAllHeroes() {

        Hero hero = new Hero();
        hero.setHeroName("Spiderman");
        hero.setHeroDescription("A superhero who can crawl walls and swing through the city with webs.");
        hero.setVillain(false);
        dao.addHero(hero);

        hero = new Hero();
        hero.setHeroName("Doctor Octopus");
        hero.setHeroDescription("A villain who uses mechanical arms to commit crimes.");
        hero.setVillain(true);
        dao.addHero(hero);

        hero = new Hero();
        hero.setHeroName("Green Goblin");
        hero.setHeroDescription("A villain who uses his glider and pumpkin bombs to cause destruction.");
        hero.setVillain(true);
        dao.addHero(hero);
        List<Hero> heroes = dao.getAllHeroes();

        assertEquals(3, heroes.size());
    }

}
