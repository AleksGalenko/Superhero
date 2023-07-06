/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gskela.superhero.dao;

import gskela.superhero.dto.Hero;
import gskela.superhero.dto.Organization;
import java.util.List;

/**
 *
 * @author gskela
 */
public interface OrganizationDAO {

    Organization addOrganization(Organization organization);

    Organization getOrganizationById(int orgID);

    List<Organization> getAllOrganizations();
    
    void updateOrganization(Organization organization);

    void deleteOrganization(int orgID);
    
    List<Organization> getOrganizationsByHero(Hero hero);

}
