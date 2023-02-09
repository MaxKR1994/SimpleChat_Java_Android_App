package com.example.simplechat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.simplechat.databinding.ActivityAuthenticationBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthenticationActivity extends AppCompatActivity {

    ActivityAuthenticationBinding binding;
    String email,name,password;
    DatabaseReference databaseReference;

    /**
     * onCreate method is called when the activity is first created and it is used to
     * initialize the view, bind the layout with data binding and set the click listeners for
     * the buttons.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        binding.login.setOnClickListener(new View.OnClickListener() {
            /**
             This method initializes the activity when it is created. It sets the content view using the data binding,
             initializes the reference to the Firebase database, sets up listeners for the login and sign up buttons,
             and retrieves the email, name, and password from the corresponding EditText fields.
             param savedInstanceState a Bundle object containing the activity's previously saved state.
             */
            @Override
            public void onClick(View view) {
                email=binding.email.getText().toString();
                password=binding.password.getText().toString();

                login();
            }
        });

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            /**
             This method is called when the activity starts and sets the current user to the authenticated user,
             and if it's not null, the activity is redirected to the MainActivity and the current activity is finished.
             */
            @Override
            public void onClick(View view) {
                name=binding.name.getText().toString();
                email=binding.email.getText().toString();
                password=binding.password.getText().toString();

                signUp();
            }
        });

    }

    /**
     * onStart method is called when the activity becomes visible to the user.
     * It checks if the user is already logged in, if yes then it takes the user to MainActivity,
     * otherwise it stays in the AuthenticationActivity.
     */
    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(AuthenticationActivity.this,MainActivity.class));
            finish();
        }
    }

    /**
     * login method is used to sign in the user to the application.
     * It takes the email and password entered by the user and uses
     * FirebaseAuth.getInstance().signInWithEmailAndPassword() method to sign in the user.
     * On success, it starts MainActivity.
     */
    private void login() {
        FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(email.trim(),password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(AuthenticationActivity.this,MainActivity.class));
                        finish();
                    }
                });
    }

    /**
     * signUp method is used to create a new account for the user.
     * It takes the name, email and password entered by the user and uses
     * FirebaseAuth.getInstance().createUserWithEmailAndPassword() method to create a new account.
     * On success, it updates the display name, creates a new UserModel object, and stores it in the database.
     * Then, it starts MainActivity.
     */
    private void signUp() {
        FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword(email.trim(),password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    /**
                     This method is called when the user has successfully signed up with their email and password.
                     A new UserProfileChangeRequest object is created to set the display name of the user to the input name.
                     The user's profile is then updated with the change request, and a new UserModel object
                     is created and added to the Firebase database.
                     Finally, the MainActivity is started and the AuthenticationActivity is finished.
                     */
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest
                                .Builder()
                                .setDisplayName(name)
                                .build();
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        firebaseUser.updateProfile(userProfileChangeRequest);
                        UserModel userModel = new UserModel(FirebaseAuth.getInstance().getUid(),name,email,password);
                        databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(userModel);
                        startActivity(new Intent(AuthenticationActivity.this,MainActivity.class));
                        finish();
                    }
                });
    }

}