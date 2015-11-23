package group.project;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.beans.value.ChangeListener;

import java.awt.*;

public class frmPost {

    private static Stage window;
    private static TextArea txtPost;

    /**
     * Display the New Post window
     */
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

        Text txtCharCount = new Text("140 characters");
        txtCharCount.setFont(Font.font("Helvetica", 12));
        txtCharCount.setFill(javafx.scene.paint.Paint.valueOf("#898989"));
        GridPane.setConstraints(txtCharCount, 3, 7);

        // Set 140 character limit on post field
        txtPost = new TextArea();
        ChangeListener<String> changeListener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() > MAX_SIZE){
                    txtPost.setText(txtPost.getText(0, MAX_SIZE));
                }
                txtCharCount.setText(MAX_SIZE-txtPost.getText().length() + " characters");
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
                // Remove any new line instances from the post
                txtPost.setText(txtPost.getText().replaceAll("\\n", " "));
                // Create a new post authored by current user
                Profile.newPost(txtPost.getText().trim(), rbPublic.isSelected());
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
        txtCharCount.setTranslateX(200);
        txtCharCount.setTranslateY(-35);

        // Add all controls to the grid
        grid.getChildren().addAll(txtPost, btnPost, btnCancel, rbPublic, rbPrivate, txtCharCount);

        // Create the scene and display the window
        Scene scene = new Scene(grid, 420, 190);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Handle the close event for the New Post window
     */
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
