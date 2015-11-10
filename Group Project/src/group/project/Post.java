package group.project;

public class Post {
    private String Msg_ID;
    private String author;
    private String message;
    private boolean isPublic;
    private int likeCount;
    private boolean archived;

    Post(String Msg_ID, String author, String message,
         boolean isPublic, int likeCount, boolean archived){
        this.Msg_ID = Msg_ID;
        this.author = author;
        this.message = message;
        this.isPublic = isPublic;
        this.archived = archived;
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

    public int getLikeCount() {
        return likeCount;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isArchived() {
        return archived;
    }
}
