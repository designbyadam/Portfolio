/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dvdlibrary.ui.controllers;

import com.dvdlibrary.dtos.Dvd;
import com.dvdlibrary.ui.views.RemoveDvdWorkflow;
import com.dvdlibrary.ui.views.ShowDvdWorkflow;
import com.dvdlibrary.ui.views.SearchTitleWorkflow;
import com.dvdlibrary.ui.views.ListDvdWorkflow;
import com.dvdlibrary.ui.views.AddDvdWorkflow;
import com.dvdlibrary.ui.views.EditDvdWorkflow;
import com.dvdlibrary.services.DvdService;
import com.dvdlibrary.ui.ConsoleIO;
import java.util.List;

/**
 *
 * @author afahrenkamp
 */
public class MainMenu {
    
    static ConsoleIO ui = new ConsoleIO();
    
    public static void run(DvdService service){
        
        boolean isDone = false;
        
        
        //1. Allow the user to add a DVD to the collection
        //2. Allow the user to remove a DVD from the collection
        //3. Allow the user to edit the information for an existing DVD in the collection
        //4. Allow the user to list the DVDs in the collection
        //5. Allow the user to display the information for a particular DVD
        //6. Allow the user to search for a DVD by title
        
        //7. Quit
        while( !isDone ){
            int userChoice = getUserChoice();
            
            switch( userChoice ){
                case 1: 
                    AddDvdWorkflow addFlow = new AddDvdWorkflow(service);
                    addFlow.run(ui);
                    break;
                 case 2: 
                    RemoveDvdWorkflow removeFlow = new RemoveDvdWorkflow(service);
                    removeFlow.run(ui);
                    break;
                 case 3: 
                    EditDvdWorkflow editFlow = new EditDvdWorkflow(service);
                    editFlow.run(ui);
                    break;
                case 4: 
                    ListDvdWorkflow listFlow = new ListDvdWorkflow(service);
                    listFlow.run(ui);
                    break;
                case 5: 
                    ShowDvdWorkflow showFlow = new ShowDvdWorkflow(service);
                    showFlow.run(ui);
                    break;
                case 6: 
                    SearchTitleWorkflow searchFlow = new SearchTitleWorkflow(service);
                    searchFlow.run(ui);
                    break;
                case 7: 
                    isDone = true;
                    break;
            }
            
        }
        
        //TODO: add thanks message before quitting
    }

    
            //1. Allow the user to add a DVD to the collection
        //2. Allow the user to remove a DVD from the collection
        //3. Allow the user to edit the information for an existing DVD in the collection
        //4. Allow the user to list the DVDs in the collection
        //5. Allow the user to display the information for a particular DVD
        //6. Allow the user to search for a DVD by title
    private static int getUserChoice() {
        
        ui.print( "\n1. Add DVD\n" );
        ui.print( "2. Remove DVD\n" );
        ui.print( "3. Edit DVD\n" );
        ui.print( "4. List all DVDs\n" );
        ui.print( "5. Show DVD details\n" );
        ui.print( "6. Search for DVD by title\n" );
        ui.print( "7. Quit\n" );
        
        int toReturn = ui.readInt("Please select a choice between 1 and 7: ", 1, 7);
        
        return toReturn;
    }
    
}
