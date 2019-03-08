/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vendingmachine.daos;

import com.vendingmachine.dtos.Item;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author afahrenkamp
 */
public class FileDao implements VendingDao {

    String path;

    public FileDao(String path) {
        this.path = path;
    }

    public FileDao() {
        path = "/home/designbyadam/Downloads/vendingitems.txt";
    }

    @Override
    public List<Item> getAllItems() throws VendingPersistenceException {
        List<Item> toReturn = new ArrayList<Item>();
        File vendItemsFile = new File(path);
        Scanner reader;

        if (!vendItemsFile.exists()) {

            throw new VendingPersistenceException("Could not find item file: " + path);
        }

        try {

            reader = new Scanner(new BufferedReader(new FileReader(vendItemsFile)));

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] cells = line.split("::");
                Item toAdd = new Item();
                toAdd.setId(Integer.parseInt(cells[0]));
                toAdd.setName(cells[1]);
                toAdd.setQuantity(Integer.parseInt(cells[2]));
                toAdd.setPriceInPennies(Integer.parseInt(cells[3]));

                toReturn.add(toAdd);
            }
        } catch (FileNotFoundException e) {
            throw new VendingPersistenceException("Could not load item data into memory.");
        }

        return toReturn;
    }

    @Override
    public Item getItemById(int id) throws VendingPersistenceException {
        Item toReturn = null;

        List<Item> allItems;
        try {
            allItems = getAllItems();
            for (Item toCheck : allItems) {
                if (toCheck.getId() == id) {
                    toReturn = toCheck;
                    break;
                }
            }

        } catch (VendingPersistenceException ex) {
            throw new VendingPersistenceException("Could not retrieve item data", ex);
        }

        return toReturn;
    }

    @Override
    public Item vendItem(Item toVend) throws VendingPersistenceException {

        Item toReturn = null;

        List<Item> allItems;

        try {
            allItems = getAllItems();
            int index = Integer.MIN_VALUE;

            for (int i = 0; i < allItems.size(); i++) {
                Item toCheck = allItems.get(i);
                if (toCheck.getId() == toVend.getId()) {
                    index = i;
                    toReturn = toCheck;
                    break;
                }

            }
            allItems.set(index, toVend);

            writeFile(allItems);
        } catch (VendingPersistenceException ex) {
            throw new VendingPersistenceException("Could not retrieve item data", ex);
        }

        return toReturn;
    }

    private boolean writeFile(List<Item> allItems) throws VendingPersistenceException {
        boolean toReturn = false;
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileWriter(path));
        } catch (IOException e) {
            throw new VendingPersistenceException("Could not save item data.", e);
        }
        for (Item toWrite : allItems) {
            String line = itemToLine(toWrite);
            pw.println(line);
        }

        pw.flush();
        pw.close();

        toReturn = true;

        return toReturn;
    }

    private String itemToLine(Item toWrite) {
        String line = toWrite.getId() + "::" + toWrite.getName() + "::"
                + toWrite.getQuantity() + "::" + toWrite.getPriceInPennies();
        return line;
    }

}
