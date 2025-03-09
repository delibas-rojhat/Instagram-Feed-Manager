import java.util.ArrayList;

public class FeedManager {
    private MyHashMap<String, User> users;  // HashMap to store users
    private MyHashMap<String, Post> posts;  // HashMap to store posts

    public FeedManager() {
        users = new MyHashMap<>();
        posts = new MyHashMap<>();
    }

    public String createUser(String userId) {
        if (users.get(userId) != null) { // if this user is already created  return error
            return "Some error occurred in create_user.";
        }
        User user = new User(userId);
        users.put(userId, user);
        return "Created user with Id " + userId + ".";
    }

    public String createPost(String userId, String postId, String content) {
        User user = users.get(userId);  // find the user
        if (user == null) { // if there is no user
            return "Some error occurred in create_post.";
        }
        if (posts.get(postId) != null) {
            return "Some error occurred in create_post.";  // if this post is already created return error
        }
        Post post = new Post(postId, content, user);
        posts.put(postId, post);
        user.getPosts().add(post);
        return userId + " created a post with Id " + postId + ".";
    }

    public String seePost(String userId, String postId) {
        User user = users.get(userId);
        Post post = posts.get(postId);

        if (user == null || post == null) { // if there is no user or post
            return "Some error occurred in see_post.";
        }

        if (user.getSeenPosts().contains(postId)) { // seeing the same post one more time
            return userId + " saw " + postId + ".";
        } else {                                    // seeing the post first time
            user.getSeenPosts().add(postId);
            return userId + " saw " + postId + ".";
        }

    }

    public String seeAllPostsFromUser(String viewerId, String viewedId) {
        User viewer = users.get(viewerId);
        User viewed = users.get(viewedId);

        if (viewer == null || viewed == null) {
            return "Some error occurred in see_all_posts_from_user.";
        }

        for (Post post : viewed.getPosts()) {
            viewer.getSeenPosts().add(post.getPostId());
        }

        return viewerId + " saw all posts of " + viewedId + ".";
    }

    public String toggleLike(String userId, String postId) {
        User user = users.get(userId);
        Post post = posts.get(postId);

        if (user == null || post == null) {
            return "Some error occurred in toggle_like.";
        }
        if (!user.getSeenPosts().contains(postId)) {
            user.getSeenPosts().add(postId); // adds the post to seenPosts
        }
        if (user.getLikedPosts().contains(postId)) { // if liked the post before, unlikes it
            post.unlike();
            user.getLikedPosts().remove(postId);
            return userId + " unliked " + postId + ".";
        } else {                                    // likes if didn't before
            post.like();
            user.getLikedPosts().add(postId);
            return userId + " liked " + postId + ".";
        }
    }

    public String generateFeed(String userId, int num) {
        User user = users.get(userId);
        if (user == null) {
            return "Some error occurred in generate_feed.";
        }
        MyPriorityQueue feedQueue = new MyPriorityQueue(); //  using priority queue to find the post has most like

        // adding posts of followed users to priority queue
        for (User followedUser : user.getFollowing()) {
            for (Post post : followedUser.getPosts()) {
                if (!user.getSeenPosts().contains(post.getPostId())) { // checking this post is seen before
                    feedQueue.insert(post);  // adding post to queue
                }
            }
        }

        StringBuilder finalString = new StringBuilder(); // string which will be log to file
        finalString.append("Feed for ").append(userId).append(":\n");
        int count = 0;

        // taking posts and adding them to final string
        while (!feedQueue.isEmpty() && count < num) { // checks there are enough post in feedQueue
            Post post = feedQueue.deleteMax();  // returns the post that has max like
            finalString.append("Post ID: ").append(post.getPostId())
                    .append(", Author: ").append(post.getAuthor().getUserId())
                    .append(", Likes: ").append(post.getLikes()).append("\n");
            count++;
        }

        if (count < num) { // if post number is insufficient
            finalString.append("No more posts available for ").append(userId).append(".\n");
        }

        return finalString.toString().trim();
    }

    public String scrollThroughFeed(String userId, int num, int[] likeActions) {
        User user = users.get(userId);

        if (user == null) {
            return "Some error occurred in scroll_through_feed.";
        }

        MyPriorityQueue feedQueue = new MyPriorityQueue();  // using priority queue to find the post has most like

        // adding posts of followed users to priority queue
        for (User followedUser : user.getFollowing()) {
            for (Post post : followedUser.getPosts()) {
                if (!user.getSeenPosts().contains(post.getPostId())) {
                    feedQueue.insert(post);
                }
            }
        }

        StringBuilder finalString = new StringBuilder(); // string which will be log to file
        finalString.append(userId).append(" is scrolling through feed:\n");
        int count = 0; // number of posts

        while (!feedQueue.isEmpty() && count < num) {
            Post post = feedQueue.deleteMax();
            user.getSeenPosts().add(post.getPostId()); // seeing the post

            // if likeActions is equal to 1, press the like button
            if (likeActions[count] == 1) {
                if (user.getLikedPosts().contains(post.getPostId())) { // post is liked already, so unlike
                    post.unlike();
                    user.getLikedPosts().remove(post.getPostId());
                    finalString.append(userId).append(" saw ").append(post.getPostId())
                            .append(" while scrolling and clicked the like button.\n");
                } else { // like the post
                    post.like();
                    user.getLikedPosts().add(post.getPostId());
                    finalString.append(userId).append(" saw ").append(post.getPostId())
                            .append(" while scrolling and clicked the like button.\n");
                }
            } else { // likeAction is 0 so just saw the post
                finalString.append(userId).append(" saw ").append(post.getPostId())
                        .append(" while scrolling.\n");
            }
            count++;
        }

        if (count < num) { // post number is insufficient
            finalString.append("No more posts in feed.\n");
        }

        return finalString.toString().trim();
    }

    public String sort_posts(String userId){
        MyPriorityQueue postQueue = new MyPriorityQueue(); // using priority queue to find the post has most like
        User user = users.get(userId);
        if (user == null) {
            return "Some error occurred in sort_posts.";
        }
        ArrayList<Post> usersPost = user.getPosts(); // returns user's posts

        StringBuilder finalString = new StringBuilder(); // string which will be log to file
        finalString.append("Sorting ").append(user.getUserId()).append("'s posts:\n");
        if (usersPost.isEmpty()){
            finalString.append("No posts from ").append(userId).append(".");
            return finalString.toString().trim();
        }
        for (Post post : usersPost){
            postQueue.insert(post); // adding user's posts to priority queue
        }
        while (!postQueue.isEmpty()){
            Post biggestPost = postQueue.deleteMax();
            finalString.append(biggestPost.getPostId()).append(", Likes: ").append(biggestPost.getLikes()).append("\n");
        }
        return finalString.toString().trim();
    }

    public User getUser(String userId) {
        return users.get(userId); // finding the user via userId
    }


    public Post getPost(String postId) {
        return posts.get(postId);  // finding the post via postId
    }


}


