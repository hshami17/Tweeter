package group.project;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertBox {

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

        // Disallow inputs to parent window
        window.initModality(Modality.APPLICATION_MODAL);
        // Set title from parameter and set the max height and width of the window
        window.setTitle(title);
        window.setResizable(false);

        // Create a new label and button
        Label lblMessage = new Label(message);
        lblMessage.setWrapText(true);
        Button btnClose = new Button("Close");
        // Have close button close the window when clicked
        btnClose.setOnAction(event -> window.close());
        btnClose.defaultButtonProperty().bind(btnClose.focusedProperty());

        // Create new layout and center layout, add controls to layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(lblMessage, btnClose);
        Scene scene = new Scene(layout, width, length);

        window.setScene(scene);
        // Wait to return to parent window until window is closed
        window.showAndWait();

    }
}
