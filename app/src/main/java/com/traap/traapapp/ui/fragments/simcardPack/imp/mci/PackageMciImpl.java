package com.traap.traapapp.ui.fragments.simcardPack.imp.mci;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getPackageMci.response.GetPackageMciResponse;
import com.traap.traapapp.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;

/**
 * Created by Javad.Abadi on 8/14/2018.
 */
public class PackageMciImpl implements PackageMciInteractor
{
    @Override
    public void findPackageMciDataRequest(OnFinishedPackageMciListener listener, String mobile)
    {
        GetPackageMciRequest request = new GetPackageMciRequest();
        request.setMobile(mobile);
        SingletonService.getInstance().getPackageMciService().GetPackageMciService(new OnServiceStatus<WebServiceClass<GetPackageMciResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetPackageMciResponse> response)
            {
                try
                {
                    listener.onFinishedPackageMciPackage(response);

                } catch (Exception e)
                {
                    listener.onErrorPackageMciPackage(e.getMessage());

                }
            }

            @Override
            public void onError(String message)
            {
                listener.onErrorPackageMciPackage(message);

            }
        }, request);
    }
}
