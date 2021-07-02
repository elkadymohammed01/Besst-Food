package com.e.goodcheif.model;

public class PopularFood {


    String name ;
    String price;
    String offer;
    Integer imageUrl;

    public PopularFood(){

    }

    public PopularFood(String name, String price, Integer imageUrl, String offer) {
        this.name = name;
        this.price = price;
        this.offer = offer;
        this.imageUrl = imageUrl;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        this.imageUrl = imageUrl;
    }
}
