package group.project;

import java.util.ArrayList;

public class Profile {
    public static String username;
    private static ArrayList<Post> userPosts = new ArrayList<>();
    private static ArrayList<Post> likedPosts = new ArrayList<>();
    private static ArrayList<User> following = new ArrayList<>();
    private static ArrayList<User> followers = new ArrayList<>();

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
