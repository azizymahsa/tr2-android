package com.traap.traapapp.apiServices.generator;

import javax.inject.Inject;

import com.traap.traapapp.apiServices.ServiceApplication;
import com.traap.traapapp.apiServices.di.component.NetComponent;
import com.traap.traapapp.apiServices.part.AddCardService;
import com.traap.traapapp.apiServices.part.ArchiveVideoService;
import com.traap.traapapp.apiServices.part.BankListService;
import com.traap.traapapp.apiServices.part.BillCodePayCodeService;
import com.traap.traapapp.apiServices.part.BusService;
import com.traap.traapapp.apiServices.part.BuyChargeWalletService;
import com.traap.traapapp.apiServices.part.BuyPackageWalletService;
import com.traap.traapapp.apiServices.part.CategoryByIdVideosService;
import com.traap.traapapp.apiServices.part.DeleteCardService;
import com.traap.traapapp.apiServices.part.DoTransferCardService;
import com.traap.traapapp.apiServices.part.DoTransferWalletService;
import com.traap.traapapp.apiServices.part.EditCardService;
import com.traap.traapapp.apiServices.part.FlightService;
import com.traap.traapapp.apiServices.part.ForgetPasswordWalletService;
import com.traap.traapapp.apiServices.part.GetAllBoxesService;
import com.traap.traapapp.apiServices.part.GetBalancePasswordLessService;
import com.traap.traapapp.apiServices.part.GetBillInfoService;
import com.traap.traapapp.apiServices.part.GetBoughtForService;
import com.traap.traapapp.apiServices.part.GetCardListService;
import com.traap.traapapp.apiServices.part.GetContactInfoService;
import com.traap.traapapp.apiServices.part.GetHappyCardInfoService;
import com.traap.traapapp.apiServices.part.GetInviteFriendService;
import com.traap.traapapp.apiServices.part.GetMainPhotosService;
import com.traap.traapapp.apiServices.part.GetMainVideosService;
import com.traap.traapapp.apiServices.part.GetMenuHelpService;
import com.traap.traapapp.apiServices.part.GetMenuService;
import com.traap.traapapp.apiServices.part.GetMyBillsService;
import com.traap.traapapp.apiServices.part.GetPackageIrancellService;
import com.traap.traapapp.apiServices.part.GetPackageMciService;
import com.traap.traapapp.apiServices.part.GetPredictService;
import com.traap.traapapp.apiServices.part.GetProfileService;
import com.traap.traapapp.apiServices.part.GetRightelPackService;
import com.traap.traapapp.apiServices.part.GetSettingService;
import com.traap.traapapp.apiServices.part.GetShetabCardInfoService;
import com.traap.traapapp.apiServices.part.GetTicketInfoService;
import com.traap.traapapp.apiServices.part.GetVersionService;
import com.traap.traapapp.apiServices.part.HotelService;
import com.traap.traapapp.apiServices.part.LikeVideoService;
import com.traap.traapapp.apiServices.part.LiveScoreService;
import com.traap.traapapp.apiServices.part.LoginService;
import com.traap.traapapp.apiServices.part.MerchantService;
import com.traap.traapapp.apiServices.part.MobileChargeService;
import com.traap.traapapp.apiServices.part.NewsService;
import com.traap.traapapp.apiServices.part.PackageBuyService;
import com.traap.traapapp.apiServices.part.PhotoArchiveService;
import com.traap.traapapp.apiServices.part.PointsService;
import com.traap.traapapp.apiServices.part.ReservationMatchService;
import com.traap.traapapp.apiServices.part.SendPredictService;
import com.traap.traapapp.apiServices.part.SendProfileService;
import com.traap.traapapp.apiServices.part.SheatcChangePassService;
import com.traap.traapapp.apiServices.part.SheatcForgotPassService;
import com.traap.traapapp.apiServices.part.GetMatchListService;
import com.traap.traapapp.apiServices.part.SpectatorInfoService;
import com.traap.traapapp.apiServices.part.TransactionDetailService;
import com.traap.traapapp.apiServices.part.TransactionService;
import com.traap.traapapp.apiServices.part.VerifyService;
import com.traap.traapapp.apiServices.part.WithdrawWalletService;

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


    public LoginService getLoginService()
    {
        return new LoginService(serviceGenerator);
    }

    public GetCardListService getCardListService()
    {
        return new GetCardListService(serviceGenerator);
    }

    public LikeVideoService getLikeVideoService()
    {
        return new LikeVideoService(serviceGenerator);
    }

    public GetMatchListService getMatchListService()
    {
        return new GetMatchListService(serviceGenerator);
    }


    public VerifyService getVerifyService()
    {
        return new VerifyService(serviceGenerator);
    }

    public GetMyBillsService getMyBillsService()
    {
        return new GetMyBillsService(serviceGenerator);
    }

    public GetBoughtForService getBoughtForService()
    {
        return new GetBoughtForService(serviceGenerator);
    }

    public GetMainVideosService getMainVideosService()
    {
        return new GetMainVideosService(serviceGenerator);
    }

    public GetMainPhotosService getMainPhotosService()
    {
        return new GetMainPhotosService(serviceGenerator);
    }

    public ArchiveVideoService getArchiveVideoService()
    {
        return new ArchiveVideoService(serviceGenerator);
    }

    public PhotoArchiveService getPhotoArchiveService()
    {
        return new PhotoArchiveService(serviceGenerator);
    }


    public GetContactInfoService getContactInfoService()
    {
        return new GetContactInfoService(serviceGenerator);
    }

    public GetInviteFriendService getInviteFriendService(){

        return new GetInviteFriendService(serviceGenerator);
    }

    public GetMenuHelpService getMenuHelpService(){
        return new GetMenuHelpService(serviceGenerator);
    }

    public GetAllBoxesService getAllBoxesService()
    {
        return new GetAllBoxesService(serviceGenerator);
    }

    public BillCodePayCodeService getBillCodePayCode()
    {
        return new BillCodePayCodeService(serviceGenerator);
    }

    public GetTicketInfoService getTicketInfoService()
    {
        return new GetTicketInfoService(serviceGenerator);
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

    public GetBalancePasswordLessService getBalancePasswordLessService()
    {
        return new GetBalancePasswordLessService(serviceGenerator);
    }


    public DoTransferWalletService doTransferWalletService()
    {
        return new DoTransferWalletService(serviceGenerator);
    }

    public TransactionService getTransactionService()
    {
        return new TransactionService(serviceGenerator);
    }

    public TransactionDetailService getTransactionDetailService()
    {
        return new TransactionDetailService(serviceGenerator);
    }

    public PackageBuyService packageBuyService()
    {
        return new PackageBuyService(serviceGenerator);
    }

    public com.traap.traapapp.apiServices.part.PaymentMatchService paymentMatch()
    {
        return new com.traap.traapapp.apiServices.part.PaymentMatchService(serviceGenerator);
    }

    public MobileChargeService getMobileCharge()
    {
        return new MobileChargeService(serviceGenerator);
    }
    public WithdrawWalletService withdrawWalletService()
    {
        return new WithdrawWalletService(serviceGenerator);
    }

    public BuyChargeWalletService buyChargeWalletService()
    {
        return new BuyChargeWalletService(serviceGenerator);
    }

    public BuyPackageWalletService buyPackageWalletService(){

        return new BuyPackageWalletService(serviceGenerator);
    }

    public GetRightelPackService getPackageRightelService()
    {
        return new GetRightelPackService(serviceGenerator);
    }

    public GetSettingService getSettingService()
    {
        return new GetSettingService(serviceGenerator);
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

    public CategoryByIdVideosService categoryByIdVideosService()
    {
        return new CategoryByIdVideosService(serviceGenerator);
    }

    public BankListService getBankListService()
    {
        return new BankListService(serviceGenerator);
    }

    public SheatcChangePassService doChangePassService()
    {
        return new SheatcChangePassService(serviceGenerator);
    }

    public ForgetPasswordWalletService forgetPasswordWalletService(){
        return new ForgetPasswordWalletService(serviceGenerator);
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

    public SpectatorInfoService getSpectatorInfoService()
    {
        return new SpectatorInfoService(serviceGenerator);
    }

    public SendPredictService sendPredictService()
    {
        return new SendPredictService(serviceGenerator);
    }

    public GetProfileService getProfileService()
    {
        return new GetProfileService(serviceGenerator);
    }

    public SendProfileService sendProfileService()
    {
        return new SendProfileService(serviceGenerator);
    }

    public PointsService getPointsService()
    {
        return new PointsService(serviceGenerator);
    }

    public NewsService getNewsService()
    {
        return new NewsService(serviceGenerator);
    }


}
