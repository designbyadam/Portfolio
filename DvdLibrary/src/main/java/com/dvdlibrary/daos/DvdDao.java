/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvdlibrary.daos;

import com.dvdlibrary.dtos.Dvd;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public interface DvdDao {

    public Dvd addDvd(Dvd toAdd);

    public List<Dvd> listDvds();

    public boolean removeDvd(int id);

    public Dvd getDvdById(int id);

    public Dvd editDvd(Dvd toEdit);

    
}
