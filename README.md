# Instagram Feed Manager

## Project Overview

This project is an Instagram Feed Manager developed in Java for the Data Structures and Algorithms course. The application is designed to manage users, their posts, likes, and generate personalized feeds based on user interactions.

Features

## 1. User Management

Users can be created with unique IDs.

Users can follow or unfollow other users.

## 2. Post Management

Users can create posts with unique IDs and content.

Users can view posts from other users.

Users can like/unlike posts.

## 3. Feed Generation

A user's feed consists of posts from followed users.

The feed prioritizes posts with the most likes.

If posts have the same number of likes, they are sorted lexicographically by post ID.

Users should not see their own posts or posts they have already seen in their feed.

## 4. Sorting Posts

Users can sort their own posts based on the number of likes.

If two posts have the same number of likes, the one with the larger lexicographical ID appears first.

## Input & Output

The program processes an input file containing user commands.

A log file is generated to record the operations and their results.

## Example Commands

create_user user1
follow_user user1 user2
create_post user1 post1 Hello
see_post user2 post1
generate_feed user1 5
sort_posts user1

## Example Log Output

Created user with Id user1.
user1 followed user2.
user1 created a post with Id post1.
user2 saw post1.
Feed for user1:
Post ID: post1, Author: user2, Likes: 0

## Implementation Details

The program uses efficient data structures to handle large datasets and ensure optimal performance.

Priority Queue (Heap): Used for efficiently selecting top-liked posts.

HashMaps & HashSets: Used for fast lookups of users, posts, and interactions.

Sorting Algorithms: Implemented for ordering posts correctly.

# Running the Project

## Compilation & Execution

javac *.java
java Main <input_file> <output_file>

<input_file>: Path to the input file containing commands.

<output_file>: Path to save the generated log output.


## Notes

Execution time constraints are optimized for large datasets.

Program behavior follows the given specification strictly.

Error handling ensures invalid operations are logged correctly.








