package com.example.appy_saranya.newfirebase;

import android.app.ListActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button submit,fetch,nextPage;
    DatabaseReference rootRef,demoRef;
    TextView demoValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.etValue);
        demoValue = (TextView) findViewById(R.id.tvValue);
        submit = (Button) findViewById(R.id.btnSubmit);
        fetch = (Button) findViewById(R.id.btnFetch);
        nextPage =(Button) findViewById(R.id.btnMoveNextPage);

        //database reference pointing to root of database
        rootRef = FirebaseDatabase.getInstance().getReference();

        //database reference pointing to demo node
        demoRef = rootRef.child("demo");


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = editText.getText().toString();
                //push creates a unique id in database
                demoRef.child("value").setValue(value);

                demoRef.child("value").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        demoValue.setText(value);
                        Log.e("value==",value);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListElementsActivity.class);
                startActivity(intent);
            }
        });

         // when click on fetch then only it will fetch value from database
//        fetch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                demoRef.child("value").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        String value = dataSnapshot.getValue(String.class);
//                        demoValue.setText(value);
//                        Log.e("value==",value);
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                });
//            }
//        });
    }


    }

