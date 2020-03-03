package com.traap.traapapp.ui.fragments.moneyTransfer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.mainPage.MainPageResponse;
import com.traap.traapapp.ui.adapters.MoneyTransferBankLogoAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.TextViewEx;
import com.traap.traapapp.utilities.TraapMaskedEditText;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Created by Javad.Abadi on 7/10/2018.
 */
@SuppressLint("ValidFragment")
public class MoneyTransferFragment extends BaseFragment
{
    private FragmentManager fragmentManager;
    private RecyclerView rvBankLogo;
    private LinearLayout llCarDetail, llCard;
    private TextView tvOriginCard, tvAmount;
    private CircularProgressButton btnCardConfirm;
    private TraapMaskedEditText etExpireDate, etOrigin, etDestination, etAmount, etDescription;


    private MoneyTransferBankLogoAdapter bankLogoAdapter;
    private FragmentTransfer fragmentTransfer;
    private View v;

    private MainActionView mainView;
    private TextViewEx tvBankName;
    private ImageView ivOrigin;

    final int duration = 10;
    final int pixelsToMove = 30;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Runnable SCROLLING_RUNNABLE = new Runnable()
    {

        @Override
        public void run()
        {
            rvBankLogo.smoothScrollBy(pixelsToMove, 0);
            mHandler.postDelayed(this, duration);
        }
    };

    public MoneyTransferFragment()
    {

    }

    public static MoneyTransferFragment newInstance(MainActionView mainView, FragmentTransfer fragmentTransfer)
    {
        MoneyTransferFragment f = new MoneyTransferFragment();
        f.setMainView(mainView);
        f.setFragmentTransfer(fragmentTransfer);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    private void setFragmentTransfer(FragmentTransfer fragmentTransfer)
    {
        this.fragmentTransfer = fragmentTransfer;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {


        v = inflater.inflate(R.layout.fragment_transfer_money, container, false);
        initView();
        initRvBankLogo();
        getMainpage();

        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
                {

                    FragmentManager fm = getFragmentManager();
                    if (fm.getBackStackEntryCount() > 0)
                    {
                        mainView.backToMainFragment();
                    } else
                    {
                        if (llCarDetail.getVisibility() == View.VISIBLE)
                        {
                            llCard.setVisibility(View.VISIBLE);
                            llCarDetail.setVisibility(View.GONE);

                        }
                    }


                    return true;
                }
                return false;
            }
        });
    }

    private void initView()
    {

        rvBankLogo = v.findViewById(R.id.rvBankLogo);
        llCarDetail = v.findViewById(R.id.llCarDetail);
        llCard = v.findViewById(R.id.llCard);
        etOrigin = v.findViewById(R.id.etOrigin);
        etExpireDate = v.findViewById(R.id.etExpireDate);
        btnCardConfirm = v.findViewById(R.id.btnCardConfirm);
        etDestination = v.findViewById(R.id.etDestination);
        etAmount = v.findViewById(R.id.etAmount);
        tvBankName = v.findViewById(R.id.tvBankName);
        etDescription = v.findViewById(R.id.etDescription);
        ivOrigin = v.findViewById(R.id.ivOrigin);
/*
        tvBankName.setTypeFace(Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf"));

        tvBankName.setLineSpacing(5);

        tvBankName.setTextSize(getResources().getDimension(R.dimen.textSize_12dp));
        tvBankName.setTextColor(R.color.textHint);*/


        ivOrigin.setOnClickListener(v ->
        {
            fragmentTransfer.onOriginCard();


        });

        btnCardConfirm.setOnClickListener(v ->
        {
            boolean error = false;

            if (Objects.requireNonNull(etOrigin.getText()).toString().replaceAll("-", "").replaceAll("_", "").length() != 16
                    || !Utility.CheckCartDigit(etOrigin.getText().toString().replaceAll("-", "").replaceAll("_", "")))
            {
                etOrigin.setError("لطفا شماره کارت را صحیح وارد نمایید.");
                error = true;
            }

            if (Objects.requireNonNull(etDestination.getText()).toString().replaceAll("-", "").replaceAll("_", "").length() != 16
                    || !Utility.CheckCartDigit(etDestination.getText().toString().replaceAll("-", "").replaceAll("_", "")))
            {
                etDestination.setError("لطفا شماره کارت را صحیح وارد نمایید.");
                error = true;

            }
            if (TextUtils.isEmpty(Objects.requireNonNull(etAmount.getText()).toString()))
            {
                etAmount.setError("لطفا مبلغ را وارد نمایید.");
                error = true;

            }

            if (TextUtils.isEmpty(Objects.requireNonNull(etDescription.getText()).toString()))
            {
                etDescription.setError("لطفا شرح تراکنش را وارد نمایید.");
                error = true;

            }

            if (error)
                return;

            llCard.setVisibility(View.GONE);
            llCarDetail.setVisibility(View.VISIBLE);

        });
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void initRvBankLogo()
    {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        bankLogoAdapter = new MoneyTransferBankLogoAdapter();
        rvBankLogo.setAdapter(bankLogoAdapter);
        rvBankLogo.setLayoutManager(layoutManager);
        rvBankLogo.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                int lastItem = layoutManager.findLastCompletelyVisibleItemPosition();
                if (lastItem == layoutManager.getItemCount() - 1)
                {
                    mHandler.removeCallbacks(SCROLLING_RUNNABLE);
                    Handler postHandler = new Handler();
                    postHandler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            rvBankLogo.setAdapter(null);
                            rvBankLogo.setAdapter(bankLogoAdapter);
                            mHandler.postDelayed(SCROLLING_RUNNABLE, 2000);
                        }
                    }, 2000);
                }
            }
        });
        mHandler.postDelayed(SCROLLING_RUNNABLE, 2000);

    }


    public void getMainpage()
    {
        mainView.showLoading();
        SingletonService.getInstance().getBankListService().mainpage(new OnServiceStatus<WebServiceClass<MainPageResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<MainPageResponse> response)
            {
                mainView.hideLoading();
                try
                {
                    StringBuilder bankname = new StringBuilder();
                    bankname.append("شما میتوانید از مبدا بانکهای ");

                    for (int i = 0; i < response.data.getBank().size(); i++)
                    {
                        bankname.append(response.data.getBank().get(i).getName());
                        if (i != response.data.getBank().size() - 2)
                            bankname.append("، ");
                        else
                            bankname.append(" و ");

                    }

                    bankname.append(" انتقال وجه انجام دهید.");
                    tvBankName.setText(bankname.toString(), true);


                } catch (Exception e)
                {
                }


            }

            @Override
            public void onError(String message)
            {

            }
        });


    }
}