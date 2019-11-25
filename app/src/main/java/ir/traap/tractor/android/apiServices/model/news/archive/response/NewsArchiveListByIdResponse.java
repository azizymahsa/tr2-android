package ir.traap.tractor.android.apiServices.model.news.archive.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ir.traap.tractor.android.apiServices.model.news.category.response.NewsArchiveCategory;
import lombok.Getter;
import lombok.Setter;

public class NewsArchiveListByIdResponse
{

    @Expose @Getter @Setter
    @SerializedName("results")
    private ArrayList<NewsArchiveListByIdResponse> newsArchiveListById;

    @Expose @Getter @Setter
    @SerializedName("previous")
    private String previous;

    @Expose @Getter @Setter
    @SerializedName("next")
    private String next;

    @Expose @Getter @Setter
    @SerializedName("count")
    private int count;
}
