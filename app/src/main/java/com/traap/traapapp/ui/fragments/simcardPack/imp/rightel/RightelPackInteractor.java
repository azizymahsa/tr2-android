package com.traap.traapapp.ui.fragments.simcardPack.imp.rightel;

import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getRightelPack.response.GetRightelPackRespone;


/**
 * Created by Javad.Abadi on 8/11/2018.
 */
public interface RightelPackInteractor
{
    interface OnFinishedRightelPackListener
    {
        void onFinishedRightelPack(WebServiceClass<GetRightelPackRespone> packRespone);

        void onErrorRightelPack(String error);
    }

    void findRightelPackData(OnFinishedRightelPackListener listener, String mobile);


}
