package com.creaginetech.shopserver.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creaginetech.shopserver.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public LinearLayout rootItem;
    public TextView txtItemName,txtitemPrice;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        rootItem = itemView.findViewById(R.id.list_item);
        txtItemName = itemView.findViewById(R.id.text_itemName);
        txtitemPrice = itemView.findViewById(R.id.text_itemPrice);


    }


    public void txtItemName(String nameItem) {
        txtItemName.setText(nameItem);
    }

    public void txtitemPrice(String priceItem) {
        txtitemPrice.setText(priceItem);
    }
}
