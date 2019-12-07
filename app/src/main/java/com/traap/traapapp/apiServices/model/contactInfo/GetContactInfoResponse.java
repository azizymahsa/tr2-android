package com.traap.traapapp.apiServices.model.contactInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MahtabAzizi on 12/7/2019.
 */
public class GetContactInfoResponse
{

    @Expose
    @Getter
    @Setter
    @SerializedName("results")
    private List<ResultContactInfo> resultContactInfos;

}
