package ir.trap.tractor.android.apiServices.generator;

import javax.inject.Inject;

import ir.trap.tractor.android.apiServices.ServiceApplication;
import ir.trap.tractor.android.apiServices.di.component.NetComponent;
import library.android.service.generator.DaggerComponentService;
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


//    public SendActiveCodeService getSendActiveCodeService()
//    {
//        return new SendActiveCodeService(serviceGenerator);
//    }
}
