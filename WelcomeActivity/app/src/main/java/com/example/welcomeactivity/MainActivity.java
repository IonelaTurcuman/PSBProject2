package com.example.welcomeactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.welcomeactivity.HomeActivity;
import com.example.welcomeactivity.LoginActivity;
import com.example.welcomeactivity.R;
import com.example.welcomeactivity.RegisterActivity;
import com.example.welcomeactivity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    public static final String FILE_NAME_CREDENTIALS = "credentials.txt";

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        /*String credentials = readFromFile(FILE_NAME_CREDENTIALS);
        if (!TextUtils.isEmpty(credentials)) {
            String [] data = credentials.split("<USER_INFO>");
            user = new User(data[0], data[1]);

            auth.signInWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent goToHome = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(goToHome);
                    }
                }
            });

            Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(homeIntent);
        }*/

        Button registerButton = findViewById(R.id.registerChoiceButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        Button loginButton = findViewById(R.id.loginChoiceButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginIntent);
            }
        });
    }

    private String readFromFile(String filename) {
        String ret = "";
        try {
            InputStream inputStream = openFileInput(filename);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (Exception e) {
            Log.e("ERROR", "Can not read file. Check main.");
        }
        return ret;
    }
}