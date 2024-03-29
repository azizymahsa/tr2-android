package com.traap.traapapp.ui.fragments.payment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.text.InputFilter;
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

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.buyPackage.response.PackageBuyResponse;
import com.traap.traapapp.apiServices.model.card.CardBankItem;
import com.traap.traapapp.apiServices.model.mobileCharge.response.MobileChargeResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.models.otherModels.paymentInstance.SimPackPaymentInstance;
import com.traap.traapapp.models.otherModels.paymentInstance.TicketPaymentInstance;
import com.traap.traapapp.models.otherModels.qrModel.QrModel;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.favoriteCard.FavoriteCardFragment;
import com.traap.traapapp.ui.fragments.favoriteCard.FavoriteCardParentActionView;
import com.traap.traapapp.ui.fragments.simcardCharge.imp.irancell.IrancellBuyImpl;
import com.traap.traapapp.ui.fragments.simcardPack.imp.BuyPackageImpl;
import com.traap.traapapp.utilities.ClearableEditText;

public class PaymentFragment<T, I extends PaymentParentActionView> extends BaseFragment implements FavoriteCardParentActionView,
        PaymentParentActionView, BuyChargeCardImpl.onBuyChargeCardListener, BuyPackCardImpl.onBuyPackCardListener,
        BuyMatchTicketCardImpl.onBuyMatchTicketCardListener
//        ,PaymentActionView
{
    private Context context;
    private Fragment cardFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private TextView tvEmpty;

    private LinearLayout llContent;

//    private TextView tvUserName, tvHeaderPopularNo;

    private Toolbar mToolbar;

    private CardBankItem mCard;

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
    private String cvv2 = "";
    private String expDate = "";
    private Integer cardId;
    private String requestId;
    private String titlePackageType;
    private int profileId;

//    private OnClickContinueBuyTicket onClickContinueBuyTicketListener;

    private T response;
    private SimChargePaymentInstance simChargePaymentInstance;
    private TicketPaymentInstance ticketPaymentInstance;
    private SimPackPaymentInstance simPackPaymentInstance;
    private int idBill=0;


    public PaymentFragment()
    {
        // Required empty public constructor
    }


    public static <T, I extends PaymentParentActionView> PaymentFragment newInstance(I paymentParentActionView,
                                                  String price,
                                                  String title,
                                                  int imgLogo,
                                                  String mobile,
                                                  T response,int idBill)
    {
        PaymentFragment fragment = new PaymentFragment();
        fragment.setParentActionView(paymentParentActionView);

        fragment.setBillId(idBill);
        Bundle args = new Bundle();

        args.putInt("imgLogo", imgLogo);
        args.putString("price", price);
        args.putString("title", title);
        args.putParcelable("response", (Parcelable) response);

        args.putString("MOBILE", mobile);

        fragment.setArguments(args);

        return fragment;
    }

    private void setBillId(int idBill)
    {
        this.idBill=idBill;
    }

//    public static PaymentFragment newInstance(int PAYMENT_STATUS,
//                                              String price,
//                                              String title,
//                                              int imgLogo,
//                                              PaymentParentActionView paymentParentActionView,
//                                              Object response,
//                                              int operatorType,
//                                              int simcardType,
//                                              int typeCharge,
//                                              String mobile,String s
//    )
//    {
//        PaymentFragment fragment = new PaymentFragment();
//        fragment.setParentActionView(paymentParentActionView);
//
//        Bundle args = new Bundle();
//
//        args.putInt("PAYMENT_STATUS", PAYMENT_STATUS);
//        args.putInt("imgLogo", imgLogo);
//        args.putString("price", price);
//        args.putString("title", title);
//        args.putParcelable("response", (Parcelable) response);
//
//        fragment.setArguments(args);
//
//        return fragment;
//    }

//    public static PaymentFragment newInstance(int PAYMENT_STATUS,
//                                              String price,
//                                              String title,
//                                              int imgLogo,
//                                              PaymentParentActionView paymentParentActionView,
//                                              Object response,
//                                              int operatorType,
//                                              int simcardType,
//                                              int typeCharge,
//                                              String mobile)
//    {
//        PaymentFragment fragment = new PaymentFragment();
//        fragment.setParentActionView(paymentParentActionView);
//
//        Bundle args = new Bundle();
//
//        args.putInt("PAYMENT_STATUS", PAYMENT_STATUS);
//        args.putInt("imgLogo", imgLogo);
//        args.putString("price", price);
//        args.putString("title", title);
//        args.putParcelable("response", (Parcelable) response);
//
//        args.putInt("OPERATOR_TYPE", operatorType);
//        args.putInt("SIMCARD_TYPE", simcardType);
//        args.putString("MOBILE", mobile);
//        args.putInt("TYPE_CHARGE", typeCharge);
//
//        fragment.setArguments(args);
//
//        return fragment;
//    }

//    public static PaymentFragment newInstance(int PAYMENT_STATUS, BuyTicketsFragment buyTicketsFragment,
//                                              PaymentParentActionView paymentParentActionView,
//                                              OnClickContinueBuyTicket onClickContinueBuyTicket)
//    {
//        PaymentFragment fragment = new PaymentFragment();
//        fragment.setOnClickContinueBuyTicket(onClickContinueBuyTicket);
//        fragment.setParentActionView(paymentParentActionView);
//
//        Bundle args = new Bundle();
//        args.putInt("PAYMENT_STATUS", PAYMENT_STATUS);
//        fragment.setArguments(args);
//
//        return fragment;
//    }
//
//    public static PaymentFragment newInstance(Integer PAYMENT_STATUS,
//                                              PaymentParentActionView pActionView,
//                                              String price,
//                                              String title,
//                                              Integer imgLogo,
//                                              Integer profileId,
//                                              Integer operatorType,
//                                              String titlePackageType,
//                                              String requestId,
//                                              String mobile)
//    {
//        PaymentFragment fragment = new PaymentFragment();
//        fragment.setParentActionView(pActionView);
//
//        Bundle args = new Bundle();
//
//        args.putInt("PAYMENT_STATUS", PAYMENT_STATUS);
//        args.putInt("imgLogo", imgLogo);
//        args.putString("price", price);
//        args.putString("title", title);
//
//        args.putInt("OPERATOR_TYPE", operatorType);
//        args.putInt("PROFILE_ID", profileId);
//        args.putString("MOBILE", mobile);
//        args.putString("TITLE_PACKAGE_TYPE", titlePackageType);
//        args.putString("REQUEST_ID", requestId);
//
//        fragment.setArguments(args);
//
//        return fragment;
//
//    }

//    private void setOnClickContinueBuyTicket(OnClickContinueBuyTicket onClickContinueBuyTicket)
//    {
//        this.onClickContinueBuyTicketListener = onClickContinueBuyTicket;
//    }

    private void setParentActionView(PaymentParentActionView pActionView)
    {
        this.pActionView = pActionView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mCard = new CardBankItem();

        if (getArguments() != null)
        {
            price = getArguments().getString("price");
            title = getArguments().getString("title");
            drawableIcon = getArguments().getInt("imgLogo", 0);
            mobile = getArguments().getString("MOBILE", "");

            response = getArguments().getParcelable("response");
//
//            if (response instanceof SimChargePaymentInstance)
//            {
//                simChargePaymentInstance = (SimChargePaymentInstance) response;
//
//                PAYMENT_STATUS = simChargePaymentInstance.getPAYMENT_STATUS();
//                operatorType = simChargePaymentInstance.getOperatorType();
//                typeCharge = simChargePaymentInstance.getTypeCharge();
//                simcardType = simChargePaymentInstance.getSimcardType();
//
////                PAYMENT_STATUS = getArguments().getInt("PAYMENT_STATUS", 0);
////                operatorType = getArguments().getInt("OPERATOR_TYPE", 0);
////                typeCharge = getArguments().getInt("TYPE_CHARGE", 0);
////                simcardType = getArguments().getInt("SIMCARD_TYPE", 0);
//            }
//            else if (response instanceof TicketPaymentInstance)
//            {
//                ticketPaymentInstance = (TicketPaymentInstance) response;
//
//            }
//            else if (response instanceof SimPackPaymentInstance)
//            {
//                simPackPaymentInstance = (SimPackPaymentInstance) response;
//
//                profileId = simPackPaymentInstance.getProfileId();
//                titlePackageType = simPackPaymentInstance.getTitlePackageType();
//                requestId = simPackPaymentInstance.getRequestId();
//
////                profileId = getArguments().getInt("PROFILE_ID", 0);
////                titlePackageType = getArguments().getString("TITLE_PACKAGE_TYPE", "");
////                requestId = getArguments().getString("REQUEST_ID", "");
//            }


//            getObjectTypeArg(response);

            //////

            EventBus.getDefault().register(this);
        }

      //  EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_payment, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

//        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> pActionView.openDrawer());
//        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> pActionView.backToMainFragment());
      //  tvUserName = mToolbar.findViewById(R.id.tvUserName);
        //tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
     //   tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView)
    {

        //----------------card fragment----------------
        fragmentManager = getChildFragmentManager();
        cardFragment = FavoriteCardFragment.newInstance(this);

        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        if (transaction.isEmpty())
        {
            transaction.add(R.id.container, cardFragment)
                    .commit();
        } else
        {
            transaction.replace(R.id.container, cardFragment)
                    .commit();
        }
        //----------------card fragment----------------
        irancellBuy = new IrancellBuyImpl();
        buyPackage = new BuyPackageImpl();
        tvAmount = rootView.findViewById(R.id.tvAmount);
        tvTitle = rootView.findViewById(R.id.tvTitle);
        tvEmpty = rootView.findViewById(R.id.tvEmpty);
        llContent = rootView.findViewById(R.id.llContent);
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

        etCvv2.setFilters(new InputFilter[] { new InputFilter.LengthFilter(4) });
        edtExpMound.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2) });
        edtExpYear.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2) });

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
//            pActionView.onPaymentCancelAndBack();
            getActivity().onBackPressed();
        }
        else if (v.getId() == R.id.btnBuy)
        {
            if (setError())
            {
                if (response instanceof SimChargePaymentInstance)
                {
                    simChargePaymentInstance = (SimChargePaymentInstance) response;

                    PAYMENT_STATUS = simChargePaymentInstance.getPAYMENT_STATUS();
                    operatorType = simChargePaymentInstance.getOperatorType();
                    typeCharge = simChargePaymentInstance.getTypeCharge();
                    simcardType = simChargePaymentInstance.getSimcardType();

                }
                else if (response instanceof TicketPaymentInstance)
                {
                    ticketPaymentInstance = (TicketPaymentInstance) response;

                }
                else if (response instanceof SimPackPaymentInstance)
                {
                    simPackPaymentInstance = (SimPackPaymentInstance) response;

                    profileId = simPackPaymentInstance.getProfileId();
                    titlePackageType = simPackPaymentInstance.getTitlePackageType();
                    requestId = simPackPaymentInstance.getRequestId();

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
        this.context = context;
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
    public void onGetCardDetails(CardBankItem card)
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
        } else
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
        cardId = mCard.getCardId();
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
    public void onEmptyCard()
    {
        llContent.setVisibility(View.INVISIBLE);
        tvEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPaymentParentLoading()
    {
        pActionView.showPaymentParentLoading();
    }

    @Override
    public void hidePaymentParentLoading()
    {
        pActionView.hidePaymentParentLoading();
    }

//    @Override
//    public void onPaymentGDSFlight()
//    {
//
//    }
//
//    @Override
//    public void onPaymentGDSHotel()
//    {
//
//    }
//
//    @Override
//    public void onPaymentGDSBus()
//    {
//
//    }
//
//    @Override
//    public void onPaymentChargeSimCard(MobileChargeResponse data, String mobile)
//    {
//        showToast(getActivity(), "خرید شارژ برای شماره " + mobile + "با موفقیت انجام شد", R.color.green);
//        getActivity().onBackPressed();
//    }
//
//    @Override
//    public void onPaymentPackSimCard(PackageBuyResponse response, String mobile)
//    {
//        showToast(getActivity(), "خرید بسته برای شماره " + mobile + "با موفقیت انجام شد", R.color.green);
//        getActivity().onBackPressed();
//    }
//
//    @Override
//    public void onErrorPackSimcard(String message)
//    {
//        showToast(getActivity(), message, R.color.red);
//    }
//
//    @Override
//    public void onPaymentTransferMoney()
//    {
//
//    }
//
//    @Override
//    public void onShowPaymentWithoutCardFragment(@Nullable QrModel model)
//    {
//
//    }
//
//    @Override
//    public void onPaymentBill()
//    {
//
//    }
//
//    @Override
//    public void onPaymentTicket()
//    {
//
//    }
//
//    @Override
//    public void onErrorCharge(String message)
//    {
//        showToast(getActivity(), message, R.color.red);
//    }

    @Override
    public void onPaymentCancelAndBack()
    {
        getActivity().onBackPressed();
    }


    @Override
    public void onBuyChargeCardCompleted(String message)
    {
        showAlertSuccess(context, message, "", true);
    }

    @Override
    public void onBuyChargeCardError(String message)
    {
        showAlertFailure(context, message, getString(R.string.error), false);
    }

    @Override
    public void onBuyMatchTicketCardCompleted(String message)
    {
        showAlertSuccess(context, message, "", true);
    }

    @Override
    public void onBuyMatchTicketCardError(String message)
    {
        showAlertFailure(context, message, getString(R.string.error), false);
    }

    @Override
    public void onBuyPackCardCompleted(String message)
    {
        showAlertSuccess(context, message, "", true);
//        showToast(getActivity(), "خرید بسته برای شماره " + mobile + "با موفقیت انجام شد", R.color.green);
//        getActivity().onBackPressed();
    }

    @Override
    public void onBuyPackCardError(String message)
    {
        showAlertFailure(context, message, getString(R.string.error), false);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
