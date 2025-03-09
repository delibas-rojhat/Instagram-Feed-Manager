import java.util.ArrayList;
public class User {
    private String userId;
    private ArrayList<User> following;
    private ArrayList<User> followers;
    private ArrayList<Post> posts;
    private ArrayList<String> seenPosts;
    private ArrayList<String> likedPosts;

    public User(String userId) {
        this.userId = userId;
        this.following = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.seenPosts = new ArrayList<>();
        this.likedPosts = new ArrayList<>();
    }
    public String follow(User user) {
        if (this == user) { // checking that it is following itself
            return "Some error occurred in follow_user.";
        }

        if (following.contains(user)) { // checking the user is followed already
            return "Some error occurred in follow_user.";
        }

        following.add(user); // follow the user
        user.followers.add(this);

        return this.userId + " followed " + user.userId + ".";
    }

    public String unfollow(User user) {
        if (this == user) {
            return "Some error occurred in unfollow_user.";
        }

        if (!following.contains(user)) { // checking the user is followed or not
            return "Some error occurred in unfollow_user.";
        }

        following.remove(user); // unfollow user
        user.followers.remove(this);

        return this.userId + " unfollowed " + user.userId + ".";
    }
    public String getUserId() {
        return userId;
    }

    public ArrayList<User> getFollowing() {
        return following;
    }

    public ArrayList<User> getFollowers() {
        return followers;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public ArrayList<String> getSeenPosts() {
        return seenPosts;
    }
    public ArrayList<String> getLikedPosts(){
        return likedPosts;
    }
}
