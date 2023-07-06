/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gskela.superhero.controller;

import gskela.superhero.dao.HeroDAO;
import gskela.superhero.dao.LocationDAO;
import gskela.superhero.dao.SightingDAO;
import gskela.superhero.dto.Hero;
import gskela.superhero.dto.Location;
import gskela.superhero.dto.Sighting;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
public class SightingController {

    @Autowired
    SightingDAO sightingDao;

    @Autowired
    HeroDAO heroDao;

    @Autowired
    LocationDAO locationDao;

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        model.addAttribute("sightings", sightings);
        List<Hero> heroes = heroDao.getAllHeroes();
        model.addAttribute("heroes", heroes);
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        LocalDate date = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(request.getParameter("sightingDate"), formatter);
        } catch (DateTimeParseException e) {
        }
        Sighting sighting = new Sighting();
        sighting.setSightingDate(date);
        try {
            sighting.setHero(heroDao.getHeroById(Integer.parseInt(request.getParameter("hero"))));
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("errors", violations);
        }
        try {
            sighting.setLocation(locationDao.getLocationById(Integer.parseInt(request.getParameter("location"))));
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("errors", violations);
        }
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        violations = validate.validate(sighting);

        if (violations.isEmpty()) {
            sightingDao.addSighting(sighting);
        } else {
            redirectAttributes.addFlashAttribute("errors", violations);
        }
        return "redirect:/sightings";
    }

    @GetMapping("sightings/editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingByID(id);
        model.addAttribute("sighting", sighting);
        List<Hero> heroes = heroDao.getAllHeroes();
        model.addAttribute("heroes", heroes);
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "sightings/editSighting";
    }

    @PostMapping("sightings/editSighting")
    public String performEditSighting(HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        LocalDate date = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(request.getParameter("sightingDate"), formatter);
        } catch (DateTimeParseException e) {
        }
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingByID(id);
        sighting.setSightingDate(date);
        sighting.setHero(heroDao.getHeroById(Integer.parseInt(request.getParameter("hero"))));
        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(request.getParameter("location"))));
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);
        if (violations.isEmpty()) {
            sightingDao.updateSighting(sighting);
        } else {
            redirectAttributes.addFlashAttribute("errors", violations);
            return "redirect:/sightings/editSighting?id=" + sighting.getSightingID();
        }
        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        sightingDao.deleteSighting(id);
        return "redirect:/sightings";
    }

}
