package com.creaginetech.shopserver;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.creaginetech.shopserver.common.Common;
import com.creaginetech.shopserver.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_in_Activity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "Sign_in_Activity";
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private EditText edtEmail,edtPassword;
    private Button btnLogin,btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        //Views
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        //Click Listener
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null){
            onAuthSuccess(mAuth.getCurrentUser());
        }

    }

    private void signUp() {

        startActivity(new Intent(Sign_in_Activity.this,RegisterActivity.class));

    }

    private void onAuthSuccess(FirebaseUser user) {
//        String username = usernameFromEmail(user.getEmail());
//
//        //Write new user
//        writeNewUser(user.getUid(),username,user.getEmail());
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Common.userId = userid;

        startActivity(new Intent(Sign_in_Activity.this,MainActivity.class));
        finish();
    }

    private void writeNewUser(String userId, String name, String email) {

        User user = new User(name,email);

        mDatabase.child("users").child(userId).setValue(user);
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")){
            return email.split("@")[0];
        }else {
            return email;
        }
    }

    private void signIn() {
        Log.d(TAG, "signIn: ");
        if (!validateForm()){
            return;
        }

        showProgressDialog();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete: " +task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()){
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(Sign_in_Activity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private boolean validateForm(){
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
        if (i == R.id.btnLogin){
            signIn();
        } else if ( i == R.id.btnRegister){
            signUp();
        }

    }
}
