package com.creaginetech.shopserver;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.creaginetech.shopserver.common.Common;
import com.creaginetech.shopserver.models.Category;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemActivity extends AppCompatActivity {

    private EditText categoryNameEditText;
    private Button btnSaveCategory;
    private Spinner spinnerCategory;

    private DatabaseReference mDatabase;
    private String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        categoryNameEditText = findViewById(R.id.categoryNameEditText);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);


        uId = Common.userId;


//        mDatabase.child("categorys").child(uId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                    final List<String> name = new ArrayList<String>();
//
//                    for (DataSnapshot nameSnapshot: dataSnapshot.getChildren()){
//                        String nameCategory = nameSnapshot.child("name").getValue(String.class);
//                        name.add(nameCategory);
//                    }
//
//                    Spinner categorySpinner = findViewById(R.id.spinnerCategory);
//                    ArrayAdapter<String> categorysAdapter = new ArrayAdapter<String>(CategoryItemActivity.this,android.R.layout.simple_spinner_item,name);
//                    categorysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    categorySpinner.setAdapter(categorysAdapter);
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



        btnSaveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveNewCategory();


            }
        });

    }


    private void saveNewCategory() {

        String categoryName = categoryNameEditText.getText().toString();

        String catId = mDatabase.push().getKey();
        Common.catId = catId;

        Category category = new Category(categoryName,uId);

        mDatabase.child("categorys").child(catId).setValue(category).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(CategoryItemActivity.this, "save success", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(CategoryItemActivity.this, "save failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
