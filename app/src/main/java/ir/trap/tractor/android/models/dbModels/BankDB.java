package ir.trap.tractor.android.models.dbModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

public class BankDB extends RealmObject
{
    @Getter @Setter
    private int _ID;

    @Getter @Setter
    private int id;

    @Getter @Setter
    private String imageCardBack;

    @Getter @Setter
    private String imageCard;

    @Getter @Setter
    private String bankBin;

    @Getter @Setter
    private String bankName;

    @Getter @Setter
    private String colorText;

    @Getter @Setter
    private int orderItem;

    @Getter @Setter
    private String colorNumber;
}
