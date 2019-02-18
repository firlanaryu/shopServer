package com.creaginetech.shopserver;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.creaginetech.shopserver.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {

    private EditText edtUserName,edtUserEmail,edtUserAdrress;
    private Button btnUpdateUser;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        edtUserName = findViewById(R.id.edtUserName);
        edtUserEmail = findViewById(R.id.edtUserEmail);
        edtUserAdrress = findViewById(R.id.edtUserAddress);
        btnUpdateUser = findViewById(R.id.btnUpdateUser);

        String userId = mAuth.getCurrentUser().getUid();


        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                edtUserName.setText(user.username);
                edtUserEmail.setText(user.email);
                edtUserAdrress.setText(user.address);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateUser();

                Toast.makeText(UserActivity.this, "User Updated !", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(UserActivity.this,MainActivity.class));
                finish();

//                Snackbar.make(findViewById(android.R.id.content),"User Updated !",Snackbar.LENGTH_SHORT)
//                        .setAction("Back to Home", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                                startActivity(new Intent(UserActivity.this,MainActivity.class));
//                                finish();
//
//                            }
//                        });

            }
        });

    }

    private void updateUser() {

        String name = edtUserName.getText().toString();
        String email = edtUserEmail.getText().toString();
        String address = edtUserAdrress.getText().toString();

        writeUpdateuser(name,email,address);

    }

    private void writeUpdateuser(String name,String email,String address) {

        String userId = mAuth.getCurrentUser().getUid();

        User user = new User(name,email,address);

        mDatabase.child(userId).setValue(user);

    }
}
