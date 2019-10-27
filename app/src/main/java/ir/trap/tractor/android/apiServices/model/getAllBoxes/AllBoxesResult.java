package ir.trap.tractor.android.apiServices.model.getAllBoxes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 10/27/2019.
 */
public class AllBoxesResult
{
    public AllBoxesResult(Integer id,String name, Integer seats) {
        this.id = id;
        this.name = name;
        this.seats=seats;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("seats")
    @Expose
    private Integer seats;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }
}
