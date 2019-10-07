package ir.trap.tractor.android.apiServices.generator;

import javax.inject.Inject;

import ir.trap.tractor.android.apiServices.ServiceApplication;
import ir.trap.tractor.android.apiServices.di.component.NetComponent;
//import library.android.service.generator.DaggerComponentService;
import ir.trap.tractor.android.apiServices.part.DoTransferCardService;
import ir.trap.tractor.android.apiServices.part.GetBillInfoService;
import ir.trap.tractor.android.apiServices.part.GetHappyCardInfoService;
import ir.trap.tractor.android.apiServices.part.GetPackageIrancellService;
import ir.trap.tractor.android.apiServices.part.GetPackageMciService;
import ir.trap.tractor.android.apiServices.part.GetRightelPackService;
import ir.trap.tractor.android.apiServices.part.GetShetabCardInfoService;
import ir.trap.tractor.android.apiServices.part.MobileChargeService;
import ir.trap.tractor.android.apiServices.part.PackageBuyService;
import okhttp3.OkHttpClient;

/**
 * Created by Javad.Abadi on 3/26/2018.
 */
public class SingletonService
{
    private NetComponent netComponent;
    @Inject
    ServiceGenerator serviceGenerator;
    @Inject
    OkHttpClient okHttpClient;
    private ServiceApplication serviceApplication;
    private static final SingletonService ourInstance = new SingletonService();

    public static SingletonService getInstance()
    {
        return ourInstance;
    }

    public SingletonService setNetComponent(NetComponent netComponent)
    {
        this.netComponent = netComponent;
        return this;
    }

    public void inject()
    {
        ComponentService componentService = DaggerComponentService.builder().netComponent(netComponent).build();
        componentService.inject(this);
    }

    public OkHttpClient getOkHttpClient()
    {
        return okHttpClient;
    }

    private SingletonService()
    {
    }

    public ServiceApplication getContext()
    {
        return serviceApplication;
    }

    public void setContext(ServiceApplication context)
    {
        this.serviceApplication = context;
    }

    public PackageBuyService packageBuyService()
    {
        return new PackageBuyService(serviceGenerator);
    }

    public MobileChargeService getMobileCharge()
    {
        return new MobileChargeService(serviceGenerator);
    }

    public GetRightelPackService getPackageRightelService()
    {
        return new GetRightelPackService(serviceGenerator);
    }

    public GetPackageMciService getPackageMciService()
    {
        return new GetPackageMciService(serviceGenerator);
    }

    public GetPackageIrancellService getPackageIrancellService()
    {
        return new GetPackageIrancellService(serviceGenerator);
    }

    public GetBillInfoService getBillInfoService()
    {
        return new GetBillInfoService(serviceGenerator);
    }

    public GetHappyCardInfoService getHappyCardInfoService()
    {
        return new GetHappyCardInfoService(serviceGenerator);
    }

    public GetShetabCardInfoService getShetabCardInfoService()
    {
        return new GetShetabCardInfoService(serviceGenerator);
    }

    public DoTransferCardService doTransferCardService()
    {
        return new DoTransferCardService(serviceGenerator);
    }




}
