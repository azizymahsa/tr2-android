package com.traap.traapapp.ui.fragments.transaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
//import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getTransaction.ResponseTransaction;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.ui.adapters.Leaguse.DataBean;
import com.traap.traapapp.ui.adapters.transaction.TransactionListAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.calendar.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.traap.traapapp.utilities.calendar.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class TransactionsListFragment
        extends BaseFragment implements OnAnimationEndListener, View.OnClickListener, DatePickerDialog.OnDateSetListener//, OnBackPressed
{
    private String teamId = "";
    private View rootView;
    private SlidingUpPanelLayout upPanelLayout;
    private MainActionView mainView;

    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName,tvPopularPlayer,tvCount, tvHeaderPopularNo;
    private View imgBack, imgMenu;

    /*scroll view*/
    public List<DataBean> data = new ArrayList<>();
    private RecyclerView transactionRecycler;
    private TransactionListAdapter fixTableAdapter;
    private RecyclerView rvCategories;
    private View llFilter,btnConfirm;
    private CheckBox cbSuccessPayment,cbFailedPayment;
    private TextView etTimeUntil,etTimeFrom;
    private ImageView imgTimeFromReset,imgTimeUntilReset,ivSearch;

    private PersianCalendar currentDate;

    private DatePickerDialog pickerDialogDate;
    private View rlTimeUntil,rlTimeFrom;
    Integer amountRange=null;
    Boolean status=null;
    Integer typeTransactionId=null;
    String createDateRange=null;


    public TransactionsListFragment()
    {
    }

    public static TransactionsListFragment newInstance(MainActionView mainView)
    {
        TransactionsListFragment f = new TransactionsListFragment();

        Bundle args = new Bundle();

        f.setArguments(args);
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            teamId = getArguments().getString("teamId");
        }

    }

    public void initView()
    {
        try
        {
            upPanelLayout = rootView.findViewById(R.id.sliding_layout);

            cbSuccessPayment=rootView.findViewById(R.id.cbSuccessPayment);
            cbFailedPayment=rootView.findViewById(R.id.cbFailedPayment);

            ivSearch=rootView.findViewById(R.id.ivSearch);
            ivSearch.setOnClickListener(this);

            rlTimeUntil=rootView.findViewById(R.id.rlTimeUntil);
            rlTimeFrom=rootView.findViewById(R.id.rlTimeFrom);
            rlTimeFrom.setOnClickListener(this);
            rlTimeUntil.setOnClickListener(this);

            imgTimeFromReset=rootView.findViewById(R.id.imgTimeFromReset);
            imgTimeUntilReset=rootView.findViewById(R.id.imgTimeUntilReset);

            etTimeUntil=rootView.findViewById(R.id.etTimeUntil);
            etTimeFrom=rootView.findViewById(R.id.etTimeFrom);

            rvCategories=rootView.findViewById(R.id.rvCategories);
            rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));

            btnConfirm=rootView.findViewById(R.id.btnConfirm);
            btnConfirm.setOnClickListener(this);
            llFilter=rootView.findViewById(R.id.llFilter);
            llFilter.setOnClickListener(this);

            transactionRecycler = rootView.findViewById(R.id.transactionRecycler);
            tvCount = rootView.findViewById(R.id.tvCount);
            //Toolbar Create
            mToolbar = rootView.findViewById(R.id.toolbar);
            tvUserName = mToolbar.findViewById(R.id.tvUserName);
            tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mainView.openDrawer();
                }
            });
            tvTitle = rootView.findViewById(R.id.tvTitle);
            imgMenu = rootView.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
            imgBack = rootView.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvTitle.setText("سوابق خرید");
            initDate();

        } catch (Exception e)
        {

        }
    }
    private void initDate()
    {
        currentDate = new PersianCalendar();

        pickerDialogDate = DatePickerDialog.newInstance(this,
                currentDate.getPersianYear(),
                currentDate.getPersianMonth(),
                currentDate.getPersianDay()
        );
        pickerDialogDate.setMaxDate(currentDate);
    }
    private void openFilterLayout()
    {
        upPanelLayout.setScrollableView(rvCategories);

        final Handler handler = new Handler();
        handler.postDelayed(() -> upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED), 100);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            rootView = null;
        }

        rootView = inflater.inflate(R.layout.transaction_list_fragment, container, false);
        initView();

        sendRequest(amountRange,status,typeTransactionId,createDateRange);


        EventBus.getDefault().register(this);
        return rootView;
    }



    private void sendRequest(Integer amountRange, Boolean status, Integer typeTransactionId, String createDateRange)
    {
        mainView.showLoading();

        SingletonService.getInstance().getTransactionService().getTransactionList(new OnServiceStatus<WebServiceClass<ResponseTransaction>>()
        {
            @Override
            public void onReady(WebServiceClass<ResponseTransaction> response)
            {
                try
                {
                    mainView.hideLoading();
                    if (response == null || response.info == null)
                    {
                        return;
                    }
                    if (response.info.statusCode != 200)
                    {


                        return;
                    }
                    if (response.info.statusCode == 200)
                    {

                        tvCount.setText(response.data.getResults().size()+ "مورد خرید بلیت یافت شد.");
                        transactionRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                        fixTableAdapter = new TransactionListAdapter(response.data.getResults(), getActivity());
                        //fixTableAdapter.setClickListener(this);
                        transactionRecycler.setAdapter(fixTableAdapter);

                    }
                } catch (Exception e)
                {
                    mainView.showError(e.getMessage());
                    mainView.hideLoading();
                }
            }

            @Override
            public void onError(String message)
            {
              //  mainView.showError(message);
                mainView.hideLoading();
                if (Tools.isNetworkAvailable(getActivity()))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                } else
                {
                    showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                }
            }
        },null,null,null,null);
    }




    @Override
    public void onStop()
    {
        super.onStop();


    }

    @Override
    public void onResume()
    {
        super.onResume();

    }

    @Override
    public void onPause()
    {
        super.onPause();
    }


    @Override
    public void onAnimationEnd()
    {


    }

    @Subscribe
    public void getHeaderContent(HeaderModel headerModel)
    {
        if (headerModel.getPopularNo() != 0)
        {
            tvHeaderPopularNo.setText(String.valueOf(headerModel.getPopularNo()));
        }
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.llFilter:

               // openFilterLayout();

                break;

            case R.id.btnConfirm:

                hideFilterSlide();
                break;

            case R.id.imgTimeUntilReset:

                etTimeUntil.setText("");
                imgTimeUntilReset.setVisibility(View.GONE);

                break;

            case R.id.imgTimeFromReset:

                etTimeFrom.setText("");
                imgTimeFromReset.setVisibility(View.GONE);

                break;

            case R.id.ivSearch:

                break;
            case R.id.rlTimeFrom:
                pickerDialogDate.show(getActivity().getSupportFragmentManager(),"TimeFrom");

                break;

            case R.id.rlTimeUntil:
                pickerDialogDate.show(getActivity().getSupportFragmentManager(),"TimeUntil");

                break;

        }

    }

    private void hideFilterSlide()
    {
        upPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int endYear, int endMonth, int endDay)
    {
        if (view.getTag().equals("TimeFrom"))
        {
            PersianCalendar calendar = new PersianCalendar();
            calendar.set(year, monthOfYear, dayOfMonth);

            String createDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;

            etTimeFrom.setText(createDate);
            imgTimeFromReset.setVisibility(View.VISIBLE);

        }
        else if (view.getTag().equals("TimeUntil")){
            PersianCalendar calendar = new PersianCalendar();
            calendar.set(year, monthOfYear, dayOfMonth);

            String createDate = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;

            etTimeUntil.setText(createDate);
            imgTimeUntilReset.setVisibility(View.VISIBLE);

        }

    }



 /*   @Override
    public void onBackPressed()
    {
        getActivity().getSupportFragmentManager().popBackStack();
    }*/
}
