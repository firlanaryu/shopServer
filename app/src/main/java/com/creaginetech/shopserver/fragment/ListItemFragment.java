package com.creaginetech.shopserver.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creaginetech.shopserver.R;
import com.creaginetech.shopserver.common.Common;
import com.creaginetech.shopserver.models.ItemCategory;
import com.creaginetech.shopserver.viewHolder.ItemViewHolder;
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
public class ListItemFragment extends Fragment {

    private RecyclerView recyclerViewItem;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter<ItemCategory, ItemViewHolder> firebaseRecyclerAdapter;
    private DatabaseReference mDatabase;

    private String uid;


    public ListItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_item, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        recyclerViewItem = view.findViewById(R.id.listItem);


        uid = Common.userId;

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewItem.setLayoutManager(linearLayoutManager);
        recyclerViewItem.setHasFixedSize(true);
        fetch();

        return view;
    }

    private void fetch() {

        Query query = mDatabase.child("items").orderByChild("uid").equalTo(uid);

        FirebaseRecyclerOptions<ItemCategory> options = new FirebaseRecyclerOptions.Builder<ItemCategory>().setQuery(query, new SnapshotParser<ItemCategory>() {
            @NonNull
            @Override
            public ItemCategory parseSnapshot(@NonNull DataSnapshot snapshot) {
                return new ItemCategory(snapshot.child("nameItem").getValue().toString(),
                        snapshot.child("priceItem").getValue().toString());
            }
        }).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ItemCategory, ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull ItemCategory model) {

                holder.txtItemName(model.getNameItem());
                holder.txtitemPrice(model.getPriceItem());

            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);

                return new ItemViewHolder(view);
            }
        };
        recyclerViewItem.setAdapter(firebaseRecyclerAdapter);

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
