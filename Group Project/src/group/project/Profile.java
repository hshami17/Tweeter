package group.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Profile {
    public static String username;
    private static ArrayList<Post> userPosts = new ArrayList<>();
    private static ArrayList<Post> likedPosts = new ArrayList<>();
    private static ArrayList<User> following = new ArrayList<>();
    private static ArrayList<User> followers = new ArrayList<>();

    public static void clear(){
        username = "";
        userPosts = new ArrayList<>();
        likedPosts = new ArrayList<>();
        following = new ArrayList<>();
        followers = new ArrayList<>();
    }

    public static void newPost(String content, boolean isPublic){
        // Add new user post to post repository
        PostRepository.add(new Post(PostRepository.currentID.toString(),
                username, content, isPublic, 0));
        // Add post to current users repository
        addPost(PostRepository.currentID.toString(), username, content, isPublic, 0);
    }

    private static void addPost(String ID, String username, String content, boolean isPublic, int likeCount){
        userPosts.add(new Post(PostRepository.currentID.toString(),
                username, content, isPublic, likeCount));
    }

    public static void addLikedPost(Post liked){
        likedPosts.add(liked);
    }

    public static void removeLikedPost(String ID){
        likedPosts.remove(searchLikedPosts(ID));
    }

    public static void retrieveLikes(){
        try {
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
                    System.out.println("\n" + "Current user is: " + nxtLine);
                    // Read the first liked post ID
                    nxtLine = file.next();
                    // Keep reading all liked post IDs until END line is read
                    while (!nxtLine.equals("END")){
                        System.out.println("Looking for this ID: " + nxtLine);
                        // Search and get the liked post from ID read and add to users liked posts
                        Post likedPost = PostRepository.getPost(PostRepository.search(nxtLine));
                        likedPosts.add(likedPost);
                        // Read the next line
                        nxtLine = file.next();
                    }

                }
                else {
                    while (!nxtLine.equals("END")) {
                        nxtLine = file.next();
                    }
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static boolean getLikedPost(String ID){
        if (searchLikedPosts(ID) != -1)
            return true;
        else
            return false;
    }

    private static int searchLikedPosts(String ID){
        for (int i=0; i<likedPosts.size(); i++){
            if (likedPosts.get(i).getMsg_ID().equals(ID)){
                return i;
            }
        }
        return -1;
    }
}
