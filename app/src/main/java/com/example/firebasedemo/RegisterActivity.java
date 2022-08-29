package com.example.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText email,password;
    private Button btnRegister;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Registration");


        email = findViewById(R.id.email);
        password = findViewById(R.id.passwd);
        btnRegister = findViewById(R.id.btn_register);
        auth = FirebaseAuth.getInstance();


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = email.getText().toString();
                String userPassword = password.getText().toString();

                if((TextUtils.isEmpty(emailAddress)) || (TextUtils.isEmpty(userPassword))) {
                    Toast.makeText(getApplicationContext(), "Empty Credentials", Toast.LENGTH_LONG).show();
                }
                else if(userPassword.length() < 8)
                {
                    Toast.makeText(getApplicationContext(), "Password must too short",Toast.LENGTH_LONG).show();
                }
                else
                {
                    registerUser(emailAddress, userPassword);

                }

            }
        });
    }

    //User registration
    public void registerUser(String email, String password)
    {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void Cancel(View view) {
        email.setText("");
        password.setText("");
    }
}