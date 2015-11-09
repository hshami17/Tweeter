package group.project;

import javafx.beans.value.ObservableValue;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
        Stage window = new Stage();
        window.setTitle("New Post");
        window.setResizable(false);

        final int MAX_SIZE = 140;

        window.initModality(Modality.APPLICATION_MODAL);

        GridPane grid = new GridPane();
        //grid.setGridLinesVisible(true);
        grid.setHgap(10);
        grid.setVgap(10);

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
        txtPost.setPrefSize(200, 200);
        txtPost.setWrapText(true);
        GridPane.setRowSpan(txtPost, 4);
        GridPane.setConstraints(txtPost, 1, 0);

        ToggleGroup group = new ToggleGroup();
        RadioButton rbPublic = new RadioButton("Public");
        rbPublic.setSelected(true);
        rbPublic.setToggleGroup(group);
        rbPublic.setTranslateY(-70);
        GridPane.setConstraints(rbPublic, 2, 3);
        RadioButton rbPrivate = new RadioButton("Private");
        rbPrivate.setToggleGroup(group);
        GridPane.setConstraints(rbPrivate, 2, 3);

        Button btnPost = new Button("Post");
        btnPost.setOnAction(event -> {
            try {
                // Store new post info into variables
                String username = Profile.username;
                String ID = PostRepository.currentID.toString();
                boolean isPublic = rbPublic.isSelected();
                String isPublicStr = "Public";
                if (!rbPublic.isSelected()) {isPublicStr = "Private";}
                String content = txtPost.getText();
                // Create a new post authored by current user
                Profile.newPost(content, isPublic);
                // Save post to text file
                FileWriter file = new FileWriter("Post.txt", true);
                BufferedWriter out = new BufferedWriter(file);
                out.write("\n" + username + " " + ID + " " + isPublicStr
                        + " " + content + "\n");
                out.close();
                frmHomePage.getAllPublicPosts();
                window.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        });
        btnPost.defaultButtonProperty().bind(btnPost.focusedProperty());
        GridPane.setConstraints(btnPost, 1, 5);

        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> window.close());
        btnCancel.defaultButtonProperty().bind(btnCancel.focusedProperty());
        GridPane.setConstraints(btnCancel, 1, 5);
        btnCancel.setTranslateX(57);


        // Translate X and Y properties for cleaner GUI
        btnPost.setTranslateY(-10);
        btnCancel.setTranslateY(-10);
        btnCancel.setTranslateX(57);
        rbPublic.setTranslateY(-70);


        grid.getChildren().addAll(txtPost, btnPost, btnCancel, rbPublic, rbPrivate);

        Scene scene = new Scene(grid, 300, 240);
        window.setScene(scene);
        window.showAndWait();
    }
}
