package com.traap.traapapp.ui.fragments.simcardCharge;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.RecyclerView;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.availableAmount.AvailableAmounResponse;
import com.traap.traapapp.apiServices.model.availableAmount.Result;
import com.traap.traapapp.apiServices.model.buyPackage.response.PackageBuyResponse;
import com.traap.traapapp.apiServices.model.contact.OnSelectContact;
import com.traap.traapapp.apiServices.model.getBoughtFor.GetBoughtForResponse;
import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.apiServices.model.mobileCharge.response.MobileChargeResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.BarcodeType;
import com.traap.traapapp.enums.LeagueTableParent;
import com.traap.traapapp.enums.MediaPosition;
import com.traap.traapapp.enums.PredictPosition;
import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.models.otherModels.paymentInstance.SimPackPaymentInstance;
import com.traap.traapapp.models.otherModels.qrModel.QrModel;
import com.traap.traapapp.ui.activities.main.OnContactClick;
import com.traap.traapapp.ui.adapters.charge.ChargeAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.events.PersonEvent;
import com.traap.traapapp.ui.fragments.main.BuyTicketAction;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.payment.PaymentActionView;
import com.traap.traapapp.ui.fragments.payment.PaymentParentActionView;
import com.traap.traapapp.ui.fragments.simcardCharge.imp.irancell.IrancellBuyImpl;
import com.traap.traapapp.ui.fragments.simcardCharge.imp.mci.MciBuyImpl;
import com.traap.traapapp.ui.fragments.simcardCharge.imp.mci.MciBuyInteractor;
import com.traap.traapapp.ui.fragments.simcardCharge.imp.rightel.RightelBuyImpl;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import static com.traap.traapapp.utilities.Utility.changeFontInViewGroup;

/**
 * Created by Javad.Abadi on 7/14/2018.
 */
@SuppressLint("ValidFragment")
public class ChargeFragment extends BaseFragment
        implements OnContactClick, IrancellBuyImpl.OnFinishedIrancellBuyListener,
        ChargeFragmentInteractor, CompoundButton.OnCheckedChangeListener, OnAnimationEndListener, View.OnFocusChangeListener,
        RightelBuyImpl.OnFinishedRightelBuyListener, PaymentParentActionView,
        MciBuyInteractor.OnFinishedMciBuyInListener, TextWatcher, MainActionView, PaymentActionView
        , OnClickContinueSelectPayment, ChargeAdapter.ChargeAdapterEvent

{

    private Context context;
    private Fragment pFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;


    private int operatorType = 0;

    private static int OPERATOR_TYPE_MCI = 2;
    private static int OPERATOR_TYPE_MTN = 1;
    private static int OPERATOR_TYPE_RIGHTEL = 3;

    private MainActionView mainView;

    private static final int SIMCARD_TYPE_ETEBARI = 0;
    private static final int SIMCARD_TYPE_DAEMI = 1;
    private int simcardType = 0;
    private String amount,amounAfterTax;
    private String mobile;
    private int imageDrawable = 0;
    private View mToolbar;
    private TextView tvUserName;
    private TextView tvHeaderPopularNo;
    private OnClickContinueSelectPayment onClickContinueBuyChargeListener;
    private Integer backState;


    public ChargeFragment()
    {

    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    public static ChargeFragment newInstance(MainActionView mainView, Integer backState)//, OnClickContinueSelectPayment onClickContinueBuyCharg)
    {
        ChargeFragment f = new ChargeFragment();
        // f.setContinueSelectPayment(onClickContinueBuyCharg);
        f.setMainView(mainView);
        f.setBackState(backState);
        return f;
    }

    private void setBackState(Integer backState)
    {
        this.backState = backState;
    }

    private void setContinueSelectPayment(OnClickContinueSelectPayment mainView)
    {
        this.onClickContinueBuyChargeListener = mainView;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    private String[] irancellFilter = {"سیم کارت اعتباري", "سیم کارت دائمي"};
    private String[] irancelTypeChargelFilter = {"شارژ مستقیم", "شارژ شگفت انگیز"};
    private String[] rightelTypeChargelFilter = {"شارژ مستقیم", "شارژ شور انگیز"};
    private String[] amountFilter = {"10,000", "20,000", "50,000", "100,000", "200,000", "500,000"};


    private View rootView;
    private IrancellBuyImpl irancellBuy;
    private MciBuyImpl mciBuy;
    private RightelBuyImpl rightelBuy;
    private String cardNumber, cardName, chargeType = "0", cardNumberCheck, ccv2, chargeName = "شارژ عادی";
    private int profileType = 0;
    private int rightelType = 2;
    private boolean isMci = true, isMtn = false, isRightel = false, isInitView = true;

    @BindView(R.id.tabLayoutIrancell)
    TabLayout tabLayoutIrancell;

    @BindView(R.id.contentView)
    LinearLayout contentView;

    @BindView(R.id.imgBack)
    View imgBack;

    @BindView(R.id.imgMenu)
    ImageView imgMenu;

    @BindView(R.id.rvMciAmount)
    RecyclerView rvMciAmount;

    @BindView(R.id.rvIrancellAmount)
    RecyclerView rvIrancellAmount;

    @BindView(R.id.swIrancell)
    SwitchCompat swIrancell;
    @BindView(R.id.swRightel)
    SwitchCompat swRightel;

    @BindView(R.id.rvRightelAmount)
    RecyclerView rvRightelAmount;


    @BindView(R.id.tvAmpuntPassCharge)
    TextView tvAmpuntPassCharge;
    @BindView(R.id.tvDescriptionSelectedOperator)
    TextView tvDescriptionSelectedOperator;
    @BindView(R.id.ivSelectedOperator)
    CircleImageView ivSelectedOperator;
    @BindView(R.id.llOperatorImages)
    LinearLayout llOperatorImages;


    @BindView(R.id.autoCompletePhoneNumberMci)
    AutoCompleteTextView autoCompletePhoneNumberMci;

    @BindView(R.id.autoCompletePhoneNumberIrancel)
    AutoCompleteTextView autoCompletePhoneNumberIrancel;

    @BindView(R.id.autoCompletePhoneNumberRightel)
    AutoCompleteTextView autoCompletePhoneNumberRightel;

    @BindView(R.id.flIrancell)
    FrameLayout flIrancell;
    @BindView(R.id.flHamraheAval)
    FrameLayout flHamraheAval;
    @BindView(R.id.flRightel)
    FrameLayout flRightel;
    @BindView(R.id.ivIrancell)
    CircleImageView ivIrancell;
    @BindView(R.id.ivHamraheAval)
    CircleImageView ivHamraheAval;
    @BindView(R.id.ivRightel)
    CircleImageView ivRightel;

    @BindView(R.id.btnBackToCharge)
    View btnBackToCharge;








    @BindView(R.id.spinnerChargeTypeMci)
    Spinner spinnerChargeTypeMci;


    @BindView(R.id.btnBuyCharge)
    CircularProgressButton btnBuyCharge;
    /* @BindView(R.id.etChargeAmount)
     ClearableEditText etChargeAmount;*/
    @BindView(R.id.etCvv2)
    ClearableEditText etCvv2;



    @BindView(R.id.btnIrancellRecent)
    View btnIrancellRecent;
    @BindView(R.id.btnMciRecent)
    View btnMciRecent;
    @BindView(R.id.btnRightelRecent)
    View btnRightelRecent;


    /*@BindView(R.id.etMobileChargeRightel)
    ClearableEditText etMobileChargeRightel;*/


    /* @BindView(R.id.etMobileCharge)
     ClearableEditText etMobileCharge;*/
    /*@BindView(R.id.etMCINumber)
    ClearableEditText etMCINumber;*/
    @BindView(R.id.tvChargeTitle)
    TextView tvChargeTitle;
    @BindView(R.id.ivContactI)
    ImageView ivContactI;
    @BindView(R.id.ivContactM)
    ImageView ivContactM;
    @BindView(R.id.ivContactR)
    ImageView ivContactR;
    @BindView(R.id.llPassCharge)
    LinearLayout llPassCharge;
    @BindView(R.id.rlIrancellSpinner)
    LinearLayout rlIrancellSpinner;
    @BindView(R.id.llMCICharge)
    LinearLayout llMCICharge;
    @BindView(R.id.llMTNCharge)
    LinearLayout llMTNCharge;
    @BindView(R.id.llRightelCharge)
    LinearLayout llRightelCharge;
    @BindView(R.id.llCvv2)
    LinearLayout llCvv2;
    @BindView(R.id.etPassCharge)
    ClearableEditText etPassCharge;
    @BindView(R.id.tlPassCharge)
    TextInputLayout tlPassCharge;
    @BindView(R.id.rbNormalChargeIrancell)
    RadioButton rbNormalChargeIrancell;
    @BindView(R.id.rbSpecialChargeIrancell)
    RadioButton rbSpecialChargeIrancell;
    @BindView(R.id.rbYoungMCN)
    RadioButton rbYoungMCN;
    @BindView(R.id.rbDirectMCN)
    RadioButton rbDirectMCN;
    @BindView(R.id.rbWomenMCN)
    RadioButton rbWomenMCN;
    @BindView(R.id.rbSpecialChargeRightel)
    RadioButton rbSpecialChargeRightel;
    @BindView(R.id.rbNormalChargeRightel)
    RadioButton rbNormalChargeRightel;
    /* @BindView(R.id.tilMIrancell)
     TextInputLayout tilMIrancell;*/
    /*@BindView(R.id.tilMMci)
    TextInputLayout tilMMci;*/
/*    @BindView(R.id.tilMRightel)

    TextInputLayout tilMRightel;*/
    @BindView(R.id.tipCvv2)
    TextInputLayout tipCvv2;
    @BindView(R.id.nested)
    NestedScrollView nested;
    private List<Result> mciResultList = new ArrayList<>();
    private List<Result> mtnResultList = new ArrayList<>();
    private List<Result> rightelResultList = new ArrayList<>();
    private List<Result> mciResultListFilter = new ArrayList<>();
    private List<Result> mtnResultListFilter = new ArrayList<>();
    private List<Result> rightelResultListFilter = new ArrayList<>();

    @OnClick(R.id.ivContactR)
    void ivContactR()
    {

        mainView.onContact();
    }

    @OnClick(R.id.autoCompletePhoneNumberIrancel)
    void autoCompletePhoneNumberIrancel()
    {

      /*  View view = getActivity().getCurrentFocus();
        if (view != null)
        {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        }*/
    }

    @OnClick(R.id.ivContactM)
    void ivContactM()
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;

        }
        mainView.onContact();

    }

    @OnClick(R.id.ivContactI)
    void ivContactI()
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;

        }
        mainView.onContact();

    }


    @OnClick(R.id.btnIrancellRecent)
    void irancellRecent()
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;

        } else
        {
            autoCompletePhoneNumberMci.setText("0"+Prefs.getString("mobile", ""));
            autoCompletePhoneNumberIrancel.setText("0"+Prefs.getString("mobile", ""));
            autoCompletePhoneNumberRightel.setText("0"+Prefs.getString("mobile", ""));


        }


    }

    @OnClick(R.id.btnMciRecent)
    void mciRecent()
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;

        } else
        {
            autoCompletePhoneNumberMci.setText("0"+Prefs.getString("mobile", ""));
            autoCompletePhoneNumberIrancel.setText("0"+Prefs.getString("mobile", ""));
            autoCompletePhoneNumberRightel.setText("0"+Prefs.getString("mobile", ""));


        }


    }

    @OnClick(R.id.btnRightelRecent)
    void rightelRecent()
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;

        } else
        {
            autoCompletePhoneNumberMci.setText("0"+Prefs.getString("mobile", ""));
            autoCompletePhoneNumberIrancel.setText("0"+Prefs.getString("mobile", ""));
            autoCompletePhoneNumberRightel.setText("0"+Prefs.getString("mobile", ""));


        }

    }


    @OnClick(R.id.flIrancell)
    void irancell()
    {

        autoCompletePhoneNumberIrancel.requestFocus();
        autoCompletePhoneNumberIrancel.setSelection(autoCompletePhoneNumberIrancel.getText().length());
        closeAutoComplete();

//        tvChargeTitle.setText("خرید شارژ آنلاین " + "ایرانسل");
        ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.btnColorSecondary));
        ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        ivIrancell.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.irancell));
        ivHamraheAval.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.hamrahe_aval2));
        ivRightel.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rightel2));

        llOperatorImages.setVisibility(View.VISIBLE);
        llMCICharge.setVisibility(View.GONE);
        llPassCharge.setVisibility(View.GONE);
        llRightelCharge.setVisibility(View.GONE);
        llMTNCharge.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInUp)
                .duration(200)
                .playOn(llMTNCharge);
        new Handler().postDelayed(() ->
        {
            rlIrancellSpinner.setVisibility(View.VISIBLE);
        }, 250);
        isMtn = true;
        isMci = false;
        isRightel = false;

    }


    @OnClick(R.id.flHamraheAval)
    void hamraheAval()
    {

        autoCompletePhoneNumberMci.requestFocus();
        autoCompletePhoneNumberMci.setSelection(autoCompletePhoneNumberMci.getText().length());
        closeAutoComplete();

        rlIrancellSpinner.setVisibility(View.INVISIBLE);

//        tvChargeTitle.setText("خرید شارژ آنلاین " + "همراه اول");
        ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.btnColorSecondary));
        ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        ivIrancell.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.irancell2));
        ivHamraheAval.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.hamrahe_aval));
        ivRightel.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rightel2));


        llMTNCharge.setVisibility(View.GONE);
        llPassCharge.setVisibility(View.GONE);
        llRightelCharge.setVisibility(View.GONE);
        llMCICharge.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInUp)
                .duration(200)
                .playOn(llMCICharge);
        isMtn = false;
        isMci = true;
        isRightel = false;

    }

    @OnClick(R.id.flRightel)
    void rightel()
    {

        autoCompletePhoneNumberRightel.requestFocus();
        autoCompletePhoneNumberRightel.setSelection(autoCompletePhoneNumberRightel.getText().length());
        closeAutoComplete();

        rlIrancellSpinner.setVisibility(View.INVISIBLE);

//        tvChargeTitle.setText("خرید شارژ آنلاین " + "رایتل");
        ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.btnColorSecondary));

        ivIrancell.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.irancell2));
        ivHamraheAval.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.hamrahe_aval2));
        ivRightel.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rightel));


        llMCICharge.setVisibility(View.GONE);
        llPassCharge.setVisibility(View.GONE);
        llRightelCharge.setVisibility(View.VISIBLE);
        llMTNCharge.setVisibility(View.GONE);
        YoYo.with(Techniques.SlideInUp)
                .duration(200)
                .playOn(llRightelCharge);

        isMtn = false;
        isMci = false;
        isRightel = true;
    }


    @OnClick(R.id.btnBuyCharge)
    void setBtnBuyCharge()
    {

    }


    @OnClick(R.id.btnBackToCharge)
    public void setBtnBackToCharge()
    {
        if (isMtn)
        {
            llOperatorImages.setVisibility(View.VISIBLE);
            llPassCharge.setVisibility(View.GONE);
            llMTNCharge.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInLeft)
                    .duration(200)
                    .playOn(llMTNCharge);
            return;
        }
        if (isMci)
        {
            llPassCharge.setVisibility(View.GONE);
            llOperatorImages.setVisibility(View.VISIBLE);
            llMCICharge.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInLeft)
                    .duration(200)
                    .playOn(llMCICharge);
            return;

        }
        if (isRightel)
        {
            llOperatorImages.setVisibility(View.VISIBLE);
            llPassCharge.setVisibility(View.GONE);
            llRightelCharge.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInLeft)
                    .duration(200)
                    .playOn(llMCICharge);

        }


    }


    private void setDataLayoutPassCharge()
    {
        imageDrawable = 0;
        String chargeStr = "";
        if (isMtn)
        {
            //  amount = spinnerAmountIrancell.getSelectedItem().toString();
            // amount = etChargeAmount.getText().toString();
            imageDrawable = R.drawable.irancell;
            chargeStr = "ایرانسل";
            mobile = autoCompletePhoneNumberIrancel.getText().toString();
            // chargeType=spinnerChargeTypeIrancell.getSelectedItem().toString();

        } else if (isMci)
        {
         //   amount = spinnerAmountMci.getSelectedItem().toString();
            //  amount = etMCIAmount.getText().toString();
            imageDrawable = R.drawable.hamrahe_aval;
            chargeStr = "همراه اول";
            mobile = autoCompletePhoneNumberMci.getText().toString();
            // chargeType=spinnerChargeTypeMci.getSelectedItem().toString();

        } else if (isRightel)
        {
         //   amount = spinnerAmountRightel.getSelectedItem().toString();
            // amount = etChargeAmountRightel.getText().toString();
            imageDrawable = R.drawable.rightel;
            chargeStr = "رایتل";
            mobile = autoCompletePhoneNumberRightel.getText().toString();
            //chargeType=spinnerChargeTypeRightel.getSelectedItem().toString();

        }


        //----------------------------new for payment fragment-----------------------

        String title = "با انجام این پرداخت ، مبلغ " + amount + " ریال بابت شارژ موبایل " + mobile + "، از حساب شما کسر خواهد شد.";

        //fragmentManager = getChildFragmentManager();
        operatorType = getOperatorType(mobile);

        SimChargePaymentInstance paymentInstance = new SimChargePaymentInstance();
        paymentInstance.setPAYMENT_STATUS(TrapConfig.PAYMENT_STAUS_ChargeSimCard);
        paymentInstance.setOperatorType(operatorType);
        paymentInstance.setSimcardType(simcardType);
        paymentInstance.setTypeCharge(Integer.valueOf(chargeType));

        getUrlChargePayment(amounAfterTax, operatorType, simcardType, chargeType, mobile);

        //////////////////////////////////////////////////////////////////////


        //  ((TextView) rootView.findViewById(R.id.tvTitle)).setText("پرداخت");

     /*   YoYo.with(Techniques.SlideOutRight).withListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                super.onAnimationEnd(animation);
                contentView.setVisibility(View.GONE);

            }
        }).duration(200)
                .playOn(llCvv2);*/


        //----------------------------new for payment fragment-----------------------
    }

    private void getUrlChargePayment(String amount, int operatorType, int simcardType, String chargeType, String mobile)
    {
        mainView.showLoading();

        irancellBuy.findDataIrancellBuyRequest(this, amount, operatorType, simcardType, chargeType, mobile);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(rootView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
            return rootView;
        rootView = inflater.inflate(R.layout.fragment_charge, container, false);

        ((TextView) rootView.findViewById(R.id.tvTitle)).setText("شارژ");

        irancellBuy = new IrancellBuyImpl();
        mciBuy = new MciBuyImpl();
        rightelBuy = new RightelBuyImpl();
        getAvailableAmount();


//        fragmentManager = getChildFragmentManager();
//        pFragment = FavoriteCardFragment.newInstance(this);
//
//        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//
//        if (transaction.isEmpty())
//        {
//            transaction.add(R.id.container, pFragment)
//                    .commit();
//        }
//        else
//        {
//            transaction.replace(R.id.container, pFragment)
//                    .commit();
//        }
        return rootView;
    }

    private void checkPhoneNumberOperator()
    {
        autoCompletePhoneNumberIrancel.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count)
            {
                try
                {
                    if (autoCompletePhoneNumberIrancel.getText().toString().length() == 4 || autoCompletePhoneNumberIrancel.getText().toString().length() ==11 )
                    {
                        operatorType = getOperatorType(charSequence.toString());

                        if (OPERATOR_TYPE_MCI == getOperatorType(charSequence.toString()))
                        {

                            if (isMci)
                            {

                            } else
                            {
                                autoCompletePhoneNumberMci.setText(charSequence.toString());
                                hamraheAval();
                                return;

                            }

                        } else if (OPERATOR_TYPE_RIGHTEL == getOperatorType(charSequence.toString()))
                        {

                            if (isRightel)
                            {

                            } else
                            {
                                autoCompletePhoneNumberRightel.setText(charSequence.toString());
                                rightel();
                                return;

                            }
                        }
                    }
                } catch (Exception e)
                {

                }

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        autoCompletePhoneNumberMci.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count)
            {
                try
                {
                    if (autoCompletePhoneNumberMci.getText().toString().length() == 4 || autoCompletePhoneNumberMci.getText().toString().length() == 11)
                    {
                        operatorType = getOperatorType(charSequence.toString());

                        if (OPERATOR_TYPE_MTN == getOperatorType(charSequence.toString()))
                        {

                            if (isMtn)
                            {

                            } else
                            {
                                autoCompletePhoneNumberIrancel.setText(charSequence.toString());
                                irancell();
                                return;
                            }


                        } else if (OPERATOR_TYPE_RIGHTEL == getOperatorType(charSequence.toString()))
                        {

                            if (isRightel)
                            {

                            } else
                            {
                                autoCompletePhoneNumberRightel.setText(charSequence.toString());
                                rightel();
                                return;

                            }

                        }
                    }
                } catch (Exception e)
                {

                }

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        autoCompletePhoneNumberRightel.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count)
            {
                try
                {
                    if (autoCompletePhoneNumberRightel.getText().toString().length() == 4 || autoCompletePhoneNumberRightel.getText().toString().length() == 11){
                    operatorType = getOperatorType(charSequence.toString());

                    if (OPERATOR_TYPE_MCI == getOperatorType(charSequence.toString()))
                    {

                        if (isMci)
                        {

                        } else
                        {
                            autoCompletePhoneNumberMci.setText(charSequence.toString());
                            hamraheAval();
                        }

                    } else if (OPERATOR_TYPE_MTN == getOperatorType(charSequence.toString()))
                    {

                        if (isMtn)
                        {

                        } else
                        {
                            autoCompletePhoneNumberIrancel.setText(charSequence.toString());
                            irancell();
                        }

                    }
                    }
                } catch (Exception e)
                {

                }

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();

        etCvv2.setText("");
        etPassCharge.setText("");
        // etChargeAmount.setText("");
        // etMCIAmount.setText("");
        // etChargeAmountRightel.setText("");
        if (isInitView)
        {
            isInitView = false;
            initView();
        }

        checkPhoneNumberOperator();

        setDataTypeChargeSpinner();

        setDataAmountSpinner();

    }

    private void setDataTypeChargeSpinner()
    {

        swIrancell.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            if (isChecked)
            {
                profileType = 1;
                chargeName = "شارژ شگفت انگیز";
                chargeType = "1";

            } else
            {


                chargeType = "0";
                profileType = 0;
                chargeName = "شارژ عادی";

            }
            irancellFilter(simcardType, profileType);

        });


        swRightel.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            if (isChecked)
            {
                profileType = 1;
                chargeName = "شارژ شور انگیز";
                chargeType = "1";

            } else
            {


                chargeType = "0";
                profileType = 0;
                chargeName = "شارژ عادی";

            }
            rightelFilter(simcardType, profileType);

        });


        //  spinnerChargeTypeMci.setAdapter(adapterTypeChargeIrancell);
        spinnerChargeTypeMci.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (spinnerChargeTypeMci.getSelectedItemPosition() == 0)
                {

                    profileType = 1;
                    chargeName = "شارژ شور انگیز";
                    chargeType = "1";
                } else
                {

                    profileType = 1;
                    chargeName = "شارژ شگفت انگیز";
                    chargeType = "1";

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });








        if (tabLayoutIrancell.getTabCount() == 2)
            return;
        tabLayoutIrancell.addTab(tabLayoutIrancell.newTab().setText("سیم کارت دائمی"));
        tabLayoutIrancell.addTab(tabLayoutIrancell.newTab().setText(" سیم کارت اعتباری"), true);
        tabLayoutIrancell.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                if (tabLayoutIrancell.getSelectedTabPosition() == 0)
                {
                    simcardType = SIMCARD_TYPE_DAEMI;
                } else
                {
                    simcardType = SIMCARD_TYPE_ETEBARI;
                }
                irancellFilter(simcardType, profileType);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });


        changeFontInViewGroup(tabLayoutIrancell, "fonts/iran_sans_normal.ttf");


    }

    private void setDataAmountSpinner()
    {

    }

    @Override
    public void onStop()
    {
        super.onStop();
        etPassCharge.setText("");
        //  EventBus.getDefault().unregister(this);
    }


    private void initView()
    {
        mToolbar = rootView.findViewById(R.id.toolbar);
        FrameLayout flLogoToolbar = rootView.findViewById(R.id.flLogoToolbar);
        flLogoToolbar.setOnClickListener(v ->
        {
            mainView.backToMainFragment();

        });

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView ->
                        mainView.backToAllServicePackage(backState,4)
                // onClickContinueBuyChargeListener.onBackClicked()
        );
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("خرید شارژ");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(11);
        autoCompletePhoneNumberMci.setFilters(filterArray);
        autoCompletePhoneNumberIrancel.setFilters(filterArray);
        autoCompletePhoneNumberRightel.setFilters(filterArray);

        imgMenu.setOnClickListener(v ->
        {
            mainView.openDrawer();
        });

        imgBack.setOnClickListener(v ->
        {
            mainView.backToAllServicePackage(backState,4);
        });

       // btnChargeConfirmRightel.setText("ادامه");

        //btnChargeConfirm.setText("ادامه");
        // btnBackToCharge.setText("بازگشت");
        tlPassCharge.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        tipCvv2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        // etChargeAmountRightel.addTextChangedListener(new NumberTextWatcher(etChargeAmountRightel));
        // etMCIAmount.addTextChangedListener(new NumberTextWatcher(etMCIAmount));
        rbSpecialChargeIrancell.setOnCheckedChangeListener(this);
        rbNormalChargeIrancell.setOnCheckedChangeListener(this);
        rbYoungMCN.setOnCheckedChangeListener(this);
        rbDirectMCN.setOnCheckedChangeListener(this);
        rbWomenMCN.setOnCheckedChangeListener(this);
        rbNormalChargeRightel.setOnCheckedChangeListener(this);
        rbSpecialChargeRightel.setOnCheckedChangeListener(this);
        rbNormalChargeIrancell.setChecked(true);
        rbDirectMCN.setChecked(true);
        rbYoungMCN.setChecked(true);
        rbNormalChargeRightel.setChecked(true);


        // etMobileCharge.setFilters(filterArray);
        // etMCINumber.setFilters(filterArray);
        // etMobileChargeRightel.setFilters(filterArray);

        initDefaultOperatorView();

        try
        {

            if (!cardNumberCheck.equals(TrapConfig.HappyBaseCardNo))
            {
                llCvv2.setVisibility(View.VISIBLE);

            }
        } catch (NullPointerException e)
        {

        }
        getBoughtForRequest();
/*        flLogoToolbar.setOnClickListener(v -> {
            mainView.backToMainFragment();

        });*/
     /*   btnIrancellRecent.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_phone_book));
        btnMciRecent.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_phone_book));
        btnRightelRecent.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_phone_book));*/

    }

    private void getBoughtForRequest()
    {
        SingletonService.getInstance().getBoughtForService().getBoughtFor_InCharge(new OnServiceStatus<WebServiceClass<GetBoughtForResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetBoughtForResponse> response)
            {
                try
                {
                    if (response.info.statusCode == 200)
                    {

                        onGetBoutForSuccess(response.data.getResults());

                    } else
                    {
                        showToast(((Activity) context), response.info.message, R.color.red);
                    }
                } catch (Exception e)
                {
                    showToast(((Activity) context), e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
                if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                {
                    showToast(getActivity(), "خطای دسترسی به سرور!", 0);
                } else
                {
                    showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                }

            }
        });
    }

    private void onGetBoutForSuccess(List<String> results)
    {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), R.layout.custom_spinner_dropdown_item, results);
        autoCompletePhoneNumberMci.setThreshold(1);//will start working from first character
        autoCompletePhoneNumberMci.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

        autoCompletePhoneNumberIrancel.setThreshold(1);//will start working from first character
        autoCompletePhoneNumberIrancel.setAdapter(adapter);

        autoCompletePhoneNumberRightel.setThreshold(1);//will start working from first character
        autoCompletePhoneNumberRightel.setAdapter(adapter);
    }

    private void initDefaultOperatorView()
    {
        operatorType = getOperatorType("0"+Prefs.getString("mobile", ""));
        // operatorType = getOperatorType("09121234567");

        switch (operatorType)
        {
            case 2:
            {
//                tvChargeTitle.setText("خرید شارژ آنلاین " + "همراه اول");

                llMCICharge.setVisibility(View.VISIBLE);
                llMTNCharge.setVisibility(View.GONE);
                llRightelCharge.setVisibility(View.GONE);

                rlIrancellSpinner.setVisibility(View.INVISIBLE);

                ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.btnColorSecondary));
                ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

                ivIrancell.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.irancell2));
                ivHamraheAval.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.hamrahe_aval));
                ivRightel.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rightel2));

                isMtn = false;
                isMci = true;
                isRightel = false;
                break;
            }
            case 1:
            {
//                tvChargeTitle.setText("خرید شارژ آنلاین " + "ایرانسل");

                llMCICharge.setVisibility(View.GONE);
                llMTNCharge.setVisibility(View.VISIBLE);
                llRightelCharge.setVisibility(View.GONE);

                new Handler().postDelayed(() ->
                {
                    rlIrancellSpinner.setVisibility(View.VISIBLE);


                }, 250);

                ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.btnColorSecondary));
                ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

                ivIrancell.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.irancell));
                ivHamraheAval.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.hamrahe_aval2));
                ivRightel.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rightel2));


                isMtn = true;
                isMci = false;
                isRightel = false;
                break;
            }
            case 3:
            {
//                tvChargeTitle.setText("خرید شارژ آنلاین " + "رایتل");

                llMCICharge.setVisibility(View.GONE);
                llMTNCharge.setVisibility(View.GONE);
                llRightelCharge.setVisibility(View.VISIBLE);

                rlIrancellSpinner.setVisibility(View.INVISIBLE);

                ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.btnColorSecondary));

                ivIrancell.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.irancell2));
                ivHamraheAval.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.hamrahe_aval2));
                ivRightel.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rightel));

                isMtn = false;
                isMci = false;
                isRightel = true;
                break;
            }
        }
        llPassCharge.setVisibility(View.GONE);


//        llMCICharge.setVisibility(View.VISIBLE);
//        llMTNCharge.setVisibility(View.GONE);
//        llRightelCharge.setVisibility(View.GONE);
//        ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
//        ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.btnColorSecondary));
//        ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
//        isMtn = false;
//        isMci = true;
//        isRightel = false;
        autoCompletePhoneNumberMci.setText("0"+Prefs.getString("mobile", ""));
        autoCompletePhoneNumberIrancel.setText("0"+Prefs.getString("mobile", ""));
        autoCompletePhoneNumberRightel.setText("0"+Prefs.getString("mobile", ""));
        // etMCIAmount.setOnFocusChangeListener(this);
/*        etMCINumber.setOnFocusChangeListener(this);
        etMobileCharge.setOnFocusChangeListener(this);
        etMobileChargeRightel.setOnFocusChangeListener(this);*/
        //   etChargeAmountRightel.setOnFocusChangeListener(this);
        // etMCIAmount.setInputType(InputType.TYPE_NULL);
        // etChargeAmountRightel.setInputType(InputType.TYPE_NULL);

        // etMobileCharge.addTextChangedListener(this);
        // etMCINumber.addTextChangedListener(this);
        // etMobileChargeRightel.addTextChangedListener(this);

    }

    private int  getOperatorType(String phoneNo)
    {
        String startPhoneNo = phoneNo.substring(0, 4);
        Logger.e("-startPhoneNo-", startPhoneNo);

        String[] typeMCI_No = {"0990", "0991", "0910", "0911", "0912", "0913", "0914", "0915", "0916", "0917", "0918", "0919", "0992","0994"};
        String[] typeMTN_No = {"0901", "0902", "0903", "0905", "0930", "0933", "0935", "0936", "0937", "0938", "0939"};
        String[] typeRightel_No = {"0920", "0921", "0922"};

        if (Arrays.asList(typeMCI_No).contains(startPhoneNo))
        {
            return OPERATOR_TYPE_MCI;
        } else if (Arrays.asList(typeMTN_No).contains(startPhoneNo))
        {
            return OPERATOR_TYPE_MTN;
        } else if (Arrays.asList(typeRightel_No).contains(startPhoneNo))
        {
            return OPERATOR_TYPE_RIGHTEL;
        } else
        {
            return OPERATOR_TYPE_MCI;
        }
    }


    @Override
    public void onFinishedIrancellBuy(MobileChargeResponse response, String mobileChargeNo)
    {
        etPassCharge.setText("");
        btnBuyCharge.revertAnimation(this);
        btnBuyCharge.setClickable(true);
  /*      if (response.getServiceMessage().getCode() == 200)
        {
            initDefaultOperatorView();
            ResultBuyCharge charge = new ResultBuyCharge(getActivity(), response.getCreateDate(),
                    response.getTrnBizKey() + "", cardNumber, cardName, etChargeAmount.getText().toString()
//                    , false, archiveCardDBModels.getCardImage(), archiveCardDBModels.getCardNumberColor(),
                    , false, "", "",
                    mobileChargeNo,
//                    tvChargeTitle.getText().toString(),
                    getString(R.string.buyChargeIrancellTitle),
                    response.getRefNo());
            charge.show(getActivity().getSupportFragmentManager(), "chargeResult");
        } else
        {
            mainView.showError(response.getServiceMessage().getMessage());
            hideSoftKeyboard(etPassCharge);
        }*/
    }

    @Override
    public void onErrorIrancellBuy(String error)
    {
        initDefaultOperatorView();
        etPassCharge.setText("");

        btnBuyCharge.revertAnimation(this);
        btnBuyCharge.setClickable(true);
        mainView.showError(error);
        hideSoftKeyboard(etPassCharge);


    }

//    @Override
//    public void cardModel(ArchiveCardDBModel archiveCardDBModels)
//    {
//
//
//        try
//        {
//            this.cardNumber = archiveCardDBModels.getNumber();
//            this.cardName = archiveCardDBModels.getfullName();
//            this.archiveCardDBModels = archiveCardDBModels;
//            cardNumberCheck = archiveCardDBModels.getNumber().substring(0, 6);
//
//            if (rootView != null)
//                if (!cardNumberCheck.equals(TrapConfig.HappyBaseCardNo))
//                {
//
//                    llCvv2.setVisibility(View.VISIBLE);
//                    YoYo.with(Techniques.SlideInLeft)
//                            .duration(200)
//                            .playOn(llCvv2);
//
//
//                } else
//                {
//
//                    YoYo.with(Techniques.SlideOutLeft).withListener(new AnimatorListenerAdapter()
//                    {
//                        @Override
//                        public void onAnimationEnd(Animator animation)
//                        {
//                            super.onAnimationEnd(animation);
//                            llCvv2.setVisibility(View.GONE);
//
//                        }
//                    })
//                            .duration(200)
//                            .playOn(llCvv2);
//
//                }
//        } catch (Exception e)
//        {
//
//            mainView.showError(e.getMessage());
//
//
//        }
//
//    }
//
//
//    @Override
//    public void webUrl(String url)
//    {
//    }
//
//    @Override
//    public void barcode(String barcode)
//    {
//    }
//
//    @Override
//    public void setAmount(String amount)
//    {
//        etMCIAmount.setText(amount);
//        etChargeAmountRightel.setText(amount);
//    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        switch (compoundButton.getId())
        {
            case R.id.rbSpecialChargeIrancell:
                if (rbSpecialChargeIrancell.isChecked())
                    rbNormalChargeIrancell.setChecked(false);
                profileType = 1;
                chargeName = "شارژ شگفت انگیز";
                chargeType = "1";

                break;
            case R.id.rbNormalChargeIrancell:
                if (rbNormalChargeIrancell.isChecked())
                    rbSpecialChargeIrancell.setChecked(false);
                profileType = 0;
                chargeName = "شارژ عادی";

                break;
            case R.id.rbYoungMCN:
                if (rbYoungMCN.isChecked())
                {
                    rbDirectMCN.setChecked(false);
                    rbWomenMCN.setChecked(false);
                }
                chargeType = "0";
                chargeName = "شارژ عادی";
                break;
            case R.id.rbDirectMCN:
                if (rbDirectMCN.isChecked())
                {
                    rbYoungMCN.setChecked(false);
                    rbWomenMCN.setChecked(false);
                }
                //  chargeType = "DIRECT";
                chargeType = "0";
                chargeName = "شارژ مستقیم";

                break;
            case R.id.rbWomenMCN:
                if (rbWomenMCN.isChecked())
                {
                    rbDirectMCN.setChecked(false);
                    rbYoungMCN.setChecked(false);
                }
                chargeType = "1";
                chargeName = "شارژ شگفت انگیز";

                break;

            case R.id.rbNormalChargeRightel:
                if (rbNormalChargeRightel.isChecked())
                    rbSpecialChargeRightel.setChecked(false);
                chargeType = "0";
                chargeName = "شارژ عادی";

                break;
            case R.id.rbSpecialChargeRightel:
                if (rbSpecialChargeRightel.isChecked())
                    rbNormalChargeRightel.setChecked(false);
                chargeType = "1";
                chargeName = "شارژ شگفت انگیز";

                break;
        }

    }

    @Override
    public void onAnimationEnd()
    {
  /*      btnBuyCharge.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background40));
        btnIrancellRecent.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background40));
        btnMciRecent.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background40));
        btnRightelRecent.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background40));*/


    }

    @Override
    public void onFocusChange(View view, boolean b)
    {
        if (b)
        {
//            mainView.showPricePanel();
            // hideSoftKeyboard(etMCIAmount);
            //  hideSoftKeyboard(etChargeAmountRightel);

        }
      /*  if (etMCINumber.isFocused()){
            etMobileChargeRightel.setText(etMCINumber.getText().toString());
            etMobileCharge.setText(etMCINumber.getText().toString());
        }*/


    }

    protected void hideSoftKeyboard(EditText input)
    {
        //  input.setInputType(0);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    @Override
    public void onFinishedMciBuyIn(MobileChargeResponse mciBuyResponse, String mobileChargeNo)
    {
        etPassCharge.setText("");

        btnBuyCharge.revertAnimation(this);
        btnBuyCharge.setClickable(true);
   /*     if (mciBuyResponse.getServiceMessage().getCode() == 200)
        {
            initDefaultOperatorView();
            ResultBuyCharge charge = new ResultBuyCharge(getActivity(), mciBuyResponse.getCreateDate(), mciBuyResponse.getTrnBizKey(),
                    cardNumber, cardName, etMCIAmount.getText().toString(), false,
//                    archiveCardDBModels.getCardImage(), archiveCardDBModels.getCardNumberColor(),
                    "", "",
                    mobileChargeNo,
//                    tvChargeTitle.getText().toString() + " ( " + chargeName + " ) ",mciBuyResponse.getRefNo());
                    getString(R.string.buyChargeMCITitle) + " ( " + chargeName + " ) ", mciBuyResponse.getRefNo());
            charge.show(getActivity().getSupportFragmentManager(), "chargeResult");
        } else
        {

            mainView.showError(mciBuyResponse.getServiceMessage().getMessage());
            hideSoftKeyboard(etPassCharge);


        }*/
    }

    @Override
    public void onErrorMciBuyIn(String error)
    {
        initDefaultOperatorView();
        etPassCharge.setText("");

        btnBuyCharge.revertAnimation(this);
        btnBuyCharge.setClickable(true);
        mainView.showError(error);
        hideSoftKeyboard(etPassCharge);
    }

    @Override
    public void onFinishedRightelBuy(MobileChargeResponse response, String mobileChargeNo)
    {
        etPassCharge.setText("");

        btnBuyCharge.revertAnimation(this);
        btnBuyCharge.setClickable(true);
    /*    if (response.getServiceMessage().getCode() == 200)
        {
            initDefaultOperatorView();
            ResultBuyCharge charge = new ResultBuyCharge(getActivity(), response.getCreateDate(),
                    response.getTrnBizKey(), cardNumber, cardName, etChargeAmountRightel.getText().toString(),
//                    false, archiveCardDBModels.getCardImage(), archiveCardDBModels.getCardNumberColor(),
                    false, "", "",
                    mobileChargeNo,
                    getString(R.string.buyChargeRightelTitle) + " ( " + chargeName + " ) ", response.getRefNo());
            charge.show(getActivity().getSupportFragmentManager(), "chargeResult");
        } else
        {
            mainView.showError(response.getServiceMessage().getMessage());
            hideSoftKeyboard(etPassCharge);

        }*/

    }

    @Override
    public void onErrorRightelBuy(String error)
    {
        initDefaultOperatorView();
        etPassCharge.setText("");

        btnBuyCharge.revertAnimation(this);
        btnBuyCharge.setClickable(true);
        mainView.showError(error);
        hideSoftKeyboard(etPassCharge);


    }


    @Override
    public void onStart()
    {
        super.onStart();
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {


    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
        Logger.d("onTextChanged", charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable)
    {

    }

    @Override
    public void onDestroyView()
    {
        //  initDefaultOperatorView();
        super.onDestroyView();
    }

    @Override
    public void showPaymentParentLoading()
    {
        mainView.showLoading();
    }

    @Override
    public void hidePaymentParentLoading()
    {
        mainView.hideLoading();
    }

    @Override
    public void onPaymentCancelAndBack()
    {
        rootView.findViewById(R.id.container).setVisibility(View.GONE);
//      transaction.detach(pFragment);
        contentView.setVisibility(View.VISIBLE);
        setBtnBackToCharge();
    }

    @Override
    public void onBill(String title,Integer idBillType,String barcode)
    {

    }

    @Override
    public void onChargeSimCard(Integer backState)
    {

    }

    @Override
    public void onBillMotor(Integer status)
    {

    }

    @Override
    public void onBillCar(Integer status)
    {

    }

    @Override
    public void onBillToll(Integer status)
    {

    }

    @Override
    public void onBillTrafic(Integer status)
    {

    }

    @Override
    public void onPackSimCard(Integer status)
    {

    }

    @Override
    public void openBarcode(BarcodeType bill)
    {

    }

    @Override
    public void onBarcodReader(BarcodeType barcodeType)
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

        mainView.hideLoading();
        String urlPayment = data.getUrl();
        

        StringBuilder builder = new StringBuilder();
        builder.append("با انجام این پرداخت ، مبلغ ");
        builder.append(amounAfterTax);
        builder.append(" ریال با احتساب 9 درصد مالیات بر ارزش افزوده، بابت \"خرید شارژ");


        if (getOperatorType(mobile)==1){
            builder.append(" ایرانسل");

        }else if (operatorType==2){
            builder.append(" همراه اول");

        }else{
            builder.append(" رایتل");

        }
        builder.append("\"");
        builder.append("برای شماره ");
        builder.append(mobile);
        builder.append("، ");
        builder.append("از کیف پول شما کسر خواهد شد.");


        //fragmentManager = getChildFragmentManager();
        operatorType = getOperatorType(mobile);

        SimChargePaymentInstance paymentInstance = new SimChargePaymentInstance();
        paymentInstance.setPAYMENT_STATUS(TrapConfig.PAYMENT_STAUS_ChargeSimCard);
        paymentInstance.setOperatorType(operatorType);
        paymentInstance.setSimcardType(simcardType);
        paymentInstance.setTypeCharge(Integer.valueOf(chargeType));


        mainView.openChargePaymentFragment(this, urlPayment, imageDrawable,
                builder.toString(), amounAfterTax, paymentInstance, mobile, TrapConfig.PAYMENT_STAUS_ChargeSimCard);

    }

    @Override
    public void onErrorCharge(String message)
    {

        mainView.hideLoading();
        showError(message);
    }

    @Override
    public void onPaymentPackSimCard(PackageBuyResponse response, String mobile)
    {

    }

    @Override
    public void onErrorPackSimcard(String message)
    {

    }

    @Override
    public void onPaymentTransferMoney()
    {

    }

    @Override
    public void onShowPaymentWithoutCardFragment(@Nullable QrModel model)
    {

    }

    @Override
    public void onPaymentWithoutCard(OnClickContinueSelectPayment onClickContinueSelectPayment, String urlPayment, int imageDrawable, String title, String amount, SimChargePaymentInstance paymentInstance, String mobile, int PAYMENT_STATUS)
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
    public void doTransferMoney()
    {

    }

    @Override
    public void onContact()
    {

    }

    @Override
    public void onInternetAlert()
    {

    }

    @Override
    public void showError(String message)
    {

    }

    @Override
    public void backToMainFragment()
    {

    }

    @Override
    public void openDrawer()
    {

    }

    @Override
    public void closeDrawer()
    {

    }

    @Override
    public void startAddCardActivity()
    {
        mainView.startAddCardActivity();
    }

    @Override
    public void onBuyTicketClick(MatchItem matchBuyable)
    {

    }

    @Override
    public void onLeageClick(ArrayList<MatchItem> matchBuyable)
    {

    }

    @Override
    public void onPredict(PredictPosition position, Integer matchId, Boolean isPredictable, Boolean isFormationPredict)
    {

    }

    @Override
    public void onBackToPredict(PredictPosition position, Integer matchId, Boolean isPredictable, Boolean isFormationPredict)
    {

    }


    @Override
    public void onPredictLeagueTable(Integer teamId, Integer matchId, Boolean isPredictable)
    {

    }

    @Override
    public void onCash()
    {

    }

    @Override
    public void onFootBallServiceOne()
    {

    }

    @Override
    public void onFootBallServiceTwo()
    {

    }

    @Override
    public void onFootBallServiceThree()
    {

    }

    @Override
    public void onFootBallServiceFour()
    {

    }

    @Override
    public void onFootBallServiceFive()
    {

    }

    @Override
    public void onFootBallServiceSix()
    {

    }

    @Override
    public void onFootBallServiceLottery()
    {

    }

    @Override
    public void onFootBallServiceCharity()
    {

    }

    @Override
    public void onLotteryPrimaryResultDetails(int id)
    {

    }

    @Override
    public void onLotteryPrimaryHistoryWinnerList(int id)
    {

    }

    @Override
    public void onNewsArchiveClick(SubMediaParent parent, MediaPosition mediaPosition)
    {

    }

    @Override
    public void onNewsFavoriteClick(SubMediaParent parent, MediaPosition mediaPosition)
    {

    }

    @Override
    public void onPhotosArchiveClick(SubMediaParent parent, MediaPosition mediaPosition)
    {

    }

    @Override
    public void onPhotosFavoriteClick(SubMediaParent parent, MediaPosition mediaPosition)
    {

    }

    @Override
    public void onVideosArchiveClick(SubMediaParent parent, MediaPosition mediaPosition)
    {

    }

    @Override
    public void onVideosFavoriteClick(SubMediaParent parent, MediaPosition mediaPosition)
    {

    }

    @Override
    public void onMainVideoClick()
    {

    }

    @Override
    public void onMainQuestionClick(Integer idQuest)
    {

    }


    @Override
    public void openPastResultFragment(LeagueTableParent parent, String matchId, Boolean isPredictable, String teamId, String imageLogo, String logoTitle)
    {

    }

    @Override
    public void openChargePaymentFragment(OnClickContinueSelectPayment onClickContinueSelectPayment, String urlPayment, int imageDrawable, String title, String priceFormat, SimChargePaymentInstance paymentInstance, String mobile, int PAYMENT_STATUS)
    {

    }


    @Override
    public void openWebView(MainActionView mainView, String uRl, String gds_token, String title)
    {

    }

    @Override
    public void openIncreaseWalletPaymentFragment(OnClickContinueSelectPayment onClickContinueSelectPayment, String urlPayment, int imageDrawable, String title, String amount, SimChargePaymentInstance paymentInstance, String mobile, int PAYMENT_STATUS)
    {

    }

    @Override
    public void openPackPaymentFragment(OnClickContinueSelectPayment onClickContinueSelectPayment, String urlPayment, int imageDrawable, String title, String amount, SimPackPaymentInstance paymentInstance, String mobile, int PAYMENT_STATUS)
    {

    }


    @Override
    public void getBuyEnable(BuyTicketAction buyTicketAction)
    {

    }

    @Override
    public void onSetPredictCompleted(Integer matchIdt, Boolean isPredictable, Boolean isFormationPredict, String message)
    {

    }

    @Override
    public void onBackToChargFragment(int payment_status, int PAYMENT_STATUS, int idBill, ArrayList<PersonEvent> personEvents)
    {

    }




    @Override
    public void backToAllServicePackage(Integer backState,Integer idMenuClicked)
    {

    }

    @Override
    public void onBackToHomeWallet(int i)
    {

    }

    @Override
    public void onBackToMatch()
    {

    }

    @Override
    public void onChangeMediaPosition(MediaPosition mediaPosition)
    {

    }

    @Override
    public void onShowDetailWinnerList(List<Winner> winnerList)
    {

    }

    @Override
    public void onShowLast5PastMatch(Integer teamLiveScoreId)
    {

    }

    @Override
    public void onIntroduceTeam()
    {

    }

    @Override
    public void onCompationTeam()
    {

    }

    @Override
    public void onHeadCoach(Integer coachId, String title, boolean flagFavorite)
    {

    }



    @Override
    public void onMediaPlayersFragment()
    {

    }

    @Override
    public void openBillPaymentFragment(String url, String textBillPayment, String number, Integer idSelectedBillType, String amount,int PAYMENT_STATUS_BILL)
    {

    }

    @Override
    public void onPerformanceEvaluation(Integer matchId, MatchItem matchItem)
    {

    }

    @Override
    public void onSetPlayerPerformanceEvaluation(Integer matchId, Integer playerId, String name, String imageURL)
    {

    }

    @Override
    public void onPlayerPerformanceEvaluationResult(Integer matchId, Integer playerId, String name, String imageURL)
    {

    }

    @Override
    public void openEventPaymentFragment(String url, String textEventPayment, Integer count, ArrayList<PersonEvent> personEvents, String amount, int PAYMENT_STATUS_BILL)
    {

    }

    @Override
    public void onCardManagement()
    {

    }


    private void closeAutoComplete()
    {
        autoCompletePhoneNumberRightel.dismissDropDown();
        autoCompletePhoneNumberMci.dismissDropDown();
        autoCompletePhoneNumberIrancel.dismissDropDown();
    }

    public void onSelectContact(OnSelectContact event)
    {
        try
        {
            autoCompletePhoneNumberIrancel.setText(event.getNumber().replaceAll(" ", ""));
            autoCompletePhoneNumberMci.setText(event.getNumber().replaceAll(" ", ""));
            autoCompletePhoneNumberRightel.setText(event.getNumber().replaceAll(" ", ""));
            closeAutoComplete();


        } catch (Exception e)
        {
        }

    }

    @Override
    public void showLoading()
    {

    }

    @Override
    public void hideLoading()
    {

    }

    @Override
    public void onBackClicked()
    {

    }

    /*  @Override
      public void onContactClicked(String number, String name)
      {
          try
          {
              if (isMtn)
              {
                  etMobileCharge.setText(number.replaceAll(" ", ""));
                  tilMIrancell.setHint(name);


                  return;
              }
              if (isMci)
              {
                  etMCINumber.setText(number.replaceAll(" ", ""));
                  tilMMci.setHint(name);


                  return;


              }
              if (isRightel)
              {
                  etMobileChargeRightel.setText(number.replaceAll(" ", ""));
                  tilMRightel.setHint(name);


              }

          } catch (Exception e)
          {
          }
      }*/
    public void getAvailableAmount()
    {
        SingletonService.getInstance().getMobileCharge().getAvailableAmount(new OnServiceStatus<WebServiceClass<AvailableAmounResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<AvailableAmounResponse> response)
            {

                try
                {


                    for (Result data : response.data.getResults())
                    {
                        if (data.getOperatorType() == 1)
                        {
                            mtnResultList.add(data);
                            mtnResultListFilter.add(data);

                        }
                        if (data.getOperatorType() == 2)
                        {
                            mciResultList.add(data);
                            mciResultListFilter.add(data);


                        }
                        if (data.getOperatorType() == 3)
                        {
                            rightelResultList.add(data);
                            rightelResultListFilter.add(data);

                        }


                    }

                    rvMciAmount.setAdapter(new ChargeAdapter(mciResultList, getActivity(), ChargeFragment.this));
                    // rvIrancellAmount.setAdapter(new ChargeAdapter(mtnResultList,getActivity(),ChargeFragment.this));
                    //rvRightelAmount.setAdapter(new ChargeAdapter(rightelResultList, getActivity(), ChargeFragment.this));
                    irancellFilter(0, 0);
                    rightelFilter(0, 0);


                } catch (Exception e)
                {
                }


            }

            @Override
            public void onError(String message)
            {
                if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                {
                    showToast(getActivity(), "خطای دسترسی به سرور!", 0);
                } else
                {
                    showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                }


            }
        });
    }

    @Override
    public void onClickChargeAmount(Result result)
    {
        if (result.getOperatorType() == 1)
        {
            if (TextUtils.isEmpty(autoCompletePhoneNumberIrancel.getText().toString()))
            {
                mainView.showError("لطفا شماره تلفن همراه را وارد نمایید.");
                return;
            }
            try
            {
                if (!Utility.getMobileValidation(autoCompletePhoneNumberIrancel.getText().toString()))
                {
                    mainView.showError("لطفا شماره تلفن همراه را صحیح وارد نمایید.");
                    return;
                }
            } catch (Exception e)
            {
                mainView.showError("لطفا شماره تلفن همراه را صحیح وارد نمایید.");
                return;

            }
            if (Utility.checkTaliyaValidation(autoCompletePhoneNumberIrancel.getText().toString()))
            {

                mainView.showError("شماره تلفن وارد شده تالیا می باشد،امکان خرید شارژ برای این شماره وجود ندارد.");
                return;

            }
            amount = result.getAmount().toString();
            amounAfterTax = result.getAmount_with_tax().toString();

            isMtn = true;
            isMci = false;
            isRightel = false;

            setDataLayoutPassCharge();
        }
        if (result.getOperatorType() == 2)
        {
            if (TextUtils.isEmpty(autoCompletePhoneNumberMci.getText().toString()))
            {
                mainView.showError("لطفا شماره تلفن همراه را وارد نمایید.");
                return;
            }
            try
            {
                if (!Utility.getMobileValidation(autoCompletePhoneNumberMci.getText().toString()))
                {
                    mainView.showError("لطفا شماره تلفن همراه را صحیح وارد نمایید.");

                    return;
                }
            } catch (Exception e)
            {
                mainView.showError("لطفا شماره تلفن همراه را صحیح وارد نمایید.");

                return;
            }
            if (Utility.checkTaliyaValidation(autoCompletePhoneNumberMci.getText().toString()))
            {

                mainView.showError("شماره تلفن وارد شده تالیا می باشد،امکان خرید شارژ برای این شماره وجود ندارد.");
                return;

            }
            amount = result.getAmount().toString();
            amounAfterTax = result.getAmount_with_tax().toString();

            etPassCharge.requestFocus();

            isMci = true;
            isMtn = false;
            isRightel = false;

            setDataLayoutPassCharge();


        }
        if (result.getOperatorType()==3){

            if (TextUtils.isEmpty(autoCompletePhoneNumberRightel.getText().toString()))
            {
                mainView.showError("لطفا شماره تلفن همراه را وارد نمایید.");
                return;
            }

            try
            {
                if (!Utility.getMobileValidation(autoCompletePhoneNumberRightel.getText().toString()))
                {
                    mainView.showError("لطفا شماره تلفن همراه را صحیح وارد نمایید.");
                    return;
                }
            } catch (Exception e)
            {
                mainView.showError("لطفا شماره تلفن همراه را صحیح وارد نمایید.");

                return;

            }
            if (Utility.checkTaliyaValidation(autoCompletePhoneNumberRightel.getText().toString()))
            {

                mainView.showError("شماره تلفن وارد شده تالیا می باشد،امکان خرید شارژ برای این شماره وجود ندارد.");
                return;

            }


       /* if (!cardNumberCheck.equals(TrapConfig.HappyBaseCardNo) && Integer.valueOf(etChargeAmountRightel.getText().toString().replaceAll(",", "")) < 1000)
        {
            mainView.showError("حداقل مبلغ در این قسمت 1000 ریال می باشد.");
            return;
        }
        if (cardNumberCheck.equals(TrapConfig.HappyBaseCardNo) && Integer.valueOf(etChargeAmountRightel.getText().toString().replaceAll(",", "")) == 0)
        {
            mainView.showError("حداقل مبلغ در این قسمت 1 ریال می باشد.");
            return;
        }*/
            isMtn = false;
            isMci = false;
            isRightel = true;
            //mainView.needExpanded(true);
            llRightelCharge.setVisibility(View.GONE);
            amount = result.getAmount().toString();
            amounAfterTax = result.getAmount_with_tax().toString();

            setDataLayoutPassCharge();




        }


    }

    public void irancellFilter(Integer simcardType, Integer chargeType)
    {
        Observable
                .fromIterable(mtnResultListFilter)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .filter(x ->
                        {
                            return x.getSimCardType().equals(simcardType) && x.getChargeType().equals(chargeType);


                        }

                )
                .toList()
                .subscribe(new SingleObserver<List<Result>>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                    }

                    @Override
                    public void onSuccess(List<Result> results)
                    {
                        rvIrancellAmount.removeAllViews();

                        rvIrancellAmount.setAdapter(new ChargeAdapter(results, getActivity(), ChargeFragment.this));


                    }

                    @Override
                    public void onError(Throwable e)
                    {
                    }
                });
    }
    public void rightelFilter(Integer simcardType, Integer chargeType)
    {
        Observable
                .fromIterable(rightelResultList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .filter(x ->
                        {
                            return x.getSimCardType().equals(simcardType) && x.getChargeType().equals(chargeType);


                        }

                )
                .toList()
                .subscribe(new SingleObserver<List<Result>>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                    }

                    @Override
                    public void onSuccess(List<Result> results)
                    {
                        rvRightelAmount.removeAllViews();

                        rvRightelAmount.setAdapter(new ChargeAdapter(results, getActivity(), ChargeFragment.this));


                    }

                    @Override
                    public void onError(Throwable e)
                    {
                    }
                });
    }

}
