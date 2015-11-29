package group.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class UserRepository {
    private static ArrayList<User> userRepo;
    public static Integer userID = 0;

    /**
     * Populate the user repo with all the registered users in the system
     */
    public static void populateUserRepository(){
        try {
            userRepo = new ArrayList<>();
            Scanner file = new Scanner(new File("UserInfo.txt"));

            while (file.hasNext()){
                String username = file.next();
                String ID = file.next();
                String gender = file.next();
                String age = file.next();
                String userBio = file.nextLine();
                add(new User(username, ID, gender, age, userBio));
                if (!file.hasNext())
                    userID = new Integer(ID);
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Look through user repository to find a user and return the
     * index key where user is located.
     * @param username The username used for search criteria
     * @return The index value where the user was found or -1
     * if not found
     */
    public static int search(String username){
        for (int i=0; i<userRepo.size(); i++){
            if (userRepo.get(i).getUsername().equals(username)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Get a specified user from the user repo
     * @param username Username for the user being retrieved
     * @return The user object being searched for
     */
    public static User getUser(String username){
        int key = search(username);
        if (key != -1){
            return userRepo.get(key);
        }
        else
            return new User();
    }

    /**
     * Get the user from the user repo at a specified index
     * @param index The index to be returned for the user repo
     * @return The user object from the specified index
     */
    public static User getUser(int index) {return userRepo.get(index);}

    public static User getUserByID(String ID){
        for (int i=0; i<userRepo.size(); i++){
            if (userRepo.get(i).getUserID().equals(ID)){
                return userRepo.get(i);
            }
        }
        return new User();
    }

    /**
     * Get the current size of the user repo
     * @return The size of the user repo
     */
    public static int getRepoSize() {return userRepo.size();}

    /**
     * Sort the users in the user repo by username
     */
    public static void sortUserRepoByUsername(){
        Collections.sort(userRepo);
    }

    /**
     * Save all users to the UserInfo.txt file
     */
    public static void saveAllUsers() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter("UserInfo.txt"));
            for (int i=0; i<userRepo.size(); i++){
                out.println(userRepo.get(i).toString());
            }
            out.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Search through user repo to see if an ID is taken
     * @param ID The ID being searched for
     * @return False if ID is not taken, true if it is
     */
    public static boolean IDtaken(String ID){
        for (int i=0; i<userRepo.size(); i++){
            if (userRepo.get(i).getUserID().equals(ID))
                return true;
        }
        return false;
    }

    /**
     * Add newly registered user into the user repo
     * @param newUser The new user object being added
     */
    public static void add(User newUser) {userRepo.add(newUser);}

    /**
     * Remove user from the user repo
     * @param username The username of the user to remove
     */
    public static void removeUser(String username) {userRepo.remove(search(username));}
}
