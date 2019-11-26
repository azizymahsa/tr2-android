package ir.traap.tractor.android.models.otherModels.ticket;

/**
 * Created by MahtabAzizi on 10/30/2019.
 */
public class StadiumPositionModel
{
    String color;
    String number;
    boolean isFull;
    Integer id;
    Integer amount;

    public StadiumPositionModel(String color, String number,boolean isFull) {
        this.color = color;
        this.number = number;
        this.isFull=isFull;
    }

    public Integer getAmount()
    {
        return amount;
    }

    public void setAmount(Integer amount)
    {
        this.amount = amount;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
