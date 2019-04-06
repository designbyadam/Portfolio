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
public class DeleteSuperResponse extends Response {
    private Super deletedSuper;

    /**
     * @return the deletedSuper
     */
    public Super getDeletedSuper() {
        return deletedSuper;
    }

    /**
     * @param deletedSuper the deletedSuper to set
     */
    public void setDeletedSuper(Super deletedSuper) {
        this.deletedSuper = deletedSuper;
    }
}
