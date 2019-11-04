package ir.traap.tractor.android.ui.fragments.simcardPack.imp.irancell;

import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;


/**
 * Created by Javad.Abadi on 8/25/2018.
 */
public interface GetPackageIrancellInteractor
{
    interface OnFinishedGetPackageIrancellListener
    {
        void onFinishedGetPackageIrancell(WebServiceClass<GetPackageIrancellResponse> getPackageMciResponse);

        void onErrorGetPackageIrancell(String error);
    }

    void findGetPackageIrancellDataRequest(OnFinishedGetPackageIrancellListener listener, String mobile);


}

