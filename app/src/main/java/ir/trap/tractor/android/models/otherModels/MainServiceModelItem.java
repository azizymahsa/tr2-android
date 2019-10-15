package ir.trap.tractor.android.models.otherModels;

import com.google.gson.annotations.Expose;

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

}
