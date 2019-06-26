package com.example.appy_saranya.newfirebase;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    EditText username, password;
    Button registerButton;
    String user, pass;
    TextView login;



    EditText editText;
    Button submit,fetch,nextPage;
    DatabaseReference rootRef,demoRef;
    TextView demoValue;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        registerButton = (Button)findViewById(R.id.registerButton);
        login = (TextView)findViewById(R.id.login);

        Firebase.setAndroidContext(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, VerifyUserName.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();

                if(user.equals("")){
                    username.setError("can't be blank");
                }
                else if(pass.equals("")){
                    password.setError("can't be blank");
                }
                else if(!user.matches("[A-Za-z0-9]+")){
                    username.setError("only alphabet or number allowed");
                }
                else if(user.length()<5){
                    username.setError("at least 5 characters long");
                }
                else if(pass.length()<5){
                    password.setError("at least 5 characters long");
                }
                else {
                    final ProgressDialog pd = new ProgressDialog(MainActivity.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    String url = "https://newfirebase-add84.firebaseio.com/users.json";

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            Firebase reference = new Firebase("https://newfirebase-add84.firebaseio.com/users");

                            if(s.equals("null")) {
                                reference.child(user).child("password").setValue(pass);
                                Toast.makeText(MainActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                            }
                            else {
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if (!obj.has(user)) {
                                        reference.child(user).child("password").setValue(pass);
                                        Toast.makeText(MainActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "username already exists", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }

                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError );
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
                    rQueue.add(request);
                }
            }
        });

          nextPage =(Button) findViewById(R.id.btnMoveNextPage);
//
//        //database reference pointing to root of database
//        //rootRef = FirebaseDatabase.getInstance().getReference();
//
//        //database reference pointing to demo node
//       // demoRef = rootRef.child("demo");
//
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String value = editText.getText().toString();
//                //push creates a unique id in database
//                demoRef.child("value").setValue(value);
//
//                demoRef.child("value").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        String value = dataSnapshot.getValue(String.class);
//                        demoValue.setText(value);
//                        Log.e("value==",value);
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//        });
//
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Hello Test",Toast.LENGTH_SHORT).show();
                mAuth.signOut();

               // TastyToast.makeText(getApplicationContext(), "Sign out", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
//
//         // when click on fetch then only it will fetch value from database
////        fetch.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                demoRef.child("value").addListenerForSingleValueEvent(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(DataSnapshot dataSnapshot) {
////                        String value = dataSnapshot.getValue(String.class);
////                        demoValue.setText(value);
////                        Log.e("value==",value);
////                    }
////                    @Override
////                    public void onCancelled(DatabaseError databaseError) {
////                    }
////                });
////            }
////        });
    }


    }

