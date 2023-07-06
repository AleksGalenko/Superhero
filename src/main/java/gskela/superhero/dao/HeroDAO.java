/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gskela.superhero.dao;

import gskela.superhero.dto.Hero;
import gskela.superhero.dto.Location;
import gskela.superhero.dto.Superpower;
import java.util.List;

/**
 *
 * @author gskela
 */
public interface HeroDAO {

    Hero addHero(Hero hero);

    void updateHero(Hero hero);

    void deleteHero(int heroID);

    Hero getHeroById(int heroID);

    List<Hero> getAllHeroes();
    
    List<Hero> getHeroesBySuperpower(Superpower superpower);
    
    List<Hero> getHeroesByLocation(Location location);

}
