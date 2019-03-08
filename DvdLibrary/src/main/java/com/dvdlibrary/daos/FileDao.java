/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvdlibrary.daos;

import com.dvdlibrary.dtos.Dvd;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author afahrenkamp
 */
public class FileDao implements DvdDao {

    String path;

    public FileDao(String path) {
        this.path = path;
    }

    @Override
    public Dvd addDvd(Dvd toAdd) {

        Dvd toReturn = toAdd;

        List<Dvd> allDvds = listDvds();

        int newId = generateNewId(allDvds);

        toAdd.setId(newId);

        allDvds.add(toAdd);

        boolean success = writeFile(allDvds);

        if (!success) {
            toReturn = null;
        }

        return toReturn;
    }

    @Override
    public List<Dvd> listDvds() {

        List<Dvd> toReturn = new ArrayList<Dvd>();

        try {

            File dvdFile = new File(path);

            if (!dvdFile.exists()) {
                dvdFile.createNewFile();
            }

            Scanner reader = new Scanner(
                    new BufferedReader(
                            new FileReader(dvdFile)));

            //no header row, start reading data immediately
            while (reader.hasNextLine()) {
                //ID::Title::Director::Studio::Rating::Year::Note
                String line = reader.nextLine();

                //TODO: make sure user doesn't enter :: into any data
                String[] cells = line.split("::");

                Dvd toAdd = new Dvd();
                toAdd.setId(Integer.parseInt(cells[0]));
                toAdd.setTitle(cells[1]);
                toAdd.setDirector(cells[2]);
                toAdd.setStudio(cells[3]);
                toAdd.setRating(cells[4]);
                toAdd.setReleaseYear(Integer.parseInt(cells[5]));
                if (cells.length > 6) {
                toAdd.setNote(cells[6]);
                } else {
                    toAdd.setNote("");
                }

                toReturn.add(toAdd);
            }

        } catch (FileNotFoundException ex) {
            toReturn = null;
        } catch (IOException ex) {
            toReturn = null;
        }

        return toReturn;
    }

    @Override
    public boolean removeDvd(int id) {
        //1. get all (current) dvds
        List<Dvd> allDvds = listDvds();

        //2. remove the matching dvd from the list
        int index = Integer.MIN_VALUE;

        for (int i = 0; i < allDvds.size(); i++) {
            Dvd toCheck = allDvds.get(i);
            if (toCheck.getId() == id) {
                //we found a match
                //record the index
                //break the loop
                index = i;
                break;
            }
        }

        allDvds.remove(index);

        //3. write the list out to the file
        boolean success = writeFile(allDvds);

        return success;
    }

    @Override
    public Dvd getDvdById(int id) {
        Dvd toReturn = null;

        List<Dvd> allDvds = listDvds();

        for (Dvd toCheck : allDvds) {
            if (toCheck.getId() == id) {
                toReturn = toCheck;
                break;
            }
        }

        return toReturn;
    }

    @Override
    public Dvd editDvd(Dvd toEdit) {
        Dvd toReturn = null;
        
        List<Dvd> allDvds = listDvds();
        
        //2. remove the matching dvd from the list
        int index = Integer.MIN_VALUE;

        int id = toEdit.getId();
        
        for (int i = 0; i < allDvds.size(); i++) {
            Dvd toCheck = allDvds.get(i);
            if (toCheck.getId() == id) {
                //we found a match
                //record the index
                //break the loop
                index = i;
                break;
            }
        }

        if( index >= 0 && index < allDvds.size()) {
            allDvds.set(index, toEdit);
        
             boolean success = writeFile(allDvds);
            if( success ) {
                toReturn = toEdit;
            }
        }
        


        return toReturn;
    }

    private int generateNewId(List<Dvd> allDvds) {
        //find max id and add 1

        int toReturn = Integer.MIN_VALUE;

        if (allDvds.isEmpty()) {
            toReturn = 0;
        } else {
            for (Dvd toInspect : allDvds) {
                if (toInspect.getId() > toReturn) {
                    toReturn = toInspect.getId();
                }
            }

            toReturn++;
        }

        return toReturn;
    }

    private boolean writeFile(List<Dvd> allDvds) {

        boolean success = false;

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(path));

            for (Dvd toWrite : allDvds) {
                String line = dvdToLine(toWrite);
                pw.println(line);
            }

            pw.flush();
            pw.close();

            success = true;
        } catch (IOException ex) {
            //on failure, leave success as false
        }

        return success;
    }

    private String dvdToLine(Dvd toWrite) {
        //ID::Title::Director::Studio::Rating::Year::Note

        String toReturn
                = toWrite.getId() + "::"
                + toWrite.getTitle() + "::"
                + toWrite.getDirector() + "::"
                + toWrite.getStudio() + "::"
                + toWrite.getRating() + "::"
                + toWrite.getReleaseYear() + "::"
                + toWrite.getNote();

        return toReturn;
    }    

}
