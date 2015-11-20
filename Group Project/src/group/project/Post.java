package group.project;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Post {
    private String Msg_ID;
    private String author;
    private String message;
    private boolean isPublic;
    private int likeCount;

    Post(String Msg_ID, String author, String message,
         boolean isPublic, int likeCount){
        this.Msg_ID = Msg_ID;
        this.author = author;
        this.message = message;
        this.isPublic = isPublic;
        this.likeCount = likeCount;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setMsg_ID(String msg_ID) {
        Msg_ID = msg_ID;
    }

    public String getMsg_ID() {
        return Msg_ID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void addPostComponents(Post post, VBox centerPane, ScrollPane scrollPane,
                                          BorderPane borderPane, String form){
        // Create text to display likes
        Text txtLikes = new Text();

        // Set initial state of like update button
        Button btnLikeUpdate = new Button();
        if (Profile.getLikedPost(post.getMsg_ID()))
            btnLikeUpdate.setText("Unlike");
        else
            btnLikeUpdate.setText("Like");

        // Create button to display follow or delete
        Button btnFollowDelete = new Button();
        // Set initial state of follow/delete button
        if (post.getAuthor().trim().equals(Profile.username))
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
                    PostRepository.deletePost(post);
                    PostRepository.saveAllPosts();
                    if (form.equals("Home")) {
                        if (frmHomePage.rbPublic.isSelected())
                            frmHomePage.getAllPublicPosts();
                        else
                            frmHomePage.getAllPrivatePosts();
                    }
                    else if (form.equals("Tagged Posts")){
                        frmTaggedPosts.getAllTaggedPosts();
                    }
                    else if (form.equals("HashTag Posts")){
                        frmHashTagSearch.getHashTagPosts();
                    }
                }
            }
        });
        btnFollowDelete.defaultButtonProperty().bind(btnFollowDelete.focusedProperty());
        btnFollowDelete.setFont(Font.font("Helvetica", 15));
        btnFollowDelete.setTranslateY(-15);


        // Execute appropriate action depending on state of update like button
        btnLikeUpdate.setOnAction(event -> {
            if (btnLikeUpdate.getText().equals("Like")) {
                post.setLikeCount(post.getLikeCount() + 1);
                Profile.addLikedPost(post);
                PostRepository.saveAllPosts();
                btnLikeUpdate.setText("Unlike");
                btnFollowDelete.setTranslateX(68);
                txtLikes.setText(post.getLikeCount().toString() + " " + "likes");
            } else {
                post.setLikeCount(post.getLikeCount() - 1);
                Profile.removeLikedPost(post.getMsg_ID());
                PostRepository.saveAllPosts();
                btnLikeUpdate.setText("Like");
                btnFollowDelete.setTranslateX(55);
                txtLikes.setText(post.getLikeCount().toString() + " " + "likes");
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
        txtLikes.setText(post.getLikeCount().toString() + " " + "likes");
        txtLikes.setFont(Font.font("Helvetica", 14));
        txtLikes.setTranslateY(-7);
        centerPane.getChildren().add(txtLikes);


        int lineWidth;
        if (form.equals("Home"))
            lineWidth = 435;
        else
            lineWidth = 420;

        // Create line to divide posts
        Line divider = new Line(0, 100, lineWidth, 100);
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

    @Override
    public String toString() {
        String isPublicStr;
        if (isPublic())
            isPublicStr = "Public";
        else
            isPublicStr = "Private";
        return author + " " + Msg_ID + " " + isPublicStr + " " + likeCount + " " + message;
    }
}
