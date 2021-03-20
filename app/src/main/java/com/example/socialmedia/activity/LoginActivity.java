package com.example.socialmedia.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialmedia.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    TextInputLayout name, password;
    TextView textView, forgetpassword;
    Button next;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String username,passwords;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        name = findViewById(R.id.luser);
        password = findViewById(R.id.lpassword);
        textView = findViewById(R.id.textView);
        next = findViewById(R.id.lbutton);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save() {

        username=name.getEditText().getText().toString().trim();
        passwords= password.getEditText().getText().toString();

        if( validateLogin()) {

            firebaseAuth.signInWithEmailAndPassword(username, passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();                    }
                }
            });
        }else {
            Toast.makeText(this, "enter email and password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateLogin() {
        if(username.isEmpty()){
            Toast.makeText(this, "enter email id", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!username.matches(emailPattern)){
            Toast.makeText(this, "enter valid email id", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(passwords.isEmpty()){
            Toast.makeText(this, "enter password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        startActivity(intent);
//    }
}