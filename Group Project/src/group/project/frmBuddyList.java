package group.project;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

import javax.swing.event.ChangeListener;
import java.io.*;
import java.util.*;

/**
 *
 * @author Bethany
 */
public class frmBuddyList {
    
    /**
    private static BorderPane border;
    private static RadioButton rbFollowers;
    private static RadioButton rbFollowing;
    private static ScrollPane scroll;
    private static VBox center;
    
    public static void display(){
    
        // Create new window
        Stage window = new Stage();
        window.setTitle("Buddy List");
        window.setResizable(false);

        // Disallow inputs to parent window
        window.initModality(Modality.APPLICATION_MODAL);
    
        // Declare all the layouts
        border = new BorderPane();
        HBox topPane = new HBox(10);
        topPane.setStyle("-fx-background-color: #BF9393");
        topPane.setPadding(new Insets(5, 5, 15, 0));
        HBox bottomPane = new HBox(10);
        bottomPane.setStyle("-fx-background-color: #A0D2F7");
        bottomPane.setPadding(new Insets(3, 3, 15, 0));
        
        // Set the top pane
        border.setTop(topPane);
        border.setBottom(bottomPane);
        
        // Create the followers and following radio buttons
        ToggleGroup group = new ToggleGroup();
        rbFollowers = new RadioButton("Followers");
        rbFollowers.setOnAction(event -> getFollowers());
        rbFollowers.setToggleGroup(group);
        rbFollowers.setFont(Font.font("Helvetica", FontWeight.BOLD, 13));
        rbFollowers.setTranslateX(-10);
        rbFollowers.setTranslateY(5);

        rbFollowing = new RadioButton("Following");
        rbFollowing.setOnAction(event -> getFollowings());
        rbFollowing.setToggleGroup(group);
        rbFollowing.setFont(Font.font("Helvetica", FontWeight.BOLD, 13));
        rbFollowing.setTranslateX(10);
        rbFollowing.setTranslateY(5);
        
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
        Scene scene = new Scene(border, 300, 400);
        window.setScene(scene);
        window.showAndWait();
        
    }
    
    public static void getFollowers(){
        //Select followers radio button
        rbFollowers.setSelected(true);
        //Create the layouts and set padding
        center = new VBox(6);
        scroll = new ScrollPane();
        center.setPadding(new Insets(5, 5, 5, 5));
        if(UserRepository.getRepoSize() != 0){
            ArrayList<User> f = Profile.getFollowers();
            for(int i = 0; i < f.size(); i++){
                //if(!addFollowing.isFollower()){
                    Text person = new Text(f.get(i).getUsername().trim());
                    person.setFont(Font.font("Helvetica", FontWeight.BOLD, 17));
                    // Add items to center pane
                    center.getChildren().addAll(person);
                    // Add post interaction buttons
                    //addBuddyComponents(addFollowers);
                //}
            
            }
        }
    }
    
    public static void getFollowing(){
        rbFollowing.setSelected(true);
        center = new VBox(6);
        scroll = new ScrollPane();
        center.setPadding(new Insets(5, 5, 5, 5));
        if(UserRepository.getRepoSize() != 0){
            User[] f = Profile.getFollowings().toArray();
            for(int i = 0; i < g.length(); i++){
                //if(!addFollowing.isFollower()){
                    Text person = new Text(f[i].getUsername().trim());
                    person.setFont(Font.font("Helvetica", FontWeight.BOLD, 17));
                    // Add items to center pane
                    center.getChildren().addAll(person);
                    // Add post interaction buttons
                    //addBuddyComponents(addFollowing);
                //}
            }
        }
    }
    
    private static void addBuddyComponents(){
        //Set initial state of follow button
        Button btnFollow = new Button();
        if(){
            btnFollow.setText("Unfollow");
        }
        else{
            btnFollow.setText("Follow");
        }
        
        btnFollow.setTranslateX(55);
        
        btnFollow.setOnAction(event -> {
            if(btnFollow.getText().equals("Unfollow")){
                ConfirmBox.display("Unfollow User", "Are you sure you no longer want to"
                        + " subscribe to this user?", 300, 110);
                if(ConfirmBox.result){
                    
                }
                if(rbFollowers.isSelected()){
                    
                }
                else(rbFollowing.isSelected()){
                
                }
            }
        });
        
        btnFollow.setOnAction(event -> {
            if(){
                btnFollow.setText("Unfollow");
            }
            else{
                btnFollow.setText("Follow");
            }
        });
        
        
        btnFollow.setFont(Font.font("Helvetica", 15));
        btnFollow.setTranslateY(20);
        center.getChildren().addAll(btnFollow);
        
        Line divider = new Line(0, 100, 435, 100);
        divider.setTranslateY(-10);
        divider.setStroke(Color.LIGHTGRAY);
        center.getChildren().add(divider);
        
        // Set center pane alignment and color
        centerPane.setAlignment(Pos.TOP_CENTER);
        centerPane.setStyle("-fx-background-color: #EFF2FB");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        scrollPane.setContent(center);
        borderPane.setCenter(scroll);
    
    }
     */
    
}
