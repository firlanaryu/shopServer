package com.creaginetech.shopserver.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.creaginetech.shopserver.AddItemActivity;
import com.creaginetech.shopserver.ListItemActivity;
import com.creaginetech.shopserver.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    private Button btnItem,btnListItem;


    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        btnItem = view.findViewById(R.id.buttonItem);
        btnListItem = view.findViewById(R.id.btnListItem);

        btnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddItemActivity.class));
            }
        });

        btnListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListItemActivity.class));
            }
        });

        return view;
    }

}
