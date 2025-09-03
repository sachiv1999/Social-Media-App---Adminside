package com.example.socialmedia;

public class FriendRequests {
    public static String from;
    public String to;
    public String status;
    public String requestId;


    public FriendRequests() {}

    public FriendRequests(String from, String to, String status) {
        this.from = from;
        this.to = to;
        this.status = status;
    }

    public static String getFrom() {
        return from != null ? from : "";
    }


   // public String getFrom() { return from != null ? from : ""; }
    public String getTo() { return to != null ? to : ""; }
    public String getStatus() { return status != null ? status : ""; }


}
