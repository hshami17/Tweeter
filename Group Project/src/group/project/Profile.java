package group.project;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Profile {
    public static String username;
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
            // Check if user exists
            String taggedUser = content.substring(content.indexOf("@") + 1, i).trim();
            if (UserRepository.search(taggedUser) != -1) {
                try {
                    Scanner file = new Scanner(new File("TaggedPost.txt"));
                    PrintWriter writer = new PrintWriter("temp.txt");

                    String nxtItem = "";
                    boolean foundUser = false;

                    while (!foundUser && file.hasNext()) {
                        nxtItem = file.next();

                        if (!nxtItem.equals(taggedUser)) {
                            // Print the item
                            writer.println(nxtItem);
                        } else {
                            // Print the user name and new liked ID
                            foundUser = true;
                            writer.println(nxtItem);
                            writer.println(PostRepository.currentID.toString());
                        }
                    }
                    // Print the rest of the file
                    while (file.hasNext()) {
                        writer.println(file.next());
                    }
                    writer.close();

                    //Source file, from which content will be copied
                    File sourceFile = new File("temp.txt");

                    // destination file, where the content to be pasted
                    File destFile = new File("TaggedPost.txt");

                    //if file not exist then create one
                    if (!destFile.exists()) {
                        try {
                            destFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    InputStream input = null;
                    OutputStream output = null;

                    try {
                        // FileInputStream to read streams
                        input = new FileInputStream(sourceFile);

                        // FileOutputStream to write streams
                        output = new FileOutputStream(destFile);

                        byte[] buf = new byte[1024];
                        int bytesRead;

                        while ((bytesRead = input.read(buf)) > 0) {
                            output.write(buf, 0, bytesRead);
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (null != input) {
                                input.close();
                                sourceFile.delete();
                            }

                            if (null != output) {
                                output.close();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    } // END NEW POST

    private static void addPost(String ID, String username, String content, boolean isPublic, int likeCount){
        userPosts.add(new Post(PostRepository.currentID.toString(),
                username, content, isPublic, likeCount));
    }

    public static void addLikedPost(Post liked) {
        likedPosts.add(liked);
        try {
            Scanner file = new Scanner(new File("UserLikes.txt"));
            PrintWriter writer = new PrintWriter("temp.txt");

            String nxtItem = "";
            boolean foundUser = false;

            while (!foundUser && file.hasNext()) {
                nxtItem = file.next();

                if (!nxtItem.equals(username)) {
                    // Print the item
                    writer.println(nxtItem);
                } else {
                    // Print the user name and new liked ID
                    foundUser = true;
                    writer.println(nxtItem);
                    writer.println(liked.getMsg_ID());
                }
            }

            while (file.hasNext()) {
                writer.println(file.next());
            }
            writer.close();

            //Source file, from which content will be copied
            File sourceFile = new File("temp.txt");

            // destination file, where the content to be pasted
            File destFile = new File("UserLikes.txt");

            //if file not exist then create one
            if (!destFile.exists()) {
                try {
                    destFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            InputStream input = null;
            OutputStream output = null;

            try {
                // FileInputStream to read streams
                input = new FileInputStream(sourceFile);

                // FileOutputStream to write streams
                output = new FileOutputStream(destFile);

                byte[] buf = new byte[1024];
                int bytesRead;

                while ((bytesRead = input.read(buf)) > 0) {
                    output.write(buf, 0, bytesRead);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != input) {
                        input.close();
                        sourceFile.delete();
                    }

                    if (null != output) {
                        output.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static void addTaggedPost(Post tagged){
        taggedPosts.add(tagged);
    }

    public static void removeLikedPost(String ID) throws IOException{
        likedPosts.remove(searchLikedPosts(ID));

        Scanner file = new Scanner(new File("UserLikes.txt"));
        boolean foundPost = false;

        PrintWriter writer = new PrintWriter("temp.txt");

        String nxtItem = "";

        boolean foundUser = false;

        while(!foundUser && file.hasNext()){
            nxtItem = file.next();

            if(!nxtItem.equals(username)){
                writer.println(nxtItem);
            }
            else{
                writer.println(nxtItem);
                foundUser = true;
            }
        }

        while (!foundPost && file.hasNext()){
            nxtItem = file.next();

            if (nxtItem.equals(ID)){
                foundPost = true;
            }
            else{
                writer.println(nxtItem);
            }

        }
        while (file.hasNext()){
            writer.println(file.next());
        }
        writer.close();


        //Source file, from which content will be copied
        File sourceFile = new File("temp.txt");

        // destination file, where the content to be pasted
        File destFile = new File("UserLikes.txt");

        //if file not exist then create one
        if (!destFile.exists()) {
            try {
                destFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        InputStream input = null;
        OutputStream output = null;

        try {

            // FileInputStream to read streams
            input = new FileInputStream(sourceFile);

            // FileOutputStream to write streams
            output = new FileOutputStream(destFile);

            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                if (null != input) {
                    input.close();
                    sourceFile.delete();
                }

                if (null != output) {
                    output.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


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
                       // System.out.println("\n" + "Current user is: " + nxtLine);
                        // Read the first tagged post ID
                        nxtLine = file.next();
                        // Keep reading all liked post IDs until END line is read
                        while (!nxtLine.equals("END")) {
                           // System.out.println("Looking for this ID: " + nxtLine);
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

    public static Post getTaggedPost(int index){return taggedPosts.get(index);}

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
