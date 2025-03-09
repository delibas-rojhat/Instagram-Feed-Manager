public class Post {
    private String postId;
    private String content;
    private User author;
    private int likes;
    public Post(String postId, String content, User author) {
        this.postId = postId;
        this.content = content;
        this.author = author;
        this.likes = 0;
    }
    public void like() {
        this.likes++;
    }

    public void unlike() {
        this.likes--;
    }

    public String getPostId(){
        return postId;
    }

    public String getContent(){
        return content;
    }

    public User getAuthor(){
        return author;
    }

    public int getLikes(){
        return likes;
    }
}
