package com.traap.traapapp.apiServices.model.techs.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("image_name")
@Expose
private ImageName imageName;
@SerializedName("category")
@Expose
private String category;
@SerializedName("title")
@Expose
private String title;
@SerializedName("subtitle")
@Expose
private String subtitle;
@SerializedName("create_date")
@Expose
private String createDate;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public ImageName getImageName() {
return imageName;
}

public void setImageName(ImageName imageName) {
this.imageName = imageName;
}

public String getCategory() {
return category;
}

public void setCategory(String category) {
this.category = category;
}

public String getTitle() {
return title;
}

public void setTitle(String title) {
this.title = title;
}

public String getSubtitle() {
return subtitle;
}

public void setSubtitle(String subtitle) {
this.subtitle = subtitle;
}

public String getCreateDate() {
return createDate;
}

public void setCreateDate(String createDate) {
this.createDate = createDate;
}

}