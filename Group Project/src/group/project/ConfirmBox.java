package group.project;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ConfirmBox {

    public static boolean result;

    /**
     * Display an alert box
     * @param title The title of the alert window
     * @param message The message in the alert window
     * @param width Width of the alert window
     * @param length Height of the alert window
     */
    public static void display(String title, String message, int width, int length){
        // Create a new window
        Stage window = new Stage();

        result = false;

        // Disallow inputs to parent window
        window.initModality(Modality.APPLICATION_MODAL);
        // Set title from parameter and set the max height and width of the window
        window.setTitle(title);
        window.setResizable(false);

        BorderPane borderPane = new BorderPane();
        VBox topPane = new VBox(5);
        HBox bottomPane = new HBox(8);

        // Create a new label and button
        Text txtMessage = new Text(message);
        txtMessage.setWrappingWidth(210);
        txtMessage.setFont(Font.font("Helvetica", 13));

        Button btnYes = new Button("Yes");
        btnYes.setOnAction(event -> {
            result = true;
            window.close();
        });
        btnYes.defaultButtonProperty().bind(btnYes.focusedProperty());
        btnYes.setFont(Font.font("Helvetica", 13));


        Button btnCancel = new Button("Cancel");
        btnCancel.setOnAction(event -> {
            result = false;
            window.close();
        });
        btnCancel.defaultButtonProperty().bind(btnCancel.focusedProperty());
        btnCancel.setFont(Font.font("Helvetica", 13));


        // Create new layout and center layout, add controls to layout
        topPane.getChildren().add(txtMessage);
        topPane.setPadding(new Insets(25, 0, 0, 0));
        topPane.setAlignment(Pos.CENTER);

        bottomPane.getChildren().addAll(btnYes, btnCancel);
        bottomPane.setPadding(new Insets(0, 0, 25, 0));
        bottomPane.setAlignment(Pos.CENTER);

        borderPane.setTop(topPane);
        borderPane.setBottom(bottomPane);

        Scene scene = new Scene(borderPane, width, length);

        window.setScene(scene);
        // Wait to return to parent window until window is closed
        window.showAndWait();
    }
}
