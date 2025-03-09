import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
          return;
        }
        String inputFile = args[0];
        String outputFile = args[1];

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            FeedManager feedManager = new FeedManager();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String command = parts[0];
                String result = "";

                switch (command) {
                    case "create_user": {
                        String userId = parts[1];
                        result = feedManager.createUser(userId);
                        break;
                    }
                    case "follow_user": {
                        String followUserId1 = parts[1];
                        String followUserId2 = parts[2];
                        User user1 = feedManager.getUser(followUserId1);
                        User user2 = feedManager.getUser(followUserId2);
                        if (user1 != null && user2 != null) {
                            result = user1.follow(user2);
                        } else {
                            result = "Some error occurred in follow_user.";
                        }
                        break;
                    }
                    case "unfollow_user": {
                        String unfollowUserId1 = parts[1];
                        String unfollowUserId2 = parts[2];
                        User unfollowUser1 = feedManager.getUser(unfollowUserId1);
                        User unfollowUser2 = feedManager.getUser(unfollowUserId2);
                        if (unfollowUser1 != null && unfollowUser2 != null) {
                            result = unfollowUser1.unfollow(unfollowUser2);
                        } else {
                            result = "Some error occurred in unfollow_user.";
                        }
                        break;
                    }
                    case "create_post": {
                        String userId = parts[1];
                        String postId = parts[2];
                        String content = String.join(" ", Arrays.copyOfRange(parts, 3, parts.length));
                        result = feedManager.createPost(userId, postId, content);
                        break;
                    }
                    case "see_post": {
                        String userId = parts[1];
                        String postId = parts[2];
                        result = feedManager.seePost(userId, postId); // Yeni metot
                        break;
                    }
                    case "see_all_posts_from_user": {
                        String viewerId = parts[1];
                        String viewedId = parts[2];
                        result = feedManager.seeAllPostsFromUser(viewerId, viewedId); // Yeni metot
                        break;
                    }
                    case "toggle_like": {
                        String userId = parts[1];
                        String postId = parts[2];
                        result = feedManager.toggleLike(userId, postId); // Yeni metot
                        break;
                    }
                    case "generate_feed": {
                        String userId = parts[1];
                        int num = Integer.parseInt(parts[2]);
                        result = feedManager.generateFeed(userId, num);
                        break;
                    }
                    case "scroll_through_feed": {
                        String userId = parts[1];
                        int num = Integer.parseInt(parts[2]);

                        // create likeActions array
                        int[] likeActions = new int[parts.length - 3];
                        for (int i = 3; i < parts.length; i++) {
                            likeActions[i - 3] = Integer.parseInt(parts[i]);
                        }

                        result = feedManager.scrollThroughFeed(userId, num, likeActions);
                        break;
                    }
                    case "sort_posts": {
                        String userId = parts[1];
                        result = feedManager.sort_posts(userId);
                        break;
                    }
                }

                // writing log file
                bw.write(result);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

