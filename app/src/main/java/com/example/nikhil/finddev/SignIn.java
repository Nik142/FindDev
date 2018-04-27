package com.example.nikhil.finddev;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    private EditText email, password;
    private String mail, pass, pNmuber;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference myRef, idRef;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.emailET);
        password = findViewById(R.id.passwordET);
        signUp = findViewById(R.id.signUpTxt);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Users");
        idRef = database.getReference().child("userId");

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

        mAuth.signInWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    user = mAuth.getCurrentUser();

                    try{

                        idRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                pNmuber = dataSnapshot.getValue(String.class);

                                SharedPreferences preferences = getSharedPreferences("FindDev", Context.MODE_PRIVATE);
                                SharedPreferences.Editor edit = preferences.edit();
                                edit.putString("pNumber",pNmuber).commit();

                                Intent intent = new Intent(SignIn.this, MainDisplay.class);
                                startActivity(intent);
                                SignIn.this.finish();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }catch(NullPointerException e){

                        Toast.makeText(SignIn.this, "Null Pointer occured", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}
