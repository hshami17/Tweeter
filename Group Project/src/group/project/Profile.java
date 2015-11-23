package group.project;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Profile {
    public static String username;
    public static String password;
    private static ArrayList<Post> likedPosts = new ArrayList<>();
    private static ArrayList<Post> taggedPosts = new ArrayList<>();
    private static ArrayList<User> followings = new ArrayList<>();
    private static ArrayList<User> followers = new ArrayList<>();

    /**
     * Clear all current User info
     */
    public static void clear(){
        username = "";
        likedPosts = new ArrayList<>();
        taggedPosts = new ArrayList<>();
        followings = new ArrayList<>();
        followers = new ArrayList<>();
    }

    /**
     * Create a new post authored by current user
     * @param content The content of the post
     * @param isPublic The visibility of the post
     */
    public static void newPost(String content, boolean isPublic) {
        PostRepository.currentID++;
        // Add new user post to post repository
        PostRepository.add(new Post(PostRepository.currentID.toString(),
                username, content, isPublic, 0));

        // Check if the message contained an @ tag
        if (content.contains("@") && content.length() > 1) {
            int i = content.indexOf("@") + 1;
            boolean done = false;
            // Extract the username tagged from the post
            while (!done) {
                if (i + 1 > content.length())
                    done = true;
                else {
                    String letter = content.substring(i, i + 1);
                    if (letter.equals(" "))
                        done = true;
                    else
                        i++;
                }
            }
            String taggedUser = content.substring(content.indexOf("@") + 1, i).trim();
            if (taggedUser.length() == 0){
                return;
            }
            // Check for special characters at the end of the tagged user
            String endChar = taggedUser.substring(taggedUser.length() - 1);
            Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(endChar);
            if (m.find()){
                taggedUser = taggedUser.replaceAll("[^a-z0-9 ]", "");
            }
            // Update TaggedPost.txt
            FileUpdater.updateTaggedPostFile(PostRepository.currentID.toString(), taggedUser);
        }
    }

    /**
     * Add liked post into current users list and UserLikes.txt
     * @param liked The post that was liked
     */
    public static void addLikedPost(Post liked) {
        likedPosts.add(liked);
        FileUpdater.addToUserLikesFile(username, liked.getMsg_ID());
    }

    /**
     * Add tagged post into current users list
     * @param tagged The post the user was tagged in
     */
    public static void addTaggedPost(Post tagged){
        taggedPosts.add(tagged);
    }

    /**
     * Remove liked post from users list and UserLikes.txt
     * @param ID The post ID that was un-liked
     */
    public static void removeLikedPost(String ID){
        likedPosts.remove(searchLikedPosts(ID));
        FileUpdater.removeFromUserLikesFile(username, ID);
    }

    /**
     * Get all the currents user's liked from UserLikes.txt
     */
    public static void retrieveLikes(){
        try {
            if (PostRepository.getRepoSize() != 0) {
                // Open the user likes file
                Scanner file = new Scanner(new File("UserLikes.txt"));
                // Set flag for when current user is found
                boolean found = false;
                // Break loop if current user was found or end of file reached
                while (!found && file.hasNext()) {
                    // Get the next line
                    String nxtLine = file.next();
                    // Check if it equals to the current user username
                    if (nxtLine.equals(username)) {
                        found = true;
                        // Read the first liked post ID
                        nxtLine = file.next();
                        // Keep reading all liked post IDs until END line is read
                        while (!nxtLine.equals("END")) {
                            // Search and get the liked post from ID read and add to users liked posts
                            if (PostRepository.search(nxtLine) != -1) {
                                Post likedPost = PostRepository.getPost(PostRepository.search(nxtLine));
                                likedPosts.add(likedPost);
                            }
                            // Read the next line
                            nxtLine = file.next();
                        }

                    } else {
                        while (!nxtLine.equals("END")) {
                            nxtLine = file.next();
                        }
                    }
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Get all the posts current user was tagged in from TaggedPost.txt
     */
    public static void retrieveTaggedPosts(){
        try {
            if (PostRepository.getRepoSize() != 0) {
                taggedPosts = new ArrayList<>();
                // Open the user likes file
                Scanner file = new Scanner(new File("TaggedPost.txt"));
                // Set flag for when current user is found
                boolean found = false;
                // Break loop if current user was found or end of file reached
                while (!found && file.hasNext()) {
                    // Get the next line
                    String nxtLine = file.next();
                    // Check if it equals to the current user username
                    if (nxtLine.equals(username)) {
                        found = true;
                        // Read the first tagged post ID
                        nxtLine = file.next();
                        // Keep reading all liked post IDs until END line is read
                        while (!nxtLine.equals("END")) {
                            // Search and get the liked post from ID read and add to users liked posts
                            if (PostRepository.search(nxtLine) != -1) {
                                Post taggedPost = PostRepository.getPost(PostRepository.search(nxtLine));
                                taggedPosts.add(taggedPost);
                            }
                            // Read the next line
                            nxtLine = file.next();
                        }
                    } else {
                        while (!nxtLine.equals("END")) {
                            nxtLine = file.next();
                        }
                    }
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * The current size of the user's tagged post list
     * @return The size of the tagged post list
     */
    public static int taggedPostSize() {return taggedPosts.size();}

    /**
     * Get a tagged post at a specified index
     * @param index The index to get the post from in the list
     * @return Tagged post from the index specified
     */
    public static Post getTaggedPost(int index) {return taggedPosts.get(index);}

    /**
     * Get whether or not a certain post was liked
     * @param ID The post ID being checked
     * @return True or false if the post ID was liked
     */
    public static boolean getLikedPost(String ID){
        if (searchLikedPosts(ID) != -1)
            return true;
        else
            return false;
    }

    /**
     * Search through the user's liked post list
     * @param ID The post ID being used as the search key
     * @return The index if the post was found or -1 if not
     */
    private static int searchLikedPosts(String ID){
        for (int i=0; i<likedPosts.size(); i++){
            if (likedPosts.get(i).getMsg_ID().equals(ID)){
                return i;
            }
        }
        return -1;
    }
    
    public static void addFollower(User follower){
        followers.add(follower);
        FileUpdater.addToFollowersFile(username, follower.getUsername());
    }
    
    public static void addFollowing(User follow){
        followings.add(follow);
        FileUpdater.addToFollowingFile(username, follow.getUsername());
    }
    
    public static void removeFollower(String follower){
        //followers.remove(searchFollowerList(username));
        FileUpdater.removeFromFollowersFile(username, follower);
    }
    
    public static void removeFollowing(String following){
        //followings.remove(searchFollowingList(username));
        FileUpdater.removeFromFollowingFile(username, following);
    }
    
    public static void retrieveFollowers(){
        try {
            if (UserRepository.getRepoSize() != 0) {
                // Open the followers file
                Scanner file = new Scanner(new File("Followers.txt"));
                // Set flag for when current user is found
                boolean found = false;
                // Break loop if current user was found or end of file reached
                while (!found && file.hasNext()) {
                    // Get the next line
                    String nxtLine = file.next();
                    // Check if it equals to the current user username
                    if (nxtLine.equals(username)) {
                        found = true;
                        // Read the first follower username
                        nxtLine = file.next();
                        // Keep reading all followers until END line is read
                        while (!nxtLine.equals("END")) {
                            // Search and get the liked post from ID read and add to users liked posts
                            if (UserRepository.search(nxtLine) != -1) {
                                User follower = UserRepository.getUser(nxtLine);
                                followers.add(follower);
                            }
                            // Read the next line
                            nxtLine = file.next();
                        }

                    } else {
                        while (!nxtLine.equals("END")) {
                            nxtLine = file.next();
                        }
                    }
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
    public static void retrieveFollowing(){
        try {
            if (PostRepository.getRepoSize() != 0) {
                // Open the user likes file
                Scanner file = new Scanner(new File("Following.txt"));
                // Set flag for when current user is found
                boolean found = false;
                // Break loop if current user was found or end of file reached
                while (!found && file.hasNext()) {
                    // Get the next line
                    String nxtLine = file.next();
                    // Check if it equals to the current user username
                    if (nxtLine.equals(username)) {
                        found = true;
                        // Read the first liked post ID
                        nxtLine = file.next();
                        // Keep reading all liked post IDs until END line is read
                        while (!nxtLine.equals("END")) {
                            // Search and get the liked post from ID read and add to users liked posts
                            if (UserRepository.search(nxtLine) != -1) {
                                User following = UserRepository.getUser(nxtLine);
                                followings.add(following);
                            }
                            // Read the next line
                            nxtLine = file.next();
                        }

                    } else {
                        while (!nxtLine.equals("END")) {
                            nxtLine = file.next();
                        }
                    }
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
    public static ArrayList<User> getFollowers(){
        return followers;
    }
    
    public static ArrayList<User> getFollowings(){
        return followings;
    }
    
}
