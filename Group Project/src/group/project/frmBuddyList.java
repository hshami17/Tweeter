package group.project;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class frmBuddyList {

    private static BorderPane borderPane;
    private static RadioButton rbFollowers;
    private static RadioButton rbFollowing;
    private static ScrollPane scrollPane;
    private static VBox centerPane;

    public static void display(){
    
        // Create new window
        Stage window = new Stage();
        window.setTitle("Buddy List");
        window.setResizable(false);

        // Disallow inputs to parent window
        window.initModality(Modality.APPLICATION_MODAL);
    
        // Declare all the layouts
        borderPane = new BorderPane();
        HBox topPane = new HBox(10);
        topPane.setStyle("-fx-background-color: #BF9393");
        topPane.setPadding(new Insets(5, 5, 15, 0));
        HBox bottomPane = new HBox(10);
        bottomPane.setStyle("-fx-background-color: #A0D2F7");
        bottomPane.setPadding(new Insets(3, 3, 15, 0));
        
        // Set the top pane
        borderPane.setTop(topPane);
        borderPane.setBottom(bottomPane);
        
        // Create the followers and following radio buttons
        ToggleGroup group = new ToggleGroup();
        rbFollowers = new RadioButton("Followers");
        rbFollowers.setOnAction(event -> getFollowers());
        rbFollowers.setToggleGroup(group);
        rbFollowers.setFont(Font.font("Helvetica", FontWeight.BOLD, 13));
        rbFollowers.setTranslateX(-10);
        rbFollowers.setTranslateY(5);

        rbFollowing = new RadioButton("Following");
        rbFollowing.setOnAction(event -> getFollowing());
        rbFollowing.setToggleGroup(group);
        rbFollowing.setFont(Font.font("Helvetica", FontWeight.BOLD, 13));
        rbFollowing.setTranslateX(10);
        rbFollowing.setTranslateY(5);

        getFollowers();
        
        // Create button to close buddy list window
        Button btnHome = new Button("Back to Home");
        btnHome.setFont(Font.font("Helvetica", 15));
        btnHome.setTranslateY(7.5);
        btnHome.setTranslateX(-7.5);
        btnHome.defaultButtonProperty().bind(btnHome.focusedProperty());
        btnHome.setOnAction(event -> window.close());
        
        // Add controls to the top pane
        topPane.getChildren().addAll(rbFollowers, rbFollowing);
        topPane.setAlignment(Pos.TOP_CENTER);
        
        //Add controls to bottom pane
        bottomPane.getChildren().addAll(btnHome);
        bottomPane.setAlignment(Pos.BOTTOM_RIGHT);
        
        // Create the scene and display the home page window
        Scene scene = new Scene(borderPane, 450, 450);
        window.setScene(scene);
        window.showAndWait();
        
    }


    public static void getFollowers(){
        //Select followers radio button
        rbFollowers.setSelected(true);
        centerPane = new VBox(6);
        scrollPane = new ScrollPane();
        centerPane.setPadding(new Insets(5, 5, 5, 5));
        centerPane.setStyle("-fx-background-color: #EFF2FB");
        if(UserRepository.getRepoSize() != 0){
            for(int i=0; i < Profile.followersSize(); i++){
                User user = Profile.getFollower(i);

                Text person = new Text(user.getUsername().trim());
                person.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

                // Add items to center pane
                centerPane.getChildren().add(person);

                addBuddyComponents(user);
            }

            VBox.setVgrow(scrollPane, Priority.ALWAYS);
            scrollPane.setContent(centerPane);
            borderPane.setCenter(scrollPane);
        }
        else
            borderPane.setCenter(centerPane);
    }

    
    public static void getFollowing(){
        rbFollowing.setSelected(true);
        centerPane = new VBox(6);
        scrollPane = new ScrollPane();
        centerPane.setPadding(new Insets(5, 5, 5, 5));
        centerPane.setStyle("-fx-background-color: #EFF2FB");
        if(UserRepository.getRepoSize() != 0){
            for(int i=0; i < Profile.followingSize(); i++){
                User user = Profile.getFollowing(i);

                Text person = new Text(user.getUsername().trim());
                person.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));

                // Add items to center pane
                centerPane.getChildren().add(person);

                addBuddyComponents(user);
            }

            VBox.setVgrow(scrollPane, Priority.ALWAYS);
            scrollPane.setContent(centerPane);
            borderPane.setCenter(scrollPane);
        }
        else
            borderPane.setCenter(centerPane);
    }


    public static void addBuddyComponents(User user){
        // Set initial state of follow button
        Button btnFollowUnfollow= new Button();
        if (Profile.isFollowing(user.getUsername()))
            btnFollowUnfollow.setText("Unfollow");
        else
            btnFollowUnfollow.setText("Follow");

        // Execute appropriate action depending on state of follow button
        btnFollowUnfollow.setOnAction(event -> {
            if (btnFollowUnfollow.getText().equals("Follow")) {
                Profile.addFollowing(user);
                btnFollowUnfollow.setText("Unfollow");
            } else {
                Profile.removeFollowing(user);
                btnFollowUnfollow.setText("Follow");
            }
        });
        btnFollowUnfollow.defaultButtonProperty().bind(btnFollowUnfollow.focusedProperty());
        btnFollowUnfollow.setFont(Font.font("Helvetica", 12));

        // Create line to divide posts
        Line divider = new Line(0, 100, 421, 100);
        divider.setStroke(Color.LIGHTGRAY);

        centerPane.getChildren().addAll(btnFollowUnfollow, divider);
    }
}

