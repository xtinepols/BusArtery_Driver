package com.example.christinebpolesti.busartery_driver.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.christinebpolesti.busartery_driver.R;

public class LoginActivity extends AppCompatActivity {

    RequestQueue requestQueue;

//    String baseUrl = ; //given by julius
    String url;

    private EditText mEdtUsername;
    private EditText mEdtPassword;

    private Button mBtnLogin;

    private String username, password;

    SharedPreferences.Editor editor;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this);

        pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
        editor = pref.edit();

        if (pref.contains("username") && pref.contains("password")) {
            Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
            startActivity(intent);
            finish();
        }

        this.mEdtUsername = (EditText) findViewById(R.id.edtUsername);
        this.mEdtPassword = (EditText) findViewById(R.id.edtPassword);

        this.mBtnLogin = (Button) findViewById(R.id.btnLogin);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = mEdtUsername.getText().toString();
                password = mEdtPassword.getText().toString();

//                if (!username.isEmpty() && !password.isEmpty()) {
                //DO SOMETHING HERE.. WEB SERVICE.....................

                //IF USERNAME AND PASSWORD MATCHES
//                    editor.putString("username", username);
//                    editor.putString("password", password);
//                    editor.commit();
//                    Toast.makeText(LoginActivity.this, "Successfully logged in.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                    startActivity(intent);

                //else
//                    else {
//                        Toast.makeText(LoginActivity.this, "Username and password doesn't match.", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    if (username.isEmpty())
//                        mEdtUsername.setError("REQUIRED");
//                    if (password.isEmpty())
//                        mEdtPassword.setError("REQUIRED");
//                }
            }
        });
    }
}

