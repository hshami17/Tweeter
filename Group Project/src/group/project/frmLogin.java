package group.project;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class frmLogin extends Application {

    private static TextField txtUsername;
    private static PasswordField txtPassword;
    public static boolean exploreMode;

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create the user repository
        UserRepository.populateUserRepository();
        // Create the post repository
        PostRepository.populatePostRepository();
        // Display the login form
        display();
    }

    /**
     * Display the Tweeter Login window
     */
    public static void display(){
        //Setup the window
        Stage window = new Stage();
        window.setTitle("Tweeter Login");
        window.setResizable(false);

        // Create a new Grid Pane layout
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: #F2F9FF");
        // Insets - constrains use (top, right, bottom, left)
        grid.setPadding(new Insets(20, 10, 10, 15));
        // Set the horizontal and vertical gap between controls
        grid.setVgap(8);
        grid.setHgap(10);

        // Create and set title label
        Label lblTitle = new Label("Tweeter");
        lblTitle.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 28));
        lblTitle.setTextFill(Color.web("#4396CC"));
        lblTitle.setPadding(new Insets(0, 0, 10, 55));
        GridPane.setConstraints(lblTitle, 0, 0);
        GridPane.setHalignment(lblTitle, HPos.CENTER);
        GridPane.setColumnSpan(lblTitle, 3);

        // Name Label - constrains use (child, column, row)
        Label lblUserName = new Label("Username:");
        lblUserName.setFont(Font.font("Helvetica", 14));
        GridPane.setConstraints(lblUserName, 0, 1);

        // Name text field
        txtUsername = new TextField();
        txtUsername.setText("hshami");
        txtUsername.setFont(Font.font("Helvetica", 18));
        GridPane.setConstraints(txtUsername, 1, 1);
        GridPane.setColumnSpan(txtUsername, 2);

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

        // Password Label
        Label lblPassword = new Label("Password:");
        lblPassword.setFont(Font.font("Helvetica", 14));
        GridPane.setConstraints(lblPassword, 0, 2);

        // Password text field
        txtPassword = new PasswordField();
        txtPassword.setText("toilet");
        txtPassword.setFont(Font.font(18));
        GridPane.setConstraints(txtPassword, 1, 2);
        GridPane.setColumnSpan(txtPassword, 2);

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

        // Make login button and handle text validation when pressed
        Button btnLogin = new Button("Log in");
        btnLogin.setOnAction(event -> {
            try {
                if (validateUserInfo()) {
                    window.close();
                    // Clear current profile info
                    Profile.clear();
                    // Get the username from text field and get current user likes from file
                    Profile.username = txtUsername.getText().trim();
                    Profile.password = txtPassword.getText().trim();
                    Profile.retrieveLikes();
                    Profile.retrieveTaggedPosts();
                    exploreMode = false;
                    frmHomePage.display();
                } else
                    AlertBox.display("Login Failed", "Wrong username and/or password!", 250, 100);
            } catch (FileNotFoundException ex) {
                System.err.println("User info file not found.");
            }
        });
        btnLogin.defaultButtonProperty().bind(btnLogin.focusedProperty());
        btnLogin.setFont(Font.font("Helvetica", 15));
        GridPane.setConstraints(btnLogin, 1, 3);

        // Make register button open new window to create account when pressed
        Button btnSignUp = new Button("Sign up");
        btnSignUp.setOnAction(event -> frmRegister.display());
        btnSignUp.defaultButtonProperty().bind(btnSignUp.focusedProperty());
        btnSignUp.setFont(Font.font("Helvetica", 15));
        GridPane.setConstraints(btnSignUp, 2, 3);


        // Create explore button to preview Tweeter
        Button btnExplore = new Button("Explore");
        btnExplore.setOnAction(event -> {
            exploreMode = true;
            Profile.clear();
            window.close();
            frmHomePage.display();
        });
        btnExplore.defaultButtonProperty().bind(btnExplore.focusedProperty());
        btnExplore.setFont(Font.font("Helvetica", 15));
        GridPane.setConstraints(btnExplore, 0, 3);


        // Add Tweeter icon to the form
        Image image = new Image("fat-twitter.png");
        ImageView icnTweeter = new ImageView();
        icnTweeter.setImage(image);
        icnTweeter.setFitWidth(80);
        icnTweeter.setPreserveRatio(true);
        icnTweeter.setSmooth(true);
        icnTweeter.setCache(true);
        icnTweeter.setTranslateY(-30);
        icnTweeter.setTranslateX(15);
        GridPane.setConstraints(icnTweeter, 0, 0);
        GridPane.setRowSpan(icnTweeter, 2);

        // Set control re-alignments to make display cleaner
        lblTitle.setTranslateX(5);
        lblUserName.setTranslateY(-2);
        lblPassword.setTranslateY(4);
        txtUsername.setTranslateY(-2);
        txtPassword.setTranslateY(4);
        btnLogin.setTranslateY(10);
        btnSignUp.setTranslateY(10);
        btnExplore.setTranslateY(10);
        btnExplore.setTranslateX(-3);

        //Add everything to grid
        grid.getChildren().addAll(icnTweeter, lblTitle, lblUserName, txtUsername, lblPassword,
                txtPassword, btnLogin, btnSignUp, btnExplore);

        Scene scene = new Scene(grid, 280, 200);
        window.setScene(scene);
        window.show();
    }

    /**
     * Validate the username and password input by searching to check
     * if the user exists as a registered user.
     * @return True or false if the user information was found
     * @throws FileNotFoundException
     */
    private static boolean validateUserInfo() throws FileNotFoundException{
        Scanner file = new Scanner(new File("LoginInfo.txt"));
        while(file.hasNext()){
            if((txtUsername.getText().trim().equals(file.next())) &&
                    txtPassword.getText().trim().equals(file.next())) {
                return true;
            }
        }
        return false;
    }
}