package com.creaginetech.shopserver;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.creaginetech.shopserver.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";

    private EditText edtEmail,edtPassword;
    private Button btnRegister;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtEmail = findViewById(R.id.edtRegisterEmail);
        edtPassword = findViewById(R.id.edtRegisterPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);

    }

    private void registerUser() {
        Log.d(TAG,"SignUp");
        if (!validateForm()){
            return;
        }

        showProgressDialog();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete: " + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()){
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to Sign Up", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void onAuthSuccess(FirebaseUser user) {

        String username = usernameFromEmail(user.getEmail());

        //Write new user
        writeNewUser(user.getUid(),username,user.getEmail());

        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
        finish();

    }

    private void writeNewUser(String uid, String username, String email) {

        User user = new User(username,email);

        mDatabase.child("users").child(uid).setValue(user);
    }

    private String usernameFromEmail(String email) {

        if (email.contains("@")){
            return email.split("@")[0];
        }else {
            return email;
        }

    }

    private boolean validateForm() {

        boolean result = true;
        if (TextUtils.isEmpty(edtEmail.getText().toString())){
            edtEmail.setError("Required");
            result=false;
        }else {
            edtEmail.setError(null);
        }

        if (TextUtils.isEmpty(edtPassword.getText().toString())){
            edtPassword.setError("Required");
            result=false;
        } else {
            edtPassword.setError(null);
        }

        return result;

    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btnRegister){
            registerUser();
        }

    }
}
