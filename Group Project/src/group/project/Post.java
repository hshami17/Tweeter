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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Post {
    private String Msg_ID;
    private String author;
    private String message;
    private boolean isPublic;
    private int likeCount;

    /**
     * Constructor for a new post object
     * @param Msg_ID Post ID
     * @param author The author of the new post
     * @param message The contents of the post
     * @param isPublic The post visibility
     * @param likeCount Number of likes for the post
     */
    Post(String Msg_ID, String author, String message,
         boolean isPublic, int likeCount){
        this.Msg_ID = Msg_ID;
        this.author = author;
        this.message = message;
        this.isPublic = isPublic;
        this.likeCount = likeCount;
    }

    /**
     * Set the post author
     * @param author The name of the author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Get the author of the post
     * @return The name of the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Set the post ID
     * @param msg_ID The ID of the post
     */
    public void setMsg_ID(String msg_ID) {
        Msg_ID = msg_ID;
    }

    /**
     * Get the post ID
     * @return The ID of the post
     */
    public String getMsg_ID() {
        return Msg_ID;
    }

    /**
     * Set the contents of the post
     * @param message The contents of the post
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get the contents of the post
     * @return The contents of the post
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the visibility of the post
     * @param isPublic The visibility of the post
     */
    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * Get the visibility of the post
     * @return The visibility of the post
     */
    public boolean isPublic() {
        return isPublic;
    }

    /**
     * Set the number of likes of the post
     * @param likeCount The number of likes for the post
     */
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    /**
     * Get the number of likes of the post
     * @return The number of liked for the post
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     * Add the post interaction buttons and like count when displaying post
     * @param centerPane Center layout in the border pane
     * @param scrollPane Scroll pane for the center pane to be in
     * @param borderPane The border pane to add the center pane into
     * @param form The window in which the posts are being printed
     */
    public void addPostComponents(VBox centerPane, ScrollPane scrollPane,
                                  BorderPane borderPane, String form){
        // Create text to display likes
        Text txtLikes = new Text();

        // Set initial state of like update button
        Button btnLikeUpdate = new Button();
        if (Profile.getLikedPost(Msg_ID))
            btnLikeUpdate.setText("Unlike");
        else
            btnLikeUpdate.setText("Like");

        // Create button to display follow or delete
        Button btnFollowDelete = new Button();
        // Set initial state of follow/delete button
        if (author.trim().equals(Profile.username))
            btnFollowDelete.setText("Delete");
        else {
            if (Profile.isFollowing(author)){
                btnFollowDelete.setText("Unfollow");
            }
            else
                btnFollowDelete.setText("Follow");
        }
        // Set the X position of the follow/delete button
        if (btnLikeUpdate.getText().equals("Like"))
            btnFollowDelete.setTranslateX(52);
        else
            btnFollowDelete.setTranslateX(65);


        // Execute appropriate action depending on button state
        btnFollowDelete.setOnAction(event -> {
            if (btnFollowDelete.getText().equals("Delete")) {
                ConfirmBox.display("Delete Post", "Are you sure you want to delete this post?", 300, 110);
                if (ConfirmBox.result) {
                    PostRepository.deletePost(this);
                    PostRepository.saveAllPosts();
                    FileUpdater.removePostID(Msg_ID);
                    if (form.equals("Home")) {
                        if (frmHomePage.rbPublic.isSelected())
                            frmHomePage.getAllPublicPosts();
                        else
                            frmHomePage.getAllPrivatePosts();
                    } else if (form.equals("Tagged Posts")) {
                        frmTaggedPosts.getAllTaggedPosts();
                    } else if (form.equals("HashTag Posts")) {
                        frmHashTagSearch.getHashTagPosts();
                    }
                }
            }
            else if (btnFollowDelete.getText().equals("Follow")){
                Profile.addFollowing(UserRepository.getUser(author));
                btnFollowDelete.setText("Unfollow");
            }
            else if (btnFollowDelete.getText().equals("Unfollow")){
                Profile.removeFollowing(UserRepository.getUser(author));
                btnFollowDelete.setText("Follow");
            }
        });
        btnFollowDelete.defaultButtonProperty().bind(btnFollowDelete.focusedProperty());
        btnFollowDelete.setFont(Font.font("Helvetica", 13));
        if (form.equals("Home"))
            btnFollowDelete.setTranslateY(-14);
        else
            btnFollowDelete.setTranslateY(-15);


        // Execute appropriate action depending on state of update like button
        btnLikeUpdate.setOnAction(event -> {
            if (btnLikeUpdate.getText().equals("Like")) {
                this.setLikeCount(likeCount + 1);
                Profile.addLikedPost(this);
                PostRepository.saveAllPosts();
                btnLikeUpdate.setText("Unlike");
                btnFollowDelete.setTranslateX(68);
                if (likeCount == 1)
                    txtLikes.setText(likeCount + " " + "like");
                else
                    txtLikes.setText(likeCount + " " + "likes");
            } else {
                this.setLikeCount(likeCount - 1);
                Profile.removeLikedPost(Msg_ID);
                PostRepository.saveAllPosts();
                btnLikeUpdate.setText("Like");
                btnFollowDelete.setTranslateX(55);
                if (likeCount == 1)
                    txtLikes.setText(likeCount + " " + "like");
                else
                    txtLikes.setText(likeCount + " " + "likes");
            }
        });
        btnLikeUpdate.defaultButtonProperty().bind(btnLikeUpdate.focusedProperty());
        btnLikeUpdate.setFont(Font.font("Helvetica", 13 ));
        btnLikeUpdate.setTranslateY(17);

        centerPane.getChildren().addAll(btnLikeUpdate, btnFollowDelete);

        // Disable post interaction buttons if in explore mode
        if (frmLogin.exploreMode){
            btnLikeUpdate.setDisable(true);
            btnFollowDelete.setDisable(true);
        }

        // Show likes for the post
        if (likeCount == 1)
            txtLikes.setText(likeCount + " " + "like");
        else
            txtLikes.setText(likeCount + " " + "likes");
        txtLikes.setFont(Font.font("Helvetica", 14));
        txtLikes.setTranslateY(-7);
        centerPane.getChildren().add(txtLikes);


        int lineWidth;
        if (form.equals("Home"))
            lineWidth = 435;
        else if (form.equals("Tagged Posts"))
            lineWidth = 421;
        else
            lineWidth = 425;

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

    /**
     * Looks for a certain hash tag phrase in the post content
     * @param phrase The hash tag phrase being searched for
     * @return True of false if the hash tag phrase was found in the post
     */
    public boolean containsHashTagPhrase(String phrase) {
        if (message.contains("#") && message.length() > 1) {
            int i = message.indexOf("#") + 1;
            boolean done = false;
            // Extract the hash tag phrase from post
            while (!done) {
                if (i + 1 > message.length())
                    done = true;
                else {
                    String letter = message.substring(i, i + 1);
                    if (letter.equals(" "))
                        done = true;
                    else
                        i++;
                }
            }
            String phraseCompare = message.substring(message.indexOf("#") + 1, i).trim();
            // Return false if no content after hash tag
            if (phraseCompare.length() == 0)
                return false;
            // Check for special characters and remove if any found
            String endChar = phraseCompare.substring(phraseCompare.length() - 1);
            Pattern p = Pattern.compile("[^a-zA-Z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(endChar);
            if (m.find()) {
                phraseCompare = phraseCompare.replaceAll("[^a-zA-Z0-9 ]", "");
            }
            // Compare if phrase searching for equals phrase in the post
            if (phrase.equals(phraseCompare)){
                return true;
            }
            else
                return false;
        }
        else
            return false;
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
