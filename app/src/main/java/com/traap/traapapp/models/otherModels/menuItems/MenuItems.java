package com.traap.traapapp.models.otherModels.menuItems;

import lombok.Getter;
import lombok.Setter;

public class MenuItems
{
    @Getter @Setter
    private int id;

    @Getter @Setter
    private int imgResID;

    @Getter @Setter
    private String ItemName;

    @Getter @Setter
    private int ItemNumber;

    @Getter @Setter
    private Boolean isActive;

    public MenuItems(int id, String itemName, int imgResID, int itemNumber)
    {
        this.id = id;
        this.imgResID = imgResID;
        ItemName = itemName;
        ItemNumber = itemNumber;
        this.isActive = true;
    }

    public MenuItems()
    {
    }

    public MenuItems(int id, String itemName, int imgResID, Boolean isActive)
    {
        this.id = id;
        this.ItemName = itemName;
        this.imgResID = imgResID;
        this.isActive = isActive;
        this.ItemNumber = -1;
    }


}
