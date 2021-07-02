package com.e.goodcheif.data;

public class Delivery {
    String clint ,details;

    Double price ;


    public Delivery() {

    }

    public Delivery(String clint, String details, Double price) {
        this.clint = clint;
        this.details = details;
        this.price = price;
    }

    public String getClint() {
        return clint;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setClint(String clint) {
        this.clint = clint;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
