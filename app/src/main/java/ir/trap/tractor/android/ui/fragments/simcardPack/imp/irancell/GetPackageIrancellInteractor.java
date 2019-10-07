package ir.trap.tractor.android.ui.fragments.simcardPack.imp.irancell;

import ir.trap.tractor.android.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;


/**
 * Created by Javad.Abadi on 8/25/2018.
 */
public interface GetPackageIrancellInteractor
{
    interface OnFinishedGetPackageIrancellListener
    {
        void onFinishedGetPackageIrancell(GetPackageIrancellResponse getPackageMciResponse);

        void onErrorGetPackageIrancell(String error);
    }

    void findGetPackageIrancellDataRequest(OnFinishedGetPackageIrancellListener listener, String mobile);


}

