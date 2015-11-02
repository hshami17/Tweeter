package group.project;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class frmLogin extends Application {

    Stage window;
    TextField txtUserName;
    PasswordField txtPassword;

    public static void main(String[] args) {
        // Calls the start function which is overrided below
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Login");
        window.setResizable(false);

        //GridPane with 10px padding around edge
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(50, 10, 10, 10));
        // Set the horizontal and vertical gap between controls
        grid.setVgap(8);
        grid.setHgap(10);

        //Name Label - constrains use (child, column, row)
        Label lblUserName = new Label("Username:");
        GridPane.setConstraints(lblUserName, 0, 0);

        //Name Input
        txtUserName = new TextField();
        // Set grayed out text
        txtUserName.setPromptText("username");
        GridPane.setConstraints(txtUserName, 1, 0);

        //Password Label
        Label lblPassword = new Label("Password:");
        GridPane.setConstraints(lblPassword, 0, 1);

        //Password Input
        txtPassword = new PasswordField();
        txtPassword.setPromptText("password");
        GridPane.setConstraints(txtPassword, 1, 1);

        // Make login button and handle text validation when pressed
        Button btnLogin = new Button("Log In");
        btnLogin.setOnAction(event -> {
            if (validateUserInfo()) {
                System.out.println("Login Successful");
                window.close();
            } else
                AlertBox.display("Login Failed", "Wrong username and/or password!", 250, 100);
        });
        GridPane.setConstraints(btnLogin, 1, 2);


        //Add everything to grid
        grid.getChildren().addAll(lblUserName, txtUserName, lblPassword, txtPassword, btnLogin);

        Scene scene = new Scene(grid, 300, 200);
        window.setScene(scene);
        window.show();
    }

    public boolean validateUserInfo(){
        if((txtUserName.getText().equals("hshami")) &&
                txtPassword.getText().equals("toilet"))
            return true;
        else
            return false;
    }
}
