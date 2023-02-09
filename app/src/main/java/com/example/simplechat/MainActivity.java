package com.example.simplechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.simplechat.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    DatabaseReference databaseReference;
    UserAdapter userAdapter;

//    This method is called when the activity is first created. It initializes the layout
//    by inflating the ActivityMainBinding class, sets the adapter for the recycler view
//    and retrieves the list of users from the Firebase database and populates the adapter
//    with the data.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userAdapter = new UserAdapter(this);
        binding.recycler.setAdapter(userAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));


        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.addValueEventListener(new ValueEventListener() {

//            This is a method that retrieves user data from a Firebase database using
//            a DataSnapshot object. It listens for changes in the data and updates
//            the list of users accordingly.
//            The method starts by clearing the current list of users in the userAdapter.
//            Then, it iterates through the children of the DataSnapshot object,
//            which represent the individual users in the database.
//            For each child, it gets the key, which is the user's unique identifier (uid),
//            and checks if it's equal to the uid of the current user, which is obtained
//            from the Firebase authentication instance. If it is, the user is skipped.
//            Otherwise, the method gets the user data from the DataSnapshot object, which
//            is expected to be in the form of a UserModel class, and adds it to the list
//            of users in the userAdapter.
//            In summary, this method provides a way to retrieve and update the list of users
//            from a Firebase database, while ignoring the current user's data.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userAdapter.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String uid = dataSnapshot.getKey();
                    if(!uid.equals(FirebaseAuth.getInstance().getUid())){
                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                        userAdapter.add(userModel);
                    }
                }
            }
//            This method is called when an error occurs while trying to retrieve the data
//            from the Firebase database. It is typically used to handle the error and log it
//            for debugging purposes.
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    This method is used to create the options menu for the activity.
//    It inflates the menu resource file main_menu and returns true
//    to indicate that the menu has been created successfully
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

//    This method is called when a menu item is selected.
//    It checks the selected item's ID and if it is equal to R.id.logout,
//    it signs out the current user and starts the AuthenticationActivity
//    to allow the user to log in again. If the item is not a logout menu item,
//    the method returns false.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity((new Intent(MainActivity.this,AuthenticationActivity.class)));
            finish();
            return true;
        }
        return false;
    }
}