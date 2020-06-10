package com.traap.traapapp.apiServices.model.techs.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageName {

@SerializedName("thumbnail_large")
@Expose
private String thumbnailLarge;
@SerializedName("thumbnail_small")
@Expose
private String thumbnailSmall;

public String getThumbnailLarge() {
return thumbnailLarge;
}

public void setThumbnailLarge(String thumbnailLarge) {
this.thumbnailLarge = thumbnailLarge;
}

public String getThumbnailSmall() {
return thumbnailSmall;
}

public void setThumbnailSmall(String thumbnailSmall) {
this.thumbnailSmall = thumbnailSmall;
}

}