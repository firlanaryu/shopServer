package com.creaginetech.shopserver.models;

public class ItemCategory {

    String nameItem;
    String priceItem;
    String categoryItem;
    String uid;


    public ItemCategory() {
    }

    public ItemCategory(String nameItem, String priceItem) {
        this.nameItem = nameItem;
        this.priceItem = priceItem;
    }

    public ItemCategory(String nameItem, String priceItem, String categoryItem, String uid) {
        this.nameItem = nameItem;
        this.priceItem = priceItem;
        this.categoryItem = categoryItem;
        this.uid = uid;
    }


    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getPriceItem() {
        return priceItem;
    }

    public void setPriceItem(String priceItem) {
        this.priceItem = priceItem;
    }

    public String getCategoryItem() {
        return categoryItem;
    }

    public void setCategoryItem(String categoryItem) {
        this.categoryItem = categoryItem;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
