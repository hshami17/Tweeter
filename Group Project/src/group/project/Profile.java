package group.project;

import java.util.ArrayList;

public class Profile {
    public static String username;
    private static ArrayList<Post> userPosts = new ArrayList<>();

    public static void newPost(String content, boolean isPublic){
        PostRepository.add(new Post(PostRepository.currentID.toString(), username, content, isPublic));
        addPost(PostRepository.currentID.toString(), username, content, isPublic);
    }

    private static void addPost(String ID, String username, String content, boolean isPublic){
        userPosts.add(new Post(PostRepository.currentID.toString(), username, content, isPublic));
    }
}
