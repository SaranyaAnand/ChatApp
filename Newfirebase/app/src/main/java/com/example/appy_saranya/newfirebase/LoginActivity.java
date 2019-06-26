package com.example.appy_saranya.newfirebase;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputName, inputEmail;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    //private ProgressBar progressBar;

    private EditText ed_email;
    private EditText ed_password;
    private Button btn_login;
    private TextView btn_signin;
    private FirebaseAuth mAuth;

    private String userId;
    FirebaseAuth.AuthStateListener mAuthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            // finish();
        }

        // Displaying toolbar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        ed_email =  findViewById(R.id.ed_email);
        ed_password =  findViewById(R.id.ed_password);
        btn_login =  findViewById(R.id.btn_login);
        btn_signin =  findViewById(R.id.btn_signin);
        //progressBar = findViewById(R.id.progressBar);

       // mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
       // mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
//        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

        // app_title change listener
//        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.e(TAG, "App title updated");
//
//                String appTitle = dataSnapshot.getValue(String.class);
//
//                // update toolbar title
//                getSupportActionBar().setTitle(appTitle);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.e(TAG, "Failed to read app title value.", error.toException());
//            }
//        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ed_email.getText().toString();
                final String password = ed_password.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter email id", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                //authenticate user
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //
                                //progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // there was an error
                                    Log.d(TAG, "signInWithEmail:success");
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.d(TAG, "singInWithEmail:Fail");
                                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        };
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });



//        // Save / update the user
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = inputName.getText().toString();
//                String email = inputEmail.getText().toString();
//
//                // Check for already existed userId
//                if (TextUtils.isEmpty(userId)) {
//                    createUser(name, email);
//                } else {
//                    updateUser(name, email);
//                }
//            }
//        });
//
//        toggleButton();
//    }

    //Changing button text
//    private void toggleButton() {
//        if (TextUtils.isEmpty(userId)) {
//            btnSave.setText("Save");
//        } else {
//            btnSave.setText("Update");
//        }
//    }
//
//    /**
//     * Creating new user node under 'users'
//     */
//    private void createUser(String name, String email) {
//        // TODO
//        // In real apps this userId should be fetched
//        // by implementing firebase auth
//        if (TextUtils.isEmpty(userId)) {
//            userId = mFirebaseDatabase.push().getKey();
//        }
//
//        UserModel user = new UserModel(name, email);
//
//        mFirebaseDatabase.child(userId).setValue(user);
//
//        addUserChangeListener();
//    }


    /**
     * User data change listener
     */
//    private void addUserChangeListener() {
//        // User data change listener
//        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                UserModel user = dataSnapshot.getValue(UserModel.class);
//
//                // Check for null
//                if (user == null) {
//                    Log.e(TAG, "User data is null!");
//                    return;
//                }
//
//                Log.e(TAG, "User data is changed!" + user.name + ", " + user.email);
//
//                // Display newly updated name and email
//                txtDetails.setText(user.name + ", " + user.email);
//
//                // clear edit text
//                inputEmail.setText("");
//                inputName.setText("");
//
//                toggleButton();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.e(TAG, "Failed to read user", error.toException());
//            }
//        });
//    }

//    private void updateUser(String name, String email) {
//        // updating the user via child nodes
//        if (!TextUtils.isEmpty(name))
//            mFirebaseDatabase.child(userId).child("name").setValue(name);
//
//        if (!TextUtils.isEmpty(email))
//            mFirebaseDatabase.child(userId).child("email").setValue(email);
    }
    }

