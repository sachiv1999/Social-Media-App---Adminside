package com.example.socialmedia;

import java.util.List;

public class Post {
    private String userId;
    private String postId;
    private String content;
    private String imageDescription;
    private List<String> likedBy;
    private long likeCount;

    public Post() {}

    public Post(String postId, String userId, String content, String imageDescription, List<String> likedBy, long likeCount) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.imageDescription = imageDescription;
        this.likedBy = likedBy;
        this.likeCount = likeCount;
    }

    public String getUserId() { return userId; }
    public String getPostId() { return postId; }
    public String getContent() { return content; }
    public String getImageDescription() { return imageDescription; }
    public List<String> getLikedBy() { return likedBy; }
    public long getLikeCount() { return likeCount; }
}

