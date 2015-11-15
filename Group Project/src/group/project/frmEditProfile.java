/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group.project;

import javafx.beans.value.ObservableValue;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import javafx.beans.value.ChangeListener;
import java.io.*;
import java.util.*;

/**
 *
 * @author abigailmayodell
 */
public class frmEditProfile {
     public static void display(){
        // Create a window to update profile
        Stage window = new Stage();
        window.setTitle("Edit Profile");
        window.setResizable(false);

               


        // Disallow inputs to parent window
        window.initModality(Modality.APPLICATION_MODAL);

        // Create a new grid pane layout
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: #DCEEFF");
        grid.setHgap(10);
        grid.setVgap(10);

        final TextField username = new TextField();
        username.setPromptText("Enter your new username.");
        GridPane.setConstraints(username, 0, 0);
        grid.getChildren().add(username);
        

        final TextField password = new TextField();
        password.setPromptText("Enter your new password.");
        GridPane.setConstraints(password, 0, 1);
        grid.getChildren().add(password);

        final TextField bio = new TextField();
        bio.setPromptText("Enter your new bio.");
        GridPane.setConstraints(bio, 0, 2);
        grid.getChildren().add(bio);

        // Create a cancel button to close the post window
        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> {
            if (!username.getText().trim().isEmpty()) {
                ConfirmBox.display("Cancel Editing Profile", "Any changes to your profile will be lost, are you sure you want to close?",
                        300, 110);
                if (ConfirmBox.result)
                    window.close();
            } else
                window.close();
        });
        btnCancel.setFont(Font.font("Helvetica", 15));
        btnCancel.defaultButtonProperty().bind(btnCancel.focusedProperty());
        GridPane.setConstraints(btnCancel, 3, 7);
        btnCancel.setTranslateX(57);

        
        //Create a save button to save the profile changes
        Button btnSave = new Button("Save Changes");
        btnSave.setOnAction(event -> {
            if (!username.getText().trim().isEmpty()) {
                
                
                ConfirmBox.display("Save Changes to Profile", "Any previous profile details will be overwritten, are you sure you want to continue?",
                        300, 110);
                saveUsername();
                savePassword();
                saveBio();
                
                if (ConfirmBox.result)
                    window.close();
            } else
                window.close();
        });
        
        btnSave.setFont(Font.font("Helvetica", 15));
        btnSave.defaultButtonProperty().bind(btnSave.focusedProperty());
        GridPane.setConstraints(btnSave, 5, 7);
        btnSave.setTranslateX(57);

        // Translate X and Y properties for cleaner GUI
    
        btnSave.setTranslateX(57);
     
        btnSave.setTranslateY(-5);
     

        // Add all controls to the grid
        grid.getChildren().addAll(btnCancel, btnSave);

        // Create the scene and display the window
        Scene scene = new Scene(grid, 550, 190);
        window.setScene(scene);
        window.showAndWait();
        
        
    }
     
      public static void saveUsername(){
        try {
            PrintWriter out = new PrintWriter(new FileWriter("LoginInfo.txt"));
            
            out.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
      public static void savePassword(){
        try {
            PrintWriter out = new PrintWriter(new FileWriter("LoginInfo.txt"));
            
            out.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
      public static void saveBio(){
        try {
            PrintWriter out = new PrintWriter(new FileWriter("UserInfo.txt"));
            
            out.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}

