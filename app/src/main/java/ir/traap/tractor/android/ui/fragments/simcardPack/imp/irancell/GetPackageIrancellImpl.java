package ir.traap.tractor.android.ui.fragments.simcardPack.imp.irancell;

import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;
import ir.traap.tractor.android.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;

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
