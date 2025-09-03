package com.example.socialmedia;

import java.util.List;

public class Friends {
    private List<String> list;

    public Friends() {} // Required for Firebase

    public Friends(List<String> list) {
        this.list = list;
    }

    public List<String> getList() { return list; }




}
