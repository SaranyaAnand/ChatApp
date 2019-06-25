package com.example.appy_saranya.newfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListElementsActivity extends AppCompatActivity {

    private List<String> friends;
    DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_elements);

        // this is used to add and retrieve data into firebase using List/ArrayList
        friends = new ArrayList<>();
        friends.add("John");
        friends.add("Saranya");
        friends.add("Steve");
        friends.add("Anna");

        rootRef = FirebaseDatabase.getInstance().getReference();
        for(String friend : friends) {
            rootRef.child("friends").child(friend).setValue(true);
        }

        DatabaseReference friendsRef = rootRef.child("friends");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 friends = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String friend = ds.getKey();
                    friends.add(friend);
                }
                Log.d("TAG", String.valueOf(friends));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        friendsRef.addListenerForSingleValueEvent(eventListener);

    }
}
