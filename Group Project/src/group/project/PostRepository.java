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

    public static void add(Post newPost){
        postRepo.add(newPost);
    }

    public static void deletePost(Post removePost){
        // Delete post from index returned by search
        postRepo.remove(search(removePost.getMsg_ID()));
    }

    public static int search(String ID){
        for (int i=0; i<postRepo.size(); i++){
            if (postRepo.get(i).getMsg_ID().equals(ID)){
                return i;
            }
        }
        return -1;
    }

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

    public static Post getPost(int ID){
        return postRepo.get(ID);
    }

    public static int getRepoSize(){return postRepo.size();}


}
