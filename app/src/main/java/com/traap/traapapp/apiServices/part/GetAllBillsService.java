package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.billCar.RequestBillCar;
import com.traap.traapapp.apiServices.model.billCar.ResponseBillCar;
import com.traap.traapapp.apiServices.model.getAllBoxes.GetAllBoxesRequest;
import com.traap.traapapp.apiServices.model.getAllBoxes.GetAllBoxesResponse;

/**
 * Created by MahsaAzizi on 5/16/2020.
 */
public class GetAllBillsService extends BasePart
{

    public GetAllBillsService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getAllBillCar(OnServiceStatus<WebServiceClass<ResponseBillCar>> listener, RequestBillCar request)
    {
        start(getServiceGenerator().createService().getAllBillCar(request), listener);
    }

    public void getAllBillMotor(OnServiceStatus<WebServiceClass<ResponseBillCar>> listener, RequestBillCar request)
    {
        start(getServiceGenerator().createService().getAllBillMotor(request), listener);
    }


}
