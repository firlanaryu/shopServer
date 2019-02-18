package com.creaginetech.shopserver.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.creaginetech.shopserver.CategoryItemActivity;
import com.creaginetech.shopserver.ListItemActivity;
import com.creaginetech.shopserver.R;
import com.creaginetech.shopserver.common.Common;
import com.creaginetech.shopserver.models.Category;
import com.creaginetech.shopserver.viewHolder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private FloatingActionButton fabButtonAddCategory;

    private String uId;


    public ItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        recyclerView = view.findViewById(R.id.list);
        fabButtonAddCategory = view.findViewById(R.id.fabButtonAddCategory);

        uId = Common.userId;

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetch();

        fabButtonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), CategoryItemActivity.class));
            }
        });

        return view;
    }

    private void fetch() {

        Query query = FirebaseDatabase.getInstance().getReference().child("categorys").orderByChild("uid").equalTo(uId);

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>().setQuery(query, new SnapshotParser<Category>() {
            @NonNull
            @Override
            public Category parseSnapshot(@NonNull DataSnapshot snapshot) {
                return new Category(snapshot.child("name").getValue().toString());
            }
        }).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Category, CategoryViewHolder>(options) {
            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_category,viewGroup,false);

                return new CategoryViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position, @NonNull final Category model) {

                final DatabaseReference catRef = getRef(position);

                holder.txtCategory(model.getName());

                // Set click listener for the whole category view

//                final String catKey =  catRef.getKey();
                final String catKey =  catRef.getKey();
                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText(getActivity(), "position " +String.valueOf(position) +" key " +catRef.getKey(), Toast.LENGTH_SHORT).show();

                        //Launch item category
                        Intent intent = new Intent(getActivity(), ListItemActivity.class);
                        intent.putExtra("selectCategory",catKey);
                        startActivity(intent);
                    }
                });


//                holder.root.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View view) {
//
//                        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                        builder.setCancelable(false);
//                        builder.setTitle("Delete Item");
//                        builder.setMessage("Are you sure want to delete this item ?");
//
//                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                Toast.makeText(getActivity(), "position " +String.valueOf(position) +" name " +model.getName() +" deleted !", Toast.LENGTH_SHORT).show();
//                                firebaseRecyclerAdapter.getRef(position).removeValue();
//
//                            }
//                        });
//
//                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        });
//
//                        builder.create().show();
//
//                        return false;
//                    }
//                });

            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);


    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }
}
