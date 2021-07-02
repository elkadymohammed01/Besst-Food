package com.e.goodcheif.data;

public class Item_Cart {
    String id,title,price,type;
    int items;

    public Item_Cart(String id, String title, String price, String type, int items) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.type = type;
        this.items = items;
    }

    public Item_Cart() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }
}
