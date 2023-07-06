/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gskela.superhero.controller;

import gskela.superhero.dao.HeroDAO;
import gskela.superhero.dao.OrganizationDAO;
import gskela.superhero.dto.Hero;
import gskela.superhero.dto.Organization;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author gskela
 */
@Controller
public class OrganizationController {

    @Autowired
    OrganizationDAO orgDAO;

    @Autowired
    HeroDAO heroDAO;

    Set<ConstraintViolation<Organization>> violations = new HashSet<>();

    @GetMapping("org")
    public String displayLocations(Model model) {
        List<Organization> organizations = orgDAO.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        List<Hero> heroes = heroDAO.getAllHeroes();
        model.addAttribute("heroes", heroes);
        return "org";
    }

    @PostMapping("addOrg")
    public String addOrganization(HttpServletRequest request, RedirectAttributes redirectAttributes) {

        String name = request.getParameter("orgName");
        String description = request.getParameter("orgDescription");
        String address = request.getParameter("orgAddress");
        String phone = request.getParameter("orgPhone");
        String email = request.getParameter("orgEmail");
        boolean villains = Boolean.parseBoolean(request.getParameter("orgOfVillains"));
        String[] formDataArray = request.getParameterValues("members");

        List<Hero> heroes = new ArrayList<>();
        if (formDataArray != null) {
            for (String heroId : formDataArray) {
                heroes.add(heroDAO.getHeroById(Integer.parseInt(heroId)));
            }
        }
        Organization org = new Organization();
        org.setOrgName(name);
        org.setOrgDescription(description);
        org.setOrgAddress(address);
        org.setOrgPhone(phone);
        org.setOrgEmail(email);
        org.setOrgOfVillains(villains);
        org.setHeroes(heroes);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(org);

        if (violations.isEmpty()) {
            orgDAO.addOrganization(org);
        } else {
            redirectAttributes.addFlashAttribute("errors", violations);
        }
        return "redirect:/org";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        orgDAO.deleteOrganization(id);
        return "redirect:/org";
    }

    @GetMapping("org/editOrg")
    public String editOrg(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization org = orgDAO.getOrganizationById(id);
        model.addAttribute("organization", org);
        List<Hero> heroes = heroDAO.getAllHeroes();
        model.addAttribute("heroes", heroes);
        return "org/editOrg";
    }

    @PostMapping("org/editOrg")
    public String performEditSuperpower(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization org = orgDAO.getOrganizationById(id);
        List<Hero> heroes = new ArrayList<>();
        if (request.getParameterValues("members") != null) {
            for (String heroId : request.getParameterValues("members")) {
                heroes.add(heroDAO.getHeroById(Integer.parseInt(heroId)));
            }
        }
        org.setOrgName(request.getParameter("orgName"));
        org.setOrgDescription(request.getParameter("orgDescription"));
        org.setOrgAddress(request.getParameter("orgAddress"));
        org.setOrgPhone(request.getParameter("orgPhone"));
        org.setOrgEmail(request.getParameter("orgEmail"));
        org.setOrgOfVillains(Boolean.parseBoolean(request.getParameter("orgOfVillains")));
        org.setHeroes(heroes);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(org);

        if (violations.isEmpty()) {
             orgDAO.updateOrganization(org);
        } else {
            redirectAttributes.addFlashAttribute("errors", violations);
            return "redirect:/org/editOrg?id=" + org.getOrgID();
        }
        return "redirect:/org";
    }

    @GetMapping("org/orgDetails")
    public String orgDetails(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization org = orgDAO.getOrganizationById(id);
        model.addAttribute("organization", org);
        List<Hero> heroes = org.getHeroes();
        model.addAttribute("heroes", heroes);
        return "org/orgDetails";
    }

}
