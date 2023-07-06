/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gskela.superhero.dao;

import gskela.superhero.dto.Hero;
import gskela.superhero.dto.Location;
import gskela.superhero.dto.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author gskela
 */
public interface SightingDAO {

    Sighting addSighting(Sighting sighting);

    Sighting getSightingByID(int sightingID);

    List<Sighting> getAllSightings();

    List<Sighting> getSightingsByDate (LocalDate date);

    List<Sighting> getSightingsByLocation (Location location);

    List<Sighting> getSightingsByHero(Hero hero);
    
    List<Sighting> getLastSightings();

    void updateSighting(Sighting sighting);

    void deleteSighting(int sightingID);
}
