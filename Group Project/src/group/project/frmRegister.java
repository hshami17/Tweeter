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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class frmRegister {

    private static TextField txtUsername;
    private static TextField txtPassword;
    public static boolean success;

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

        success = false;

        // Create a new grid pane layout
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: #F2F9FF");
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


        // Create a text field for password input
        txtPassword = new PasswordField();
        txtPassword.setMaxWidth(150);
        GridPane.setColumnSpan(txtPassword, 2);
        GridPane.setConstraints(txtPassword, 1, 2);

        // Set 11 character limit on password field
        int maxSizePassTxt = 11;
        ChangeListener<String> changeListenerPass = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > maxSizePassTxt){
                    txtPassword.setText(txtPassword.getText(0, maxSizePassTxt));
                }
            }
        };
        txtPassword.textProperty().addListener(changeListenerPass);

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
        GridPane.setConstraints(lblBio, 0, 5);

        // Create a text area for the bio
        TextArea txtBio = new TextArea();
        txtBio.setPrefSize(220, 150);
        txtBio.setWrapText(true);
        txtBio.setFont(Font.font("Helvetica", 14));
        GridPane.setColumnSpan(txtBio, 3);
        GridPane.setConstraints(txtBio, 1, 5);

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
        Button btnSignUp = new Button("Sign up");
        btnSignUp.setOnAction(event -> {
            try {
                // Setup check for special characters
                Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(txtUsername.getText().trim());
                if (txtUsername.getText().trim().isEmpty() ||
                        txtPassword.getText().trim().isEmpty() ||
                        (!rbMale.isSelected() && !rbFemale.isSelected()) ||
                        txtAge.getText().trim().isEmpty() ||
                        txtBio.getText().trim().isEmpty()) {
                    // Prompt user that a field is empty
                    AlertBox.display("Invalid", "Please fill out all of the fields", 220, 100);
                }
                else if (!txtAge.getText().matches("\\d+")){
                    AlertBox.display("Invalid", "Please enter a valid age", 220, 100);
                }
                else if (m.find()){
                    AlertBox.display("Invalid", "Please make sure the username contains no special characters.", 320, 100);
                }
                else if (txtUsername.getText().trim().contains(" ")){
                    AlertBox.display("Invalid", "Please make sure the username contains no spaces.", 320, 100);
                }
                else if (validAccount()) {
                    success = true;
                    // Set the gender string
                    String gender;
                    if (rbMale.isSelected())
                        gender = rbMale.getText();
                    else
                        gender = rbFemale.getText();
                    // Add new user to the repository
                    String username = txtUsername.getText().trim();
                    String age = txtAge.getText().trim();
                    String bio = txtBio.getText();
                    User newUser = new User(username, gender, age, bio);
                    UserRepository.add(newUser);
                    // Add new user to the appropriate files
                    FileUpdater.addNewUserToFiles(newUser, txtPassword.getText().trim());
                    // Close the window
                    window.close();
                }
                else
                    // Prompt user that the username already exists
                    AlertBox.display("Invalid", "This username is already taken!", 220, 100);
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


