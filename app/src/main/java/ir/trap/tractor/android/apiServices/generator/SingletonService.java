package ir.trap.tractor.android.apiServices.generator;

import javax.inject.Inject;

import ir.trap.tractor.android.apiServices.ServiceApplication;
import ir.trap.tractor.android.apiServices.di.component.NetComponent;
//import library.android.service.generator.DaggerComponentService;
import ir.trap.tractor.android.apiServices.part.AddCardService;
import ir.trap.tractor.android.apiServices.part.BankListService;
import ir.trap.tractor.android.apiServices.part.BillCodePayCodeService;
import ir.trap.tractor.android.apiServices.part.BusService;
import ir.trap.tractor.android.apiServices.part.DeleteCardService;
import ir.trap.tractor.android.apiServices.part.DoTransferCardService;
import ir.trap.tractor.android.apiServices.part.EditCardService;
import ir.trap.tractor.android.apiServices.part.FlightService;
import ir.trap.tractor.android.apiServices.part.GetAllBoxesService;
import ir.trap.tractor.android.apiServices.part.GetBillInfoService;
import ir.trap.tractor.android.apiServices.part.GetCardListService;
import ir.trap.tractor.android.apiServices.part.GetHappyCardInfoService;
import ir.trap.tractor.android.apiServices.part.GetMenuService;
import ir.trap.tractor.android.apiServices.part.GetMyBillsService;
import ir.trap.tractor.android.apiServices.part.GetPackageIrancellService;
import ir.trap.tractor.android.apiServices.part.GetPackageMciService;
import ir.trap.tractor.android.apiServices.part.GetRightelPackService;
import ir.trap.tractor.android.apiServices.part.GetShetabCardInfoService;
import ir.trap.tractor.android.apiServices.part.GetVersionService;
import ir.trap.tractor.android.apiServices.part.HotelService;
import ir.trap.tractor.android.apiServices.part.LoginService;
import ir.trap.tractor.android.apiServices.part.MerchantService;
import ir.trap.tractor.android.apiServices.part.MobileChargeService;
import ir.trap.tractor.android.apiServices.part.PackageBuyService;
import ir.trap.tractor.android.apiServices.part.SheatcChangePassService;
import ir.trap.tractor.android.apiServices.part.SheatcForgotPassService;
import ir.trap.tractor.android.apiServices.part.TicketService;
import ir.trap.tractor.android.apiServices.part.VerifyService;
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



    public LoginService getLoginService(){
        return new LoginService(serviceGenerator);
    }

    public GetCardListService getCardListService()
    {
        return new GetCardListService(serviceGenerator);
    }
    public TicketService getTicketServices()
    {
        return new TicketService(serviceGenerator);
    }


    public VerifyService getVerifyService(){
        return new VerifyService(serviceGenerator);
    }

    public GetMyBillsService getMyBillsService(){
        return new GetMyBillsService(serviceGenerator);
    }

    public GetAllBoxesService getAllBoxesService(){
        return new GetAllBoxesService(serviceGenerator);
    }

    public BillCodePayCodeService getBillCodePayCode()
    {
        return new BillCodePayCodeService(serviceGenerator);
    }
    public GetMenuService getMenuService()
    {
        return new GetMenuService(serviceGenerator);
    }
    public MerchantService getMerchantService()
    {
        return new MerchantService(serviceGenerator);
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

    public BusService getBusService()
    {
        return new BusService(serviceGenerator);
    }

    public FlightService getFlightService()
    {
        return new FlightService(serviceGenerator);
    }

    public HotelService getHotelService()
    {
        return new HotelService(serviceGenerator);
    }

    public AddCardService addCardService()
    {
        return new AddCardService(serviceGenerator);
    }

    public DeleteCardService deleteCardService()
    {
        return new DeleteCardService(serviceGenerator);
    }

    public EditCardService editCardService()
    {
        return new EditCardService(serviceGenerator);
    }

    public BankListService getBankListService()
    {
        return new BankListService(serviceGenerator);
    }

    public SheatcChangePassService doChangePassService()
    {
        return new SheatcChangePassService(serviceGenerator);
    }

    public SheatcForgotPassService doForgotPassService()
    {
        return new SheatcForgotPassService(serviceGenerator);
    }

    public GetVersionService getVersionService()
    {
        return new GetVersionService(serviceGenerator);
    }


}
