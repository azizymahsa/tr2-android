package ir.trap.tractor.android.models.otherModels;

public class MenuItems
{

    int id;
    int imgResID;
    String ItemName;
    int ItemNumber;

    public MenuItems(int id, String itemName, int imgResID, int itemNumber)
    {
        this.id = id;
        this.imgResID = imgResID;
        ItemName = itemName;
        ItemNumber = itemNumber;
    }

    public MenuItems()
    {
    }

    public MenuItems(int id, String itemName, int imgResID)
    {
        this.id = id;
        this.ItemName = itemName;
        this.imgResID = imgResID;
        this.ItemNumber = -1;
    }

    public int getId()
    {
        return id;
    }

    public int getItemNumber()
    {
        return ItemNumber;
    }

    public void setItemNumber(int itemNumber)
    {
        ItemNumber = itemNumber;
    }

    public String getItemName()
    {
        return ItemName;
    }

    public void setItemName(String itemName)
    {
        ItemName = itemName;
    }

    public int getImgResID()
    {
        return imgResID;
    }

    public void setImgResID(int imgResID)
    {
        this.imgResID = imgResID;
    }

}
