package com.example.nikhil.finddev;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    private EditText email, password;
    private String mail, pass;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.emailET);
        password = findViewById(R.id.passwordET);
        signUp = findViewById(R.id.signUpTxt);

        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });
    }

    public void Login(View view){

        mail = email.getText().toString();
        pass = password.getText().toString();

        mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                user = mAuth.getCurrentUser();
                user.getPhoneNumber();
            }
        });
    }
}
