package group.project;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class frmFindBuddies {
    private static BorderPane borderPane;
    private static VBox centerPane;

    /**
     * Display Find Buddies window
     */
    public static void display() {
        // Create new window
        Stage window = new Stage();
        window.setTitle("Find Buddies");
        window.setResizable(false);

        // Disallow inputs from parent window
        window.initModality(Modality.APPLICATION_MODAL);

        // Create a new border pane
        borderPane = new BorderPane();
        // Create top pane layout
        HBox topPane = new HBox(10);
        topPane.setAlignment(Pos.CENTER);
        topPane.setStyle("-fx-background-color: #BF9393");
        topPane.setPadding(new Insets(3, 3, 15, 0));
        // Create bottom pane layout
        HBox bottomPane = new HBox(10);
        bottomPane.setAlignment(Pos.CENTER_RIGHT);
        bottomPane.setStyle("-fx-background-color: #A0D2F7");
        bottomPane.setPadding(new Insets(10, 3, 3, 0));

        // Create the title text
        Text txtTitle = new Text("Find Buddies");
        txtTitle.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 30));
        txtTitle.setFill(Color.web("#CBE5FF"));
        txtTitle.setTranslateY(5);

        // Create the close button to close the window
        Button btnClose = new Button("Back to Home");
        btnClose.setOnAction(event -> {
            window.close();
            frmHomePage.getAllPublicPosts();
        });
        btnClose.defaultButtonProperty().bind(btnClose.focusedProperty());
        btnClose.setTranslateX(-5);
        btnClose.setTranslateY(-5);
        btnClose.setFont(Font.font("Helvetica", 15));

        // Insert controls into their layouts and setup border pane
        bottomPane.getChildren().add(btnClose);
        topPane.getChildren().add(txtTitle);
        borderPane.setTop(topPane);
        borderPane.setBottom(bottomPane);

        // Get all the users
        UserRepository.sortUserRepoByUsername();
        getAllUsers();

        // Create the scene and display the window
        Scene scene = new Scene(borderPane, 450, 450);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Get all of the currently registered users in the system
     */
    private static void getAllUsers(){
        centerPane = new VBox(10);
        ScrollPane scrollPane = new ScrollPane();
        centerPane.setPadding(new Insets(5, 5, 5, 5));
        centerPane.setStyle("-fx-background-color: #EFF2FB");
        for (int i=0; i<UserRepository.getRepoSize(); i++){
            User user = UserRepository.getUser(i);
            if (!user.getUsername().equals(Profile.username)) {
                // Get the username
                Text txtUsername = new Text(user.getUsername());
                txtUsername.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

                // Create view profile button to display user's profile
                Button btnViewProfile = new Button("View Profile");
                btnViewProfile.setOnAction(event -> frmUserProfile.display(user));
                btnViewProfile.defaultButtonProperty().bind(btnViewProfile.focusedProperty());
                btnViewProfile.setFont(Font.font("Helvetica", 12));

                // Create line to divide posts
                Line divider = new Line(0, 100, 421, 100);
                divider.setStroke(Color.LIGHTGRAY);

                centerPane.getChildren().addAll(txtUsername, btnViewProfile, divider);
            }
        }
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        scrollPane.setContent(centerPane);
        borderPane.setCenter(scrollPane);
    }
}
