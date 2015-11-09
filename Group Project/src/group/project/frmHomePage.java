package group.project;

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

public class frmHomePage {

    private static VBox centerPane;
    private static BorderPane borderPane;

    public static void display(){
        // Create new window
        Stage window = new Stage();
        window.setTitle("Tweeter Home");
        window.setResizable(false);
        // Disallow inputs to parent window
        window.initModality(Modality.APPLICATION_MODAL);

        borderPane = new BorderPane();
        VBox leftPane = new VBox(7);
        HBox topPane = new HBox(10);
        centerPane = new VBox(15);

        getAllPublicPosts();

        Text username = new Text(UserRepository.getUser(Profile.username).getUsername());
        Text gender = new Text(UserRepository.getUser(Profile.username).getGender());
        Text age = new Text(UserRepository.getUser(Profile.username).getAge());
        Text bio = new Text(UserRepository.getUser(Profile.username).getUserBio());

        bio.setWrappingWidth(70);

        Button btnPost = new Button("New Post");
        btnPost.defaultButtonProperty().bind(btnPost.focusedProperty());
        btnPost.setOnAction(event -> frmPost.display());

        Button btnLogOut = new Button("Log out");
        btnLogOut.defaultButtonProperty().bind(btnLogOut.focusedProperty());
        btnLogOut.setOnAction(event -> {
            window.close();
            frmLogin.display();
        });

        topPane.getChildren().addAll(btnPost, btnLogOut);
        topPane.setAlignment(Pos.TOP_RIGHT);

        leftPane.getChildren().addAll(username, gender, age, bio);
        leftPane.setAlignment(Pos.TOP_LEFT);

        centerPane.setAlignment(Pos.TOP_CENTER);

        borderPane.setLeft(leftPane);
        borderPane.setTop(topPane);

        Scene scene = new Scene(borderPane, 600, 600);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void getAllPublicPosts(){
        centerPane = new VBox(15);
        for (int i=PostRepository.getRepoSize()-1; i >= 0; i--){
            Post addPost = PostRepository.getPost(i);
            if (addPost.isPublic()) {
                Text txtPost = new Text(addPost.getMessage());
                txtPost.setWrappingWidth(200);
                centerPane.getChildren().add(txtPost);
            }
        }
        borderPane.setCenter(centerPane);
        centerPane.setAlignment(Pos.TOP_CENTER);
    }
}

