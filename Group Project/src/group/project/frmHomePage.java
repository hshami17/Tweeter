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

public class frmHomePage {

    private static Stage window;
    private static BorderPane borderPane;
    private static VBox centerPane;
    private static ScrollPane scrollPane;
    private static RadioButton rbPublic;
    private static RadioButton rbPrivate;

    public static void display(){
        // Create new window
        window = new Stage();
        window.setTitle("Tweeter Home");
        window.setResizable(false);

        // Disallow inputs to parent window
        window.initModality(Modality.APPLICATION_MODAL);

        window.setOnCloseRequest(event -> {
            event.consume();
            closeWindow();
        });

        // Declare all the layouts
        borderPane = new BorderPane();
        VBox leftPane = new VBox(7);
        leftPane.setStyle("-fx-background-color: #A0D2F7");
        leftPane.setPadding(new Insets(5, 17, 0, 3));
        HBox topPane = new HBox(10);
        topPane.setStyle("-fx-background-color: #BF9393");
        topPane.setPadding(new Insets(3, 3, 15, 0));


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
        btnEditProfile.setOnAction(event -> {


        });
        btnEditProfile.defaultButtonProperty().bind(btnEditProfile.focusedProperty());
        btnEditProfile.setFont(Font.font("Helvetica", 15));
        btnEditProfile.setTranslateX(7);

        // Create a tagged post button and open users tagged posts
        Button btnTaggedPosts = new Button("Tagged Posts");
        btnTaggedPosts.setOnAction(event -> frmTaggedPosts.display());
        btnTaggedPosts.defaultButtonProperty().bind(btnTaggedPosts.focusedProperty());
        btnTaggedPosts.setFont(Font.font("Helvetica", 15));
        btnTaggedPosts.setTranslateX(7);

        // Create search by # button to search all posts for # phrases
        Button btnSearchByHashtag = new Button("Search by #");
        btnSearchByHashtag.setOnAction(event -> {


        });
        btnSearchByHashtag.defaultButtonProperty().bind(btnSearchByHashtag.focusedProperty());
        btnSearchByHashtag.setFont(Font.font("Helvetica", 15));
        btnSearchByHashtag.setTranslateX(7);
        btnSearchByHashtag.setTranslateY(30);

        // Create a buddy list button to open a window containing your followers and following
        Button btnBuddyList = new Button("Buddy List");
        btnBuddyList.setOnAction(event -> frmBuddyList.display());
        btnBuddyList.defaultButtonProperty().bind(btnBuddyList.focusedProperty());
        btnBuddyList.setFont(Font.font("Helvetica", 15));
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
        btnLogOut.setTranslateX(-2);
        btnLogOut.defaultButtonProperty().bind(btnLogOut.focusedProperty());
        btnLogOut.setOnAction(event -> closeWindow());

        // Create the public and private feed radio buttons
        ToggleGroup group = new ToggleGroup();
        rbPublic = new RadioButton("Public Feed");
        rbPublic.setOnAction(event -> getAllPublicPosts());
        rbPublic.setToggleGroup(group);
        rbPublic.setFont(Font.font("Helvetica", FontWeight.BOLD, 13));
        rbPublic.setTranslateX(-17);
        rbPublic.setTranslateY(20);

        rbPrivate = new RadioButton("Private Feed");
        rbPrivate.setOnAction(event -> getAllPrivatePosts());
        rbPrivate.setToggleGroup(group);
        rbPrivate.setFont(Font.font("Helvetica", FontWeight.BOLD, 13));
        rbPrivate.setTranslateX(-12);
        rbPrivate.setTranslateY(20);

        // Retrieve all of the public posts to the feed
        getAllPublicPosts();

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

        // Make the proper changes to the home page when in explore mode
         if (frmLogin.exploreMode){
             // Give user option to create an account
             Button btnRegister = new Button("Sign up");
             btnRegister.setOnAction(event -> {
                 frmRegister.display();
                 if (frmRegister.success){
                     window.close();
                     frmLogin.display();
                 }
             });
             btnRegister.setFont(Font.font("Helvetica", 15));
             btnRegister.defaultButtonProperty().bind(btnRegister.focusedProperty());
             btnRegister.setTranslateX(7);
             leftPane.getChildren().add(btnRegister);
             txtUserInfo.setText("Register for Tweeter above!");
             // Disable certain buttons if exploring
             rbPrivate.setDisable(true);
             btnPost.setDisable(true);
             btnEditProfile.setDisable(true);
             btnTaggedPosts.setDisable(true);
             btnBuddyList.setDisable(true);
             btnLogOut.setText("Exit");
             btnLogOut.setTranslateX(-3);
             btnLogOut.setPrefSize(70, 10);
        }


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

    private static void closeWindow(){
        if (!frmLogin.exploreMode) {
            ConfirmBox.display("Log out", "Are you sure you want to log out?", 300, 110);
            if (ConfirmBox.result) {
                window.close();
                frmLogin.display();
            }
        }
        else {
            window.close();
            frmLogin.display();
        }
    }

    /**
     * Get all public posts from post repo and
     * them add to the center pane.
     */
    public static void getAllPublicPosts(){
        rbPublic.setSelected(true);
        centerPane = new VBox(6);
        scrollPane = new ScrollPane();
        centerPane.setPadding(new Insets(5, 5, 5, 5));
        // Check to see if size is 0
        if (PostRepository.getRepoSize() != 0) {
            for (int i = PostRepository.getRepoSize() - 1; i >= 0; i--) {
                Post addPost = PostRepository.getPost(i);
                if (addPost.isPublic()) {
                    // Get the post author
                    Text txtAuthor = new Text("@" + addPost.getAuthor().trim());
                    txtAuthor.setFont(Font.font("Helvetica", FontWeight.BOLD, 17));

                    // Get the post
                    Text txtPost = new Text(addPost.getMessage().trim());
                    txtPost.setFont(Font.font("Helvetica", 17));
                    txtPost.setWrappingWidth(400);

                    centerPane.getChildren().addAll(txtAuthor, txtPost);
                    addPostComponents(addPost);
                }
            }
        }
    }

    public static void getAllPrivatePosts(){
        // Select private radio button
        rbPrivate.setSelected(true);
        // Create the layouts and set padding
        centerPane = new VBox(6);
        scrollPane = new ScrollPane();
        centerPane.setPadding(new Insets(5, 5, 5, 5));
        if (PostRepository.getRepoSize() != 0) {
            // Go through post repo and get the private posts
            for (int i = PostRepository.getRepoSize() - 1; i >= 0; i--) {
                Post addPost = PostRepository.getPost(i);
                if (!addPost.isPublic()) {
                    // Get the post author
                    Text txtAuthor = new Text("@" + addPost.getAuthor().trim());
                    txtAuthor.setFont(Font.font("Helvetica", FontWeight.BOLD, 17));

                    // Get the post
                    Text txtPost = new Text(addPost.getMessage().trim());
                    txtPost.setFont(Font.font("Helvetica", 17));
                    txtPost.setWrappingWidth(400);

                    // Add items to the center pane
                    centerPane.getChildren().addAll(txtAuthor, txtPost);
                    // Add post interaction buttons
                    addPostComponents(addPost);
                }
            }
        }
    }

    private static void addPostComponents(Post addPost){
        // Create text to display likes
        Text txtLikes = new Text();

        // Set initial state of like update button
        Button btnLikeUpdate = new Button();
        if (Profile.getLikedPost(addPost.getMsg_ID()))
            btnLikeUpdate.setText("Unlike");
        else
            btnLikeUpdate.setText("Like");

        // Create button to display follow or delete
        Button btnFollowDelete = new Button();
        // Set initial state of follow/delete button
        if (addPost.getAuthor().trim().equals(Profile.username))
            btnFollowDelete.setText("Delete");
        else
            btnFollowDelete.setText("Follow");
        // Set the X position of the follow/delete button
        if (btnLikeUpdate.getText().equals("Like"))
            btnFollowDelete.setTranslateX(55);
        else
            btnFollowDelete.setTranslateX(68);


        // Execute appropriate action depending on button state
        btnFollowDelete.setOnAction(event -> {
            if (btnFollowDelete.getText().equals("Delete")) {
                ConfirmBox.display("Delete Post", "Are you sure you want to delete this post?", 300, 110);
                if (ConfirmBox.result) {
                    PostRepository.deletePost(addPost);
                    PostRepository.saveAllPosts();
                    if (rbPublic.isSelected())
                        getAllPublicPosts();
                    else
                        getAllPrivatePosts();
                }
            }
        });
        btnFollowDelete.defaultButtonProperty().bind(btnFollowDelete.focusedProperty());
        btnFollowDelete.setFont(Font.font("Helvetica", 15));
        btnFollowDelete.setTranslateY(-15);


        // Execute appropriate action depending on state of update like button
        btnLikeUpdate.setOnAction(event -> {
            if (btnLikeUpdate.getText().equals("Like")) {
                addPost.setLikeCount(addPost.getLikeCount() + 1);
                Profile.addLikedPost(addPost);
                PostRepository.saveAllPosts();
                btnLikeUpdate.setText("Unlike");
                btnFollowDelete.setTranslateX(68);
                txtLikes.setText(addPost.getLikeCount().toString() + " " + "likes");
            } else {
                addPost.setLikeCount(addPost.getLikeCount() - 1);
                Profile.removeLikedPost(addPost.getMsg_ID());
                PostRepository.saveAllPosts();
                btnLikeUpdate.setText("Like");
                btnFollowDelete.setTranslateX(55);
                txtLikes.setText(addPost.getLikeCount().toString() + " " + "likes");
            }
        });
        btnLikeUpdate.defaultButtonProperty().bind(btnLikeUpdate.focusedProperty());
        btnLikeUpdate.setFont(Font.font("Helvetica", 15));
        btnLikeUpdate.setTranslateY(20);

        centerPane.getChildren().addAll(btnLikeUpdate, btnFollowDelete);

        // Disable post interaction buttons if in explore mode
        if (frmLogin.exploreMode){
            btnLikeUpdate.setDisable(true);
            btnFollowDelete.setDisable(true);
        }

        // Show likes for a post
        txtLikes.setText(addPost.getLikeCount().toString() + " " + "likes");
        txtLikes.setFont(Font.font("Helvetica", 14));
        txtLikes.setTranslateY(-7);
        centerPane.getChildren().add(txtLikes);

        // Create line to divide posts
        Line divider = new Line(0, 100, 435, 100);
        divider.setTranslateY(-10);
        divider.setStroke(Color.LIGHTGRAY);
        centerPane.getChildren().add(divider);

        // Set center pane alignment and color
        centerPane.setAlignment(Pos.TOP_LEFT);
        centerPane.setStyle("-fx-background-color: #EFF2FB");
        // Put the VBox onto the scroll pane and add to the border pane
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        scrollPane.setContent(centerPane);
        borderPane.setCenter(scrollPane);
    }
}

