package com.monguide.monguide.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseHelper {
    public static DatabaseReference getReferenceToRoot() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public static DatabaseReference getReferenceToAllUsers() {
        return getReferenceToRoot().child("users");
    }

    public static DatabaseReference getReferenceToParticularUser(String uid) {
        return getReferenceToAllUsers().child(uid);
    }

    public static DatabaseReference getReferenceToAllQuestions() {
        return getReferenceToRoot().child("questions");
    }

    public static DatabaseReference getReferenceToParticularQuestions(String qid) {
        return getReferenceToAllQuestions().child(qid);
    }

}
