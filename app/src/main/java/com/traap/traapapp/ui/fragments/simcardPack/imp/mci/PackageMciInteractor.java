package com.traap.traapapp.ui.fragments.simcardPack.imp.mci;

import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getPackageMci.response.GetPackageMciResponse;


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
