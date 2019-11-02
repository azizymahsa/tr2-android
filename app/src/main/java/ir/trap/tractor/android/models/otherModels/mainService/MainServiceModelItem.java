package ir.trap.tractor.android.models.otherModels.mainService;

import com.google.gson.annotations.Expose;

import java.util.List;

import ir.trap.tractor.android.apiServices.model.allService.response.SubMenu;
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
    private List<ir.trap.tractor.android.apiServices.model.allService.response.SubMenu> SubMenu;
}
