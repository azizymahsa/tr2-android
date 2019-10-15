package ir.trap.tractor.android.ui.fragments.simcardCharge;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
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

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputLayout;
import com.pixplicity.easyprefs.library.Prefs;
import com.scottyab.aescrypt.AESCrypt;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.model.mobileCharge.response.MobileChargeResponse;
import ir.trap.tractor.android.ui.base.BaseFragment;
import ir.trap.tractor.android.ui.dialogs.ResultBuyCharge;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;
import ir.trap.tractor.android.ui.fragments.simcardCharge.imp.irancell.IrancellBuyImpl;
import ir.trap.tractor.android.ui.fragments.simcardCharge.imp.mci.MciBuyImpl;
import ir.trap.tractor.android.ui.fragments.simcardCharge.imp.mci.MciBuyInteractor;
import ir.trap.tractor.android.ui.fragments.simcardCharge.imp.rightel.RightelBuyImpl;
import ir.trap.tractor.android.utilities.ClearableEditText;
import ir.trap.tractor.android.utilities.Logger;
import ir.trap.tractor.android.utilities.NumberTextWatcher;
import library.android.eniac.utility.Utility;

/**
 * Created by Javad.Abadi on 7/14/2018.
 */
@SuppressLint("ValidFragment")
public class ChargeFragment extends BaseFragment 
        implements IrancellBuyImpl.OnFinishedIrancellBuyListener,
        ChargeFragmentInteractor, CompoundButton.OnCheckedChangeListener, OnAnimationEndListener, View.OnFocusChangeListener,
        RightelBuyImpl.OnFinishedRightelBuyListener,
        MciBuyInteractor.OnFinishedMciBuyInListener, TextWatcher
{
    private int operatorType = 0;

    private static int OPERATOR_TYPE_MCI = 0;
    private static int OPERATOR_TYPE_MTN = 1;
    private static int OPERATOR_TYPE_RIGHTEL = 2;

    private MainActionView mainView;

    private static final int SIMCARD_TYPE_ETEBARI = 0;
    private static final int SIMCARD_TYPE_DAEMI = 1;
    private int simcardType = 0;
    private String amount;
    private String mobile;


    public ChargeFragment()
    {

    }

    public static ChargeFragment newInstance(MainActionView mainView)
    {
        ChargeFragment f = new ChargeFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    private String[] irancellFilter = {"اعتباري", "دائمي"};

    private View v;
    private IrancellBuyImpl irancellBuy;
    private MciBuyImpl mciBuy;
    private RightelBuyImpl rightelBuy;
    private String cardNumber, cardName, chargeType = "DIRECT", cardNumberCheck, ccv2, chargeName = "";
    private int profileType = 20;
    private int rightelType = 2;
    private boolean isMci = true, isMtn = false, isRightel = false, isInitView = true;
//    private ArchiveCardDBModel archiveCardDBModels;


    @BindView(R.id.tvAmpuntPassCharge)
    TextView tvAmpuntPassCharge;
    @BindView(R.id.tvDescriptionSelectedOperator)
    TextView tvDescriptionSelectedOperator;
    @BindView(R.id.ivSelectedOperator)
    CircleImageView ivSelectedOperator;
    @BindView(R.id.llOperatorImages)
    LinearLayout llOperatorImages;

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
    @BindView(R.id.btnMCIChargeConfirm)
    CircularProgressButton btnMCIChargeConfirm;

    @BindView(R.id.spinnerIrancell)
    Spinner spinnerIrancell;


    @BindView(R.id.btnBuyCharge)
    CircularProgressButton btnBuyCharge;
    @BindView(R.id.etChargeAmount)
    ClearableEditText etChargeAmount;
    @BindView(R.id.etCvv2)
    ClearableEditText etCvv2;

    @BindView(R.id.btnChargeConfirmRightel)
    CircularProgressButton btnChargeConfirmRightel;

    @BindView(R.id.btnIrancellRecent)
    CircularProgressButton btnIrancellRecent;
    @BindView(R.id.btnMciRecent)
    CircularProgressButton btnMciRecent;
    @BindView(R.id.btnRightelRecent)
    CircularProgressButton btnRightelRecent;


    @BindView(R.id.etMobileChargeRightel)
    ClearableEditText etMobileChargeRightel;
    @BindView(R.id.etChargeAmountRightel)
    ClearableEditText etChargeAmountRightel;


    @BindView(R.id.etMobileCharge)
    ClearableEditText etMobileCharge;
    @BindView(R.id.etMCINumber)
    ClearableEditText etMCINumber;
    @BindView(R.id.etMCIAmount)
    ClearableEditText etMCIAmount;
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
    @BindView(R.id.tilMIrancell)
    TextInputLayout tilMIrancell;
    @BindView(R.id.tilMMci)
    TextInputLayout tilMMci;
    @BindView(R.id.tilMRightel)

    TextInputLayout tilMRightel;
    @BindView(R.id.tipCvv2)
    TextInputLayout tipCvv2;
    @BindView(R.id.nested)
    NestedScrollView nested;

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
    void irancellRecent()
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;

        }

        transactionsRequest();

    }

    @OnClick(R.id.btnMciRecent)
    void mciRecent()
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;

        }

        transactionsRequest();

    }

    @OnClick(R.id.btnRightelRecent)
    void rightelRecent()
    {
        if (!Utility.isNetworkAvailable())
        {
            mainView.onInternetAlert();
            return;

        }

        transactionsRequest();

    }


    @OnClick(R.id.flIrancell)
    void irancell()
    {

        //mainView.needExpanded(false);

        tvChargeTitle.setText("خرید شارژ آنلاین " + "ایرانسل");
        ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.buttonColor));
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
/*        etMCINumber.setText(etMobileCharge.getText());
        etMobileChargeRightel.setText(etMobileCharge.getText());*/

    }


    @OnClick(R.id.flHamraheAval)
    void hamraheAval()
    {

        //mainView.needExpanded(false);

        rlIrancellSpinner.setVisibility(View.INVISIBLE);

        tvChargeTitle.setText("خرید شارژ آنلاین " + "همراه اول");
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
        YoYo.with(Techniques.SlideInUp)
                .duration(200)
                .playOn(llMCICharge);
        etMCIAmount.setText("");
        isMtn = false;
        isMci = true;
        isRightel = false;
       /* etMobileChargeRightel.setText(etMCINumber.getText());
        etMobileCharge.setText(etMCINumber.getText());
*/

    }

    @OnClick(R.id.flRightel)
    void rightel()
    {
        //mainView.needExpanded(false);

        rlIrancellSpinner.setVisibility(View.INVISIBLE);

        tvChargeTitle.setText("خرید شارژ آنلاین " + "رایتل");
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
        etChargeAmountRightel.setText("");
        isMtn = false;
        isMci = false;
        isRightel = true;
 /*       etMobileCharge.setText(etMCINumber.getText());
        etMCINumber.setText(etMCINumber.getText());
        etMobileChargeRightel.setText(etMCINumber.getText());*/
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

        btnBuyCharge.startAnimation();
        btnBuyCharge.setClickable(false);


        if (isMtn)
        {
            irancellBuy.findDataIrancellBuyRequest(this, Prefs.getInt("userId", 0),
                    profileType, Integer.valueOf(etChargeAmount.getText().toString().replaceAll(",", "")),
                    cardNumber, etPassCharge.getText().toString(), etMobileCharge.getText().toString(), ccv2,
//                    archiveCardDBModels.getExpireYear() + archiveCardDBModels.getExpireMonth(), simcardType);
                    "", simcardType);
            return;
        }
        if (isMci)
        {
            mciBuy.findDataMciBuyInRequest(this, Prefs.getInt("userId", 0),
                    chargeType, Integer.valueOf(etMCIAmount.getText().toString().replaceAll(",", "")),
                    cardNumber, etPassCharge.getText().toString(), etMCINumber.getText().toString(), ccv2,
//                    archiveCardDBModels.getExpireYear() + archiveCardDBModels.getExpireMonth());
                    "");
            return;


        }
        if (isRightel)
        {
            rightelBuy.findRightelBuyDataRequest(this, Prefs.getInt("userId", 0),
                    rightelType + "", Integer.valueOf(etChargeAmountRightel.getText().toString().replaceAll(",", "")),
                    cardNumber, etPassCharge.getText().toString(), etMobileChargeRightel.getText().toString(), ccv2,
//                    archiveCardDBModels.getExpireYear() + archiveCardDBModels.getExpireMonth());
                    "");
        }


    }


    @OnClick(R.id.btnBackToCharge)
    void setBtnBackToCharge()
    {
        if (isMtn)
        {
            //mainView.needExpanded(false);
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
            //mainView.needExpanded(false);
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
            //mainView.needExpanded(false);
            llOperatorImages.setVisibility(View.VISIBLE);
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
//        Tools.showToast(getActivity(), "simcardType = " + simcardType);
        if (!Utility.mtnValidation(etMobileCharge.getText().toString()))
        {
            mainView.showError("لطفا شماره موبایل ایرانسل را صحیح وارد نمایید.");
            return;
        }
        if (TextUtils.isEmpty(etChargeAmount.getText().toString()))
        {
            mainView.showError("لطفا مبلغ را وارد نمایید.");
            return;
        }

        if (Integer.valueOf(etChargeAmount.getText().toString().replaceAll(",", "")) < 1000)
        {
            mainView.showError("حداقل مبلغ در این قسمت 1000 ریال می باشد.");
            return;
        }
        isMtn = true;
        isMci = false;
        isRightel = false;
        //mainView.needExpanded(true);
        llMTNCharge.setVisibility(View.GONE);
        setDataLayoutPassCharge();
        llPassCharge.setVisibility(View.VISIBLE);
        llOperatorImages.setVisibility(View.GONE);
        etPassCharge.requestFocus();
        YoYo.with(Techniques.SlideInRight)
                .duration(200)
                .playOn(llPassCharge);


//        if (archiveCardDBModels.isChargeSe())
//        {
//            try
//            {
//                etPassCharge.setText(AESCrypt.decrypt(Prefs.getString(SingletonDiba.getInstance().getPASS_KEY(), ""), archiveCardDBModels.getPassSe()));
//                etPassCharge.setText("");
//                setBtnBuyCharge();
//
//            }
//            catch (GeneralSecurityException e)
//            {
//                e.printStackTrace();
//            }
//
//
//        }
    }

    private void setDataLayoutPassCharge() {
        if (isMtn) {
            amount=etChargeAmount.getText().toString();
            ivSelectedOperator.setImageResource(R.drawable.irancell);
            mobile=etMobileCharge.getText().toString();

        }else if (isMci){
            amount=etMCIAmount.getText().toString();
            ivSelectedOperator.setImageResource(R.drawable.hamrahe_aval);
            mobile=etMCINumber.getText().toString();
        }else if (isRightel){
            amount=etChargeAmountRightel.getText().toString();
            ivSelectedOperator.setImageResource(R.drawable.rightel);
            mobile=etMobileChargeRightel.getText().toString();
        }
        tvAmpuntPassCharge.setText(amount);
        tvDescriptionSelectedOperator.setText("با انجام این پرداخت ، مبلغ "+amount+  " ریال بابت شارژ شماره " +mobile+" از حساب شما کسر خواهد شد.");
        }

    @OnClick(R.id.btnChargeConfirmRightel)
    void setBtnChargeConfirmRightel()
    {
        if (!Utility.rightelValidation(etMobileChargeRightel.getText().toString()))
        {
            mainView.showError("لطفا شماره موبایل رایتل را صحیح وارد نمایید.");
            return;
        }

        if (TextUtils.isEmpty(etChargeAmountRightel.getText().toString()))
        {
            mainView.showError("لطفا مبلغ را وارد نمایید.");
            return;
        }

       /* if (!cardNumberCheck.equals("003725") && Integer.valueOf(etChargeAmountRightel.getText().toString().replaceAll(",", "")) < 1000)
        {
            mainView.showError("حداقل مبلغ در این قسمت 1000 ریال می باشد.");
            return;
        }
        if (cardNumberCheck.equals("003725") && Integer.valueOf(etChargeAmountRightel.getText().toString().replaceAll(",", "")) == 0)
        {
            mainView.showError("حداقل مبلغ در این قسمت 1 ریال می باشد.");
            return;
        }*/
        isMtn = false;
        isMci = false;
        isRightel = true;
        //mainView.needExpanded(true);
        llRightelCharge.setVisibility(View.GONE);
        setDataLayoutPassCharge();

        llPassCharge.setVisibility(View.VISIBLE);
        llOperatorImages.setVisibility(View.GONE);
        etPassCharge.requestFocus();
        YoYo.with(Techniques.SlideInRight)
                .duration(200)
                .playOn(llPassCharge);
//        if (archiveCardDBModels.isChargeSe())
//        {
//            try
//            {
//                etPassCharge.setText(AESCrypt.decrypt(Prefs.getString(SingletonDiba.getInstance().getPASS_KEY(), ""), archiveCardDBModels.getPassSe()));
//                setBtnBuyCharge();
//
//            } catch (GeneralSecurityException e)
//            {
//                e.printStackTrace();
//            }
//
//
//        }

    }

    @OnClick(R.id.etMCIAmount)
    void setEtMCIAmount()
    {
//        mainView.showPricePanel();
        hideSoftKeyboard(etMCIAmount);
    }

    @OnClick(R.id.etChargeAmountRightel)
    void setEtChargeAmountRightel()
    {
        //  mainView.showPricePanel();
        // hideSoftKeyboard(etChargeAmountRightel);
    }

    @OnClick(R.id.btnMCIChargeConfirm)
    void setBtnMCIChargeConfirm()
    {
        if (!Utility.mciValidation(etMCINumber.getText().toString()))
        {
            mainView.showError("لطفا شماره موبایل همراه اول را صحیح وارد نمایید.");

            return;
        }

        if (TextUtils.isEmpty(etMCIAmount.getText().toString()))
        {
            mainView.showError("لطفا مبلغ را وارد نمایید.");
            return;
        }
        hideSoftKeyboard(etMCINumber);
        etPassCharge.requestFocus();

        isMci = true;
        isMtn = false;
        isRightel = false;
        //mainView.needExpanded(true);
        llMCICharge.setVisibility(View.GONE);
        setDataLayoutPassCharge();

        llOperatorImages.setVisibility(View.GONE);
        llPassCharge.setVisibility(View.VISIBLE);
        YoYo.with(Techniques.SlideInRight)
                .duration(200)
                .playOn(llPassCharge);
//        if (archiveCardDBModels.isChargeSe())
//        {
//            try
//            {
//                etPassCharge.setText(AESCrypt.decrypt(Prefs.getString(SingletonDiba.getInstance().getPASS_KEY(), ""), archiveCardDBModels.getPassSe()));
//                setBtnBuyCharge();
//
//            } catch (GeneralSecurityException e)
//            {
//                e.printStackTrace();
//            }
//
//
//        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (v != null)
            return v;
        v = inflater.inflate(R.layout.fragment_charge, container, false);
        irancellBuy = new IrancellBuyImpl();
        mciBuy = new MciBuyImpl();
        rightelBuy = new RightelBuyImpl();
        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        etCvv2.setText("");
        etPassCharge.setText("");
        etChargeAmount.setText("");
        etMCIAmount.setText("");
        etChargeAmountRightel.setText("");
        if (isInitView)
        {
            isInitView = false;
            initView();
        }

        ArrayAdapter<String> adapterIrancell = new ArrayAdapter<String>(getActivity(),
                R.layout.simple_spinner_item, irancellFilter);
        adapterIrancell.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        spinnerIrancell.setAdapter(adapterIrancell);
        spinnerIrancell.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (spinnerIrancell.getSelectedItemPosition() == 0)
                {
                    simcardType = SIMCARD_TYPE_ETEBARI;
                }
                else
                {
                    simcardType = SIMCARD_TYPE_DAEMI;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {}
        });

    }

    @Override
    public void onStop()
    {
        super.onStop();
        etPassCharge.setText("");
//        EventBus.getDefault().unregister(this);
    }


    private void initView()
    {
        btnChargeConfirmRightel.setText("ادامه");
        btnMCIChargeConfirm.setText("ادامه");
        btnChargeConfirm.setText("ادامه");
       // btnBackToCharge.setText("بازگشت");
        tlPassCharge.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        tipCvv2.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        etChargeAmount.addTextChangedListener(new NumberTextWatcher(etChargeAmount));
        etChargeAmountRightel.addTextChangedListener(new NumberTextWatcher(etChargeAmountRightel));
        etMCIAmount.addTextChangedListener(new NumberTextWatcher(etMCIAmount));
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

        initDefaultOperatorView();

        try
        {

            if (!cardNumberCheck.equals("003725"))
            {
                llCvv2.setVisibility(View.VISIBLE);

            }
        }
        catch (NullPointerException e)
        {

        }
        btnIrancellRecent.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_calendar));
        btnMciRecent.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_calendar));
        btnRightelRecent.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_calendar));

    }

    private void initDefaultOperatorView()
    {
//        operatorType = getOperatorType(Prefs.getString("mobile", ""));
        operatorType = getOperatorType("09121234567");

        switch (operatorType)
        {
            case 0:
            {
                tvChargeTitle.setText("خرید شارژ آنلاین " + "همراه اول");

                llMCICharge.setVisibility(View.VISIBLE);
                llMTNCharge.setVisibility(View.GONE);
                llRightelCharge.setVisibility(View.GONE);

                rlIrancellSpinner.setVisibility(View.INVISIBLE);

                ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.buttonColor));
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
                tvChargeTitle.setText("خرید شارژ آنلاین " + "ایرانسل");

                llMCICharge.setVisibility(View.GONE);
                llMTNCharge.setVisibility(View.VISIBLE);
                llRightelCharge.setVisibility(View.GONE);

                new Handler().postDelayed(() ->
                {
                    rlIrancellSpinner.setVisibility(View.VISIBLE);


                }, 250);

                ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.buttonColor));
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
            case 2:
            {
                tvChargeTitle.setText("خرید شارژ آنلاین " + "رایتل");

                llMCICharge.setVisibility(View.GONE);
                llMTNCharge.setVisibility(View.GONE);
                llRightelCharge.setVisibility(View.VISIBLE);

                rlIrancellSpinner.setVisibility(View.INVISIBLE);

                ivIrancell.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
                ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.buttonColor));

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
//        ivHamraheAval.setBorderColor(ContextCompat.getColor(getActivity(), R.color.buttonColor));
//        ivRightel.setBorderColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
//        isMtn = false;
//        isMci = true;
//        isRightel = false;
        etMCINumber.setText(Prefs.getString("mobile", ""));
        etMobileCharge.setText(Prefs.getString("mobile", ""));
        etMobileChargeRightel.setText(Prefs.getString("mobile", ""));
       // etMCIAmount.setOnFocusChangeListener(this);
/*        etMCINumber.setOnFocusChangeListener(this);
        etMobileCharge.setOnFocusChangeListener(this);
        etMobileChargeRightel.setOnFocusChangeListener(this);*/
        //   etChargeAmountRightel.setOnFocusChangeListener(this);
       // etMCIAmount.setInputType(InputType.TYPE_NULL);
        // etChargeAmountRightel.setInputType(InputType.TYPE_NULL);

        etMobileCharge.addTextChangedListener(this);
        etMCINumber.addTextChangedListener(this);
        etMobileChargeRightel.addTextChangedListener(this);

    }

    private int getOperatorType(String phoneNo)
    {
        String startPhoneNo = phoneNo.substring(0, 4);
        Logger.e("-startPhoneNo-", startPhoneNo);

        String[] typeMCI_No = {"0990", "0991", "0910", "0911", "0912", "0913", "0914", "0915", "0916", "0917", "0918", "0919"};
        String[] typeMTN_No = {"0901", "0902", "0903", "0905", "0930", "0933", "0935", "0936", "0937", "0938", "0939"};
        String[] typeRightel_No = {"0920", "0921", "0922" };

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


    @Override
    public void onFinishedIrancellBuy(MobileChargeResponse response, String mobileChargeNo)
    {
        etPassCharge.setText("");
        btnBuyCharge.revertAnimation(this);
        btnBuyCharge.setClickable(true);
        if (response.getServiceMessage().getCode() == 200)
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
        }
        else
        {
            mainView.showError(response.getServiceMessage().getMessage());
            hideSoftKeyboard(etPassCharge);
        }
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
                chargeType = "DIRECT";
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
                rightelType = 0;
                chargeName = "شارژ عادی";

                break;
            case R.id.rbSpecialChargeRightel:
                if (rbSpecialChargeRightel.isChecked())
                    rbNormalChargeRightel.setChecked(false);
                rightelType = 1;
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
            hideSoftKeyboard(etMCIAmount);
            hideSoftKeyboard(etChargeAmountRightel);
            hideSoftKeyboard(etChargeAmount);

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
        if (mciBuyResponse.getServiceMessage().getCode() == 200)
        {
            initDefaultOperatorView();
            ResultBuyCharge charge = new ResultBuyCharge(getActivity(), mciBuyResponse.getCreateDate(), mciBuyResponse.getTrnBizKey(),
                    cardNumber, cardName, etMCIAmount.getText().toString(), false,
//                    archiveCardDBModels.getCardImage(), archiveCardDBModels.getCardNumberColor(),
                    "", "",
                    mobileChargeNo,
//                    tvChargeTitle.getText().toString() + " ( " + chargeName + " ) ",mciBuyResponse.getRefNo());
                    getString(R.string.buyChargeMCITitle) + " ( " + chargeName + " ) ",mciBuyResponse.getRefNo());
            charge.show(getActivity().getSupportFragmentManager(), "chargeResult");
        }
        else
        {

            mainView.showError(mciBuyResponse.getServiceMessage().getMessage());
            hideSoftKeyboard(etPassCharge);


        }
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
        if (response.getServiceMessage().getCode() == 200)
        {
            initDefaultOperatorView();
            ResultBuyCharge charge = new ResultBuyCharge(getActivity(), response.getCreateDate(),
                    response.getTrnBizKey(), cardNumber, cardName, etChargeAmountRightel.getText().toString(),
//                    false, archiveCardDBModels.getCardImage(), archiveCardDBModels.getCardNumberColor(),
                    false, "", "",
                    mobileChargeNo,
                    getString(R.string.buyChargeRightelTitle) + " ( " + chargeName + " ) ",response.getRefNo());
            charge.show(getActivity().getSupportFragmentManager(), "chargeResult");
        }
        else
        {
            mainView.showError(response.getServiceMessage().getMessage());
            hideSoftKeyboard(etPassCharge);

        }

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


    public void onSelectContact(String number, String name)
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
    }

    private void transactionsRequest()
    {
//        List<Transaction> transactions = new ArrayList<>();
//
//
//        if (isMtn)
//        {
//            btnIrancellRecent.startAnimation();
//            btnIrancellRecent.setClickable(false);
//
//
//        }
//        if (isMci)
//        {
//            btnMciRecent.startAnimation();
//            btnMciRecent.setClickable(false);
//
//
//        }
//        if (isRightel)
//        {
//
//            btnRightelRecent.startAnimation();
//            btnRightelRecent.setClickable(false);
//
//
//        }
//
//
//        GetHistoryTransactionsRequest request = new GetHistoryTransactionsRequest();
//        request.setTypeTransaction(3);
//        request.setUserId(Prefs.getInt("userId", 0));
//        SingletonService.getInstance().getHistoryTransactionsService().GetHistoryTransactionsService(new OnServiceStatus<GetHistoryTransactionsResponse>()
//        {
//            @Override
//            public void onReady(GetHistoryTransactionsResponse response)
//            {
//
//
//                btnIrancellRecent.revertAnimation(ChargeFragment.this);
//                btnIrancellRecent.setClickable(true);
//
//                btnMciRecent.revertAnimation(ChargeFragment.this);
//                btnMciRecent.setClickable(true);
//
//                btnRightelRecent.revertAnimation(ChargeFragment.this);
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
//                                if (response.getTransactions().get(i).getChargeVm().getTypeOperatorId() == 1)
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
//                                if (response.getTransactions().get(i).getChargeVm().getTypeOperatorId() == 2)
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
//                                if (response.getTransactions().get(i).getChargeVm().getTypeOperatorId() == 3)
//                                    transactions.add(response.getTransactions().get(i));
//
//                            }
//
//
//                        }
//
//                        mainView.transactionsExpand(transactions);
//
//
//                    } else
//                    {
//                        mainView.showError(response.getServiceMessage().getMessage());
//
//                    }
//
//                } catch (Exception e)
//                {
//                    mainView.showError(e.getMessage());
//                }
//            }
//
//            @Override
//            public void onError(String message)
//            {
//                mainView.showError(message);
//                btnIrancellRecent.revertAnimation(ChargeFragment.this);
//                btnIrancellRecent.setClickable(true);
//                btnMciRecent.revertAnimation(ChargeFragment.this);
//                btnMciRecent.setClickable(true);
//                btnRightelRecent.revertAnimation(ChargeFragment.this);
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

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(Transaction event)
//    {
//        if (event.getTypeTransactionId() != 3)
//            return;
//        if (isMtn)
//        {
//            etMobileCharge.setText(event.getChargeVm().getMobileNumber());
//            etChargeAmount.setText(event.getAmount() + "");
//
//
//            return;
//        }
//        if (isMci)
//        {
//            etMCINumber.setText(event.getChargeVm().getMobileNumber());
//            etMCIAmount.setText(event.getAmount() + "");
//
//            return;
//
//
//        }
//        if (isRightel)
//        {
//            etMobileChargeRightel.setText(event.getChargeVm().getMobileNumber());
//            etChargeAmountRightel.setText(event.getAmount() + "");
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
        Log.e("onTextChanged", charSequence.toString());


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
    public void onDestroyView()
    {
        initDefaultOperatorView();
        super.onDestroyView();
    }
}
