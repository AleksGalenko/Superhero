/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gskela.superhero.controller;

import gskela.superhero.dao.SightingDAO;
import gskela.superhero.dto.Hero;
import gskela.superhero.dto.Sighting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author gskela
 */
@Controller
public class IndexPageController {

    @Autowired
    SightingDAO sightingDao;

    @GetMapping("/")
    public String displayPage(Model model) {
        List<Sighting> sightings = sightingDao.getLastSightings();
        model.addAttribute("sightings", sightings);
        return "index";
    }

}
