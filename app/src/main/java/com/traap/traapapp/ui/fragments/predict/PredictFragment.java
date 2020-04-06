package com.traap.traapapp.ui.fragments.predict;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.LegendPositionMode;
import com.anychart.enums.WordWrap;
import com.anychart.graphics.vector.text.Direction;
import com.anychart.graphics.vector.text.HAlign;
import com.anychart.graphics.vector.text.VAlign;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import io.reactivex.disposables.CompositeDisposable;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.apiServices.model.predict.getPredict.response.BarChart;
import com.traap.traapapp.apiServices.model.predict.getPredict.response.PieChart;
import com.traap.traapapp.apiServices.model.predict.getPredict.response.GetPredictResponse;
import com.traap.traapapp.apiServices.model.predict.sendPredict.request.SendPredictRequest;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.adapters.predict.PredictBarChartProgressAdapter;
import com.traap.traapapp.ui.adapters.predict.PredictMatchResultAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.dialogs.LotteryWinnerListDialog;
import com.traap.traapapp.ui.fragments.main.CountDownTimerView;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.utilities.CountDownTimerPredict;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Javad.Abadi on 7/14/2018.
 */
@SuppressLint("ValidFragment")
public class PredictFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<GetPredictResponse>>,
        OnAnimationEndListener, CountDownTimerView, PredictActionView
{
    private CompositeDisposable disposable = new CompositeDisposable();

    private Integer matchId;
    private AVLoadingIndicatorView progressPieChart;
    private TextView tvUserName, tvHeaderPopularNo;

    private NumberPicker numPickerAway, numPickerHome;

    private Integer teamHomeId, teamAwayId;

    private Context context;

    private LinearLayout llPredict, llTimer;
    private TextView tvPredictText, tvTimePredict, tvHomePieChartTitle, tvEqualPieChartTitle, tvAwayPieChartTitle;
    private CountDownTimer countDownTimer;

    private RecyclerView rcMatchResult, rcBarChart;

    private LinearLayout llAwayResultList, llHomeResultList, llChart;
    private TextView tvAwayHeader, tvHomeHeader, tvPredictEmpty, tvAwayPredict, tvHomePredict, tvMatchDate, tvCurrentMatchResult;
    private ImageView imgHomeHeader, imgAwayHeader, imgHomePredict, imgAwayPredict, imgCupLogo;

    private View vColorHomePieChart, vColorEqualPieChart, vColorAwayPieChart;

    private CircularProgressButton btnSendPredict;

    private Pie pieChart;

    private Boolean isPredictable;

    private MainActionView mainView;

    private AnyChartView chartViewPie;

    private Toolbar mToolbar;

    private View rootView, rlShirt;

    private LinearLayout llChartLinear;
    private LinearLayout llChart1, llChart2, llChart3;
    private Space spChartPredictOne, spChartPredictTwo, spChartPredictThree;
    private RelativeLayout rlChartPredictOne, rlChartPredictTwo, rlChartPredictThree;
    private View vColorHomeOne, vColorHomeTwo, vColorHomeThree, vColorAwayOne, vColorAwayTwo, vColorAwayThree, vColorHomeFour, vColorAwayFour;
    private TextView tvChartPredictOne, tvChartPredictTwo, tvChartPredictThree, tvHomeChartTitle, tvAwayChartTitle;
    private TextView tvChartTotalUserOne, tvChartTotalUserTwo, tvChartTotalUserThree;

    public PredictFragment()
    {
    }

    public static PredictFragment newInstance(MainActionView mainView, Integer matchId, Boolean isPredictable)
    {
        PredictFragment f = new PredictFragment();
        f.setMainView(mainView);

        Bundle arg = new Bundle();
        arg.putInt("matchId", matchId);
        arg.putBoolean("isPredictable", isPredictable);

        f.setArguments(arg);

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
            matchId = getArguments().getInt("matchId");
            isPredictable = getArguments().getBoolean("isPredictable");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;
        }

        rootView = inflater.inflate(R.layout.fragment_predict, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        ((TextView) mToolbar.findViewById(R.id.tvTitle)).setText("پیش بینی بازی");
        rootView.findViewById(R.id.imgBack).setOnClickListener(v ->
        {
//            mainView.backToMainFragment();
            getActivity().onBackPressed();
        });

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
        tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
        tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

        initView();

        EventBus.getDefault().register(this);

        return rootView;
    }

    private void initView()
    {
        progressPieChart = rootView.findViewById(R.id.progressPieChart);

        tvMatchDate = rootView.findViewById(R.id.tvMatchDate);

        llPredict = rootView.findViewById(R.id.llPredict);
        llTimer = rootView.findViewById(R.id.llTimer);
        numPickerAway = rootView.findViewById(R.id.numPickerAway);
        numPickerHome = rootView.findViewById(R.id.numPickerHome);
        tvCurrentMatchResult = rootView.findViewById(R.id.tvCurrentMatchResult);
        tvPredictText = rootView.findViewById(R.id.tvPredictText);
        tvAwayPieChartTitle = rootView.findViewById(R.id.tvAwayPieChartTitle);
        tvHomePieChartTitle = rootView.findViewById(R.id.tvHomePieChartTitle);
        tvEqualPieChartTitle = rootView.findViewById(R.id.tvEqualPieChartTitle);
        tvTimePredict = rootView.findViewById(R.id.tvTimePredict);
        rcMatchResult = rootView.findViewById(R.id.rcMatchResult);
        rcBarChart = rootView.findViewById(R.id.rcBarChart);

        chartViewPie = rootView.findViewById(R.id.chartViewPie);
        APIlib.getInstance().setActiveAnyChartView(chartViewPie);

//        chartViewColumn = rootView.findViewById(R.id.chartViewColumn);
//        APIlib.getInstance().setActiveAnyChartView(chartViewColumn);

        btnSendPredict = rootView.findViewById(R.id.btnSendPredict);

        llChartLinear = rootView.findViewById(R.id.llChartLinear);
        rlChartPredictOne = rootView.findViewById(R.id.rlChartPredictOne);
        rlChartPredictTwo = rootView.findViewById(R.id.rlChartPredictTwo);
        rlChartPredictThree = rootView.findViewById(R.id.rlChartPredictThree);
        spChartPredictOne = rootView.findViewById(R.id.spChartPredictOne);
        spChartPredictTwo = rootView.findViewById(R.id.spChartPredictTwo);
        spChartPredictThree = rootView.findViewById(R.id.spChartPredictThree);
        llChart1 = rootView.findViewById(R.id.rlChart1);
        llChart2 = rootView.findViewById(R.id.rlChart2);
        llChart3 = rootView.findViewById(R.id.rlChart3);
        vColorHomeOne = rootView.findViewById(R.id.vColorHomeOne);
        vColorHomeTwo = rootView.findViewById(R.id.vColorHomeTwo);
        vColorHomeThree = rootView.findViewById(R.id.vColorHomeThree);
        vColorHomeFour = rootView.findViewById(R.id.vColorHomeFour);
        vColorAwayOne = rootView.findViewById(R.id.vColorAwayOne);
        vColorAwayTwo = rootView.findViewById(R.id.vColorAwayTwo);
        vColorAwayThree = rootView.findViewById(R.id.vColorAwayThree);
        vColorAwayFour = rootView.findViewById(R.id.vColorAwayFour);
        tvChartPredictOne = rootView.findViewById(R.id.tvChartPredictOne);
        tvChartPredictTwo = rootView.findViewById(R.id.tvChartPredictTwo);
        tvChartPredictThree = rootView.findViewById(R.id.tvChartPredictThree);
        tvHomeChartTitle = rootView.findViewById(R.id.tvHomeChartTitle);
        tvAwayChartTitle = rootView.findViewById(R.id.tvAwayChartTitle);
        tvChartTotalUserOne = rootView.findViewById(R.id.tvChartTotalUserOne);
        tvChartTotalUserTwo = rootView.findViewById(R.id.tvChartTotalUserTwo);
        tvChartTotalUserThree = rootView.findViewById(R.id.tvChartTotalUserThree);

        vColorEqualPieChart = rootView.findViewById(R.id.vColorEqualPieChart);
        vColorAwayPieChart = rootView.findViewById(R.id.vColorAwayPieChart);
        vColorHomePieChart = rootView.findViewById(R.id.vColorHomePieChart);

        llAwayResultList = rootView.findViewById(R.id.llAwayResultList);
        llHomeResultList = rootView.findViewById(R.id.llHomeResultList);

        llChart = rootView.findViewById(R.id.llChart);

        tvAwayPredict = rootView.findViewById(R.id.tvAwayPredict);
        tvHomePredict = rootView.findViewById(R.id.tvHomePredict);

        tvAwayHeader = rootView.findViewById(R.id.tvAwayHeader);
        tvHomeHeader = rootView.findViewById(R.id.tvHomeHeader);

        tvPredictEmpty = rootView.findViewById(R.id.tvPredictEmpty);
        rlShirt = rootView.findViewById(R.id.rlShirt);
        rlShirt.setOnClickListener(v ->
                startActivityForResult(new Intent(SingletonContext.getInstance().getContext(),
                MyProfileActivity.class),100)
        );
        imgAwayHeader = rootView.findViewById(R.id.imgAwayHeader);
        imgAwayPredict = rootView.findViewById(R.id.imgAway3);
        imgHomeHeader = rootView.findViewById(R.id.imgHomeHeader);
        imgHomePredict = rootView.findViewById(R.id.imgHome3);

        imgCupLogo = rootView.findViewById(R.id.imgCupLogo);

        numPickerAway.setMinValue(0);
        numPickerAway.setMaxValue(20);
        numPickerAway.setWrapSelectorWheel(false);

        numPickerHome.setMinValue(0);
        numPickerHome.setMaxValue(20);
        numPickerHome.setWrapSelectorWheel(false);

        pieChart = AnyChart.pie();

        if (!isPredictable)
        {
            numPickerHome.setVisibility(View.GONE);
            numPickerAway.setVisibility(View.GONE);
            btnSendPredict.setVisibility(View.GONE);
        }
        rcMatchResult.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        rcBarChart.setLayoutManager(new GridLayoutManager(context, 1, RecyclerView.VERTICAL, false));
        getBaseData();

        btnSendPredict.setOnClickListener(v ->
        {
            sendPredict();
        });

        llAwayResultList.setOnClickListener(v ->
        {
            mainView.onShowLast5PastMatch(teamAwayId);
        });

        llHomeResultList.setOnClickListener(v ->
        {
            mainView.onShowLast5PastMatch(teamHomeId);
        });

        llPredict.setOnClickListener(v ->
        {
            LotteryWinnerListDialog dialog = new LotteryWinnerListDialog(matchId, this);
            dialog.show(getActivity().getFragmentManager(), "predictWinListDialog");
        });

        FrameLayout flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
        flLogoToolbar.setOnClickListener(v ->
        {
            mainView.backToMainFragment();
        });

        imgAwayHeader.setOnClickListener(v -> mainView.onPredictLeagueTable(teamAwayId, matchId, isPredictable));
        imgAwayPredict.setOnClickListener(v -> mainView.onPredictLeagueTable(teamAwayId, matchId, isPredictable));

        imgHomeHeader.setOnClickListener(v -> mainView.onPredictLeagueTable(teamHomeId, matchId, isPredictable));
        imgHomePredict.setOnClickListener(v -> mainView.onPredictLeagueTable(teamHomeId, matchId, isPredictable));
    }

    private void sendPredict()
    {

        btnSendPredict.startAnimation();
        btnSendPredict.setClickable(false);

        SendPredictRequest request = new SendPredictRequest();
        request.setMatchId(matchId);
        request.setAwayTeamScore(numPickerAway.getValue());
        request.setHomeTeamScore(numPickerHome.getValue());

        SingletonService.getInstance().sendPredictService().sendPredictService(request, new OnServiceStatus<WebServiceClass<Object>>()
        {
            @Override
            public void onReady(WebServiceClass<Object> response)
            {
                btnSendPredict.revertAnimation(PredictFragment.this);
                btnSendPredict.setClickable(true);

                try
                {
                    if (response.info.statusCode != 200)
                    {
                        showAlert(context, response.info.message, 0);
                    }
                    else
                    {
                        mainView.onSetPredictCompleted(matchId, true, response.info.message);
                    }
                }
                catch (NullPointerException e)
                {
                    showAlert(context, "خطای ارتباط با سرور!" + "\n" + "لطفا مجددا اقدام نمایید.", R.string.error);
                }
            }

            @Override
            public void onError(String message)
            {
                btnSendPredict.revertAnimation(PredictFragment.this);
                btnSendPredict.setClickable(true);
                if (Tools.isNetworkAvailable((Activity) context))
                {
                    showAlert(context, "خطای ارتباط با سرور!" + "\n" + "لطفا مجددا اقدام نمایید.", R.string.error);
                }
                else
                {
                    showError(context,  getString(R.string.networkErrorMessage));
                }
            }
        });
    }

    public void startTimer(long time)
    {
        countDownTimer = new CountDownTimerPredict(time, 1000, this);
        countDownTimer.start();
    }

    private void getBaseData()
    {
        mainView.showLoading();
        SingletonService.getInstance().getPredictService().getPredictService(matchId, this);
    }

    @Override
    public void onReady(WebServiceClass<GetPredictResponse> response)
    {
        mainView.hideLoading();
        if (response == null || response.data == null)
        {
            showErrorAndBackToMain("خطا در دریافت اطلاعات از سرور!");
            Logger.e("-GetPredictResponse-", "null");
            return;
        }
        if (response.info.statusCode != 200)
        {
            showErrorAndBackToMain(response.info.message);
        }
        else
        {
            teamHomeId = response.data.getMatchPredict().getHomeTeam().getLiveScoreId();
            teamAwayId = response.data.getMatchPredict().getAwayTeam().getLiveScoreId();

            tvMatchDate.setText(response.data.getMatchPredict().getMatchDatetimeStr());

            rcMatchResult.setAdapter(new PredictMatchResultAdapter(
                    response.data.getMatchTeamResults(),
                    response.data.getMatchPredict().getHomeTeam(),
                    response.data.getMatchPredict().getAwayTeam(),
                    new PredictMatchResultAdapter.OnLogoClickListener()
                    {
                        @Override
                        public void onHomeLogoClick()
                        {
                            mainView.onPredictLeagueTable(teamHomeId, matchId, isPredictable);
                        }

                        @Override
                        public void onAwayLogoClick()
                        {
                            mainView.onPredictLeagueTable(teamAwayId, matchId, isPredictable);
                        }
                    })
            );

            llAwayResultList.removeAllViews();
            llHomeResultList.removeAllViews();

            for (String itemStr : response.data.getAwayLastPlays())
            {
                llAwayResultList.addView(getWinLoseListView(itemStr));
            }
            for (String itemStr : response.data.getHomeLastPlays())
            {
                llHomeResultList.addView(getWinLoseListView(itemStr));
            }

            setImageIntoIV(imgAwayHeader, response.data.getMatchPredict().getAwayTeam().getTeamLogo());
            setImageIntoIV(imgHomeHeader, response.data.getMatchPredict().getHomeTeam().getTeamLogo());

            try
            {
                if (response.data.getMatchPredict().getLastMatchResult() != null)
                {
                    if (response.data.getMatchPredict().getLastMatchResult().getHomeScore() != null || response.data.getMatchPredict().getLastMatchResult().getAwayScore() != null)
                    {
                        tvCurrentMatchResult.setText(
                                response.data.getMatchPredict().getLastMatchResult().getHomeScore() +
                                        " - " +
                                        response.data.getMatchPredict().getLastMatchResult().getAwayScore());
                    }
                    else
                    {
                        tvCurrentMatchResult.setVisibility(View.INVISIBLE);
                    }
                }
                else
                {
                    tvCurrentMatchResult.setVisibility(View.INVISIBLE);
                }
            }
            catch (Exception e)
            {
                tvCurrentMatchResult.setVisibility(View.INVISIBLE);
            }

            tvAwayHeader.setText(response.data.getMatchPredict().getAwayTeam().getTeamName());
            tvHomeHeader.setText(response.data.getMatchPredict().getHomeTeam().getTeamName());

            setImageIntoIV(imgAwayPredict, response.data.getMatchPredict().getAwayTeam().getTeamLogo());
            setImageIntoIV(imgHomePredict, response.data.getMatchPredict().getHomeTeam().getTeamLogo());
            setImageIntoIV(imgCupLogo, response.data.getMatchPredict().getCup().getCupLogo());

            //-------------------timer----------------------------
            try
            {
                if (response.data.getMatchPredict().isPredict())
                {
                    isPredictable = true;
                    long predictTime = response.data.getMatchPredict().getPredictTime().longValue() * 1000;
                    long dateTimeNow = response.data.getMatchPredict().getServerTime().longValue() * 1000;
                    long remainPredictTime = predictTime - dateTimeNow;
                    Logger.e("--PredictTime--", "predictTime: " + predictTime + ", dateTimeNow: " + dateTimeNow);
                    Logger.e("--diff PredictTime--", "remainPredictTime: " + remainPredictTime);

                    if (remainPredictTime > 0)
                    {
//                        isPredictable = true;
                        llTimer.setVisibility(View.VISIBLE);
                        tvPredictText.setText(" پیش بینی کن برنده شو!");

                        startTimer(remainPredictTime);
                    }
                    else
                    {
//                        isPredictable = false;
                        llTimer.setVisibility(View.GONE);
                        tvPredictText.setTextSize(14f);
                        tvPredictText.setText("مشاهده برندگان پیش بینی");
                    }
                }
                else
                {
                    isPredictable = false;
                    llTimer.setVisibility(View.GONE);
                    tvPredictText.setText("مشاهده برندگان پیش بینی");
                    tvPredictText.setTextSize(14f);
                    btnSendPredict.setVisibility(View.GONE);
                    numPickerHome.setVisibility(View.GONE);
                    numPickerAway.setVisibility(View.GONE);
                    tvAwayPredict.setVisibility(View.VISIBLE);
                    tvHomePredict.setVisibility(View.VISIBLE);
                }
            }
            catch (Exception e)
            {
                Logger.e("-predict Timer Exception", "Exception: " + e.getMessage());
                e.printStackTrace();
            }
            //-------------------timer----------------------------

            if (response.data.getYouPredict())
            {
                try
                {
                    if (isPredictable)
                    {
                        numPickerHome.setValue(response.data.getYouPredictResult().getHomeScore());
                        numPickerAway.setValue(response.data.getYouPredictResult().getAwayScore());
                    }
                    else
                    {
                        tvHomePredict.setText(String.valueOf(response.data.getYouPredictResult().getHomeScore()));
                        tvAwayPredict.setText(String.valueOf(response.data.getYouPredictResult().getAwayScore()));

                        numPickerHome.setVisibility(View.GONE);
                        numPickerAway.setVisibility(View.GONE);
                        btnSendPredict.setVisibility(View.GONE);
                        tvHomePredict.setVisibility(View.VISIBLE);
                        tvAwayPredict.setVisibility(View.VISIBLE);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            if (response.data.getChart().getBarChart() == null || response.data.getChart().getBarChart().isEmpty() ||
                    response.data.getChart().getPieChart() == null || response.data.getChart().getPieChart().isEmpty())
            {
                llChart.setVisibility(View.GONE);
                tvPredictEmpty.setVisibility(View.VISIBLE);
            }
            else
            {
                llChart.setVisibility(View.VISIBLE);
                tvPredictEmpty.setVisibility(View.GONE);

                //-----------------------------------PieChart start-----------------------------------
                List<DataEntry> data = new ArrayList<>();
                ArrayList<String> colorList = new ArrayList<>();

                for (PieChart pieChart : response.data.getChart().getPieChart())
                {
                    try
                    {
                        if (pieChart.getChartPrediction() == 0) //0 = مساوی
                        {
                            tvEqualPieChartTitle.setText("مساوی (%" + pieChart.getTotalUser() + ")");

                            colorList.add("#de9b89");
//                            PredictDataEntry item = new PredictDataEntry("مساوی", pieChart.getTotalUser(), getPersianChar(String.valueOf(pieChart.getTotalUser())));
////                            item.setValue("مساوی", getPersianChar(String.valueOf(pieChart.getTotalUser())));
//                            data.add(item);

                            data.add(new ValueDataEntry("مساوی (%" + pieChart.getTotalUser() + ")", pieChart.getTotalUser()));
                        }
                        else if (pieChart.getChartPrediction() == 1) //1 = میزبان برنده
                        {
                            String teamName = response.data.getMatchPredict().getHomeTeam().getTeamName();
                            tvHomePieChartTitle.setText("برد " + teamName + " (%" + pieChart.getTotalUser() + ")");

                            colorList.add(response.data.getMatchPredict().getHomeTeam().getTeamColorCode());
//                            PredictDataEntry item = new PredictDataEntry("برد " + response.data.getHomeTeamName(),
//                                    pieChart.getTotalUser(),
//                                    getPersianChar(String.valueOf(pieChart.getTotalUser())));
////                            item.setValue("برد " + response.data.getHomeTeamName(),
////                                    getPersianChar(String.valueOf(pieChart.getTotalUser())));
//                            data.add(item);

                            data.add(new ValueDataEntry("برد " + response.data.getMatchPredict().getHomeTeam().getTeamName()
                                    + "(%" + pieChart.getTotalUser() + ")",
                                    pieChart.getTotalUser()));
                        }
                        else if (pieChart.getChartPrediction() == 2) //2 = مهمان برنده
                        {
                            String teamName = response.data.getMatchPredict().getAwayTeam().getTeamName();
                            tvAwayPieChartTitle.setText("برد " + teamName + " (%" + pieChart.getTotalUser() + ")");
                            colorList.add(response.data.getMatchPredict().getAwayTeam().getTeamColorCode());
//                            PredictDataEntry item = new PredictDataEntry("برد " + response.data.getAwayTeamName(),
//                                    pieChart.getTotalUser(),
//                                    getPersianChar(String.valueOf(pieChart.getTotalUser())));
////                            item.setValue("برد " + response.data.getAwayTeamName(),
////                                    getPersianChar(String.valueOf(pieChart.getTotalUser())));
//                            data.add(item);

                            data.add(new ValueDataEntry("برد " + response.data.getMatchPredict().getAwayTeam().getTeamName()
                                    + "(%" + pieChart.getTotalUser() + ")",
                                    pieChart.getTotalUser()));
                        }
                    }
                    catch (Exception e)
                    {

                    }
                }
                pieChart.data(data);
                pieChart.innerRadius(30);

                String color[] = new String[colorList.size()];
//                String color[] = {"#de9b89", response.data.getHomeTeamColorCode(), response.data.getAwayTeamColorCode()};
                for (int i = 0; i < colorList.size(); i++)
                {
                    color[i] = colorList.get(i);
                }
                pieChart.palette(color);

                pieChart.labels().position("outside");
                pieChart.labels().fontColor("#000");
                pieChart.labels().enabled(false);
//                Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_normal.ttf");
                Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/IRANSansMobile_Bold.ttf");
                pieChart.labels().fontFamily(face.toString());
//                File font = new File("file:///android_asset/fonts/iran_sans_normal.ttf");

                pieChart.legend()
//                        .position("center-bottom")
                        .position("left")
                        .itemsLayout(LegendLayout.VERTICAL_EXPANDABLE)
                        .textDirection(Direction.RTL)
                        .fontSize(14)
                        .inverted(false)
                        .positionMode(LegendPositionMode.OUTSIDE)
                        .wordWrap(WordWrap.NORMAL)
                        .vAlign(VAlign.BOTTOM)
                        .hAlign(HAlign.RIGHT)
                        .align(Align.CENTER)
                        .enabled(false);


                pieChart.tooltip()
//                        .title().align("right")
                        .enabled(false);
//                pieChart.tooltip()
//                        .format("درصد پیش بینی: " + )
//                        .enabled(true);
//                showError(context, pieChart.tooltip().getJsBase());
//                pieChart.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"})
//                {
//                    @Override
//                    public void onClick(Event event)
//                    {
//                        pieChart.tooltip().format(event.getData().put("ارزش: ", event.getData().get("value")));
//                    }
//                });
                chartViewPie.setProgressBar(progressPieChart);
                chartViewPie.setChart(pieChart);

                vColorHomePieChart.setBackgroundColor(Color.parseColor(response.data.getMatchPredict().getHomeTeam().getTeamColorCode()));
                vColorAwayPieChart.setBackgroundColor(Color.parseColor(response.data.getMatchPredict().getAwayTeam().getTeamColorCode()));
                vColorEqualPieChart.setBackgroundColor(Color.parseColor("#de9b89"));

                //-----------------------------------PieChart end-------------------------------------

                //-----------------------------------BarChart start-----------------------------------b v
                if (response.data.getChart().getBarChart().isEmpty())
                {
                    llChartLinear.setVisibility(View.GONE);
                }

                List<BarChart> barChartList = response.data.getChart().getBarChart();

                rcBarChart.setAdapter(new PredictBarChartProgressAdapter(context,
                        barChartList,
                        response.data.getMatchPredict().getHomeTeam(),
                        response.data.getMatchPredict().getAwayTeam() )
                );

                //--------------BarChart OLD-------------
                if (barChartList.size() >= 1)
                {
                    LinearLayout.LayoutParams paramsSpace = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    params.weight = Float.valueOf(barChartList.get(0).getTotalUser().toString());
                    paramsSpace.weight = Float.valueOf(String.valueOf(100 - barChartList.get(0).getTotalUser()));
                    rlChartPredictOne.setLayoutParams(params);
                    spChartPredictOne.setLayoutParams(paramsSpace);

                    llChart1.setVisibility(View.VISIBLE);
                    tvChartTotalUserOne.setText(barChartList.get(0).getTotalUser().toString() + " %");
                    vColorHomeOne.setBackgroundColor(Color.parseColor(response.data.getMatchPredict().getHomeTeam().getTeamColorCode()));
                    vColorAwayOne.setBackgroundColor(Color.parseColor(response.data.getMatchPredict().getAwayTeam().getTeamColorCode()));

                    tvChartPredictOne.setText(barChartList.get(0).getAwayScore() + "-" + barChartList.get(0).getHomeScore());
                }
//                else
//                {
//                    llChart2.setVisibility(View.GONE);
//                    llChart3.setVisibility(View.GONE);
//                }

                if (barChartList.size() >= 2)
                {
                    LinearLayout.LayoutParams paramsSpace = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    params.weight = Float.valueOf(barChartList.get(1).getTotalUser().toString());
                    paramsSpace.weight = Float.valueOf(String.valueOf(100 - barChartList.get(1).getTotalUser()));
                    rlChartPredictTwo.setLayoutParams(params);
                    spChartPredictTwo.setLayoutParams(paramsSpace);

                    llChart2.setVisibility(View.VISIBLE);
                    tvChartTotalUserTwo.setText(barChartList.get(1).getTotalUser().toString() + " %");
                    vColorHomeTwo.setBackgroundColor(Color.parseColor(response.data.getMatchPredict().getHomeTeam().getTeamColorCode()));
                    vColorAwayTwo.setBackgroundColor(Color.parseColor(response.data.getMatchPredict().getAwayTeam().getTeamColorCode()));

                    tvChartPredictTwo.setText(barChartList.get(1).getAwayScore() + "-" + barChartList.get(1).getHomeScore());
                }
                else
                {
                    llChart2.setVisibility(View.GONE);
                    llChart3.setVisibility(View.GONE);
                }

                if (barChartList.size() == 3)
                {
                    LinearLayout.LayoutParams paramsSpace = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    params.weight = Float.valueOf(barChartList.get(2).getTotalUser().toString());
                    paramsSpace.weight = Float.valueOf(String.valueOf(100 - barChartList.get(2).getTotalUser()));
                    rlChartPredictThree.setLayoutParams(params);
                    spChartPredictThree.setLayoutParams(paramsSpace);

                    llChart3.setVisibility(View.VISIBLE);
                    tvChartTotalUserThree.setText(barChartList.get(2).getTotalUser().toString() + " %");
                    vColorHomeThree.setBackgroundColor(Color.parseColor(response.data.getMatchPredict().getHomeTeam().getTeamColorCode()));
                    vColorAwayThree.setBackgroundColor(Color.parseColor(response.data.getMatchPredict().getAwayTeam().getTeamColorCode()));

                    tvChartPredictThree.setText(barChartList.get(2).getAwayScore() + "-" + barChartList.get(2).getHomeScore());
                }
                else if (barChartList.size() < 3)
                {
                    llChart3.setVisibility(View.GONE);
                }

                vColorHomeFour.setBackgroundColor(Color.parseColor(response.data.getMatchPredict().getHomeTeam().getTeamColorCode()));
                vColorAwayFour.setBackgroundColor(Color.parseColor(response.data.getMatchPredict().getAwayTeam().getTeamColorCode()));

                tvHomeChartTitle.setText(response.data.getMatchPredict().getHomeTeam().getTeamName());
                tvAwayChartTitle.setText(response.data.getMatchPredict().getAwayTeam().getTeamName());
                //--------------BarChart OLD-------------

                //-----------------------------------BarChart end-------------------------------------

            }

        }
    }

    @Override
    public void onError(String message)
    {
        mainView.hideLoading();
        Logger.e("-showErrorMessage-", "Error: " + message);

        if (Tools.isNetworkAvailable((Activity) context))
        {
            showErrorAndBackToMain("خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            showErrorAndBackToMain( getString(R.string.networkErrorMessage));
        }
    }

    private void setImageIntoIV(ImageView imageView, String link)
    {
        Picasso.with(context).load(link).into(imageView);
    }

    private RelativeLayout getWinLoseListView(String itemStr)
    {
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.item_win_lose, null, false);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ((int) getResources().getDimension(R.dimen.win_lose_view_height)),
                ((int) getResources().getDimension(R.dimen.win_lose_view_height))
        );
        int magSize = (int) getResources().getDimension(R.dimen.margin_static);
        params.setMargins(magSize, magSize, magSize, magSize);
        rl.setLayoutParams(params);
        TextView tv = rl.findViewById(R.id.tvResult);

        switch (itemStr)
        {
            case "-1":
                rl.setBackground(getResources().getDrawable(R.drawable.background_rectangle_red));
                tv.setText("باخت");
                break;
            case "1":
                rl.setBackground(getResources().getDrawable(R.drawable.background_rectangle_green));
                tv.setText("برد");
                break;
            case "0":
                rl.setBackground(getResources().getDrawable(R.drawable.background_rectangle_red_a));
                tv.setText("مساوی");
                break;
        }
        return rl;
    }

    @Override
    public void onFinishTimer()
    {
        isPredictable = false;
        llTimer.setVisibility(View.GONE);
        tvPredictText.setText("مشاهده برندگان پیش بینی");
    }

    @Override
    public void onTickTimer(String time)
    {
        tvTimePredict.setText(time);
    }

    @Override
    public void onErrorTimer(String message)
    {

    }

    private void showErrorAndBackToMain(String message)
    {
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, getResources().getString(R.string.error),
                message, false, MessageAlertDialog.TYPE_ERROR, new MessageAlertDialog.OnConfirmListener()
        {
            @Override
            public void onConfirmClick()
            {
                mainView.backToMainFragment();
            }

            @Override
            public void onCancelClick()
            {

            }
        });
        dialog.show(((Activity)context).getFragmentManager(), "dialog");
    }

    @Override
    public void onAnimationEnd()
    {
        btnSendPredict.setBackground(ContextCompat.getDrawable(context, R.drawable.background_button_login));
    }

    @Override
    public void showAlertFailure(String message)
    {
        showAlertFailure(context, message, "خطا!", false);
    }

    @Override
    public void onShowDetailWinnerList(List<Winner> winnerList)
    {
        mainView.onShowDetailWinnerList(winnerList);
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
        disposable.clear();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
