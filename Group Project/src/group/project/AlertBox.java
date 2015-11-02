package group.project;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertBox {

    public static void display(String title, String message, int width, int length){
        // Create a new window
        Stage window = new Stage();

        // Disallow inputs to parent window
        window.initModality(Modality.APPLICATION_MODAL);
        // Set title from parameter and set the max height and width of the window
        window.setTitle(title);
        window.setResizable(false);

        // Create a new label and button
        Label label = new Label(message);
        Button close = new Button("Close");
        // Have close button close the window when clicked
        close.setOnAction(event -> window.close());
        // Create new layout and center layout, add controls to layout
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, close);
        Scene scene = new Scene(layout, width, length);

        window.setScene(scene);
        // Wait to return to parent window until window is closed
        window.showAndWait();

    }
}
