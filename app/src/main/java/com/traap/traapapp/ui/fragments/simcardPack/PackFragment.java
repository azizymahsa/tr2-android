package com.traap.traapapp.ui.fragments.simcardPack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyPackage.response.PackageBuyResponse;
import com.traap.traapapp.apiServices.model.contact.OnSelectContact;
import com.traap.traapapp.apiServices.model.getBoughtFor.GetBoughtForResponse;
import com.traap.traapapp.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;
import com.traap.traapapp.apiServices.model.getPackageMci.response.GetPackageMciResponse;
import com.traap.traapapp.apiServices.model.getRightelPack.response.Detail;
import com.traap.traapapp.apiServices.model.getRightelPack.response.GetRightelPackRespone;
import com.traap.traapapp.apiServices.model.getSimPackageList.response.SimContentItem;
import com.traap.traapapp.apiServices.model.getSimPackageList.response.SimPackage;
import com.traap.traapapp.apiServices.model.mobileCharge.response.MobileChargeResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.pack.RightelPackModel;
import com.traap.traapapp.models.otherModels.paymentInstance.SimPackPaymentInstance;
import com.traap.traapapp.models.otherModels.qrModel.QrModel;
import com.traap.traapapp.singleton.SingletonPaymentPlace;
import com.traap.traapapp.ui.adapters.pack.DetailPackAdapter;
import com.traap.traapapp.ui.adapters.pack.DetailPackAdapter_old;
import com.traap.traapapp.ui.adapters.pack.TitlePackAdapter;
import com.traap.traapapp.ui.adapters.pack.TitlePackAdapter_old;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.payment.PaymentActionView;
import com.traap.traapapp.ui.fragments.payment.PaymentParentActionView;
import com.traap.traapapp.ui.fragments.simcardCharge.OnClickContinueSelectPayment;
import com.traap.traapapp.ui.fragments.simcardPack.imp.BuyPackageImpl;
import com.traap.traapapp.ui.fragments.simcardPack.imp.BuyPackageInteractor;
import com.traap.traapapp.ui.fragments.simcardPack.imp.GetSimPackageListImpl;
import com.traap.traapapp.ui.fragments.simcardPack.imp.irancell.GetPackageIrancellImpl;
import com.traap.traapapp.ui.fragments.simcardPack.imp.mci.PackageMciImpl;
import com.traap.traapapp.ui.fragments.simcardPack.imp.rightel.RightelPackImpl;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.traap.traapapp.utilities.Utility.changeFontInViewGroup;

/**
 * Created by Javad.Abadi on 7/14/2018.
 */
@SuppressLint("ValidFragment")
public class PackFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener, OnAnimationEndListener, View.OnFocusChangeListener
        , RightelPackImpl.OnFinishedRightelPackListener, PackFragmentInteractor, DetailPackAdapter_old.GetPackInAdapter,
        PackageMciImpl.OnFinishedPackageMciListener, BuyPackageInteractor.OnFinishedBuyPackageListener, PaymentParentActionView,
        GetPackageIrancellImpl.OnFinishedGetPackageIrancellListener, AdapterView.OnItemSelectedListener, PaymentActionView
        , OnClickContinueSelectPayment, GetSimPackageListImpl.GetSimPackagesListener, DetailPackAdapter.GetPackFromAdapterListener
{
    private int myOperatorType = 0;

    private static int OPERATOR_TYPE_MCI = 2;
    private static int OPERATOR_TYPE_MTN = 1;
    private static int OPERATOR_TYPE_RIGHTEL = 3;

    private MainActionView mainView;
    private BuyPackageImpl buyPackageImpl;
    private SimPackPaymentInstance paymentInstance;
    private int imageDrawable = 0;
    private String title;
    private TextView tvUserName;
    private TextView tvHeaderPopularNo;
    private Integer backState;
    private List<SimPackage> allPackageList;

    public PackFragment()
    {

    }

    public static PackFragment newInstance(MainActionView mainView, Integer backState)
    {
        PackFragment f = new PackFragment();
        f.setMainView(mainView);
        f.setBackState(backState);
        return f;
    }

    private void setBackState(Integer backState)
    {
        this.backState = backState;
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
    private TitlePackAdapter_old packAdapter;
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

    @BindView(R.id.contentView)
    LinearLayout contentView;

    @BindView(R.id.rightelRecycler)
    RecyclerView rightelRecycler;
    @BindView(R.id.mciRecycler)
    RecyclerView mciRecycler;
    @BindView(R.id.irancellRecycler)
    RecyclerView irancellRecycler;

    @BindView(R.id.tabLayoutIrancell)
    TabLayout tabLayoutIrancell;

    @BindView(R.id.tabLayoutRightel)
    TabLayout tabLayoutRightel;


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
    RelativeLayout llDetailDescription;
    @BindView(R.id.llDetailDescriptionRightel)
    RelativeLayout llDetailDescriptionRightel;
    @BindView(R.id.llDetailDescriptionMci)
    RelativeLayout llDetailDescriptionMci;
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
    AutoCompleteTextView etMobileNumberRightel;
    @BindView(R.id.etMobileCharge)
    AutoCompleteTextView etMobileNumberIranCell;
    @BindView(R.id.etMCINumber)
    AutoCompleteTextView etMobileNumberMCI;
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
    @BindView(R.id.tipCvv2)
    TextInputLayout tipCvv2;
    @BindView(R.id.etCvv2)
    ClearableEditText etCvv2;
    @BindView(R.id.spinnerMci)
    Spinner spinnerMci;

    @BindView(R.id.llMciSpinner)
    LinearLayout llMciSpinner;
    @BindView(R.id.llRightelSpinner)
    LinearLayout llRightelSpinner;

    @BindView(R.id.llIrancellSpinner)
    LinearLayout llIrancellSpinner;

    private String[] mciFilter = {"همه", "اعتباری و دائمی", "اعتباری", "دائمی"};
    private String[] irancellFilter = {" اعتباري", " دائمي"};
    private String[] rightelFilter = {"اعتباری", "دائمی", "دیتا"};

    @OnClick(R.id.imgUserMobileIranCell)
    void onUserMobileIranCell()
    {
        etMobileNumberIranCell.setText("0" + Prefs.getString("mobile", ""));
    }

    ;

    @OnClick(R.id.imgUserMobileMci)
    void onUserMobileMci()
    {
        etMobileNumberMCI.setText("0" + Prefs.getString("mobile", ""));
    }

    ;

    @OnClick(R.id.imgUserMobileRightel)
    void onUserMobileRightel()
    {
        etMobileNumberRightel.setText("0" + Prefs.getString("mobile", ""));
    };


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
    }

    @OnClick(R.id.btnChargeBackRightel)
    void setRightelPack()
    {
        llDetailDescriptionRightel.setVisibility(View.VISIBLE);

        llRightelMobile.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInRight)
                .duration(200)
                .playOn(llRightelMobile);
        llRightelSpinner.setVisibility(View.GONE);
        btnChargeConfirmRightel.setVisibility(View.VISIBLE);
        rightelRecycler.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnPackBackIrancell)
    void setBtnIrancellBack()
    {
        llDetailDescription.setVisibility(View.VISIBLE);
        llIrancellMobile.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInRight)
                .duration(200)
                .playOn(llIrancellMobile);

        btnChargeConfirm.setVisibility(View.VISIBLE);
        irancellRecycler.setVisibility(View.GONE);
        llIrancellSpinner.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnPackBackMci)
    void setMciPack()
    {
        llDetailDescriptionMci.setVisibility(View.VISIBLE);

        lMciMobile.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInRight)
                .duration(200)
                .playOn(lMciMobile);
        llMciFilter.setVisibility(View.GONE);
        btnMCIPackConfirm.setVisibility(View.VISIBLE);
        mciRecycler.setVisibility(View.GONE);
        llMciSpinner.setVisibility(View.GONE);
    }

    @OnClick(R.id.flIrancell)
    void irancell()
    {
        etMobileNumberIranCell.requestFocus();
        etMobileNumberIranCell.setSelection(etMobileNumberIranCell.getText().length());

        closeAutoComplete();
        llDetailDescription.setVisibility(View.VISIBLE);

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


        imageDrawable = R.drawable.irancell;

        btnChargeConfirm.setVisibility(View.VISIBLE);
        irancellRecycler.setVisibility(View.GONE);
        llIrancellSpinner.setVisibility(View.GONE);

        setBtnChargeConfirm();
    }

    @OnClick(R.id.flHamraheAval)
    void hamraheAval()
    {
        etMobileNumberMCI.requestFocus();
        etMobileNumberMCI.setSelection(etMobileNumberMCI.getText().length());

        closeAutoComplete();
        llDetailDescriptionMci.setVisibility(View.VISIBLE);

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

        btnMCIPackConfirm.setVisibility(View.VISIBLE);
        mciRecycler.setVisibility(View.GONE);
        llMciSpinner.setVisibility(View.GONE);
        YoYo.with(Techniques.SlideInUp)
                .duration(200)
                .playOn(llMCICharge);
        isMtn = false;
        isMci = true;
        isRightel = false;

        imageDrawable = R.drawable.hamrahe_aval;

        getMci();
    }

    private void closeAutoComplete()
    {
        etMobileNumberIranCell.dismissDropDown();
        etMobileNumberMCI.dismissDropDown();
        etMobileNumberRightel.dismissDropDown();
    }

    @OnClick(R.id.flRightel)
    void rightel()
    {
        etMobileNumberRightel.requestFocus();
        etMobileNumberRightel.setSelection(etMobileNumberRightel.getText().length());

        closeAutoComplete();
        //     llDescriptionSelectPackRightel.setVisibility(View.GONE);
        llDetailDescriptionRightel.setVisibility(View.VISIBLE);
        llRightelMobile.setVisibility(View.VISIBLE);

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
        llRightelSpinner.setVisibility(View.GONE);
        btnChargeConfirmRightel.setVisibility(View.VISIBLE);
        rightelRecycler.setVisibility(View.GONE);

        isMtn = false;
        isMci = false;
        isRightel = true;
        imageDrawable = R.drawable.rightel;

        setBtnChargeConfirmRightel();
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
        {
            if (TextUtils.isEmpty(etCvv2.getText().toString()))
            {
                Utility.hideSoftKeyboard(btnBuyCharge.getRootView(), getActivity());
                mainView.showError("لطفا cvv2 را وارد نمایید");
                return;
            }
            else
            {
                ccv2 = etCvv2.getText().toString();
            }
        }

        String operatorTypeStr = "";
        String rightelRequestId = "";

        btnBuyCharge.startAnimation();
        btnBuyCharge.setClickable(false);
        if (isMtn)
        {
            operatorTypeStr = "1";
            mobile = etMobileNumberIranCell.getText().toString();

        }
        if (isMci)
        {
            operatorTypeStr = "2";
            mobile = etMobileNumberMCI.getText().toString();

            //  return;

        }
        if (isRightel)
        {
            operatorTypeStr = "3";
            rightelRequestId = requestId;
            mobile = etMobileNumberRightel.getText().toString();
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
            llMCICharge.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInLeft)
                    .duration(200)
                    .playOn(llMCICharge);
            return;
        }
        if (isRightel)
        {
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
        if (TextUtils.isEmpty(etMobileNumberIranCell.getText().toString()))
        {
            return;
        }
        try
        {
            if (!Utility.getMobileValidation(etMobileNumberIranCell.getText().toString()))
            {
                return;
            }
        }
        catch (Exception e)
        {
            return;
        }
        if (Utility.checkTaliyaValidation(etMobileNumberIranCell.getText().toString()))
        {
            mainView.showError("شماره تلفن وارد شده تالیا می باشد،امکان خرید شارژ برای این شماره وجود ندارد.");
            return;
        }
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;
        }

        mainView.showLoading();
//        getPackageIrancell.findGetPackageIrancellDataRequest(this, etMobileNumberIranCell.getText().toString());
        GetSimPackageListImpl.getSimPackageList(TrapConfig.OPERATOR_TYPE_MTN, etMobileNumberIranCell.getText().toString(),this);

        isMtn = true;
        isMci = false;
        isRightel = false;
    }

    @OnClick(R.id.btnChargeConfirmRightel)
    void setBtnChargeConfirmRightel()
    {
        initSpinner();
        setupRecycler();
        if (TextUtils.isEmpty(etMobileNumberRightel.getText().toString()))
        {
            return;
        }
        try
        {
            if (!Utility.getMobileValidation(etMobileNumberRightel.getText().toString()))
            {
                return;
            }
        }
        catch (Exception e)
        {
            return;
        }
        if (Utility.checkTaliyaValidation(etMobileNumberRightel.getText().toString()))
        {
            mainView.showError("شماره تلفن وارد شده تالیا می باشد،امکان خرید شارژ برای این شماره وجود ندارد.");
            return;
        }
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;

        }
        btnChargeConfirmRightel.startAnimation();
        btnChargeConfirmRightel.setClickable(false);
        mainView.showLoading();

//        rightelPack.findRightelPackData(this, etMobileNumberRightel.getText().toString());
        GetSimPackageListImpl.getSimPackageList(TrapConfig.OPERATOR_TYPE_RIGHTELL, etMobileNumberIranCell.getText().toString(),this);

        isMtn = false;
        isMci = false;
        isRightel = true;

    }

    @OnClick(R.id.btnMCIPackConfirm)
    void setBtnMCIPackConfirm()
    {
        getMci();
    }

    public void getMci()
    {
        initSpinner();
        setupRecycler();
        if (TextUtils.isEmpty(etMobileNumberMCI.getText().toString()))
        {
            return;
        }
        try
        {
            if (!Utility.getMobileValidation(etMobileNumberMCI.getText().toString()))
            {
                hideSoftKeyboard(etMobileNumberMCI);

                return;
            }
        }
        catch (Exception e)
        {
            return;

        }
        if (Utility.checkTaliyaValidation(etMobileNumberMCI.getText().toString()))
        {
            mainView.showError("شماره تلفن وارد شده تالیا می باشد،امکان خرید شارژ برای این شماره وجود ندارد.");
            return;
        }
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;
        }

        llDetailDescriptionMci.setVisibility(View.GONE);
        mainView.showLoading();
//        packageMci.findPackageMciDataRequest(this, etMobileNumberMCI.getText().toString());
        GetSimPackageListImpl.getSimPackageList(TrapConfig.OPERATOR_TYPE_MCI, etMobileNumberIranCell.getText().toString(),this);

        hideSoftKeyboard(etMobileNumberMCI);

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
        rightelPack = new RightelPackImpl();
        packageMci = new PackageMciImpl();
        getPackageIrancell = new GetPackageIrancellImpl();
        buyPackageImpl = new BuyPackageImpl();
        getBoughtForRequest();

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
        if (SingletonPaymentPlace.getInstance().getPaymentPlace() != 0)
        {
            Objects.requireNonNull(getActivity()).onBackPressed();
            getActivity().onBackPressed();
            SingletonPaymentPlace.getInstance().setPaymentPlace(0);

            return;
        }
        checkPhoneNumberOperator();
        try
        {
            if (isInitView)
            {
                tabLayoutIrancell.addTab(tabLayoutIrancell.newTab().setText("سیم کارت دائمی"));
                tabLayoutIrancell.addTab(tabLayoutIrancell.newTab().setText(" سیم کارت اعتباری"), true);
                tabLayoutIrancell.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
                {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab)
                    {
                        if (tabLayoutIrancell.getSelectedTabPosition() == 0)
                        {
                            filterType = "2";
//                            new ChangeData().execute();
                            irancellRecycler.setAdapter(new TitlePackAdapter(operatorType, TrapConfig.SIM_TYPE_FULL, allPackageList, PackFragment.this));
                        }
                        else
                        {
                            filterType = "1";
//                            new ChangeData().execute();
                            irancellRecycler.setAdapter(new TitlePackAdapter(operatorType, TrapConfig.SIM_TYPE_CREDIT, allPackageList, PackFragment.this));
                        }
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
                tabLayoutRightel.addTab(tabLayoutRightel.newTab().setText("سیم کارت دیتا"));

                tabLayoutRightel.addTab(tabLayoutRightel.newTab().setText("سیم کارت دائمی"));
                tabLayoutRightel.addTab(tabLayoutRightel.newTab().setText(" سیم کارت اعتباری"), true);
                tabLayoutRightel.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
                {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab)
                    {
                        if (tabLayoutRightel.getSelectedTabPosition() == 0)
                        {
                            filterType = "3";
//                            new ChangeData().execute();
                            rightelRecycler.setAdapter(new TitlePackAdapter(operatorType, TrapConfig.SIM_TYPE_DATA, allPackageList, PackFragment.this));
                        }
                        else if (tabLayoutRightel.getSelectedTabPosition() == 1)
                        {
                            filterType = "2";
//                            new ChangeData().execute();
                            rightelRecycler.setAdapter(new TitlePackAdapter(operatorType, TrapConfig.SIM_TYPE_FULL, allPackageList, PackFragment.this));
                        }
                        else
                        {
                            filterType = "1";
//                            new ChangeData().execute();
                            rightelRecycler.setAdapter(new TitlePackAdapter(operatorType, TrapConfig.SIM_TYPE_CREDIT, allPackageList, PackFragment.this));

                        }
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
                changeFontInViewGroup(tabLayoutRightel, "fonts/iran_sans_normal.ttf");
                isInitView = false;
                initView();

                initDefaultOperatorView();
                etCvv2.setText("");
                etPassCharge.setText("");
            }
            initSpinner();
            setupRecycler();
        }
        catch (Exception e)
        {

        }
    }

    private void checkPhoneNumberOperator()
    {
        etMobileNumberIranCell.addTextChangedListener(new TextWatcher()
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
                    if (etMobileNumberIranCell.getText().toString().length() == 11)
                    {
                        operatorType = getOperatorType(charSequence.toString());

                        if (OPERATOR_TYPE_MCI == getOperatorType(charSequence.toString()))
                        {
                            etMobileNumberMCI.setText(charSequence.toString());
                            hamraheAval();
                            return;

                        }
                        else if (OPERATOR_TYPE_RIGHTEL == getOperatorType(charSequence.toString()))
                        {
                            etMobileNumberRightel.setText(charSequence.toString());
                            rightel();
                            return;
                        }
                    }
                }
                catch (Exception e)
                {

                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        etMobileNumberMCI.addTextChangedListener(new TextWatcher()
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
                    if (etMobileNumberMCI.getText().toString().length() == 11)

                    {
                        operatorType = getOperatorType(charSequence.toString());

                        if (OPERATOR_TYPE_MTN == getOperatorType(charSequence.toString()))
                        {
                            etMobileNumberIranCell.setText(charSequence.toString());
                            irancell();
                            return;
                            // }


                        }
                        else if (OPERATOR_TYPE_RIGHTEL == getOperatorType(charSequence.toString()))
                        {
                            etMobileNumberRightel.setText(charSequence.toString());
                            rightel();
                            return;

                        }
                    }
                }
                catch (Exception e)
                {

                }

            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        etMobileNumberRightel.addTextChangedListener(new TextWatcher()
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

                    if (etMobileNumberRightel.getText().toString().length() == 11)
                    {
                        operatorType = getOperatorType(charSequence.toString());

                        if (OPERATOR_TYPE_MCI == getOperatorType(charSequence.toString()))
                        {
                            etMobileNumberMCI.setText(charSequence.toString());
                            hamraheAval();
                            return;
                        }
                        else if (OPERATOR_TYPE_MTN == getOperatorType(charSequence.toString()))
                        {
                            etMobileNumberIranCell.setText(charSequence.toString());
                            irancell();
                            return;
                        }
                    }
                }
                catch (Exception e)
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
    public void onStop()
    {
        super.onStop();
        try
        {
            if (etPassCharge != null)
            {
                etPassCharge.setText("");
            }
            if (etCvv2 != null)
            {
                etCvv2.setText("");
            }
        }
        catch (Exception e)
        {
        }

    }

    private int getOperatorType(String phoneNo)
    {
        String startPhoneNo = phoneNo.substring(0, 4);
        Logger.e("-startPhoneNo-", startPhoneNo);

        String[] typeMCI_No = {"0990", "0991", "0910", "0911", "0912", "0913", "0914", "0915", "0916", "0917", "0918", "0919", "0992", "0094"};
        String[] typeMTN_No = {"0901", "0902", "0903", "0905", "0930", "0933", "0935", "0936", "0937", "0938", "0939"};
        String[] typeRightel_No = {"0920", "0921", "0922"};

        if (Arrays.asList(typeMCI_No).contains(startPhoneNo))
        {
            return OPERATOR_TYPE_MCI;
        }
        else if (Arrays.asList(typeMTN_No).contains(startPhoneNo))
        {
            return OPERATOR_TYPE_MTN;
        }
        else if (Arrays.asList(typeRightel_No).contains(startPhoneNo))
        {
            return OPERATOR_TYPE_RIGHTEL;
        }
        else
        {
            return OPERATOR_TYPE_MCI;
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
        spinnerMci.setOnItemSelectedListener(this);
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
//                    new ChangeData().execute();
                    mciRecycler.setAdapter(new TitlePackAdapter(operatorType, TrapConfig.SIM_TYPE_FULL, allPackageList, PackFragment.this));
                    break;
                case 1:
                    filterType = "2";
//                    new ChangeData().execute();
                    mciRecycler.setAdapter(new TitlePackAdapter(operatorType, TrapConfig.SIM_TYPE_FULL, allPackageList, PackFragment.this));
                    break;
                case 2:
                    filterType = "1";
//                    new ChangeData().execute();
                    mciRecycler.setAdapter(new TitlePackAdapter(operatorType, TrapConfig.SIM_TYPE_FULL, allPackageList, PackFragment.this));
                    break;
                case 3:
                    filterType = "0";
//                    new ChangeData().execute();
                    mciRecycler.setAdapter(new TitlePackAdapter(operatorType, TrapConfig.SIM_TYPE_FULL, allPackageList, PackFragment.this));
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    }


    private void initView()
    {
        mToolbar = v.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.flLogoToolbar).setOnClickListener(v -> mainView.backToMainFragment());

        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView ->
                mainView.backToAllServicePackage(backState, 4));
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("خرید بسته اینترنت");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        btnChargeConfirm.setText("ادامه");
        btnMCIPackConfirm.setText("ادامه");
        btnChargeConfirmRightel.setText("ادامه");

        etMobileNumberMCI.setText("0" + Prefs.getString("mobile", ""));
        etMobileNumberIranCell.setText("0" + Prefs.getString("mobile", ""));
        etMobileNumberRightel.setText("0" + Prefs.getString("mobile", ""));

        tlPassCharge.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        tipCvv2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));

        initDefaultOperatorView();

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(11);
        etMobileNumberIranCell.setFilters(filterArray);
        etMobileNumberMCI.setFilters(filterArray);
        etMobileNumberRightel.setFilters(filterArray);

        rbAll.setOnCheckedChangeListener(this);
        rbCredit.setOnCheckedChangeListener(this);
        rbFirst.setOnCheckedChangeListener(this);
        rbRightelFirst.setOnCheckedChangeListener(this);
        rbRightelCredit.setOnCheckedChangeListener(this);
        rbRightelAll.setOnCheckedChangeListener(this);
        rbMciAll.setOnCheckedChangeListener(this);
        rbMciCredit.setOnCheckedChangeListener(this);
        rbMciFirst.setOnCheckedChangeListener(this);

        if (cardNumberCheck != null && !cardNumberCheck.equals("003725"))
        {
            llCvv2.setVisibility(View.VISIBLE);
        }

        etMobileNumberMCI.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (etMobileNumberMCI.getText().toString().length() == 11)
                {

                    setBtnMCIPackConfirm();
                }

            }
        });


        etMobileNumberIranCell.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (etMobileNumberIranCell.getText().toString().length() == 11)
                {

                    setBtnChargeConfirm();

                }

            }
        });

        etMobileNumberRightel.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (etMobileNumberRightel.getText().toString().length() == 11)
                {

                    setBtnChargeConfirmRightel();


                }

            }
        });


    }

    private void getBoughtForRequest()
    {
        SingletonService.getInstance().getBoughtForService().getBoughtFor(new OnServiceStatus<WebServiceClass<GetBoughtForResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetBoughtForResponse> response)
            {
                try
                {
                    if (response.info.statusCode == 200)
                    {
                        onGetBoutForSuccess(response.data.getResults());
                    }
                    else
                    {
                        Tools.showToast(getContext(), response.info.message, R.color.red);
                    }
                }
                catch (Exception e)
                {
                    Tools.showToast(getContext(), e.getMessage(), R.color.red);

                }
            }

            @Override
            public void onError(String message)
            {
                if (Tools.isNetworkAvailable(getActivity()))
                {
                    showToast(getActivity(), message, R.color.red);

                }
                else
                {
                    showAlertFailure(getActivity(), getString(R.string.networkErrorMessage), getString(R.string.networkError), true);
                }


            }
        });
    }

    private void onGetBoutForSuccess(List<String> results)
    {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), R.layout.custom_spinner_dropdown_item, results);
        etMobileNumberMCI.setThreshold(1);//will start working from first character
        etMobileNumberMCI.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

        etMobileNumberIranCell.setThreshold(1);//will start working from first character
        etMobileNumberIranCell.setAdapter(adapter);

        etMobileNumberRightel.setThreshold(1);//will start working from first character
        etMobileNumberRightel.setAdapter(adapter);
    }

    private void initDefaultOperatorView()
    {
        myOperatorType = Utility.getOperatorType("0" + Prefs.getString("mobile", ""));
        tvAmountPackage.setVisibility(View.GONE);
        llPassCharge.setVisibility(View.GONE);
        llSelectOptaror.setVisibility(View.VISIBLE);


        switch (myOperatorType)
        {
            case TrapConfig.OPERATOR_TYPE_MCI:
            {
                llMCICharge.setVisibility(View.VISIBLE);
                llMTNCharge.setVisibility(View.GONE);
                llRightelCharge.setVisibility(View.GONE);

                ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.btnColorSecondary));
                ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

                ivIrancell.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.irancell2));
                ivHamraheAval.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.hamrahe_aval));
                ivRightel.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.rightel2));


                btnMCIPackConfirm.setVisibility(View.VISIBLE);
                mciRecycler.setVisibility(View.GONE);
                llMciSpinner.setVisibility(View.GONE);
                isMtn = false;
                isMci = true;
                isRightel = false;
                break;
            }
            case TrapConfig.OPERATOR_TYPE_MTN:
            {
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
                irancellRecycler.setVisibility(View.GONE);
                llIrancellSpinner.setVisibility(View.GONE);

                break;
            }
            case TrapConfig.OPERATOR_TYPE_RIGHTELL:
            {
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
                llRightelSpinner.setVisibility(View.GONE);
                btnChargeConfirmRightel.setVisibility(View.VISIBLE);
                rightelRecycler.setVisibility(View.GONE);
                break;
            }
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
//                    new ChangeData().execute();

                }

                if (packAdapter == null)
                {
                    return;
                }
                break;
            case R.id.rbCredit:
                if (rbCredit.isChecked())
                {
                    rbFirst.setChecked(false);
                    rbAll.setChecked(false);
                    filterType = "1";
//                    new ChangeData().execute();

                }

                break;
            case R.id.rbFirst:
                if (rbFirst.isChecked())
                {
                    rbAll.setChecked(false);
                    rbCredit.setChecked(false);
                    filterType = "2";
//                    new ChangeData().execute();

                }
                break;

            case R.id.rbRightelAll:
                if (rbRightelAll.isChecked())
                {
                    rbRightelCredit.setChecked(false);
                    rbRightelFirst.setChecked(false);
                    filterType = "all";
//                    new ChangeData().execute();
                }
                break;
            case R.id.rbRightelCredit:
                if (rbRightelCredit.isChecked())
                {
                    rbRightelAll.setChecked(false);
                    rbRightelFirst.setChecked(false);
                    filterType = "1";
//                    new ChangeData().execute();

                }
                break;
            case R.id.rbRightelFirst:
                if (rbRightelFirst.isChecked())
                {
                    rbRightelCredit.setChecked(false);
                    rbRightelAll.setChecked(false);
                    filterType = "2";
//                    new ChangeData().execute();

                }
                break;


            case R.id.rbMciFirst:
                if (rbMciFirst.isChecked())
                {
                    rbMciCredit.setChecked(false);
                    rbMciAll.setChecked(false);
                    filterType = "0";
//                    new ChangeData().execute();

                }
                break;

            case R.id.rbMciCredit:
                if (rbMciCredit.isChecked())
                {
                    rbMciAll.setChecked(false);
                    rbMciFirst.setChecked(false);
                    filterType = "1";
//                    new ChangeData().execute();

                }
                break;

            case R.id.rbMciAll:
                if (rbMciAll.isChecked())
                {
                    rbMciCredit.setChecked(false);
                    rbMciFirst.setChecked(false);
                    filterType = "2";
//                    new ChangeData().execute();

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
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }


    @Override
    public void onFinishedRightelPack(WebServiceClass<GetRightelPackRespone> packResponse)
    {
        btnChargeConfirmRightel.revertAnimation(this);
        btnChargeConfirmRightel.setClickable(true);
        mainView.hideLoading();

        llDetailDescriptionRightel.setVisibility(View.GONE);

        if (packResponse.info.statusCode == 200)
        {
            requestId = packResponse.data.getPackages().getRequestId();
            rightelRecycler.setNestedScrollingEnabled(false);
            irancellPack.clear();

            if (packResponse.data.getPackages().getDaily() != null && packResponse.data.getPackages().getDaily().size() != 0)
            {
                irancellPack.add(new RightelPackModel("روزانه", packResponse.data.getPackages().getDaily()));
            }

            if (packResponse.data.getPackages().getThreeDays() != null && packResponse.data.getPackages().getThreeDays().size() != 0)
            {
                irancellPack.add(new RightelPackModel("سه روزه", packResponse.data.getPackages().getThreeDays()));
            }

            if (packResponse.data.getPackages().getTenDays() != null && packResponse.data.getPackages().getTenDays().size() != 0)
            {
                irancellPack.add(new RightelPackModel("ده روزه", packResponse.data.getPackages().getTenDays()));
            }

            if (packResponse.data.getPackages().getWeekly() != null && packResponse.data.getPackages().getWeekly().size() != 0)
            {
                irancellPack.add(new RightelPackModel("هفتگی", packResponse.data.getPackages().getWeekly()));
            }

            if (packResponse.data.getPackages().getFifteenDays() != null && packResponse.data.getPackages().getFifteenDays().size() != 0)
            {
                irancellPack.add(new RightelPackModel("پانزده روزه", packResponse.data.getPackages().getFifteenDays()));
            }

            if (packResponse.data.getPackages().getMonthly() != null && packResponse.data.getPackages().getMonthly().size() != 0)
            {
                irancellPack.add(new RightelPackModel("ماهیانه", packResponse.data.getPackages().getMonthly()));
            }

            if (packResponse.data.getPackages().getThreeMonths() != null && packResponse.data.getPackages().getThreeMonths().size() != 0)
            {
                irancellPack.add(new RightelPackModel("سه ماهه", packResponse.data.getPackages().getThreeMonths()));
            }

            if (packResponse.data.getPackages().getSixMonths() != null && packResponse.data.getPackages().getSixMonths().size() != 0)
            {
                irancellPack.add(new RightelPackModel("شش ماهه", packResponse.data.getPackages().getSixMonths()));
            }

            if (packResponse.data.getPackages().getOneYear() != null && packResponse.data.getPackages().getSixMonths().size() != 0)
            {
                irancellPack.add(new RightelPackModel("یک ساله", packResponse.data.getPackages().getOneYear()));
            }
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
                            amount = irancellPack.get(i).getDetail().get(j).getAmount();
                            tvAmountPackage.setText("قیمت: " + Utility.priceFormat(amount) + " ریال");
                            llRightelCharge.setVisibility(View.GONE);
                        }
                    }
                }
                if (!isActive)
                {
                    mainView.showError("وضعیت بسته شما تغییر کرده، لطفا مجددا بسته خود را انتخاب کنید.");
                }
                packageType = "";

            }

            packAdapter = new TitlePackAdapter_old(irancellPack, this, "", 3);


            rightelRecycler.setAdapter(packAdapter);

            btnChargeConfirmRightel.setVisibility(View.VISIBLE);
            llRightelMobile.setVisibility(View.VISIBLE);
            llRightelSpinner.setVisibility(View.VISIBLE);
            rightelRecycler.setVisibility(View.VISIBLE);

        }
        else
        {
            mainView.showError(packResponse.info.message);


        }


    }

    @Override
    public void onErrorRightelPack(String error)
    {
        mainView.hideLoading();
        btnChargeConfirmRightel.revertAnimation(this);
        btnChargeConfirmRightel.setClickable(true);
        mainView.showError(error);

    }

    @Override
    public void getPackRightel(Detail o, Integer operatorType)
    {
        imageDrawable = 0;
        amount = o.getBillAmount();

        tvAmountPackage.setText("قیمت: " + Utility.priceFormat(amount) + " ریال");

        if (operatorType == 1)
        {
            imageDrawable = R.drawable.irancell;
            profileType = o.getProfileId();
            mobile = etMobileNumberIranCell.getText().toString();

        }
        else if (operatorType == 2)
        {
            imageDrawable = R.drawable.hamrahe_aval;
            profileType = Integer.valueOf(o.getProfileId());
            mobile = etMobileNumberMCI.getText().toString();
        }
        else
        {
            imageDrawable = R.drawable.rightel;
            profileType = Integer.valueOf(o.getProfileId());
            mobile = etMobileNumberRightel.getText().toString();
        }


        //----------------------------new for payment fragment-----------------------
        title = "با انجام این پرداخت ، مبلغ " +
                Utility.priceFormat(amount) + " ریال بابت " +
                o.getTitle() + " برای شماره " + mobile + "، از حساب شما کسر خواهد شد.";

        operatorType = Utility.getOperatorType(mobile);

        paymentInstance = new SimPackPaymentInstance();
        paymentInstance.setPAYMENT_STATUS(TrapConfig.PAYMENT_STAUS_PackSimCard);
        paymentInstance.setOperatorType(operatorType);
        paymentInstance.setProfileId(o.getProfileId());
        paymentInstance.setRequestId(requestId);
        paymentInstance.setTitlePackageType(o.getTitle() + " ( " + o.getTitlePackageType() + " ) ");

        getUrlPackagePayment(amount, paymentInstance, mobile);

        //----------------------------new for payment fragment-----------------------
    }

    private void getUrlPackagePayment(String amount, SimPackPaymentInstance paymentInstance, String mobile)
    {
        mainView.showLoading();
        buyPackageImpl.findBuyPackageDataRequest(this, paymentInstance.getRequestId()
                , paymentInstance.getOperatorType(), paymentInstance.getTitlePackageType(), paymentInstance.getProfileId(), mobile, amount);
    }


    @Override
    public void onFinishedPackageMciPackage(WebServiceClass<GetPackageMciResponse> packRespone)
    {

        btnMCIPackConfirm.revertAnimation(this);
        btnMCIPackConfirm.setClickable(true);
        mainView.hideLoading();
        if (packRespone.info.statusCode == 200)
        {
            mciRecycler.setNestedScrollingEnabled(false);
            llMciFilter.setVisibility(View.GONE);
            llMciSpinner.setVisibility(View.GONE);
            irancellPack.clear();
            if (packRespone.data.getPackages().getNightly() != null && packRespone.data.getPackages().getNightly().size() != 0)
            {
                irancellPack.add(new RightelPackModel("شبانه", packRespone.data.getPackages().getNightly()));
            }
            if (packRespone.data.getPackages().getDaily() != null && packRespone.data.getPackages().getDaily().size() != 0)
            {
                irancellPack.add(new RightelPackModel("روزانه", packRespone.data.getPackages().getDaily()));
            }

            if (packRespone.data.getPackages().getThreeDays() != null && packRespone.data.getPackages().getThreeDays().size() != 0)
            {
                irancellPack.add(new RightelPackModel("سه روزه", packRespone.data.getPackages().getThreeDays()));
            }

            if (packRespone.data.getPackages().getWeekly() != null && packRespone.data.getPackages().getWeekly().size() != 0)
            {
                irancellPack.add(new RightelPackModel("هفتگی", packRespone.data.getPackages().getWeekly()));
            }

            if (packRespone.data.getPackages().getMonthly() != null && packRespone.data.getPackages().getMonthly().size() != 0)
            {
                irancellPack.add(new RightelPackModel("ماهیانه", packRespone.data.getPackages().getMonthly()));
            }

            if (packRespone.data.getPackages().getTwoMonths() != null && packRespone.data.getPackages().getTwoMonths().size() != 0)
            {
                irancellPack.add(new RightelPackModel("دو ماهه", packRespone.data.getPackages().getTwoMonths()));
            }

            if (packRespone.data.getPackages().getThreeMonths() != null && packRespone.data.getPackages().getThreeMonths().size() != 0)
            {
                irancellPack.add(new RightelPackModel("سه ماهه", packRespone.data.getPackages().getThreeMonths()));
            }

            if (packRespone.data.getPackages().getFourMonths() != null && packRespone.data.getPackages().getFourMonths().size() != 0)
            {
                irancellPack.add(new RightelPackModel("چهار ماهه", packRespone.data.getPackages().getFourMonths()));
            }

            if (packRespone.data.getPackages().getSixMonths() != null && packRespone.data.getPackages().getSixMonths().size() != 0)
            {
                irancellPack.add(new RightelPackModel("شش ماهه", packRespone.data.getPackages().getSixMonths()));
            }

            if (packRespone.data.getPackages().getOneYear() != null && packRespone.data.getPackages().getOneYear().size() != 0)
            {
                irancellPack.add(new RightelPackModel("یک ساله", packRespone.data.getPackages().getOneYear()));
            }

            if (packRespone.data.getPackages().getOther() != null && packRespone.data.getPackages().getOther().size() != 0)
            {
                irancellPack.add(new RightelPackModel("موارد دیگر", packRespone.data.getPackages().getOther()));
            }


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
                            amount = irancellPack.get(i).getDetail().get(j).getAmount();
                            tvAmountPackage.setText("قیمت: " + Utility.priceFormat(amount) + " ریال");
                            llMCICharge.setVisibility(View.GONE);
                        }
                    }
                }

                if (!isActive)
                {
                    mainView.showError("وضعیت بسته شما تغییر کرده، لطفا مجددا بسته خود را انتخاب کنید.");
                }
                packageType = "";

            }

            packAdapter = new TitlePackAdapter_old(irancellPack, this, "", 2);
            mciRecycler.setAdapter(packAdapter);
            btnMCIPackConfirm.setVisibility(View.GONE);
            llMciSpinner.setVisibility(View.VISIBLE);
            mciRecycler.setVisibility(View.VISIBLE);
        }
        else
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
        btnBuyCharge.revertAnimation(this);
        btnBuyCharge.setClickable(true);

        if (response.info.statusCode == 200)
        {

        }
        else
        {
            mainView.showError(response.info.message);

        }
    }


    @Override
    public void onErrorMciBuyPackagePackage(String error)
    {
        btnBuyCharge.revertAnimation(this);
        btnBuyCharge.setClickable(true);
        mainView.showError(error);
    }

    @Override
    public void onFinishedGetPackageIrancell(WebServiceClass<GetPackageIrancellResponse> packRespone)
    {
        btnChargeConfirm.revertAnimation(this);
        btnChargeConfirm.setClickable(true);
        mainView.hideLoading();

        llDetailDescription.setVisibility(View.GONE);

        if (packRespone.info.statusCode == 200)
        {
            rbAll.setChecked(true);

            irancellRecycler.setNestedScrollingEnabled(false);

            irancellPack.clear();
            if (packRespone.data.getIrancellPackage().getHourly() != null && packRespone.data.getIrancellPackage().getHourly().size() != 0)
            {
                irancellPack.add(new RightelPackModel("ساعتی", packRespone.data.getIrancellPackage().getHourly()));
            }

            if (packRespone.data.getIrancellPackage().getDaily() != null && packRespone.data.getIrancellPackage().getDaily().size() != 0)
            {
                irancellPack.add(new RightelPackModel("روزانه", packRespone.data.getIrancellPackage().getDaily()));
            }


            if (packRespone.data.getIrancellPackage().getThreeDays() != null && packRespone.data.getIrancellPackage().getThreeDays().size() != 0)
            {
                irancellPack.add(new RightelPackModel("سه روزه", packRespone.data.getIrancellPackage().getThreeDays()));
            }

            if (packRespone.data.getIrancellPackage().getWeekly() != null && packRespone.data.getIrancellPackage().getWeekly().size() != 0)
            {
                irancellPack.add(new RightelPackModel("هفتگی", packRespone.data.getIrancellPackage().getWeekly()));
            }


            if (packRespone.data.getIrancellPackage().getFifteenDays() != null && packRespone.data.getIrancellPackage().getFifteenDays().size() != 0)
            {
                irancellPack.add(new RightelPackModel("پانزده روزه", packRespone.data.getIrancellPackage().getFifteenDays()));
            }


            if (packRespone.data.getIrancellPackage().getMonthly() != null && packRespone.data.getIrancellPackage().getMonthly().size() != 0)
            {
                irancellPack.add(new RightelPackModel("ماهیانه", packRespone.data.getIrancellPackage().getMonthly()));
            }

            if (packRespone.data.getIrancellPackage().getTwoMonths() != null && packRespone.data.getIrancellPackage().getTwoMonths().size() != 0)
            {
                irancellPack.add(new RightelPackModel("دو ماهه", packRespone.data.getIrancellPackage().getTwoMonths()));
            }

            if (packRespone.data.getIrancellPackage().getThreeMonths() != null && packRespone.data.getIrancellPackage().getThreeMonths().size() != 0)
            {
                irancellPack.add(new RightelPackModel("سه ماهه", packRespone.data.getIrancellPackage().getThreeMonths()));
            }

            if (packRespone.data.getIrancellPackage().getSixMonths() != null && packRespone.data.getIrancellPackage().getSixMonths().size() != 0)
            {
                irancellPack.add(new RightelPackModel("شش ماهه", packRespone.data.getIrancellPackage().getSixMonths()));
            }

            if (packRespone.data.getIrancellPackage().getOneYear() != null && packRespone.data.getIrancellPackage().getOneYear().size() != 0)
            {
                irancellPack.add(new RightelPackModel("یک ساله", packRespone.data.getIrancellPackage().getOneYear()));
            }


            imageDrawable = R.drawable.irancell;

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
                            llPassCharge.setVisibility(View.VISIBLE);
                            tvAmountPackage.setVisibility(View.VISIBLE);
                            llSelectOptaror.setVisibility(View.GONE);

                            YoYo.with(Techniques.SlideInRight)
                                    .duration(200)
                                    .playOn(llPassCharge);
                            YoYo.with(Techniques.SlideInRight)
                                    .duration(200)
                                    .playOn(tvAmountPackage);
                            profileType = irancellPack.get(i).getDetail().get(j).getProfileId();
                            amount = irancellPack.get(i).getDetail().get(j).getAmount();
                            tvAmountPackage.setText("قیمت: " + Utility.priceFormat(amount) + " ریال");
                            llMTNCharge.setVisibility(View.GONE);


                        }
                    }


                }
                if (!isActive)
                {
                    mainView.showError("وضعیت بسته شما تغییر کرده، لطفا مجددا بسته خود را انتخاب کنید.");
                }
                packageType = "";

            }


            packAdapter = new TitlePackAdapter_old(irancellPack, this, "", 1);
            irancellRecycler.setAdapter(packAdapter);
            btnChargeConfirm.setVisibility(View.GONE);
            irancellRecycler.setVisibility(View.VISIBLE);
            llIrancellSpinner.setVisibility(View.VISIBLE);
        }
        else
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
    }


    public void onSelectContact(OnSelectContact onSelectContact)
    {
        //todo change this
        try
        {
            etMobileNumberIranCell.setText(onSelectContact.getNumber().replaceAll(" ", ""));
            etMobileNumberMCI.setText(onSelectContact.getNumber().replaceAll(" ", ""));
            etMobileNumberRightel.setText(onSelectContact.getNumber().replaceAll(" ", ""));
            closeAutoComplete();
        }
        catch (Exception e)
        {
        }
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

        mainView.hideLoading();
        mainView.openPackPaymentFragment(this, urlPayment, imageDrawable,
                title, amount, paymentInstance, mobile, TrapConfig.PAYMENT_STAUS_PackSimCard);
    }

    @Override
    public void onErrorPackSimcard(String message)
    {
        mainView.hideLoading();

        showAlertFailure(getActivity(), message, "", true);

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
    public void onPaymentBill()
    {

    }

    @Override
    public void onPaymentTicket()
    {

    }

    @Override
    public void onBackClicked()
    {

    }

    @Override
    public void onGetSimPackagesCompleted(int operatorType, List<SimPackage> packageList, String requestId)
    {
        mainView.hideLoading();
        this.allPackageList = packageList;

        llDetailDescription.setVisibility(View.GONE);

        rbAll.setChecked(true);

        switch (operatorType)
        {
            case TrapConfig.OPERATOR_TYPE_MTN:
            {

                btnChargeConfirm.revertAnimation(PackFragment.this);
                btnChargeConfirm.setClickable(true);
                irancellRecycler.setNestedScrollingEnabled(false);

//                irancellPack.clear();
                imageDrawable = R.drawable.irancell;

                irancellRecycler.setAdapter(new TitlePackAdapter(operatorType, TrapConfig.SIM_TYPE_CREDIT, packageList, this));
                btnChargeConfirm.setVisibility(View.GONE);
                irancellRecycler.setVisibility(View.VISIBLE);
                llIrancellSpinner.setVisibility(View.VISIBLE);
                break;
            }

            case TrapConfig.OPERATOR_TYPE_MCI:
            {
                btnMCIPackConfirm.revertAnimation(this);
                btnMCIPackConfirm.setClickable(true);
                mciRecycler.setNestedScrollingEnabled(false);
                llMciFilter.setVisibility(View.GONE);
                llMciSpinner.setVisibility(View.GONE);

                mciRecycler.setAdapter(new TitlePackAdapter(operatorType, TrapConfig.SIM_MCI_TYPE, packageList, this));
                btnMCIPackConfirm.setVisibility(View.GONE);
                llMciSpinner.setVisibility(View.VISIBLE);
                mciRecycler.setVisibility(View.VISIBLE);
                break;
            }

            case TrapConfig.OPERATOR_TYPE_RIGHTELL:
            {
                btnChargeConfirmRightel.revertAnimation(this);
                btnChargeConfirmRightel.setClickable(true);
                llDetailDescriptionRightel.setVisibility(View.GONE);
                this.requestId = requestId;
                rightelRecycler.setNestedScrollingEnabled(false);

                rightelRecycler.setAdapter(new TitlePackAdapter(operatorType, TrapConfig.SIM_TYPE_CREDIT, packageList, this));

                btnChargeConfirmRightel.setVisibility(View.VISIBLE);
                llRightelMobile.setVisibility(View.VISIBLE);
                llRightelSpinner.setVisibility(View.VISIBLE);
                rightelRecycler.setVisibility(View.VISIBLE);

                break;
            }

        }
    }

    @Override
    public void onGetSimPackagesError(String message)
    {
        showAlertFailure(getActivity(), message, "", true);
        btnChargeConfirm.revertAnimation(PackFragment.this);
        btnChargeConfirm.setClickable(true);
    }

    @Override
    public void getPackDetails(int operatorType, SimContentItem simContentItem)
    {
        imageDrawable = 0;
        amount = String.valueOf(simContentItem.getBillAmount());

        tvAmountPackage.setText("قیمت: " + Utility.priceFormat(amount) + " ریال");

        switch (operatorType)
        {
            case TrapConfig.OPERATOR_TYPE_MTN:
            {
                imageDrawable = R.drawable.irancell;
                profileType = simContentItem.getPackageId();
                mobile = etMobileNumberIranCell.getText().toString();
                break;
            }
            case TrapConfig.OPERATOR_TYPE_MCI:
            {
                imageDrawable = R.drawable.hamrahe_aval;
                profileType = Integer.valueOf(simContentItem.getPackageId());
                mobile = etMobileNumberMCI.getText().toString();
                break;
            }
            case TrapConfig.OPERATOR_TYPE_RIGHTELL:
            {
                imageDrawable = R.drawable.rightel;
                profileType = Integer.valueOf(simContentItem.getPackageId());
                mobile = etMobileNumberRightel.getText().toString();
                break;
            }
        }

        //----------------------------new for payment fragment-----------------------
        title = "با انجام این پرداخت ، مبلغ " +
                Utility.priceFormat(amount) + " ریال بابت " +
                simContentItem.getTitle() + " برای شماره " + mobile + "، از حساب شما کسر خواهد شد.";

        operatorType = Utility.getOperatorType(mobile);

        paymentInstance = new SimPackPaymentInstance();
        paymentInstance.setPAYMENT_STATUS(TrapConfig.PAYMENT_STAUS_PackSimCard);
        paymentInstance.setOperatorType(operatorType);
        paymentInstance.setProfileId(simContentItem.getPackageId());
        paymentInstance.setRequestId(requestId);

        paymentInstance.setTitlePackageType(new StringBuilder(simContentItem.getTitle())
                .append(" ( ")
                .append(simContentItem.getTitlePackageType())
                .append(" )")
                .toString()
        );

        getUrlPackagePayment(amount, paymentInstance, mobile);

        //----------------------------new for payment fragment-----------------------
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
                packAdapter = new TitlePackAdapter_old(irancellPack, PackFragment.this, filterType, 1);

            }
            catch (InterruptedException e)
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
        super.onDestroyView();
    }
}
