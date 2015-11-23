package group.project;

public class User implements Comparable<User> {
    private String username;
    private String userID;
    private String gender;
    private String age;
    private String userBio;

    User(){
        username = "";
        gender = "";
        age = "";
        userBio = "";
    }

    User(String username, String userID, String gender, String age, String userBio){
        this.username = username;
        this.userID = userID;
        this.gender = gender;
        this.age = age;
        this.userBio = userBio;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAge() {
        return age;
    }

    public void setUserBio(String userBio) {this.userBio = userBio;}

    public String getUserBio() {return userBio;}

    public String toString(){
        return username + " " + userID + " " + gender + " " + age + " " + userBio;
    }

    @Override
    public int compareTo(User o) {
        return username.compareTo(o.getUsername());
    }
}
