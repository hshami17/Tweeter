package group.project;

import javafx.beans.value.ObservableValue;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.beans.value.ChangeListener;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class frmEditProfile {
     private static Stage window;

     public static void display(){
         // Create new window
         window = new Stage();
         window.setTitle("Edit Profile");
         window.setResizable(false);
         // Disallow inputs to parent window
         window.initModality(Modality.APPLICATION_MODAL);

         // Handle close event when x button pressed
         window.setOnCloseRequest(event -> {
             event.consume();
             closeWindow();
         });

         // Create a new grid pane layout
         GridPane grid = new GridPane();
         grid.setStyle("-fx-background-color: #F2F9FF");
         grid.setPadding(new Insets(10, 10, 10, 10));
         grid.setHgap(10);
         grid.setVgap(8);

         // Create window title
         Text lblTitle = new Text("Edit Profile");
         lblTitle.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 20));
         lblTitle.setTranslateX(10);
         GridPane.setHalignment(lblTitle, HPos.CENTER);
         GridPane.setColumnSpan(lblTitle, 4);
         GridPane.setConstraints(lblTitle, 0, 0);

         // Username text field label
         Label lblUsername = new Label("Username:");
         lblUsername.setFont(Font.font("Helvetica", 13));
         GridPane.setConstraints(lblUsername, 0, 1);

         // Password text field label
         Label lblOldPassword = new Label("Old Password:");
         lblOldPassword.setFont(Font.font("Helvetica", 13));
         GridPane.setConstraints(lblOldPassword, 0, 2);

         Label lblNewPassword = new Label("New Password");
         lblNewPassword.setFont(Font.font("Helvetica", 13));
         GridPane.setConstraints(lblNewPassword, 0, 3);

         // Create a text field for username input
         TextField txtUsername = new TextField(Profile.username);
         txtUsername.setMaxWidth(150);
         txtUsername.setFont(Font.font("Helvetica", 13));
         GridPane.setColumnSpan(txtUsername, 2);
         GridPane.setConstraints(txtUsername, 1, 1);

         // Set 11 character limit on username field
         int maxSizeUserTxt = 11;
         ChangeListener<String> changeListenerUser = new ChangeListener<String>() {
             @Override
             public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 if (newValue.length() > maxSizeUserTxt){
                     txtUsername.setText(txtUsername.getText(0, maxSizeUserTxt));
                 }
             }
         };
         txtUsername.textProperty().addListener(changeListenerUser);

         // Create a text field for old password input
         TextField txtOldPassword = new PasswordField();
         txtOldPassword.setMaxWidth(150);
         GridPane.setColumnSpan(txtOldPassword, 2);
         GridPane.setConstraints(txtOldPassword, 1, 2);

         // Set 11 character limit on old password field
         int maxSizePassTxt = 11;
         ChangeListener<String> changeListenerPass = new ChangeListener<String>() {
             @Override
             public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 if (newValue.length() > maxSizePassTxt){
                     txtOldPassword.setText(txtOldPassword.getText(0, maxSizePassTxt));
                 }
             }
         };
         txtOldPassword.textProperty().addListener(changeListenerPass);

         // Create a text field for new password input
         TextField txtNewPassword = new PasswordField();
         txtNewPassword.setMaxWidth(150);
         GridPane.setColumnSpan(txtNewPassword, 2);
         GridPane.setConstraints(txtNewPassword, 1, 3);

         // Set 11 character limit on new password field
         ChangeListener<String> changeListenerPass2 = new ChangeListener<String>() {
             @Override
             public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 if (newValue.length() > maxSizePassTxt){
                     txtNewPassword.setText(txtNewPassword.getText(0, maxSizePassTxt));
                 }
             }
         };
         txtNewPassword.textProperty().addListener(changeListenerPass2);

         // Create radio buttons for gender selection
         ToggleGroup group = new ToggleGroup();
         RadioButton rbMale = new RadioButton("Male");
         rbMale.setToggleGroup(group);
         rbMale.setFont(Font.font("Helvetica", 13));
         RadioButton rbFemale = new RadioButton("Female");
         rbFemale.setToggleGroup(group);
         rbFemale.setFont(Font.font("Helvetica", 13));
         GridPane.setConstraints(rbMale, 1, 4);
         GridPane.setConstraints(rbFemale, 2, 4);
         // Set initial state of gender radio button
         if (UserRepository.getUser(Profile.username).getGender().equals("Male"))
             rbMale.setSelected(true);
         else
            rbFemale.setSelected(true);

         // Create new label for age text field
         Label lblAge = new Label("Age:");
         lblAge.setFont(Font.font("Helvetica", 13));
         GridPane.setHalignment(lblAge, HPos.RIGHT);
         GridPane.setConstraints(lblAge, 0, 5);

         // Create new text field for age input
         TextField txtAge = new TextField(UserRepository.getUser(Profile.username).getAge());
         txtAge.setMaxWidth(50);
         txtAge.setFont(Font.font("Helvetica", 13));
         GridPane.setConstraints(txtAge, 1, 5);

         // Set 3 character limit on age field
         int maxSizeAgeTxt = 3;
         ChangeListener<String> changeListenerAge = new ChangeListener<String>() {
             @Override
             public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 if (newValue.length() > maxSizeAgeTxt){
                     txtAge.setText(txtAge.getText(0, maxSizeAgeTxt));
                 }
             }
         };
         txtAge.textProperty().addListener(changeListenerAge);

         // Create a label for the bio text area
         Label lblBio = new Label("Bio:");
         lblBio.setFont(Font.font("Helvetica", 13));
         GridPane.setHalignment(lblBio, HPos.RIGHT);
         GridPane.setConstraints(lblBio, 0, 6);

         // Create a text area for the bio
         TextArea txtBio = new TextArea(UserRepository.getUser(Profile.username).getUserBio().trim());
         txtBio.setPrefSize(220, 150);
         txtBio.setWrapText(true);
         txtBio.setFont(Font.font("Helvetica", 14));
         GridPane.setColumnSpan(txtBio, 3);
         GridPane.setConstraints(txtBio, 1, 6);

         // Set 50 character limit on bio field
         int maxSizeBioTxt = 50;
         ChangeListener<String> changeListenerBio = new ChangeListener<String>() {
             @Override
             public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                 if (newValue.length() > maxSizeBioTxt){
                     txtBio.setText(txtBio.getText(0, maxSizeBioTxt));
                 }
             }
         };
         txtBio.textProperty().addListener(changeListenerBio);

         // Create register button and have it validate inputs when pressed
         Button btnSave = new Button("Save");
         btnSave.setOnAction(event -> {
             try {
                 boolean invalidUsername = false;
                 if (!txtUsername.getText().trim().equals(Profile.username)) {
                     // Make sure username does not exist
                     Scanner file = new Scanner(new File("LoginInfo.txt"));
                     while (file.hasNext()) {
                         String username = file.next();
                         if (txtUsername.getText().trim().equals(username))
                             invalidUsername = true;
                         // Skip the password string
                         String skip = file.next();
                     }
                 }
                 if (invalidUsername) {
                     AlertBox.display("Invalid", "This username is already taken!", 220, 100);
                 } else {
                     // Check for correct password if password entered
                     if (!txtOldPassword.getText().trim().equals(Profile.password) &&
                             !txtOldPassword.getText().trim().isEmpty())
                         AlertBox.display("Invalid", "Incorrect password", 200, 100);
                         // Make sure a new password was given if attempting to change
                     else if (!txtOldPassword.getText().trim().isEmpty() && txtNewPassword.getText().trim().isEmpty())
                         AlertBox.display("Invalid", "Please enter new password", 250, 100);
                         // Make sure old and new password are not equal
                     else if (txtOldPassword.getText().trim().equals(txtNewPassword.getText().trim()) &&
                             (!txtOldPassword.getText().trim().isEmpty() && !txtNewPassword.getText().trim().isEmpty()))
                         AlertBox.display("Invalid", "Please make sure new password is different", 270, 100);
                         // Make sure old password was entered if changing
                     else if (!txtNewPassword.getText().trim().isEmpty() && txtOldPassword.getText().trim().isEmpty())
                         AlertBox.display("Invalid", "Please enter the old password", 250, 100);
                         // Update the user info
                     else {
                         User editedUser = UserRepository.getUser(Profile.username);
                         // Store the old username
                         String oldUsername = Profile.username;

                         // Set new password if successfully changed
                         if (txtOldPassword.getText().trim().equals(Profile.password) &&
                                 (!txtOldPassword.getText().trim().isEmpty() && !txtOldPassword.getText().trim().isEmpty()) &&
                                 !txtOldPassword.getText().trim().equals(txtNewPassword.getText().trim()))
                             Profile.password = txtNewPassword.getText().trim();
                         // Change the username
                         editedUser.setUsername(txtUsername.getText().trim());
                         Profile.username = txtUsername.getText().trim();
                         // Set new gender if changed
                         if (rbMale.isSelected())
                             editedUser.setGender("Male");
                         else
                             editedUser.setGender("Female");
                         // Set the new age
                         editedUser.setAge(txtAge.getText().trim());
                         // Set the new user bio
                         editedUser.setUserBio(txtBio.getText().trim());

                         // Save all users to reflect any changes
                         UserRepository.saveAllUsers();
                         // Update user name if it was changed
                         FileUpdater.updateUsername(oldUsername, Profile.username);

                         window.close();
                     }
                 }
             } catch (IOException ex) {
                 ex.printStackTrace();
             }
         });
         btnSave.defaultButtonProperty().bind(btnSave.focusedProperty());
         btnSave.setFont(Font.font("Helvetica", 12));
         GridPane.setConstraints(btnSave, 0, 7);
         btnSave.setTranslateX(20);

         // Create new button to cancel registration when pressed
         Button btnCancel = new Button("Cancel");
         btnCancel.setOnAction(event -> closeWindow());
         btnCancel.setFont(Font.font("Helvetica", 12));
         btnCancel.defaultButtonProperty().bind(btnCancel.focusedProperty());
         GridPane.setConstraints(btnCancel, 1, 7);
         btnCancel.setTranslateX(-25);

         // Create delete button to delete user's account when pressed
         Button btnDeleteAccount = new Button("Delete Account");
         btnDeleteAccount.setOnAction(event -> {
             ConfirmBox.display("Delete Account", "This cannot be undone, Are you sure you want to delete your account",
                     300, 110);
             if (ConfirmBox.result) {
                 UserRepository.removeUser(Profile.username);
                 UserRepository.saveAllUsers();
                 FileUpdater.deleteUserFromFiles(Profile.username);

                 AlertBox.display("Delete Complete", "You will now be returned to the login screen", 250, 100);
                 window.close();
                 frmHomePage.window.close();
                 frmLogin.display();
             }
         });
         btnDeleteAccount.setFont(Font.font("Helvetica", 12));
         btnDeleteAccount.defaultButtonProperty().bind(btnDeleteAccount.focusedProperty());
         btnDeleteAccount.setTranslateX(-25);
         btnDeleteAccount.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
         GridPane.setConstraints(btnDeleteAccount, 2, 7);
         GridPane.setColumnSpan(btnDeleteAccount, 2);



         // Add controls to layout and setup the window scene
         grid.getChildren().addAll(lblTitle, lblUsername, lblOldPassword, lblNewPassword, lblBio, lblAge,
                 txtUsername, txtOldPassword, txtNewPassword, rbMale, rbFemale, txtAge,
                 txtBio, btnSave, btnCancel, btnDeleteAccount);
         Scene scene = new Scene(grid, 280, 330);
         window.setScene(scene);
         window.showAndWait();
    }

    private static void closeWindow(){
        ConfirmBox.display("Cancel Edit", "Any unsaved changes will be lost, are you sure you want to cancel?",
                320, 100);
        if (ConfirmBox.result)
            window.close();
    }
}