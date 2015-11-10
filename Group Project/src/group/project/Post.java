package group.project;

public class Post {
    private String Msg_ID;
    private String author;
    private String message;
    private boolean isPublic;
    private int likeCount;

    Post(String Msg_ID, String author, String message,
         boolean isPublic, int likeCount){
        this.Msg_ID = Msg_ID;
        this.author = author;
        this.message = message;
        this.isPublic = isPublic;
        this.likeCount = likeCount;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setMsg_ID(String msg_ID) {
        Msg_ID = msg_ID;
    }

    public String getMsg_ID() {
        return Msg_ID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    @Override
    public String toString() {
        String isPublicStr;
        if (isPublic())
            isPublicStr = "Public";
        else
            isPublicStr = "Private";
        return author + " " + Msg_ID + " " + isPublicStr + " " + likeCount + " " + message;
    }
}
