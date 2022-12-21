package com.example.wildlifetracker.UI;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wildlifetracker.Database.Repository;
import com.example.wildlifetracker.Entity.UserEntity;
import com.example.wildlifetracker.R;
import com.example.wildlifetracker.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.SupportMapFragment;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText editUsername;
    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TODO --UNCOMMENT THE FOLLOWING CODE TO CREATE THE
        // "TEST" USER AND "ADMIN" USER--

        /* Repository repo = new Repository(getApplication());
        UserEntity admin = new UserEntity(1, "admin", "admin");
        UserEntity test = new UserEntity(2, "test", "test");
        repo.insertUser(admin);
        repo.insertUser(test); */
    }

    public void openApp(View view) {
        editUsername = findViewById(R.id.editTextUsername);
        editPassword = findViewById(R.id.editTextPassword);
        String enteredUsername = editUsername.getText().toString();
        String enteredPassword = editPassword.getText().toString();
        if(loginValidation(enteredUsername, enteredPassword)) {
            Intent intent = new Intent(LoginActivity.this, AnimalListActivity.class);
            startActivity(intent);
        }
    }

    public boolean loginValidation(String username, String password) {
        boolean validLogin = false;
        Repository repo = new Repository(getApplication());
        List<UserEntity> usersList = repo.getAllUsers();
        boolean nameFound = false;
        boolean passFound = false;
        for(UserEntity user:usersList) {
            if(username.equals(user.getUsername())){
                nameFound = true;
                if(password.equals(user.getPassword()))
                passFound = true;
                validLogin = true;
                break;
            }
        }
        if(!nameFound || !passFound) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("Invalid Login Credentials");
            builder.setMessage("Invalid username or password. Please try again.");
            builder.setPositiveButton("OK", (dialog, which) -> {

            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return validLogin;
    }
}
