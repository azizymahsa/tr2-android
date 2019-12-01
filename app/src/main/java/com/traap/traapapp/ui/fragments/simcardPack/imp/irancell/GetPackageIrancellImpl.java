package com.traap.traapapp.ui.fragments.simcardPack.imp.irancell;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;
import com.traap.traapapp.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;

/**
 * Created by Javad.Abadi on 8/25/2018.
 */
public class GetPackageIrancellImpl implements GetPackageIrancellInteractor
{
    @Override
    public void findGetPackageIrancellDataRequest(OnFinishedGetPackageIrancellListener listener, String mobile)
    {
        GetPackageMciRequest request = new GetPackageMciRequest();
        request.setMobile(mobile);
        SingletonService.getInstance().getPackageIrancellService().GetPackageIrancellService(new OnServiceStatus<WebServiceClass<GetPackageIrancellResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetPackageIrancellResponse> getPackageIrancellResponse)
            {
                try
                {
                    listener.onFinishedGetPackageIrancell(getPackageIrancellResponse);

                } catch (Exception e)
                {
                    listener.onErrorGetPackageIrancell(e.getMessage());


                }
            }

            @Override
            public void onError(String message)
            {
                listener.onErrorGetPackageIrancell(message);

            }
        }, request);

    }
}
