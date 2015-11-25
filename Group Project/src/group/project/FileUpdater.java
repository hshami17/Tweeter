package group.project;

import java.io.*;
import java.util.Scanner;

public class FileUpdater {
    /**
     * Update the TaggedPost.txt file when user is tagged in a new post.
     * @param msgID Message ID user was tagged in
     * @param taggedUser The user that was tagged in the post
     */
    public static void updateTaggedPostFile(String msgID, String taggedUser){
        // Check if user exists
        if (UserRepository.search(taggedUser) != -1) {
            try {
                Scanner file = new Scanner(new File("TaggedPost.txt"));
                PrintWriter writer = new PrintWriter("temp.txt");
                String nxtItem;
                boolean foundUser = false;

                while (!foundUser && file.hasNext()) {
                    nxtItem = file.next();
                    writer.println(nxtItem);
                    // Print new liked ID when user found
                    if (nxtItem.equals(taggedUser)) {
                        foundUser = true;
                        writer.println(msgID);
                    }
                }
                // Print the rest of the file
                while (file.hasNext())
                    writer.println(file.next());

                writer.close();

                // Transfer new info into tagged post file
                transferFileContent("temp.txt", "TaggedPost.txt");
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Add new liked post ID to the user in the UserLikes.txt file.
     * @param username User who liked the post
     * @param msgID ID of the newly liked post
     */
    public static void addToUserLikesFile(String username, String msgID){
        try {
            // Open user likes file
            Scanner file = new Scanner(new File("UserLikes.txt"));
            PrintWriter writer = new PrintWriter("temp.txt");

            String nxtItem;
            boolean foundUser = false;
            // Keep reading file while user is not found
            while (!foundUser && file.hasNext()) {
                nxtItem = file.next();
                writer.println(nxtItem);
                // Print the new liked ID when user found
                if (nxtItem.equals(username)) {
                    foundUser = true;
                    writer.println(msgID);
                }
            }
            // Print the rest of the file
            while (file.hasNext())
                writer.println(file.next());

            writer.close();

            // Transfer new info into user likes file
            transferFileContent("temp.txt", "UserLikes.txt");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Remove liked post ID from user in UserLikes.txt file.
     * @param username User who un-liked post
     * @param msgID ID for the un-liked post
     */
    public static void removeFromUserLikesFile(String username, String msgID) {
        try {
            // Open user likes file
            Scanner file = new Scanner(new File("UserLikes.txt"));
            boolean foundPost = false;

            PrintWriter writer = new PrintWriter("temp.txt");

            String nxtItem;

            boolean foundUser = false;

            // Print items while user not found and file has contents
            while (!foundUser && file.hasNext()) {
                nxtItem = file.next();
                // Keep printing items to temp file
                writer.println(nxtItem);
                // User found
                if (nxtItem.equals(username))
                    foundUser = true;
            }

            // Look through users liked post while post not found
            while (!foundPost && file.hasNext()) {
                nxtItem = file.next();
                // If post found, then skip line print
                if (nxtItem.equals(msgID))
                    foundPost = true;
                else
                    writer.println(nxtItem);
            }
            // Print the rest of the file
            while (file.hasNext())
                writer.println(file.next());

            writer.close();

            // Transfer new info into user likes file
            transferFileContent("temp.txt", "UserLikes.txt");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Add new username to the user who was followed into Followers.txt.
     * @param followedUser User who was followed
     * @param ID ID of new follower for the selected user
     */
    public static void addToFollowersFile(String followedUser, String ID){
        try {
            // Open the followers file
            Scanner file = new Scanner(new File("Followers.txt"));
            PrintWriter writer = new PrintWriter("temp.txt");

            String nxtItem;
            boolean foundUser = false;

            while (!foundUser && file.hasNext()) {
                nxtItem = file.next();
                writer.println(nxtItem);

                // Print the follower ID when user found
                if (nxtItem.equals(followedUser)) {
                    writer.println(ID);
                    foundUser = true;
                }

            }

            // Print rest of file
            while (file.hasNext())
                writer.println(file.next());

            writer.close();

            // Transfer new info into followers file
            transferFileContent("temp.txt", "Followers.txt");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Add new user being followed into Following.txt file.
     * @param username User who followed new user
     * @param ID ID of user who was selected to follow
     */
    public static void addToFollowingFile(String username, String ID){
        try {
            // Open the following file
            Scanner file = new Scanner(new File("Following.txt"));
            PrintWriter writer = new PrintWriter("temp.txt");

            String nxtItem;
            boolean foundUser = false;

            while (!foundUser && file.hasNext()) {
                nxtItem = file.next();
                writer.println(nxtItem);

                // Print the new user ID being followed when user found
                if (nxtItem.equals(username)) {
                    foundUser = true;
                    writer.println(ID);
                }
            }

            // Print rest of file
            while (file.hasNext())
                writer.println(file.next());

            writer.close();

            // Transfer new info into following file
            transferFileContent("temp.txt", "Following.txt");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Remove user from following list of a user from Follower.txt file.
     * @param ID ID of user who un-followed a user
     * @param username User who was un-followed
     */
    public static void removeFromFollowersFile(String ID, String username) {
        try {
            // Open the followers file
            Scanner file = new Scanner(new File("Followers.txt"));
            boolean foundFollower = false;

            PrintWriter writer = new PrintWriter("temp.txt");

            String nxtItem;

            boolean foundUser = false;

            // Keep reading file until user ID is found
            while (!foundUser && file.hasNext()) {
                nxtItem = file.next();
                writer.println(nxtItem);
                if (nxtItem.equals(username))
                    foundUser = true;
            }

            // Find the follower ID to remove
            while (!foundFollower && file.hasNext()) {
                nxtItem = file.next();

                if (nxtItem.equals(ID))
                    foundFollower = true;
                else
                    writer.println(nxtItem);
            }
            // Print rest of file
            while (file.hasNext())
                writer.println(file.next());

            writer.close();

            // Transfer new info into the followers file
            transferFileContent("temp.txt", "Followers.txt");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Remove user a users following list in Following.txt file.
     * @param username User who un-followed selected user
     * @param ID User who was un-followed
     */
    public static void removeFromFollowingFile(String username, String ID) {
        try {
            // Open the following file
            Scanner file = new Scanner(new File("Following.txt"));
            boolean foundFollowing = false;

            PrintWriter writer = new PrintWriter("temp.txt");

            String nxtItem;

            boolean foundUser = false;

            // Keep reading file until user found
            while (!foundUser && file.hasNext()) {
                nxtItem = file.next();
                writer.println(nxtItem);

                if (nxtItem.equals(username))
                    foundUser = true;
            }

            // Find the following user ID to remove
            while (!foundFollowing && file.hasNext()) {
                nxtItem = file.next();

                if (nxtItem.equals(ID))
                    foundFollowing = true;
                else
                    writer.println(nxtItem);
            }
            // Print rest of file
            while (file.hasNext())
                writer.println(file.next());

            writer.close();

            // Transfer new info into the following file
            transferFileContent("temp.txt", "Following.txt");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Update the username when changed across all files
     * @param oldUsername The old username set by user
     * @param newUsername New username set by user
     */
    public static void updateUsername(String oldUsername, String newUsername){
        try {
            // Open user likes file
            Scanner file = new Scanner(new File("UserLikes.txt"));
            PrintWriter writer = new PrintWriter("temp.txt");
            String nxtItem;
            boolean userFound = false;

            // Read contents of file
            while (file.hasNext() && !userFound) {
                nxtItem = file.next();

                // Write new username when old one found
                if (nxtItem.equals(oldUsername)) {
                    userFound = true;
                    writer.println(newUsername);
                }
                else
                    writer.println(nxtItem);
            }
            // Print rest of file
            while (file.hasNext())
                writer.println(file.next());
            writer.close();
            // Transfer file contents into user likes file
            transferFileContent("temp.txt", "UserLikes.txt");

            // Open tagged post file
            file = new Scanner(new File("TaggedPost.txt"));
            writer = new PrintWriter("temp.txt");
            userFound = false;
            // Read contents of file
            while (file.hasNext() && !userFound) {
                nxtItem = file.next();

                // Write new username when old one found
                if (nxtItem.equals(oldUsername)) {
                    userFound = true;
                    writer.println(newUsername);
                }
                else
                    writer.println(nxtItem);
            }
            // Print rest of file
            while (file.hasNext())
                writer.println(file.next());
            writer.close();
            // Transfer file contents into tagged post file
            transferFileContent("temp.txt", "TaggedPost.txt");


            // Open the login info file
            file = new Scanner(new File("LoginInfo.txt"));
            writer = new PrintWriter("temp.txt");
            while (file.hasNext()){
                nxtItem = file.next();

                if (nxtItem.equals(oldUsername)) {
                    writer.println(newUsername + " " + Profile.password);
                    String skip = file.next();
                }
                else
                    writer.println(nxtItem + " " + file.next());
            }
            writer.close();
            transferFileContent("temp.txt", "LoginInfo.txt");

            // Open the post file
            file = new Scanner(new File("Post.txt"));
            writer = new PrintWriter("temp.txt");
            while (file.hasNext()){
                nxtItem = file.next();

                // If the old username is found for author, replace with new username
                if (nxtItem.equals(oldUsername)) {
                    writer.println(newUsername + " " + file.next() + " " + file.next() + " " +
                            file.next() + " " + file.nextLine());
                }
                // Else print the line as it was read
                else{
                    writer.println(nxtItem + " " + file.next() + " " + file.next() + " " +
                            file.next() + " " + file.nextLine());
                }
            }
            writer.close();
            // Transfer info into the post file
            transferFileContent("temp.txt", "Post.txt");
            // Repopulate post and tagged post repo to reflect any changes
            PostRepository.populatePostRepository();
            Profile.retrieveTaggedPosts();

            // Open the followers file
            file = new Scanner(new File("Followers.txt"));
            writer = new PrintWriter("temp.txt");
            userFound = false;
            // Read contents of file
            while (file.hasNext() && !userFound) {
                nxtItem = file.next();

                // Write new username when old one found
                if (nxtItem.equals(oldUsername)) {
                    userFound = true;
                    writer.println(newUsername);
                }
                else
                    writer.println(nxtItem);
            }
            // Print rest of file
            while (file.hasNext())
                writer.println(file.next());
            writer.close();
            // Transfer file contents into tagged post file
            transferFileContent("temp.txt", "Followers.txt");

            // Open the following file
            file = new Scanner(new File("Following.txt"));
            writer = new PrintWriter("temp.txt");
            userFound = false;
            // Read contents of file
            while (file.hasNext() && !userFound) {
                nxtItem = file.next();

                // Write new username when old one found
                if (nxtItem.equals(oldUsername)) {
                    userFound = true;
                    writer.println(newUsername);
                }
                else
                    writer.println(nxtItem);
            }
            // Print rest of file
            while (file.hasNext())
                writer.println(file.next());
            writer.close();
            // Transfer file contents into tagged post file
            transferFileContent("temp.txt", "Following.txt");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Delete user from all files
     * @param username Username of the user to remove
     */
    public static void deleteUserFromFiles(String username){
        try {
            // Delete from user likes file
            Scanner file = new Scanner(new File("UserLikes.txt"));
            PrintWriter writer = new PrintWriter("temp.txt");
            String nxtItem;

            while (file.hasNext()){
                nxtItem = file.next();

                // Check if user found
                if (nxtItem.equals(username)){
                    boolean endReached = false;
                    while (!endReached){
                        nxtItem = file.next();
                        // Keep reading lines until end reached
                        if (nxtItem.equals("END"))
                            endReached = true;
                        // If not end, then decrement liked post likes
                        else{
                            Post post = PostRepository.getPost(PostRepository.search(nxtItem));
                            post.setLikeCount(post.getLikeCount() - 1);
                            PostRepository.saveAllPosts();
                        }
                    }
                }
                // Write line if not username
                else
                    writer.println(nxtItem);
            }
            writer.close();
            transferFileContent("temp.txt", "UserLikes.txt");

            // Delete from tagged post files
            file = new Scanner(new File("TaggedPost.txt"));
            writer = new PrintWriter("temp.txt");

            while (file.hasNext()){
                nxtItem = file.next();

                // Check if user found
                if (nxtItem.equals(username)){
                    boolean endReached = false;
                    while (!endReached){
                        nxtItem = file.next();
                        // Keep reading lines until end reached
                        if (nxtItem.equals("END"))
                            endReached = true;
                    }
                }
                else
                    writer.println(nxtItem);
            }
            writer.close();
            transferFileContent("temp.txt", "TaggedPost.txt");

            // Delete from followers file
            file = new Scanner(new File("Followers.txt"));
            writer = new PrintWriter("temp.txt");

            while (file.hasNext()){
                nxtItem = file.next();

                // Check if user found
                if (nxtItem.equals(username)){
                    boolean endReached = false;
                    while (!endReached){
                        nxtItem = file.next();
                        // Keep reading lines until end reached
                        if (nxtItem.equals("END"))
                            endReached = true;
                        else {
                            // PUT REMOVE FOLLOWER CODE HERE
                        }
                    }
                }
                else
                    writer.println(nxtItem);
            }
            writer.close();
            transferFileContent("temp.txt", "Followers.txt");

            // Delete from following file
            file = new Scanner(new File("Following.txt"));
            writer = new PrintWriter("temp.txt");

            while (file.hasNext()){
                nxtItem = file.next();

                // Check if user found
                if (nxtItem.equals(username)){
                    boolean endReached = false;
                    while (!endReached){
                        nxtItem = file.next();
                        // Keep reading lines until end reached
                        if (nxtItem.equals("END"))
                            endReached = true;
                        else {
                            // PUT REMOVE FOLLOWING CODE HERE
                        }
                    }
                }
                else
                    writer.println(nxtItem);
            }
            writer.close();
            transferFileContent("temp.txt", "Following.txt");

            // Delete from login info file
            file = new Scanner(new File("LoginInfo.txt"));
            writer = new PrintWriter("temp.txt");

            while (file.hasNext()){
                nxtItem = file.next();

                if (nxtItem.equals(username)) {
                    String skip = file.next();
                }
                else
                    writer.println(nxtItem + " " + file.next());
            }
            writer.close();
            transferFileContent("temp.txt", "LoginInfo.txt");

            // Delete all their posts from the post file
            file = new Scanner(new File("Post.txt"));
            writer = new PrintWriter("temp.txt");

            while (file.hasNext()){
                nxtItem = file.next();

                // If post authored by deleted user, skip write of their post
                if (nxtItem.equals(username)) {
                    file.next();
                    file.next();
                    file.next();
                    file.nextLine();
                }
                else{
                    writer.println(nxtItem + " " + file.next() + " " + file.next() + " " +
                            file.next() + " " + file.nextLine());
                }
            }
            writer.close();
            // Transfer info to post file and reset post repo
            transferFileContent("temp.txt", "Post.txt");
            PostRepository.populatePostRepository();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Remove a deleted post ID from UserLikes.txt and TaggedPost.txt files.
     * @param ID ID that was deleted from post repo
     */
    public static void removePostID(String ID){
        try {
            // Open user likes file
            Scanner file = new Scanner(new File("UserLikes.txt"));
            PrintWriter writer = new PrintWriter("temp.txt");
            String nxtItem;

            // Read contents of file
            while (file.hasNext()) {
                nxtItem = file.next();

                // Skip write when deleted ID found
                if (nxtItem.equals(ID)) {}
                else
                    writer.println(nxtItem);
            }
            writer.close();
            // Transfer file contents into user likes file
            transferFileContent("temp.txt", "UserLikes.txt");

            // Open tagged post file
            file = new Scanner(new File("TaggedPost.txt"));
            writer = new PrintWriter("temp.txt");
            // Read contents of file
            while (file.hasNext()) {
                nxtItem = file.next();

                // Skip write when deleted ID found
                if (!nxtItem.equals(ID))
                    writer.println(nxtItem);
            }
            writer.close();
            // Transfer file contents into tagged post file
            transferFileContent("temp.txt", "TaggedPost.txt");
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Transfer info from temporary file into the main file.
     * @param sourceFilename File being transferred from
     * @param destFilename File being transferred into
     */
    private static void transferFileContent(String sourceFilename, String destFilename){
        //Source file, from which content will be copied
        File sourceFile = new File(sourceFilename);

        // destination file, where the content to be pasted
        File destFile = new File(destFilename);

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

            while ((bytesRead = input.read(buf)) > 0)
                output.write(buf, 0, bytesRead);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (null != input) {
                    input.close();
                    sourceFile.delete();
                }
                if (null != output)
                    output.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Add newly registered user into respective files.
     * @param newUser Username of the new user
     * @param password Password of the new user
     */
    public static void addNewUserToFiles(User newUser, String password){
        try{
            // Insert new login info into LoginInfo.txt
            FileWriter file = new FileWriter("LoginInfo.txt", true);
            BufferedWriter out = new BufferedWriter(file);
            out.newLine();
            out.write(newUser.getUsername() + " " + password);
            out.close();

            // Insert new user info into UserInfo.txt
            file = new FileWriter("UserInfo.txt", true);
            out = new BufferedWriter(file);
            out.newLine();
            out.write(newUser.toString());
            out.close();

            // Insert new user into UserLikes.txt
            file = new FileWriter("UserLikes.txt", true);
            out = new BufferedWriter(file);
            out.newLine();
            out.write(newUser.getUsername());
            out.newLine();
            out.write("END");
            out.close();

            // Insert new user into TaggedPost.txt
            file = new FileWriter("TaggedPost.txt", true);
            out = new BufferedWriter(file);
            out.newLine();
            out.write(newUser.getUsername());
            out.newLine();
            out.write("END");
            out.close();

            // Insert new user into Followers.txt
            file = new FileWriter("Followers.txt", true);
            out = new BufferedWriter(file);
            out.newLine();
            out.write(newUser.getUsername());
            out.newLine();
            out.write("END");
            out.close();

            // Insert new user into Following.txt
            file = new FileWriter("Following.txt", true);
            out = new BufferedWriter(file);
            out.newLine();
            out.write(newUser.getUsername());
            out.newLine();
            out.write("END");
            out.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
