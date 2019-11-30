package ir.traap.tractor.android.apiServices.model.bookMarkPhoto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahsaAzizi on 11/30/2019.
 */
public class BookMarkPhotoResponse
{
    @SerializedName("is_bookmarked")
    @Expose
    private Boolean isBookMarked;


    public Boolean getBookMarked()
    {
        return isBookMarked;
    }

    public void setBookMarked(Boolean bookMarked)
    {
        isBookMarked = bookMarked;
    }
}
