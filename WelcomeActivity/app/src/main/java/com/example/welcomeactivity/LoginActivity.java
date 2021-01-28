package com.example.welcomeactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = findViewById(R.id.loginEmailEditText);
        final EditText password = findViewById(R.id.loginPasswordEditText);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "Empty credentials.", Toast.LENGTH_SHORT).show();
                } else {
                    user = new User(email.getText().toString(), password.getText().toString());
                    auth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String credentialsData = user.getEmail() + "<USER_INFO>" + user.getPassword() + "<USER_INFO>0<USER_INFO>0";
                                writeToFile(MainActivity.FILE_NAME_CREDENTIALS, credentialsData);

                                Toast.makeText(LoginActivity.this, "Login succesful!",Toast.LENGTH_SHORT).show();
                                Intent goToHome = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(goToHome);
                            } else {
                                FirebaseAuthException e = (FirebaseAuthException )task.getException();
                                Toast toast = Toast.makeText(LoginActivity.this, "Login failed!\n"+e.getMessage(),Toast.LENGTH_LONG);
                                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                                if (v != null) {
                                    v.setGravity(Gravity.CENTER);
                                }
                                toast.show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void writeToFile(String filename, String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(filename, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("ERROR", "File write failed: " + e.toString());
        }
    }
}