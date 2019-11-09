package ir.traap.tractor.android.ui.fragments.simcardPack.imp.mci;

import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getPackageMci.response.GetPackageMciResponse;
import ir.traap.tractor.android.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;

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