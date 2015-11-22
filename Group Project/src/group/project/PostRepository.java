package group.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class PostRepository {
    private static ArrayList<Post> postRepo;
    public static Integer currentID = 0;

    /**
     * Populate the post repo from the Post.txt file
     */
    public static void populatePostRepository(){
        try {
            postRepo = new ArrayList<>();
            Scanner file = new Scanner(new File("Post.txt"));

            while (file.hasNext()){
                String author = file.next();
                String Msg_ID = file.next();
                boolean isPublic;
                if (file.next().equals("Public"))
                    isPublic = true;
                else
                    isPublic = false;
                Integer likeCount = new Integer(file.next());
                String message = file.nextLine();
                add(new Post(Msg_ID, author, message, isPublic, likeCount));
                if (!file.hasNext()) {
                    Integer ID = new Integer(Msg_ID);
                    currentID = ID;
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Add new post into the post repo
     * @param newPost New post being added into the repo
     */
    public static void add(Post newPost){
        postRepo.add(newPost);
    }

    /**
     * Remove a post from the post repo
     * @param removePost The post to be removed in the repo
     */
    public static void deletePost(Post removePost){
        // Delete post from index returned by search
        postRepo.remove(search(removePost.getMsg_ID()));
    }

    /**
     * Search for a post in the post repo
     * @param ID The post ID to be used as the search key
     * @return The index if post found or -1 if not found
     */
    public static int search(String ID){
        for (int i=0; i<postRepo.size(); i++){
            if (postRepo.get(i).getMsg_ID().equals(ID)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Write all posts currently in the repo to the Post.txt file
     */
    public static void saveAllPosts(){
        try {
            PrintWriter out = new PrintWriter(new FileWriter("Post.txt"));
            for (int i=0; i<postRepo.size(); i++){
                out.println(postRepo.get(i).toString());
            }
            out.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Get a post at a specified index from the repo
     * @param index Index in post repo to be returned
     * @return The post at a given index in the post repo
     */
    public static Post getPost(int index) {return postRepo.get(index);}

    /**
     * Get the current size of the post repo
     * @return The size of the post repo
     */
    public static int getRepoSize() {return postRepo.size();}
}
