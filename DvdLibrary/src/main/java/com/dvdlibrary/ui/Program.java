/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvdlibrary.ui;

import com.dvdlibrary.ui.controllers.MainMenu;
import com.dvdlibrary.daos.DvdDao;
import com.dvdlibrary.daos.FileDao;
import com.dvdlibrary.services.DvdService;

/**
 *
 * @author afahrenkamp
 */
public class Program {
    public static void main( String[] args ){
        DvdDao dao = new FileDao("/home/designbyadam/Downloads/dvds.txt");
        DvdService service = new DvdService(dao);
        MainMenu.run(service);
    }
}
