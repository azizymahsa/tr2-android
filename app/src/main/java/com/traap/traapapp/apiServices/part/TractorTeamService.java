package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.cup.CupResponse;
import com.traap.traapapp.apiServices.model.techHistory.GetTechsHistoryResponse;
import com.traap.traapapp.apiServices.model.techs.GetTechsIdResponse;
import com.traap.traapapp.apiServices.model.topPlayers.TopPlayerResponse;
import com.traap.traapapp.apiServices.model.tractorTeam.TractorTeamResponse;
import com.traap.traapapp.ui.fragments.competitions.QuestionCompationFragment;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class TractorTeamService extends BasePart
{

    public TractorTeamService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void traktor( OnServiceStatus<WebServiceClass<TractorTeamResponse>> listener)
    {
        start(getServiceGenerator().createService().traktor(), listener);
    }

    public void getTech(OnServiceStatus<WebServiceClass<TopPlayerResponse>> listener, String role__code_name,
                        Boolean is_present, Boolean tech_staff__is_featured)
    {
        start(getServiceGenerator().createService().getTech(role__code_name,is_present,tech_staff__is_featured), listener);
    }
    public void getSearchTech(OnServiceStatus<WebServiceClass<TopPlayerResponse>> listener, String name)
    {
        start(getServiceGenerator().createService().getSearchTech(name), listener);
    }

    public void getCup(OnServiceStatus<WebServiceClass<CupResponse>> listener)
    {
        start(getServiceGenerator().createService().cup(), listener);
    }

    public void getTechsId(Integer id,OnServiceStatus<WebServiceClass<GetTechsIdResponse>> listener)
    {
        start(getServiceGenerator().createService().getTechsId(id), listener);
    }

public void getQuestionCompation(Integer id, QuestionCompationFragment listener)
    {
        start(getServiceGenerator().createService().getAllQuestions(id), listener);
    }

    public void getTechsHistory(Integer id, OnServiceStatus<WebServiceClass<GetTechsHistoryResponse>> listener){
        start(getServiceGenerator().createService().getTechsHistory(id),listener);
    }

}
