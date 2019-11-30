package ir.traap.tractor.android.apiServices.model.news.details.sendComment.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class SendCommentNewsRequest
{
    @SerializedName("body")
    @Expose @Getter @Setter
    private String body;
}
