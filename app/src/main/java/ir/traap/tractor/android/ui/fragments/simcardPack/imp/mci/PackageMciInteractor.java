package ir.traap.tractor.android.ui.fragments.simcardPack.imp.mci;

import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getPackageMci.response.GetPackageMciResponse;


/**
 * Created by Javad.Abadi on 8/14/2018.
 */
public interface PackageMciInteractor
{
    interface OnFinishedPackageMciListener
    {
        void onFinishedPackageMciPackage(WebServiceClass<GetPackageMciResponse> getPackageMciResponse);

        void onErrorPackageMciPackage(String error);
    }

    void findPackageMciDataRequest(OnFinishedPackageMciListener listener, String mobile);


}
