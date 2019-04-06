/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superheroSightings.services;

import com.sg.superheroSightings.models.Super;

/**
 *
 * @author afahrenkamp
 */
public class EditSuperResponse extends Response {
    private Super editedSup;

    /**
     * @return the editedSup
     */
    public Super getEditedSup() {
        return editedSup;
    }

    /**
     * @param editedSup the editedSup to set
     */
    public void setEditedSup(Super editedSup) {
        this.editedSup = editedSup;
    }
}
