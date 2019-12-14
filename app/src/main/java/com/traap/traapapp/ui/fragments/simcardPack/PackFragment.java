package com.traap.traapapp.ui.fragments.simcardPack;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputLayout;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyPackage.response.PackageBuyResponse;
import com.traap.traapapp.apiServices.model.contact.OnSelectContact;
import com.traap.traapapp.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;
import com.traap.traapapp.apiServices.model.getPackageMci.response.GetPackageMciResponse;
import com.traap.traapapp.apiServices.model.getRightelPack.response.Detail;
import com.traap.traapapp.apiServices.model.getRightelPack.response.GetRightelPackRespone;
import com.traap.traapapp.apiServices.model.mobileCharge.response.MobileChargeResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.pack.RightelPackModel;
import com.traap.traapapp.models.otherModels.paymentInstance.SimPackPaymentInstance;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.userProfile.UserProfileActivity;
import com.traap.traapapp.ui.adapters.pack.DetailPackAdapter;
import com.traap.traapapp.ui.adapters.pack.TitlePackAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.payment.PaymentActionView;
import com.traap.traapapp.ui.fragments.payment.PaymentFragment;
import com.traap.traapapp.ui.fragments.payment.PaymentParentActionView;
import com.traap.traapapp.ui.fragments.simcardPack.imp.BuyPackageImpl;
import com.traap.traapapp.ui.fragments.simcardPack.imp.BuyPackageInteractor;
import com.traap.traapapp.ui.fragments.simcardPack.imp.irancell.GetPackageIrancellImpl;
import com.traap.traapapp.ui.fragments.simcardPack.imp.irancell.GetPackageIrancellInteractor;
import com.traap.traapapp.ui.fragments.simcardPack.imp.mci.PackageMciImpl;
import com.traap.traapapp.ui.fragments.simcardPack.imp.mci.PackageMciInteractor;
import com.traap.traapapp.ui.fragments.simcardPack.imp.rightel.RightelPackImpl;
import com.traap.traapapp.ui.fragments.simcardPack.imp.rightel.RightelPackInteractor;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

/**
 * Created by Javad.Abadi on 7/14/2018.
 */
@SuppressLint("ValidFragment")
public class PackFragment
        extends BaseFragment implements
        CompoundButton.OnCheckedChangeListener, OnAnimationEndListener, View.OnFocusChangeListener
        , RightelPackImpl.OnFinishedRightelPackListener,
        PackFragmentInteractor, DetailPackAdapter.GetPackInAdapter,
        PackageMciImpl.OnFinishedPackageMciListener, BuyPackageInteractor.OnFinishedBuyPackageListener, PaymentParentActionView,
        GetPackageIrancellImpl.OnFinishedGetPackageIrancellListener, TextWatcher, AdapterView.OnItemSelectedListener, PaymentActionView
{


    private Fragment pFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private int myOperatorType = 0;

    private static int OPERATOR_TYPE_MCI = 2;
    private static int OPERATOR_TYPE_MTN = 1;
    private static int OPERATOR_TYPE_RIGHTEL = 3;

    private MainActionView mainView;
    private BuyPackageImpl buyPackageImpl;
    private SimPackPaymentInstance paymentInstance;
    private int imageDrawable=0;
    private String title;
    private TextView tvUserName;
    private TextView tvHeaderPopularNo;

    public PackFragment()
    {

    }

    public static PackFragment newInstance(MainActionView mainView)
    {
        PackFragment f = new PackFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    private View v;
    private LinearLayoutManager layoutManager;
    private boolean isMci = true, isMtn = false, isRightel = false, isHappy, isInitView = true;
    private RightelPackImpl rightelPack;
    private PackageMciImpl packageMci;
    private GetPackageIrancellImpl getPackageIrancell;
    private String bundleId, requestId, cardNumber, cardName, amount, packageType, filterType = "", cardNumberCheck, ccv2;
    private int profileType, amountType, operatorType;
    private List<RightelPackModel> irancellPack = new ArrayList<>();
    private TitlePackAdapter packAdapter;
    //    private ArchiveCardDBModel archiveCardDBModels;
    String mobile = "";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvPopularPlayer)
    TextView tvPopularPlayer;

    @BindView(R.id.imgBack)
    View imgBack;

    @BindView(R.id.imgMenu)
    View imgMenu;

    @BindView(R.id.rlShirt)
    View rlShirt;


    //toolbar

/*

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
   // rlShirt = rootView.findViewById(R.id.rlShirt);
            rlShirt.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            startActivity(new Intent(SingletonContext.getInstance().getContext(), UserProfileActivity.class));
        }
    });
            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
{
    @Override
    public void onClick(View v)
    {
        mainView.openDrawer();
    }
});
    tvTitle = rootView.findViewById(R.id.tvTitle);
            tvTitle.setText("سرویس ها");
    imgMenu = rootView.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
    imgBack = rootView.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
    {
        getActivity().onBackPressed();
    });

    tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));
*/

    @BindView(R.id.contentView)
    LinearLayout contentView;

    @BindView(R.id.rightelRecycler)
    RecyclerView rightelRecycler;
    @BindView(R.id.mciRecycler)
    RecyclerView mciRecycler;
    @BindView(R.id.irancellRecycler)
    RecyclerView irancellRecycler;
    @BindView(R.id.tvAmountPackage)
    TextView tvAmountPackage;
    @BindView(R.id.ivContactI)
    ImageView ivContactI;
    @BindView(R.id.ivContactM)
    ImageView ivContactM;
    @BindView(R.id.ivContactR)
    ImageView ivContactR;
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
    @BindView(R.id.btnChargeConfirm)
    CircularProgressButton btnChargeConfirm;
    @BindView(R.id.btnBackToCharge)
    View btnBackToCharge;
    @BindView(R.id.llDescriptionSelectPack)
    LinearLayout llDescriptionSelectPack;
    @BindView(R.id.llDetailDescription)
    LinearLayout llDetailDescription;
    @BindView(R.id.llDetailDescriptionRightel)
    LinearLayout llDetailDescriptionRightel;
    @BindView(R.id.llDetailDescriptionMci)
    LinearLayout llDetailDescriptionMci;
    @BindView(R.id.llDescriptionSelectPackRightel)
    LinearLayout llDescriptionSelectPackRightel;
    @BindView(R.id.btnMCIPackConfirm)
    CircularProgressButton btnMCIPackConfirm;
    @BindView(R.id.btnPackBackIrancell)
    View btnPackBackIrancell;
    @BindView(R.id.nested)
    NestedScrollView nested;
    @BindView(R.id.rbAll)
    RadioButton rbAll;
    @BindView(R.id.rbCredit)
    RadioButton rbCredit;
    @BindView(R.id.rbFirst)
    RadioButton rbFirst;
    @BindView(R.id.rbRightelFirst)
    RadioButton rbRightelFirst;
    @BindView(R.id.rbRightelCredit)
    RadioButton rbRightelCredit;
    @BindView(R.id.rbRightelAll)
    RadioButton rbRightelAll;
    @BindView(R.id.rbMciFirst)
    RadioButton rbMciFirst;
    @BindView(R.id.rbMciCredit)
    RadioButton rbMciCredit;
    @BindView(R.id.rbMciAll)
    RadioButton rbMciAll;
    @BindView(R.id.btnBuyCharge)
    CircularProgressButton btnBuyCharge;
/*
    @BindView(R.id.etChargeAmount)
    ClearableEditText etChargeAmount;
*/

    @BindView(R.id.btnChargeConfirmRightel)
    CircularProgressButton btnChargeConfirmRightel;
    @BindView(R.id.btnChargeBackRightel)
    View btnChargeBackRightel;
    @BindView(R.id.btnPackBackMci)
    View btnPackBackMci;
    @BindView(R.id.btnIrancellRecent)
    View btnIrancellRecent;
    @BindView(R.id.btnMciRecent)
    View btnMciRecent;
    @BindView(R.id.btnRightelRecent)
    View btnRightelRecent;
    @BindView(R.id.etMobileChargeRightel)
    ClearableEditText etMobileChargeRightel;
    /* @BindView(R.id.etChargeAmountRightel)
     ClearableEditText etChargeAmountRightel;
 */
    @BindView(R.id.etMobileCharge)
    ClearableEditText etMobileCharge;
    @BindView(R.id.etMCINumber)
    ClearableEditText etMCINumber;
    @BindView(R.id.tvPackTitle)
    TextView tvPackTitle;
    @BindView(R.id.llPassCharge)
    LinearLayout llPassCharge;
    @BindView(R.id.llChargeBackRightel)
    LinearLayout llChargeBackRightel;
    @BindView(R.id.llSelectOptaror)
    LinearLayout llSelectOptaror;
    @BindView(R.id.llIrancellFilter)
    LinearLayout llIrancellFilter;
    @BindView(R.id.llCvv2)
    LinearLayout llCvv2;
    @BindView(R.id.llMCICharge)
    LinearLayout llMCICharge;
    @BindView(R.id.llPackBackIrancell)
    LinearLayout llPackBackIrancell;
    @BindView(R.id.llMciFilter)
    LinearLayout llMciFilter;
    @BindView(R.id.llMTNCharge)
    LinearLayout llMTNCharge;
    @BindView(R.id.llRightelCharge)
    LinearLayout llRightelCharge;
    @BindView(R.id.llRightelMobile)
    LinearLayout llRightelMobile;
    @BindView(R.id.lMciMobile)
    LinearLayout lMciMobile;
    @BindView(R.id.llPackBackMci)
    LinearLayout llPackBackMci;
    @BindView(R.id.llRightelFilter)
    LinearLayout llRightelFilter;
    @BindView(R.id.llIrancellMobile)
    LinearLayout llIrancellMobile;
    @BindView(R.id.etPassCharge)
    ClearableEditText etPassCharge;
    @BindView(R.id.tlPassCharge)
    TextInputLayout tlPassCharge;
    @BindView(R.id.tilMIrancell)
    TextInputLayout tilMIrancell;
    @BindView(R.id.tilMMci)
    TextInputLayout tilMMci;
    @BindView(R.id.tilMRightel)
    TextInputLayout tilMRightel;
    @BindView(R.id.tipCvv2)
    TextInputLayout tipCvv2;
    @BindView(R.id.etCvv2)
    ClearableEditText etCvv2;
    @BindView(R.id.spinnerMci)
    Spinner spinnerMci;
    @BindView(R.id.spinnerIrancell)
    Spinner spinnerIrancell;
    @BindView(R.id.llMciSpinner)
    LinearLayout llMciSpinner;
    @BindView(R.id.llRightelSpinner)
    LinearLayout llRightelSpinner;
    @BindView(R.id.spinnerRightel)
    Spinner spinnerRightel;
    @BindView(R.id.llIrancellSpinner)
    LinearLayout llIrancellSpinner;
    private String[] mciFilter = {"همه", "اعتباری و دائمی", "اعتباری", "دائمی"};
    private String[] irancellFilter = {"همه", "4G اعتباري", "4G دائمي", "TD_LTE اعتباري", "TD_LTE + 4G اعتباري", "بر اساس اینترنت بین المللی",
            "مناسبتی", "TD_LTE دائمي", "TD_LTE + 4G دائمي"};
    private String[] rightelFilter = {"همه", "اعتباری", "دائمی", "دیتا", "دکا"};

    @OnClick(R.id.ivContactR)
    void ivContactR()
    {
        mainView.onContact();
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
    void irancellRecentP()
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;
        }
     //   transactionsRequest();
    }

    @OnClick(R.id.btnMciRecent)
    void mciRecentP()
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;
        }
       // transactionsRequest();
    }

    @OnClick(R.id.btnRightelRecent)
    void rightelRecentP()
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;

        }
        //transactionsRequest();

    }

    @OnClick(R.id.btnChargeBackRightel)
    void setRightelPack()
    {
        llDescriptionSelectPackRightel.setVisibility(View.GONE);
        llDetailDescriptionRightel.setVisibility(View.VISIBLE);
//        mainView.needExpanded(false);
        tvPackTitle.setText("خرید بسته اینترنت " + "رایتل");
        tvPackTitle.setTextSize(18);

        llRightelMobile.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInRight)
                .duration(200)
                .playOn(llRightelMobile);
        llChargeBackRightel.setVisibility(View.GONE);
        //llRightelFilter.setVisibility(View.GONE);
        llRightelSpinner.setVisibility(View.GONE);
        btnChargeConfirmRightel.setVisibility(View.VISIBLE);
        rightelRecycler.setVisibility(View.GONE);
//        mainView.needExpanded(true);


    }

    @OnClick(R.id.btnPackBackIrancell)
    void setBtnIrancellBack()
    {
        llDescriptionSelectPack.setVisibility(View.GONE);
        llDetailDescription.setVisibility(View.VISIBLE);
//        mainView.needExpanded(false);
        tvPackTitle.setText("خرید بسته ایرانسل " + "رایتل");
        tvPackTitle.setTextSize(18);

        // llIrancellFilter.setVisibility(View.GONE);
        llIrancellMobile.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInRight)
                .duration(200)
                .playOn(llIrancellMobile);

        btnChargeConfirm.setVisibility(View.VISIBLE);
        llPackBackIrancell.setVisibility(View.GONE);
        irancellRecycler.setVisibility(View.GONE);
        llIrancellSpinner.setVisibility(View.GONE);

//        mainView.needExpanded(true);

    }

    @OnClick(R.id.btnPackBackMci)
    void setMciPack()
    {
//        mainView.needExpanded(false);
        llDetailDescriptionMci.setVisibility(View.VISIBLE);
        tvPackTitle.setText("خرید بسته اینترنت " + "همراه اول");
        tvPackTitle.setTextSize(18);

        lMciMobile.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInRight)
                .duration(200)
                .playOn(lMciMobile);
        llPackBackMci.setVisibility(View.GONE);
        llMciFilter.setVisibility(View.GONE);
        btnMCIPackConfirm.setVisibility(View.VISIBLE);
        mciRecycler.setVisibility(View.GONE);
        llMciSpinner.setVisibility(View.GONE);
//        mainView.needExpanded(true);

    }

    @OnClick(R.id.flIrancell)
    void irancell()
    {
//        mainView.needExpanded(false);
        tvPackTitle.setText("خرید بسته اینترنت " + "ایرانسل");
        tvPackTitle.setTextSize(18);

        ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.btnColorSecondary));
        ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        ivIrancell.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.irancell));
        ivHamraheAval.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.hamrahe_aval2));
        ivRightel.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rightel2));


        llMCICharge.setVisibility(View.GONE);
        llPassCharge.setVisibility(View.GONE);
        llRightelCharge.setVisibility(View.GONE);
        llMTNCharge.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInUp)
                .duration(200)
                .playOn(llMTNCharge);
        isMtn = true;
        isMci = false;
        isRightel = false;
        etMCINumber.setText(etMobileCharge.getText());
        etMobileChargeRightel.setText(etMobileCharge.getText());
        btnChargeConfirm.setVisibility(View.VISIBLE);
        llPackBackIrancell.setVisibility(View.GONE);
        irancellRecycler.setVisibility(View.GONE);
        llIrancellSpinner.setVisibility(View.GONE);
    }

    @OnClick(R.id.flHamraheAval)
    void hamraheAval()
    {
//        mainView.needExpanded(false);
        llDetailDescriptionMci.setVisibility(View.VISIBLE);

        tvPackTitle.setText("خرید بسته اینترنت " + "همراه اول");
        tvPackTitle.setTextSize(18);

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


        llPackBackMci.setVisibility(View.GONE);
        btnMCIPackConfirm.setVisibility(View.VISIBLE);
        mciRecycler.setVisibility(View.GONE);
        llMciSpinner.setVisibility(View.GONE);
        YoYo.with(Techniques.SlideInUp)
                .duration(200)
                .playOn(llMCICharge);
        isMtn = false;
        isMci = true;
        isRightel = false;
        etMobileChargeRightel.setText(etMCINumber.getText());
        etMobileCharge.setText(etMCINumber.getText());


    }

    @OnClick(R.id.flRightel)
    void rightel()
    {
        llDescriptionSelectPackRightel.setVisibility(View.GONE);
        llDetailDescriptionRightel.setVisibility(View.VISIBLE);
        llRightelMobile.setVisibility(View.VISIBLE);
//        mainView.needExpanded(false);
        tvPackTitle.setText("خرید بسته اینترنت " + "رایتل");
        tvPackTitle.setTextSize(18);

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
        //   etChargeAmountRightel.setText("");


        llChargeBackRightel.setVisibility(View.GONE);
        //llRightelFilter.setVisibility(View.GONE);
        llRightelSpinner.setVisibility(View.GONE);
        btnChargeConfirmRightel.setVisibility(View.VISIBLE);
        rightelRecycler.setVisibility(View.GONE);

        isMtn = false;
        isMci = false;
        isRightel = true;
        etMCINumber.setText(etMobileChargeRightel.getText());
        etMobileCharge.setText(etMobileChargeRightel.getText());
    }


    @OnClick(R.id.btnBuyCharge)
    void setBtnBuyCharge()
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;

        }


        if (TextUtils.isEmpty(etPassCharge.getText().toString()))
        {
            mainView.showError("لطفا رمز دوم کارت خود را وارد نمایید.");
            return;
        }
        if (!cardNumberCheck.equals("003725"))
            if (TextUtils.isEmpty(etCvv2.getText().toString()))
            {
                Utility.hideSoftKeyboard(btnBuyCharge.getRootView(), getActivity());
                mainView.showError("لطفا cvv2 را وارد نمایید");
                return;
            } else
            {
                ccv2 = etCvv2.getText().toString();
            }

        String operatorTypeStr = "";
        String rightelRequestId = "";


    /*    if (!isHappy) {
            mainView.onShetapPayment();
            return;
        }*/

        btnBuyCharge.startAnimation();
        btnBuyCharge.setClickable(false);
        if (isMtn)
        {
            operatorTypeStr = "1";
            mobile = etMobileCharge.getText().toString();

        }
        if (isMci)
        {
            operatorTypeStr = "2";
            mobile = etMCINumber.getText().toString();

            //  return;

        }
        if (isRightel)
        {
            operatorTypeStr = "3";
            rightelRequestId = requestId;
            mobile = etMobileChargeRightel.getText().toString();

        }


    }

    @OnClick(R.id.btnBackToCharge)
    void setBtnBackToCharge()
    {
        llSelectOptaror.setVisibility(View.VISIBLE);
        tvAmountPackage.setVisibility(View.GONE);
        YoYo.with(Techniques.SlideInLeft)
                .duration(200)
                .playOn(llSelectOptaror);
        if (isMtn)
        {

//            mainView.needExpanded(false);
            llPassCharge.setVisibility(View.GONE);
            llMTNCharge.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInLeft)
                    .duration(200)
                    .playOn(llMTNCharge);
            return;
        }
        if (isMci)
        {
//            mainView.needExpanded(false);
            llPassCharge.setVisibility(View.GONE);
            llMCICharge.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInLeft)
                    .duration(200)
                    .playOn(llMCICharge);
            return;

        }
        if (isRightel)
        {
//            mainView.needExpanded(false);
            tvPackTitle.setText("خرید بسته اینترنت " + "رایتل");
            tvPackTitle.setTextSize(18);

            llPassCharge.setVisibility(View.GONE);
            llRightelCharge.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInLeft)
                    .duration(200)
                    .playOn(llMCICharge);

        }
    }

    @OnClick(R.id.btnChargeConfirm)
    void setBtnChargeConfirm()
    {
        initSpinner();
        setupRecycler();
        if (!Utility.mtnValidation(etMobileCharge.getText().toString()))
        {
            mainView.showError("لطفا شماره تلفن همراه ایرانسل را صحیح وارد نمایید.");
            hideSoftKeyboard(etMobileCharge);
            return;
        }
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;
        }

        btnChargeConfirm.startAnimation();
        btnChargeConfirm.setClickable(false);
        getPackageIrancell.findGetPackageIrancellDataRequest(this, etMobileCharge.getText().toString());
        llDetailDescription.setVisibility(View.GONE);
        llDescriptionSelectPack.setVisibility(View.VISIBLE);
        hideSoftKeyboard(etMobileCharge);

        isMtn = true;
        isMci = false;
        isRightel = false;


    }

    @OnClick(R.id.btnChargeConfirmRightel)
    void setBtnChargeConfirmRightel()
    {

        initSpinner();
        setupRecycler();
        if (!Utility.rightelValidation(etMobileChargeRightel.getText().toString()))
        {
            hideSoftKeyboard(etMobileChargeRightel);

            mainView.showError("لطفا شماره تلفن همراه رایتل را صحیح وارد نمایید.");
            return;
        }
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;

        }
        llDetailDescriptionRightel.setVisibility(View.GONE);
        llDescriptionSelectPackRightel.setVisibility(View.VISIBLE);
        btnChargeConfirmRightel.startAnimation();
        btnChargeConfirmRightel.setClickable(false);
        rightelPack.findRightelPackData(this, etMobileChargeRightel.getText().toString());
        hideSoftKeyboard(etMobileChargeRightel);

        isMtn = false;
        isMci = false;
        isRightel = true;

    }


    /*@OnClick(R.id.etChargeAmountRightel)
    void setEtChargeAmountRightel() {
        mainView.showPricePanel();
        hideSoftKeyboard(etChargeAmountRightel);
    }
*/
    @OnClick(R.id.btnMCIPackConfirm)
    void setBtnMCIPackConfirm()
    {
        initSpinner();
        setupRecycler();
        if (!Utility.mciValidation(etMCINumber.getText().toString()))
        {
            mainView.showError("لطفا شماره تلفن همراه همراه اول را صحیح وارد نمایید.");
            hideSoftKeyboard(etMCINumber);

            return;
        }
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;

        }

        llDetailDescriptionMci.setVisibility(View.GONE);
        btnMCIPackConfirm.startAnimation();
        btnMCIPackConfirm.setClickable(false);
        packageMci.findPackageMciDataRequest(this, etMCINumber.getText().toString());
        hideSoftKeyboard(etMCINumber);

        isMtn = false;
        isMci = true;
        isRightel = false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (v != null)
        {
            initDefaultOperatorView();
            return v;

        }
        v = inflater.inflate(R.layout.fragment_pack, container, false);
        //((TextView) v.findViewById(R.id.tvTitle)).setText("خرید بسته اینترنت");
        rightelPack = new RightelPackImpl();
        packageMci = new PackageMciImpl();
        getPackageIrancell = new GetPackageIrancellImpl();
        buyPackageImpl = new BuyPackageImpl();
        return v;
    }

    protected void setupRecycler()
    {
        layoutManager = new LinearLayoutManager(getActivity());
        rightelRecycler.setLayoutManager(layoutManager);
        mciRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        irancellRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onResume()
    {
        super.onResume();
        try
        {

            if (isInitView)
            {
                isInitView = false;
                initView();
                initDefaultOperatorView();
                etCvv2.setText("");
                etPassCharge.setText("");

            }
            initSpinner();
            setupRecycler();
        } catch (Exception e)
        {
        }

/*
        if (isInitView) {
            isInitView = false;
            initView();
            setupRecycler();


        }*/


    }

    @Override
    public void onStop()
    {
        super.onStop();
        try
        {
            if (etPassCharge != null)
                etPassCharge.setText("");
            if (etCvv2 != null)
                etCvv2.setText("");
//            EventBus.getDefault().unregister(this);

        } catch (Exception e)
        {
        }

    }

    public void initSpinner()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.simple_spinner_item, mciFilter);
        ArrayAdapter<String> adapterIrancell = new ArrayAdapter<String>(getActivity(),
                R.layout.simple_spinner_item, irancellFilter);
        ArrayAdapter<String> adapterRightel = new ArrayAdapter<String>(getActivity(),
                R.layout.simple_spinner_item, rightelFilter);

        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        adapterIrancell.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        adapterRightel.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinnerMci.setAdapter(adapter);
        spinnerIrancell.setAdapter(adapterIrancell);
        spinnerRightel.setAdapter(adapterRightel);
        spinnerMci.setOnItemSelectedListener(this);
        spinnerIrancell.setOnItemSelectedListener(this);
        spinnerRightel.setOnItemSelectedListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        YoYo.with(Techniques.FadeIn)
                .duration(700)
                .playOn(v);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
    {
        if (parent.getId() == R.id.spinnerMci)
        {

            switch (position)
            {
                case 0:
                    filterType = "all";
                    new ChangeData().execute();
                    break;
                case 1:
                    filterType = "2";
                    new ChangeData().execute();
                    break;
                case 2:
                    filterType = "1";
                    new ChangeData().execute();
                    break;
                case 3:
                    filterType = "0";
                    new ChangeData().execute();
                    break;
            }
        }
        if (parent.getId() == R.id.spinnerIrancell)
        {
            switch (position)
            {
                case 0:
                    filterType = "all";
                    new ChangeData().execute();
                    break;
                case 1:
                    filterType = "1";
                    new ChangeData().execute();
                    break;
                case 2:
                    filterType = "2";
                    new ChangeData().execute();
                    break;
                case 3:
                    filterType = "3";
                    new ChangeData().execute();
                    break;
                case 4:
                    filterType = "4";
                    new ChangeData().execute();
                    break;
                case 5:
                    filterType = "5";
                    new ChangeData().execute();
                    break;
                case 6:
                    filterType = "6";
                    new ChangeData().execute();
                    break;
                case 7:
                    filterType = "7";
                    new ChangeData().execute();
                    break;
                case 8:
                    filterType = "8";
                    new ChangeData().execute();
                    break;
            }


        }
        if (parent.getId() == R.id.spinnerRightel)
        {

            switch (position)
            {
                case 0:
                    filterType = "all";
                    new ChangeData().execute();
                    break;
                case 1:
                    filterType = "1";
                    new ChangeData().execute();
                    break;
                case 2:
                    filterType = "2";
                    new ChangeData().execute();
                    break;
                case 3:
                    filterType = "3";
                    new ChangeData().execute();
                    break;
                case 4:
                    filterType = "4";
                    new ChangeData().execute();
                    break;

            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        // TODO Auto-generated method stub
    }


    private void initView()
    {
        mToolbar = v.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> mainView.backToMainFragment());
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("خرید بسته اینترنت");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        tvPackTitle.setTextSize(18);
        btnChargeConfirm.setText("ادامه");
        btnMCIPackConfirm.setText("ادامه");
        btnChargeConfirmRightel.setText("ادامه");

        tlPassCharge.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        tipCvv2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));

        //initDefaultOperatorView();

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(11);
        etMobileCharge.setFilters(filterArray);
        etMCINumber.setFilters(filterArray);
        etMobileChargeRightel.setFilters(filterArray);

        rbAll.setOnCheckedChangeListener(this);
        rbCredit.setOnCheckedChangeListener(this);
        rbFirst.setOnCheckedChangeListener(this);
        rbRightelFirst.setOnCheckedChangeListener(this);
        rbRightelCredit.setOnCheckedChangeListener(this);
        rbRightelAll.setOnCheckedChangeListener(this);
        rbMciAll.setOnCheckedChangeListener(this);
        rbMciCredit.setOnCheckedChangeListener(this);
        rbMciFirst.setOnCheckedChangeListener(this);
/*        rbAll.setChecked(true);
        rbRightelAll.setChecked(true);
        rbMciAll.setChecked(true);*/
        etMobileCharge.addTextChangedListener(this);
        etMCINumber.addTextChangedListener(this);
        etMobileChargeRightel.addTextChangedListener(this);
        if (!cardNumberCheck.equals("003725"))
        {
            llCvv2.setVisibility(View.VISIBLE);
        }
        etMCINumber.setText(Prefs.getString("mobile", ""));
        etMobileCharge.setText(Prefs.getString("mobile", ""));
        etMobileChargeRightel.setText(Prefs.getString("mobile", ""));

//        btnMciRecent.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_calendar));
//        btnIrancellRecent.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_calendar));
//        btnRightelRecent.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_calendar));

    }

    private void initDefaultOperatorView()
    {
        myOperatorType = getOperatorType(Prefs.getString("mobile", ""));
        tvAmountPackage.setVisibility(View.GONE);
        llPassCharge.setVisibility(View.GONE);
        llSelectOptaror.setVisibility(View.VISIBLE);


        switch (myOperatorType)
        {
            case 0:
            {
                tvPackTitle.setText("خرید بسته اینترنت " + "همراه اول");

                llMCICharge.setVisibility(View.VISIBLE);
                llMTNCharge.setVisibility(View.GONE);
                llRightelCharge.setVisibility(View.GONE);

                ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.btnColorSecondary));
                ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

                ivIrancell.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.irancell2));
                ivHamraheAval.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.hamrahe_aval));
                ivRightel.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rightel2));


                llPackBackMci.setVisibility(View.GONE);
                btnMCIPackConfirm.setVisibility(View.VISIBLE);
                mciRecycler.setVisibility(View.GONE);
                llMciSpinner.setVisibility(View.GONE);
                isMtn = false;
                isMci = true;
                isRightel = false;
                break;
            }
            case 1:
            {
                tvPackTitle.setText("خرید بسته اینترنت " + "ایرانسل");

                llMCICharge.setVisibility(View.GONE);
                llMTNCharge.setVisibility(View.VISIBLE);
                llRightelCharge.setVisibility(View.GONE);

                ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.btnColorSecondary));
                ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

                ivIrancell.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.irancell));
                ivHamraheAval.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.hamrahe_aval2));
                ivRightel.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rightel2));

                isMtn = true;
                isMci = false;
                isRightel = false;

                btnChargeConfirm.setVisibility(View.VISIBLE);
                llPackBackIrancell.setVisibility(View.GONE);
                irancellRecycler.setVisibility(View.GONE);
                llIrancellSpinner.setVisibility(View.GONE);

                break;
            }
            case 2:
            {
                tvPackTitle.setText("خرید بسته اینترنت " + "رایتل");

                llMCICharge.setVisibility(View.GONE);
                llMTNCharge.setVisibility(View.GONE);
                llRightelCharge.setVisibility(View.VISIBLE);

                ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.btnColorSecondary));

                ivIrancell.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.irancell));
                ivHamraheAval.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.hamrahe_aval2));
                ivRightel.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rightel));

                isMtn = false;
                isMci = false;
                isRightel = true;
                llChargeBackRightel.setVisibility(View.GONE);
                //llRightelFilter.setVisibility(View.GONE);
                llRightelSpinner.setVisibility(View.GONE);
                btnChargeConfirmRightel.setVisibility(View.VISIBLE);
                rightelRecycler.setVisibility(View.GONE);
                break;
            }
        }

    }

    private int getOperatorType(String phoneNo)
    {
        String startPhoneNo = phoneNo.substring(0, 4);
        Logger.e("-startPhoneNo-", startPhoneNo);


        String[] typeMCI_No = {"0990", "0991", "0910", "0911", "0912", "0913", "0914", "0915", "0916", "0917", "0918", "0919"};
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
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        switch (compoundButton.getId())
        {
            case R.id.rbAll:
                if (rbAll.isChecked())
                {
                    rbCredit.setChecked(false);
                    rbFirst.setChecked(false);
                    filterType = "all";
                    new ChangeData().execute();

                }

                if (packAdapter == null)
                    return;
             /*   irancellPackFilter.clear();
                irancellPackFilter.addAll(irancellPack);
                packAdapter.notifyDataSetChanged();*/
                break;
            case R.id.rbCredit:
                if (rbCredit.isChecked())
                {
                    rbFirst.setChecked(false);
                    rbAll.setChecked(false);
                    filterType = "1";
                    new ChangeData().execute();

                }

                //  filterIrancell("1");


                break;
            case R.id.rbFirst:
                if (rbFirst.isChecked())
                {
                    rbAll.setChecked(false);
                    rbCredit.setChecked(false);
                    filterType = "2";
                    new ChangeData().execute();

                }
                break;

            case R.id.rbRightelAll:
                if (rbRightelAll.isChecked())
                {
                    rbRightelCredit.setChecked(false);
                    rbRightelFirst.setChecked(false);
                    filterType = "all";
                    new ChangeData().execute();
                }
                break;
            case R.id.rbRightelCredit:
                if (rbRightelCredit.isChecked())
                {
                    rbRightelAll.setChecked(false);
                    rbRightelFirst.setChecked(false);
                    filterType = "1";
                    new ChangeData().execute();

                }
                break;
            case R.id.rbRightelFirst:
                if (rbRightelFirst.isChecked())
                {
                    rbRightelCredit.setChecked(false);
                    rbRightelAll.setChecked(false);
                    filterType = "2";
                    new ChangeData().execute();

                }
                break;


            case R.id.rbMciFirst:
                if (rbMciFirst.isChecked())
                {
                    rbMciCredit.setChecked(false);
                    rbMciAll.setChecked(false);
                    filterType = "0";
                    new ChangeData().execute();

                }
                break;

            case R.id.rbMciCredit:
                if (rbMciCredit.isChecked())
                {
                    rbMciAll.setChecked(false);
                    rbMciFirst.setChecked(false);
                    filterType = "1";
                    new ChangeData().execute();

                }
                break;

            case R.id.rbMciAll:
                if (rbMciAll.isChecked())
                {
                    rbMciCredit.setChecked(false);
                    rbMciFirst.setChecked(false);
                    filterType = "2";
                    new ChangeData().execute();

                }
                break;

        }


    }

    @Override
    public void onAnimationEnd()
    {

    }

    @Override
    public void onFocusChange(View view, boolean b)
    {


    }

    protected void hideSoftKeyboard(EditText input)
    {
        //input.setInputType(0);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }


    @Override
    public void onFinishedRightelPack(WebServiceClass<GetRightelPackRespone> packRespone)
    {
        btnChargeConfirmRightel.revertAnimation(this);
        btnChargeConfirmRightel.setClickable(true);

        if (packRespone.info.statusCode == 200)
        {
            //initDefaultOperatorView();

            requestId = packRespone.data.getPackages().getRequestId();
            rightelRecycler.setNestedScrollingEnabled(false);
//            mainView.needExpanded(false);
            llRightelMobile.setVisibility(View.GONE);
            irancellPack.clear();

            if (packRespone.data.getPackages().getDaily() != null && packRespone.data.getPackages().getDaily().size() != 0)
                irancellPack.add(new RightelPackModel("روزانه", packRespone.data.getPackages().getDaily()));

            if (packRespone.data.getPackages().getThreeDays() != null && packRespone.data.getPackages().getThreeDays().size() != 0)
                irancellPack.add(new RightelPackModel("سه روزه", packRespone.data.getPackages().getThreeDays()));

            if (packRespone.data.getPackages().getTenDays() != null && packRespone.data.getPackages().getTenDays().size() != 0)
                irancellPack.add(new RightelPackModel("ده روزه", packRespone.data.getPackages().getTenDays()));

            if (packRespone.data.getPackages().getWeekly() != null && packRespone.data.getPackages().getWeekly().size() != 0)
                irancellPack.add(new RightelPackModel("هفتگی", packRespone.data.getPackages().getWeekly()));

            if (packRespone.data.getPackages().getMonthly() != null && packRespone.data.getPackages().getMonthly().size() != 0)
                irancellPack.add(new RightelPackModel("ماهیانه", packRespone.data.getPackages().getMonthly()));

            if (packRespone.data.getPackages().getThreeMonths() != null && packRespone.data.getPackages().getThreeMonths().size() != 0)
                irancellPack.add(new RightelPackModel("سه ماهه", packRespone.data.getPackages().getThreeMonths()));

            if (packRespone.data.getPackages().getSixMonths() != null && packRespone.data.getPackages().getSixMonths().size() != 0)
                irancellPack.add(new RightelPackModel("شش ماهه", packRespone.data.getPackages().getSixMonths()));

            if (packRespone.data.getPackages().getOneYear() != null && packRespone.data.getPackages().getSixMonths().size() != 0)
                irancellPack.add(new RightelPackModel("یک ساله", packRespone.data.getPackages().getOneYear()));
            if (!TextUtils.isEmpty(packageType) && operatorType == 3)
            {
                boolean isActive = false;
                for (int i = 0; i < irancellPack.size(); i++)
                {
                    for (int j = 0; j < irancellPack.get(i).getDetail().size(); j++)
                    {
                        if (irancellPack.get(i).getDetail().get(j).getProfileId().toString().equals(packageType)
                                && irancellPack.get(i).getDetail().get(j).getAmount().equals(amountType + ""))
                        {
                            isActive = true;
//                            mainView.needExpanded(false);
                            llPassCharge.setVisibility(View.VISIBLE);
                            tvAmountPackage.setVisibility(View.VISIBLE);
                            llSelectOptaror.setVisibility(View.GONE);


                            YoYo.with(Techniques.SlideInRight)
                                    .duration(200)
                                    .playOn(llPassCharge);
                            YoYo.with(Techniques.SlideInRight)
                                    .duration(200)
                                    .playOn(tvAmountPackage);
                            tvPackTitle.setText(irancellPack.get(i).getDetail().get(j).getTitle());
                            // profileType = irancellPack.get(i).getDetail().get(j).getProfileId();
                            //bundleId = irancellPack.get(i).getDetail().get(j).getBundleId();
                            tvPackTitle.setTextSize(14);
                            amount = irancellPack.get(i).getDetail().get(j).getAmount();
                            tvAmountPackage.setText("قیمت: " + Utility.priceFormat(amount) + " ریال");
                            llRightelCharge.setVisibility(View.GONE);


                        }
                    }
                }
                if (!isActive)
                    mainView.showError("وضعیت بسته شما تغییر کرده، لطفا مجددا بسته خود را انتخاب کنید.");
                packageType = "";

            }

            packAdapter = new TitlePackAdapter(irancellPack, this, "");


            rightelRecycler.setAdapter(packAdapter);

            btnChargeConfirmRightel.setVisibility(View.GONE);
            llChargeBackRightel.setVisibility(View.VISIBLE);
            // llRightelFilter.setVisibility(View.VISIBLE);
            llRightelSpinner.setVisibility(View.VISIBLE);
            rightelRecycler.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() ->
            {
                spinnerRightel.performClick();
            }, 500);
        } else
        {
            mainView.showError(packRespone.info.message);


        }


    }

    @Override
    public void onErrorRightelPack(String error)
    {
        // initDefaultOperatorView();

        btnChargeConfirmRightel.revertAnimation(this);
        btnChargeConfirmRightel.setClickable(true);
        mainView.showError(error);

    }


//    @Override
//    public void onFinishedRightelBuyPackage(PackageBuyResponse response)
//    {
//        //initDefaultOperatorView();
//
//        etPassCharge.setText("");
//        etCvv2.setText("");
//        btnBuyCharge.revertAnimation(this);
//        btnBuyCharge.setClickable(true);
//        if (response.getServiceMessage().getCode() == 200)
//        {
//            ResultBuyCharge charge = new ResultBuyCharge(getActivity(), response.getCreateDate(),
//                    response.getTrnBizKey() + "", cardNumber, cardName, Utility.priceFormat(amount), true,
//                    archiveCardDBModels.getCardImage(), archiveCardDBModels.getCardNumberColor(),
//                    etMobileChargeRightel.getText().toString(), tvPackTitle.getText().toString(), response.getRefNo());
//            charge.show(getActivity().getFragmentManager(), "packageResult");
//
//        } else
//        {
//            mainView.onError(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
//
//        }
//
//
//    }
//
//    @Override
//    public void onErrorRightelBuyPackage(String error)
//    {
//        etPassCharge.setText("");
//        etCvv2.setText("");
//        btnBuyCharge.revertAnimation(this);
//        btnBuyCharge.setClickable(true);
//        mainView.onError(error, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//
//    }


    @Override
    public void getPackRightel(Detail o)
    {
        imageDrawable = 0;

//        mainView.needExpanded(true);
       /* llPassCharge.setVisibility(View.VISIBLE);
        tvAmountPackage.setVisibility(View.VISIBLE);
        llSelectOptaror.setVisibility(View.GONE);
        etPassCharge.requestFocus();*/


       /* YoYo.with(Techniques.SlideInRight)
                .duration(200)
                .playOn(llPassCharge);
        YoYo.with(Techniques.SlideInRight)
                .duration(200)
                .playOn(tvAmountPackage);*/
        //tvPackTitle.setText(o.getTitle());
        // bundleId = o.getBundleId();
        // tvPackTitle.setTextSize(14);
        amount = o.getAmount();

        tvAmountPackage.setText("قیمت: " + Utility.priceFormat(o.getAmount()) + " ریال");


        if (isMci)
        {
            imageDrawable = R.drawable.hamrahe_aval;
           // llMCICharge.setVisibility(View.GONE);
            profileType = Integer.valueOf(o.getProfileId());
            mobile = etMCINumber.getText().toString();


        }
        if (isRightel)
        {
            imageDrawable = R.drawable.rightel;
         //   llRightelCharge.setVisibility(View.GONE);
            profileType = Integer.valueOf(o.getProfileId());
            mobile = etMobileChargeRightel.getText().toString();

        }
        if (isMtn)
        {
            imageDrawable = R.drawable.irancell;
          //  llMTNCharge.setVisibility(View.GONE);
            profileType = o.getProfileId();
            mobile = etMobileCharge.getText().toString();


        }

        //----------------------------new for payment fragment-----------------------
        title = "با انجام این پرداخت ، مبلغ " +
                Utility.priceFormat(o.getAmount()) + " ریال بابت بسته " +
                o.getTitle() + " برای شماره " + mobile + "، از حساب شما کسر خواهد شد.";

        operatorType = getOperatorType(mobile);

        paymentInstance = new SimPackPaymentInstance();
        paymentInstance.setPAYMENT_STATUS(TrapConfig.PAYMENT_STAUS_PackSimCard);
        paymentInstance.setOperatorType(operatorType);
        paymentInstance.setProfileId(o.getProfileId());
        paymentInstance.setRequestId(requestId);
        paymentInstance.setTitlePackageType(o.getTitlePackageType());

        getUrlPackagePayment(amount,paymentInstance,mobile);

//        pFragment = PaymentFragment.newInstance(TrapConfig.PAYMENT_STAUS_PackSimCard,this,
//                Utility.priceFormat(o.getAmount()),
//                title,
//                imageDrawable,
//                o.getProfileId(),
//                operatorType,
//                o.getTitlePackageType(),
//                requestId,
//                mobile);

//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);


        //----------------------------new for payment fragment-----------------------


    }

    private void getUrlPackagePayment(String amount, SimPackPaymentInstance paymentInstance, String mobile)
    {
        buyPackageImpl.findBuyPackageDataRequest(this,paymentInstance.getRequestId()
                ,paymentInstance.getOperatorType(),paymentInstance.getTitlePackageType(),paymentInstance.getProfileId(),mobile,amount);
    }


    @Override
    public void onFinishedPackageMciPackage(WebServiceClass<GetPackageMciResponse> packRespone)
    {
        // initDefaultOperatorView();

        btnMCIPackConfirm.revertAnimation(this);
        btnMCIPackConfirm.setClickable(true);
        if (packRespone.info.statusCode == 200)
        {
            //requestId=getPackageMciResponse.getRequestId();
            mciRecycler.setNestedScrollingEnabled(false);
//            mainView.needExpanded(false);
            llPackBackMci.setVisibility(View.GONE);
            llMciFilter.setVisibility(View.GONE);
            llMciSpinner.setVisibility(View.GONE);
          /*  YoYo.with(Techniques.SlideInRight)
                    .duration(200)
                    .playOn(tvSelectMci);*/
            irancellPack.clear();
            if (packRespone.data.getPackages().getNightly() != null && packRespone.data.getPackages().getNightly().size() != 0)
                irancellPack.add(new RightelPackModel("شبانه", packRespone.data.getPackages().getNightly()));
            if (packRespone.data.getPackages().getDaily() != null && packRespone.data.getPackages().getDaily().size() != 0)
                irancellPack.add(new RightelPackModel("روزانه", packRespone.data.getPackages().getDaily()));

            if (packRespone.data.getPackages().getThreeDays() != null && packRespone.data.getPackages().getThreeDays().size() != 0)
                irancellPack.add(new RightelPackModel("سه روزه", packRespone.data.getPackages().getThreeDays()));

            if (packRespone.data.getPackages().getWeekly() != null && packRespone.data.getPackages().getWeekly().size() != 0)
                irancellPack.add(new RightelPackModel("هفتگی", packRespone.data.getPackages().getWeekly()));

            if (packRespone.data.getPackages().getMonthly() != null && packRespone.data.getPackages().getMonthly().size() != 0)
                irancellPack.add(new RightelPackModel("ماهیانه", packRespone.data.getPackages().getMonthly()));

            if (packRespone.data.getPackages().getTwoMonths() != null && packRespone.data.getPackages().getTwoMonths().size() != 0)
                irancellPack.add(new RightelPackModel("دو ماهه", packRespone.data.getPackages().getTwoMonths()));

            if (packRespone.data.getPackages().getThreeMonths() != null && packRespone.data.getPackages().getThreeMonths().size() != 0)
                irancellPack.add(new RightelPackModel("سه ماهه", packRespone.data.getPackages().getThreeMonths()));

            if (packRespone.data.getPackages().getFourMonths() != null && packRespone.data.getPackages().getFourMonths().size() != 0)
                irancellPack.add(new RightelPackModel("چهار ماهه", packRespone.data.getPackages().getFourMonths()));

            if (packRespone.data.getPackages().getSixMonths() != null && packRespone.data.getPackages().getSixMonths().size() != 0)
                irancellPack.add(new RightelPackModel("شش ماهه", packRespone.data.getPackages().getSixMonths()));

            if (packRespone.data.getPackages().getOneYear() != null && packRespone.data.getPackages().getOneYear().size() != 0)
                irancellPack.add(new RightelPackModel("یک ساله", packRespone.data.getPackages().getOneYear()));

            if (packRespone.data.getPackages().getOther() != null && packRespone.data.getPackages().getOther().size() != 0)
                irancellPack.add(new RightelPackModel("موارد دیگر", packRespone.data.getPackages().getOther()));


            if (!TextUtils.isEmpty(packageType) && operatorType == 2)
            {
                boolean isActive = false;
                for (int i = 0; i < irancellPack.size(); i++)
                {
                    for (int j = 0; j < irancellPack.get(i).getDetail().size(); j++)
                    {
                        if (irancellPack.get(i).getDetail().get(j).getProfileId().toString().equals(packageType)
                                && irancellPack.get(i).getDetail().get(j).getAmount().equals(amountType + ""))
                        {
                            isActive = true;
//                            mainView.needExpanded(true);
                            llPassCharge.setVisibility(View.VISIBLE);
                            tvAmountPackage.setVisibility(View.VISIBLE);
                            llSelectOptaror.setVisibility(View.GONE);


                            YoYo.with(Techniques.SlideInRight)
                                    .duration(200)
                                    .playOn(llPassCharge);
                            YoYo.with(Techniques.SlideInRight)
                                    .duration(200)
                                    .playOn(tvAmountPackage);
                            tvPackTitle.setText(irancellPack.get(i).getDetail().get(j).getTitle());
                            // profileType = irancellPack.get(i).getDetail().get(j).getProfileId();
                            //bundleId = irancellPack.get(i).getDetail().get(j).getBundleId();
                            tvPackTitle.setTextSize(14);
                            amount = irancellPack.get(i).getDetail().get(j).getAmount();
                            tvAmountPackage.setText("قیمت: " + Utility.priceFormat(amount) + " ریال");
                            llMCICharge.setVisibility(View.GONE);


                        }
                    }
                }

                if (!isActive)
                    mainView.showError("وضعیت بسته شما تغییر کرده، لطفا مجددا بسته خود را انتخاب کنید.");
                packageType = "";

            }

            packAdapter = new TitlePackAdapter(irancellPack, this, "");
            mciRecycler.setAdapter(packAdapter);
            btnMCIPackConfirm.setVisibility(View.GONE);
            llPackBackMci.setVisibility(View.VISIBLE);
            //   llMciFilter.setVisibility(View.VISIBLE);
            llMciSpinner.setVisibility(View.VISIBLE);
            mciRecycler.setVisibility(View.VISIBLE);
        } else
        {
            mainView.showError(packRespone.info.message);


        }
    }

    @Override
    public void onErrorPackageMciPackage(String error)
    {
        btnMCIPackConfirm.revertAnimation(this);
        btnMCIPackConfirm.setClickable(true);
        mainView.showError(error);


    }

    @Override
    public void onFinishedMciBuyPackagePackage(WebServiceClass<PackageBuyResponse> response)
    {
        // initDefaultOperatorView();

        Toast.makeText(getContext(), "mciii", Toast.LENGTH_SHORT).show();

        btnBuyCharge.revertAnimation(this);
        btnBuyCharge.setClickable(true);

        if (response.info.statusCode == 200)
        {
//            ResultBuyCharge charge = new ResultBuyCharge(getActivity(), response.getCreateDate(),
//                    response.getTrnBizKey() + "", cardNumber, cardName, Utility.priceFormat(amount), true, archiveCardDBModels.getCardImage(),
//                    archiveCardDBModels.getCardNumberColor(), etMobileCharge.getText().toString(), tvPackTitle.getText().toString(), response.getRefNo());
//            charge.show(getActivity().getFragmentManager(), "packageResult");

        } else
        {
            mainView.showError(response.info.message);

        }
    }


    @Override
    public void onErrorMciBuyPackagePackage(String error)
    {
        // initDefaultOperatorView();

        btnBuyCharge.revertAnimation(this);
        btnBuyCharge.setClickable(true);
        mainView.showError(error);
    }

    @Override
    public void onFinishedGetPackageIrancell(WebServiceClass<GetPackageIrancellResponse> packRespone)
    {
        //initDefaultOperatorView();

        btnChargeConfirm.revertAnimation(this);
        btnChargeConfirm.setClickable(true);
        if (packRespone.info.statusCode == 200)
        {
            rbAll.setChecked(true);

            //requestId=getPackageMciResponse.getRequestId();
            irancellRecycler.setNestedScrollingEnabled(false);
//            mainView.needExpanded(false);
            llPackBackIrancell.setVisibility(View.GONE);
            //  llIrancellFilter.setVisibility(View.VISIBLE);
        /*    YoYo.with(Techniques.SlideInRight)
                    .duration(200)
                    .playOn(tvSelectIrancell);*/
         /*   YoYo.with(Techniques.SlideInRight)
                    .duration(200)
                    .playOn(llIrancellFilter);*/

            irancellPack.clear();
            if (packRespone.data.getIrancellPackage().getHourly() != null && packRespone.data.getIrancellPackage().getHourly().size() != 0)
                irancellPack.add(new RightelPackModel("ساعتی", packRespone.data.getIrancellPackage().getHourly()));

            if (packRespone.data.getIrancellPackage().getDaily() != null && packRespone.data.getIrancellPackage().getDaily().size() != 0)
                irancellPack.add(new RightelPackModel("روزانه", packRespone.data.getIrancellPackage().getDaily()));


            if (packRespone.data.getIrancellPackage().getThreeDays() != null && packRespone.data.getIrancellPackage().getThreeDays().size() != 0)
                irancellPack.add(new RightelPackModel("سه روزه", packRespone.data.getIrancellPackage().getThreeDays()));

            if (packRespone.data.getIrancellPackage().getWeekly() != null && packRespone.data.getIrancellPackage().getWeekly().size() != 0)
                irancellPack.add(new RightelPackModel("هفتگی", packRespone.data.getIrancellPackage().getWeekly()));


            if (packRespone.data.getIrancellPackage().getFifteenDays() != null && packRespone.data.getIrancellPackage().getFifteenDays().size() != 0)
                irancellPack.add(new RightelPackModel("پانزده روزه", packRespone.data.getIrancellPackage().getFifteenDays()));


            if (packRespone.data.getIrancellPackage().getMonthly() != null && packRespone.data.getIrancellPackage().getMonthly().size() != 0)
                irancellPack.add(new RightelPackModel("ماهیانه", packRespone.data.getIrancellPackage().getMonthly()));

            if (packRespone.data.getIrancellPackage().getThreeMonths() != null && packRespone.data.getIrancellPackage().getThreeMonths().size() != 0)
                irancellPack.add(new RightelPackModel("سه ماهه", packRespone.data.getIrancellPackage().getThreeMonths()));

            if (packRespone.data.getIrancellPackage().getSixMonths() != null && packRespone.data.getIrancellPackage().getSixMonths().size() != 0)
                irancellPack.add(new RightelPackModel("شش ماهه", packRespone.data.getIrancellPackage().getSixMonths()));

            if (packRespone.data.getIrancellPackage().getOneYear() != null && packRespone.data.getIrancellPackage().getOneYear().size() != 0)
                irancellPack.add(new RightelPackModel("یک ساله", packRespone.data.getIrancellPackage().getOneYear()));

            if (!TextUtils.isEmpty(packageType) && operatorType == 1)
            {
                boolean isActive = false;
                for (int i = 0; i < irancellPack.size(); i++)
                {
                    for (int j = 0; j < irancellPack.get(i).getDetail().size(); j++)
                    {
                        if (irancellPack.get(i).getDetail().get(j).getProfileId().equals(Integer.valueOf(packageType))
                                && irancellPack.get(i).getDetail().get(j).getAmount().equals(amountType + ""))
                        {
                            isActive = true;
//                            mainView.needExpanded(false);
                            llPassCharge.setVisibility(View.VISIBLE);
                            tvAmountPackage.setVisibility(View.VISIBLE);
                            llSelectOptaror.setVisibility(View.GONE);


                            YoYo.with(Techniques.SlideInRight)
                                    .duration(200)
                                    .playOn(llPassCharge);
                            YoYo.with(Techniques.SlideInRight)
                                    .duration(200)
                                    .playOn(tvAmountPackage);
                            tvPackTitle.setText(irancellPack.get(i).getDetail().get(j).getTitle());
                            profileType = irancellPack.get(i).getDetail().get(j).getProfileId();
                            //  bundleId =irancellPack.get(i).getDetail().get(j).getBundleId();
                            tvPackTitle.setTextSize(14);
                            amount = irancellPack.get(i).getDetail().get(j).getAmount();
                            tvAmountPackage.setText("قیمت: " + Utility.priceFormat(amount) + " ریال");
                            llMTNCharge.setVisibility(View.GONE);


                        }
                    }


                }
                if (!isActive)
                    mainView.showError("وضعیت بسته شما تغییر کرده، لطفا مجددا بسته خود را انتخاب کنید.");
                packageType = "";

            }


            packAdapter = new TitlePackAdapter(irancellPack, this, "");
            irancellRecycler.setAdapter(packAdapter);
            btnChargeConfirm.setVisibility(View.GONE);
            llPackBackIrancell.setVisibility(View.VISIBLE);
            irancellRecycler.setVisibility(View.VISIBLE);
            llIrancellSpinner.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() ->
            {
                spinnerIrancell.performClick();
            }, 500);
        } else
        {
            mainView.showError(packRespone.info.message);
        }
    }

    @Override
    public void onErrorGetPackageIrancell(String error)
    {
        mainView.showError(error);
        btnChargeConfirm.revertAnimation(this);
        btnChargeConfirm.setClickable(true);
    }


    @Override
    public void onStart()
    {
        super.onStart();
//        EventBus.getDefault().register(this);
    }


    public void onSelectContact(OnSelectContact onSelectContact)
    {
        //todo change this
        try
        {
            if (isMtn)
            {
                etMobileCharge.setText(onSelectContact.getNumber().replaceAll(" ", ""));
                tilMIrancell.setHint(onSelectContact.getName());


                return;
            }
            if (isMci)
            {
                etMCINumber.setText(onSelectContact.getNumber().replaceAll(" ", ""));
                tilMMci.setHint(onSelectContact.getName());


                return;


            }
            if (isRightel)
            {
                etMobileChargeRightel.setText(onSelectContact.getNumber().replaceAll(" ", ""));
                tilMRightel.setHint(onSelectContact.getName());


            }

        } catch (Exception e)
        {
        }
    }

    // @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onConfirmButtonPhoneNumber(Transaction event)
//    {
//
//        if (event.getTypeTransactionId() != 4)
//            return;
//        Log.e("teeeeeeeest", event.getInternetPackageVm().getPackageType() + "");
//        packageType = event.getInternetPackageVm().getPackageType();
//        amountType = event.getAmount();
//        operatorType = event.getInternetPackageVm().getTypeOperatorId();
//        if (isMtn)
//        {
//
//            etMobileCharge.setText(event.getInternetPackageVm().getMobileNumber());
//            //  etChargeAmount.setText(event.getAmount()+"");
//            btnChargeConfirm.startAnimation();
//            btnChargeConfirm.setClickable(false);
//            getPackageIrancell.findGetPackageIrancellDataRequest(this, etMobileCharge.getText().toString());
//            hideSoftKeyboard(etMobileCharge);
//
//
//            return;
//        }
//        if (isMci)
//        {
//            etMCINumber.setText(event.getInternetPackageVm().getMobileNumber());
//            //   etMCIAmount.setText(event.getAmount()+"");
//            btnMCIPackConfirm.startAnimation();
//            btnMCIPackConfirm.setClickable(false);
//            packageMci.findPackageMciDataRequest(this, etMCINumber.getText().toString());
//            hideSoftKeyboard(etMCINumber);
//            return;
//
//
//        }
//        if (isRightel)
//        {
//            etMobileChargeRightel.setText(event.getInternetPackageVm().getMobileNumber());
//            //   etChargeAmountRightel.setText(event.getAmount()+"");
//            btnChargeConfirmRightel.startAnimation();
//            btnChargeConfirmRightel.setClickable(false);
//            rightelPack.findRightelPackData(this, etMobileChargeRightel.getText().toString());
//            hideSoftKeyboard(etMobileChargeRightel);
//
//
//        }
//
//
//    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {
        if (TextUtils.isEmpty(charSequence))
        {
            try
            {
                if (isMtn)
                {
                    tilMIrancell.setHint("شماره موبایل");


                    return;
                }
                if (isMci)
                {
                    tilMMci.setHint("شماره موبایل");

                    return;


                }
                if (isRightel)
                {
                    tilMRightel.setHint("شماره موبایل");


                }

            } catch (Exception e)
            {
            }

        }

    }

    @Override
    public void afterTextChanged(Editable editable)
    {

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
        v.findViewById(R.id.container).setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
        setBtnBackToCharge();
    }

    @Override
    public void startAddCardActivity()
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

    }

    @Override
    public void onErrorCharge(String message)
    {

    }

    @Override
    public void onPaymentPackSimCard(PackageBuyResponse response, String mobile)
    {

        String urlPayment = response.getUrl();

        mainView.showLoading();
        mainView.openPackPaymentFragment(urlPayment, imageDrawable,
                title, amount,paymentInstance,mobile);
    }

    @Override
    public void onErrorPackSimcard(String message)
    {
        Tools.showToast(getContext(),message,R.color.red);

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

    @SuppressLint("StaticFieldLeak")
    private class ChangeData extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPreExecute()
        {
        }

        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                Thread.sleep(300);
                packAdapter = new TitlePackAdapter(irancellPack, PackFragment.this, filterType);

            } catch (InterruptedException e)
            {
                Thread.interrupted();

            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result)
        {
            if (isMtn)
            {
                irancellRecycler.setAdapter(packAdapter);


                return;
            }
            if (isMci)
            {
                mciRecycler.setAdapter(packAdapter);


                return;


            }
            if (isRightel)
            {
                rightelRecycler.setAdapter(packAdapter);
            }
        }

    }

    @Override
    public void onDestroyView()
    {
        //initDefaultOperatorView();

        super.onDestroyView();
    }



    /*
    public void filterIrancell(String text) {
        irancellPackFilter.clear();
        for (int i = 0; i <irancellPack.size() ; i++) {
            for (int j = 0; j <irancellPack.get(i).getDetail().size() ; j++) {
                if (irancellPack.get(i).getDetail().get(j).getPackageType().equals(text))
                    irancellPackFilter.add(irancellPack.get(i));
            }
        }
        packAdapter = new TitlePackAdapter(irancellPackFilter, this);
        irancellRecycler.setAdapter(packAdapter);

    }*/


}
