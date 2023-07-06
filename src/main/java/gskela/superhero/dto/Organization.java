/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gskela.superhero.dto;

import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 *
 * @author gskela
 */
public class Organization {

    private int orgID;

    @NotBlank(message = "Name must not be blank. ")
    @Size(max = 50, message = "Name must be fewer than 50 characters. ")
    private String orgName;
    
    @NotBlank(message = "Description must not be blank. ")
    @Size(max = 255, message = "Description must be fewer than 255 characters. ")
    private String orgDescription;
    private String orgPhone;
    private String orgEmail;
    
    private Boolean orgOfVillains;
    private String orgAddress;
    private List<Hero> heroes;

    public int getOrgID() {
        return orgID;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgDescription() {
        return orgDescription;
    }

    public void setOrgDescription(String orgDescription) {
        this.orgDescription = orgDescription;
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public Boolean getOrgOfVillains() {
        return orgOfVillains;
    }

    public void setOrgOfVillains(Boolean orgOfVillains) {
        this.orgOfVillains = orgOfVillains;
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.orgID;
        hash = 79 * hash + Objects.hashCode(this.orgName);
        hash = 79 * hash + Objects.hashCode(this.orgDescription);
        hash = 79 * hash + Objects.hashCode(this.orgPhone);
        hash = 79 * hash + Objects.hashCode(this.orgEmail);
        hash = 79 * hash + Objects.hashCode(this.orgOfVillains);
        hash = 79 * hash + Objects.hashCode(this.orgAddress);
        hash = 79 * hash + Objects.hashCode(this.heroes);
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
        final Organization other = (Organization) obj;
        if (this.orgID != other.orgID) {
            return false;
        }
        if (!Objects.equals(this.orgName, other.orgName)) {
            return false;
        }
        if (!Objects.equals(this.orgDescription, other.orgDescription)) {
            return false;
        }
        if (!Objects.equals(this.orgPhone, other.orgPhone)) {
            return false;
        }
        if (!Objects.equals(this.orgEmail, other.orgEmail)) {
            return false;
        }
        if (!Objects.equals(this.orgAddress, other.orgAddress)) {
            return false;
        }
        if (!Objects.equals(this.orgOfVillains, other.orgOfVillains)) {
            return false;
        }
        return Objects.equals(this.heroes, other.heroes);
    }

}
