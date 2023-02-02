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

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    EditText editUsername;
    EditText editPassword;
    int userID;

    /**
     * OnCreate method for the Login Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editUsername = findViewById(R.id.editTextUsername);
        editPassword = findViewById(R.id.editTextPassword);

    }

    /**
     * This method first checks the user's login validation to confirm that a matching username
     * and password are stored within the database. If this check returns true, the app is opened.
     *
     */
    public void openApp(View view) {
        String enteredUsername = editUsername.getText().toString();
        String enteredPassword = editPassword.getText().toString();
        if(loginValidation(enteredUsername, enteredPassword)) {
            Intent intent = new Intent(LoginActivity.this, AnimalListActivity.class);
            startActivity(intent);
        }
    }

    /**
     * This method takes in the user input for username and password and checks within the database
     * to confirm that a username and password match a data entry within the database.
     * If a matching username or password are not found a message is displayed informing the user
     * that they have entered an invalid username or password.
     * @param username string entered into the username EditText
     * @param password string entered into the password EditText
     * @return returns a boolean validLogin determining if the entered username and password is a
     * valid login.
     */
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

    /**
     * This method is ran when the user clicks the register button on the Login screen. It first uses
     * input validation to confirm that an identical username is not already stored within the database.
     * If no matching usernames are found, it then checks to determine if the password's character
     * length is at least 6 characters. If both of these checks return false, the user is then created and
     * stored within the database.
     */
    public void registerUser(View view) {
        userID = getIntent().getIntExtra("userID", -1);
        boolean userFound = false;
        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();
        Repository repo = new Repository(getApplication());
        List<UserEntity> usersList = repo.getAllUsers();
        int currentUserID = (usersList.get(usersList.size() - 1).getUserID()) + 1;
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        for(UserEntity user : usersList) {
            if(username.equals(user.getUsername())) {
                userFound = true;
                break;
        }
    }
        if(userFound) {
            builder.setTitle("User Already Exists");
            builder.setMessage("User " + username + " already exist. Please enter a different username.");
            builder.setNegativeButton("OK", (dialog, which) -> {});
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (password.length() < 6) {
            builder.setTitle("Longer Password Required");
            builder.setMessage("Please enter a password with no fewer than 6 characters.");
            builder.setNegativeButton("OK", (dialog, which) -> {});
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            UserEntity newUser = new UserEntity(currentUserID, username, password);
            repo.insertUser(newUser);
            builder.setTitle("User Created");
            builder.setMessage("User " + username + " created successfully.");
            builder.setNegativeButton("OK", (dialog, which) -> {});
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
