/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gskela.superhero.controller;

import gskela.superhero.dao.HeroDAO;
import gskela.superhero.dao.LocationDAO;
import gskela.superhero.dto.Hero;
import gskela.superhero.dto.Location;
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
public class LocationController {

    @Autowired
    LocationDAO locationDAO;

    @Autowired
    HeroDAO heroDAO;

    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDAO.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String name = request.getParameter("locationName");
        String latitude = request.getParameter("locationLatitude");
        String longitude = request.getParameter("locationLongitude");
        String description = request.getParameter("locationDescription");
        String address = request.getParameter("locationAddress");
        Location location = new Location();
        location.setLocationName(name);
        location.setLocationLatitude(latitude);
        location.setLocationLongitude(longitude);
        location.setLocationDescription(description);
        location.setLocationAddress(address);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);
        if (violations.isEmpty()) {
            locationDAO.addLocation(location);
        } else {
            redirectAttributes.addFlashAttribute("errors", violations);
        }
        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationDAO.deleteLocation(id);
        return "redirect:/locations";
    }

    @GetMapping("locations/editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDAO.getLocationById(id);
        model.addAttribute("location", location);
        return "locations/editLocation";
    }

    @PostMapping("locations/editLocation")
    public String performEditLocation(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDAO.getLocationById(id);
        location.setLocationName(request.getParameter("locationName"));
        location.setLocationDescription(request.getParameter("locationDescription"));
        location.setLocationLatitude(request.getParameter("locationLatitude"));
        location.setLocationLongitude(request.getParameter("locationLongitude"));
        location.setLocationAddress(request.getParameter("locationAddress"));
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);
        if (violations.isEmpty()) {
            locationDAO.updateLocation(location);
        } else {
            redirectAttributes.addFlashAttribute("errors", violations);
            return "redirect:/locations/editLocation?id=" + location.getLocationID();
        }
        
        return "redirect:/locations";
    }

    @GetMapping("locations/locationDetails")
    public String displayDetailsLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDAO.getLocationById(id);
        model.addAttribute("location", location);
        List<Hero> heroes = heroDAO.getHeroesByLocation(location);
        model.addAttribute("heroes", heroes);
        return "locations/locationDetails";
    }

    @GetMapping("locations/locationDetails_1")
    public String displayDetailsLocation_1(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDAO.getLocationById(id);
        model.addAttribute("location", location);
        List<Hero> heroes = heroDAO.getHeroesByLocation(location);
        model.addAttribute("heroes", heroes);
        return "locations/locationDetails_1";
    }
}
