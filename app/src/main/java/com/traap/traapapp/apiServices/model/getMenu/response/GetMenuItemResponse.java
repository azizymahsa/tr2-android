package com.traap.traapapp.apiServices.model.getMenu.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.traap.traapapp.apiServices.model.allService.response.SubMenu;
import lombok.Getter;
import lombok.Setter;

public class GetMenuItemResponse implements Parcelable
{
    @SerializedName("id")
    @Expose @Getter @Setter
    private Integer id;

    @SerializedName("image_name")
    @Expose @Getter @Setter
    private String imageName;

    @SerializedName("title")
    @Expose @Getter @Setter
    private String title;

    @SerializedName("is_visible")
    @Expose @Getter @Setter
    private Boolean isVisible;

    @SerializedName("key_id")
    @Expose @Getter @Setter
    private Integer keyId;

    @SerializedName("key_name")
    @Expose @Getter @Setter
    private String keyName;

    @SerializedName("order_item")
    @Expose @Getter @Setter
    private Integer orderItem;


    @SerializedName("sub_menu")
    @Expose @Getter @Setter
    private List<SubMenu> subMenu = null;
    @SerializedName("logo")
    @Expose @Getter @Setter
    private String logo;
    @SerializedName("logo_selected")
    @Expose @Getter @Setter
    private String logoSelected;
    @SerializedName("base_url")
    @Expose
    @Getter
    @Setter
    private String baseUrl;
    protected GetMenuItemResponse(Parcel in)
    {
        if (in.readByte() == 0)
        {
            id = null;
        } else
        {
            id = in.readInt();
        }
        imageName = in.readString();
        title = in.readString();
        byte tmpIsVisible = in.readByte();
        isVisible = tmpIsVisible == 0 ? null : tmpIsVisible == 1;
        if (in.readByte() == 0)
        {
            keyId = null;
        } else
        {
            keyId = in.readInt();
        }
        keyName = in.readString();
        if (in.readByte() == 0)
        {
            orderItem = null;
        } else
        {
            orderItem = in.readInt();
        }
    }

    public static final Creator<GetMenuItemResponse> CREATOR = new Creator<GetMenuItemResponse>()
    {
        @Override
        public GetMenuItemResponse createFromParcel(Parcel in)
        {
            return new GetMenuItemResponse(in);
        }

        @Override
        public GetMenuItemResponse[] newArray(int size)
        {
            return new GetMenuItemResponse[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        if (id == null)
        {
            dest.writeByte((byte) 0);
        } else
        {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(imageName);
        dest.writeString(title);
        dest.writeByte((byte) (isVisible == null ? 0 : isVisible ? 1 : 2));
        if (keyId == null)
        {
            dest.writeByte((byte) 0);
        } else
        {
            dest.writeByte((byte) 1);
            dest.writeInt(keyId);
        }
        dest.writeString(keyName);
        if (orderItem == null)
        {
            dest.writeByte((byte) 0);
        } else
        {
            dest.writeByte((byte) 1);
            dest.writeInt(orderItem);
        }
    }
}
