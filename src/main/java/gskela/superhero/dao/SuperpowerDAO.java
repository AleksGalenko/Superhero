/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gskela.superhero.dao;

import gskela.superhero.dto.Superpower;
import java.util.List;

/**
 *
 * @author gskela
 */
public interface SuperpowerDAO {

    Superpower getSuperpowerById(int id);

    List<Superpower> getAllSuperpowers();

    Superpower addSuperpower(Superpower superpower);

    void updateSuperpower(Superpower superpower);

    void deleteSuperpowerById(int id);

}
