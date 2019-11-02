package ir.trap.tractor.android.ui.fragments.payment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.buyPackage.response.PackageBuyResponse;
import ir.trap.tractor.android.apiServices.model.card.Result;
import ir.trap.tractor.android.apiServices.model.mobileCharge.response.MobileChargeResponse;
import ir.trap.tractor.android.conf.TrapConfig;
import ir.trap.tractor.android.ui.base.BaseFragment;
import ir.trap.tractor.android.ui.fragments.favoriteCard.FavoriteCardFragment;
import ir.trap.tractor.android.ui.fragments.favoriteCard.FavoriteCardParentActionView;
import ir.trap.tractor.android.ui.fragments.simcardCharge.imp.irancell.IrancellBuyImpl;
import ir.trap.tractor.android.ui.fragments.simcardPack.imp.BuyPackageImpl;
import ir.trap.tractor.android.ui.fragments.ticket.BuyTickets;
import ir.trap.tractor.android.ui.fragments.ticket.OnClickContinueBuyTicket;
import ir.trap.tractor.android.utilities.ClearableEditText;
import ir.trap.tractor.android.utilities.Tools;

public class PaymentFragment extends BaseFragment implements FavoriteCardParentActionView, PaymentParentActionView, PaymentActionView
{

    private Fragment cardFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private Result mCard;

    private TextView tvAmount, tvTitle;
    private ImageView imgLogo;
    private View btnBack;
    private CircularProgressButton btnConfirmPayment;
    private ClearableEditText etCvv2, etPass;
    private EditText edtExpYear, edtExpMound;

    private LinearLayout llCvv2, llPass, llExpireDate;
    private RelativeLayout rlTitle;

    private PaymentParentActionView pActionView;

    private int PAYMENT_STATUS;
    private String price;
    private String title;
    private int drawableIcon;
    private IrancellBuyImpl irancellBuy;
    private BuyPackageImpl buyPackage;
    private int operatorType;
    private int typeCharge;
    private int simcardType;
    private String mobile;
    private String cvv2="";
    private String expDate="";
    private Integer cardId;
    private String requestId;
    private String titlePackageType;
    private int profileId;

    private OnClickContinueBuyTicket onClickContinueBuyTicketListener;

    public PaymentFragment()
    {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance(int PAYMENT_STATUS,
                                              String price,
                                              String title,
                                              int imgLogo,
                                              PaymentParentActionView paymentParentActionView,
                                              Object response,
                                              int operatorType,
                                              int simcardType,
                                              int typeCharge,
                                              String mobile
    )
    {
        PaymentFragment fragment = new PaymentFragment();
        fragment.setParentActionView(paymentParentActionView);

        Bundle args = new Bundle();

        args.putInt("PAYMENT_STATUS", PAYMENT_STATUS);
        args.putInt("imgLogo", imgLogo);
        args.putString("price", price);
        args.putString("title", title);
        args.putParcelable("response", (Parcelable) response);

        args.putInt("OPERATOR_TYPE",operatorType);
        args.putInt("SIMCARD_TYPE",simcardType);
        args.putString("MOBILE",mobile);
        args.putInt("TYPE_CHARGE",typeCharge);

        fragment.setArguments(args);

        return fragment;
    }
    public static PaymentFragment newInstance(int PAYMENT_STATUS,
                                              String price,
                                              String title,
                                              int imgLogo,
                                              PaymentParentActionView paymentParentActionView,
                                              Object response,
                                              int operatorType,
                                              int simcardType,
                                              int typeCharge,
                                              String mobile,String s
    )
    {
        PaymentFragment fragment = new PaymentFragment();
        fragment.setParentActionView(paymentParentActionView);

        Bundle args = new Bundle();

        args.putInt("PAYMENT_STATUS", PAYMENT_STATUS);
        args.putInt("imgLogo", imgLogo);
        args.putString("price", price);
        args.putString("title", title);
        args.putParcelable("response", (Parcelable) response);

        args.putInt("OPERATOR_TYPE",operatorType);
        args.putInt("SIMCARD_TYPE",simcardType);
        args.putString("MOBILE",mobile);
        args.putInt("TYPE_CHARGE",typeCharge);

        fragment.setArguments(args);

        return fragment;
    }
    public static PaymentFragment newInstance(int PAYMENT_STATUS, BuyTickets buyTickets,
                                              PaymentParentActionView paymentParentActionView,OnClickContinueBuyTicket onClickContinueBuyTicket)
    {
        PaymentFragment fragment = new PaymentFragment();
        fragment.setOnClickContinueBuyTicket(onClickContinueBuyTicket);
        fragment.setParentActionView(paymentParentActionView);
        Bundle args = new Bundle();
        args.putInt("PAYMENT_STATUS", PAYMENT_STATUS);
        fragment.setArguments(args);

        return fragment;
    }
    public static PaymentFragment newInstance(int PAYMENT_STATUS,
                                              PaymentParentActionView paymentParentActionView, String price,
                                              String title, int imgLogo,
                                              Integer profileId, int operatorType,
                                              String titlePackageType,
                                              String requestId, String mobile)
    {
        PaymentFragment fragment = new PaymentFragment();
        fragment.setParentActionView(paymentParentActionView);

        Bundle args = new Bundle();

        args.putInt("PAYMENT_STATUS", PAYMENT_STATUS);
        args.putInt("imgLogo", imgLogo);
        args.putString("price", price);
        args.putString("title", title);

        args.putInt("OPERATOR_TYPE",operatorType);
        args.putInt("PROFILE_ID",profileId);
        args.putString("MOBILE",mobile);
        args.putString("TITLE_PACKAGE_TYPE",titlePackageType);
        args.putString("REQUEST_ID",requestId);

        fragment.setArguments(args);

        return fragment;

    }
    private void setOnClickContinueBuyTicket(OnClickContinueBuyTicket onClickContinueBuyTicket)
    {
        this.onClickContinueBuyTicketListener=onClickContinueBuyTicket;
    }


    private void setParentActionView(PaymentParentActionView pActionView)
    {
        this.pActionView = pActionView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mCard = new Result();

        if (getArguments() != null)
        {
            price = getArguments().getString("price");
            title = getArguments().getString("title");
            PAYMENT_STATUS = getArguments().getInt("PAYMENT_STATUS", 0);
            drawableIcon = getArguments().getInt("imgLogo", 0);
            operatorType=getArguments().getInt("OPERATOR_TYPE",0);
            typeCharge=getArguments().getInt("TYPE_CHARGE",0);
            simcardType=getArguments().getInt("SIMCARD_TYPE",0);
            mobile=getArguments().getString("MOBILE","");
            getObjectTypeArg(getArguments().getParcelable("response"));

            //////
            profileId=getArguments().getInt("PROFILE_ID",0);
            titlePackageType=getArguments().getString("TITLE_PACKAGE_TYPE","");
            requestId=getArguments().getString("REQUEST_ID","");

        }

        EventBus.getDefault().register(this);
    }

    private void getObjectTypeArg(Parcelable response)
    {
        switch (PAYMENT_STATUS)
        {
            case TrapConfig.PAYMENT_STAUS_GDS_FLIGHT:
            {

                break;
            }
            case TrapConfig.PAYMENT_STAUS_GDS_HOTEL:
            {

                break;
            }
            case TrapConfig.PAYMENT_STAUS_GDS_BUS:
            {

                break;
            }
            case TrapConfig.PAYMENT_STAUS_ChargeSimCard:
            {

                break;
            }
            case TrapConfig.PAYMENT_STAUS_PackSimCard:
            {

                break;
            }
            case TrapConfig.PAYMENT_STAUS_TransferMoney:
            {

                break;
            }
            case TrapConfig.PAYMENT_STAUS_WithoutCard:
            {

                break;
            }
            case TrapConfig.PAYMENT_STAUS_BillTicket:
            {

                break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);

        initView(rootView);

        return rootView;
    }

    private void initView(View rootView)
    {

        //----------------card fragment----------------
        fragmentManager = getChildFragmentManager();
        cardFragment = FavoriteCardFragment.newInstance(this);

        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        if (transaction.isEmpty())
        {
            transaction.add(R.id.container, cardFragment)
                    .commit();
        }
        else
        {
            transaction.replace(R.id.container, cardFragment)
                    .commit();
        }
        //----------------card fragment----------------
        irancellBuy = new IrancellBuyImpl();
        buyPackage=new BuyPackageImpl();
        tvAmount = rootView.findViewById(R.id.tvAmount);
        tvTitle = rootView.findViewById(R.id.tvTitle);
        imgLogo = rootView.findViewById(R.id.imgLogo);
        btnBack = rootView.findViewById(R.id.btnBack);
        btnConfirmPayment = rootView.findViewById(R.id.btnBuy);
        etCvv2 = rootView.findViewById(R.id.etCvv2);
        etPass = rootView.findViewById(R.id.etPass);
        edtExpYear = rootView.findViewById(R.id.edtExpYear);
        edtExpMound = rootView.findViewById(R.id.edtExpMound);
        llCvv2 = rootView.findViewById(R.id.llCvv2);
        llPass = rootView.findViewById(R.id.llPass);
        llExpireDate = rootView.findViewById(R.id.llExpireDate);
        rlTitle = rootView.findViewById(R.id.rlTitle);

        btnBack.setOnClickListener(clickListener);
        btnConfirmPayment.setOnClickListener(clickListener);

        setContent();
    }

    private void setContent()
    {
        tvAmount.setText(price);
        tvTitle.setText(title);

        if (drawableIcon == 0)
        {
            imgLogo.setVisibility(View.GONE);
        }
        else
        {
            Picasso.with(getActivity()).load(drawableIcon).into(imgLogo);
        }
    }

    View.OnClickListener clickListener = v ->
    {
        if (v.getId() == R.id.btnBack)
        {
            pActionView.onPaymentCancelAndBack();
        }
        else if (v.getId() == R.id.btnBuy)
        {
            if (setError())
            {
                switch (PAYMENT_STATUS)
                {
                    case TrapConfig.PAYMENT_STAUS_GDS_FLIGHT:
                    {

                        break;
                    }
                    case TrapConfig.PAYMENT_STAUS_GDS_HOTEL:
                    {

                        break;
                    }
                    case TrapConfig.PAYMENT_STAUS_GDS_BUS:
                    {

                        break;
                    }
                    case TrapConfig.PAYMENT_STAUS_ChargeSimCard:
                    {
                        irancellBuy.findDataIrancellBuyRequest(this,price,simcardType,operatorType
                                ,typeCharge,etPass.getText().toString(),mobile,cvv2,expDate,cardId);
                        break;
                    }
                    case TrapConfig.PAYMENT_STAUS_PackSimCard:
                    {
                        buyPackage.findBuyPackageDataRequest(this, requestId, operatorType,
                                cardId,titlePackageType,profileId,mobile
                                , etPass.getText().toString(),cvv2
                                ,expDate, price
                        );
                        break;
                    }
                    case TrapConfig.PAYMENT_STAUS_TransferMoney:
                    {

                        break;
                    }
                    case TrapConfig.PAYMENT_STAUS_WithoutCard:
                    {

                        break;
                    }
                    case TrapConfig.PAYMENT_STAUS_BillTicket:
                    {

                        break;
                    }
                    case TrapConfig.PAYMENT_STAUS_StudiomTicket:
                    {
                        onClickContinueBuyTicketListener.onContinueClicked();
                        break;
                    }
                }
            }
        }
    };

    private boolean setError()
    {
        return true;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener)
//        {
//            mListener = (OnFragmentInteractionListener) context;
//        } else
//        {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
//        mListener = null;

        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetCardDetails(Result card)
    {
        mCard = card;
        if (mCard.getBankBin().equals(""))
        {
            YoYo.with(Techniques.SlideOutLeft).withListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    super.onAnimationEnd(animation);
                    llCvv2.setVisibility(View.GONE);

                }
            }).duration(200)
                    .playOn(llCvv2);
            YoYo.with(Techniques.SlideOutLeft).withListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    super.onAnimationEnd(animation);
                    llPass.setVisibility(View.GONE);

                }
            }).duration(200)
                    .playOn(llPass);

            btnConfirmPayment.setAlpha(0.5f);
            btnConfirmPayment.setClickable(false);
        }
        else if (mCard.getBankBin().equalsIgnoreCase(TrapConfig.HappyBaseCardNo))
        {
            btnConfirmPayment.setAlpha(1f);
            btnConfirmPayment.setClickable(true);

            if (llPass.getVisibility() != View.VISIBLE)
            {
                llPass.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.SlideInLeft)
                        .duration(200)
                        .playOn(llPass);
            }
            if (llCvv2.getVisibility() == View.VISIBLE)
            {
                YoYo.with(Techniques.SlideOutLeft).withListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        llCvv2.setVisibility(View.GONE);

                    }
                }).duration(200)
                        .playOn(llCvv2);
            }
            if (llExpireDate.getVisibility() == View.VISIBLE)
            {
                YoYo.with(Techniques.SlideOutLeft).withListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        llExpireDate.setVisibility(View.GONE);

                    }
                }).duration(200)
                        .playOn(llExpireDate);
            }
        }
        else
        {
            llCvv2.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInLeft)
                    .duration(200)
                    .playOn(llCvv2);
            llExpireDate.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInLeft)
                    .duration(200)
                    .playOn(llExpireDate);
        }
        cardId=mCard.getCardId();
    }


    @Override
    public void showFavoriteCardParentLoading()
    {
        pActionView.showPaymentParentLoading();
    }

    @Override
    public void hideFavoriteCardParentLoading()
    {
        pActionView.hidePaymentParentLoading();
    }

    @Override
    public void startAddCardActivity()
    {
        pActionView.startAddCardActivity();
    }

    @Override
    public void showPaymentParentLoading()
    {

    }

    @Override
    public void hidePaymentParentLoading()
    {

    }

    @Override
    public void onPaymentGDSFlight()
    {

    }

    @Override
    public void onPaymentGDSHotel()
    {

    }

    @Override
    public void onPaymentGDSBus()
    {

    }

    @Override
    public void onPaymentChargeSimCard(MobileChargeResponse data, String mobile)
    {
        Tools.showToast(getActivity(),"خرید شارژ برای شماره "+mobile + "با موفقیت انجام شد", R.color.green);
        getActivity().onBackPressed();
    }

    @Override
    public void onPaymentPackSimCard(PackageBuyResponse response, String mobile)
    {
        Tools.showToast(getActivity(),"خرید بسته برای شماره "+mobile + "با موفقیت انجام شد", R.color.green);
        getActivity().onBackPressed();
    }

    @Override
    public void onErrorPackSimcard(String message)
    {
        Tools.showToast(getContext(),message , R.color.red);
    }

    @Override
    public void onPaymentTransferMoney()
    {

    }

    @Override
    public void onPaymentWithoutCard()
    {

    }

    @Override
    public void onPaymentBill()
    {

    }

    @Override
    public void onPaymentTicket()
    {

    }



    @Override
    public void onErrorCharge(String message)
    {
        Tools.showToast(getContext(),message , R.color.red);
    }

    @Override
    public void onPaymentCancelAndBack()
    {

    }
}
