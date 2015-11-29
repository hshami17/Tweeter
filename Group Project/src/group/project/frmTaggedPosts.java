package group.project;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class frmTaggedPosts {

    private static BorderPane borderPane;
    private static VBox centerPane;
    private static ScrollPane scrollPane;

    /**
     * Display the Tagged Posts window
     */
    public static void display(){
        // Create new window
        Stage window = new Stage();
        window.setTitle("Tagged Posts");
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
        Text txtTitle = new Text("Tagged Posts");
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

        // Populate center pane with all tagged posts for current user
        getAllTaggedPosts();

        // Insert controls into their layouts and setup border pane
        bottomPane.getChildren().add(btnClose);
        topPane.getChildren().add(txtTitle);
        borderPane.setTop(topPane);
        borderPane.setBottom(bottomPane);

        // Create the scene and display the window
        Scene scene = new Scene(borderPane, 450, 450);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Get all the posts the currently logged in user was tagged in.
     */
    public static void getAllTaggedPosts() {
        centerPane = new VBox(6);
        scrollPane = new ScrollPane();
        centerPane.setPadding(new Insets(5, 5, 5, 5));
        if (Profile.taggedPostSize() != 0) {
            for (int i=0; i<Profile.taggedPostSize(); i++) {
                Post taggedPost = Profile.getTaggedPost(i);

                // Get the post author
                Text txtAuthor = new Text("@" + taggedPost.getAuthor().trim());
                txtAuthor.setFont(Font.font("Helvetica", FontWeight.BOLD, 17));

                // Get the post
                Text txtPost = new Text(taggedPost.getMessage().trim());
                txtPost.setFont(Font.font("Helvetica", 17));
                txtPost.setWrappingWidth(400);

                centerPane.getChildren().addAll(txtAuthor, txtPost);
                taggedPost.addPostComponents(centerPane, scrollPane, borderPane, txtPost, "Tagged Posts");
            }
        }
    }
}

