package com.example.soho2020;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class AnonSignInActivity extends AppCompatActivity {

    private EditText mName, mEmail, mPassword; // text fields for sign up info
    private Button mSignUp; // Button to sign up

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anon_sign_in);

        // Get the button
        mSignUp = findViewById(R.id.anon_sign_up_button);

        // add a listener for the sign up button
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
    }

    /**
     * Upgrade the user to actual user from email entered.
     */
    private void addUser() {
        // Get the edit texts
        mName = findViewById(R.id.name_field);
        mEmail = findViewById(R.id.email_field);
        mPassword = findViewById(R.id.password_field);

        // Get the strings from the input
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        AuthCredential credential;

        // Get the AuthCredential
        try {
            credential = EmailAuthProvider.getCredential(email, password);
            FirebaseAuth
                    .getInstance()
                    .getCurrentUser()
                    .linkWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("SUCCESS", "SUCCESSFULLY UPGRADED");
                                startHomeActivity();
                            } else {
                                Log.e("FAILURE", "NOT UPGRADED TRY AGAIN");
                                Toast.makeText(AnonSignInActivity.this,
                                        "Please enter valid email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (IllegalArgumentException e) {
            Log.e("FAILURE", "NOT UPGRADED TRY AGAIN");
            Toast.makeText(AnonSignInActivity.this, "Please enter valid email",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Launch the home activity with the new account.
     */
    private void startHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}