package com.creaginetech.shopserver.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creaginetech.shopserver.R;

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout root;
        public TextView txtCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.list_category);
            txtCategory = itemView.findViewById(R.id.text_category);
        }

        public void txtCategory(String name) {
            txtCategory.setText(name);
        }
    }

