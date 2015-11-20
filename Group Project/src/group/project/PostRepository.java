import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static boolean containsHashTagPhrase(String phrase, int index) {
        String content = postRepo.get(index).getMessage();

        if (content.contains("#") && content.length() > 1) {
            int i = content.indexOf("#") + 1;
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
            String phraseCompare = content.substring(content.indexOf("#") + 1, i).trim();
            if (phraseCompare.length() == 0)
                return false;
            // Check for special characters at the end of the phrase
            String endChar = phraseCompare.substring(phraseCompare.length() - 1);
            Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(endChar);
            if (m.find()) {
                phraseCompare = phraseCompare.replaceAll("[^a-z0-9 ]", "");
            }
            if (phrase.equals(phraseCompare)){
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    public static Post getPost(int index){
        return postRepo.get(index);
    }

    public static int getRepoSize(){return postRepo.size();}
}
