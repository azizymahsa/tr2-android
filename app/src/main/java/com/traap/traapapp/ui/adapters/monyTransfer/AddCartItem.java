package com.traap.traapapp.ui.adapters.monyTransfer;

public class AddCartItem {
    private String image;
    private String name;
    private String number;

    public AddCartItem(String image, String name, String number) {
        this.image = image;
        this.name = name;
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
