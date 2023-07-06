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
public class Superpower {

    private int superpowerID;

    @NotBlank(message = "Name must not be blank. ")
    @Size(max = 50, message = "Name must be fewer than 50 characters. ")
    private String superpowerName;

    @NotBlank(message = "Description must not be blank. ")
    @Size(max = 255, message = "Description must be fewer than 255 characters. ")
    private String superpowerDescription;

    public int getSuperpowerID() {
        return superpowerID;
    }

    public void setSuperpowerID(int superpowerID) {
        this.superpowerID = superpowerID;
    }

    public String getSuperpowerName() {
        return superpowerName;
    }

    public void setSuperpowerName(String superpowerName) {
        this.superpowerName = superpowerName;
    }

    public String getSuperpowerDescription() {
        return superpowerDescription;
    }

    public void setSuperpowerDescription(String superpowerDescription) {
        this.superpowerDescription = superpowerDescription;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.superpowerID;
        hash = 67 * hash + Objects.hashCode(this.superpowerName);
        hash = 67 * hash + Objects.hashCode(this.superpowerDescription);
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
        final Superpower other = (Superpower) obj;
        if (this.superpowerID != other.superpowerID) {
            return false;
        }
        if (!Objects.equals(this.superpowerName, other.superpowerName)) {
            return false;
        }
        return Objects.equals(this.superpowerDescription, other.superpowerDescription);
    }

}
