package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;
import ir.trap.tractor.android.apiServices.model.getRightelPack.response.GetRightelPackRespone;

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
