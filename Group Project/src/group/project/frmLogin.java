package group.project;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class frmLogin extends Application {

    private Stage frmLogin;
    private TextField txtUserName;
    private PasswordField txtPassword;

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception {
        frmLogin = primaryStage;
        frmLogin.setTitle("Tweeter Login");
        frmLogin.setResizable(false);

        //GridPane with 10px padding around edge
        GridPane grid = new GridPane();
        //grid.setGridLinesVisible(true);
        // Insets - constrains use (top, right, bottom, left)
        grid.setPadding(new Insets(20, 10, 10, 15));
        // Set the horizontal and vertical gap between controls
        grid.setVgap(8);
        grid.setHgap(10);

        Label lblTitle = new Label("Tweeter Login");
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblTitle.setPadding(new Insets(0, 0, 10, 40));
        GridPane.setConstraints(lblTitle, 0, 0);
        GridPane.setHalignment(lblTitle, HPos.CENTER);
        GridPane.setColumnSpan(lblTitle, 3);

        //Name Label - constrains use (child, column, row)
        Label lblUserName = new Label("Username:");
        GridPane.setConstraints(lblUserName, 0, 1);

        //Name Input
        txtUserName = new TextField();
        // Set grayed out text
        txtUserName.setPromptText("username");
        GridPane.setConstraints(txtUserName, 1, 1);
        GridPane.setColumnSpan(txtUserName, 2);

        //Password Label
        Label lblPassword = new Label("Password:");
        GridPane.setConstraints(lblPassword, 0, 2);

        //Password Input
        txtPassword = new PasswordField();
        txtPassword.setPromptText("password");
        GridPane.setConstraints(txtPassword, 1, 2);
        GridPane.setColumnSpan(txtPassword, 2);

        // Make login button and handle text validation when pressed
        Button btnLogin = new Button("Log In");
        btnLogin.setOnAction(event -> {
            try {
                if (validateUserInfo()) {
                    System.out.println("Login Successful");
                    frmLogin.close();
                } else
                    AlertBox.display("Login Failed", "Wrong username and/or password!", 250, 100);
            }
            catch (FileNotFoundException ex){
                System.err.println("User info file not found.");
            }
        });
        btnLogin.defaultButtonProperty().bind(btnLogin.focusedProperty());
        GridPane.setConstraints(btnLogin, 1, 3);

        // Make register button open new window to create account when pressed
        Button btnRegister = new Button("Register");
        btnRegister.setOnAction(event -> {
            frmRegister.display();
        });
        btnRegister.defaultButtonProperty().bind(btnRegister.focusedProperty());
        GridPane.setConstraints(btnRegister, 2, 3);

        //Add everything to grid
        grid.getChildren().addAll(lblTitle, lblUserName, txtUserName, lblPassword, txtPassword, btnLogin, btnRegister);

        Scene scene = new Scene(grid, 280, 180);
        frmLogin.setScene(scene);
        frmLogin.show();
    }

    /**
     * Validate the username and password input by searching to check
     * if the user exists as a registered user.
     * @author Hasan Shami
     * @return True or false if the user information was found
     * @throws FileNotFoundException
     */
    public boolean validateUserInfo() throws FileNotFoundException{
        Scanner file = new Scanner(new File("UserInfo.txt"));
        while(file.hasNext()){
            if((txtUserName.getText().trim().equals(file.next())) &&
                    txtPassword.getText().trim().equals(file.next()))
                return true;
        }
        return false;
    }
}