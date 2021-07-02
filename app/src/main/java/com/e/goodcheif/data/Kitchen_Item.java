package com.e.goodcheif.data;

public class Kitchen_Item {
    String Title,type,price,offer,url;

    public Kitchen_Item(String title, String type, String price, String offer, String url) {
        Title = title;
        this.type = type;
        this.price = price;
        this.offer = offer;
        this.url = url;
    }

    public Kitchen_Item() {

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
