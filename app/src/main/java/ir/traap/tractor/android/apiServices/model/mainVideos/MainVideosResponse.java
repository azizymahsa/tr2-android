package ir.traap.tractor.android.apiServices.model.mainVideos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MahtabAzizi on 11/23/2019.
 */
public class MainVideosResponse
{
    @SerializedName("list_categories")
    @Expose
    private List<ListCategory> listCategories = null;

    @SerializedName("recent")
    @Expose
    private List<Recent> recent = null;

    @SerializedName("category")
    @Expose
    private List<Category> category = null;

    @SerializedName("favorites")
    @Expose
    private List<Object> favorites = null;


    public List<Recent> getRecent() {
        return recent;
    }

    public void setRecent(List<Recent> recent) {
        this.recent = recent;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Object> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Object> favorites) {
        this.favorites = favorites;
    }

    public List<ListCategory> getListCategories() {
        return listCategories;
    }

    public void setListCategories(List<ListCategory> listCategories) {
        this.listCategories = listCategories;
    }
}
