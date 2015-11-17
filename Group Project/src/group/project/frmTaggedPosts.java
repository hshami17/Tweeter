package group.project;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.io.IOException;

public class frmTaggedPosts {

    private static BorderPane borderPane;
    private static VBox centerPane;
    private static ScrollPane scrollPane;

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
    private static void getAllTaggedPosts() {
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
                addPostComponents(taggedPost);
            }
        }
    }

    private static void addPostComponents(Post taggedPost){
        // Create text to display likes
        Text txtLikes = new Text();

        // Set initial state of like update button
        Button btnLikeUpdate = new Button();
        if (Profile.getLikedPost(taggedPost.getMsg_ID()))
            btnLikeUpdate.setText("Unlike");
        else
            btnLikeUpdate.setText("Like");

        // Create button to display follow or delete
        Button btnFollowDelete = new Button();
        // Set initial state of follow/delete button
        if (taggedPost.getAuthor().trim().equals(Profile.username))
            btnFollowDelete.setText("Delete");
        else
            btnFollowDelete.setText("Follow");
        // Set the X position of the follow/delete button
        if (btnLikeUpdate.getText().equals("Like"))
            btnFollowDelete.setTranslateX(55);
        else
            btnFollowDelete.setTranslateX(68);


        // Execute appropriate action depending on button state
        btnFollowDelete.setOnAction(event -> {
            if (btnFollowDelete.getText().equals("Delete")) {
                ConfirmBox.display("Delete Post", "Are you sure you want to delete this post?", 300, 110);
                if (ConfirmBox.result) {
                    PostRepository.deletePost(taggedPost);
                    PostRepository.saveAllPosts();
                    getAllTaggedPosts();
                }
            }
        });
        btnFollowDelete.defaultButtonProperty().bind(btnFollowDelete.focusedProperty());
        btnFollowDelete.setFont(Font.font("Helvetica", 15));
        btnFollowDelete.setTranslateY(-15);


        // Execute appropriate action depending on state of update like button
        btnLikeUpdate.setOnAction(event -> {
            if (btnLikeUpdate.getText().equals("Like")) {
                taggedPost.setLikeCount(taggedPost.getLikeCount() + 1);
                Profile.addLikedPost(taggedPost);
                PostRepository.saveAllPosts();
                btnLikeUpdate.setText("Unlike");
                btnFollowDelete.setTranslateX(68);
                txtLikes.setText(taggedPost.getLikeCount().toString() + " " + "likes");
            } else {
                taggedPost.setLikeCount(taggedPost.getLikeCount() - 1);
                Profile.removeLikedPost(taggedPost.getMsg_ID());
                PostRepository.saveAllPosts();
                btnLikeUpdate.setText("Like");
                btnFollowDelete.setTranslateX(55);
                txtLikes.setText(taggedPost.getLikeCount().toString() + " " + "likes");

            }
        });
        btnLikeUpdate.defaultButtonProperty().bind(btnLikeUpdate.focusedProperty());
        btnLikeUpdate.setFont(Font.font("Helvetica", 15));
        btnLikeUpdate.setTranslateY(20);

        centerPane.getChildren().addAll(btnLikeUpdate, btnFollowDelete);

        // Show likes for a post
        txtLikes.setText(taggedPost.getLikeCount().toString() + " " + "likes");
        txtLikes.setFont(Font.font("Helvetica", 14));
        txtLikes.setTranslateY(-7);
        centerPane.getChildren().add(txtLikes);

        // Create line to divide posts
        Line divider = new Line(0, 100, 420, 100);
        divider.setTranslateY(-10);
        divider.setStroke(Color.LIGHTGRAY);
        centerPane.getChildren().add(divider);

        // Set center pane alignment and color
        centerPane.setAlignment(Pos.TOP_LEFT);
        centerPane.setStyle("-fx-background-color: #EFF2FB");
        // Put the VBox onto the scroll pane and add to the border pane
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        scrollPane.setContent(centerPane);
        borderPane.setCenter(scrollPane);
    }
}

