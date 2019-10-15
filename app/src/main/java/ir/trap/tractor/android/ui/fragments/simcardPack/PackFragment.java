package ir.trap.tractor.android.ui.fragments.simcardPack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputLayout;
import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.model.buyPackage.response.PackageBuyResponse;
import ir.trap.tractor.android.apiServices.model.getPackageIrancell.response.GetPackageIrancellResponse;
import ir.trap.tractor.android.apiServices.model.getPackageMci.response.GetPackageMciResponse;
import ir.trap.tractor.android.apiServices.model.getRightelPack.response.Detail;
import ir.trap.tractor.android.apiServices.model.getRightelPack.response.GetRightelPackRespone;
import ir.trap.tractor.android.models.otherModels.pack.RightelPackModel;
import ir.trap.tractor.android.ui.adapters.pack.DetailPackAdapter;
import ir.trap.tractor.android.ui.adapters.pack.TitlePackAdapter;
import ir.trap.tractor.android.ui.base.BaseFragment;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;
import ir.trap.tractor.android.ui.fragments.simcardPack.imp.BuyPackageImpl;
import ir.trap.tractor.android.ui.fragments.simcardPack.imp.BuyPackageInteractor;
import ir.trap.tractor.android.ui.fragments.simcardPack.imp.irancell.GetPackageIrancellImpl;
import ir.trap.tractor.android.ui.fragments.simcardPack.imp.mci.PackageMciImpl;
import ir.trap.tractor.android.ui.fragments.simcardPack.imp.rightel.RightelPackImpl;
import ir.trap.tractor.android.utilities.ClearableEditText;
import library.android.eniac.utility.Utility;

/**
 * Created by Javad.Abadi on 7/14/2018.
 */
@SuppressLint("ValidFragment")
public class PackFragment
        extends BaseFragment implements
        CompoundButton.OnCheckedChangeListener, OnAnimationEndListener, View.OnFocusChangeListener
        , RightelPackImpl.OnFinishedRightelPackListener,
        PackFragmentInteractor, DetailPackAdapter.GetPackInAdapter,
        PackageMciImpl.OnFinishedPackageMciListener, BuyPackageInteractor.OnFinishedBuyPackageListener,
        GetPackageIrancellImpl.OnFinishedGetPackageIrancellListener, TextWatcher, AdapterView.OnItemSelectedListener
{

    private int myOperatorType = 0;
    private static int OPERATOR_TYPE_MCI = 0;
    private static int OPERATOR_TYPE_MTN = 1;
    private static int OPERATOR_TYPE_RIGHTEL = 2;

    private MainActionView mainView;

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
    private BuyPackageImpl mciBuyPackage;
    private BuyPackageImpl rightelPackageBuy;
    private PackageMciImpl packageMci;
    private GetPackageIrancellImpl getPackageIrancell;
    private BuyPackageImpl irancellPackageBuy;
    private String bundleId, requestId, cardNumber, cardName, amount, packageType, filterType = "", cardNumberCheck, ccv2;
    private int profileType, amountType, operatorType;
    private List<RightelPackModel> irancellPack = new ArrayList<>();
    private TitlePackAdapter packAdapter;
    //    private ArchiveCardDBModel archiveCardDBModels;
    String mobile = "";
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
    @BindView(R.id.btnMCIPackConfirm)
    CircularProgressButton btnMCIPackConfirm;
    @BindView(R.id.btnPackBackIrancell)
    CircularProgressButton btnPackBackIrancell;
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
    CircularProgressButton btnChargeBackRightel;
    @BindView(R.id.btnPackBackMci)
    CircularProgressButton btnPackBackMci;
    @BindView(R.id.btnIrancellRecent)
    CircularProgressButton btnIrancellRecent;
    @BindView(R.id.btnMciRecent)
    CircularProgressButton btnMciRecent;
    @BindView(R.id.btnRightelRecent)
    CircularProgressButton btnRightelRecent;
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
        transactionsRequest();
    }

    @OnClick(R.id.btnMciRecent)
    void mciRecentP()
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;
        }
        transactionsRequest();
    }

    @OnClick(R.id.btnRightelRecent)
    void rightelRecentP()
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;

        }
        transactionsRequest();

    }

    @OnClick(R.id.btnChargeBackRightel)
    void setRightelPack()
    {
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
        tvPackTitle.setText("خرید بسته اینترنت " + "همراه اول");
        tvPackTitle.setTextSize(18);

        lMciMobile.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInRight)
                .duration(200)
                .playOn(lMciMobile);
        llPackBackMci.setVisibility(View.GONE);
        //llMciFilter.setVisibility(View.GONE);
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

        ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.buttonColor));
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
        tvPackTitle.setText("خرید بسته اینترنت " + "همراه اول");
        tvPackTitle.setTextSize(18);

        ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.buttonColor));
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
//        mainView.needExpanded(false);
        tvPackTitle.setText("خرید بسته اینترنت " + "رایتل");
        tvPackTitle.setTextSize(18);

        ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.buttonColor));
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
     /*       irancellPackageBuy.findIrancellPackageBuyRequest(this, profileType, profileType + "",
                    Prefs.getInt("userId", 0)
                    , cardNumber, etPassCharge.getText().toString(), etMobileCharge.getText().toString(),
                    tvPackTitle.getText().toString(), Integer.valueOf(amount)
                    , ccv2, archiveCardDBModels.getExpireYear() + archiveCardDBModels.getExpireMonth());


            return;*/
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


         /*   rightelPackageBuy.findRightelBuyDataRequest(this, bundleId, requestId,
                    Prefs.getInt("userId", 0)
                    , cardNumber, etPassCharge.getText().toString(), etMobileChargeRightel.getText().toString(), tvPackTitle.getText().toString()
                    , ccv2, archiveCardDBModels.getExpireYear() + archiveCardDBModels.getExpireMonth());*/


        }
        mciBuyPackage.findBuyPackageDataRequest(this, profileType, profileType + "",
                Prefs.getInt("userId", 0)
                , cardNumber, etPassCharge.getText().toString(), mobile, tvPackTitle.getText().toString(), amount
//                , ccv2, archiveCardDBModels.getExpireYear() + archiveCardDBModels.getExpireMonth(), operatorTypeStr, rightelRequestId);
                , ccv2, "" + "", operatorTypeStr, rightelRequestId);


    }

//    @Override
//    public void cardModel(ArchiveCardDBModel archiveCardDBModels)
//    {
//        try
//        {
//            this.cardNumber = archiveCardDBModels.getNumber();
//            this.cardName = archiveCardDBModels.getfullName();
//            this.archiveCardDBModels = archiveCardDBModels;
//            if (archiveCardDBModels.getPic() == 100)
//                return;
//            cardNumberCheck = archiveCardDBModels.getNumber().substring(0, 6);
//
//            if (v != null)
//                if (!cardNumberCheck.equals("003725"))
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
//            mainView.onError(e.getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//
//
//        }
//
//    }

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
        if (!Utility.mtnValidation(etMobileCharge.getText().toString()))
        {
            mainView.showError("لطفا شماره موبایل ایرانسل را صحیح وارد نمایید.");
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
        hideSoftKeyboard(etMobileCharge);

        isMtn = true;
        isMci = false;
        isRightel = false;


    }

    @OnClick(R.id.btnChargeConfirmRightel)
    void setBtnChargeConfirmRightel()
    {


        if (!Utility.rightelValidation(etMobileChargeRightel.getText().toString()))
        {
            hideSoftKeyboard(etMobileChargeRightel);

            mainView.showError("لطفا شماره موبایل رایتل را صحیح وارد نمایید.");
            return;
        }
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;

        }

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

        if (!Utility.mciValidation(etMCINumber.getText().toString()))
        {
            mainView.showError("لطفا شماره موبایل همراه اول را صحیح وارد نمایید.");
            hideSoftKeyboard(etMCINumber);

            return;
        }
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;

        }


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
        rightelPack = new RightelPackImpl();
        rightelPackageBuy = new BuyPackageImpl();
        packageMci = new PackageMciImpl();
        mciBuyPackage = new BuyPackageImpl();
        getPackageIrancell = new GetPackageIrancellImpl();
        irancellPackageBuy = new BuyPackageImpl();
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
//        tvPackTitle.setText("خرید بسته اینترنت " + "همراه اول");
        tvPackTitle.setTextSize(18);
        btnChargeConfirm.setText("ادامه");
        btnMCIPackConfirm.setText("ادامه");
        btnChargeConfirmRightel.setText("ادامه");
        //btnBackToCharge.setText("بازگشت");
        btnChargeBackRightel.setText("بازگشت");
        btnPackBackMci.setText("بازگشت");
        btnPackBackIrancell.setText("بازگشت");
        tlPassCharge.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        tipCvv2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));

        //initDefaultOperatorView();

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

        btnMciRecent.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_calendar));
        btnIrancellRecent.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_calendar));
        btnRightelRecent.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_calendar));

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
                ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.buttonColor));
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

                ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.buttonColor));
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
                ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.buttonColor));

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


//
//        llMCICharge.setVisibility(View.VISIBLE);
//        llPassCharge.setVisibility(View.GONE);
//        llMTNCharge.setVisibility(View.GONE);
//        llRightelCharge.setVisibility(View.GONE);
//        ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
//        ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.buttonColor));
//        ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
//        isMtn = false;
//        isMci = true;
//        isRightel = false;

    }

    private int getOperatorType(String phoneNo)
    {
        String startPhoneNo = phoneNo.substring(0, 4);
        Log.e("-startPhoneNo-", startPhoneNo);


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

/*        new Handler().postDelayed(() -> {
            btnBuyCharge.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background40));
            btnChargeConfirm.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background40));
            btnMCIPackConfirm.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background40));
            btnChargeConfirmRightel.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background40));
            btnIrancellRecent.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background40));
            btnMciRecent.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background40));
            btnRightelRecent.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background40));

        }, 50);*/


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
    public void onFinishedRightelPack(GetRightelPackRespone packRespone)
    {
        btnChargeConfirmRightel.revertAnimation(this);
        btnChargeConfirmRightel.setClickable(true);

        if (packRespone.getServiceMessage().getCode() == 200)
        {
            //initDefaultOperatorView();

            requestId = packRespone.getRequestId();
            rightelRecycler.setNestedScrollingEnabled(false);
//            mainView.needExpanded(false);
            llRightelMobile.setVisibility(View.GONE);
        /*    YoYo.with(Techniques.SlideInRight)
                    .duration(200)
                    .playOn(tvSelectRightel);*/
            irancellPack.clear();

            if (packRespone.getPackages().getDaily() != null && packRespone.getPackages().getDaily().size() != 0)
                irancellPack.add(new RightelPackModel("روزانه", packRespone.getPackages().getDaily()));

            if (packRespone.getPackages().getThreeDays() != null && packRespone.getPackages().getThreeDays().size() != 0)
                irancellPack.add(new RightelPackModel("سه روزه", packRespone.getPackages().getThreeDays()));

            if (packRespone.getPackages().getTenDays() != null && packRespone.getPackages().getTenDays().size() != 0)
                irancellPack.add(new RightelPackModel("ده روزه", packRespone.getPackages().getTenDays()));

            if (packRespone.getPackages().getWeekly() != null && packRespone.getPackages().getWeekly().size() != 0)
                irancellPack.add(new RightelPackModel("هفتگی", packRespone.getPackages().getWeekly()));

            if (packRespone.getPackages().getMonthly() != null && packRespone.getPackages().getMonthly().size() != 0)
                irancellPack.add(new RightelPackModel("ماهیانه", packRespone.getPackages().getMonthly()));

            if (packRespone.getPackages().getThreeMonths() != null && packRespone.getPackages().getThreeMonths().size() != 0)
                irancellPack.add(new RightelPackModel("سه ماهه", packRespone.getPackages().getThreeMonths()));

            if (packRespone.getPackages().getSixMonths() != null && packRespone.getPackages().getSixMonths().size() != 0)
                irancellPack.add(new RightelPackModel("شش ماهه", packRespone.getPackages().getSixMonths()));

            if (packRespone.getPackages().getOneYear() != null && packRespone.getPackages().getSixMonths().size() != 0)
                irancellPack.add(new RightelPackModel("یک ساله", packRespone.getPackages().getOneYear()));
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
                            bundleId = irancellPack.get(i).getDetail().get(j).getBundleId();
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
            new Handler().postDelayed(() -> {
                spinnerRightel.performClick();
            }, 500);
        } else
        {
            mainView.showError(packRespone.getServiceMessage().getMessage());


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
//        mainView.needExpanded(true);
        llPassCharge.setVisibility(View.VISIBLE);
        tvAmountPackage.setVisibility(View.VISIBLE);
        llSelectOptaror.setVisibility(View.GONE);
        etPassCharge.requestFocus();


        YoYo.with(Techniques.SlideInRight)
                .duration(200)
                .playOn(llPassCharge);
        YoYo.with(Techniques.SlideInRight)
                .duration(200)
                .playOn(tvAmountPackage);
        tvPackTitle.setText(o.getTitle());
        bundleId = o.getBundleId();
        tvPackTitle.setTextSize(14);
        amount = o.getAmount();

        tvAmountPackage.setText("قیمت: " + Utility.priceFormat(o.getAmount()) + " ریال");


        if (isMci)
        {
            llMCICharge.setVisibility(View.GONE);
            profileType = Integer.valueOf(o.getProfileId());

        }
        if (isRightel)
        {

            llRightelCharge.setVisibility(View.GONE);
            profileType = Integer.valueOf(o.getProfileId());
        }
        if (isMtn)
        {

            llMTNCharge.setVisibility(View.GONE);
            profileType = o.getProfileId();

        }

//        if (archiveCardDBModels.isPackSe())
//        {
//            try
//            {
//                etPassCharge.setText(AESCrypt.decrypt(Prefs.getString(SingletonDiba.getInstance().getPASS_KEY(), ""), archiveCardDBModels.getPassSe()));
//                setBtnBuyCharge();
//            } catch (GeneralSecurityException e)
//            {
//                e.printStackTrace();
//            }
//
//
//        }


    }


    @Override
    public void onFinishedPackageMciPackage(GetPackageMciResponse packRespone)
    {
        // initDefaultOperatorView();

        btnMCIPackConfirm.revertAnimation(this);
        btnMCIPackConfirm.setClickable(true);
        if (packRespone.getServiceMessage().getCode() == 200)
        {
            //requestId=getPackageMciResponse.getRequestId();
            mciRecycler.setNestedScrollingEnabled(false);
//            mainView.needExpanded(false);
            llPackBackMci.setVisibility(View.GONE);
            //  llMciFilter.setVisibility(View.GONE);
            llMciSpinner.setVisibility(View.GONE);
          /*  YoYo.with(Techniques.SlideInRight)
                    .duration(200)
                    .playOn(tvSelectMci);*/
            irancellPack.clear();
            if (packRespone.getPackages().getNightly() != null && packRespone.getPackages().getNightly().size() != 0)
                irancellPack.add(new RightelPackModel("شبانه", packRespone.getPackages().getNightly()));
            if (packRespone.getPackages().getDaily() != null && packRespone.getPackages().getDaily().size() != 0)
                irancellPack.add(new RightelPackModel("روزانه", packRespone.getPackages().getDaily()));

            if (packRespone.getPackages().getThreeDays() != null && packRespone.getPackages().getThreeDays().size() != 0)
                irancellPack.add(new RightelPackModel("سه روزه", packRespone.getPackages().getThreeDays()));

            if (packRespone.getPackages().getWeekly() != null && packRespone.getPackages().getWeekly().size() != 0)
                irancellPack.add(new RightelPackModel("هفتگی", packRespone.getPackages().getWeekly()));

            if (packRespone.getPackages().getMonthly() != null && packRespone.getPackages().getMonthly().size() != 0)
                irancellPack.add(new RightelPackModel("ماهیانه", packRespone.getPackages().getMonthly()));

            if (packRespone.getPackages().getTwoMonths() != null && packRespone.getPackages().getTwoMonths().size() != 0)
                irancellPack.add(new RightelPackModel("دو ماهه", packRespone.getPackages().getTwoMonths()));

            if (packRespone.getPackages().getThreeMonths() != null && packRespone.getPackages().getThreeMonths().size() != 0)
                irancellPack.add(new RightelPackModel("سه ماهه", packRespone.getPackages().getThreeMonths()));

            if (packRespone.getPackages().getFourMonths() != null && packRespone.getPackages().getFourMonths().size() != 0)
                irancellPack.add(new RightelPackModel("چهار ماهه", packRespone.getPackages().getFourMonths()));

            if (packRespone.getPackages().getSixMonths() != null && packRespone.getPackages().getSixMonths().size() != 0)
                irancellPack.add(new RightelPackModel("شش ماهه", packRespone.getPackages().getSixMonths()));

            if (packRespone.getPackages().getOneYear() != null && packRespone.getPackages().getOneYear().size() != 0)
                irancellPack.add(new RightelPackModel("یک ساله", packRespone.getPackages().getOneYear()));

            if (packRespone.getPackages().getOther() != null && packRespone.getPackages().getOther().size() != 0)
                irancellPack.add(new RightelPackModel("موارد دیگر", packRespone.getPackages().getOther()));


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
                            bundleId = irancellPack.get(i).getDetail().get(j).getBundleId();
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
            mainView.showError(packRespone.getServiceMessage().getMessage());


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
    public void onFinishedMciBuyPackagePackage(PackageBuyResponse response)
    {
        // initDefaultOperatorView();

        btnBuyCharge.revertAnimation(this);
        btnBuyCharge.setClickable(true);

        if (response.getServiceMessage().getCode() == 200)
        {
//            ResultBuyCharge charge = new ResultBuyCharge(getActivity(), response.getCreateDate(),
//                    response.getTrnBizKey() + "", cardNumber, cardName, Utility.priceFormat(amount), true, archiveCardDBModels.getCardImage(),
//                    archiveCardDBModels.getCardNumberColor(), etMobileCharge.getText().toString(), tvPackTitle.getText().toString(), response.getRefNo());
//            charge.show(getActivity().getFragmentManager(), "packageResult");

        } else
        {
            mainView.showError(response.getServiceMessage().getMessage());

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
    public void onFinishedGetPackageIrancell(GetPackageIrancellResponse packRespone)
    {
        //initDefaultOperatorView();

        btnChargeConfirm.revertAnimation(this);
        btnChargeConfirm.setClickable(true);
        if (packRespone.getServiceMessage().getCode() == 200)
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
            if (packRespone.getIrancellPackage().getHourly() != null && packRespone.getIrancellPackage().getHourly().size() != 0)
                irancellPack.add(new RightelPackModel("ساعتی", packRespone.getIrancellPackage().getHourly()));

            if (packRespone.getIrancellPackage().getDaily() != null && packRespone.getIrancellPackage().getDaily().size() != 0)
                irancellPack.add(new RightelPackModel("روزانه", packRespone.getIrancellPackage().getDaily()));


            if (packRespone.getIrancellPackage().getThreeDays() != null && packRespone.getIrancellPackage().getThreeDays().size() != 0)
                irancellPack.add(new RightelPackModel("سه روزه", packRespone.getIrancellPackage().getThreeDays()));

            if (packRespone.getIrancellPackage().getWeekly() != null && packRespone.getIrancellPackage().getWeekly().size() != 0)
                irancellPack.add(new RightelPackModel("هفتگی", packRespone.getIrancellPackage().getWeekly()));


            if (packRespone.getIrancellPackage().getFifteenDays() != null && packRespone.getIrancellPackage().getFifteenDays().size() != 0)
                irancellPack.add(new RightelPackModel("پانزده روزه", packRespone.getIrancellPackage().getFifteenDays()));


            if (packRespone.getIrancellPackage().getOneMonth() != null && packRespone.getIrancellPackage().getOneMonth().size() != 0)
                irancellPack.add(new RightelPackModel("ماهیانه", packRespone.getIrancellPackage().getOneMonth()));

            if (packRespone.getIrancellPackage().getThreeMonths() != null && packRespone.getIrancellPackage().getThreeMonths().size() != 0)
                irancellPack.add(new RightelPackModel("سه ماهه", packRespone.getIrancellPackage().getThreeMonths()));

            if (packRespone.getIrancellPackage().getSixMonths() != null && packRespone.getIrancellPackage().getSixMonths().size() != 0)
                irancellPack.add(new RightelPackModel("شش ماهه", packRespone.getIrancellPackage().getSixMonths()));

            if (packRespone.getIrancellPackage().getOneYear() != null && packRespone.getIrancellPackage().getOneYear().size() != 0)
                irancellPack.add(new RightelPackModel("یک ساله", packRespone.getIrancellPackage().getOneYear()));

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
            new Handler().postDelayed(() -> {
                spinnerIrancell.performClick();
            }, 500);
        } else
        {
            mainView.showError(packRespone.getServiceMessage().getMessage());
        }
    }

    @Override
    public void onErrorGetPackageIrancell(String error)
    {
        mainView.showError(error);
        btnChargeConfirm.revertAnimation(this);
        btnChargeConfirm.setClickable(true);
    }

//    @Override
//    public void onFinishedIrancellPackageBuy(IrancellPackageBuyResponse response)
//    {
//        // initDefaultOperatorView();
//        etPassCharge.setText("");
//        etCvv2.setText("");
//        btnBuyCharge.revertAnimation(this);
//        btnBuyCharge.setClickable(true);
//        if (response.getServiceMessage().getCode() == 200)
//        {
//            ResultBuyCharge charge = new ResultBuyCharge(getActivity(), response.getCreateDate(),
//                    response.getTrnBizKey() + "", cardNumber, cardName, Utility.priceFormat(amount), true, archiveCardDBModels.getCardImage(),
//                    archiveCardDBModels.getCardNumberColor(), etMobileCharge.getText().toString(), tvPackTitle.getText().toString(), response.getRefNo());
//            charge.show(getActivity().getFragmentManager(), "packageResult");
//
//        } else
//        {
//            mainView.onError(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
//        }
//    }

//    @Override
//    public void onErrorIrancellPackageBuy(String error)
//    {
//        //initDefaultOperatorView();
//
//        etPassCharge.setText("");
//        etCvv2.setText("");
//        btnBuyCharge.revertAnimation(this);
//        btnBuyCharge.setClickable(true);
//        mainView.onError(error, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//    }

    private void transactionsRequest()
    {
//        List<Transaction> transactions = new ArrayList<>();
//        if (isMtn)
//        {
//            btnIrancellRecent.startAnimation();
//            btnIrancellRecent.setClickable(false);
//        }
//        if (isMci)
//        {
//            btnMciRecent.startAnimation();
//            btnMciRecent.setClickable(false);
//        }
//        if (isRightel)
//        {
//            btnRightelRecent.startAnimation();
//            btnRightelRecent.setClickable(false);
//        }
//
//
//        GetHistoryTransactionsRequest request = new GetHistoryTransactionsRequest();
//        request.setTypeTransaction(4);
//        request.setUserId(Prefs.getInt("userId", 0));
//        SingletonService.getInstance().getHistoryTransactionsService().GetHistoryTransactionsService(new OnServiceStatus<GetHistoryTransactionsResponse>()
//        {
//            @Override
//            public void onReady(GetHistoryTransactionsResponse response)
//            {
//
//
//                btnIrancellRecent.revertAnimation(PackFragment.this);
//                btnIrancellRecent.setClickable(true);
//
//                btnMciRecent.revertAnimation(PackFragment.this);
//                btnMciRecent.setClickable(true);
//
//                btnRightelRecent.revertAnimation(PackFragment.this);
//                btnRightelRecent.setClickable(true);
//
//                try
//                {
//                    if (response.getServiceMessage().getCode() == 200)
//                    {
//                        transactions.clear();
//
//
//                        if (isMtn)
//                        {
//
//                            for (int i = 0; i < response.getTransactions().size(); i++)
//                            {
//                                if (response.getTransactions().get(i).getInternetPackageVm().getTypeOperatorId() == 1)
//                                    transactions.add(response.getTransactions().get(i));
//
//                            }
//
//
//                        }
//                        if (isMci)
//                        {
//                            for (int i = 0; i < response.getTransactions().size(); i++)
//                            {
//                                if (response.getTransactions().get(i).getInternetPackageVm().getTypeOperatorId() == 2)
//                                    transactions.add(response.getTransactions().get(i));
//
//                            }
//
//
//                        }
//                        if (isRightel)
//                        {
//                            for (int i = 0; i < response.getTransactions().size(); i++)
//                            {
//                                if (response.getTransactions().get(i).getInternetPackageVm().getTypeOperatorId() == 3)
//                                    transactions.add(response.getTransactions().get(i));
//
//                            }
//
//
//                        }
//                        mainView.transactionsExpand(transactions);
//
//
//                    } else
//                    {
//                        mainView.onError(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
//
//                    }
//
//                } catch (Exception e)
//                {
//                    mainView.onError(e.getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//                }
//            }
//
//            @Override
//            public void onError(String message)
//            {
//                mainView.onError(message, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
//                btnIrancellRecent.revertAnimation(PackFragment.this);
//                btnIrancellRecent.setClickable(true);
//                btnMciRecent.revertAnimation(PackFragment.this);
//                btnMciRecent.setClickable(true);
//                btnRightelRecent.revertAnimation(PackFragment.this);
//                btnRightelRecent.setClickable(true);
//
//
//            }
//        }, request);


    }


    @Override
    public void onStart()
    {
        super.onStart();
//        EventBus.getDefault().register(this);
    }


    public void onSelectContact(String number, String name)
    {
        //todo change this
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
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(Transaction event)
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
