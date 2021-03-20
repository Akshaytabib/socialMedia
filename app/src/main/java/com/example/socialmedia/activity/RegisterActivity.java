package com.example.socialmedia.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.socialmedia.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.widget.Toast.LENGTH_SHORT;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    TextInputLayout unames, name, email, password, mobilenumber;
    String username;
    String ruseranme;
    String useremail;
    String userpassword;
    String usermobilenumber;
    Button next;
    String userId;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String pattern = "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$";
    Matcher m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        unames = findViewById(R.id.rusername);
        name = findViewById(R.id.rname);
        email = findViewById(R.id.remail);
        password = findViewById(R.id.rpassword);
        mobilenumber = findViewById(R.id.rmobile);
        next = findViewById(R.id.rbutton);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ruseranme = unames.getEditText().getText().toString().trim();
                username = name.getEditText().getText().toString().trim();
                useremail = email.getEditText().getText().toString().trim();
                userpassword = password.getEditText().getText().toString().trim();
                usermobilenumber = mobilenumber.getEditText().getText().toString().trim();

                if (validateData()) {
                    firebaseAuth.createUserWithEmailAndPassword(useremail, userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Successful", LENGTH_SHORT).show();
                                userId = firebaseAuth.getCurrentUser().getUid();

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("id", userId);
                                hashMap.put("username", username);
                                hashMap.put("name", username);
                                hashMap.put("email", useremail);
                                hashMap.put("password", userpassword);
                                hashMap.put("mobileno", usermobilenumber);
                                hashMap.put("bio", "");
                                hashMap.put("imageurl", "default");

                                databaseReference.child(userId).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                        Toast.makeText(RegisterActivity.this, "user register", LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(RegisterActivity.this, "Unsucessful", LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Failed", LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "enter all field", LENGTH_SHORT).show();
                }

            }
        });
    }

    public boolean validateData() {

        Pattern r = Pattern.compile(pattern);
        m = r.matcher(usermobilenumber);
        if (username.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "enter userName", LENGTH_SHORT).show();
            return false;
        } else if (useremail.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "enter EmailAddress", LENGTH_SHORT).show();
            return false;
        } else if (!useremail.matches(emailPattern)) {
            Toast.makeText(RegisterActivity.this, "Enter correct emailid", LENGTH_SHORT).show();
            return false;
        } else if (userpassword.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "enter Password", LENGTH_SHORT).show();
            return false;
        } else if (usermobilenumber.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "enter mobile number", LENGTH_SHORT).show();
            return false;
        } else if (!m.find()) {
            Toast.makeText(RegisterActivity.this, "enter 10 digit Mobile number", LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(RegisterActivity.this, "enter all the field", LENGTH_SHORT).show();
        }
        return true;
    }

}