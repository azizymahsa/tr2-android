package ir.traap.tractor.android.apiServices.model.news.details.getContent.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.traap.tractor.android.apiServices.model.news.main.ImageName;
import lombok.Getter;
import lombok.Setter;

public class GetNewsDetailsResponse
{

    @Expose @Getter @Setter
    @SerializedName("body")
    private String body;

    @Expose @Getter @Setter
    @SerializedName("subtitle")
    private String subtitle;

    @Expose @Getter @Setter
    @SerializedName("title")
    private String title;

    @Expose @Getter @Setter
    @SerializedName("images")
    private List<ImageName> images;

    @Expose @Getter @Setter
    @SerializedName("related_news")
    private List<RelatedNews> relatedNews;

    @Expose @Getter @Setter
    @SerializedName("rated")
    private int rated;

    @Expose @Getter @Setter
    @SerializedName("update_date")
    private double updateDate;

    @Expose @Getter @Setter
    @SerializedName("create_date")
    private double createDate;

    @Expose @Getter @Setter
    @SerializedName("id")
    private int id;
}
