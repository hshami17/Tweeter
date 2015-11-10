package group.project;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
        //grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(8);

        // Create window title
        Text lblTitle = new Text("Enter new account information");
        lblTitle.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 17));
        lblTitle.setTranslateX(10);
        GridPane.setColumnSpan(lblTitle, 4);
        GridPane.setConstraints(lblTitle, 0, 0);

        // Username text field label
        Label lblUsername = new Label("Username:");
        lblUsername.setFont(Font.font("Helvetica", 13));
        GridPane.setConstraints(lblUsername, 0, 1);

        // Password text field label
        Label lblPassword = new Label("Password:");
        lblPassword.setFont(Font.font("Helvetica", 13));
        GridPane.setConstraints(lblPassword, 0, 2);

        // Create a text field for username input
        txtUsername = new TextField();
        txtUsername.setMaxWidth(150);
        txtUsername.setFont(Font.font("Helvetica", 13));
        GridPane.setColumnSpan(txtUsername, 2);
        GridPane.setConstraints(txtUsername, 1, 1);

        // Create a text field for password input
        txtPassword = new PasswordField();
        txtPassword.setMaxWidth(150);
        GridPane.setColumnSpan(txtPassword, 2);
        GridPane.setConstraints(txtPassword, 1, 2);

        // Create radio buttons for gender selection
        ToggleGroup group = new ToggleGroup();
        RadioButton rbMale = new RadioButton("Male");
        rbMale.setToggleGroup(group);
        rbMale.setFont(Font.font("Helvetica", 13));
        RadioButton rbFemale = new RadioButton("Female");
        rbFemale.setToggleGroup(group);
        rbFemale.setFont(Font.font("Helvetica", 13));
        GridPane.setConstraints(rbMale, 1, 3);
        GridPane.setConstraints(rbFemale, 2, 3);

        // Create new label for age text field
        Label lblAge = new Label("Age:");
        lblAge.setFont(Font.font("Helvetica", 13));
        GridPane.setHalignment(lblAge, HPos.RIGHT);
        GridPane.setConstraints(lblAge, 0, 4);

        // Create new text field for age input
        TextField txtAge = new TextField();
        txtAge.setMaxWidth(50);
        txtAge.setFont(Font.font("Helvetica", 13));
        GridPane.setConstraints(txtAge, 1, 4);

        // Create a label for the bio text area
        Label lblBio = new Label("Bio:");
        lblBio.setFont(Font.font("Helvetica", 13));
        GridPane.setHalignment(lblBio, HPos.RIGHT);
        GridPane.setConstraints(lblBio, 0, 5);

        // Create a text area for the bio
        TextArea txtBio = new TextArea();
        txtBio.setPrefSize(220, 150);
        txtBio.setWrapText(true);
        txtBio.setFont(Font.font("Helvetica", 14));
        GridPane.setColumnSpan(txtBio, 3);
        GridPane.setConstraints(txtBio, 1, 5);

        // Set 50 character limit on bio field
        final int MAX_SIZE = 50;
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > MAX_SIZE){
                    txtBio.setText(txtBio.getText(0, MAX_SIZE));
                }
            }
        };
        txtBio.textProperty().addListener(changeListener);

        // Create register button and have it validate inputs when pressed
        Button btnSignUp = new Button("Sign up");
        btnSignUp.setOnAction(event -> {
            try {
                if (txtUsername.getText().trim().isEmpty() ||
                        txtPassword.getText().trim().isEmpty() ||
                        (!rbMale.isSelected() && !rbFemale.isSelected()) ||
                        txtAge.getText().trim().isEmpty() ||
                        txtBio.getText().trim().isEmpty()) {
                    // Prompt user that a field is empty
                    AlertBox.display("Invalid", "Please fill out all of the fields", 220, 100);
                } else if (validAccount()) {
                    // Insert new login info into text file
                    FileWriter file = new FileWriter("LoginInfo.txt", true);
                    BufferedWriter out = new BufferedWriter(file);
                    out.newLine();
                    out.write(txtUsername.getText().trim() + " " +
                            txtPassword.getText().trim());
                    out.close();

                    // Insert new user info into text file
                    FileWriter file2 = new FileWriter("UserInfo.txt", true);
                    out = new BufferedWriter(file2);
                    String gender;
                    if (rbMale.isSelected())
                        gender = rbMale.getText();
                    else
                        gender = rbFemale.getText();
                    out.newLine();
                    out.write(txtUsername.getText().trim() + " " + gender +
                            " " + txtAge.getText().trim() + " " + txtBio.getText());
                    out.close();

                    // Add new user to the repository
                    String username = txtUsername.getText().trim();
                    String age = txtAge.getText().trim();
                    String bio = txtBio.getText();
                    UserRepository.add(new User(username, gender, age, bio));
                    // Close the window
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
        GridPane.setConstraints(btnSignUp, 1, 6);

        // Create new button to cancel registration when pressed
        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> window.close());
        btnCancel.setFont(Font.font("Helvetica", 14));
        btnCancel.defaultButtonProperty().bind(btnCancel.focusedProperty());
        GridPane.setConstraints(btnCancel, 2, 6);


        // Add controls to layout and setup the window scene
        grid.getChildren().addAll(lblTitle, lblUsername, lblPassword, lblBio, lblAge, txtUsername,
                txtPassword, rbMale, rbFemale, txtAge, txtBio, btnSignUp, btnCancel);
        Scene scene = new Scene(grid, 280, 280);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Validate the username and password input to
     * make sure new info creates a valid account.
     * @return True or false depending on if new info was valid
     * @throws IOException
     */
    private static boolean validAccount() throws IOException{
        Scanner file = new Scanner(new File("LoginInfo.txt"));
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


