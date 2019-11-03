package ir.trap.tractor.android.apiServices.model;

/**
 * Created by MahtabAzizi on 10/30/2019.
 */
public class StadiumPositionModel
{
    String color;
    Integer number;
    boolean isFull;
    Integer id;

    public StadiumPositionModel(String color, Integer number,boolean isFull) {
        this.color = color;
        this.number = number;
        this.isFull=isFull;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public boolean isFull()
    {
        return isFull;
    }

    public void setFull(boolean full)
    {
        isFull = full;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
