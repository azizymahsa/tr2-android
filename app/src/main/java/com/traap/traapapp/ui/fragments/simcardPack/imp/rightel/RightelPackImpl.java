package com.traap.traapapp.ui.fragments.simcardPack.imp.rightel;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;
import com.traap.traapapp.apiServices.model.getRightelPack.response.GetRightelPackRespone;

/**
 * Created by Javad.Abadi on 8/11/2018.
 */
public class RightelPackImpl implements RightelPackInteractor
{


    @Override
    public void findRightelPackData(OnFinishedRightelPackListener listener, String mobile)
    {
        GetPackageMciRequest request = new GetPackageMciRequest();
        request.setMobile(mobile);
        SingletonService.getInstance().getPackageRightelService().GetRightelPackService(new OnServiceStatus<WebServiceClass<GetRightelPackRespone>>()
        {
            @Override
            public void onReady(WebServiceClass<GetRightelPackRespone> packRespone)
            {
                try
                {
                    listener.onFinishedRightelPack(packRespone);

                } catch (Exception e)
                {
                    listener.onErrorRightelPack(e.getMessage());

                }

            }

            @Override
            public void onError(String message)
            {
                listener.onErrorRightelPack(message);

            }
        }, request);
    }
}
