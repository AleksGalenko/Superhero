/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gskela.superhero.dao;

import gskela.superhero.dao.HeroDaoDB.HeroMapper;
import gskela.superhero.dto.Hero;
import gskela.superhero.dto.Organization;
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
public class OrganizationDaoDB implements OrganizationDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int orgID) {
        try {
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM Organization WHERE orgID = ?";
            Organization organization = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), orgID);
            organization.setHeroes(getHeroesForOrganization(orgID));
            return organization;
        } catch (DataAccessException ex) {
            return null;
        }
    }


    private List<Hero> getHeroesForOrganization(int orgID) {
        final String SELECT_HEROES_FOR_ORGANIZATION = "SELECT h.* FROM Hero h JOIN Members m ON m.heroID = h.heroID WHERE m.orgID = ?";
        return jdbc.query(SELECT_HEROES_FOR_ORGANIZATION, new HeroMapper(), orgID);

    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        final String ADD_ORGANIZATION = "INSERT INTO Organization (orgName, orgDescription, orgPhone, orgEmail, orgOfVillains, orgAddress) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        jdbc.update(ADD_ORGANIZATION, organization.getOrgName(), organization.getOrgDescription(), organization.getOrgPhone(),
                organization.getOrgEmail(), organization.getOrgOfVillains(), organization.getOrgAddress());
        int newID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setOrgID(newID);
        insertHeroes(organization);
        return organization;
    }

    private void insertHeroes(Organization organization) {
        final String INSERT_HEROES = "INSERT INTO Members(heroID, orgID) VALUES(?,?)";
        for (Hero hero : organization.getHeroes()) {
            jdbc.update(INSERT_HEROES,
                    hero.getHeroID(),
                    organization.getOrgID()
                    );
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM Organization";
        return jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
    }

    @Override
    @Transactional
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE Organization SET orgName = ?, orgDescription = ?, orgPhone = ?, orgEmail = ?, orgOfVillains = ?, orgAddress = ? "
                + "WHERE orgID = ?";
        jdbc.update(UPDATE_ORGANIZATION, organization.getOrgName(), organization.getOrgDescription(), organization.getOrgPhone(),
                organization.getOrgEmail(), organization.getOrgOfVillains(), organization.getOrgAddress(), organization.getOrgID());

        final String DELETE_FROM_MEMBERS = "DELETE FROM Members WHERE orgID=?";
        jdbc.update(DELETE_FROM_MEMBERS, organization.getOrgID());
        insertHeroes(organization);
    }

    @Override
    @Transactional
    public void deleteOrganization(int orgID) {
        final String DELETE_ORGANIZATION_FROM_MEMBERS = "DELETE FROM Members WHERE orgID = ?";
        jdbc.update(DELETE_ORGANIZATION_FROM_MEMBERS, orgID);

        final String DELETE_ORGANIZATION = "DELETE FROM Organization WHERE orgID = ?";
        jdbc.update(DELETE_ORGANIZATION, orgID);
    }

    @Override
    public List<Organization> getOrganizationsByHero(Hero hero) {
        final String SELECT_ORGANIZATIONS_BY_HERO = "SELECT o.* FROM Organization o "
                + "JOIN members m ON m.orgID = o.orgID "
                + "WHERE m.heroID = ?";
        return jdbc.query(SELECT_ORGANIZATIONS_BY_HERO, new OrganizationMapper(), hero.getHeroID());
    }

    private final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {

            Organization organization = new Organization();
            organization.setOrgID(rs.getInt("orgID"));
            organization.setOrgName(rs.getString("orgName"));
            organization.setOrgDescription(rs.getString("orgDescription"));
            organization.setOrgPhone(rs.getString("orgPhone"));
            organization.setOrgEmail(rs.getString("orgEmail"));
            organization.setOrgOfVillains(rs.getBoolean("orgOfVillains"));
            organization.setOrgAddress(rs.getString("orgAddress"));
            organization.setHeroes(getHeroesForOrganization(organization.getOrgID()));

            return organization;
        }
    }
}
