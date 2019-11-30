package com.traap.traapapp.ui.fragments.simcardPack.imp.irancell;

import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;


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

