package com.example.nikhil.finddev;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private EditText email, password, pno;
    private FirebaseAuth mAuth;
    private String mail, pass, SWY, YSW;
    private static String pnumber;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.emailET);
        password = findViewById(R.id.passwordET);
        pno = findViewById(R.id.phoneET);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("Users");
    }

    public void Create(View view){

        mail = email.getText().toString();
        pass = password.getText().toString();
        pnumber = pno.getText().toString();
        SWY = getResources().getString(R.string.SWY);
        YSW = getResources().getString(R.string.YSW);

        if(!validateForm()){
            return;
        }

        mAuth.createUserWithEmailAndPassword(mail,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        user = mAuth.getCurrentUser();
                        myRef.child(pnumber).child(SWY).setValue("No Value");
                        myRef.child(pnumber).child(YSW).setValue("No Value");
                    }
                });

        startActivity(new Intent(SignUp.this,MainDisplay.class));
    }

    private boolean validateForm() {
        boolean valid = true;

        mail = email.getText().toString();
        if (TextUtils.isEmpty(mail)) {
            email.setError("Required.");
            valid = false;
        } else {
            email.setError(null);
        }

        pass = password.getText().toString();
        if (TextUtils.isEmpty(pass)) {
            password.setError("Required.");
            valid = false;
        } else {
            password.setError(null);
        }

        pnumber = pno.getText().toString();
        if (TextUtils.isEmpty(pnumber)) {
            pno.setError("Required.");
            valid = false;
        } else {
            pno.setError(null);
        }

        return valid;
    }

    public static String getNumber(){

        return pnumber;
    }
}
