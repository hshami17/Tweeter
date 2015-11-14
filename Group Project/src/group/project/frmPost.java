package group.project;

import javafx.beans.value.ObservableValue;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import javafx.beans.value.ChangeListener;
import java.io.*;
import java.util.*;

public class frmPost {

    private static Stage window;
    private static TextArea txtPost;

    public static void display(){
        // Create a new post window
        window = new Stage();
        window.setTitle("New Post");
        window.setResizable(false);

        // Set character limit for post
        final int MAX_SIZE = 140;

        // Disallow inputs to parent window
        window.initModality(Modality.APPLICATION_MODAL);

        window.setOnCloseRequest(event -> {
            event.consume();
            closeWindow();
        });

        // Create a new grid pane layout
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: #DCEEFF");
        grid.setHgap(10);
        grid.setVgap(10);

        // Set 140 character limit on post field
        txtPost = new TextArea();
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > MAX_SIZE){
                    txtPost.setText(txtPost.getText(0, MAX_SIZE));
                }
            }
        };
        txtPost.textProperty().addListener(changeListener);
        txtPost.setFont(Font.font("Helvetica", 17));
        txtPost.setFocusTraversable(false);
        txtPost.setPromptText("Enter Post");
        txtPost.setPrefSize(300, 140);
        txtPost.setWrapText(true);
        GridPane.setRowSpan(txtPost, 6);
        GridPane.setConstraints(txtPost, 3, 0);

        // Create the public and private radio buttons
        ToggleGroup group = new ToggleGroup();
        RadioButton rbPublic = new RadioButton("Public");
        rbPublic.setFont(Font.font("Helvetica", 15));
        rbPublic.setSelected(true);
        rbPublic.setToggleGroup(group);
        GridPane.setConstraints(rbPublic, 4, 3);

        RadioButton rbPrivate = new RadioButton("Private");
        rbPrivate.setFont(Font.font("Helvetica", 15));
        rbPrivate.setToggleGroup(group);
        GridPane.setConstraints(rbPrivate, 4, 5);

        // Create a new post button to post the content and add to repo
        Button btnPost = new Button("Post");
        btnPost.setOnAction(event -> {
            if (txtPost.getText().trim().isEmpty()) {
                AlertBox.display("Invalid", "Please enter content for your post", 250, 100);
            }
            else {
                // Store new post info into variables
                String username = Profile.username;
                // Put new post into the next ID
                PostRepository.currentID++;
                String ID = PostRepository.currentID.toString();
                boolean isPublic = rbPublic.isSelected();
                String isPublicStr = "Public";
                if (!rbPublic.isSelected()) {
                    isPublicStr = "Private";
                }
                String content = txtPost.getText();
                // Create a new post authored by current user
                Profile.newPost(content, isPublic);
                PostRepository.saveAllPosts();

                if (rbPublic.isSelected())
                    frmHomePage.getAllPublicPosts();
                else
                    frmHomePage.getAllPrivatePosts();

                window.close();
            }
        });
        btnPost.defaultButtonProperty().bind(btnPost.focusedProperty());
        btnPost.setFont(Font.font("Helvetica", 15));
        GridPane.setConstraints(btnPost, 3, 7);

        // Create a cancel button to close the post window
        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> closeWindow());
        btnCancel.setFont(Font.font("Helvetica", 15));
        btnCancel.defaultButtonProperty().bind(btnCancel.focusedProperty());
        GridPane.setConstraints(btnCancel, 3, 7);
        btnCancel.setTranslateX(57);


        // Translate X and Y properties for cleaner GUI
        txtPost.setTranslateY(10);
        txtPost.setTranslateX(-10);
        btnPost.setTranslateX(-10);
        btnCancel.setTranslateX(57);
        btnPost.setTranslateY(-5);
        btnCancel.setTranslateY(-5);
        rbPublic.setTranslateX(-10);
        rbPrivate.setTranslateX(-10);

        // Add all controls to the grid
        grid.getChildren().addAll(txtPost, btnPost, btnCancel, rbPublic, rbPrivate);

        // Create the scene and display the window
        Scene scene = new Scene(grid, 420, 190);
        window.setScene(scene);
        window.showAndWait();
    }

    private static void closeWindow(){
        if (!txtPost.getText().trim().isEmpty()) {
            ConfirmBox.display("Cancel Post", "Post content will be lost, are you sure you want to close?",
                    300, 110);
            if (ConfirmBox.result)
                window.close();
        } else
            window.close();
    }
}
