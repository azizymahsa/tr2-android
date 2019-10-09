package ir.trap.tractor.android.models.otherModels.pack;

import java.util.List;

import ir.trap.tractor.android.apiServices.model.getRightelPack.response.Detail;

/**
 * Created by Javad.Abadi on 8/13/2018.
 */
public class RightelPackModel
{
    private String title;
    private List<Detail> detail;

    public RightelPackModel(String title, List<Detail> detail)
    {
        this.title = title;
        this.detail = detail;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List<Detail> getDetail()
    {
        return detail;
    }

    public void setDetail(List<Detail> detail)
    {
        this.detail = detail;
    }
}
