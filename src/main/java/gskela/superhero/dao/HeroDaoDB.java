/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gskela.superhero.dao;

import gskela.superhero.dao.SuperpowerDaoDB.SuperpowerMapper;
import gskela.superhero.dto.Hero;
import gskela.superhero.dto.Location;
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
public class HeroDaoDB implements HeroDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        final String ADD_HERO = "INSERT INTO Hero (heroName, heroDescription, villain, heroImage) "
                + "VALUES (?, ?, ?, ?)";
        jdbc.update(ADD_HERO, hero.getHeroName(), hero.getHeroDescription(), hero.getVillain(), hero.getHeroImage());
        int newID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setHeroID(newID);
        insertHeroSuperpowers(hero);
        return hero;
    }

    @Override
    public void updateHero(Hero hero) {
        final String UPDATE_HERO = "UPDATE Hero SET heroName = ?, heroDescription = ?, villain = ?, heroImage = ? "
                + "WHERE heroID = ?";
        jdbc.update(UPDATE_HERO, hero.getHeroName(), hero.getHeroDescription(), hero.getVillain(), hero.getHeroImage(), hero.getHeroID());
        final String DELETE_FROM_HEROSUPERPOWERS = "DELETE FROM HeroSuperpowers WHERE heroID=?";
        jdbc.update(DELETE_FROM_HEROSUPERPOWERS, hero.getHeroID());
        insertHeroSuperpowers(hero);
    }

    @Override
    @Transactional
    public void deleteHero(int heroID) {
        final String DELETE_HERO_FROM_MEMBERS = "DELETE FROM Members WHERE heroID = ?";
        jdbc.update(DELETE_HERO_FROM_MEMBERS, heroID);

        final String DELETE_HERO_FROM_HEROSUPERPOWERS = "DELETE FROM HeroSuperpowers WHERE heroID = ?";
        jdbc.update(DELETE_HERO_FROM_HEROSUPERPOWERS, heroID);

        final String DELETE_SIGHTING = "DELETE FROM Sighting WHERE heroID = ?";
        jdbc.update(DELETE_SIGHTING, heroID);

        final String DELETE_HERO = "DELETE FROM Hero WHERE heroID = ?";
        jdbc.update(DELETE_HERO, heroID);
    }

    @Override
    public Hero getHeroById(int heroID) {
        try {
            final String SELECT_HERO_BY_ID = "SELECT * FROM Hero WHERE heroID = ?";
            Hero hero = jdbc.queryForObject(SELECT_HERO_BY_ID, new HeroMapper(), heroID);

            hero.setSuperpowers(getSuperpowersByHero(hero));
            return hero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private List<Superpower> getSuperpowersByHero(Hero hero) {
        final String SELECT_SUPERPOWERS_BY_HERO = "SELECT * FROM Superpower JOIN HeroSuperpowers ON HeroSuperpowers.SuperpowerID ="
                + " Superpower.SuperpowerID WHERE HeroSuperpowers.HeroId = ?";
        return jdbc.query(SELECT_SUPERPOWERS_BY_HERO, new SuperpowerMapper(), hero.getHeroID());
    }

    @Override
    public List<Hero> getAllHeroes() {
        final String SELECT_ALL_HEROES = "SELECT * FROM Hero";
        return jdbc.query(SELECT_ALL_HEROES, new HeroMapper());
    }

    @Override
    public List<Hero> getHeroesBySuperpower(Superpower superpower) {
        final String SELECT_HEROES_BY_SUPERPOWER = "SELECT * FROM Hero JOIN "
                + "HeroSuperpowers ON HeroSuperpowers.heroID = Hero.heroID WHERE HeroSuperpowers.SuperpowerID = ?";
        List<Hero> heroes = jdbc.query(SELECT_HEROES_BY_SUPERPOWER,
                new HeroMapper(), superpower.getSuperpowerID());
        return heroes;
    }

    public void insertHeroSuperpowers(Hero hero) {
        final String INSERT_HEROSUPERPOWERS = "INSERT INTO "
                + "HeroSuperpowers(heroID, superpowerID) VALUES(?,?)";
        if (hero.getSuperpowers() != null) {
            for (Superpower superpower : hero.getSuperpowers()) {
                jdbc.update(INSERT_HEROSUPERPOWERS,
                        hero.getHeroID(),
                        superpower.getSuperpowerID());
            }
        }
    }

    @Override
    public List<Hero> getHeroesByLocation(Location location) {
        final String SELECT_HEROES_BY_LOCATION = "SELECT * FROM Hero "
                + "JOIN Sighting ON Sighting.heroID = Hero.heroID WHERE locationID = ?";
        List<Hero> heroes = jdbc.query(SELECT_HEROES_BY_LOCATION,
                new HeroMapper(), location.getLocationID());
        return heroes;

    }

    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setHeroID(rs.getInt("heroID"));
            hero.setHeroName(rs.getString("heroName"));
            hero.setHeroDescription(rs.getString("heroDescription"));
            hero.setVillain(rs.getBoolean("villain"));
            hero.setHeroImage(rs.getBytes("heroImage"));
            return hero;
        }
    }
}
