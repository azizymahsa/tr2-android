package com.traap.traapapp.models.otherModels.mainService;

import com.google.gson.annotations.Expose;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class MainServiceModelItem
{
    @Getter @Setter @Expose
    private Integer id;

    @Getter @Setter @Expose
    private String title;

    @Getter @Setter @Expose
    private String imageLink;

    @Expose @Getter @Setter
    private String keyName;

    @Getter @Setter @Expose
    private String logo_selected;

    @Expose @Getter @Setter
    private List<com.traap.traapapp.apiServices.model.allService.response.SubMenu> SubMenu;

    @Getter @Setter @Expose
    private String base_url;
}
