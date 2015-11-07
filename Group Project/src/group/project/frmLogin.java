import javafx.application.Application;
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

    private Stage frmLogin;
    private TextField txtUserName;
    private PasswordField txtPassword;

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception {
        frmLogin = primaryStage;
        frmLogin.setTitle("Tweeter Login");
        frmLogin.setResizable(false);

        // GridPane with 10px padding around edge
        GridPane grid = new GridPane();
        //grid.setGridLinesVisible(true);
        // Insets - constrains use (top, right, bottom, left)
        grid.setPadding(new Insets(20, 10, 10, 15));
        // Set the horizontal and vertical gap between controls
        grid.setVgap(8);
        grid.setHgap(10);

        // Create and set title label
        Label lblTitle = new Label("Tweeter");
        lblTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 28));
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
        txtUserName = new TextField();
        txtUserName.setFont(Font.font("Helvetica", 18));
        GridPane.setConstraints(txtUserName, 1, 1);
        GridPane.setColumnSpan(txtUserName, 2);

        // Password Label
        Label lblPassword = new Label("Password:");
        lblPassword.setFont(Font.font("Helvetica", 14));
        GridPane.setConstraints(lblPassword, 0, 2);

        // Password text field
        txtPassword = new PasswordField();
        txtPassword.setFont(Font.font("Helvetica", 18));
        GridPane.setConstraints(txtPassword, 1, 2);
        GridPane.setColumnSpan(txtPassword, 2);

        // Make login button and handle text validation when pressed
        Button btnLogin = new Button("Log in");
        btnLogin.setOnAction(event -> {
            try {
                if (validateUserInfo()) {
                    System.out.println("Login Successful");
                    frmLogin.close();
                } else
                    AlertBox.display("Login Failed", "Wrong username and/or password!", 250, 100);
            } catch (FileNotFoundException ex) {
                System.err.println("User info file not found.");
            }
        });
        btnLogin.defaultButtonProperty().bind(btnLogin.focusedProperty());
        btnLogin.setFont(Font.font("Helvetica", 14));
        GridPane.setConstraints(btnLogin, 1, 3);

        // Make register button open new window to create account when pressed
        Button btnSignUp = new Button("Sign up");
        btnSignUp.setOnAction(event -> {
            frmRegister.display();
        });
        btnSignUp.defaultButtonProperty().bind(btnSignUp.focusedProperty());
        btnSignUp.setFont(Font.font("Helvetica", 14));
        GridPane.setConstraints(btnSignUp, 2, 3);

        // Add Tweeter icon to the form
        Image image = new Image("fat-twitter.png");
        ImageView icnTweeter = new ImageView();
        icnTweeter.setImage(image);
        icnTweeter.setFitWidth(80);
        icnTweeter.setPreserveRatio(true);
        icnTweeter.setSmooth(true);
        icnTweeter.setCache(true);
        icnTweeter.setTranslateY(-30);
        icnTweeter.setTranslateX(-5);
        GridPane.setConstraints(icnTweeter, 0, 0);
        GridPane.setRowSpan(icnTweeter, 2);

        // Set control re-alignments to make display cleaner
        lblUserName.setTranslateY(-5);
        lblPassword.setTranslateY(-5);
        txtUserName.setTranslateY(-5);
        txtPassword.setTranslateY(-5);
        btnLogin.setTranslateY(-2);
        btnSignUp.setTranslateY(-2);

        //Add everything to grid
        grid.getChildren().addAll(icnTweeter, lblTitle, lblUserName, txtUserName, lblPassword, txtPassword, btnLogin, btnSignUp);

        Scene scene = new Scene(grid, 280, 185);
        frmLogin.setScene(scene);
        frmLogin.show();
    }

    /**
     * Validate the username and password input by searching to check
     * if the user exists as a registered user.
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