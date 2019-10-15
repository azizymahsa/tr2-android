package ir.trap.tractor.android.ui.fragments.simcardPack.imp.irancell;

import com.pixplicity.easyprefs.library.Prefs;

import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;
import ir.trap.tractor.android.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;

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
        request.setUserId(Prefs.getInt("userId", 0) + "");

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
