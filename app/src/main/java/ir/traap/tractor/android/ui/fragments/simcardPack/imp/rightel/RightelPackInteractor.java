package ir.traap.tractor.android.ui.fragments.simcardPack.imp.rightel;

import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getRightelPack.response.GetRightelPackRespone;


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