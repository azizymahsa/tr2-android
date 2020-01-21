package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.card.Result;
import com.traap.traapapp.apiServices.model.card.addCard.request.AddCardRequest;
import com.traap.traapapp.apiServices.model.points.groupBy.PointsGroupByResponse;
import com.traap.traapapp.apiServices.model.points.guide.PointsGuideResponse;
import com.traap.traapapp.apiServices.model.points.records.PointsRecordResponse;

public class PointsService extends BasePart
{

    public PointsService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getPointRecord(OnServiceStatus<WebServiceClass<PointsRecordResponse>> listener)
    {
        start(getServiceGenerator().createService().getPointRecord(), listener);
    }

    public void getPointGuide(OnServiceStatus<WebServiceClass<PointsGuideResponse>> listener)
    {
        start(getServiceGenerator().createService().getPointGuide(), listener);
    }

    public void getPointGroupBy(OnServiceStatus<WebServiceClass<PointsGroupByResponse>> listener)
    {
        start(getServiceGenerator().createService().getPointGroupBy(), listener);
    }

}
