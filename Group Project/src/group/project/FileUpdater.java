package group.project;

import java.io.*;
import java.util.Scanner;

public class FileUpdater {

    public static void updateTaggedPostFile(String content, String taggedUser){
        // Check if user exists
        if (UserRepository.search(taggedUser) != -1) {
            try {
                Scanner file = new Scanner(new File("TaggedPost.txt"));
                PrintWriter writer = new PrintWriter("temp.txt");
                String nxtItem;
                boolean foundUser = false;

                while (!foundUser && file.hasNext()) {
                    nxtItem = file.next();

                    if (!nxtItem.equals(taggedUser))
                        // Print the item
                        writer.println(nxtItem);
                    else {
                        // Print the user name and new liked ID
                        foundUser = true;
                        writer.println(nxtItem);
                        writer.println(PostRepository.currentID.toString());
                    }
                }
                // Print the rest of the file
                while (file.hasNext())
                    writer.println(file.next());

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
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void addToUserLikesFile(String username, String msgID){
        try {
            Scanner file = new Scanner(new File("UserLikes.txt"));
            PrintWriter writer = new PrintWriter("temp.txt");

            String nxtItem;
            boolean foundUser = false;

            while (!foundUser && file.hasNext()) {
                nxtItem = file.next();

                if (!nxtItem.equals(username))
                    // Print the item
                    writer.println(nxtItem);
                else {
                    // Print the user name and new liked ID
                    foundUser = true;
                    writer.println(nxtItem);
                    writer.println(msgID);
                }
            }

            while (file.hasNext())
                writer.println(file.next());

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
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static void removeFromUserLikesFile(String username, String msgID) {
        try {
            Scanner file = new Scanner(new File("UserLikes.txt"));
            boolean foundPost = false;

            PrintWriter writer = new PrintWriter("temp.txt");

            String nxtItem;

            boolean foundUser = false;

            while (!foundUser && file.hasNext()) {
                nxtItem = file.next();

                if (!nxtItem.equals(username))
                    writer.println(nxtItem);
                else {
                    writer.println(nxtItem);
                    foundUser = true;
                }
            }

            while (!foundPost && file.hasNext()) {
                nxtItem = file.next();

                if (nxtItem.equals(msgID))
                    foundPost = true;
                else
                    writer.println(nxtItem);
            }
            while (file.hasNext())
                writer.println(file.next());

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

                while ((bytesRead = input.read(buf)) > 0)
                    output.write(buf, 0, bytesRead);
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

            // Insert new user into Following.txt

        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
    public static void addToFollowersFile(String username, String follower){
        try {
            Scanner file = new Scanner(new File("Followers.txt"));
            PrintWriter writer = new PrintWriter("temp2.txt");

            String nxtItem;
            boolean foundUser = false;

            while (!foundUser && file.hasNext()) {
                nxtItem = file.next();

                if (!nxtItem.equals(username))
                    // Print the item
                    writer.println(nxtItem);
                else {
                    // Print the user name and new follower's username
                    foundUser = true;
                    writer.println(nxtItem);
                    writer.println(follower);
                }
            }

            while (file.hasNext())
                writer.println(file.next());

            writer.close();

            //Source file, from which content will be copied
            File sourceFile = new File("temp2.txt");

            // destination file, where the content to be pasted
            File destFile = new File("Followers.txt");

            //if file does not exist then create one
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
                    }
                    if (null != output)
                        output.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
    public static void addToFollowingFile(String username, String following){
        try {
            Scanner file = new Scanner(new File("Following.txt"));
            PrintWriter writer = new PrintWriter("temp2.txt");

            String nxtItem;
            boolean foundUser = false;

            while (!foundUser && file.hasNext()) {
                nxtItem = file.next();

                if (!nxtItem.equals(username))
                    // Print the item
                    writer.println(nxtItem);
                else {
                    // Print the user name and new liked ID
                    foundUser = true;
                    writer.println(nxtItem);
                    writer.println(following);
                }
            }

            while (file.hasNext())
                writer.println(file.next());

            writer.close();

            //Source file, from which content will be copied
            File sourceFile = new File("temp2.txt");

            // destination file, where the content to be pasted
            File destFile = new File("Following.txt");

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
                    }
                    if (null != output)
                        output.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
    public static void removeFromFollowersFile(String username, String follower) {
        try {
            Scanner file = new Scanner(new File("Followers.txt"));
            boolean foundPost = false;

            PrintWriter writer = new PrintWriter("temp2.txt");

            String nxtItem;

            boolean foundUser = false;

            while (!foundUser && file.hasNext()) {
                nxtItem = file.next();

                if (!nxtItem.equals(username))
                    writer.println(nxtItem);
                else {
                    writer.println(nxtItem);
                    foundUser = true;
                }
            }

            while (!foundPost && file.hasNext()) {
                nxtItem = file.next();

                if (nxtItem.equals(follower))
                    foundPost = true;
                else
                    writer.println(nxtItem);
            }
            while (file.hasNext())
                writer.println(file.next());

            writer.close();


            //Source file, from which content will be copied
            File sourceFile = new File("temp2.txt");

            // destination file, where the content to be pasted
            File destFile = new File("Followers.txt");

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
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != input) {
                        input.close();
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
    
    public static void removeFromFollowingFile(String username, String following) {
        try {
            Scanner file = new Scanner(new File("Following.txt"));
            boolean foundPost = false;

            PrintWriter writer = new PrintWriter("temp2.txt");

            String nxtItem;

            boolean foundUser = false;

            while (!foundUser && file.hasNext()) {
                nxtItem = file.next();

                if (!nxtItem.equals(username))
                    writer.println(nxtItem);
                else {
                    writer.println(nxtItem);
                    foundUser = true;
                }
            }

            while (!foundPost && file.hasNext()) {
                nxtItem = file.next();

                if (nxtItem.equals(following))
                    foundPost = true;
                else
                    writer.println(nxtItem);
            }
            while (file.hasNext())
                writer.println(file.next());

            writer.close();


            //Source file, from which content will be copied
            File sourceFile = new File("temp2.txt");

            // destination file, where the content to be pasted
            File destFile = new File("Following.txt");

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
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != input) {
                        input.close();
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
    
}
