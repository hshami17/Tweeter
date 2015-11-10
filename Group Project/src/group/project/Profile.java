package group.project;

import java.util.ArrayList;

public class Profile {
    public static String username;
    private static ArrayList<Post> userPosts = new ArrayList<>();

    public static void newPost(String content, boolean isPublic){
        // Add new user post to post repository
        PostRepository.add(new Post(PostRepository.currentID.toString(),
                username, content, isPublic, 0, false));
        // Add post to current users repository
        addPost(PostRepository.currentID.toString(), username, content, isPublic, 0, false);
    }

    private static void addPost(String ID, String username, String content,
                                boolean isPublic, int likeCount, boolean isArchived){
        userPosts.add(new Post(PostRepository.currentID.toString(),
                username, content, isPublic, likeCount, isArchived));
    }
}
