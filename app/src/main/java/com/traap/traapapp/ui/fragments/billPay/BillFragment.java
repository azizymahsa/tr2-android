package com.traap.traapapp.ui.fragments.billPay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputLayout;
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
import com.traap.traapapp.apiServices.model.contact.OnSelectContact;
import com.traap.traapapp.apiServices.model.getBillCodePayCode.GetBillCodePayCodeRequest;
import com.traap.traapapp.apiServices.model.getBillCodePayCode.GetBillCodePayCodeResponse;
import com.traap.traapapp.apiServices.model.getInfoPhoneBill.LstPhoneBill;
import com.traap.traapapp.apiServices.model.getMyBill.Bills;
import com.traap.traapapp.apiServices.model.getMyBill.GetMyBillRequest;
import com.traap.traapapp.apiServices.model.getMyBill.GetMyBillResponse;
import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.enums.BarcodeType;
import com.traap.traapapp.enums.LeagueTableParent;
import com.traap.traapapp.enums.MediaPosition;
import com.traap.traapapp.enums.PredictPosition;
import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.models.dbModels.ArchiveCardDBModel;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.models.otherModels.paymentInstance.SimPackPaymentInstance;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.adapters.MyBillsAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
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
public class BillFragment extends BaseFragment implements MainActionView, OnAnimationEndListener, CompoundButton.OnCheckedChangeListener
{
    private Context context;
    private View v;
    private CardView cvBarcode;
    private ClearableEditText etPayId, etBillId, etMobile, etTitleAddMyBill;
    private CircularProgressButton btnConfirm, btnAddBill, btnBillInfoEnd, btnMyBills,btnPhoneInfo;
    private View  btnContact;
    private RecyclerView rvInfo, rvMyBills;
    private LinearLayout llDetailPayment, llCardDetail, llBill, llCvv2, llSelect, rlBillList, llTitleAddMyBill,llContinueBill;
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
    private LinearLayout llBillInfo, llBillPayment, llMobileNumber, llbillCodePayCodeNow, llbillCodePayCodeEnd, llPaymentBarcode, tvBillEnd, llAddToMyBills, llDateFirst, llDateEnd, llbillFirst;
    private ImageView ivBillInfo, ivBillPayment;
    private TextView tvBillPayment, tvBillInfo, tvPrice, tvBillName, tvBillName2, tvBillDescription, tvBillsTitle, tvBillCodeFirst, tvDateFirst, tvAmountFirst, tvPayCodeFirst, tvBillCodeEnd, tvDateEnd, tvAmountEnd, tvPayCodeEnd;
    private ImageView ivBillLogo;
    private NestedScrollView nested;
    boolean isMobile = false;
    // private List<BillActiveVm> billActiveVm;
    // private List<MenusSub> billSubMenus;
    private String phone, contactPhone;
    boolean mianDore = false, payanDore = false;
    private MainActionView mainView;
    private Spinner spinnerBillTypes;
    private ArrayList<String> bills;
    private Integer idSelectedBillType;
    private TextInputLayout etMobileHint;
    private List<Bills> billsList = new ArrayList<>();
    private MyBillsAdapter myBillsAdapter;
    private SlidingUpPanelLayout upPanelLayout;
    private ArrayList<Integer> billsTypePosition;
    private RadioButton rbBillEnd, rbBillFirst;
    private int idBillNowEndSelected = -1;
    private CheckBox cbAddToMyBill;
    private boolean addToMyBill = false;
    private View vDateEnd,vDateFirst;
    private TextView tvBillTitle;


    public BillFragment()
    {

    }

    public static BillFragment newInstance(MainActionView mainView, String billTitle, int idSelectedBillType)
    {
        BillFragment f = new BillFragment();
        f.setMainView(mainView);

        Bundle args = new Bundle();
        args.putString("TITLE", billTitle);
        args.putInt("ID_BILL_TYPE", idSelectedBillType);

        f.setArguments(args);

        return f;
    }

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
        Log.e("testspeech8", "onSpeechBill: " + phone);

        if (v != null)
            v = null;

        v = inflater.inflate(R.layout.bill_fragment, container, false);
        ((TextView) v.findViewById(R.id.tvTitle)).setText("پرداخت قبض");

        Log.e("testspeech5", "onSpeechBill: " + phone);


        initView();
        Log.e("testspeech6", "onSpeechBill: " + phone);


        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.e("testspeech7", "onSpeechBill: " + phone);

        etCvv2.setText("");
        etPass.setText("");
        etBillId.setText("");
        etPayId.setText("");
        initSpeesh();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        etMobile.setText("");
        etCvv2.setText("");
        etPass.setText("");
        etBillId.setText("");
        etPayId.setText("");
        phone = null;
        // cbAddToMyBill.setChecked(false);
        // addToMyBill=false;

    }

    private void initView()
    {
        Log.e("sssss", "setPager: ");
        if (getArguments() != null)
        {
            idSelectedBillType = getArguments().getInt("ID_BILL_TYPE");
            billTitle = getArguments().getString("TITLE");
        }
        tvBillTitle = v.findViewById(R.id.tvBillTitle);
        tvBillsTitle = v.findViewById(R.id.tvBillsTitle);
        rlBillList = v.findViewById(R.id.rlBillList);
        upPanelLayout = v.findViewById(R.id.slidingLayout);
        cvBarcode = v.findViewById(R.id.cvBarcode);
        nested = v.findViewById(R.id.nested);
        etPayId = v.findViewById(R.id.etPayId);
        etBillId = v.findViewById(R.id.etBillId);
        btnConfirm = v.findViewById(R.id.btnConfirm);
        btnContact = v.findViewById(R.id.btnContact);
        btnMyBills = v.findViewById(R.id.btnMyBills);
        etMobile = v.findViewById(R.id.etMobile);
        etMobileHint = v.findViewById(R.id.etMobileHint);
        btnPhoneInfo = v.findViewById(R.id.btnPhoneInfo);
        rvInfo = v.findViewById(R.id.rvInfo);
        llDetailPayment = v.findViewById(R.id.llDetailPayment);
        etLayoutCvv = v.findViewById(R.id.etLayoutCvv);
        btnBackToList = v.findViewById(R.id.btnBackToList);
        btnPassConfirm = v.findViewById(R.id.btnPassConfirm);
        tvBillInfo = v.findViewById(R.id.tvBillInfo);
        ivBillInfo = v.findViewById(R.id.ivBillInfo);
        tvBillPayment = v.findViewById(R.id.tvBillPayment);
        ivBillPayment = v.findViewById(R.id.ivBillPayment);
        llBillPayment = v.findViewById(R.id.llBillPayment);
        llPaymentBarcode = v.findViewById(R.id.llPayment);
        llBillInfo = v.findViewById(R.id.llBillInfo);
        etLayoutPass = v.findViewById(R.id.etLayoutPass);
        llCardDetail = v.findViewById(R.id.llCardDetail);
        llMobileNumber = v.findViewById(R.id.llMobileNumber);
        llbillCodePayCodeNow = v.findViewById(R.id.llbillCodePayCodeNow);
        llbillCodePayCodeEnd = v.findViewById(R.id.llbillCodePayCodeEnd);
        llAddToMyBills = v.findViewById(R.id.llAddToMyBills);
        tvBillEnd = v.findViewById(R.id.tvBillEnd);
        llbillFirst = v.findViewById(R.id.llbillFirst);
        llContinueBill = v.findViewById(R.id.llContinueBill);
        llBill = v.findViewById(R.id.llBill);
        llCvv2 = v.findViewById(R.id.llCvv2);
        tvPrice = v.findViewById(R.id.tvPrice);
        etPass = v.findViewById(R.id.etPass);
        etCvv2 = v.findViewById(R.id.etCvv2);
        ivBillLogo = v.findViewById(R.id.ivBillLogo);
        tvBillName = v.findViewById(R.id.tvBillName);
        llSelect = v.findViewById(R.id.llSelect);
        tvBillName2 = v.findViewById(R.id.tvBillName2);
        tvBillDescription = v.findViewById(R.id.tvBillDescription);
        rvMyBills = v.findViewById(R.id.rvMyBills);
        btnAddBill = v.findViewById(R.id.btnAddBill);

        cbAddToMyBill = v.findViewById(R.id.cbAddToMyBill);

        llTitleAddMyBill = v.findViewById(R.id.llTitleAddMyBill);
        tvBillCodeFirst = v.findViewById(R.id.tvBillCodeFirst);
        tvDateFirst = v.findViewById(R.id.tvDateFirst);
        vDateFirst=v.findViewById(R.id.vDateFirst);
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
        vDateEnd=v.findViewById(R.id.vDateEnd);
        llDateEnd=v.findViewById(R.id.llDateEnd);
        btnBillInfoEnd = v.findViewById(R.id.btnBillInfoEnd);
        rbBillEnd = v.findViewById(R.id.rbBillEnd);
        rbBillFirst.setOnCheckedChangeListener(this);
        rbBillEnd.setOnCheckedChangeListener(this);
        llContinueBill.setVisibility(View.GONE);
        tvBillTitle.setText(billTitle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            ivBillPayment.setImageTintList(null);
            ivBillInfo.setImageTintList(ContextCompat.getColorStateList(getActivity(), R.color.textColorSecondary));
        }

        llAddToMyBills.setOnClickListener(view ->
        {
            if (cbAddToMyBill.isChecked())
            {
                cbAddToMyBill.setChecked(false);
                llTitleAddMyBill.setVisibility(View.GONE);
                addToMyBill = false;
            }
            else
            {
                cbAddToMyBill.setChecked(true);
                llTitleAddMyBill.setVisibility(View.VISIBLE);
                addToMyBill = true;
            }
        });


        //tvBillInfo.setText(billSubMenus.get(1).getTitle());
        //  tvBillPayment.setText(billSubMenus.get(0).getTitle());

//        if (billSubMenus != null) {
//            if (billSubMenus.get(1).getVisible()) {
//                llSelect.setVisibility(View.GONE);
//                llMobileNumber.setVisibility(View.GONE);
//                llbillCodePayCodeNow.setVisibility(View.GONE);
//                llbillCodePayCodeEnd.setVisibility(View.GONE);
//                llDetailPayment.setVisibility(View.VISIBLE);
//            }
//
//            if (billSubMenus.get(0).getVisible()) {
        llSelect.setVisibility(View.GONE);
        llMobileNumber.setVisibility(View.VISIBLE);
        llbillCodePayCodeNow.setVisibility(View.GONE);
        llbillCodePayCodeEnd.setVisibility(View.GONE);
        llDetailPayment.setVisibility(View.GONE);

//            }
//
//            if (billSubMenus.get(0).getVisible() && billSubMenus.get(1).getVisible()) {
//                llSelect.setVisibility(View.VISIBLE);
//                llMobileNumber.setVisibility(View.VISIBLE);
//                llbillCodePayCodeNow.setVisibility(View.GONE);
//                llbillCodePayCodeEnd.setVisibility(View.GONE);
//                llDetailPayment.setVisibility(View.GONE);
//            }


        //  }


       /* if (billActiveVm != null) {
            setDataSpinnerBillTypesList(billActiveVm);
            setDescriptionBillText(billActiveVm.get(0).getDescription());
        }*/
        new Handler(Looper.getMainLooper()).post(() -> {
//            billInfoAdapter = new BillInfoAdapter(lstPhoneBills, this, mainView);
//            rvInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
//            rvInfo.setAdapter(billInfoAdapter);
        });

        etLayoutPass.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        etLayoutCvv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));
        btnConfirm.setText("تایید");
        // btnBackToList.setText("بازگشت");
        btnPassConfirm.setText("پرداخت");
        ViewCompat.setNestedScrollingEnabled(nested, false);

     /*   if (!cardNumberCheck.equals("003725")) {
            llCvv2.setVisibility(View.VISIBLE);
        }*/

        btnBillInfoEnd.setOnClickListener(view -> {

            if (idBillNowEndSelected == 0)
            {
                billId = tvBillCodeFirst.getText().toString();
                payId = tvPayCodeFirst.getText().toString();
            } else if (idBillNowEndSelected == 1)
            {
                billId = tvBillCodeEnd.getText().toString();
                payId = tvPayCodeEnd.getText().toString();
            } else
            {
                billId = tvBillCodeEnd.getText().toString();
                payId = tvPayCodeEnd.getText().toString();
            }
            if (addToMyBill)
            {

              /*  if (TextUtils.isEmpty(etTitleAddMyBill.getText())) {
                    mainView.showErrorMessage("لطفا عنوان را وارد نمایید.", this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
                    return;
                } else {
                    btnBillInfoEnd.startAnimation();
                    btnBillInfoEnd.setClickable(false);
                    //addMyBillRequest(billId, etTitleAddMyBill.getText().toString(), idSelectedBillType);
                    getBillInfoRequest();
                }*/

            } else
            {
                btnBillInfoEnd.startAnimation();
                btnBillInfoEnd.setClickable(false);
                getBillInfoRequest();
            }

        });
        btnAddBill.setOnClickListener(view -> {
//            addMyBillDialog = new AddMyBillDialog(getActivity(), this, billActiveVm, mainView);
//            addMyBillDialog.show(getActivity().getFragmentManager(), "addBill");
        });

        btnConfirm.setOnClickListener(view -> {
            if (TextUtils.isEmpty(etPayId.getText()))
            {
                showToast(getContext(), "لطفا شناسه پرداخت را وارد نمایید.", R.color.red);

                return;
            }
            if (TextUtils.isEmpty(etBillId.getText()))
            {
                showToast(getContext(), "لطفا شناسه قبض را وارد نمایید.", R.color.red);

                return;
            }
            Utility.hideSoftKeyboard(view, getActivity());
            /*     if (!isMobile)*/
            billId = etBillId.getText().toString();
            payId = etPayId.getText().toString();
            btnConfirm.startAnimation();
            btnConfirm.setClickable(false);
            getBillInfoRequest();
        });

        cvBarcode.setOnClickListener(view -> {
            //  mainView.openBarcode(BarcodeType.Bill);
        });

        btnContact.setOnClickListener(view -> {
            mainView.onContact();
        });
        btnMyBills.setOnClickListener(view -> {
            openMyBills();
        });


        btnPhoneInfo.setOnClickListener(view -> {
         /*   if (idSelectedBillType == 1 || idSelectedBillType == 2 || idSelectedBillType == 3) {
                llAddToMyBills.setVisibility(View.VISIBLE);
            } else {
                llAddToMyBills.setVisibility(View.GONE);
            }*/
            if (!Utility.isNetworkAvailable())
            {
                mainView.onInternetAlert();
                return;
            }
            if (TextUtils.isEmpty(etMobile.getText().toString()))
            {

                if (idSelectedBillType == 1 || idSelectedBillType == 2 || idSelectedBillType == 3)
                {

                    showToast(((Activity) context), "لطفا شناسه قبض را وارد نمایید.", R.color.red);

                }
                else
                {
                    showToast(((Activity) context), "لطفا شماره ثابت یا همراه را وارد نمایید.", R.color.red);
                }
                return;
            }
            Utility.hideSoftKeyboard(view, getActivity());
            String[] number = etMobile.getText().toString().split(" ");
            this.number = number[0];
            cbAddToMyBill.setChecked(false);
            addToMyBill = false;
            rbBillFirst.setSelected(false);
            rbBillEnd.setSelected(true);
            etTitleAddMyBill.setText("");
            getBillCodePayCode();
        });


        btnBackToList.setOnClickListener(view -> {
            // mainView.needExpanded(false);

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
            ////////   payBillRequest();
        });


        llBillInfo.setOnClickListener(view -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                ivBillInfo.setImageTintList(null);
                ivBillPayment.setImageTintList(ContextCompat.getColorStateList(getActivity(), R.color.textColorSecondary));
            }
            tvBillPayment.setTextColor(getActivity().getResources().getColor(R.color.textColorSecondary));
            tvBillInfo.setTextColor(getActivity().getResources().getColor(R.color.warmGray));
            llMobileNumber.setVisibility(View.GONE);
            llDetailPayment.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInLeft)
                    .duration(200)
                    .playOn(llDetailPayment);

        });


        llBillPayment.setOnClickListener(view -> {
            tvBillPayment.setTextColor(getActivity().getResources().getColor(R.color.warmGray));
            tvBillInfo.setTextColor(getActivity().getResources().getColor(R.color.warmGray));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                ivBillInfo.setImageTintList(ContextCompat.getColorStateList(getActivity(), R.color.textColorSecondary));
                ivBillPayment.setImageTintList(null);
            }


            llDetailPayment.setVisibility(View.GONE);
            llMobileNumber.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInLeft)
                    .duration(200)
                    .playOn(llMobileNumber);
        });
        etMobile.setLength(40);

        tvBillName.setOnClickListener(view -> {
           /* if (billActiveVm != null) {
                getActivity().startActivityForResult(new Intent(getActivity(), AvailableBankActivity.class)
                        .putExtra("billActiveVm", new Gson().toJson(billActiveVm)));
            }*/
        });
        tvBillName2.setOnClickListener(view -> {
           /* if (billActiveVm != null) {
                getActivity().startActivityForResult(new Intent(getActivity(), AvailableBankActivity.class)
                        .putExtra("billActiveVm", new Gson().toJson(billActiveVm)));
            }*/
        });


    }

    private void getBillCodePayCode()
    {

        btnPhoneInfo.startAnimation();
        btnPhoneInfo.setClickable(false);

        GetBillCodePayCodeRequest request = new GetBillCodePayCodeRequest();
        request.setBillType(idSelectedBillType);
        request.setParameter(number);
        SingletonService.getInstance().getBillCodePayCode().getBillCodePayCodeService(new OnServiceStatus<WebServiceClass<GetBillCodePayCodeResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetBillCodePayCodeResponse> response)
            {
                try
                {

                    btnPhoneInfo.revertAnimation(BillFragment.this);
                    btnPhoneInfo.setClickable(true);
                    if (response.info.statusCode == 200)
                    {

                        onGetBillCodePayCodeSuccess(response.data);

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
                // Utility.hideSoftKeyboard(view, getActivity());
                if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                {

                    showToast(((Activity) context), message, R.color.red);


                }
                else
                {
                    mainView.showError(getString(R.string.networkErrorMessage));

                }

            }
        }, request);


    }

    private void onGetBillCodePayCodeSuccess(GetBillCodePayCodeResponse response)
    {
        llContinueBill.setVisibility(View.VISIBLE);
        setVisibleCodePayCodeLayout(response);
        setDataBillCodePayCode(response);
    }

    private void setDataBillCodePayCode(GetBillCodePayCodeResponse response)
    {

    /*    if (response.getBillNow() != null) {
            tvBillCodeFirst.setText(response.getBillNow().getBillCode().toString());
            if (response.getBillNow().getDate().isEmpty()) {
                llDateFirst.setVisibility(View.GONE);
                vDateFirst.setVisibility(View.GONE);
            } else {
                llDateFirst.setVisibility(View.VISIBLE);
                vDateFirst.setVisibility(View.VISIBLE);
                tvDateFirst.setText(response.getBillNow().getDate().toString());
            }
            tvAmountFirst.setText(Utility.priceFormat(Integer.valueOf(response.getBillNow().getAmount()) + "") + " ریال");
            tvPayCodeFirst.setText(response.getBillNow().getPayCode().toString());
        }
        if (response.getBill()!=null) {
            tvBillCodeEnd.setText(response.getBill().getBillCode().toString());

            if (response.getBill().getDate().isEmpty()) {
                llDateEnd.setVisibility(View.GONE);
                vDateEnd.setVisibility(View.GONE);
            } else {
                llDateEnd.setVisibility(View.VISIBLE);
                vDateEnd.setVisibility(View.VISIBLE);
                tvDateEnd.setText(response.getBill().getDate().toString());
            }
            tvAmountEnd.setText(Utility.priceFormat(Integer.valueOf(response.getBill().getAmount()) + "") + " ریال");
            tvPayCodeEnd.setText(response.getBill().getPayCode().toString());
        }*/

    }

    private void setVisibleCodePayCodeLayout(GetBillCodePayCodeResponse response)
    {
  /*      if (response.getBillNow() == null&&response.getBill()!=null) {
            idBillNowEndSelected = 1;
            llSelect.setVisibility(View.GONE);
            llMobileNumber.setVisibility(View.GONE);
            llPaymentBarcode.setVisibility(View.GONE);
            llbillCodePayCodeNow.setVisibility(View.GONE);
            llbillCodePayCodeEnd.setVisibility(View.VISIBLE);
            tvBillEnd.setVisibility(View.GONE);
            llbillFirst.setVisibility(View.GONE);
            llDetailPayment.setVisibility(View.GONE);
        }
        if (response.getBill()==null&&response.getBillNow()!=null){
            idBillNowEndSelected = 0;
            llSelect.setVisibility(View.GONE);
            llMobileNumber.setVisibility(View.GONE);
            llPaymentBarcode.setVisibility(View.GONE);
            llbillCodePayCodeNow.setVisibility(View.VISIBLE);
            llbillCodePayCodeEnd.setVisibility(View.GONE);
            tvBillEnd.setVisibility(View.GONE);
            llDetailPayment.setVisibility(View.GONE);
            llbillFirst.setVisibility(View.GONE);
        }
        if (response.getBillNow() != null && response.getBill() != null) {
            idBillNowEndSelected = 1;
            rbBillEnd.setChecked(true);
            rbBillFirst.setChecked(false);
            llSelect.setVisibility(View.GONE);
            llMobileNumber.setVisibility(View.GONE);
            llPaymentBarcode.setVisibility(View.GONE);
            llbillCodePayCodeNow.setVisibility(View.VISIBLE);
            llbillCodePayCodeEnd.setVisibility(View.VISIBLE);
            tvBillEnd.setVisibility(View.VISIBLE);
            llbillFirst.setVisibility(View.VISIBLE);
            llDetailPayment.setVisibility(View.GONE);
        }*/
    }

  /*  private void addMyBillRequest(String parameter, String title, Integer type) {

        {
            btnAddBill.startAnimation();
            btnAddBill.setClickable(false);
            AddMyBillRequest request = new AddMyBillRequest();
            request.setParameter(parameter + "");
            request.setTitle(title);
            request.setType(type);
            SingletonService.getInstance().addMyBiilsService().addMyBillsService(new OnServiceStatus<AddMyBillResponse>() {
                @Override
                public void onReady(AddMyBillResponse response) {
                    try {

                        btnAddBill.revertAnimation(BillFragment.this);
                        btnAddBill.setClickable(true);
                        if (response.getServiceMessage().getCode() == 200) {
                            //mainView.message("قبض شما با موفقیت ثبت شد");
                            upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

                        } else {
                            mainView.showErrorMessage(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);

                        }
                    } catch (Exception e) {
                        mainView.showErrorMessage(e.getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInException);

                    }
                }

                @Override
                public void showErrorMessage(String message) {

                    mainView.showErrorMessage(message, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
                    btnAddBill.revertAnimation(BillFragment.this);
                    btnAddBill.setClickable(true);
                }
            }, request);

        }
    }*/


 /*   private void setDataSpinnerBillTypesList(List<BillActiveVm> billActiveVm) {
        bills = new ArrayList<String>();
        billsTypePosition = new ArrayList<Integer>();

        for (int i = 0; i < billActiveVm.size(); i++) {
            bills.add(billActiveVm.get(i).getTitle());
            billsTypePosition.add(billActiveVm.get(i).getId());
        }
        setDataSpinnerBillTypes(bills, billActiveVm);
    }*/

  /*  private void setDataSpinnerBillTypes(ArrayList<String> bills, List<BillActiveVm> billActiveVm) {

        String positionBillDefault = "قبض برق";
        ArrayAdapter<String> adapterBillsType =
                new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item, bills);
        adapterBillsType.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);

        spinnerBillTypes.setAdapter(adapterBillsType);

        //set default ghabze bargh
        if (positionBillDefault != null) {
            int spinnerPosition = adapterBillsType.getPosition(positionBillDefault);
            spinnerBillTypes.setSelection(spinnerPosition);
        }


        spinnerBillTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                BillActiveVm item = billActiveVm.get(position);
                idSelectedBillType = item.getId();
                setDescriptionBillText(item.getDescription());
                setHintBillEditText(item.getTitle(), item.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }*/

 /*   private void setHintBillEditText(String title, Integer id) {
        //etMobile.setText("");
        if (id == 1 || id == 2 || id == 3) {
            etMobileHint.setHint(getString(R.string.etBillHint));
            setVisibilityBtnContact(false);
        } else {
            etMobileHint.setHint(getString(R.string.etMobileHint));
            setVisibilityBtnContact(true);
        }
    }*/

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

    private void openMyBills()
    {

        myBillsRequest();
    }

    private void myBillsRequest()
    {
        btnMyBills.startAnimation();
        btnMyBills.setClickable(false);
        GetMyBillRequest request = new GetMyBillRequest();

        SingletonService.getInstance().getMyBillsService().getMyBills(new OnServiceStatus<WebServiceClass<GetMyBillResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetMyBillResponse> response)
            {
                try
                {

                    btnMyBills.revertAnimation(BillFragment.this);
                    btnMyBills.setClickable(true);
                    if (response.info.statusCode == 200)
                    {

                        onGetMyBillsServiceSuccess(response.data.getResults());

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
                btnMyBills.revertAnimation(BillFragment.this);
                btnMyBills.setClickable(true);
                if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
                {
                    showToast(getActivity(), "خطای ارتباط با سرور!", R.color.red);


                }
                else
                {
                    mainView.showError(getString(R.string.networkErrorMessage));

                }

            }
        }, request);
          /*  @Override
            public void onReady(WebServiceClass<GetMyBillResponse> response) {
                try {

                    btnMyBills.revertAnimation(BillFragment.this);
                    btnMyBills.setClickable(true);
                    if (response.getServiceMessage().getCode() == 200) {

                        onGetMyBillsServiceSuccess(response.getBills());

                    } else {
                        showToast(getContext(),response.getServiceMessage().getMessage(),R.color.red);
                    }
                } catch (Exception e) {
                    showToast(getContext(),e.getMessage(),R.color.red);

                }
            }

            @Override
            public void showErrorMessage(String message) {
              //  loginView.hideLoading();
                btnMyBills.revertAnimation(BillFragment.this);
                btnMyBills.setClickable(true);
                showToast(getActivity(),message,R.color.red);
            }
        }, request);*/


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

   /* public void payBillRequest() {
        btnPassConfirm.startAnimation();
        btnPassConfirm.setClickable(false);
        BillPaymentRequest request = new BillPaymentRequest();
        request.setAmount(amount + "");
        request.setCardNumber(archiveCardDBModels.getNumber());
        request.setCvv2(etCvv2.getText().toString());
        request.setExpDate(archiveCardDBModels.getExpireYear() + archiveCardDBModels.getExpireMonth());
        request.setPassword(etPass.getText().toString());
        request.setBillId(billId);
        request.setPayId(payId);
        request.setUserId(Prefs.getInt("userId", 0));

        SingletonService.getInstance().getBillInfoService().callBillPayment(new OnServiceStatus<BillPaymentResponse>() {
            @Override
            public void onReady(BillPaymentResponse response) {
                try {
                    btnPassConfirm.revertAnimation(BillFragment.this);
                    btnPassConfirm.setClickable(true);
                    if (response.getServiceMessage().getCode() == 200) {
                        CharitiesResultDialog resultDialog = new CharitiesResultDialog(getActivity(), response.getCreateDate(), response.getRefNo(),
                                "", Utility.priceFormat(amount + ""), "پرداخت قبض");
                        resultDialog.setTitle("پرداخت قبض با موفقیت انجام شد.");
                        resultDialog.setTitleShare("رسید پرداخت قبض");
                        resultDialog.show(getActivity().getFragmentManager(), "resultDialog");

                        etCvv2.setText("");
                        etPass.setText("");
                        amount = 0;
                        mainView.onHome();


                    } else {
                        mainView.showErrorMessage(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
                    }

                } catch (Exception e) {
                    mainView.showErrorMessage(e.getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInException);

                }
            }

            @Override
            public void showErrorMessage(String message) {
                mainView.showErrorMessage(message, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
                btnPassConfirm.revertAnimation(BillFragment.this);
                btnPassConfirm.setClickable(true);
            }
        }, request);
*//*
        SingletonService.getInstance().getBillInfoService().getBillInfoService(new OnServiceStatus<GetBillInfoResponse>() {
            @Override
            public void onReady(GetBillInfoResponse response) {
                btnConfirm.revertAnimation(BillFragment.this);
                btnConfirm.setClickable(true);
                try {
                    if (response.getServiceMessage().getCode() == 200) {
                        mainView.openWeb(response.getUrl(), false);
                    } else {
                        mainView.showErrorMessage(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), Config.showClassNameInMessage);
                    }

                } catch (Exception e) {
                    mainView.showErrorMessage(e.getMessage(), this.getClass().getSimpleName(), Config.showClassNameInException);

                }

            }

            @Override
            public void showErrorMessage(String message) {
                mainView.showErrorMessage(message, this.getClass().getSimpleName(), Config.showClassNameInException);

                btnConfirm.revertAnimation(BillFragment.this);
                btnConfirm.setClickable(true);
                mainView.openWeb(message, false);
            }
        }, Prefs.getInt("userId", 0) + "", billId, payId);
*//*

    }*/

    /*@Override
    public void cardModel(ArchiveCardDBModel archiveCardDBModels) {
        this.archiveCardDBModels = archiveCardDBModels;
        cardNumberCheck = archiveCardDBModels.getNumber().substring(0, 6);


        if (v == null)
            return;
        etCvv2.setText("");

        etPass.setText("");
        if (!cardNumberCheck.equals("003725")) {

            llCvv2.setVisibility(View.VISIBLE);
            YoYo.with(Techniques.SlideInLeft)
                    .duration(200)
                    .playOn(llCvv2);


        } else {

            YoYo.with(Techniques.SlideOutLeft).withListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    llCvv2.setVisibility(View.GONE);
                    //btnPassConfirm.setClickable(false);
                    mainView.message("پرداخت قبوض فقط با کارت بانکی امکان پذیر میباشد.");

                }
            })
                    .duration(200)
                    .playOn(llCvv2);

        }
    }*/

   /* @Override
    public void webUrl(String url) {
    }*/
/*
    @Override
    public void barcode(String barcode) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ivBillInfo.setImageTintList(null);
                        ivBillPayment.setImageTintList(ContextCompat.getColorStateList(getActivity(), R.color.textColorSecondary));
                    }
                    tvBillPayment.setTextColor(getActivity().getResources().getColor(R.color.textColorSecondary));
                    tvBillInfo.setTextColor(getActivity().getResources().getColor(R.color.new_app_color));

                    llMobileNumber.setVisibility(View.GONE);
                    llDetailPayment.setVisibility(View.VISIBLE);

                    llMobileNumber.setVisibility(View.GONE);
                    llDetailPayment.setVisibility(View.VISIBLE);
                    // rvInfo.setVisibility(View.GONE);
                    etBillId.setText(barcode.substring(0, 13));
                    etPayId.setText(barcode.substring(13));
                    //  payBillRequest();
                } catch (Exception e) {
                    mainView.showErrorMessage(e.getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
                }

            }
        }, 500);

    }*/

//    @Override
//    public void setAmount(String amount) {
//
//    }

    @Override
    public void onAnimationEnd()
    {
        btnConfirm.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.button_small_border));
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

      /*  if (TextUtils.isEmpty(etMobile.getText().toString())) {

            if (idSelectedBillType == 1 || idSelectedBillType == 2 || idSelectedBillType == 3) {
                showToast(getContext(),"لطفا شناسه قبض را وارد نمایید.",R.color.red);
            } else {
                showToast(getContext(),"لطفا شماره ثابت یا همراه را وارد نمایید.",R.color.red);
            }
            return;
        }
        lstPhoneBills.clear();
        btnPhoneInfo.startAnimation();
        btnPhoneInfo.setClickable(false);
        GetInfoPhoneBillRequest request = new GetInfoPhoneBillRequest();
        request.setUserId(Prefs.getInt("userId", 0));
        request.setPhoneNumber(number);
        // phone=null;

        SingletonService.getInstance().getBillInfoService().getInfoPhoneBill(new OnServiceStatus<GetInfoPhoneBillResponse>() {
            @Override
            public void onReady(GetInfoPhoneBillResponse response) {

                try {
                 //   mainView.needExpanded(false);

                    btnPhoneInfo.revertAnimation(BillFragment.this);
                    btnPhoneInfo.setClickable(true);

                    if (response.getServiceMessage().getCode() == 200) {
                        llDetailPayment.setVisibility(View.GONE);
                        rvInfo.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.SlideInLeft)
                                .duration(200)
                                .playOn(rvInfo);
                        lstPhoneBills.addAll(response.getLstPhoneBill());
                        billInfoAdapter.notifyDataSetChanged();
                        ViewCompat.setNestedScrollingEnabled(nested, false);
                        ViewCompat.setNestedScrollingEnabled(rvInfo, false);
                        if (mianDore) {
                            amount = response.getLstPhoneBill().get(0).getAmount();
                            billId = response.getLstPhoneBill().get(0).getBillId();
                            payId = response.getLstPhoneBill().get(0).getPayId();
                            //  billTitle=lstPhoneBills.getMidTerm();
                            getBillInfoRequest();
                            mainView.showProgress();
                        }

                        if (payanDore) {
                            amount = response.getLstPhoneBill().get(1).getAmount();
                            billId = response.getLstPhoneBill().get(1).getBillId();
                            payId = response.getLstPhoneBill().get(1).getPayId();
                            //  billTitle=lstPhoneBills.getMidTerm();
                            getBillInfoRequest();
                            mainView.showProgress();
                        }


                    } else {
                        mainView.showErrorMessage(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);

                    }

                } catch (Exception e) {
                    mainView.showErrorMessage(e.getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInException);

                }


            }

            @Override
            public void showErrorMessage(String message) {
                mainView.showErrorMessage(message, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
                btnPhoneInfo.revertAnimation(BillFragment.this);
                btnPhoneInfo.setClickable(true);


            }
        }, request);
*/

    }

    public void onClickBill(LstPhoneBill lstPhoneBills)
    {

        amount = lstPhoneBills.getAmount();
        billId = lstPhoneBills.getBillId();
        payId = lstPhoneBills.getPayId();
        //  billTitle=lstPhoneBills.getMidTerm();
        getBillInfoRequest();
        // mainView.showProgress();

    }


    public void getBillInfoRequest()
    {

       /* GetInfoBillRequest request = new GetInfoBillRequest();

        request.setBillId(billId);
        request.setPayId(payId);
        request.setUserId(Prefs.getInt("userId", 0));

        SingletonService.getInstance().getBillInfoService().callInfoBill(new OnServiceStatus<GetInfoBillResponse>() {
            @Override
            public void onReady(GetInfoBillResponse response) {
                btnConfirm.revertAnimation(BillFragment.this);
                btnConfirm.setClickable(true);
                btnPassConfirm.revertAnimation(BillFragment.this);
                btnPassConfirm.setClickable(true);
                btnBillInfoEnd.revertAnimation(BillFragment.this);
                btnBillInfoEnd.setClickable(true);
               // mainView.hideProgress();

                try {

                    if (response.getServiceMessage().getCode() == 200) {

                        if (!response.getIsPaid()) {
                            ViewCompat.setNestedScrollingEnabled(nested, true);
                           // mainView.needExpanded(true);
                            amount = Integer.valueOf(response.getAmount());
                            // tvPrice.setText(response.getTitle()+" ( "+billTitle+" ) "+" مبلغ: " + Utility.priceFormat(amount + "") + " ریال");
                            tvPrice.setText(response.getTitle() + " مبلغ: " + Utility.priceFormat(amount + "") + " ریال");
                            billId = response.getBillId();
                            payId = response.getPayId();
                            llBill.setVisibility(View.GONE);
                            llCardDetail.setVisibility(View.VISIBLE);
                            YoYo.with(Techniques.SlideInLeft)
                                    .duration(200)
                                    .playOn(llCardDetail);
                            GlideApp.with(SingletonContext.getInstance().getContext()).load(response.getLogoBill()).into(ivBillLogo);
                        } else {

                            BillAlertDialog billAlertDialog = new BillAlertDialog(getActivity(), response);
                            billAlertDialog.show(getActivity().getFragmentManager(), "billAlertDialog");
                        }


                    } else {
                        mainView.showErrorMessage(response.getServiceMessage().getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInMessage);
                    }

                } catch (Exception e) {
                    mainView.showErrorMessage(e.getMessage(), this.getClass().getSimpleName(), DibaConfig.showClassNameInException);

                }


            }

            @Override
            public void showErrorMessage(String message) {
                mainView.showErrorMessage(message, this.getClass().getSimpleName(), DibaConfig.showClassNameInException);
                btnConfirm.revertAnimation(BillFragment.this);
                btnConfirm.setClickable(true);

                btnBillInfoEnd.revertAnimation(BillFragment.this);
                btnBillInfoEnd.setClickable(true);
            }
        }, request);
*/
    }

  /*  public void setBillActiveVm(List<BillActiveVm> billActiveVm) {
        this.billActiveVm = billActiveVm;
    }*/


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
    public void onPredict(PredictPosition position, Integer matchId, Boolean isPredictable)
    {

    }

    @Override
    public void onBackToPredict(PredictPosition position, Integer matchId, Boolean isPredictable)
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
    public void onSetPredictCompleted(Integer matchId, Boolean isPredictable, String message)
    {

    }

    @Override
    public void onBackToChargFragment(int PAYMENT_STATUS)
    {

    }

    @Override
    public void backToAllServicePackage(Integer backState)
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
    public void onHeadCoach(Integer coachId)
    {

    }

    @Override
    public void onMediaPlayersFragment()
    {

    }


    @Override
    public void onBill()
    {

    }

    @Override
    public void onChargeSimCard(Integer backstate)
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
    public void onBarcodReader()
    {

    }


    @Override
    public void onPaymentWithoutCard()
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
}


