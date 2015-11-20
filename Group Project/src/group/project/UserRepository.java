import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class UserRepository {
    private static ArrayList<User> userRepo;

    public static void populateUserRepository(){
        try {
            userRepo = new ArrayList<>();
            Scanner file = new Scanner(new File("UserInfo.txt"));

            while (file.hasNext()){
                String username = file.next();
                String gender = file.next();
                String age = file.next();
                String userBio = file.nextLine();
                add(new User(username, gender, age, userBio));
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

    public static User getUser(String username){
        int key = search(username);
        if (key != -1){
            return userRepo.get(key);
        }
        else
            return new User();
    }

    public static int getRepoSize() {return userRepo.size();}

    public static void add(User newUser) {userRepo.add(newUser);}
}
