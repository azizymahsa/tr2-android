package ir.trap.tractor.android.ui.fragments.simcardPack.imp.mci;

import ir.trap.tractor.android.apiServices.model.getPackageMci.response.GetPackageMciResponse;


/**
 * Created by Javad.Abadi on 8/14/2018.
 */
public interface PackageMciInteractor {
    interface OnFinishedPackageMciListener {
        void onFinishedPackageMciPackage(GetPackageMciResponse getPackageMciResponse);
        void onErrorPackageMciPackage(String error);
    }

    void findPackageMciDataRequest(OnFinishedPackageMciListener listener, String mobile);




}
