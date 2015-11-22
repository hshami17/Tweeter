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

        
        // Enter a new username
        
        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Enter your new username.");
        GridPane.setConstraints(txtUsername, 0, 0);
        grid.getChildren().add(txtUsername);
        
        // Enter a new password
        final TextField txtPassword = new TextField();
        txtPassword.setPromptText("Enter your new password.");
        GridPane.setConstraints(txtPassword, 0, 1);
        grid.getChildren().add(txtPassword);

        // Enter a new bio
        final TextField txtBio = new TextField();
        txtBio.setPromptText("Enter your new bio.");
        GridPane.setConstraints(txtBio, 0, 2);
        grid.getChildren().add(txtBio);
        
        final TextField txtGender = new TextField();
        txtGender.setPromptText("Enter your gender.");
        GridPane.setConstraints(txtGender, 0, 3);
        grid.getChildren().add(txtGender);
        
        final TextField txtAge = new TextField();
        txtAge.setPromptText("Enter your new age.");
        GridPane.setConstraints(txtAge, 0, 4);
        grid.getChildren().add(txtAge);

        // Create a cancel button to close the edit window
        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> {
            if (!txtUsername.getText().trim().isEmpty()&&!!txtBio.getText().trim().isEmpty()&&!txtGender.getText().trim().isEmpty()&&!txtAge.getText().trim().isEmpty()) {
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
              
                if (!txtUsername.getText().trim().isEmpty()) {
                System.out.println("Old username is " + UserRepository.getUser(Profile.username).getUsername());
                String newUsername = txtUsername.getText();
                UserRepository.getUser(Profile.username).setUsername(newUsername);
                Profile.username = newUsername;
                System.out.println("New username is " + UserRepository.getUser(Profile.username).getUsername());
                }
                
               /** if(!txtPassword.getText().trim().isEmpty()){
                String newPassword = txtPassword.getText();
                UserRepository.getUser(Profile.username).setPassword(newPassword);
                }
                */
                
                if(!txtBio.getText().trim().isEmpty()){
                String newBio = txtBio.getText();
                UserRepository.getUser(Profile.username).setUserBio(newBio);    
                System.out.println("New bio is " + UserRepository.getUser(Profile.username).getUserBio());
                }
                
                if (!txtGender.getText().trim().isEmpty()) {
                String newGender = txtGender.getText();
                UserRepository.getUser(Profile.username).setGender(newGender);
                System.out.println("New gender is " + UserRepository.getUser(Profile.username).getGender());
                }
                
                if (!txtAge.getText().trim().isEmpty()) {
                String newAge = txtAge.getText();
                UserRepository.getUser(Profile.username).setAge(newAge);
                System.out.println("New age is " + UserRepository.getUser(Profile.username).getAge());
                }
                
                
                ConfirmBox.display("Save Changes to Profile", "Any previous profile details will be overwritten, are you sure you want to continue?",
                        300, 110);
                
                
                if (ConfirmBox.result)
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
        Scene scene = new Scene(grid, 550, 400);
        window.setScene(scene);
        window.showAndWait();
        
        
    }
     
     // public static void saveUsername(){
        //
          
    //}
   
}

