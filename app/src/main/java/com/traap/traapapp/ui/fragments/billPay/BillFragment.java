package com.traap.traapapp.ui.fragments.billPay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputLayout;
import com.pixplicity.easyprefs.library.Prefs;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.billCode.BillCodeResponse;
import com.traap.traapapp.apiServices.model.billElectricity.BillElectricityRequest;
import com.traap.traapapp.apiServices.model.billElectricity.BillElectricityResponse;
import com.traap.traapapp.apiServices.model.billPayment.BillPaymentRequest;
import com.traap.traapapp.apiServices.model.billPayment.BillPaymentResponse;
import com.traap.traapapp.apiServices.model.billPhone.BillPhoneRequest;
import com.traap.traapapp.apiServices.model.billPhone.BillPhoneResponse;
import com.traap.traapapp.apiServices.model.contact.OnSelectContact;
import com.traap.traapapp.apiServices.model.getBillCodePayCode.GetBillCodePayCodeResponse;
import com.traap.traapapp.apiServices.model.getInfoPhoneBill.LstPhoneBill;
import com.traap.traapapp.apiServices.model.getMyBill.Bills;
import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.BarcodeType;
import com.traap.traapapp.enums.LeagueTableParent;
import com.traap.traapapp.enums.MediaPosition;
import com.traap.traapapp.enums.PredictPosition;
import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.models.dbModels.ArchiveCardDBModel;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.models.otherModels.paymentInstance.SimPackPaymentInstance;
import com.traap.traapapp.models.otherModels.qrModel.QrModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.adapters.MyBillsAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.bill.BillCodeInfoDialog;
import com.traap.traapapp.ui.fragments.main.BuyTicketAction;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.simcardCharge.OnClickContinueSelectPayment;
import com.traap.traapapp.utilities.ClearableEditText;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

/**
 * Created by Javad.Abadi on 1/31/2019.
 */
@SuppressLint("ValidFragment")
public class BillFragment extends BaseFragment implements MainActionView, OnAnimationEndListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener
{
    private Context context;
    private View v;
    private LinearLayout cvBarcode;
    private ClearableEditText etMobile, etTitleAddMyBill,etBillCode;
    private CircularProgressButton btnConfirm, btnAddBill, btnBillInfoEnd,btnPhoneInfo,btnCancelTerm,btnOkTerm;
    private View  btnContact;
    private RecyclerView rvInfo, rvMyBills;
    private LinearLayout llDetailPayment, llCardDetail, llBill, llCvv2, rlBillList, llTitleAddMyBill,llContinueBill,llBarcode;
    private List<LstPhoneBill> lstPhoneBills = new ArrayList<>();
   // private BillInfoAdapter billInfoAdapter;
    private String billId, payId;
    private CircularProgressButton  btnPassConfirm;
    private View btnBackToList;
    private TextInputLayout etLayoutPass, etLayoutCvv;
    private EditText etPass, etCvv2;
    private ArchiveCardDBModel archiveCardDBModels;
    private String cardNumberCheck, billTitle;
    private int amount;
    private String number;
    private LinearLayout llBillInfo, llBillPayment, llMobileNumber, llbillCodePayCodeNow, llbillCodePayCodeEnd, llPaymentBarcode, tvBillEnd, llAddToMyBills, llDateFirst, llDateEnd, llbillFirst,llSelectTermBill,llBillCode,llPhone;
    private ImageView ivBillInfo, ivBillPayment;
    private TextView tvBillPayment, tvBillInfo, tvPrice, tvBillName, tvBillName2, tvBillDescription, tvBillsTitle, tvBillCodeFirst, tvDateFirst, tvAmountFirst, tvPayCodeFirst, tvBillCodeEnd, tvDateEnd, tvAmountEnd, tvPayCodeEnd,tvTitleTerm,tvPhoneNumberTerm,tvAmountTerm2,tvAmountTerm1;
    private ImageView ivBillLogo;
    private NestedScrollView nested;
    private CheckBox checkBoxTerm2,checkBoxTerm1;
    boolean isMobile = false;
    private String phone, contactPhone;
    boolean mianDore = false, payanDore = false;
    private MainActionView mainView;
    private ArrayList<String> bills;
    private Integer idSelectedBillType;
    private TextInputLayout etMobileHint;
    private List<Bills> billsList = new ArrayList<>();
    private MyBillsAdapter myBillsAdapter;
    private SlidingUpPanelLayout upPanelLayout;
    private RadioButton rbBillEnd, rbBillFirst;
    private int idBillNowEndSelected = -1;
    private CheckBox cbAddToMyBill;
    private TextView tvBillTitle;
    private Integer backState=2;
    private View mToolbar;
    private TextView tvUserName;
    private TextView tvHeaderPopularNo;
    private TextView tvTitle;

    private BillPhoneResponse responseTerm;
    private String termText;
    private String gweBillId="";
    private String billCode;
    private String qrCode="";


    public BillFragment()
    {

    }

    public static BillFragment newInstance(MainActionView mainView, String billTitle, int idSelectedBillType,String qrCode)
    {
        BillFragment f = new BillFragment();
        f.setMainView(mainView);

        Bundle args = new Bundle();
        args.putString("TITLE", billTitle);
        args.putInt("ID_BILL_TYPE", idSelectedBillType);
        args.putString("qrCode",qrCode);

        f.setArguments(args);

        return f;
    }

    public static Fragment newInstance(MainActionView mainView, Integer idBill)
    {
        BillFragment f = new BillFragment();
        f.setMainView(mainView);

        Bundle args = new Bundle();
        args.putInt("ID_BILL_TYPE", idBill);

         f.setArguments(args);

        return f;    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
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
    public void onStart()
    {
        super.onStart();
        EventBus.getDefault().register(this);
        upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        if (v != null)
            v = null;

        v = inflater.inflate(R.layout.bill_fragment, container, false);



        initView();
        barcode(qrCode);

        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        etCvv2.setText("");
        etPass.setText("");
        //etBillId.setText("");
        initSpeesh();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        etMobile.setText("");
        etCvv2.setText("");
        etPass.setText("");
        phone = null;

    }

    private void initView()
    {
        if (getArguments() != null)
        {
            idSelectedBillType = getArguments().getInt("ID_BILL_TYPE");
            billTitle = getArguments().getString("TITLE");

            qrCode=getArguments().getString("qrCode");

            Prefs.putString("TITLE_BILL", billTitle);
            Prefs.putInt("ID_BILL_TYPE", idSelectedBillType);

        }

        mToolbar = v.findViewById(R.id.toolbar);
        FrameLayout flLogoToolbar = v.findViewById(R.id.flLogoToolbar);
        flLogoToolbar.setOnClickListener(v ->
        {
            mainView.backToMainFragment();

        });

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView ->
                        mainView.backToAllServicePackage(2,6)
                // onClickContinueBuyChargeListener.onBackClicked()
        );
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText(billTitle);
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        llBillCode=v.findViewById(R.id.llBillCode);
        llPhone=v.findViewById(R.id.llPhone);
        etBillCode=v.findViewById(R.id.etBillCode);
        llBarcode=v.findViewById(R.id.llBarcode);
        llBarcode.setOnClickListener(this);
        cvBarcode=v.findViewById(R.id.cvBarcode);
       // cvBarcode.setOnClickListener(this);

        tvAmountTerm1=v.findViewById(R.id.tvAmountTerm1);
        tvAmountTerm2=v.findViewById(R.id.tvAmountTerm2);
        checkBoxTerm1=v.findViewById(R.id.checkBoxTerm1);
        checkBoxTerm2=v.findViewById(R.id.checkBoxTerm2);
        btnCancelTerm=v.findViewById(R.id.btnCancelTerm);
        btnOkTerm=v.findViewById(R.id.btnOkTerm);
        btnCancelTerm.setOnClickListener(this);
        btnOkTerm.setOnClickListener(this);

        tvBillTitle = v.findViewById(R.id.tvBillTitle);
        tvBillsTitle = v.findViewById(R.id.tvBillsTitle);
        rlBillList = v.findViewById(R.id.rlBillList);
        upPanelLayout = v.findViewById(R.id.slidingLayout);
        nested = v.findViewById(R.id.nested);
        btnContact = v.findViewById(R.id.btnContact);
        etMobile = v.findViewById(R.id.etMobile);
        etMobileHint = v.findViewById(R.id.etMobileHint);
        btnPhoneInfo = v.findViewById(R.id.btnPhoneInfo);
        llSelectTermBill=v.findViewById(R.id.llSelectTermBill);
        rvInfo = v.findViewById(R.id.rvInfo);
        etLayoutCvv = v.findViewById(R.id.etLayoutCvv);
        btnBackToList = v.findViewById(R.id.btnBackToList);
        btnPassConfirm = v.findViewById(R.id.btnPassConfirm);
        llPaymentBarcode = v.findViewById(R.id.llPayment);
        etLayoutPass = v.findViewById(R.id.etLayoutPass);
        llCardDetail = v.findViewById(R.id.llCardDetail);
        llMobileNumber = v.findViewById(R.id.llMobileNumber);
        llbillCodePayCodeNow = v.findViewById(R.id.llbillCodePayCodeNow);
        llbillCodePayCodeEnd = v.findViewById(R.id.llbillCodePayCodeEnd);
        llAddToMyBills = v.findViewById(R.id.llAddToMyBills);
        tvBillEnd = v.findViewById(R.id.tvBillEnd);
        llbillFirst = v.findViewById(R.id.llbillFirst);
       // llContinueBill = v.findViewById(R.id.llContinueBill);
        llBill = v.findViewById(R.id.llBill);
        llCvv2 = v.findViewById(R.id.llCvv2);
        tvPrice = v.findViewById(R.id.tvPrice);
        etPass = v.findViewById(R.id.etPass);
        etCvv2 = v.findViewById(R.id.etCvv2);
        ivBillLogo = v.findViewById(R.id.ivBillLogo);
        tvBillName = v.findViewById(R.id.tvBillName);
        tvBillDescription = v.findViewById(R.id.tvBillDescription);
        rvMyBills = v.findViewById(R.id.rvMyBills);
        btnAddBill = v.findViewById(R.id.btnAddBill);

        cbAddToMyBill = v.findViewById(R.id.cbAddToMyBill);

        tvTitleTerm=v.findViewById(R.id.tvTitleTerm);
        tvPhoneNumberTerm=v.findViewById(R.id.tvPhoneNumberTerm);

        llTitleAddMyBill = v.findViewById(R.id.llTitleAddMyBill);
        tvBillCodeFirst = v.findViewById(R.id.tvBillCodeFirst);
        tvDateFirst = v.findViewById(R.id.tvDateFirst);
        llDateFirst=v.findViewById(R.id.llDateFirst);
        tvAmountFirst = v.findViewById(R.id.tvAmountFirst);
        tvPayCodeFirst = v.findViewById(R.id.tvPayCodeFirst);
        // btnBillInfoFirst = v.findViewById(R.id.btnBillInfoFirst);
        rbBillFirst = v.findViewById(R.id.rbBillFirst);
        etTitleAddMyBill = v.findViewById(R.id.etTitleAddMyBill);
        tvBillCodeEnd = v.findViewById(R.id.tvBillCodeEnd);
        tvDateEnd = v.findViewById(R.id.tvDateEnd);
        tvAmountEnd = v.findViewById(R.id.tvAmountEnd);
        tvPayCodeEnd = v.findViewById(R.id.tvPayCodeEnd);
        llDateEnd=v.findViewById(R.id.llDateEnd);
        rbBillEnd = v.findViewById(R.id.rbBillEnd);
        rbBillFirst.setOnCheckedChangeListener(this);
        rbBillEnd.setOnCheckedChangeListener(this);
        llSelectTermBill.setVisibility(View.GONE);


        checkBoxTerm1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                               @Override
                                               public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                                                   if (isChecked){
                                                       checkBoxTerm2.setChecked(false);
                                                   }
                                               }
                                           }
        );

        checkBoxTerm2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                     @Override
                                                     public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                         if (isChecked){
                                                             checkBoxTerm1.setChecked(false);
                                                         }
                                                     }
                                                 }
        );

        etMobile.setFilters(new InputFilter[] { new InputFilter.LengthFilter(11) });


        if (idSelectedBillType==4)
        {
            llPhone.setVisibility(View.VISIBLE);
            llBillCode.setVisibility(View.GONE);
            llBarcode.setVisibility(View.INVISIBLE);
            tvTitle.setText("قبض تلفن ثابت");
            tvBillTitle.setText("شماره تلفن ثابتی که میخواهید قبض آن را پرداخت کنید را وارد نمایید.");
            etMobileHint.setHint("شماره تلفن ثابت");


        }else if (idSelectedBillType==5){

            llPhone.setVisibility(View.VISIBLE);
            llBillCode.setVisibility(View.GONE);
            tvTitle.setText("قبض تلفن همراه");
            llBarcode.setVisibility(View.INVISIBLE);
            tvBillTitle.setText("شماره تلفن همراهی که میخواهید قبض آن را پرداخت کنید را وارد نمایید.");
            etMobileHint.setHint("شماره تلفن همراه");

        }else if (idSelectedBillType==1){

            llBarcode.setVisibility(View.VISIBLE);
            llPhone.setVisibility(View.GONE);
            llBillCode.setVisibility(View.VISIBLE);
            tvTitle.setText("قبض آب");
            tvBillTitle.setText("شناسه قبض و شناسه پرداخت را وارد نمایید.در صورت نیاز می توانید از بارکدخوان نیز استفاده کنید.");

        }else if (idSelectedBillType==2){

            llBarcode.setVisibility(View.VISIBLE);
            llPhone.setVisibility(View.GONE);
            llBillCode.setVisibility(View.VISIBLE);
            tvTitle.setText("قبض برق");
            tvBillTitle.setText("شناسه قبض و شناسه پرداخت را وارد نمایید.در صورت نیاز می توانید از بارکدخوان نیز استفاده کنید.");

        }else if (idSelectedBillType==3){

            llBarcode.setVisibility(View.VISIBLE);
            llPhone.setVisibility(View.GONE);
            llBillCode.setVisibility(View.VISIBLE);
            tvTitle.setText("قبض گاز");
            tvBillTitle.setText("شناسه قبض و شناسه پرداخت را وارد نمایید.در صورت نیاز می توانید از بارکدخوان نیز استفاده کنید.");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {

        }

        llAddToMyBills.setOnClickListener(view ->
        {

        });


        llMobileNumber.setVisibility(View.VISIBLE);
        llbillCodePayCodeNow.setVisibility(View.GONE);
        llbillCodePayCodeEnd.setVisibility(View.GONE);


        new Handler(Looper.getMainLooper()).post(() -> {
        });

        etLayoutPass.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        etLayoutCvv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        btnPassConfirm.setText("پرداخت");
        ViewCompat.setNestedScrollingEnabled(nested, false);


        btnAddBill.setOnClickListener(view -> {
        });


        cvBarcode.setOnClickListener(view -> {
              mainView.openBarcode(BarcodeType.Bill);
        });

        btnContact.setOnClickListener(view -> {
            mainView.onContact();
        });


        btnPhoneInfo.setOnClickListener(view -> {

            if (!Utility.isNetworkAvailable())
            {
                mainView.onInternetAlert();
                return;
            }
            if (idSelectedBillType==4||idSelectedBillType==5)
            if (TextUtils.isEmpty(etMobile.getText().toString()))
            {


                showToast(((Activity) context), "لطفا شماره ثابت یا همراه را وارد نمایید.", R.color.red);

                return;
            }


                if (idSelectedBillType == 1 || idSelectedBillType == 2 || idSelectedBillType == 3)
                {
                    if (TextUtils.isEmpty(etBillCode.getText().toString()))
                    {
                    showToast(((Activity) context), "لطفا شناسه قبض را وارد نمایید.", R.color.red);
                    return;

                }
            }
            Utility.hideSoftKeyboard(view, getActivity());
            String[] number = etMobile.getText().toString().split(" ");
            this.number = number[0];

            billCode=etBillCode.getText().toString();

            getBillCodePayCode(idSelectedBillType);
        });


        btnBackToList.setOnClickListener(view -> {

            llCardDetail.setVisibility(View.GONE);
            llBill.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInLeft)
                    .duration(200)
                    .playOn(llBill);
            ViewCompat.setNestedScrollingEnabled(nested, false);

        });


        btnPassConfirm.setOnClickListener(view -> {

            if (!cardNumberCheck.equals("003725"))
            {
                if (TextUtils.isEmpty(etCvv2.getText().toString()))
                {
                    showToast(((Activity) context), "لطفا شماره cvv2 کارت را وارد نمایید.", 0);
                    return;
                }
                if (TextUtils.isEmpty(etPass.getText().toString()))
                {
                    showToast(((Activity) context), "لطفا رمز کارت را وارد نمایید.", 0);
                    return;
                }
            }
            else
            {
                showToast(((Activity) context), "پرداخت قبوض فقط با کارت بانکی امکان پذیر می باشد.", 0);
                return;
            }


            if (!Utility.isNetworkAvailable())
            {
                mainView.onInternetAlert();
                return;
            }
        });



        etMobile.setLength(40);

        tvBillName.setOnClickListener(view -> {

        });


    }

    private void getBillCodePayCode(Integer idSelectedBillType)
    {

        btnPhoneInfo.startAnimation();
        btnPhoneInfo.setClickable(false);

        BillPhoneRequest request = new BillPhoneRequest();
        request.setType(idSelectedBillType.toString());

        if (idSelectedBillType==4)
        {
            request.setBillCode(number);

            SingletonService.getInstance().billService().bill(new OnServiceStatus<WebServiceClass<BillPhoneResponse>>()
            {
                @Override
                public void onReady(WebServiceClass<BillPhoneResponse> response)
                {
                    try
                    {

                        btnPhoneInfo.revertAnimation(BillFragment.this);
                        btnPhoneInfo.setClickable(true);
                        if (response.info.statusCode == 200)
                        {

                            onGetBillTermSuccess(response.data, number, BillFragment.this.idSelectedBillType);

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
                    btnPhoneInfo.revertAnimation(BillFragment.this);
                    btnPhoneInfo.setClickable(true);
                    if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                    {

                        showError(getContext(), "خطا در دریافت اطلاعات از سرور!");


                    } else
                    {
                        showAlert(getContext(), R.string.networkErrorMessage, R.string.networkError);

                    }

                }
            }, request);
        }
        else if (idSelectedBillType==5){
        request.setBillCode(number);

        SingletonService.getInstance().billService().billMci(new OnServiceStatus<WebServiceClass<BillPhoneResponse>>()
            {
                @Override
                public void onReady(WebServiceClass<BillPhoneResponse> response)
                {
                    try
                    {

                        btnPhoneInfo.revertAnimation(BillFragment.this);
                        btnPhoneInfo.setClickable(true);
                        if (response.info.statusCode == 200)
                        {

                            onGetBillTermSuccess(response.data, number, BillFragment.this.idSelectedBillType);

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
                    btnPhoneInfo.revertAnimation(BillFragment.this);
                    btnPhoneInfo.setClickable(true);
                    if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                    {

                        showError(getContext(), "خطا در دریافت اطلاعات از سرور!");


                    } else
                    {
                        showAlert(getContext(), R.string.networkErrorMessage, R.string.networkError);

                    }

                }
            }, request);
        }
        else if (idSelectedBillType==1){
            request.setBillCode(etBillCode.getText().toString());

            SingletonService.getInstance().billService().billWater(new OnServiceStatus<WebServiceClass<BillCodeResponse>>()
            {
                @Override
                public void onReady(WebServiceClass<BillCodeResponse> response)
                {
                    try
                    {

                        btnPhoneInfo.revertAnimation(BillFragment.this);
                        btnPhoneInfo.setClickable(true);
                        if (response.info.statusCode == 200)
                        {

                            onGetBillCodeSuccess(response.data, billCode, BillFragment.this.idSelectedBillType);

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
                    btnPhoneInfo.revertAnimation(BillFragment.this);
                    btnPhoneInfo.setClickable(true);
                    if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                    {

                        showError(getContext(), "خطا در دریافت اطلاعات از سرور!");


                    } else
                    {
                        showAlert(getContext(), R.string.networkErrorMessage, R.string.networkError);

                    }

                }
            }, request);
        }
        else if (idSelectedBillType==2){
            BillElectricityRequest requestElectricity = new BillElectricityRequest();
            requestElectricity.setBillCode(etBillCode.getText().toString());

            SingletonService.getInstance().billService().billElectricity(new OnServiceStatus<WebServiceClass<BillElectricityResponse>>()
            {
                @Override
                public void onReady(WebServiceClass<BillElectricityResponse> response)
                {
                    try
                    {

                        btnPhoneInfo.revertAnimation(BillFragment.this);
                        btnPhoneInfo.setClickable(true);
                        if (response.info.statusCode == 200)
                        {

                           // onGetBillTermSuccess(response.data, number, BillFragment.this.idSelectedBillType);

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
                    btnPhoneInfo.revertAnimation(BillFragment.this);
                    btnPhoneInfo.setClickable(true);
                    if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                    {

                        showError(getContext(), "خطا در دریافت اطلاعات از سرور!");


                    } else
                    {
                        showAlert(getContext(), R.string.networkErrorMessage, R.string.networkError);

                    }

                }
            }, requestElectricity);
        }
        else if (idSelectedBillType==3){
            request.setBillCode(etBillCode.getText().toString());

            SingletonService.getInstance().billService().billGaz(new OnServiceStatus<WebServiceClass<BillCodeResponse>>()
            {
                @Override
                public void onReady(WebServiceClass<BillCodeResponse> response)
                {
                    try
                    {

                        btnPhoneInfo.revertAnimation(BillFragment.this);
                        btnPhoneInfo.setClickable(true);
                        if (response.info.statusCode == 200)
                        {

                            onGetBillCodeSuccess(response.data, number, BillFragment.this.idSelectedBillType);

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
                    btnPhoneInfo.revertAnimation(BillFragment.this);
                    btnPhoneInfo.setClickable(true);
                    if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                    {

                        showError(getContext(), "خطا در دریافت اطلاعات از سرور!");


                    } else
                    {
                        showAlert(getContext(), R.string.networkErrorMessage, R.string.networkError);

                    }

                }
            }, request);
        }


    }

    private void onGetBillCodeSuccess(BillCodeResponse data, String billCode, Integer idSelectedBillType)
    {


        BillCodeInfoDialog dialog = new BillCodeInfoDialog(getActivity(), data,
                new BillCodeInfoDialog.OnConfirmListener()
                {
                    @Override
                    public void onConfirmClick(BillCodeResponse response)
                    {

                        requestBillPayment(response,null);
                    }

                    @Override
                    public void onCancelClick()
                    {
                    }
                });
        dialog.show(getFragmentManager(), "dialog");

    }



    private void requestBillPayment( BillCodeResponse responseBillCode,BillPhoneResponse responseTerm){

       mainView.showLoading();
        BillPaymentRequest request = new BillPaymentRequest();

        if (idSelectedBillType==4||idSelectedBillType==5)
        {
            if (checkBoxTerm1.isChecked())
            {
                request.setAmount(responseTerm.get1().getAmount());
                request.setBillCode(responseTerm.getBillCode());
                request.setBillTerm("1");
                request.setType(idSelectedBillType.toString());
                request.setPayCode(responseTerm.get1().getPayCode());
                request.setGweBillId("");

            } else
            {
                request.setAmount(responseTerm.get2().getAmount());
                request.setBillCode(responseTerm.getBillCode());
                request.setBillTerm("2");
                request.setType(idSelectedBillType.toString());
                request.setPayCode(responseTerm.get2().getPayCode());
                request.setGweBillId("");

            }
        }else {
            request.setAmount(responseBillCode.getAmount());
            request.setBillCode(responseBillCode.getBillCode());
            request.setBillTerm("2");
            request.setType(idSelectedBillType.toString());
            request.setPayCode(responseBillCode.getPayCode());
            if (responseBillCode.getBillId()!=null)
            {
                request.setGweBillId(responseBillCode.getBillId());
            }else {
                request.setGweBillId("");
            }
        }


        SingletonService.getInstance().billService().billPayment(new OnServiceStatus<WebServiceClass<BillPaymentResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<BillPaymentResponse> response)
            {
                mainView.hideLoading();

                try
                {

                    if (response.info.statusCode == 200)
                    {

                        onPaymentBillSuccess(response.data,number,idSelectedBillType,request);

                    } else
                    {
                        showToast(((Activity) context), response.info.message, R.color.red);

                    }
                } catch (Exception e)
                {
                    showError(getContext(), e.getMessage());

                }


            }

            @Override
            public void onError(String message)
            {
                mainView.hideLoading();

                if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                {

                    showError(getContext(), "خطا در دریافت اطلاعات از سرور!");


                }
                else
                {
                    showAlert(getContext(), R.string.networkErrorMessage, R.string.networkError);

                }

            }
        }, request);
    }

    private void onPaymentBillSuccess(BillPaymentResponse data, String number, Integer idSelectedBillType, BillPaymentRequest request)
    {
        String typeBill="";
        String numberText ="";
        if (request.getBillTerm().equals("1")){
            termText="میان دوره";
        }else if (request.getBillTerm().equals("2")&&idSelectedBillType.equals(4)||idSelectedBillType.equals(5)){
            termText="پایان دوره";
        }else {
            termText="";
        }
        if (idSelectedBillType==4){
            typeBill=" تلفن ثابت'";
            numberText = " برای شماره " + request.getBillCode();
        }else if (idSelectedBillType==5){
            typeBill=" تلفن همراه'";
            numberText = " برای شماره " + request.getBillCode();

        } else if (idSelectedBillType==1){
            typeBill=" آب'";
            numberText="";
         }else if (idSelectedBillType==2){
         typeBill=" برق'";
            numberText="";
        }else if (idSelectedBillType==3){
        typeBill=" گاز'";
            numberText="";

        }

        String textBillPayment=   "با انجام این پرداخت، مبلغ "+Utility.priceFormat(request.getAmount()) +" ریال بابت ' قبض " +termText+typeBill+numberText
            +"، از حساب شما کسر خواهد شد."   ;
         mainView.openBillPaymentFragment(data.getUrl(),textBillPayment,number,idSelectedBillType,request.getAmount().toString(),TrapConfig.PAYMENT_STATUS_BILL);

    }

    private void onGetBillTermSuccess(BillPhoneResponse response,String number,Integer idSelectedBillType)
    {
        llMobileNumber.setVisibility(View.GONE);
        if (idSelectedBillType==4){
            tvTitleTerm.setText("شماره تلفن ثابت:");
            tvPhoneNumberTerm.setText(number);
        }else if (idSelectedBillType==5){
            tvTitleTerm.setText("شماره تلفن همراه:");
            tvPhoneNumberTerm.setText(number);
        }
        tvAmountTerm1.setText(response.get1().getAmount().toString()+" ریال" );
        tvAmountTerm2.setText(response.get2().getAmount().toString() +" ریال");

        responseTerm=response;

        llSelectTermBill.setVisibility(View.VISIBLE);

    }

    private void setDataBillCodePayCode(GetBillCodePayCodeResponse response)
    {

    }

    private void setVisibleCodePayCodeLayout(GetBillCodePayCodeResponse response)
    {

    }



    private void setVisibilityBtnContact(boolean hasContact)
    {
        if (hasContact)
        {
            btnContact.setVisibility(View.VISIBLE);
        } else
        {
            btnContact.setVisibility(View.GONE);
        }
    }

    private void onGetMyBillsServiceSuccess(List<Bills> bills)
    {
        if (bills.size() == 0)
        {
            tvBillsTitle.setText("تا این لحظه شما قبضی را اضافه نکرده اید.");
        } else
        {
            billsList.clear();
            billsList.addAll(bills);
            myBillsAdapter = new MyBillsAdapter(billsList, rvMyBills, getActivity(), this);
            rvMyBills.setAdapter(myBillsAdapter);
            myBillsAdapter.notifyDataSetChanged();
            tvBillsTitle.setText("قبض های من");

        }

        upPanelLayout.setScrollableView(rvMyBills);

        rlBillList.setVisibility(View.VISIBLE);

        final Handler handler = new Handler();
        handler.postDelayed(() -> upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED), 100);

    }

    private void setClicklbtnContact()
    {
        btnContact.setOnClickListener(view -> {
            mainView.onContact();
        });
    }

    private void setDescriptionBillText(String description)
    {
        tvBillDescription.setText(description.toString());
    }

    public void initSpeesh()
    {
        Log.e("testspeech4", "onSpeechBill: " + phone);
        if (!TextUtils.isEmpty(phone))
        {
            etMobile.setText(phone);
            getPhoneInfoRequest(phone);
        } else if (!TextUtils.isEmpty(contactPhone))
        {
            etMobile.setText(contactPhone);
            mianDore = false;
            payanDore = false;
        } else
        {
            etMobile.setText(contactPhone);
            mianDore = false;
            payanDore = false;
        }
    }

    public void barcode(String barcode)
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {

                    etBillCode.setText(barcode.substring(0, 13));

                } catch (Exception e)
                {

                }

            }
        }, 500);
    }


    @Override
    public void onAnimationEnd()
    {
       // btnConfirm.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.button_small_border));
        btnPassConfirm.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.button_small_border));

    }

    public void onSelectContact(OnSelectContact onSelectContact)
    {
        //todo change this
        try
        {
            contactPhone = onSelectContact.getNumber().replaceAll(" ", "") + " ( " + onSelectContact.getName() + " ) ";

        } catch (Exception e)
        {
        }
    }

    public void getPhoneInfoRequest(String number)
    {

    }

    public void onClickBill(LstPhoneBill lstPhoneBills)
    {

        amount = lstPhoneBills.getAmount();
        getBillInfoRequest();

    }


    public void getBillInfoRequest()
    {

    }



    public void transactionsCollapsed()
    {
        upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
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
    public void onBackToChargFragment(int PAYMENT_STATUS, Integer idBill)
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
    public void openBillPaymentFragment(String url, String textBillPayment, String number, Integer idSelectedBillType,String amount,int PAYMENT_STATUS_BILL)
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
    public void onBill(String title,Integer idBillType,String barcode)
    {

    }

    @Override
    public void onChargeSimCard(Integer backstate)
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
    public void onShowPaymentWithoutCardFragment(@Nullable QrModel model)
    {

    }

    @Override
    public void onPaymentWithoutCard(OnClickContinueSelectPayment onClickContinueSelectPayment, String urlPayment, int imageDrawable, String title, String amount, SimChargePaymentInstance paymentInstance, String mobile, int PAYMENT_STATUS)
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

  /*  public void setBillSubMenus(List<MenusSub> billSubMenus) {
        this.billSubMenus = billSubMenus;
    }*/

    public void onSpeechBill(boolean billInfo, String phone, boolean mianDore, boolean payanDore)
    {
        this.phone = phone;
        this.mianDore = mianDore;
        this.payanDore = payanDore;
        Log.e("testspeech2", "onSpeechBill: " + phone);

        if (v != null)
        {
            Log.e("testspeech1", "onSpeechBill: " + phone);
            etMobile.setText("");

            if (!TextUtils.isEmpty(phone))
            {
                Log.e("testspeech3", "onSpeechBill: " + phone);

                etMobile.setText(phone);
                getPhoneInfoRequest(phone);

            }


        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Bills event)
    {
        try
        {
            etMobile.setText(event.getParameter());
            idSelectedBillType = event.getType();
           /* for (int i = 0; i < billsTypePosition.size(); i++) {
                if (event.getType() == billsTypePosition.get(i)) {
                    spinnerBillTypes.setSelection(i);
                }
            }*/
        } catch (Exception e)
        {
            etMobile.setText("");
        }
    }


    /* @Override
     public void addBill(String parameter, String title, Integer type) {
         addMyBillRequest(parameter, title, type);
     }
 */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        switch (compoundButton.getId())
        {
            case R.id.rbBillFirst:
                if (rbBillFirst.isChecked())
                {
                    rbBillEnd.setChecked(false);
                }
                idBillNowEndSelected = 0;

                break;
            case R.id.rbBillEnd:
                if (rbBillEnd.isChecked())
                {
                    rbBillFirst.setChecked(false);
                }
                idBillNowEndSelected = 1;

                break;
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
    public void onClick(View v)
    {
        switch (v.getId()){

            case R.id.llBarcode:

                mainView.openBarcode(BarcodeType.Bill);

                break;

            case R.id.btnCancelTerm:
                llMobileNumber.setVisibility(View.VISIBLE);
                llSelectTermBill.setVisibility(View.GONE);
                break;
            case R.id.btnOkTerm:
                llMobileNumber.setVisibility(View.GONE);
                llSelectTermBill.setVisibility(View.VISIBLE);
                if (checkBoxTerm1.isChecked()||checkBoxTerm2.isChecked())
                {

                      requestBillPayment(null,responseTerm);

                }else {

                    showAlertFailure(getContext(),"لطفا دوره مورد نظر را انتخاب کنید.","",false);

                }
                break;
        }
    }
}


