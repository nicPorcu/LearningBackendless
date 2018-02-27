package com.example.nicolo.learningbackendless;

/**
 * Created by per6 on 2/22/18.
 */

public class Restaurant {
    private String name;
    private int rating;
    private String address;
    private String objectId;

    public Restaurant() {
    }

    public Restaurant(String name, int rating, String address) {
        this.name = name;
        this.rating = rating;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", rating=" + rating +
                ", address='" + address + '\'' +
                '}';
    }
}
