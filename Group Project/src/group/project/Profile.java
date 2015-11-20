package group.project;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Profile {
    public static String username;
    public static String password;
    private static ArrayList<Post> userPosts = new ArrayList<>();
    private static ArrayList<Post> likedPosts = new ArrayList<>();
    private static ArrayList<Post> taggedPosts = new ArrayList<>();
    private static ArrayList<User> following = new ArrayList<>();
    private static ArrayList<User> followers = new ArrayList<>();

    public static void clear(){
        username = "";
        userPosts = new ArrayList<>();
        likedPosts = new ArrayList<>();
        taggedPosts = new ArrayList<>();
        following = new ArrayList<>();
        followers = new ArrayList<>();
    }

    public static void newPost(String content, boolean isPublic) {
        PostRepository.currentID++;
        // Add new user post to post repository
        PostRepository.add(new Post(PostRepository.currentID.toString(),
                username, content, isPublic, 0));
        // Add post to current users repository
        addPost(PostRepository.currentID.toString(), username, content, isPublic, 0);


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
            FileUpdater.updateTaggedPostFile(content, taggedUser);
        }
    }

    private static void addPost(String ID, String username, String content, boolean isPublic, int likeCount){
        userPosts.add(new Post(PostRepository.currentID.toString(),
                username, content, isPublic, likeCount));
    }

    public static void addLikedPost(Post liked) {
        likedPosts.add(liked);
        FileUpdater.addToUserLikesFile(username, liked.getMsg_ID());
    }

    public static void addTaggedPost(Post tagged){
        taggedPosts.add(tagged);
    }

    public static void removeLikedPost(String ID){
        likedPosts.remove(searchLikedPosts(ID));
        FileUpdater.removeFromUserLikesFile(username, ID);
    }

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

    public static void retrieveTaggedPosts(){
        try {
            if (PostRepository.getRepoSize() != 0) {
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

    public static int taggedPostSize() {return taggedPosts.size();}

    public static Post getTaggedPost(int index) {return taggedPosts.get(index);}

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
