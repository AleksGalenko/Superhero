/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gskela.superhero.dto;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 * @author gskela
 */
public class Hero {
    
    private int heroID;
    
    @NotBlank(message = "Name must not be blank. ")
    @Size(max = 50, message="Name must be fewer than 50 characters. ")
    private String heroName;
    
    @NotBlank(message = "Description must not be blank. ")
    @Size(max = 255, message = "Description must be fewer than 255 characters. ")
    private String heroDescription;

    private Boolean villain;
    
    private byte[] heroImage;
    
    @NotEmpty(message = "Superpower must not be empty. ")
    private List<Superpower> superpowers;

    
    
    public int getHeroID() {
        return heroID;
    }

    public void setHeroID(int heroID) {
        this.heroID = heroID;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public String getHeroDescription() {
        return heroDescription;
    }

    public void setHeroDescription(String heroDescription) {
        this.heroDescription = heroDescription;
    }


    public Boolean getVillain() {
        return villain;
    }

    public void setVillain(Boolean villain) {
        this.villain = villain;
    }
    
    public byte[] getHeroImage() {
        return this.heroImage;
    }
    
    public void setHeroImage(byte[] heroImage) {
        this.heroImage = heroImage;
    }

    public List<Superpower> getSuperpowers() {
        return superpowers;
    }

    public void setSuperpowers(List<Superpower> superpowers) {
        this.superpowers = superpowers;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.heroID;
        hash = 17 * hash + Objects.hashCode(this.heroName);
        hash = 17 * hash + Objects.hashCode(this.heroDescription);
        hash = 17 * hash + Objects.hashCode(this.villain);
        hash = 17 * hash + Arrays.hashCode(this.heroImage);

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
        final Hero other = (Hero) obj;
        if (this.heroID != other.heroID) {
            return false;
        }
        if (!Objects.equals(this.heroName, other.heroName)) {
            return false;
        }
        if (!Objects.equals(this.heroDescription, other.heroDescription)) {
            return false;
        }
        if (!Objects.equals(this.villain, other.villain)) {
            return false;
        }
        return Arrays.equals(this.heroImage, other.heroImage);
    }
  
}
