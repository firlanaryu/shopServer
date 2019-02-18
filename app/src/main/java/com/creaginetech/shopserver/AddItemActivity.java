package com.creaginetech.shopserver;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.creaginetech.shopserver.common.Common;
import com.creaginetech.shopserver.models.ItemCategory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity implements View .OnClickListener{

//    private static final String EXTRA_POST_KEY = "post_key";
    private TextView edtNameItem,edtPriceItem;
    private Toolbar addItemToolbar;
    private Spinner spinnerCategory;
    private Button btnSaveItem;

    private DatabaseReference mDatabase;
//    private DatabaseReference mItemReference;

    private String uId,mCategoryKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        uId = Common.userId;

//        // Get post key from intent
//        mCategoryKey = getIntent().getStringExtra(EXTRA_POST_KEY);
//        if (mCategoryKey == null){
//            throw new IllegalArgumentException("Must pass EXTRA_POSTKEY");
//        }
//
//        mItemReference = FirebaseDatabase.getInstance().getReference()
//                .child("items").child(mCategoryKey);

        widgetsItem();

        readSpinnerCategory();

        btnSaveItem.setOnClickListener(this);


    }

    private void readSpinnerCategory() {

        //Read data Spinner category from firebase

        mDatabase.child("categorys").orderByChild("uid").equalTo(uId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final List<String> name = new ArrayList<String>();

                for (DataSnapshot nameSnapshot: dataSnapshot.getChildren()){
                    String nameCategory = nameSnapshot.child("name").getValue(String.class);
                    name.add(nameCategory);
                }

                ArrayAdapter<String> categorysAdapter = new ArrayAdapter<String>(AddItemActivity.this,android.R.layout.simple_spinner_item,name);
                categorysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategory.setAdapter(categorysAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void widgetsItem() {

        spinnerCategory = findViewById(R.id.spinnerCategory);
        edtNameItem = findViewById(R.id.edtNameItem);
        edtPriceItem = findViewById(R.id.edtPriceItem);
        addItemToolbar = findViewById(R.id.addItemToolbar);
        setSupportActionBar(addItemToolbar);
        btnSaveItem = findViewById(R.id.btnSaveItem);

    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btnSaveItem){
            saveNewItem();
        }

    }


    private void saveNewItem() {

        String itemId = mDatabase.push().getKey();

        String name = edtNameItem.getText().toString();
        String price = edtPriceItem.getText().toString();
//        String categoryName = spinnerCategory.getSelectedItem().toString();
        String categoryName = spinnerCategory.getSelectedItem().toString();

        ItemCategory itemCategory = new ItemCategory(name,price,Common.catId,uId);

//        mDatabase.child("items").child(categoryName).child(itemId).setValue(itemCategory)
//        mItemReference.push()
        mDatabase.child("items").child(itemId).setValue(itemCategory).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AddItemActivity.this, "save item success", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddItemActivity.this, "save item Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void categoryName() {

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String categoryName = spinnerCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
