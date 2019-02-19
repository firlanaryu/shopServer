package com.creaginetech.shopserver;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.creaginetech.shopserver.common.Common;
import com.creaginetech.shopserver.models.Category;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AddCategoryItemActivity extends AppCompatActivity {

    private EditText categoryNameEditText;
    private Button btnSaveCategory;
    private ImageView addCategoryImage;

    private Uri categoryImageUri = null;
    private DatabaseReference mDatabase;
    private String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category_item);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        categoryNameEditText = findViewById(R.id.categoryNameEditText);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);
        addCategoryImage = findViewById(R.id.addCategoryImage);


        uId = Common.userId;

        addCategoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //start cropping
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512,512)
                        .setAspectRatio(1,1)
                        .start(AddCategoryItemActivity.this);
            }
        });

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

                Toast.makeText(AddCategoryItemActivity.this, "save category success", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(AddCategoryItemActivity.this, "failed to save category", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                categoryImageUri = result.getUri();
                addCategoryImage.setImageURI(categoryImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }

    }
}
