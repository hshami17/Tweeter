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
import java.io.*;
import java.util.*;

public class frmHomePage {

    private static BorderPane borderPane;

    public static void display(){
        // Create new window
        Stage window = new Stage();
        window.setTitle("Tweeter Home");
        window.setResizable(false);

        // Disallow inputs to parent window
        window.initModality(Modality.APPLICATION_MODAL);

        // Declare all the layouts
        borderPane = new BorderPane();
        VBox leftPane = new VBox(7);
        leftPane.setStyle("-fx-background-color: #A0D2F7");
        leftPane.setPadding(new Insets(5, 17, 0, 3));
        HBox topPane = new HBox(10);
        topPane.setStyle("-fx-background-color: #BF9393");
        topPane.setPadding(new Insets(3, 3, 15, 0));

        // Retrieve all of the public posts to th feed
        getAllPublicPosts();

        TextArea txtUserInfo = new TextArea();
        txtUserInfo.setText("@" + UserRepository.getUser(Profile.username).getUsername() + "\n\n" +
                UserRepository.getUser(Profile.username).getGender() + "\n" +
                UserRepository.getUser(Profile.username).getAge() + "\n" +
                UserRepository.getUser(Profile.username).getUserBio().trim());

        txtUserInfo.setEditable(false);
        txtUserInfo.setWrapText(true);
        txtUserInfo.setTranslateX(7);
        txtUserInfo.setPrefSize(100, 200);
        txtUserInfo.setFont(Font.font("Helvetica", 15));

        // Create edit profile button and open edit window when clicked
        Button btnEditProfile = new Button("Edit Profile");
        btnEditProfile.setFont(Font.font("Helvetica", 14));
        btnEditProfile.setTranslateX(7);

        // Create a tagged post button and open users tagged posts
        Button btnTaggedPosts = new Button("Tagged Posts");
        btnTaggedPosts.setFont(Font.font("Helvetica", 14));
        btnTaggedPosts.setTranslateX(7);

        // Create search by # button to search all posts for # phrases
        Button btnSearchByHashtag = new Button("Search by #");
        btnSearchByHashtag.setFont(Font.font("Helvetica", 14));
        btnSearchByHashtag.setTranslateX(7);
        btnSearchByHashtag.setTranslateY(30);

        // Create a buddy list button to open a window containing your followers and following
        Button btnBuddyList = new Button("Buddy List");
        btnBuddyList.setFont(Font.font("Helvetica", 14));
        btnBuddyList.setTranslateX(7);
        btnBuddyList.setTranslateY(30);

        // Get the logged in user's account info to display
       // Text username = new Text("@" + UserRepository.getUser(Profile.username).getUsername());
       // username.setFont(Font.font("Helvetica", 15));
        //Text gender = new Text(UserRepository.getUser(Profile.username).getGender());
       // gender.setFont(Font.font("Helvetica", 15));
       // Text age = new Text(UserRepository.getUser(Profile.username).getAge());
       // age.setFont(Font.font("Helvetica", 15));
       // Text bio = new Text(UserRepository.getUser(Profile.username).getUserBio().trim());
       // bio.setFont(Font.font("Helvetica", 15));
        //bio.setWrappingWidth(70);

        // Create post button to open new post window
        Button btnPost = new Button("New Post");
        btnPost.setFont(Font.font("Helvetica", 15));
        btnPost.setTranslateY(13);
        btnPost.defaultButtonProperty().bind(btnPost.focusedProperty());
        btnPost.setOnAction(event -> frmPost.display());

        // Create logout button to exit Tweeter and go back to login
        Button btnLogOut = new Button("Log out");
        btnLogOut.setFont(Font.font("Helvetica", 15));
        btnLogOut.setTranslateY(13);
        btnLogOut.defaultButtonProperty().bind(btnLogOut.focusedProperty());
        btnLogOut.setOnAction(event -> {
            window.close();
            frmLogin.display();
        });

        // Create the public and private feed radio buttons
        ToggleGroup group = new ToggleGroup();
        RadioButton rbPublic = new RadioButton("Public Feed");
        rbPublic.setSelected(true);
        rbPublic.setToggleGroup(group);
        rbPublic.setFont(Font.font("Helvetica", FontWeight.BOLD, 13));
        rbPublic.setTranslateX(-17);
        rbPublic.setTranslateY(20);
        RadioButton rbPrivate = new RadioButton("Private Feed");
        rbPrivate.setToggleGroup(group);
        rbPrivate.setFont(Font.font("Helvetica", FontWeight.BOLD, 13));
        rbPrivate.setTranslateX(-12);
        rbPrivate.setTranslateY(20);

        // Create Tweeter text
        Text txtTweeter = new Text("Tweeter");
        txtTweeter.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 30));
        txtTweeter.setFill(Color.web("#CBE5FF"));
        txtTweeter.setTranslateX(-38);
        txtTweeter.setTranslateY(13);

        // Add Tweeter icon to the form
        Image image = new Image("fat-twitter.png");
        ImageView icnTweeter = new ImageView();
        icnTweeter.setImage(image);
        icnTweeter.setFitWidth(60);
        icnTweeter.setPreserveRatio(true);
        icnTweeter.setSmooth(true);
        icnTweeter.setCache(true);
        icnTweeter.setTranslateX(-28);

        /**
        if (frmLogin.exploreMode){
            btnPost.setVisible(false);
            btnLogOut.setText("Exit");
            btnLogOut.setVisible(false);
        }
         */

        // Add controls to the top pane
        topPane.getChildren().addAll(icnTweeter, txtTweeter, rbPublic, rbPrivate, btnPost, btnLogOut);
        topPane.setAlignment(Pos.TOP_RIGHT);

        // Add user info to the left pane
        leftPane.getChildren().addAll(txtUserInfo, btnEditProfile, btnTaggedPosts, btnSearchByHashtag, btnBuddyList);
        leftPane.setAlignment(Pos.TOP_LEFT);

        // Set the left and top pane
        borderPane.setLeft(leftPane);
        borderPane.setTop(topPane);


        // Create the scene and display the home page window
        Scene scene = new Scene(borderPane, 600, 670);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Get all public posts from post repo and
     * them add to the center pane.
     */
    public static void getAllPublicPosts(){
        VBox centerPane = new VBox(6);
        ScrollPane scrollPane = new ScrollPane();
        centerPane.setPadding(new Insets(5, 5, 5, 5));
        for (int i=PostRepository.getRepoSize()-1; i >= 0; i--){
            Post addPost = PostRepository.getPost(i);
            if (addPost.isPublic()) {
                // Get the post author
                Text txtAuthor = new Text("@" + addPost.getAuthor().trim());
                txtAuthor.setFont(Font.font("Helvetica", FontWeight.BOLD ,17));

                // Get the post
                Text txtPost = new Text(addPost.getMessage().trim());
                txtPost.setFont(Font.font("Helvetica", 17));
                txtPost.setWrappingWidth(400);

                // Create the like button
                Button btnLike = new Button("Like");
                btnLike.setFont(Font.font("Helvetica", 15));
                btnLike.setTranslateY(20);
                btnLike.defaultButtonProperty().bind(btnLike.focusedProperty());
                btnLike.setOnAction(event -> {
                    addPost.setLikeCount(addPost.getLikeCount() + 1);
                    PostRepository.saveAllPosts();
                    getAllPublicPosts();
                });

                centerPane.getChildren().addAll(txtAuthor, txtPost, btnLike);

                // Show delete button if author is logged in user
                if (addPost.getAuthor().trim().equals(Profile.username)){
                    Button btnDelete = new Button("Delete");
                    btnDelete.setOnAction(event -> {
                        PostRepository.deletePost(addPost);
                        PostRepository.saveAllPosts();
                        getAllPublicPosts();
                    });
                    btnDelete.setFont(Font.font("Helvetica", 15));
                    btnDelete.setTranslateX(55);
                    btnDelete.setTranslateY(-15);
                    centerPane.getChildren().add(btnDelete);
                }
                // Show follow button if not authors post
                else{
                    Button btnFollow = new Button("Follow");
                    btnFollow.setFont(Font.font("Helvetica", 15));
                    btnFollow.setTranslateX(53);
                    btnFollow.setTranslateY(-15);
                    btnFollow.defaultButtonProperty().bind(btnFollow.focusedProperty());
                    btnFollow.setOnAction(event -> {

                    });
                    centerPane.getChildren().add(btnFollow);
                }

                // Show likes for a post
                Text txtLikes = new Text(addPost.getLikeCount().toString() + " " + "likes");
                txtLikes.setFont(Font.font("Helvetica", 14));
                txtLikes.setTranslateY(-7);
                centerPane.getChildren().add(txtLikes);

                // Create line to divide posts
                Line divider = new Line(0, 100, 440, 100);
                divider.setTranslateY(-10);
                divider.setStroke(Color.LIGHTGRAY);
                centerPane.getChildren().add(divider);
            }
        }
        centerPane.setAlignment(Pos.TOP_LEFT);
        centerPane.setStyle("-fx-background-color: #EFF2FB");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        scrollPane.setContent(centerPane);
        borderPane.setCenter(scrollPane);
    }
}

