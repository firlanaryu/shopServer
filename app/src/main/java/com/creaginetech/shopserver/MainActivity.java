package com.creaginetech.shopserver;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.creaginetech.shopserver.common.Common;
import com.creaginetech.shopserver.fragment.ItemFragment;
import com.creaginetech.shopserver.fragment.OrderFragment;
import com.creaginetech.shopserver.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private BottomNavigationView mainBottomNav;

    private OrderFragment orderFragment;
    private ItemFragment itemFragment;

    //set the boolean twiceclicked to false
    boolean twiceClicked = false;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        mainBottomNav = findViewById(R.id.mainBottomNav);
        mainBottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        //FRAGMENTS
        orderFragment = new OrderFragment();
        itemFragment = new ItemFragment();

        replaceFragment(orderFragment);

        String userId = Common.userId;

        mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                toolbar.setTitle("Hello " +user.username);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment fragment;

            switch (menuItem.getItemId()){

                case R.id.bottom_action_order:
                    fragment = new OrderFragment();
                    replaceFragment(fragment);
                    return true;

                case R.id.bottom_action_menu:
                    fragment = new ItemFragment();
                    replaceFragment(fragment);
                    return true;

            }

            return false;
        }
    };

    private void logoutUser() {

        mAuth.signOut();
        startActivity(new Intent(MainActivity.this,Sign_in_Activity.class));
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_logout:
            logoutUser();
            return true;

            case R.id.action_user:
                Intent settingIntent = new Intent(MainActivity.this,UserActivity.class);
                startActivity(settingIntent);
                return true;

                default:
                    return false;
        }

    }

    public void replaceFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container,fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {


        if (twiceClicked == true ){
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        }

        twiceClicked = true;

        Snackbar.make(findViewById(R.id.mainActivity),"Press back again to exit",Snackbar.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                twiceClicked = false;
            }
        },3000); // here we delay the twiceClicked = false; for 3 seconds
        //So in these 3 seconds twiceClicked = true and in that time user click the back button again then above if condition will satisffy
    }
}
