package ir.trap.tractor.android.ui.fragments.simcardPack.imp.mci;

import com.pixplicity.easyprefs.library.Prefs;

import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getPackageMci.response.GetPackageMciResponse;
import ir.trap.tractor.android.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;

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
        request.setUserId(Prefs.getInt("userId", 0) + "");
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
