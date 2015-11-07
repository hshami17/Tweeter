package group.project;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import java.io.*;
import java.util.*;

public class frmRegister {

    private static TextField txtUsername;
    private static TextField txtPassword;

    /**
     * Display the "New Account" window
     */
    public static void display(){
        // Create new window
        Stage window = new Stage();
        window.setTitle("New Account");
        window.setResizable(false);
        // Disallow inputs to parent window
        window.initModality(Modality.APPLICATION_MODAL);

        // Create a new grid pane layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 20));
        grid.setHgap(10);
        grid.setVgap(8);

        // Create new label for the title
        Label lblTitle = new Label("Enter new account information");
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 17));
        GridPane.setConstraints(lblTitle, 0, 0);
        GridPane.setColumnSpan(lblTitle, 3);

        // Username text field label
        Label lblUsername = new Label("Username:");
        GridPane.setConstraints(lblUsername, 0, 1);

        // Password text field label
        Label lblPassword = new Label("Password:");
        GridPane.setConstraints(lblPassword, 0, 2);

        // Create a text field for username input
        txtUsername = new TextField();
        txtUsername.setMaxWidth(150);
        txtUsername.setPromptText("Enter New Username");
        GridPane.setConstraints(txtUsername, 1, 1);

        // Create a text field for password input
        txtPassword = new PasswordField();
        txtPassword.setMaxWidth(150);
        txtPassword.setPromptText("Enter New Password");
        GridPane.setConstraints(txtPassword, 1, 2);

        // Create register button and have it validate inputs when pressed
        Button btnSignUp = new Button("Sign up");
        btnSignUp.setOnAction(event -> {
            try {
                if (txtUsername.getText().trim().isEmpty() ||
                        txtPassword.getText().trim().isEmpty()) {
                    // Prompt user that a field is empty
                    AlertBox.display("Invalid", "Please enter a valid username and/or password", 325, 100);
                } else if (validAccount()) {
                    // Insert new user info into text file
                    FileWriter fileWriter = new FileWriter("UserInfo.txt", true);
                    BufferedWriter out = new BufferedWriter(fileWriter);
                    out.newLine();
                    out.write(txtUsername.getText().trim() + " " +
                            txtPassword.getText().trim());
                    out.close();
                    window.close();
                } else
                    // Prompt user that the username already exists
                    AlertBox.display("Invalid", "The username is already taken!", 220, 100);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btnSignUp.defaultButtonProperty().bind(btnSignUp.focusedProperty());
        btnSignUp.setFont(Font.font("Helvetica", 14));
        GridPane.setConstraints(btnSignUp, 1, 3);

        // Add controls to layout and setup the window scene
        grid.getChildren().addAll(lblTitle, lblUsername, lblPassword, txtUsername, txtPassword, btnSignUp);
        Scene scene = new Scene(grid, 300, 155);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Validate the username and password input to
     * make sure new info creates a valid account.
     * @return True or false depending on if new info was valid
     * @throws IOException
     */
    public static boolean validAccount() throws IOException{
        Scanner file = new Scanner(new File("UserInfo.txt"));
        // Make sure username does not exist
        while (file.hasNext()){
            String username = file.next();
            if (txtUsername.getText().trim().equals(username))
                // Username exists so new info is invalid
                return false;
            // Skip the password string
            String skip = file.next();
        }
        return true;
    }
}


