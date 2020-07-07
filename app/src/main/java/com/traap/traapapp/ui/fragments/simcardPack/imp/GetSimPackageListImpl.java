package com.traap.traapapp.ui.fragments.simcardPack.imp;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getSimPackageList.request.GetSimPackageListRequest;
import com.traap.traapapp.apiServices.model.getSimPackageList.response.GetSimPackageListResponse;
import com.traap.traapapp.apiServices.model.getSimPackageList.response.SimPackage;
import com.traap.traapapp.utilities.Logger;

import java.util.List;

public class GetSimPackageListImpl
{
    public static void getSimPackageList(int operatorType, String mobile, GetSimPackagesListener listener)
    {
        GetSimPackageListRequest request = new GetSimPackageListRequest();
        request.setMobile(mobile);
        request.setOperatorType(operatorType);

        SingletonService.getInstance().packageBuyService().getSimCardPackageList(request, new OnServiceStatus<WebServiceClass<GetSimPackageListResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetSimPackageListResponse> response)
            {
                if (response == null || response.data == null)
                {
                    listener.onGetSimPackagesError("خطا در دریافت اطلاعات از سرور!");
                    Logger.e("-getPredictSystemFromId-", "null");
                    return;
                }
                if (response.info.statusCode != 200)
                {
                    listener.onGetSimPackagesError(response.info.message);
                }
                else
                {
                    listener.onGetSimPackagesCompleted(operatorType, response.data.getPackageList());
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onGetSimPackagesError(message);
            }
        });
    }

    public interface GetSimPackagesListener
    {
        void onGetSimPackagesCompleted(int operatorType, List<SimPackage> packageList);

        void onGetSimPackagesError(String message);
    }

}
