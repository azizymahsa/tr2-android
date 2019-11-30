
package ir.traap.tractor.android.apiServices.model.photo.response;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotosByIdResponse implements Serializable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("content")
    @Expose
    private List<Content> content = null;
    private final static long serialVersionUID = -4570456147748132099L;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

}
