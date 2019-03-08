/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.ui;

import com.vendingmachine.daos.FileDao;
import com.vendingmachine.daos.VendingAuditDao;
import com.vendingmachine.daos.VendingAuditFileDao;
import com.vendingmachine.daos.VendingDao;
import com.vendingmachine.daos.VendingPersistenceException;
import com.vendingmachine.services.VendingService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author afahrenkamp
 */
public class Program {

    public static void main(String[] args) throws VendingPersistenceException {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingService service = ctx.getBean("serviceLayer", VendingService.class);
        MainMenu mainMenu = ctx.getBean("mainMenu", MainMenu.class);
        mainMenu.run(service);
    }

}
