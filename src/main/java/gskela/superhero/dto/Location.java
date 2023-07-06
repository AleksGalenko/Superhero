/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gskela.superhero.dto;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author gskela
 */
public class Location {

    private int locationID;
    @NotBlank(message = "Name must not be blank. ")
    @Size(max = 50, message = "Name must be fewer than 50 characters. ")
    private String locationName;

    @NotBlank(message = "Description must not be blank. ")
    @Size(max = 255, message = "Description must be fewer than 255 characters. ")
    private String locationDescription;

    @NotBlank(message = "Address must not be blank. ")
    @Size(max = 255, message = "Address must be fewer than 255 characters. ")
    private String locationAddress;

    @NotBlank(message = "Latitude must not be blank. ")
    private String locationLatitude;

    @NotBlank(message = "Longitude must not be blank. ")
    private String locationLongitude;

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public String getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(String locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public String getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(String locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.locationID;
        hash = 83 * hash + Objects.hashCode(this.locationName);
        hash = 83 * hash + Objects.hashCode(this.locationDescription);
        hash = 83 * hash + Objects.hashCode(this.locationAddress);
        hash = 83 * hash + Objects.hashCode(this.locationLatitude);
        hash = 83 * hash + Objects.hashCode(this.locationLongitude);
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
        final Location other = (Location) obj;
        if (this.locationID != other.locationID) {
            return false;
        }
        if (!Objects.equals(this.locationName, other.locationName)) {
            return false;
        }
        if (!Objects.equals(this.locationDescription, other.locationDescription)) {
            return false;
        }
        if (!Objects.equals(this.locationAddress, other.locationAddress)) {
            return false;
        }
        if (!Objects.equals(this.locationLatitude, other.locationLatitude)) {
            return false;
        }
        return Objects.equals(this.locationLongitude, other.locationLongitude);
    }

}
