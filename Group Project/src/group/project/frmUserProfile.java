package group.project;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class frmUserProfile {
    public static void display(User user){
        // Create new window
        Stage window = new Stage();
        window.setTitle(user.getUsername() + "'s Profile");
        window.setResizable(false);

        // Disallow inputs from parent window
        window.initModality(Modality.APPLICATION_MODAL);

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
        Text txtTitle = new Text(user.getUsername() + "'s profile");
        txtTitle.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 30));
        txtTitle.setFill(Color.web("#CBE5FF"));
        txtTitle.setTranslateY(5);

        // Create the close button to close the window
        Button btnClose = new Button("Close");
        btnClose.setOnAction(event -> window.close());
        btnClose.setTranslateX(-5);
        btnClose.setTranslateY(-5);
        btnClose.setFont(Font.font("Helvetica", 15));

        topPane.getChildren().add(txtTitle);
        bottomPane.getChildren().add(btnClose);

        // Create center pane to display user profile info
        VBox centerPane = new VBox(15);
        centerPane.setPadding(new Insets(5, 5, 5, 5));
        centerPane.setStyle("-fx-background-color: #EFF2FB");

        Text txtAge = new Text(user.getAge());
        txtAge.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 25));

        Text txtGender = new Text(user.getGender());
        txtGender.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 25));

        Text txtBio = new Text(user.getUserBio().trim());
        txtBio.setWrappingWidth(200);
        txtBio.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 25));

        centerPane.getChildren().addAll(txtAge, txtGender, txtBio);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(centerPane);
        borderPane.setTop(topPane);
        borderPane.setBottom(bottomPane);

        Scene scene = new Scene(borderPane, 300, 300);
        window.setScene(scene);
        window.showAndWait();
    }
}
