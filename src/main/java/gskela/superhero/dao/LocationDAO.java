/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gskela.superhero.dao;

import gskela.superhero.dto.Location;
import java.util.List;

/**
 *
 * @author gskela
 */
public interface LocationDAO {

    Location addLocation(Location location);

    Location getLocationById(int locationID);

    List<Location> getAllLocations();

    void updateLocation(Location location);

    void deleteLocation(int locationID);
}
