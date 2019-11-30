package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;
import com.traap.traapapp.apiServices.model.getRightelPack.response.GetRightelPackRespone;

/**
 * Created by Javad.Abadi on 8/11/2018.
 */
public class GetRightelPackService extends BasePart{
    public GetRightelPackService(ServiceGenerator serviceGenerator) {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart() {
        return this;
    }

    public void GetRightelPackService(OnServiceStatus<WebServiceClass<GetRightelPackRespone>> listener, GetPackageMciRequest request) {
        start(getServiceGenerator().createService().getRightelPackage(request), listener);
    }

}
