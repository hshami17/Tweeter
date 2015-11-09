package group.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PostRepository {
    private static ArrayList<Post> postRepo;
    public static Integer currentID = 0;

    PostRepository(){
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
                String message = file.nextLine();
                add(new Post(Msg_ID, author, message, isPublic));
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static void add(Post newPost){
        postRepo.add(newPost);
        currentID++;
    }

    public static Post getPost(int ID){
        return postRepo.get(ID);
    }

    public static int getRepoSize(){return postRepo.size();}


}
