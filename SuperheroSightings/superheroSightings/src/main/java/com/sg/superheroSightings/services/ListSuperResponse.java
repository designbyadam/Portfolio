/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.services;

import com.sg.superheroSightings.models.Super;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class ListSuperResponse extends Response {
    private List<Super> allSupers;

    /**
     * @return the allSupers
     */
    public List<Super> getAllSupers() {
        return allSupers;
    }

    /**
     * @param allSupers the allSupers to set
     */
    public void setAllSupers(List<Super> allSupers) {
        this.allSupers = allSupers;
    }
}
