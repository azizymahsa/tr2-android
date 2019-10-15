package ir.trap.tractor.android.ui.fragments.simcardPack.imp.rightel;

import com.pixplicity.easyprefs.library.Prefs;

import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getPackageMci.response.request.GetPackageMciRequest;
import ir.trap.tractor.android.apiServices.model.getRightelPack.response.GetRightelPackRespone;

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
        request.setUserId(Prefs.getInt("userId", 0) + "");
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
