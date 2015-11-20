import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class frmHashTagSearch {

    private static Stage window;
    private static String phrase;
    private static BorderPane borderPane;
    private static VBox centerPane;
    private static ScrollPane scrollPane;
    private static Scene scene1, scene2;

    public static void display() {
        // Create new window
        window = new Stage();
        window.setTitle("Search by HashTag");
        window.setResizable(false);

        // Disallow inputs from parent window
        window.initModality(Modality.APPLICATION_MODAL);

        // Create title text
        Text txtTitle = new Text("Search Posts By #");
        txtTitle.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 25));
        txtTitle.setFill(Color.web("#4396CC"));
        txtTitle.setTranslateY(20);

        // Create search text field
        TextField txtSearch = new TextField();
        txtSearch.setFont(Font.font("Helvetica", 20));
        txtSearch.setTranslateY(20);

        // Create search button to look up the phrase
        Button btnSearch = new Button("Search");
        btnSearch.setOnAction(event -> {
            phrase = txtSearch.getText().trim();
            if (phrase.charAt(0) == '#'){
                phrase = phrase.substring(1);
            }

            getHashTagPosts();
            displayPosts();
        });
        btnSearch.defaultButtonProperty().bind(btnSearch.focusedProperty());
        btnSearch.setFont(Font.font("Helvetica", 18));
        btnSearch.setTranslateY(20);
        btnSearch.setTranslateX(-70);

        // Create close button to close window
        Button btnClose = new Button("Back to Home");
        btnClose.setOnAction(event -> window.close());
        btnClose.defaultButtonProperty().bind(btnClose.focusedProperty());
        btnClose.setFont(Font.font("Helvetica", 18));
        btnClose.setTranslateY(-23);
        btnClose.setTranslateX(50);

        // Declare layout and add controls
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(0, 40, 0, 40));
        layout.getChildren().addAll(txtTitle, txtSearch, btnSearch, btnClose);

        // Set the layout style and scene
        layout.setStyle("-fx-background-color: #EFF2FB");
        layout.setAlignment(Pos.CENTER);
        scene1 = new Scene(layout, 380, 140);

        // Set the scene and display window
        window.setScene(scene1);
        window.showAndWait();
    }

    public static void getHashTagPosts(){
        borderPane = new BorderPane();
        centerPane = new VBox(6);
        scrollPane = new ScrollPane();

        for (int i = PostRepository.getRepoSize() - 1; i >= 0; i--) {
            if (PostRepository.containsHashTagPhrase(phrase, i)){
                Post post = PostRepository.getPost(i);

                // Get the post author
                Text txtAuthor = new Text("@" + post.getAuthor().trim());
                txtAuthor.setFont(Font.font("Helvetica", FontWeight.BOLD, 17));

                // Get the post
                Text txtPost = new Text(post.getMessage().trim());
                txtPost.setFont(Font.font("Helvetica", 17));
                txtPost.setWrappingWidth(400);

                centerPane.getChildren().addAll(txtAuthor, txtPost);
                post.addPostComponents(post, centerPane, scrollPane, borderPane, "HashTag Posts");
            }
        }
    }

    private static void displayPosts(){
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
        Text txtTitle = new Text("#" + phrase);
        txtTitle.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 30));
        txtTitle.setFill(Color.web("#CBE5FF"));
        txtTitle.setTranslateY(5);

        // Create the close button to close the window
        Button btnClose = new Button("Back to Home");
        btnClose.setOnAction(event -> {
            window.close();
            if (frmHomePage.rbPublic.isSelected())
                frmHomePage.getAllPublicPosts();
            else
                frmHomePage.getAllPrivatePosts();
        });
        btnClose.setTranslateX(-5);
        btnClose.setTranslateY(-5);
        btnClose.defaultButtonProperty().bind(btnClose.focusedProperty());
        btnClose.setFont(Font.font("Helvetica", 15));

        topPane.getChildren().add(txtTitle);
        bottomPane.getChildren().add(btnClose);

        borderPane.setTop(topPane);
        borderPane.setBottom(bottomPane);
        centerPane.setPadding(new Insets(5, 0, 0, 5));

        scene2 = new Scene(borderPane, 450, 450);

        window.setScene(scene2);
        window.setX(420);
        window.setY(150);
    }
}


