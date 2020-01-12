package com.traap.traapapp.ui.fragments.transaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
//import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.jakewharton.rxbinding2.view.RxView;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import io.reactivex.disposables.CompositeDisposable;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getTransaction.ResponseTransaction;
import com.traap.traapapp.apiServices.model.media.category.TypeCategory;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.models.otherModels.newsFilterItem.FilterItem;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.adapters.Leaguse.DataBean;
import com.traap.traapapp.ui.adapters.filterArchive.FilterArchiveAdapter;
import com.traap.traapapp.ui.adapters.media.HashTagMediaAdapter;
import com.traap.traapapp.ui.adapters.transaction.TransactionListAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.calendar.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.traap.traapapp.utilities.calendar.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class TransactionsListFragment2 extends BaseFragment implements OnAnimationEndListener, View.OnClickListener,
        DatePickerDialog.OnDateSetListener//, OnBackPressed
{

    private CompositeDisposable disposable = new CompositeDisposable();
    private final int DELAY_TIME_TEXT_CHANGE = 200;

    private RecyclerView rcHashTag;
    private HashTagMediaAdapter adapterHashTag;

    private String filterStartDate = "", filterEndDate = "";

    private String idFilteredList = "", titleFilteredList = "";

    private ImageView imgStartDateReset, imgEndDateReset, imgFilterClose, imgSearch;
    private TextView tvStartDate, tvEndDate;
    private EditText edtSearchFilter, edtSearchText;
    private CircularProgressButton btnConfirmFilter, btnDeleteFilter;

    private RecyclerView rcFilterCategory;

    private int endDay = 0, endMonth = 0, endYear = 0;
    private int startDay = 0, startMonth = 0, startYear = 0;

    private LinearLayout btnFilter, llDeleteFilter, llFilterHashTag;

    private DatePickerDialog pickerDialogStartDate, pickerDialogEndDate;

    private PersianCalendar currentDate, startPersianDate, endPersianDate;
    private Integer startDateInt = 0, endDateInt = 0;

    private ArrayList<TypeCategory> typeCategoryList = new ArrayList<>();
    private List<FilterItem> filteredCategoryList = new ArrayList<>();
    private ArrayList<FilterItem> filteredShowList = new ArrayList<>();
    private ArrayList<FilterItem> tempFilteredCategoryList = new ArrayList<>();
    private FilterArchiveAdapter adapter;
    private boolean pagerWithFilter = false;




    private String teamId = "";
    private View rootView;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private MainActionView mainView;

    private Context context;

    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvCount, tvHeaderPopularNo;
    private View imgBack, imgMenu, rlShirt;

    /*scroll view*/
    public List<DataBean> data = new ArrayList<>();
    private RecyclerView rcTransactionList;
    private TransactionListAdapter fixTableAdapter;
    private RecyclerView rvCategories;
    private View btnConfirm;
    private TextView etTimeUntil, etTimeFrom;
    private ImageView imgTimeFromReset, imgTimeUntilReset;

    private DatePickerDialog pickerDialogDate;
    private View rlTimeUntil, rlTimeFrom;
    Integer amountRange = null;
    Boolean status = null;
    Integer typeTransactionId = null;
    String createDateRange = null;


    public TransactionsListFragment2()
    {
    }

    public static TransactionsListFragment2 newInstance(MainActionView mainView)
    {
        TransactionsListFragment2 f = new TransactionsListFragment2();

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
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
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
            slidingUpPanelLayout = rootView.findViewById(R.id.slidingLayout);

            btnFilter = rootView.findViewById(R.id.btnFilter);

            rlTimeUntil = rootView.findViewById(R.id.rlTimeUntil);
            rlTimeFrom = rootView.findViewById(R.id.rlTimeFrom);
            rlTimeFrom.setOnClickListener(this);
            rlTimeUntil.setOnClickListener(this);

            imgTimeFromReset = rootView.findViewById(R.id.imgTimeFromReset);
            imgTimeUntilReset = rootView.findViewById(R.id.imgTimeUntilReset);

            etTimeUntil = rootView.findViewById(R.id.etTimeUntil);
            etTimeFrom = rootView.findViewById(R.id.etTimeFrom);

            rvCategories = rootView.findViewById(R.id.rvCategories);
            rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));

            btnConfirm = rootView.findViewById(R.id.btnConfirm);
            btnConfirm.setOnClickListener(this);

            rcTransactionList = rootView.findViewById(R.id.rcTransactionList);
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
                ((Activity)context).onBackPressed();
            });

            tvTitle.setText("سوابق خرید");
            rlShirt = rootView.findViewById(R.id.rlShirt);
            rlShirt.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startActivity(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class));
                }
            });
            initDate();

            disposable.add(RxView.clicks(btnFilter)
                    .throttleFirst(200, TimeUnit.MILLISECONDS)
                    .subscribe(v->
                    {
                        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    })
            );

            slidingUpPanelLayout.setFadeOnClickListener(v ->
            {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            });

        }
        catch (Exception e)
        {
            e.printStackTrace();
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

        sendRequest(amountRange, status, typeTransactionId, createDateRange);


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

                        tvCount.setText(response.data.getTransactionLists().size() + " مورد تراکنش یافت شد.");
                        rcTransactionList.setLayoutManager(new LinearLayoutManager(getContext()));
                        fixTableAdapter = new TransactionListAdapter(response.data.getTransactionLists(), context);
                        //fixTableAdapter.setClickListener(this);
                        rcTransactionList.setAdapter(fixTableAdapter);

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

            case R.id.rlTimeFrom:
                pickerDialogDate.show(getActivity().getSupportFragmentManager(), "TimeFrom");

                break;

            case R.id.rlTimeUntil:
                pickerDialogDate.show(getActivity().getSupportFragmentManager(), "TimeUntil");

                break;

        }

    }

    private void hideFilterSlide()
    {
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
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

        } else if (view.getTag().equals("TimeUntil"))
        {
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
