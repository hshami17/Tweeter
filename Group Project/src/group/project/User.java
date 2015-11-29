package group.project;

public class User implements Comparable<User> {
    private String username;
    private String userID;
    private String gender;
    private String age;
    private String userBio;

    /**
     * Default constructor for a user object
     */
    User(){
        username = "NULL!";
        gender = "";
        age = "";
        userBio = "";
    }

    /**
     * Non-default constructor for a user object
     * @param username The username of the user
     * @param userID The user ID of the user
     * @param gender The gender of the user
     * @param age The age of the user
     * @param userBio The bio of the user
     */
    User(String username, String userID, String gender, String age, String userBio){
        this.username = username;
        this.userID = userID;
        this.gender = gender;
        this.age = age;
        this.userBio = userBio;
    }

    /**
     * Set the username of the user
     * @param username A string containing the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the username of the user
     * @return A string containing the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the user ID of the user
     * @param userID A string containing the user ID of the user
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Get the user ID of the user
     * @return A string containing the user ID of the user
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Set the gender of the user
     * @param gender A string containing the gender of the user
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Get the gender of the user
     * @return A string containing the gender of the user
     */
    public String getGender() {
        return gender;
    }

    /**
     * Set the age of the user
     * @param age A string containing the age of the user
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Get the age of the user
     * @return A string containing the age of the user
     */
    public String getAge() {
        return age;
    }

    /**
     * Set the user bio of the user
     * @param userBio A string containing the bio of the user
     */
    public void setUserBio(String userBio) {this.userBio = userBio;}

    /**
     * Get the user bio for the user
     * @return A string containing the user bio of the user
     */
    public String getUserBio() {return userBio;}

    /**
     * Print the information of the user into a string
     * @return A string containing all the attributes of the user
     */
    public String toString(){
        return username + " " + userID + " " + gender + " " + age + " " + userBio;
    }

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.getUsername());
    }
}
