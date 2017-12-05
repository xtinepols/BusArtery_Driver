package com.example.christinebpolesti.busartery_driver.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.christinebpolesti.busartery_driver.R;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText medtOldPass, medtNewPass, medtConfirmPass;
    private String usernamePref, oldPassPref, oldPass, newPass, confirmPass;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = getApplicationContext().getSharedPreferences("login", MODE_PRIVATE);
        editor = pref.edit();

        usernamePref = pref.getString("username", "");
        oldPassPref = pref.getString("password", "");

        medtOldPass = (EditText) findViewById(R.id.edtOldPass);
        medtNewPass = (EditText) findViewById(R.id.edtNewPass);
        medtConfirmPass = (EditText) findViewById(R.id.edtConfirmPass);

        medtOldPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!medtOldPass.getText().toString().trim().equals(oldPassPref)) {
                    medtOldPass.setError("Old password does not match.");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        medtConfirmPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!medtNewPass.getText().toString().trim().equals(medtConfirmPass.getText().toString().trim())) {
                    medtConfirmPass.setError("Password does not match.");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void changePass(View view) {
        oldPass = medtOldPass.getText().toString().trim();
        newPass = medtNewPass.getText().toString().trim();
        confirmPass = medtConfirmPass.getText().toString().trim();

        if (!oldPass.isEmpty() && !newPass.isEmpty() && !confirmPass.isEmpty()) {
            if (oldPass.equals(oldPassPref) && newPass.equals(confirmPass)) {
                //query first the corresponding data for this specific account
                //save the new password to database
                //assigning the new password for sharedPref
                editor.putString("password", newPass);
                editor.commit();
                Toast.makeText(this, "Your password has been changed successfully.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        } else if (oldPass.isEmpty())
            medtOldPass.setError("REQUIRED");
        else if (newPass.isEmpty())
            medtNewPass.setError("REQUIRED");
        else if (confirmPass.isEmpty()) 
            medtConfirmPass.setError("REQUIRED");
    }
}
