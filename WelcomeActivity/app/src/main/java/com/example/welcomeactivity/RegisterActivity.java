package com.example.welcomeactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText email = findViewById(R.id.registerEmailEditText);
        final EditText password = findViewById(R.id.registerPasswordEditText);
        final EditText confirmPassword = findViewById(R.id.registerPasswordEditText);

        final Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Empty credentials.", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password too short.", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords don't match.", Toast.LENGTH_SHORT).show();
                } else {
                    user = new User(email.getText().toString(), password.getText().toString());
                    auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String credentialsData = user.getEmail() + "<USER_INFO>" + user.getPassword();
                                writeToFile(MainActivity.FILE_NAME_CREDENTIALS, credentialsData);

                                Toast.makeText(RegisterActivity.this, "Register succesful!",Toast.LENGTH_SHORT).show();
                                Intent goToHome = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(goToHome);
                            } else {
                                FirebaseAuthException e = (FirebaseAuthException) task.getException();
                                Toast toast = Toast.makeText(RegisterActivity.this, "Register failed!\n"+e.getMessage(),Toast.LENGTH_LONG);
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