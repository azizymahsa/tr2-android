package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.survey.SurveyDetailResponse;
import com.traap.traapapp.apiServices.model.survey.putSurvey.PutSurveyRequest;
import com.traap.traapapp.apiServices.model.survey.putSurvey.PutSurveyResponse;

/**
 * Created by MahtabAzizi on 5/5/2020.
 */
public class SurveyDetailService extends BasePart
{
    public SurveyDetailService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getSurveyDetail(Integer id, OnServiceStatus<WebServiceClass<SurveyDetailResponse>> listener)
    {
        start(getServiceGenerator().createService().getSurveyDetail(id), listener);
    }

    public void putSurvey(Integer id,PutSurveyRequest request ,OnServiceStatus<WebServiceClass<PutSurveyResponse>> listener){

        start(getServiceGenerator().createService().putSurvey(id,request),listener);
    }

}

