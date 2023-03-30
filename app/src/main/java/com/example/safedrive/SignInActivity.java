package com.example.safedrive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    public Button button;
    private EditText mEmail_editText;
    private EditText mPassword_editText;

    private EditText mUserName_editText;


    private Button mSignIn_button;
    private Button mRegister_button;
    private Button mBack_button;

    private ProgressBar mProgress_bar;

    private FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        mAuth = FirebaseAuth.getInstance();

        mEmail_editText = (EditText) findViewById(R.id.email_editText);
        mPassword_editText = (EditText) findViewById(R.id.password_editText);
        mUserName_editText = (EditText) findViewById(R.id.username_editText);

        mSignIn_button = (Button) findViewById(R.id.signin_button);
        mRegister_button = (Button) findViewById(R.id.register_button);
        mBack_button = (Button) findViewById(R.id.back_button);

        mProgress_bar = (ProgressBar) findViewById(R.id.loading_progressBar);

        mSignIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty()) return;
                inProgress(true);
                mAuth.signInWithEmailAndPassword(mEmail_editText.getText().toString(),mPassword_editText.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(SignInActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(SignInActivity.this,newSignIn.class);
                                //Intent intent = new Intent(String.valueOf(SignInActivity.this));
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish(); return;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                inProgress(false);
                                Toast.makeText(SignInActivity.this, "Sign In Failed!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        mRegister_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty()) return;
                inProgress(true);
                mAuth.createUserWithEmailAndPassword(mEmail_editText.getText().toString(),mPassword_editText.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(SignInActivity.this, "User Registered Successfully!", Toast.LENGTH_SHORT).show();
                                inProgress(false);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                inProgress(false);
                                Toast.makeText(SignInActivity.this, "Registration Failed!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        mBack_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); return;
            }
        });
    }

    private void inProgress(boolean x) {
        if(x) {
            mProgress_bar.setVisibility(View.VISIBLE);
            mBack_button.setEnabled(false);
            mSignIn_button.setEnabled(false);
            mRegister_button.setEnabled(false);
        } else {
            mProgress_bar.setVisibility(View.GONE);
            mBack_button.setEnabled(true);
            mSignIn_button.setEnabled(true);
            mRegister_button.setEnabled(true);
        }


    }

    private boolean isEmpty() {
        if(TextUtils.isEmpty(mEmail_editText.getText().toString())) {
            mEmail_editText.setError("REQUIRED!");
            return true;
        }
        if(TextUtils.isEmpty(mPassword_editText.getText().toString())){
            mPassword_editText.setError("REQUIRED!");
            return true;
        }
        if(TextUtils.isEmpty(mUserName_editText.getText().toString())){
            mUserName_editText.setError("REQUIRED!");
            return true;
        }
        return false;
    }
}