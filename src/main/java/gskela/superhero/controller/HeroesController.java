/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gskela.superhero.controller;

import gskela.superhero.dao.HeroDAO;
import gskela.superhero.dao.OrganizationDAO;
import gskela.superhero.dao.SightingDAO;
import gskela.superhero.dao.SuperpowerDAO;
import gskela.superhero.dto.Hero;
import gskela.superhero.dto.Organization;
import gskela.superhero.dto.Sighting;
import gskela.superhero.dto.Superpower;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author gskela
 */
@Controller
public class HeroesController {

    @Autowired
    HeroDAO heroDao;

    @Autowired
    SuperpowerDAO superpowerDao;

    @Autowired
    OrganizationDAO orgDao;

    @Autowired
    SightingDAO sightingDao;

    Set<ConstraintViolation<Hero>> violations = new HashSet<>();

    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> heroes = heroDao.getAllHeroes();
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("heroes", heroes);
        model.addAttribute("superpowers", superpowers);
        return "heroes";
    }

    @PostMapping("addHero")
    public String addHero(HttpServletRequest request, @ModelAttribute("hero") Hero hero, BindingResult result, RedirectAttributes redirectAttributes, @RequestParam("heroImage") MultipartFile file) {
        hero.setHeroName(request.getParameter("heroName"));
        hero.setHeroDescription(request.getParameter("heroDescription"));
        hero.setVillain(Boolean.valueOf(request.getParameter("villain")));
        String[] formDataArray = request.getParameterValues("superpower");
        List<Superpower> superpowers = new ArrayList<>();
        if (formDataArray != null) {
            for (String superpowerID : formDataArray) {
                superpowers.add(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerID)));
            }
        }
        hero.setSuperpowers(superpowers);
        if (file != null && !file.isEmpty()) {
            try {
                hero.setHeroImage(file.getBytes());
            } catch (IOException ex) {
                FieldError error = new FieldError("hero", "heroImage", ex.getMessage());
                result.addError(error);
            }
        } else {
            hero.setHeroImage(null);
        }
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);

        if (violations.isEmpty()) {
            heroDao.addHero(hero);
        } else {
            redirectAttributes.addFlashAttribute("errors", violations);
        }

        return "redirect:/heroes";
    }

    @GetMapping("deleteHero")
    public String deleteHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        heroDao.deleteHero(id);
        return "redirect:/heroes";
    }

    @GetMapping("heroes/heroDetails")
    public String heroDetails(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDao.getHeroById(id);
        byte[] heroImage = hero.getHeroImage();
        String heroImageData = null;
        if (heroImage != null) {
            heroImageData = Base64.getMimeEncoder().encodeToString(heroImage);
        }
        model.addAttribute("hero", hero);
        model.addAttribute("heroImage", heroImageData);
        List<Superpower> superpowers = hero.getSuperpowers();
        model.addAttribute("superpowers", superpowers);
        List<Organization> organizations = orgDao.getOrganizationsByHero(hero);
        model.addAttribute("organizations", organizations);
        List<Sighting> sightings = sightingDao.getSightingsByHero(hero);
        model.addAttribute("sightings", sightings);
        return "heroes/heroDetails";
    }

    @GetMapping("heroes/heroDetails_1")
    public String heroDetails_1(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDao.getHeroById(id);
        byte[] heroImage = hero.getHeroImage();
        String heroImageData = null;
        if (heroImage != null) {
            heroImageData = Base64.getMimeEncoder().encodeToString(heroImage);
        }
        model.addAttribute("hero", hero);
        model.addAttribute("heroImage", heroImageData);
        List<Superpower> superpowers = hero.getSuperpowers();
        model.addAttribute("superpowers", superpowers);
        List<Organization> organizations = orgDao.getOrganizationsByHero(hero);
        model.addAttribute("organizations", organizations);
        List<Sighting> sightings = sightingDao.getSightingsByHero(hero);
        model.addAttribute("sightings", sightings);
        return "heroes/heroDetails_1";
    }

    @GetMapping("heroes/editHero")
    public String editHero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDao.getHeroById(id);

        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);

        byte[] heroImage = hero.getHeroImage();
        String heroImageData = null;
        if (heroImage != null) {
            heroImageData = Base64.getMimeEncoder().encodeToString(heroImage);
        }
        model.addAttribute("hero", hero);
        model.addAttribute("heroImage", heroImageData);

        return "heroes/editHero";
    }

    @PostMapping("heroes/editHero")
    public String performEditHero(HttpServletRequest request, @ModelAttribute("hero") Hero hero, BindingResult result, RedirectAttributes redirectAttributes, @RequestParam(value = "heroImage", required = false) MultipartFile file) {
        int id = Integer.parseInt(request.getParameter("id"));
        hero = heroDao.getHeroById(id);
        String[] formDataArray = request.getParameterValues("superpower");
        List<Superpower> superpowers = new ArrayList<>();
        if (formDataArray != null) {
            for (String superpowerID : formDataArray) {
                superpowers.add(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerID)));
            }
        }
        hero.setHeroName(request.getParameter("heroName"));
        hero.setHeroDescription(request.getParameter("heroDescription"));
        hero.setVillain(Boolean.valueOf(request.getParameter("villain")));
        hero.setSuperpowers(superpowers);

        if (file != null && !file.isEmpty()) {
            try {
                hero.setHeroImage(file.getBytes());
            } catch (IOException ex) {
                FieldError error = new FieldError("hero", "heroImage", ex.getMessage());
                result.addError(error);
            }
        } else {
            hero.setHeroImage(hero.getHeroImage());
        }

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);

        if (violations.isEmpty()) {
            heroDao.updateHero(hero);
        } else {
            redirectAttributes.addFlashAttribute("errors", violations);
            return "redirect:/heroes/editHero?id=" + hero.getHeroID();
        }
        return "redirect:/heroes";
    }
}
