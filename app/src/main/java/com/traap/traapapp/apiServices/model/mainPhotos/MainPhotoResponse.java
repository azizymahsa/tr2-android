package com.traap.traapapp.apiServices.model.mainPhotos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.mainVideos.Category;
import com.traap.traapapp.apiServices.model.mainVideos.ListCategory;

import java.util.ArrayList;

/**
 * Created by MahtabAzizi on 11/23/2019.
 */
public class MainPhotoResponse
{
    @SerializedName("list_categories")
    @Expose
    private ArrayList<ListCategory> listCategories = null;

    @SerializedName("recent")
    @Expose
    private ArrayList<Category> recent = null;

    @SerializedName("category")
    @Expose
    private ArrayList<Category> category = null;

    @SerializedName("favorites")
    @Expose
    private ArrayList<Category> favorites = null;


    public ArrayList<Category> getRecent() {
        return recent;
    }

    public void setRecent(ArrayList<Category> recent) {
        this.recent = recent;
    }

    public ArrayList<Category> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<Category> category) {
        this.category = category;
    }

    public ArrayList<Category> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<Category> favorites) {
        this.favorites = favorites;
    }

    public ArrayList<ListCategory> getListCategories() {
        return listCategories;
    }

    public void setListCategories(ArrayList<ListCategory> listCategories) {
        this.listCategories = listCategories;
    }
}
