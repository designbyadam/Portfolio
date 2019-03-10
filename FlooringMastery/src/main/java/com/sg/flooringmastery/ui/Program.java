/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dao.OrdersAuditDao;
import com.sg.flooringmastery.dao.OrdersAuditFileDao;
import com.sg.flooringmastery.dao.OrdersDao;
import com.sg.flooringmastery.dao.OrdersFileDao;
import com.sg.flooringmastery.dao.ProductsDao;
import com.sg.flooringmastery.dao.ProductsFileDao;
import com.sg.flooringmastery.dao.TaxDao;
import com.sg.flooringmastery.dao.TaxFileDao;
import com.sg.flooringmastery.service.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author afahrenkamp
 */
public class Program {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        OrderService service = ctx.getBean("serviceLayer", OrderService.class);
        MainMenu mainMenu = ctx.getBean("mainMenu", MainMenu.class);
        mainMenu.run(service);
    }
}
