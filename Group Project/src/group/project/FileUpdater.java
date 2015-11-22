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
     * @param username User who was followed
     * @param follower New follower for the selected user
     */
    public static void addToFollowersFile(String username, String follower){
        try {
            // Open the followers file
            Scanner file = new Scanner(new File("Followers.txt"));
            PrintWriter writer = new PrintWriter("temp.txt");

            String nxtItem;
            boolean foundUser = false;

            while (!foundUser && file.hasNext()) {
                nxtItem = file.next();
                writer.println(nxtItem);

                // Print the follower when user found
                if (nxtItem.equals(username)) {
                    writer.println(follower);
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
     * @param following User who was selected to follow
     */
    public static void addToFollowingFile(String username, String following){
        try {
            // Open the following file
            Scanner file = new Scanner(new File("Following.txt"));
            PrintWriter writer = new PrintWriter("temp.txt");

            String nxtItem;
            boolean foundUser = false;

            while (!foundUser && file.hasNext()) {
                nxtItem = file.next();
                writer.println(nxtItem);

                // Print the new follower when user found
                if (nxtItem.equals(username)) {
                    foundUser = true;
                    writer.println(following);
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
     * @param username User who un-followed a user
     * @param follower User who was un-followed
     */
    public static void removeFromFollowersFile(String username, String follower) {
        try {
            // Open the followers file
            Scanner file = new Scanner(new File("Followers.txt"));
            boolean foundFollower = false;

            PrintWriter writer = new PrintWriter("temp.txt");

            String nxtItem;

            boolean foundUser = false;

            // Keep reading file until user is found
            while (!foundUser && file.hasNext()) {
                nxtItem = file.next();
                writer.println(nxtItem);
                if (nxtItem.equals(username))
                    foundUser = true;
            }

            // Find the follower to remove
            while (!foundFollower && file.hasNext()) {
                nxtItem = file.next();

                if (nxtItem.equals(follower))
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
     * @param following User who was un-followed
     */
    public static void removeFromFollowingFile(String username, String following) {
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

            // Find the following user to remove
            while (!foundFollowing && file.hasNext()) {
                nxtItem = file.next();

                if (nxtItem.equals(following))
                    foundFollowing = true;
                else
                    writer.println(nxtItem);
            }
            // Print rest of file
            while (file.hasNext())
                writer.println(file.next());

            writer.close();

            transferFileContent("temp.txt", "Following.txt");
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
            out.write(newUser.getUsername() + " " + newUser.getGender() +
                    " " + newUser.getAge() + " " + newUser.getUserBio());
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
