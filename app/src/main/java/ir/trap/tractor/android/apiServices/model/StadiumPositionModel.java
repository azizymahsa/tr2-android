package ir.trap.tractor.android.apiServices.model;

/**
 * Created by MahtabAzizi on 10/30/2019.
 */
public class StadiumPositionModel
{
    String color;
    Integer number;

    public StadiumPositionModel(String color, Integer number) {
        this.color = color;
        this.number = number;
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
