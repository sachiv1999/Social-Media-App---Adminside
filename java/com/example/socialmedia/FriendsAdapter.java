package com.example.socialmedia;

import static java.security.AccessController.getContext;
import static java.util.Collections.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.security.AccessController;
import java.util.List;

public class FriendsAdapter {
    private final Context context;
    private final List<Post> friendList;

    public FriendsAdapter(Context context, List<Post> friendList) {
        super();
        this.context = context;
        this.friendList = friendList;
    }

//    public Friends getView(int position, View convertView, @NonNull ViewGroup parent) {
//        Friends friends = getItem(position);
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext())
//                    .inflate(R.layout.row_friends, parent, false);
//        }
//        return friends;
//    }




}