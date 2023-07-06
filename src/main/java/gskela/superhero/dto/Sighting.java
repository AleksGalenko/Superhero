/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gskela.superhero.dto;

import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 *
 * @author gskela
 */
public class Sighting {

    private int sightingID;

    @NotNull(message = "Date must not be blank. ")
    private LocalDate sightingDate;
    
    @NotNull(message = "Hero must not be empty. ")
    private Hero hero;
    
    @NotNull(message = "Location must not be empty. ")
    private Location location;

    public int getSightingID() {
        return sightingID;
    }

    public void setSightingID(int sightingID) {
        this.sightingID = sightingID;
    }

    public LocalDate getSightingDate() {
        return sightingDate;
    }

    public void setSightingDate(LocalDate sightingDate) {
        this.sightingDate = sightingDate;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.sightingID;
        hash = 59 * hash + Objects.hashCode(this.sightingDate);
        hash = 59 * hash + Objects.hashCode(this.hero);
        hash = 59 * hash + Objects.hashCode(this.location);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sighting other = (Sighting) obj;
        if (this.sightingID != other.sightingID) {
            return false;
        }
        if (!Objects.equals(this.sightingDate, other.sightingDate)) {
            return false;
        }
        if (!Objects.equals(this.hero, other.hero)) {
            return false;
        }
        return Objects.equals(this.location, other.location);
    }

}
