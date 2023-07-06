/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gskela.superhero.controller;

import gskela.superhero.dao.HeroDAO;
import gskela.superhero.dao.SuperpowerDAO;
import gskela.superhero.dto.Hero;
import gskela.superhero.dto.Superpower;
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
public class SuperpowersController {

    @Autowired
    SuperpowerDAO superpowerDAO;

    @Autowired
    HeroDAO heroDAO;

    Set<ConstraintViolation<Superpower>> violations = new HashSet<>();

    @GetMapping("superpowers")
    public String displaySuperpowers(Model model) {
        List<Superpower> superpowers = superpowerDAO.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        return "superpowers";
    }

    @PostMapping("addSuperpower")
    public String addSuperpower(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String name = request.getParameter("superpowerName");
        String description = request.getParameter("superpowerDescription");
        Superpower superpower = new Superpower();
        superpower.setSuperpowerName(name);
        superpower.setSuperpowerDescription(description);
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superpower);

        if (violations.isEmpty()) {
            superpowerDAO.addSuperpower(superpower);
        } else {
            redirectAttributes.addFlashAttribute("errors", violations);
        }
        return "redirect:/superpowers";
    }

    @GetMapping("deleteSuperpower")
    public String deleteSuperpower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superpowerDAO.deleteSuperpowerById(id);
        return "redirect:/superpowers";
    }

    @GetMapping("superpowers/editSuperpower")
    public String editSuperpower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDAO.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        return "superpowers/editSuperpower";
    }

    @PostMapping("/superpowers/editSuperpower")
    public String performEditSuperpower(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDAO.getSuperpowerById(id);
        superpower.setSuperpowerName(request.getParameter("superpowerName"));
        superpower.setSuperpowerDescription(request.getParameter("superpowerDescription"));
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(superpower);
        if (violations.isEmpty()) {
            superpowerDAO.updateSuperpower(superpower);
        } else {
            redirectAttributes.addFlashAttribute("errors", violations);
            return "redirect:/superpowers/editSuperpower?id=" + superpower.getSuperpowerID();
        }
        return "redirect:/superpowers";
    }

    @GetMapping("superpowers/superpowerDetails")
    public String superpowerDetails(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDAO.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        List<Hero> heroes = heroDAO.getHeroesBySuperpower(superpower);
        model.addAttribute("heroes", heroes);
        return "superpowers/superpowerDetails";
    }

}
