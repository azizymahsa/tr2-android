package com.traap.traapapp.ui.fragments.ticket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.google.android.material.tabs.TabLayout;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessRequest;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessResponse;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.apiServices.model.paymentMatch.PaymentMatchRequest;
import com.traap.traapapp.apiServices.model.paymentMatch.PaymentMatchResponse;
import com.traap.traapapp.apiServices.model.paymentMatch.Viewers;
import com.traap.traapapp.apiServices.model.paymentWallet.ResponsePaymentWallet;
import com.traap.traapapp.apiServices.model.spectatorInfo.GetSpectatorListResponse;
import com.traap.traapapp.apiServices.model.spectatorInfo.SpectatorInfoResponse;
import com.traap.traapapp.apiServices.model.stadium_rules.ResponseStadiumRules;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.models.otherModels.paymentInstance.SimPackPaymentInstance;
import com.traap.traapapp.models.otherModels.ticket.SpectatorInfoModel;
import com.traap.traapapp.singleton.SingletonNeedGetAllBoxesRequest;
import com.traap.traapapp.ui.activities.paymentResult.PaymentResultChargeActivity;
import com.traap.traapapp.ui.activities.ticket.BuyTicketsActivity;
import com.traap.traapapp.ui.activities.ticket.ShowTicketActivity;
import com.traap.traapapp.ui.adapters.paymentGateway.SelectPaymentAdapter;
import com.traap.traapapp.ui.adapters.spectatorList.SpectatorListAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.paymentGateWay.PaymentGateWayParentActionView;
import com.traap.traapapp.ui.fragments.paymentGateWay.paymentWallet.PaymentWalletImpl;
import com.traap.traapapp.ui.fragments.paymentGateWay.paymentWallet.PaymentWalletInteractor;
import com.traap.traapapp.ui.fragments.ticket.paymentTicket.PaymentTicketImpl;
import com.traap.traapapp.ui.fragments.ticket.paymentTicket.PaymentTicketInteractor;
import com.traap.traapapp.ui.fragments.ticket.rulesStadium.RulesStadiumImpl;
import com.traap.traapapp.ui.fragments.ticket.rulesStadium.RulesStadiumInteractor;
import com.traap.traapapp.ui.fragments.turnover.ClickTurnOverEvent;
import com.traap.traapapp.utilities.CustomViewPager;
import com.traap.traapapp.utilities.KeyboardUtils;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.NationalCodeValidation;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.EventBus;

import static com.traap.traapapp.ui.base.BaseActivity.showAlert;

public class CompeletInfoFragment
        extends BaseFragment implements View.OnClickListener, View.OnFocusChangeListener, PaymentTicketInteractor.OnFinishedPaymentTicketListener, RulesStadiumInteractor.OnFinishedRulesStadiumListener, PaymentWalletInteractor.OnFinishedPaymentWalletListener, SpectatorListAdapter.OnItemSpectatorListClickListener
{
    private Context context;
    private static final String KEY_MODEL = "KEY_MODEL";
    private View view;
    private TextView txtCondition, tvBalance, tvDate;
    private View btnBackToDetail, btnPaymentConfirm;
    private int count = 1;
    private OnClickContinueBuyTicket onClickContinueBuyTicketListener;
    private AutoCompleteTextView etNationalCode_1, etNationalCode_2, etNationalCode_3, etNationalCode_4, etNationalCode_5;
    private com.rengwuxian.materialedittext.MaterialEditText etFamily_1, etName_1;
    private com.rengwuxian.materialedittext.MaterialEditText etFamily_2, etName_2;
    private com.rengwuxian.materialedittext.MaterialEditText etFamily_3, etName_3;
    private com.rengwuxian.materialedittext.MaterialEditText etFamily_4, etName_4;
    private com.rengwuxian.materialedittext.MaterialEditText etFamily_5, etName_5;
    private LinearLayout llBoxTicket1, llBoxTicket2, llBoxTicket3, llBoxTicket4, llBoxTicket5, llPaymentGateway, llPaymentWallet;
    private TextView tvStation_1, tvStation_2, tvStation_3, tvStation_4, tvStation_5;
    private TextView tvPerson_1, tvPerson_2, tvPerson_3, tvPerson_4, tvPerson_5;
    private ImageView imgDelete1, imgDelete2, imgDelete3, imgDelete4, imgDelete5;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private CheckBox cbCondition;
    private View llConfirm, llInVisible;
    private MessageAlertDialog.OnConfirmListener listener;
    private String textStation = "", urlf;
    ArrayList<String> numbers = new ArrayList<String>();
    private int countRepetitive = 0;
    public String namePosition;
    String positionName, selectPositionId;
    Integer amountForPay;
    List<Viewers> infoViewers = new ArrayList<>();
    private List<Integer> ticketIdList;
    private PaymentTicketImpl paymentTicket;
    private RulesStadiumImpl rulesStadium;
    private Integer stadiumId;
    private Integer amountOneTicket;
    private boolean flagDelete = false;
    private int flagNumberDelete = 0;
    private PaymentMatchRequest paymentMatchRequest;
    private int counterPerson = 0;
    private NestedScrollView nested;
    private LinearLayout llTickets, llGateWaye;
    private EditText etPin2;

    //GateWaye
    private String url = "";
    private View rootView;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu, btnBuyWallet, btnBackWallet;
    private ArrayList<MatchItem> matchBuyable;

    private String amount = "";
    private String title = "";
    private int imageDrawable = 1;
    private String mobile = "";
    private TextView tvWallet, tvCardsShetab, tvGateway, tvAmount, tvTitlePay, tvAmountPay, tvTitlePayWallet;
    private ImageView imgLogo, imgLogoWallet;

    private SimChargePaymentInstance simChargePaymentInstance;
    private int PAYMENT_STATUS;
    private SimPackPaymentInstance simPackPaymentInstance;
    private PaymentGateWayParentActionView pActionView;

    private TextView tvTitlePayF, tvAmountF;
    private FrameLayout llConfirmff, btnBackF;
    private CircularProgressButton btnBuy;

    public static CompeletInfoFragment fragment;
    private PaymentWalletImpl paymentWallet;
    private View llSelectSpectator;
    private RecyclerView rvSpectatorList;
    private SpectatorListAdapter spectatorAdapter;
    private ArrayList<SpectatorInfoModel> selectedInfo;
    private View btnConfirmFilter;
    private View btnDeleteFilter;
    private View imgFilterClose;
    private EditText edtSearchFilter;
    private ArrayList<SpectatorInfoResponse> spectatorList;
    private TextView tvError;
    private ArrayList<SpectatorInfoResponse> spectatorListData=new ArrayList<>();
    private ArrayList<SpectatorInfoResponse> spectatorListDataFilter=new ArrayList<>();
    public String name,nationalCode;


    public CompeletInfoFragment()
    {
    }

    public static CompeletInfoFragment newInstance(MainActionView mainActionView
    )
    {
        CompeletInfoFragment fragment = new CompeletInfoFragment();

        return fragment;
    }


    /**
     * Receive the model list
     */
    public static CompeletInfoFragment newInstance(String s, OnClickContinueBuyTicket onClickContinueBuyTicket)
    {
        CompeletInfoFragment fragment = new CompeletInfoFragment();
        fragment.setOnClickContinueBuyTicket(onClickContinueBuyTicket);

        return fragment;
    }

    private void setOnClickContinueBuyTicket(OnClickContinueBuyTicket onClickContinueBuyTicket)
    {
        this.onClickContinueBuyTicketListener = onClickContinueBuyTicket;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }


    public void initGateWayView()
    {
        tvAmount = view.findViewById(R.id.tvAmount);
        tvTitlePay = view.findViewById(R.id.tvTitlePay);
        imgLogo = view.findViewById(R.id.imgLogo);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.pager);
        tvWallet = view.findViewById(R.id.tvWallet);
        tvCardsShetab = view.findViewById(R.id.tvCardsShetab);
        tvGateway = view.findViewById(R.id.tvGateway);
        tvWallet.setOnClickListener(this);
        tvCardsShetab.setOnClickListener(this);
        tvGateway.setOnClickListener(this);
        tvTitlePayF = view.findViewById(R.id.tvTitlePayF);
        tvAmountF = view.findViewById(R.id.tvAmountF);
        llConfirmff = view.findViewById(R.id.llConfirmff);
        btnBackF = view.findViewById(R.id.btnBackF);
        btnBackWallet = view.findViewById(R.id.btnBackWallet);
        btnBuy = view.findViewById(R.id.btnBuy);
        btnBuyWallet = view.findViewById(R.id.btnBuyWallet);
        btnBuyWallet.setOnClickListener(this);
        btnBackWallet.setOnClickListener(v ->
        {

            llGateWaye.setVisibility(View.GONE);
            llTickets.setVisibility(View.VISIBLE);
            ((BuyTicketsActivity) getActivity()).onBackPayment();

            if (slidingUpPanelLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED))
            {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                return;
            }

        });
        btnBackF.setOnClickListener(v ->
        {
            llGateWaye.setVisibility(View.GONE);
            llTickets.setVisibility(View.VISIBLE);
            ((BuyTicketsActivity) getActivity()).onBackPayment();

            if (slidingUpPanelLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED))
            {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                return;
            }

        });

        btnBuy.setOnClickListener(v ->
        {
            ((BuyTicketsActivity) getActivity()).openWebPayment(urlf);
        });


    }


    public void initView()
    {
        paymentMatchRequest = new PaymentMatchRequest();

        slidingUpPanelLayout = view.findViewById(R.id.slidingLayout);

        btnConfirmFilter = view.findViewById(R.id.btnConfirmFilter);
        btnConfirmFilter.setOnClickListener(this);
        imgFilterClose = view.findViewById(R.id.imgFilterClose);
        imgFilterClose.setOnClickListener(this);
        btnDeleteFilter = view.findViewById(R.id.btnDeleteFilter);
        btnDeleteFilter.setOnClickListener(this);
        rvSpectatorList = view.findViewById(R.id.rvSpectatorList);
        tvError = view.findViewById(R.id.tvError);

        edtSearchFilter = view.findViewById(R.id.edtSearchFilter);

        etNationalCode_1 = view.findViewById(R.id.etNationalCode_1);
        etFamily_1 = view.findViewById(R.id.etFamily_1);
        etName_1 = view.findViewById(R.id.etName_1);
        tvStation_1 = view.findViewById(R.id.tvStation_1);
        tvPerson_1 = view.findViewById(R.id.tvPerson_1);

        llSelectSpectator = view.findViewById(R.id.llSelectSpectator);
        llSelectSpectator.setOnClickListener(this);

        etNationalCode_2 = view.findViewById(R.id.etNationalCode_2);
        etFamily_2 = view.findViewById(R.id.etFamily_2);
        etName_2 = view.findViewById(R.id.etName_2);
        tvStation_2 = view.findViewById(R.id.tvStation_2);
        tvPerson_2 = view.findViewById(R.id.tvPerson_2);

        etNationalCode_3 = view.findViewById(R.id.etNationalCode_3);
        etFamily_3 = view.findViewById(R.id.etFamily_3);
        etName_3 = view.findViewById(R.id.etName_3);
        tvStation_3 = view.findViewById(R.id.tvStation_3);
        tvPerson_3 = view.findViewById(R.id.tvPerson_3);

        llTickets = view.findViewById(R.id.llTickets);
        llGateWaye = view.findViewById(R.id.llGateWaye);

        tvBalance = view.findViewById(R.id.tvBalance);
        tvDate = view.findViewById(R.id.tvDate);
        tvAmountPay = view.findViewById(R.id.tvAmountPay);
        tvTitlePayWallet = view.findViewById(R.id.tvTitlePayWallet);
        imgLogoWallet = view.findViewById(R.id.imgLogoWallet);
        etPin2 = view.findViewById(R.id.etPin2);

        llPaymentGateway = view.findViewById(R.id.llPaymentGateway);
        llPaymentWallet = view.findViewById(R.id.llPaymentWallet);


        etNationalCode_4 = view.findViewById(R.id.etNationalCode_4);
        etFamily_4 = view.findViewById(R.id.etFamily_4);
        etName_4 = view.findViewById(R.id.etName_4);
        tvStation_4 = view.findViewById(R.id.tvStation_4);
        tvPerson_4 = view.findViewById(R.id.tvPerson_4);


        etNationalCode_5 = view.findViewById(R.id.etNationalCode_5);
        etFamily_5 = view.findViewById(R.id.etFamily_5);
        etName_5 = view.findViewById(R.id.etName_5);
        tvStation_5 = view.findViewById(R.id.tvStation_5);
        tvPerson_5 = view.findViewById(R.id.tvPerson_5);


        llBoxTicket1 = view.findViewById(R.id.llBoxTicket1);
        llBoxTicket2 = view.findViewById(R.id.llBoxTicket2);
        llBoxTicket3 = view.findViewById(R.id.llBoxTicket3);
        llBoxTicket4 = view.findViewById(R.id.llBoxTicket4);
        llBoxTicket5 = view.findViewById(R.id.llBoxTicket5);

        if (textStation != null)
        {
            tvStation_1.setText(textStation);
            tvStation_2.setText(textStation);
            tvStation_3.setText(textStation);
            tvStation_4.setText(textStation);
            tvStation_5.setText(textStation);
        }
        if (count == 1)
        {
            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.GONE);
            llBoxTicket3.setVisibility(View.GONE);
            llBoxTicket4.setVisibility(View.GONE);
            llBoxTicket5.setVisibility(View.GONE);
        } else if (count == 2)
        {
            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.GONE);
            llBoxTicket4.setVisibility(View.GONE);
            llBoxTicket5.setVisibility(View.GONE);
        } else if (count == 3)
        {
            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.VISIBLE);
            llBoxTicket4.setVisibility(View.GONE);
            llBoxTicket5.setVisibility(View.GONE);
        } else if (count == 4)
        {
            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.VISIBLE);
            llBoxTicket4.setVisibility(View.VISIBLE);
            llBoxTicket5.setVisibility(View.GONE);
        } else if (count == 5)
        {
            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.VISIBLE);
            llBoxTicket4.setVisibility(View.VISIBLE);
            llBoxTicket5.setVisibility(View.VISIBLE);
        }


        txtCondition = view.findViewById(R.id.txtCondition);
        cbCondition = view.findViewById(R.id.cbCondition);
        llConfirm = view.findViewById(R.id.llConfirm);
        nested = view.findViewById(R.id.nested);
        llInVisible = view.findViewById(R.id.llInVisible);
        imgDelete1 = view.findViewById(R.id.imgDelete1);
        imgDelete2 = view.findViewById(R.id.imgDelete2);
        imgDelete3 = view.findViewById(R.id.imgDelete3);
        imgDelete4 = view.findViewById(R.id.imgDelete4);
        imgDelete5 = view.findViewById(R.id.imgDelete5);
        llConfirm.setVisibility(View.GONE);
        llInVisible.setVisibility(View.VISIBLE);


        btnBackToDetail = view.findViewById(R.id.btnBackToDetail);
        btnPaymentConfirm = view.findViewById(R.id.btnPaymentConfirm);
        imgDelete1.setOnClickListener(this);
        imgDelete2.setOnClickListener(this);
        imgDelete3.setOnClickListener(this);
        imgDelete4.setOnClickListener(this);
        imgDelete5.setOnClickListener(this);
        btnBackToDetail.setOnClickListener(this);
        btnPaymentConfirm.setOnClickListener(this);
        cbCondition.setOnClickListener(this);
        etNationalCode_1.setOnFocusChangeListener(this);
        etNationalCode_1.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                try
                {
                    if (etNationalCode_1.getText().toString().length() == 10 && NationalCodeValidation.isValidNationalCode(etNationalCode_1.getText().toString()))
                    {
                        requestSpectatorInfo(etNationalCode_1.getText().toString(), 1);
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

        etNationalCode_2.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                try
                {
                    if (etNationalCode_2.getText().toString().length() == 10 && NationalCodeValidation.isValidNationalCode(etNationalCode_2.getText().toString()))
                    {
                        requestSpectatorInfo(etNationalCode_2.getText().toString(), 2);
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

        etNationalCode_3.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                try
                {
                    if (etNationalCode_3.getText().toString().length() == 10 && NationalCodeValidation.isValidNationalCode(etNationalCode_3.getText().toString()))
                    {
                        requestSpectatorInfo(etNationalCode_3.getText().toString(), 3);
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

        etNationalCode_4.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                try
                {
                    if (etNationalCode_4.getText().toString().length() == 10 && NationalCodeValidation.isValidNationalCode(etNationalCode_4.getText().toString()))
                    {
                        requestSpectatorInfo(etNationalCode_4.getText().toString(), 4);
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

        etNationalCode_5.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                try
                {
                    if (etNationalCode_5.getText().toString().length() == 10 && NationalCodeValidation.isValidNationalCode(etNationalCode_5.getText().toString()))
                    {
                        requestSpectatorInfo(etNationalCode_5.getText().toString(), 5);
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
        etFamily_1.setOnFocusChangeListener(this);
        etName_1.setOnFocusChangeListener(this);

        etNationalCode_2.setOnFocusChangeListener(this);
        etFamily_2.setOnFocusChangeListener(this);
        etName_2.setOnFocusChangeListener(this);

        etNationalCode_3.setOnFocusChangeListener(this);
        etFamily_3.setOnFocusChangeListener(this);
        etName_3.setOnFocusChangeListener(this);

        etNationalCode_4.setOnFocusChangeListener(this);
        etFamily_4.setOnFocusChangeListener(this);
        etName_4.setOnFocusChangeListener(this);

        etNationalCode_5.setOnFocusChangeListener(this);
        etFamily_5.setOnFocusChangeListener(this);
        etName_5.setOnFocusChangeListener(this);

        txtCondition.setOnClickListener(this);

        clearAllEditText();
        //etNationalCode_1.setPrimaryColor(R.color._disable_color);
        etFamily_1.setPrimaryColor(R.color._disable_color);
        etName_1.setPrimaryColor(R.color._disable_color);
        // etNationalCode_2.setPrimaryColor(R.color._disable_color);
        etFamily_2.setPrimaryColor(R.color._disable_color);
        etName_2.setPrimaryColor(R.color._disable_color);
        // etNationalCode_3.setPrimaryColor(R.color._disable_color);
        etFamily_3.setPrimaryColor(R.color._disable_color);
        etName_3.setPrimaryColor(R.color._disable_color);
        //   etNationalCode_4.setPrimaryColor(R.color._disable_color);
        etFamily_4.setPrimaryColor(R.color._disable_color);
        etName_4.setPrimaryColor(R.color._disable_color);
        //  etNationalCode_5.setPrimaryColor(R.color._disable_color);
        etFamily_5.setPrimaryColor(R.color._disable_color);
        etName_5.setPrimaryColor(R.color._disable_color);

        requestGetSpectatorListInfo();

        edtSearchFilter.addTextChangedListener(new TextWatcher()
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
                try
                {
                    if (TextUtils.isEmpty(edtSearchFilter.getText().toString()))
                    {

                        //   KeyboardUtils.forceCloseKeyboard(edtSearchFilter);
                       // filter("");
                        tvError.setVisibility(View.GONE);
                        rvSpectatorList.setVisibility(View.VISIBLE);
                        spectatorListData.clear();
                        spectatorListData.addAll(spectatorListDataFilter);
                        spectatorAdapter.notifyDataSetChanged();


                    } else
                    {
                        filter(edtSearchFilter.getText().toString());
                    }
                } catch (Exception e)
                {

                }


            }
        });

    }

    public void filter(String text)
    {
        Observable
                .fromIterable(spectatorList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .filter(x ->
                        {
                            return x.getNationalCode().contains(text) || x.getFirstName().contains(text) || x.getLastName().contains(text);
                        }

                )
                .toList()
                .subscribe(new SingleObserver<List<SpectatorInfoResponse>>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                    }

                    @Override
                    public void onSuccess(List<SpectatorInfoResponse> results)
                    {
                        tvError.setVisibility(View.GONE);
                        rvSpectatorList.setVisibility(View.VISIBLE);
                        rvSpectatorList.removeAllViews();

                       // spectatorAdapter = new SpectatorListAdapter(results, CompeletInfoFragment.this, count);
                        spectatorListData.clear();
                        spectatorListData.addAll(results);
                        spectatorAdapter.notifyDataSetChanged();

                        if (results.size() == 0)
                        {
                            spectatorListData.clear();
                            spectatorListData.addAll(spectatorListDataFilter);
                            spectatorAdapter.notifyDataSetChanged();

                            tvError.setVisibility(View.VISIBLE);
                            rvSpectatorList.setVisibility(View.GONE);

                        }

                    }

                    @Override
                    public void onError(Throwable e)
                    {
                    }
                });
    }

    private void onGetSpectatorListSuccess(ArrayList<SpectatorInfoResponse> results)
    {
        ArrayList<String> nationalcodesList = new ArrayList<>();

        for (int i = 0; i < results.size(); i++)
        {
            nationalcodesList.add(i, results.get(i).getNationalCode());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), R.layout.custom_spinner_dropdown_item, nationalcodesList);
        etNationalCode_1.setThreshold(1);
        etNationalCode_1.setAdapter(adapter);

        etNationalCode_2.setThreshold(1);
        etNationalCode_2.setAdapter(adapter);

        etNationalCode_3.setThreshold(1);
        etNationalCode_3.setAdapter(adapter);

        etNationalCode_4.setThreshold(1);
        etNationalCode_4.setAdapter(adapter);

        etNationalCode_5.setThreshold(1);
        etNationalCode_5.setAdapter(adapter);

    }


    private void requestGetSpectatorListInfo()
    {

        ((BuyTicketsActivity) getActivity()).showLoading();

        SingletonService.getInstance().getSpectatorInfoService().getSpectatorList(new OnServiceStatus<WebServiceClass<GetSpectatorListResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetSpectatorListResponse> response)
            {
                ((BuyTicketsActivity) getActivity()).hideLoading();

                try
                {
                    if (response.info.statusCode == 200)
                    {
                        if (response.data != null)
                        {
                            if (response.data.getResults().size() > 0)
                            {
                                spectatorList = response.data.getResults();
                                setDataSpectatorList(response);
                                onGetSpectatorListSuccess(response.data.getResults());
                            }
                            else
                            {
                             //   showAlertFailure(context, "خطا در دریافت اطلاعات از سرور!", "خطا!", true);
                            }
                        }
                        else
                        {
                          //  showAlertFailure(context, "خطا در دریافت اطلاعات از سرور!", "خطا!", true);
                        }
                    }
                    else
                    {
                     //   showAlertFailure(context, "خطا در دریافت اطلاعات از سرور!", "خطا!", true);
                    }
                }
                catch (Exception e)
                {
                  //  showAlertFailure(context, "خطا در دریافت اطلاعات از سرور!", "خطا!", true);
                }
            }

            @Override
            public void onError(String message)
            {
                ((BuyTicketsActivity) getActivity()).hideLoading();

                if (Tools.isNetworkAvailable((Activity) context))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(context, "خطا در دریافت اطلاعات از سرور!");
                }
                else
                {
                    showAlert(context, R.string.networkErrorMessage, R.string.networkError);
                }

            }
        });

    }

    private void setDataSpectatorList(WebServiceClass<GetSpectatorListResponse> response)
    {
        if (response.data.getResults().isEmpty())
        {
            rvSpectatorList.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
        }
        rvSpectatorList.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
        spectatorListData = response.data.getResults();
        spectatorListDataFilter.addAll( response.data.getResults());
    }

    private void requestSpectatorInfo(String nationalCode, Integer number)
    {
        ((BuyTicketsActivity) getActivity()).showLoading();

        SingletonService.getInstance().getSpectatorInfoService().getSpectatorInfo(nationalCode, new OnServiceStatus<WebServiceClass<SpectatorInfoResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<SpectatorInfoResponse> response)
            {
                ((BuyTicketsActivity) getActivity()).hideLoading();

                try
                {

                    if (response.info.statusCode == 200)
                    {
                        setDataSpectorInfo(response, number);
                    } else
                    {
                    }
                } catch (Exception e)
                {

                }
            }

            @Override
            public void onError(String message)
            {
                ((BuyTicketsActivity) getActivity()).hideLoading();

                if (Tools.isNetworkAvailable((Activity) context))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(context, "خطا در دریافت اطلاعات از سرور!");
                } else
                {
                    showAlert(context, R.string.networkErrorMessage, R.string.networkError);
                }

            }
        });

    }

    private void setDataSpectorInfo(WebServiceClass<SpectatorInfoResponse> response, Integer number)
    {
        if (number == 1)
        {
            etName_1.setText(response.data.getFirstName());
            etFamily_1.setText(response.data.getLastName());
        } else if (number == 2)
        {
            etName_2.setText(response.data.getFirstName());
            etFamily_2.setText(response.data.getLastName());
        } else if (number == 3)
        {
            etName_3.setText(response.data.getFirstName());
            etFamily_3.setText(response.data.getLastName());
        } else if (number == 4)
        {
            etName_4.setText(response.data.getFirstName());
            etFamily_4.setText(response.data.getLastName());
        } else if (number == 5)
        {
            etName_5.setText(response.data.getFirstName());
            etFamily_5.setText(response.data.getLastName());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.complete_info_fragment, container, false);

        listener = new MessageAlertDialog.OnConfirmListener()
        {


            @Override
            public void onConfirmClick()
            {
                if (flagDelete)
                {
                    //check condition for delete  Different modes
                    checkCondition();

                    changePersonCounterName();
                    removeSpectator(name,nationalCode);
                } else
                {
                    cbCondition.setChecked(true);
                    llConfirm.setVisibility(View.VISIBLE);
                    llInVisible.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelClick()
            {
                if (flagDelete)
                {

                } else
                {
                    cbCondition.setChecked(false);
                    llConfirm.setVisibility(View.GONE);
                    llInVisible.setVisibility(View.VISIBLE);
                }
                // mainView.backToMainFragment();
            }
        };
        paymentTicket = new PaymentTicketImpl();
        paymentWallet = new PaymentWalletImpl();
        rulesStadium = new RulesStadiumImpl();
        initView();
        initGateWayView();
        return view;
    }

    private void changePersonCounterName()
    {
        counterPerson = 0;
        if (llBoxTicket1.getVisibility() == View.VISIBLE)
        {
            if (counterPerson == 0)
            {
                tvPerson_1.setText("اطلاعات نفر" + "اول");
            }
            if (counterPerson == 1)
            {
                tvPerson_1.setText("اطلاعات نفر" + "دوم");
            }
            if (counterPerson == 2)
            {
                tvPerson_1.setText("اطلاعات نفر" + "سوم");
            }
            if (counterPerson == 3)
            {
                tvPerson_1.setText("اطلاعات نفر" + "چهارم");
            }
            if (counterPerson == 4)
            {
                tvPerson_1.setText("اطلاعات نفر" + "پنجم");
            }

            counterPerson = counterPerson + 1;
        }
        if (llBoxTicket2.getVisibility() == View.VISIBLE)
        {
            if (counterPerson == 0)
            {
                tvPerson_2.setText("اطلاعات نفر" + "اول");
            }
            if (counterPerson == 1)
            {
                tvPerson_2.setText("اطلاعات نفر" + "دوم");
            }
            if (counterPerson == 2)
            {
                tvPerson_2.setText("اطلاعات نفر" + "سوم");
            }
            if (counterPerson == 3)
            {
                tvPerson_2.setText("اطلاعات نفر" + "چهارم");
            }
            if (counterPerson == 4)
            {
                tvPerson_2.setText("اطلاعات نفر" + "پنجم");
            }

            counterPerson = counterPerson + 1;
        }
        if (llBoxTicket3.getVisibility() == View.VISIBLE)
        {
            if (counterPerson == 0)
            {
                tvPerson_3.setText("اطلاعات نفر " + "اول");
            }
            if (counterPerson == 1)
            {
                tvPerson_3.setText("اطلاعات نفر " + "دوم");
            }
            if (counterPerson == 2)
            {
                tvPerson_3.setText("اطلاعات نفر " + "سوم");
            }
            if (counterPerson == 3)
            {
                tvPerson_3.setText("اطلاعات نفر " + "چهارم");
            }
            if (counterPerson == 4)
            {
                tvPerson_3.setText("اطلاعات نفر " + "پنجم");
            }

            counterPerson = counterPerson + 1;
        }
        if (llBoxTicket4.getVisibility() == View.VISIBLE)
        {
            if (counterPerson == 0)
            {
                tvPerson_4.setText("اطلاعات نفر اول");
            }
            if (counterPerson == 1)
            {
                tvPerson_4.setText("اطلاعات نفر دوم");
            }
            if (counterPerson == 2)
            {
                tvPerson_4.setText("اطلاعات نفر سوم");
            }
            if (counterPerson == 3)
            {
                tvPerson_4.setText("اطلاعات نفر چهارم");
            }
            if (counterPerson == 4)
            {
                tvPerson_4.setText("اطلاعات نفر " + "پنجم");
            }

            counterPerson = counterPerson + 1;
        }
        if (llBoxTicket5.getVisibility() == View.VISIBLE)
        {
            if (counterPerson == 0)
            {
                tvPerson_5.setText("اطلاعات نفر " + "اول");
            }
            if (counterPerson == 1)
            {
                tvPerson_5.setText("اطلاعات نفر " + "دوم");
            }
            if (counterPerson == 2)
            {
                tvPerson_5.setText("اطلاعات نفر " + "سوم");
            }
            if (counterPerson == 3)
            {
                tvPerson_5.setText("اطلاعات نفر " + "چهارم");
            }
            if (counterPerson == 4)
            {
                tvPerson_5.setText("اطلاعات نفر " + "پنجم");
            }

            counterPerson = counterPerson + 1;
        }
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {

            case R.id.llSelectSpectator:

                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

                //requestGetSpectatorListInfo();

                break;

            case R.id.btnConfirmFilter:

                setSpectatorListData();
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                etNationalCode_1.dismissDropDown();
                etNationalCode_2.dismissDropDown();
                etNationalCode_3.dismissDropDown();
                etNationalCode_4.dismissDropDown();
                etNationalCode_5.dismissDropDown();


                new Handler().postDelayed(() ->
                {
                    hideKeyboard((Activity) context);
                }, 500);
                break;
            case R.id.btnDeleteFilter:
                etNationalCode_1.dismissDropDown();
                etNationalCode_2.dismissDropDown();
                etNationalCode_3.dismissDropDown();
                etNationalCode_4.dismissDropDown();
                etNationalCode_5.dismissDropDown();

                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                new Handler().postDelayed(() ->
                {
                    hideKeyboard((Activity) context);
                }, 500);
                break;

            case R.id.imgFilterClose:
                etNationalCode_1.dismissDropDown();
                etNationalCode_2.dismissDropDown();
                etNationalCode_3.dismissDropDown();
                etNationalCode_4.dismissDropDown();
                etNationalCode_5.dismissDropDown();

                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                new Handler().postDelayed(() ->
                {
                    hideKeyboard((Activity) context);
                }, 500);

                break;

            case R.id.tvGateway:
                // viewPager.setCurrentItem(0, true);
                llPaymentGateway.setVisibility(View.VISIBLE);
                llPaymentWallet.setVisibility(View.GONE);
                tvGateway.setBackgroundResource(R.drawable.background_border_a);
                tvWallet.setBackgroundColor(Color.TRANSPARENT);
                tvCardsShetab.setBackgroundColor(Color.TRANSPARENT);
                tvWallet.setTextColor(getResources().getColor(R.color.returnButtonColor));
                tvGateway.setTextColor(getResources().getColor(R.color.borderColorRed));
                tvCardsShetab.setTextColor(getResources().getColor(R.color.gray));
                break;

            case R.id.tvWallet:
                //viewPager.setCurrentItem(2, true);
                requestGetBalance();
                llPaymentGateway.setVisibility(View.GONE);
                llPaymentWallet.setVisibility(View.VISIBLE);
                tvWallet.setBackgroundResource(R.drawable.background_border_a);
                tvCardsShetab.setBackgroundColor(Color.TRANSPARENT);
                tvGateway.setBackgroundColor(Color.TRANSPARENT);
                tvWallet.setTextColor(getResources().getColor(R.color.borderColorRed));
                tvCardsShetab.setTextColor(getResources().getColor(R.color.gray));
                tvGateway.setTextColor(getResources().getColor(R.color.returnButtonColor));
                break;

            case R.id.btnBuyWallet:

                if (etPin2.getText().toString() != null && etPin2.getText().toString().length() > 3)
                {
                    ((BuyTicketsActivity) getActivity()).showLoading();

                    // paymentMatchRequest.setViewers(infoViewers);
                    paymentMatchRequest.setPin2(etPin2.getText().toString());
                    paymentWallet.paymentWalletRequest(this, paymentMatchRequest);

                } else
                {
                    showAlertFailure(context, "رمز کارت وارد نشده است.", "", false);

                }


                break;
            case R.id.imgDelete1:
                if (count == 1)
                {
                    // imgDelete1.setVisibility(View.GONE);

                } else
                {
                    flagDelete = true;
                    flagNumberDelete = 1;
                    name=etName_1.getText().toString();
                    nationalCode=etNationalCode_1.getText().toString();
                    showDialogDelete();

                }
                break;
            case R.id.imgDelete2:
                SingletonNeedGetAllBoxesRequest.getInstance().needRequest = true;
                if (count == 1)
                {
                    //  imgDelete2.setVisibility(View.GONE);

                } else
                {
                    flagDelete = true;
                    flagNumberDelete = 2;
                    showDialogDelete();
                    name=etName_2.getText().toString();
                    nationalCode=etNationalCode_2.getText().toString();
                }
                break;
            case R.id.imgDelete3:
                SingletonNeedGetAllBoxesRequest.getInstance().needRequest = true;

                if (count == 1)
                {
                    // imgDelete3.setVisibility(View.GONE);

                } else
                {
                    flagDelete = true;
                    flagNumberDelete = 3;
                    showDialogDelete();
                    name=etName_3.getText().toString();
                    nationalCode=etNationalCode_3.getText().toString();
                }
                break;
            case R.id.imgDelete4:
                SingletonNeedGetAllBoxesRequest.getInstance().needRequest = true;

                if (count == 1)
                {
                    //  imgDelete4.setVisibility(View.GONE);
                } else
                {
                    flagDelete = true;
                    flagNumberDelete = 4;
                    showDialogDelete();
                    name=etName_4.getText().toString();
                    nationalCode=etNationalCode_4.getText().toString();
                }
                break;
            case R.id.imgDelete5:
                SingletonNeedGetAllBoxesRequest.getInstance().needRequest = true;

                if (count == 1)
                {
                    //imgDelete5.setVisibility(View.GONE);

                } else
                {
                    flagDelete = true;
                    flagNumberDelete = 5;
                    showDialogDelete();
                    name=etName_5.getText().toString();
                    nationalCode=etNationalCode_5.getText().toString();
                }
                break;
            case R.id.btnPaymentConfirm:
                checkValidation();
                break;
            case R.id.btnBackToDetail:
                infoViewers.clear();

                Prefs.putInt("CountTicket", count);
                onClickContinueBuyTicketListener.onBackClicked();

                if (slidingUpPanelLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED))
                {
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    return;
                }

                break;

            case R.id.txtCondition:
                callRulsStadiumRequest();

                break;
            case R.id.cbCondition:
                if (((CheckBox) view).isChecked())
                {
                    llConfirm.setVisibility(View.VISIBLE);
                    llInVisible.setVisibility(View.GONE);

                } else
                {
                    llConfirm.setVisibility(View.GONE);
                    llInVisible.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public void removeSpectator(String name,String nationalCode){

        for (int i = 0; i <spectatorListData.size() ; i++)
        {
            if (spectatorListData.get(i).getFirstName().equals(name)&&spectatorListData.get(i).getNationalCode().equals(nationalCode)){

                spectatorListData.get(i).setChecked(false);
            }



        }
        spectatorAdapter.notifyDataSetChanged();
    }

    private void setSpectatorListData()
    {
        try
        {
           /* if (llBoxTicket1.getVisibility()== View.VISIBLE)
            {
                etName_1.setText(selectedInfo.get(0).getFirstName());
                etFamily_1.setText(selectedInfo.get(0).getLastName());
                etNationalCode_1.setText(selectedInfo.get(0).getNationalCode());
            }else if (llBoxTicket2.getVisibility()== View.VISIBLE){
                etName_2.setText(selectedInfo.get(0).getFirstName());
                etFamily_2.setText(selectedInfo.get(0).getLastName());
                etNationalCode_2.setText(selectedInfo.get(0).getNationalCode());
            }
*/
            etName_1.setText(selectedInfo.get(0).getFirstName());
            etFamily_1.setText(selectedInfo.get(0).getLastName());
            etNationalCode_1.setText(selectedInfo.get(0).getNationalCode());

            etName_2.setText(selectedInfo.get(1).getFirstName());
            etFamily_2.setText(selectedInfo.get(1).getLastName());
            etNationalCode_2.setText(selectedInfo.get(1).getNationalCode());

            etName_3.setText(selectedInfo.get(2).getFirstName());
            etFamily_3.setText(selectedInfo.get(2).getLastName());
            etNationalCode_3.setText(selectedInfo.get(2).getNationalCode());

            etName_4.setText(selectedInfo.get(3).getFirstName());
            etFamily_4.setText(selectedInfo.get(3).getLastName());
            etNationalCode_4.setText(selectedInfo.get(3).getNationalCode());

            etName_5.setText(selectedInfo.get(4).getFirstName());
            etFamily_5.setText(selectedInfo.get(4).getLastName());
            etNationalCode_5.setText(selectedInfo.get(4).getNationalCode());


        } catch (Exception e)
        {

        }
    }


    private void requestGetBalance()
    {
        ((BuyTicketsActivity) getActivity()).showLoading();
        GetBalancePasswordLessRequest request = new GetBalancePasswordLessRequest();
        request.setIsWallet(true);
        SingletonService.getInstance().getBalancePasswordLessService().GetBalancePasswordLessService(new OnServiceStatus<WebServiceClass<GetBalancePasswordLessResponse>>()
        {


            @Override
            public void onReady(WebServiceClass<GetBalancePasswordLessResponse> response)
            {
                ((BuyTicketsActivity) getActivity()).hideLoading();

                try
                {
                    if (response.info.statusCode == 200)
                    {
                        setBalanceData(response.data);

                    } else
                    {

                        ((BuyTicketsActivity) getActivity()).showError(response.info.message);


                    }
                } catch (Exception e)
                {
                    ((BuyTicketsActivity) getActivity()).showError(e.getMessage());


                }


            }

            @Override
            public void onError(String message)
            {
                ((BuyTicketsActivity) getActivity()).hideLoading();

                ((BuyTicketsActivity) getActivity()).showError(message);

            }
        }, request);
    }

    private void setBalanceData(GetBalancePasswordLessResponse data)
    {
        tvBalance.setText(Utility.priceFormat(data.getBalanceAmount()));
        tvDate.setText(data.getDateTime());
    }

    private void showDialogDelete()
    {
        MessageAlertDialog dialog = new MessageAlertDialog(getActivity(),
                "حذف بلیت", "آیا از حذف این بلیت مطمئن هستید؟",
                true, "بله", "خیر", MessageAlertDialog.TYPE_MESSAGE, listener);
        dialog.show(getActivity().getFragmentManager(), "dialog");
    }

    private void checkCondition()
    {

        if (flagNumberDelete == 1)
        {

            llBoxTicket1.setVisibility(View.GONE);
            count = count - 1;
            Prefs.putInt("CountTicket", count);
            amountForPay = amountForPay - amountOneTicket;
            if (count == 1)
            {
                goneImgDelete();

            }

        }
        if (flagNumberDelete == 2)
        {

            llBoxTicket2.setVisibility(View.GONE);
            count = count - 1;
            Prefs.putInt("CountTicket", count);
            amountForPay = amountForPay - amountOneTicket;
            if (count == 1)
            {
                goneImgDelete();

            }
        }
        if (flagNumberDelete == 3)
        {

            llBoxTicket3.setVisibility(View.GONE);
            count = count - 1;
            Prefs.putInt("CountTicket", count);
            amountForPay = amountForPay - amountOneTicket;
            if (count == 1)
            {
                goneImgDelete();

            }
        }
        if (flagNumberDelete == 4)
        {

            llBoxTicket4.setVisibility(View.GONE);
            count = count - 1;
            Prefs.putInt("CountTicket", count);
            amountForPay = amountForPay - amountOneTicket;
            if (count == 1)
            {
                goneImgDelete();

            }
        }
        if (flagNumberDelete == 5)
        {

            llBoxTicket5.setVisibility(View.GONE);
            count = count - 1;
            Prefs.putInt("CountTicket", count);
            amountForPay = amountForPay - amountOneTicket;
            if (count == 1)
            {
                goneImgDelete();

            }
        }

    }

    private void goneImgDelete()
    {
        if (llBoxTicket1.getVisibility() == View.VISIBLE)
        {
            imgDelete1.setVisibility(View.GONE);
            return;
        } else if (llBoxTicket2.getVisibility() == View.VISIBLE)
        {
            imgDelete2.setVisibility(View.GONE);
            return;
        } else if (llBoxTicket3.getVisibility() == View.VISIBLE)
        {
            imgDelete3.setVisibility(View.GONE);
            return;
        } else if (llBoxTicket4.getVisibility() == View.VISIBLE)
        {
            imgDelete4.setVisibility(View.GONE);
            return;
        } else if (llBoxTicket5.getVisibility() == View.VISIBLE)
        {
            imgDelete5.setVisibility(View.GONE);
            return;
        }


    }

    private void clearAllEditText()
    {
        etNationalCode_1.setText("");
        etFamily_1.setText("");
        etName_1.setText("");

        etNationalCode_2.setText("");
        etFamily_2.setText("");
        etName_2.setText("");

        etNationalCode_3.setText("");
        etFamily_3.setText("");
        etName_3.setText("");

        etNationalCode_4.setText("");
        etFamily_4.setText("");
        etName_4.setText("");

        etNationalCode_5.setText("");
        etFamily_5.setText("");
        etName_5.setText("");
    }

    private void checkValidation()
    {
        String flagValidations = "";
        countRepetitive = 0;
        numbers = new ArrayList<String>();
        if (count == 1)
        {
            if (llBoxTicket1.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerOne();
            }
            if (llBoxTicket2.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerSecond();
            }
            if (llBoxTicket3.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerThird();
            }
            if (llBoxTicket4.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFourth();
            }
            if (llBoxTicket5.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFifth();
            }

        } else if (count == 2)
        {
            if (llBoxTicket1.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerOne();
            }
            if (llBoxTicket2.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerSecond();
            }
            if (llBoxTicket3.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerThird();
            }
            if (llBoxTicket4.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFourth();
            }
            if (llBoxTicket5.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFifth();
            }

        } else if (count == 3)
        {

            if (llBoxTicket1.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerOne();
            }
            if (llBoxTicket2.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerSecond();
            }
            if (llBoxTicket3.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerThird();
            }
            if (llBoxTicket4.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFourth();
            }
            if (llBoxTicket5.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFifth();
            }
        } else if (count == 4)
        {

            if (llBoxTicket1.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerOne();
            }
            if (llBoxTicket2.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerSecond();
            }
            if (llBoxTicket3.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerThird();
            }
            if (llBoxTicket4.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFourth();
            }
            if (llBoxTicket5.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFifth();
            }
        } else if (count == 5)
        {

            if (llBoxTicket1.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerOne();
            }
            if (llBoxTicket2.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerSecond();
            }
            if (llBoxTicket3.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerThird();
            }
            if (llBoxTicket4.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFourth();
            }
            if (llBoxTicket5.getVisibility() == View.VISIBLE)
            {
                flagValidations = flagValidations + PassengerFifth();
            }
        }


        if (flagValidations.contains("F"))
        {
            // ((BuyTicketsActivity) getActivity()).showError(getString(R.string.Error_edit_input));
            cbCondition.setChecked(true);
            llConfirm.setVisibility(View.VISIBLE);
            llInVisible.setVisibility(View.GONE);
            infoViewers.clear();
        } /*else if (countRepetitive > count)
        {
            mainView.showError(getString(R.string.Error_nationall_code_input));
            cbCondition.setChecked(true);
            llConfirm.setVisibility(View.VISIBLE);
            llInVisible.setVisibility(View.GONE);
            infoViewers.clear();

        } */ else
        {
            callPaymentTicketRequest();

            //BuyTicketsFragment.buyTicketsFragment.setInfoViewers(infoViewers);


        }
    }

    private void callRulsStadiumRequest()
    {
        ((BuyTicketsActivity) getActivity()).showLoading();
        rulesStadium.rulesStadiumRequest(this, stadiumId);
    }

    private void callPaymentTicketRequest()
    {
        ((BuyTicketsActivity) getActivity()).showLoading();
        paymentTicket.paymentTicketRequest(this, infoViewers, amountForPay);

    }


    private String PassengerOne()
    {
        String flagValidations = "";

        if (TextUtils.isEmpty(etNationalCode_1.getText().toString()))
        {
            etNationalCode_1.setError(getString(R.string.Please_enter_the_national_code_null));
            flagValidations = flagValidations + "F";
        } else if (etNationalCode_1.getText().toString() != null)
        {
            if (NationalCodeValidation.isValidNationalCode(etNationalCode_1.getText().toString()))
            {
                flagValidations = flagValidations + "T";
                etNationalCode_1.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etNationalCode_1", etNationalCode_1.getText().toString());
                numbers.add(etNationalCode_1.getText().toString());
                countRepetitive = countRepetitive + Collections.frequency(numbers, etNationalCode_1.getText().toString());

            } else if (etNationalCode_1.getText().toString().replace(" ", "").length() < 1)
            {
                flagValidations = flagValidations + "F";
                etNationalCode_1.setError(getString(R.string.Please_enter_the_national_code_null));
            } else
            {
                flagValidations = flagValidations + "F";
                etNationalCode_1.setError(getString(R.string.Please_enter_the_national_code));

            }
        }


        if (TextUtils.isEmpty(etFamily_1.getText().toString().replaceAll(" ", "")))
        {
            etFamily_1.setError(getString(R.string.Please_enter_last_name_in_Persian_null));
            flagValidations = flagValidations + "F";
        } else if (etFamily_1.getText().toString() != null)
        {

            //   if (etFamily_1.getText().toString().length() > 1 && !(etFamily_1.getText().toString().toLowerCase().matches("[0-9]")))
            if (etFamily_1.getText().toString().trim().length() > 1 && (etFamily_1.getText().toString().trim().matches("[آ-ی]+"))
                    || etFamily_1.getText().toString().trim().length() > 1 && (etFamily_1.getText().toString().trim().matches("[a-zA-Z]+") || etFamily_1.getText().toString().contains(" ")))
            {
                flagValidations = flagValidations + "T";
                etFamily_1.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etFamily_1", etFamily_1.getText().toString());

            } else if (etFamily_1.getText().toString().replace(" ", "").length() < 1)
            {
                flagValidations = flagValidations + "F";
                etFamily_1.setError(getString(R.string.Please_enter_last_name_in_Persian_null));
            } else
            {
                flagValidations = flagValidations + "F";
                etFamily_1.setError(getString(R.string.Please_enter_last_name_in_Persian));
            }

        }


        if (TextUtils.isEmpty(etName_1.getText().toString().replaceAll(" ", "")))
        {
            etName_1.setError(getString(R.string.Please_enter_name_in_Persian_null));
            flagValidations = flagValidations + "F";
        } else if (etName_1.getText().toString() != null)
        {

            // if (etName_1.getText().toString().length() > 1 && !(etName_1.getText().toString().toLowerCase().matches("[0-9]")))
            if (etName_1.getText().toString().trim().length() > 1 && (etName_1.getText().toString().trim().matches("[آ-ی]+"))
                    || etName_1.getText().toString().trim().length() > 1 && (etName_1.getText().toString().trim().matches("[a-zA-Z]+")) || etName_1.getText().toString().contains(" "))
            {

                flagValidations = flagValidations + "T";
                etName_1.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etName_1", etName_1.getText().toString());

            } else if (etName_1.getText().toString().replace(" ", "").length() < 1)
            {
                flagValidations = flagValidations + "F";
                etName_1.setError(getString(R.string.Please_enter_name_in_Persian_null));
            } else
            {
                flagValidations = flagValidations + "F";
                etName_1.setError(getString(R.string.Please_enter_name_in_Persian));
            }

        }
        if (flagValidations.contains("F"))
        {
            //((BuyTicketsActivity)getActivity()).showError("اطلاعات ورودی نامعتبر است");
            return "F";
        } else
        {
            Viewers viewer = new Viewers();
            viewer.setFirstName(etName_1.getText().toString());
            viewer.setLastName(etFamily_1.getText().toString());
            viewer.setNationalCode(etNationalCode_1.getText().toString());
            if (!ticketIdList.isEmpty())
            {
                viewer.setTicketId(ticketIdList.get(0));
            }
            infoViewers.add(viewer);
            return "T";

        }
    }

    private String PassengerSecond()
    {
        String flagValidations = "";
        if (TextUtils.isEmpty(etNationalCode_2.getText().toString()))
        {
            etNationalCode_2.setError(getString(R.string.Please_enter_the_national_code_null));
            flagValidations = flagValidations + "F";
        } else if (etNationalCode_2.getText().toString() != null)
        {
            if (NationalCodeValidation.isValidNationalCode(etNationalCode_2.getText().toString()))
            {
                flagValidations = flagValidations + "T";
                etNationalCode_2.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etNationalCode_2", etNationalCode_2.getText().toString());
                numbers.add(etNationalCode_2.getText().toString());
                countRepetitive = countRepetitive + Collections.frequency(numbers, etNationalCode_2.getText().toString());

            } else if (etNationalCode_2.getText().toString().replace(" ", "").length() < 1)
            {
                flagValidations = flagValidations + "F";
                etNationalCode_2.setError(getString(R.string.Please_enter_the_national_code_null));
            } else
            {
                flagValidations = flagValidations + "F";
                etNationalCode_2.setError(getString(R.string.Please_enter_the_national_code));
            }
        }


        if (TextUtils.isEmpty(etFamily_2.getText().toString().replaceAll(" ", "")))
        {
            etFamily_2.setError(getString(R.string.Please_enter_last_name_in_Persian_null));
            flagValidations = flagValidations + "F";
        } else if (etFamily_2.getText().toString() != null)
        {

            //  if (etFamily_2.getText().toString().length() > 1 && !(etFamily_2.getText().toString().toLowerCase().matches("[0-9]")))
            if (etFamily_2.getText().toString().trim().length() > 1 && (etFamily_2.getText().toString().trim().matches("[آ-ی]+"))
                    || etFamily_2.getText().toString().trim().length() > 1 && (etFamily_2.getText().toString().trim().matches("[a-zA-Z]+") || etFamily_2.getText().toString().contains(" ")))
            {
                flagValidations = flagValidations + "T";
                etFamily_2.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etFamily_2", etFamily_2.getText().toString());

            } else if (etFamily_2.getText().toString().replace(" ", "").length() < 1)
            {
                flagValidations = flagValidations + "F";
                etFamily_2.setError(getString(R.string.Please_enter_last_name_in_Persian_null));
            } else
            {
                flagValidations = flagValidations + "F";
                etFamily_2.setError(getString(R.string.Please_enter_last_name_in_Persian));
            }

        }


        if (TextUtils.isEmpty(etName_2.getText().toString().replaceAll(" ", "")))
        {
            etName_2.setError(getString(R.string.Please_enter_name_in_Persian_null));
            flagValidations = flagValidations + "F";
        } else if (etName_2.getText().toString() != null)
        {

            // if (etName_2.getText().toString().length() > 1 && !(etName_2.getText().toString().toLowerCase().matches("[0-9]")))
            if (etName_2.getText().toString().trim().length() > 1 && (etName_2.getText().toString().trim().matches("[آ-ی]+"))
                    || etName_2.getText().toString().trim().length() > 1 && (etName_2.getText().toString().trim().matches("[a-zA-Z]+")) || etName_2.getText().toString().contains(" "))
            {
                flagValidations = flagValidations + "T";
                etName_2.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etName_2", etName_2.getText().toString());

            } else if (etName_2.getText().toString().replace(" ", "").length() < 1)
            {
                flagValidations = flagValidations + "F";
                etName_2.setError(getString(R.string.Please_enter_name_in_Persian_null));
            } else
            {
                flagValidations = flagValidations + "F";
                etName_2.setError(getString(R.string.Please_enter_name_in_Persian));
            }

        }
        if (flagValidations.contains("F"))
        {
            // ((BuyTicketsActivity) getActivity()).showError("اطلاعات ورودی نامعتبر است");
            return "F";
        } else
        {
            Viewers viewer = new Viewers();
            viewer.setFirstName(etName_2.getText().toString());
            viewer.setLastName(etFamily_2.getText().toString());
            viewer.setNationalCode(etNationalCode_2.getText().toString());
            if (!ticketIdList.isEmpty() && ticketIdList.size() >= 2)
            {
                viewer.setTicketId(ticketIdList.get(1));
            }
            infoViewers.add(viewer);
            return "T";

        }
    }

    private String PassengerThird()
    {
        String flagValidations = "";
        if (TextUtils.isEmpty(etNationalCode_3.getText().toString()))
        {
            etNationalCode_3.setError(getString(R.string.Please_enter_the_national_code_null));
            flagValidations = flagValidations + "F";
        } else if (etNationalCode_3.getText().toString() != null)
        {
            if (NationalCodeValidation.isValidNationalCode(etNationalCode_3.getText().toString()))
            {
                flagValidations = flagValidations + "T";
                etNationalCode_3.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etNationalCode_3", etNationalCode_3.getText().toString());
                numbers.add(etNationalCode_3.getText().toString());
                countRepetitive = countRepetitive + Collections.frequency(numbers, etNationalCode_3.getText().toString());

            } else if (etNationalCode_3.getText().toString().replace(" ", "").length() < 1)
            {
                flagValidations = flagValidations + "F";
                etNationalCode_3.setError(getString(R.string.Please_enter_the_national_code_null));
            } else
            {
                flagValidations = flagValidations + "F";
                etNationalCode_3.setError(getString(R.string.Please_enter_the_national_code));
            }
        }

        if (TextUtils.isEmpty(etFamily_3.getText().toString().replaceAll(" ", "")))
        {
            etFamily_3.setError(getString(R.string.Please_enter_last_name_in_Persian_null));
            flagValidations = flagValidations + "F";
        } else if (etFamily_3.getText().toString() != null)
        {

            // if (etFamily_3.getText().toString().length() > 1 && !(etFamily_3.getText().toString().toLowerCase().matches("[0-9]")))
            if (etFamily_3.getText().toString().trim().length() > 1 && (etFamily_3.getText().toString().trim().matches("[آ-ی]+"))
                    || etFamily_3.getText().toString().trim().length() > 1 && (etFamily_3.getText().toString().trim().matches("[a-zA-Z]+") || etFamily_3.getText().toString().contains(" ")))
            {
                flagValidations = flagValidations + "T";
                etFamily_3.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etFamily_3", etFamily_3.getText().toString());

            } else if (etFamily_3.getText().toString().replace(" ", "").length() < 1)
            {
                flagValidations = flagValidations + "F";
                etFamily_3.setError(getString(R.string.Please_enter_last_name_in_Persian_null));
            } else
            {
                flagValidations = flagValidations + "F";
                etFamily_3.setError(getString(R.string.Please_enter_last_name_in_Persian));
            }

        }


        if (TextUtils.isEmpty(etName_3.getText().toString().replaceAll(" ", "")))
        {
            etName_3.setError(getString(R.string.Please_enter_name_in_Persian_null));
            flagValidations = flagValidations + "F";
        } else if (etName_3.getText().toString() != null)
        {

            // if (etName_3.getText().toString().length() > 1 && !(etName_3.getText().toString().toLowerCase().matches("[0-9]")))
            if (etName_3.getText().toString().trim().length() > 1 && (etName_3.getText().toString().trim().matches("[آ-ی]+"))
                    || etName_3.getText().toString().trim().length() > 1 && (etName_3.getText().toString().trim().matches("[a-zA-Z]+")) || etName_3.getText().toString().contains(" "))
            {
                flagValidations = flagValidations + "T";
                etName_3.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etName_3", etName_3.getText().toString());

            } else if (etName_3.getText().toString().replace(" ", "").length() < 1)
            {
                flagValidations = flagValidations + "F";
                etName_3.setError(getString(R.string.Please_enter_name_in_Persian_null));
            } else
            {
                flagValidations = flagValidations + "F";
                etName_3.setError(getString(R.string.Please_enter_name_in_Persian));
            }

        }
        if (flagValidations.contains("F"))
        {
            //((BuyTicketsActivity) getActivity()).showError("اطلاعات ورودی نامعتبر است");
            return "F";
        } else
        {
            Viewers viewer = new Viewers();
            viewer.setFirstName(etName_3.getText().toString());
            viewer.setLastName(etFamily_3.getText().toString());
            viewer.setNationalCode(etNationalCode_3.getText().toString());
            if (!ticketIdList.isEmpty() && ticketIdList.size() >= 3)
            {
                viewer.setTicketId(ticketIdList.get(2));
            }
            infoViewers.add(viewer);
            return "T";

        }
    }

    private String PassengerFourth()
    {
        String flagValidations = "";
        if (TextUtils.isEmpty(etNationalCode_4.getText().toString()))
        {
            etNationalCode_4.setError(getString(R.string.Please_enter_the_national_code_null));
            flagValidations = flagValidations + "F";
        } else if (etNationalCode_4.getText().toString() != null)
        {
            if (NationalCodeValidation.isValidNationalCode(etNationalCode_4.getText().toString()))
            {
                flagValidations = flagValidations + "T";
                etNationalCode_4.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etNationalCode_4", etNationalCode_4.getText().toString());
                numbers.add(etNationalCode_4.getText().toString());
                countRepetitive = countRepetitive + Collections.frequency(numbers, etNationalCode_4.getText().toString());

            } else if (etNationalCode_4.getText().toString().replace(" ", "").length() < 1)
            {
                flagValidations = flagValidations + "F";
                etNationalCode_4.setError(getString(R.string.Please_enter_the_national_code_null));
            } else
            {
                flagValidations = flagValidations + "F";
                etNationalCode_4.setError(getString(R.string.Please_enter_the_national_code));
            }
        }


        if (TextUtils.isEmpty(etFamily_4.getText().toString().replaceAll(" ", "")))
        {
            etFamily_4.setError(getString(R.string.Please_enter_last_name_in_Persian_null));
            flagValidations = flagValidations + "F";
        } else if (etFamily_4.getText().toString() != null)
        {

            // if (etFamily_4.getText().toString().length() > 1 && !(etFamily_4.getText().toString().toLowerCase().matches("[0-9]")))
            if (etFamily_4.getText().toString().trim().length() > 1 && (etFamily_4.getText().toString().trim().matches("[آ-ی]+"))
                    || etFamily_4.getText().toString().trim().length() > 1 && (etFamily_4.getText().toString().trim().matches("[a-zA-Z]+") || etFamily_4.getText().toString().contains(" ")))
            {
                flagValidations = flagValidations + "T";
                etFamily_4.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etFamily_4", etFamily_4.getText().toString());

            } else if (etFamily_4.getText().toString().replace(" ", "").length() < 1)
            {
                flagValidations = flagValidations + "F";
                etFamily_4.setError(getString(R.string.Please_enter_last_name_in_Persian_null));
            } else
            {
                flagValidations = flagValidations + "F";
                etFamily_4.setError(getString(R.string.Please_enter_last_name_in_Persian));
            }

        }


        if (TextUtils.isEmpty(etName_4.getText().toString().replaceAll(" ", "")))
        {
            etName_4.setError(getString(R.string.Please_enter_name_in_Persian_null));
            flagValidations = flagValidations + "F";
        } else if (etName_4.getText().toString() != null)
        {

            //if (etName_4.getText().toString().length() > 1 && !(etName_4.getText().toString().toLowerCase().matches("[0-9]")))
            if (etName_4.getText().toString().trim().length() > 1 && (etName_4.getText().toString().trim().matches("[آ-ی]+"))
                    || etName_4.getText().toString().trim().length() > 1 && (etName_4.getText().toString().trim().matches("[a-zA-Z]+")) || etName_4.getText().toString().contains(" "))
            {
                flagValidations = flagValidations + "T";
                etName_4.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etName_4", etName_4.getText().toString());

            } else if (etName_4.getText().toString().replace(" ", "").length() < 1)
            {
                flagValidations = flagValidations + "F";
                etName_4.setError(getString(R.string.Please_enter_name_in_Persian_null));
            } else
            {
                flagValidations = flagValidations + "F";
                etName_4.setError(getString(R.string.Please_enter_name_in_Persian));
            }

        }
        if (flagValidations.contains("F"))
        {
            //   ((BuyTicketsActivity) getActivity()).showError("اطلاعات ورودی نامعتبر است");
            return "F";
        } else
        {
            Viewers viewer = new Viewers();
            viewer.setFirstName(etName_4.getText().toString());
            viewer.setLastName(etFamily_4.getText().toString());
            viewer.setNationalCode(etNationalCode_4.getText().toString());
            if (!ticketIdList.isEmpty() && ticketIdList.size() >= 4)
            {
                viewer.setTicketId(ticketIdList.get(3));
            }
            infoViewers.add(viewer);
            return "T";

        }
    }

    private String PassengerFifth()
    {
        String flagValidations = "";
        if (TextUtils.isEmpty(etNationalCode_5.getText().toString()))
        {
            etNationalCode_5.setError(getString(R.string.Please_enter_the_national_code_null));
            flagValidations = flagValidations + "F";
        } else if (etNationalCode_5.getText().toString() != null)
        {
            if (NationalCodeValidation.isValidNationalCode(etNationalCode_5.getText().toString()))
            {
                flagValidations = flagValidations + "T";
                etNationalCode_5.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etNationalCode_5", etNationalCode_5.getText().toString());
                numbers.add(etNationalCode_5.getText().toString());
                countRepetitive = countRepetitive + Collections.frequency(numbers, etNationalCode_5.getText().toString());

            } else if (etNationalCode_5.getText().toString().replace(" ", "").length() < 1)
            {
                flagValidations = flagValidations + "F";
                etNationalCode_5.setError(getString(R.string.Please_enter_the_national_code_null));
            } else
            {
                flagValidations = flagValidations + "F";
                etNationalCode_5.setError(getString(R.string.Please_enter_the_national_code));
            }
        }


        if (TextUtils.isEmpty(etFamily_5.getText().toString().replaceAll(" ", "")))
        {
            etFamily_5.setError(getString(R.string.Please_enter_last_name_in_Persian_null));
            flagValidations = flagValidations + "F";
        } else if (etFamily_5.getText().toString() != null)
        {

            // if (etFamily_5.getText().toString().length() > 1 && !(etFamily_5.getText().toString().toLowerCase().matches("[0-9]")))
            if (etFamily_5.getText().toString().trim().length() > 1 && (etFamily_5.getText().toString().trim().matches("[آ-ی]+"))
                    || etFamily_5.getText().toString().trim().length() > 1 && (etFamily_5.getText().toString().trim().matches("[a-zA-Z]+") || etFamily_5.getText().toString().contains(" ")))
            {
                flagValidations = flagValidations + "T";
                etFamily_5.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etFamily_5", etFamily_5.getText().toString());

            } else if (etFamily_5.getText().toString().replace(" ", "").length() < 1)
            {
                flagValidations = flagValidations + "F";
                etFamily_5.setError(getString(R.string.Please_enter_last_name_in_Persian_null));
            } else
            {
                flagValidations = flagValidations + "F";
                etFamily_5.setError(getString(R.string.Please_enter_last_name_in_Persian));
            }

        }


        if (TextUtils.isEmpty(etName_5.getText().toString().replaceAll(" ", "")))
        {
            etName_5.setError(getString(R.string.Please_enter_name_in_Persian_null));
            flagValidations = flagValidations + "F";
        } else if (etName_5.getText().toString() != null)
        {

            // if (etName_5.getText().toString().length() > 1 && !(etName_5.getText().toString().toLowerCase().matches("[0-9]")))
            if (etName_5.getText().toString().trim().length() > 1 && (etName_5.getText().toString().trim().matches("[آ-ی]+"))
                    || etName_5.getText().toString().trim().length() > 1 && (etName_5.getText().toString().trim().matches("[a-zA-Z]+") || etName_5.getText().toString().contains(" ")))
            {
                flagValidations = flagValidations + "T";
                etName_5.setTextColor(Color.parseColor("#4d4d4d"));
                Prefs.putString("etName_5", etName_5.getText().toString());

            } else if (etName_5.getText().toString().replace(" ", "").length() < 1)
            {
                flagValidations = flagValidations + "F";
                etName_5.setError(getString(R.string.Please_enter_name_in_Persian_null));
            } else
            {
                flagValidations = flagValidations + "F";
                etName_5.setError(getString(R.string.Please_enter_name_in_Persian));
            }

        }
        if (flagValidations.contains("F"))
        {
            //((BuyTicketsActivity) getActivity()).showError("اطلاعات ورودی نامعتبر است");
            return "F";
        } else
        {
            Viewers viewer = new Viewers();
            viewer.setFirstName(etName_5.getText().toString());
            viewer.setLastName(etFamily_5.getText().toString());
            viewer.setNationalCode(etNationalCode_5.getText().toString());
            if (!ticketIdList.isEmpty() && ticketIdList.size() >= 5)
            {
                viewer.setTicketId(ticketIdList.get(4));
            }
            infoViewers.add(viewer);
            return "T";

        }
    }


    @Override
    public void onFocusChange(View v, boolean b)
    {

       /* switch (v.getId())
        {
            case R.id.etNationalCode_1:
                if (etNationalCode_1.getText().toString() != null)
                    if (NationalCodeValidation.isValidNationalCode(etNationalCode_1.getText().toString()))
                    {
                        etNationalCode_1.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etNationalCode_1.setError(getString(R.string.Please_enter_the_national_code));
                    }
                break;
            case R.id.etFamily_1:
                if (etFamily_1.getText().toString() != null)
                {

                    if (etFamily_1.getText().toString().length() > 1 && !(etFamily_1.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etFamily_1.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etFamily_1.setError(getString(R.string.Please_enter_last_name_in_Persian));
                    }

                }
                break;
            case R.id.etName_1:
                if (etName_1.getText().toString() != null)
                {

                    if (etName_1.getText().toString().length() > 1 && !(etName_1.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etName_1.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etName_1.setError(getString(R.string.Please_enter_name_in_Persian));
                    }

                }
                break;
            *//*PassengetSecond*//*
            case R.id.etNationalCode_2:
                if (etNationalCode_2.getText().toString() != null)
                    if (NationalCodeValidation.isValidNationalCode(etNationalCode_2.getText().toString()))
                    {
                        etNationalCode_2.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etNationalCode_2.setError(getString(R.string.Please_enter_the_national_code));
                    }
                break;
            case R.id.etFamily_2:
                if (etFamily_2.getText().toString() != null)
                {

                    if (etFamily_2.getText().toString().length() > 1 && !(etFamily_2.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etFamily_2.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etFamily_2.setError(getString(R.string.Please_enter_last_name_in_Persian));
                    }

                }
                break;
            case R.id.etName_2:
                if (etName_2.getText().toString() != null)
                {

                    if (etName_2.getText().toString().length() > 1 && !(etName_2.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etName_2.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etName_2.setError(getString(R.string.Please_enter_name_in_Persian));
                    }

                }
                break;
            *//*PassengetThird*//*
            case R.id.etNationalCode_3:
                if (etNationalCode_3.getText().toString() != null)
                    if (NationalCodeValidation.isValidNationalCode(etNationalCode_3.getText().toString()))
                    {
                        etNationalCode_3.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etNationalCode_3.setError(getString(R.string.Please_enter_the_national_code));
                    }
                break;
            case R.id.etFamily_3:
                if (etFamily_3.getText().toString() != null)
                {

                    if (etFamily_3.getText().toString().length() > 1 && !(etFamily_3.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etFamily_3.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etFamily_3.setError(getString(R.string.Please_enter_last_name_in_Persian));
                    }

                }
                break;
            case R.id.etName_3:
                if (etName_3.getText().toString() != null)
                {

                    if (etName_3.getText().toString().length() > 1 && !(etName_3.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etName_3.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etName_3.setError(getString(R.string.Please_enter_name_in_Persian));
                    }

                }
                break;
            *//*PassengetForthi*//*
            case R.id.etNationalCode_4:
                if (etNationalCode_4.getText().toString() != null)
                    if (NationalCodeValidation.isValidNationalCode(etNationalCode_4.getText().toString()))
                    {
                        etNationalCode_4.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etNationalCode_4.setError(getString(R.string.Please_enter_the_national_code));
                    }
                break;
            case R.id.etFamily_4:
                if (etFamily_4.getText().toString() != null)
                {

                    if (etFamily_4.getText().toString().length() > 1 && !(etFamily_4.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etFamily_4.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etFamily_4.setError(getString(R.string.Please_enter_last_name_in_Persian));
                    }

                }
                break;
            case R.id.etName_4:
                if (etName_4.getText().toString() != null)
                {

                    if (etName_4.getText().toString().length() > 1 && !(etName_4.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etName_4.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etName_4.setError(getString(R.string.Please_enter_name_in_Persian));
                    }

                }
                break;
            *//*PassengetFive*//*
            case R.id.etNationalCode_5:
                if (etNationalCode_5.getText().toString() != null)
                    if (NationalCodeValidation.isValidNationalCode(etNationalCode_5.getText().toString()))
                    {
                        etNationalCode_5.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etNationalCode_5.setError(getString(R.string.Please_enter_the_national_code));
                    }
                break;
            case R.id.etFamily_5:
                if (etFamily_5.getText().toString() != null)
                {

                    if (etFamily_5.getText().toString().length() > 1 && !(etFamily_5.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etFamily_5.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etFamily_5.setError(getString(R.string.Please_enter_last_name_in_Persian));
                    }

                }
                break;
            case R.id.etName_5:
                if (etName_5.getText().toString() != null)
                {

                    if (etName_5.getText().toString().length() > 1 && !(etName_5.getText().toString().toLowerCase().matches("[0-9]")))
                    {

                        etName_5.setTextColor(Color.parseColor("#4d4d4d"));

                    } else
                    {

                        etName_5.setError(getString(R.string.Please_enter_name_in_Persian));
                    }

                }
                break;
        }*/
    }

    public void getDataFormBefore(String selectPositionId, Integer count, Integer amountForPay, Integer amountOneTicket, List<Integer> ticketIdList, Integer stadiumId)
    {
        this.selectPositionId = selectPositionId;
        this.textStation = "جایگاه " + selectPositionId;
        this.count = count;
        this.amountForPay = amountForPay;
        this.amountOneTicket = amountOneTicket;
        this.ticketIdList = ticketIdList;
        this.stadiumId = stadiumId;
        Prefs.putInt("CountTicket", count);


        spectatorAdapter = new SpectatorListAdapter(getActivity(),spectatorListData, this, count);
        rvSpectatorList.setAdapter(spectatorAdapter);

        //  this.paymentMatchRequest = paymentMatchRequest;

        if (view == null)
        {
            return;
        }


        tvStation_1.setText(textStation);
        tvStation_2.setText(textStation);
        tvStation_3.setText(textStation);
        tvStation_4.setText(textStation);
        tvStation_5.setText(textStation);
        if (count == 1)
        {
            imgDelete1.setVisibility(View.GONE);

            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.GONE);
            llBoxTicket3.setVisibility(View.GONE);
            llBoxTicket4.setVisibility(View.GONE);
            llBoxTicket5.setVisibility(View.GONE);
        } else if (count == 2)
        {
            imgDelete1.setVisibility(View.VISIBLE);
            imgDelete2.setVisibility(View.VISIBLE);

            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.GONE);
            llBoxTicket4.setVisibility(View.GONE);
            llBoxTicket5.setVisibility(View.GONE);
        } else if (count == 3)
        {
            imgDelete1.setVisibility(View.VISIBLE);
            imgDelete2.setVisibility(View.VISIBLE);
            imgDelete3.setVisibility(View.VISIBLE);

            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.VISIBLE);
            llBoxTicket4.setVisibility(View.GONE);
            llBoxTicket5.setVisibility(View.GONE);
        } else if (count == 4)
        {
            imgDelete1.setVisibility(View.VISIBLE);
            imgDelete2.setVisibility(View.VISIBLE);
            imgDelete3.setVisibility(View.VISIBLE);
            imgDelete4.setVisibility(View.VISIBLE);

            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.VISIBLE);
            llBoxTicket4.setVisibility(View.VISIBLE);
            llBoxTicket5.setVisibility(View.GONE);
        } else if (count == 5)
        {
            imgDelete1.setVisibility(View.VISIBLE);
            imgDelete2.setVisibility(View.VISIBLE);
            imgDelete3.setVisibility(View.VISIBLE);
            imgDelete4.setVisibility(View.VISIBLE);
            imgDelete5.setVisibility(View.VISIBLE);

            llBoxTicket1.setVisibility(View.VISIBLE);
            llBoxTicket2.setVisibility(View.VISIBLE);
            llBoxTicket3.setVisibility(View.VISIBLE);
            llBoxTicket4.setVisibility(View.VISIBLE);
            llBoxTicket5.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onFinishedPaymentTicket(PaymentMatchResponse response)
    {
        ((BuyTicketsActivity) getActivity()).hideLoading();
        List<Viewers> infoViewers_n = new ArrayList<>(infoViewers);


        paymentMatchRequest.setViewers(infoViewers_n);

        paymentMatchRequest.setAmount(amountForPay);
        String title = "با انجام این پرداخت ، مبلغ" + Utility.priceFormat(Integer.toString(amountForPay)) + " ریال بابت خرید" + " " + count + " " + "بلیت بازی ازحساب شما کسر خواهد شد.";


        /*String title = "با انجام این پرداخت ، مبلغ" + Utility.priceFormat(Integer.toString(amountForPay)) + "ریال بابت خرید" + " " + count + " " + "بلیت بازی ازحساب شما کسر خواهد شد.";
        SelectPaymentGatewayFragment fragment2 = new SelectPaymentGatewayFragment(response.getUrl(), mainView, R.drawable.icon_payment_ticket,
                title, Utility.priceFormat(Integer.toString(amountForPay)),
                paymentMatchRequest);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment2);
        fragmentTransaction.commit();*/


        //((BuyTicketsActivity) getActivity()).openWebPayment(response.getUrl());
        llGateWaye.setVisibility(View.VISIBLE);
        llTickets.setVisibility(View.GONE);
        llPaymentWallet.setVisibility(View.GONE);
        llPaymentGateway.setVisibility(View.VISIBLE);

        tvGateway.setBackgroundResource(R.drawable.background_border_a);
        tvWallet.setBackgroundColor(Color.TRANSPARENT);
        tvCardsShetab.setBackgroundColor(Color.TRANSPARENT);
        tvWallet.setTextColor(getResources().getColor(R.color.returnButtonColor));
        tvGateway.setTextColor(getResources().getColor(R.color.borderColorRed));
        tvCardsShetab.setTextColor(getResources().getColor(R.color.gray));

        tvTitlePayF.setText(title);
        tvTitlePayWallet.setText(title);
        tvAmountPay.setText(Utility.priceFormat(Integer.toString(amountForPay)));
        tvAmountF.setText(Utility.priceFormat(Integer.toString(amountForPay)));
        infoViewers.clear();
        urlf = response.getUrl();
        ((BuyTicketsActivity) getActivity()).onPayment();

        // onClickContinueBuyTicketListener.onContinueClicked();
        // getActivity().finish();
    }

    @Override
    public void onErrorPaymentTicket(String error)
    {
        infoViewers.clear();
        ((BuyTicketsActivity) getActivity()).hideLoading();
        showToast(((Activity) context), error, R.color.red);

    }

    @Override
    public void onError(String message)
    {
        infoViewers.clear();
        ((BuyTicketsActivity) getActivity()).hideLoading();
        if (Tools.isNetworkAvailable(getActivity()))
        {
            ((BuyTicketsActivity) getActivity()).showError(message);
        } else
        {
            showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
        }
    }

    @Override
    public void onFinishedStadiumRules(ResponseStadiumRules response)
    {
        infoViewers.clear();

        ((BuyTicketsActivity) getActivity()).hideLoading();

        flagDelete = false;
        MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "قوانین و مقررات", response.getRules(), true,
                "تایید", "بستن", true, MessageAlertDialog.TYPE_MESSAGE, listener);

        dialog.show(getActivity().getFragmentManager(), "dialog");
    }

    @Override
    public void onErrorStadiumRules(String error)
    {
        ((BuyTicketsActivity) getActivity()).hideLoading();
        //showToast(((Activity) context), error, R.color.red);
        if (Tools.isNetworkAvailable(getActivity()))
        {
            Logger.e("-OnError-", "Error: " + error);
            ((BuyTicketsActivity) getActivity()).showError("خطا در دریافت اطلاعات از سرور!");
        } else
        {
            showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
        }
    }


    //tablayout
    private void createTabLayout(String amount, String title, int imageDrawable, String mobile, SimChargePaymentInstance simChargePaymentInstance, SimPackPaymentInstance simPackPaymentInstance)
    {
        // define TabLayout
        tabLayout.addTab(tabLayout.newTab().setText("درگاه بانکی"));
        tabLayout.addTab(tabLayout.newTab().setText("کارت"));
        tabLayout.addTab(tabLayout.newTab().setText("کیف پول"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


       /* SimChargePaymentInstance paymentInstance = new SimChargePaymentInstance();
        paymentInstance.setPAYMENT_STATUS(TrapConfig.PAYMENT_STAUS_ChargeSimCard);
        paymentInstance.setOperatorType(12);
        paymentInstance.setSimcardType(12);
        paymentInstance.setTypeCharge(Integer.valueOf(1));*/

        final SelectPaymentAdapter adapter = new SelectPaymentAdapter
                (getFragmentManager(), tabLayout.getTabCount(), null, amount, title, imageDrawable, mobile,
                        url, this.simChargePaymentInstance, simPackPaymentInstance, PAYMENT_STATUS);

        viewPager.setAdapter(adapter);
        //viewPager.beginFakeDrag();
        viewPager.setPagingEnabled(false);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
    }

    public void resetView()
    {
        if (llGateWaye == null)
        {
            return;
        }
        llGateWaye.setVisibility(View.GONE);
        llTickets.setVisibility(View.VISIBLE);
        ((BuyTicketsActivity) getActivity()).onBackPayment();
        if (slidingUpPanelLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED))
        {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            return;
        }
    }

    public LinearLayout getLlGateWaye()
    {
        return llGateWaye;

    }

    @Override
    public void onResume()
    {
        super.onResume();

    }

    @Override
    public void onFinishedPaymentWallet(ResponsePaymentWallet response)
    {
        ((BuyTicketsActivity) getActivity()).hideLoading();

        Intent intent = new Intent(getContext(), ShowTicketActivity.class);
        intent.putExtra("RefrenceNumber", response.getRefNumber().toString());
        intent.putExtra("isTransactionList", false);
        startActivity(intent);

     /*   Intent intent = new Intent(getActivity(), ShowTicketActivity.class);

        intent.putExtra("RefrenceNumber", response.getRefNumber());
        intent.putExtra("isTransactionList", false);
        startActivityForResult(intent,100);*/

    }

    @Override
    public void onErrorPaymentWallet(String error)
    {
        ((BuyTicketsActivity) getActivity()).hideLoading();
        showAlertFailure(context, error, "", false);

    }

    @Override
    public void OnItemSpectatorListClick(ArrayList<SpectatorInfoModel> selectedInfo,Integer position)
    {

        this.selectedInfo = selectedInfo;
        rvSpectatorList.post(() -> spectatorAdapter.notifyItemChanged(position));
    }
}

