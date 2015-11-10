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

    public static void display(){
        // Create a new post window
        Stage window = new Stage();
        window.setTitle("New Post");
        window.setResizable(false);

        // Set character limit for post
        final int MAX_SIZE = 140;

        // Disallow inputs to parent window
        window.initModality(Modality.APPLICATION_MODAL);

        // Create a new grid pane layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Set 140 character limit on post field
        TextArea txtPost = new TextArea();
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > MAX_SIZE){
                    txtPost.setText(txtPost.getText(0, MAX_SIZE));
                }
            }
        };
        txtPost.textProperty().addListener(changeListener);
        txtPost.setFont(Font.font("Helvetica", 14));
        txtPost.setFocusTraversable(false);
        txtPost.setPromptText("Enter Post");
        txtPost.setPrefSize(200, 200);
        txtPost.setWrapText(true);
        GridPane.setRowSpan(txtPost, 4);
        GridPane.setConstraints(txtPost, 1, 0);

        // Create the public and private radio buttons
        ToggleGroup group = new ToggleGroup();
        RadioButton rbPublic = new RadioButton("Public");
        rbPublic.setFont(Font.font("Helvetica", 13));
        rbPublic.setSelected(true);
        rbPublic.setToggleGroup(group);
        rbPublic.setTranslateY(-70);
        GridPane.setConstraints(rbPublic, 2, 3);
        RadioButton rbPrivate = new RadioButton("Private");
        rbPrivate.setFont(Font.font("Helvetica", 13));
        rbPrivate.setToggleGroup(group);
        GridPane.setConstraints(rbPrivate, 2, 3);

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
                /**
                 // Save post to text file
                 FileWriter file = new FileWriter("Post.txt", true);
                 BufferedWriter out = new BufferedWriter(file);
                 out.write("\n" + username + " " + ID + " " + isPublicStr + " " +
                 "0" +  " " + content + "\n");
                 out.close();
                 */

                frmHomePage.getAllPublicPosts();
                window.close();
            }
        });
        btnPost.defaultButtonProperty().bind(btnPost.focusedProperty());
        btnPost.setFont(Font.font("Helvetica", 14));
        GridPane.setConstraints(btnPost, 1, 5);

        // Create a cancel button to close the post window
        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> window.close());
        btnCancel.setFont(Font.font("Helvetica", 14));
        btnCancel.defaultButtonProperty().bind(btnCancel.focusedProperty());
        GridPane.setConstraints(btnCancel, 1, 5);
        btnCancel.setTranslateX(57);


        // Translate X and Y properties for cleaner GUI
        txtPost.setTranslateY(10);
        btnCancel.setTranslateX(57);
        rbPublic.setTranslateY(-70);

        // Add all controls to the grid
        grid.getChildren().addAll(txtPost, btnPost, btnCancel, rbPublic, rbPrivate);

        // Create the scene and display the window
        Scene scene = new Scene(grid, 300, 250);
        window.setScene(scene);
        window.showAndWait();
    }
}
