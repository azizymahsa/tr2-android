package ir.trap.tractor.android.apiServices.model.getVersion.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GetVersionResponse
{
    @SerializedName("version_app")
    @Expose @Getter @Setter
    private Integer version;

    @SerializedName("is_force_download")
    @Expose @Getter @Setter
    private Boolean isForceDownload;

    @SerializedName("create_date")
    @Expose @Getter @Setter
    private String createDate;

    @SerializedName("description")
    @Expose @Getter @Setter
    private String description;

    @SerializedName("download_url")
    @Nullable @Expose @Getter @Setter
    private String downloadUrl;

    @SerializedName("cafe_bazaar")
    @Nullable @Expose @Getter @Setter
    private String cafeBazaar;

    @SerializedName("google_play")
    @Nullable @Expose @Getter @Setter
    private String googlePlay;

    @SerializedName("web_site")
    @Nullable @Expose @Getter @Setter
    private String webSite;

    @SerializedName("title")
    @Nullable @Expose @Getter @Setter
    private String title;

    @SerializedName("keybuild")
    @Nullable @Expose @Getter @Setter
    private List<Object> keybuild = null;

    @SerializedName("popup_list")
    @Nullable @Expose @Getter @Setter
    private List<Object> popupList = null;

}
