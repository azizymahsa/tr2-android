package ir.traap.tractor.android.apiServices.generator;

import javax.inject.Inject;

import ir.traap.tractor.android.apiServices.ServiceApplication;
import ir.traap.tractor.android.apiServices.di.component.NetComponent;
//import library.android.service.generator.DaggerComponentService;
import ir.traap.tractor.android.apiServices.part.AddCardService;
import ir.traap.tractor.android.apiServices.part.BankListService;
import ir.traap.tractor.android.apiServices.part.BillCodePayCodeService;
import ir.traap.tractor.android.apiServices.part.BusService;
import ir.traap.tractor.android.apiServices.part.DeleteCardService;
import ir.traap.tractor.android.apiServices.part.DoTransferCardService;
import ir.traap.tractor.android.apiServices.part.EditCardService;
import ir.traap.tractor.android.apiServices.part.FlightService;
import ir.traap.tractor.android.apiServices.part.GetAllBoxesService;
import ir.traap.tractor.android.apiServices.part.GetBillInfoService;
import ir.traap.tractor.android.apiServices.part.GetCardListService;
import ir.traap.tractor.android.apiServices.part.GetHappyCardInfoService;
import ir.traap.tractor.android.apiServices.part.GetMenuService;
import ir.traap.tractor.android.apiServices.part.GetMyBillsService;
import ir.traap.tractor.android.apiServices.part.GetPackageIrancellService;
import ir.traap.tractor.android.apiServices.part.GetPackageMciService;
import ir.traap.tractor.android.apiServices.part.GetPredictService;
import ir.traap.tractor.android.apiServices.part.GetRightelPackService;
import ir.traap.tractor.android.apiServices.part.GetShetabCardInfoService;
import ir.traap.tractor.android.apiServices.part.GetVersionService;
import ir.traap.tractor.android.apiServices.part.HotelService;
import ir.traap.tractor.android.apiServices.part.LiveScoreService;
import ir.traap.tractor.android.apiServices.part.LoginService;
import ir.traap.tractor.android.apiServices.part.MerchantService;
import ir.traap.tractor.android.apiServices.part.MobileChargeService;
import ir.traap.tractor.android.apiServices.part.PackageBuyService;
import ir.traap.tractor.android.apiServices.part.ReservationMatchService;
import ir.traap.tractor.android.apiServices.part.SendPredictService;
import ir.traap.tractor.android.apiServices.part.SheatcChangePassService;
import ir.traap.tractor.android.apiServices.part.SheatcForgotPassService;
import ir.traap.tractor.android.apiServices.part.GetMatchListService;
import ir.traap.tractor.android.apiServices.part.VerifyService;
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

    public ReservationMatchService getReservation()
    {
        return new ReservationMatchService(serviceGenerator);
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
    public GetMatchListService getMatchListService()
    {
        return new GetMatchListService(serviceGenerator);
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
    public LiveScoreService getLiveScoreService()
    {
        return new LiveScoreService(serviceGenerator);
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
    public ir.traap.tractor.android.apiServices.part.PaymentMatchService paymentMatch()
    {
        return new ir.traap.tractor.android.apiServices.part.PaymentMatchService(serviceGenerator);
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

    public GetPredictService getPredictService()
    {
        return new GetPredictService(serviceGenerator);
    }

    public SendPredictService sendPredictService()
    {
        return new SendPredictService(serviceGenerator);
    }


}
