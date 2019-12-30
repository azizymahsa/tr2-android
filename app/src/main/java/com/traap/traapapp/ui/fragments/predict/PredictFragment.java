package com.traap.traapapp.ui.fragments.predict;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.anychart.graphics.vector.text.Direction;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.apiServices.model.predict.getPredict.response.Chart;
import com.traap.traapapp.apiServices.model.predict.getPredict.response.GetPredictResponse;
import com.traap.traapapp.apiServices.model.predict.getPredict.response.Predict;
import com.traap.traapapp.apiServices.model.predict.sendPredict.request.SendPredictRequest;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.utilities.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Javad.Abadi on 7/14/2018.
 */
@SuppressLint("ValidFragment")
public class PredictFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<GetPredictResponse>>,
        OnAnimationEndListener
{
    private MatchItem matchPredict;
    private TextView tvUserName, tvHeaderPopularNo;

    private LinearLayout llAwayResultList, llHomeResultList, llChart;
    private TextView tvAwayHeader, tvHomeHeader, tvAway, tvHome, tvCupTitle, tvDate, tvMatchResult, tvPredictEmpty, tvAwayPredict, tvHomePredict;
    private ImageView imgHomeHeader, imgAwayHeader, imgHome, imgAway, imgHomePredict, imgAwayPredict;

    private EditText edtAwayPredict, edtHomePredict;
    private View sepHome, sepAway;

    private View vHome, vHome2, vAway, vAway2;

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

    public static PredictFragment newInstance(MainActionView mainView, MatchItem matchPredict, Boolean isPredictable)
    {
        PredictFragment f = new PredictFragment();
        f.setMainView(mainView);
        Bundle arg = new Bundle();
        arg.putParcelable("matchPredict", matchPredict);
        arg.putBoolean("isPredictable", isPredictable);

        f.setArguments(arg);

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
            matchPredict = getArguments().getParcelable("matchPredict");
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
            mainView.backToMainFragment();
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
        chartViewPie = rootView.findViewById(R.id.chartViewPie);
//        APIlib.getInstance().setActiveAnyChartView(chartViewPie);

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

        llAwayResultList = rootView.findViewById(R.id.llAwayResultList);
        llHomeResultList = rootView.findViewById(R.id.llHomeResultList);

        llChart = rootView.findViewById(R.id.llChart);

        sepAway = rootView.findViewById(R.id.sepAway);
        sepHome = rootView.findViewById(R.id.sepHome);

        edtAwayPredict = rootView.findViewById(R.id.edtAwayPredict);
        edtHomePredict = rootView.findViewById(R.id.edtHomePredict);

        tvAwayPredict = rootView.findViewById(R.id.tvAwayPredict);
        tvHomePredict = rootView.findViewById(R.id.tvHomePredict);

        tvAwayHeader = rootView.findViewById(R.id.tvAwayHeader);
        tvHomeHeader = rootView.findViewById(R.id.tvHomeHeader);
        tvHome = rootView.findViewById(R.id.tvHome);
        tvAway = rootView.findViewById(R.id.tvAway);
        tvCupTitle = rootView.findViewById(R.id.tvCupTitle);
        tvDate = rootView.findViewById(R.id.tvDate);
        tvMatchResult = rootView.findViewById(R.id.tvMatchResult);

        tvPredictEmpty = rootView.findViewById(R.id.tvPredictEmpty);
        rlShirt = rootView.findViewById(R.id.rlShirt);
        rlShirt.setOnClickListener(v -> startActivity(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class))
        );
        imgAwayHeader = rootView.findViewById(R.id.imgAwayHeader);
        imgAway = rootView.findViewById(R.id.imgAway);
        imgAwayPredict = rootView.findViewById(R.id.imgAway3);
        imgHomeHeader = rootView.findViewById(R.id.imgHomeHeader);
        imgHome = rootView.findViewById(R.id.imgHome1);
        imgHomePredict = rootView.findViewById(R.id.imgHome3);

        vHome = rootView.findViewById(R.id.vHome);
        vHome2 = rootView.findViewById(R.id.vHome2);
        vAway = rootView.findViewById(R.id.vAway);
        vAway2 = rootView.findViewById(R.id.vAway2);

        pieChart = AnyChart.pie();
        if (!isPredictable)
        {

            sepHome.setVisibility(View.GONE);
            sepAway.setVisibility(View.GONE);
            edtHomePredict.setVisibility(View.GONE);
            edtAwayPredict.setVisibility(View.GONE);
            btnSendPredict.setVisibility(View.GONE);
        }
        getBaseData();

        btnSendPredict.setOnClickListener(v ->
        {
            if (edtAwayPredict.getText().toString().trim().equalsIgnoreCase(""))
            {
                edtAwayPredict.requestFocus();
                showToast(getActivity(), "مقدار پیش بینی خود را وارد نمایید.", R.color.red);
            }
            else if (edtHomePredict.getText().toString().trim().equalsIgnoreCase(""))
            {
                edtHomePredict.requestFocus();
                showToast(getActivity(), "مقدار پیش بینی خود را وارد نمایید.", R.color.red);
            }
            else
            {
                btnSendPredict.startAnimation();
                btnSendPredict.setClickable(false);

                SendPredictRequest request = new SendPredictRequest();
                request.setMatchId(matchPredict.getId());
                request.setAwayTeamDcore(Integer.parseInt(edtAwayPredict.getText().toString().trim()));
                request.setHomeTeamDcore(Integer.parseInt(edtHomePredict.getText().toString().trim()));

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
                                showAlert(getActivity(), response.info.message, 0);
                            }
                            else
                            {
                                mainView.onSetPredictCompleted(matchPredict, true, response.info.message);
                            }
                        }
                        catch (NullPointerException e)
                        {
                            showAlert(getActivity(), "خطای ارتباط با سرور!" + "\n" + "لطفا مجددا اقدام نمایید.", R.string.error);
                        }
                    }

                    @Override
                    public void onError(String message)
                    {
                        btnSendPredict.revertAnimation(PredictFragment.this);
                        btnSendPredict.setClickable(true);

                        showAlert(getActivity(), "خطای ارتباط با سرور!" + "\n" + "لطفا مجددا اقدام نمایید.", R.string.error);
                    }
                });
            }
        });
    }

    private void getBaseData()
    {
        mainView.showLoading();
        SingletonService.getInstance().getPredictService().getPredictService(matchPredict.getId(), this);
    }

    @Override
    public void onReady(WebServiceClass<GetPredictResponse> response)
    {
        mainView.hideLoading();
        if (response == null || response.data == null)
        {
            showErrorAndBackToMain("خطا در دریافت اطلاعات از سرور!");
            return;
        }
        if (response.info.statusCode != 200)
        {
            showErrorAndBackToMain(response.info.message);
            return;
        }
        else
        {
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

            setImageIntoIV(imgAwayHeader, response.data.getAwayTeamLogo());
            setImageIntoIV(imgHomeHeader, response.data.getHomeTeamLogo());

            tvHomeHeader.setText(response.data.getHomeTeamName());
            tvAwayHeader.setText(response.data.getAwayTeamName());

            setImageIntoIV(imgHome, response.data.getHomeTeamLogo());
            setImageIntoIV(imgAway, response.data.getAwayTeamLogo());

            setImageIntoIV(imgHomePredict, response.data.getHomeTeamLogo());
            setImageIntoIV(imgAwayPredict, response.data.getAwayTeamLogo());

            tvHome.setText(response.data.getHomeTeamName());
            tvAway.setText(response.data.getAwayTeamName());

            tvCupTitle.setText(response.data.getCup());
            tvDate.setText(response.data.getMatchDatetimeStr());

            vAway.setBackgroundColor(Color.parseColor(response.data.getAwayTeamColorCode()));
            vAway2.setBackgroundColor(Color.parseColor(response.data.getAwayTeamColorCode()));
            vHome.setBackgroundColor(Color.parseColor(response.data.getHomeTeamColorCode()));
            vHome2.setBackgroundColor(Color.parseColor(response.data.getHomeTeamColorCode()));

//            if (!response.data.getYouPredict())
//            {
//
//            }
            if (response.data.getYouPredict())
            {
                String result[] = response.data.getYouPredictResult().split("\\|");
                if (isPredictable)
                {
                    edtHomePredict.setText(String.valueOf(Integer.parseInt(result[0])));
                    edtAwayPredict.setText(String.valueOf(Integer.parseInt(result[1])));
                }
                else
                {
                    tvHomePredict.setText(String.valueOf(Integer.parseInt(result[0])));
                    tvAwayPredict.setText(String.valueOf(Integer.parseInt(result[1])));

                    sepHome.setVisibility(View.GONE);
                    sepAway.setVisibility(View.GONE);
                    edtHomePredict.setVisibility(View.GONE);
                    edtAwayPredict.setVisibility(View.GONE);
                    btnSendPredict.setVisibility(View.GONE);
                    tvHomePredict.setVisibility(View.VISIBLE);
                    tvAwayPredict.setVisibility(View.VISIBLE);
                }
            }

            tvMatchResult.setText(response.data.getAwayScore() + " - " + response.data.getHomeScore());

            if (response.data.getPredict() == null || response.data.getPredict().isEmpty() ||
                    response.data.getChart() == null || response.data.getChart().isEmpty())
            {
                llChart.setVisibility(View.GONE);
                tvPredictEmpty.setVisibility(View.VISIBLE);
            } else
            {
                llChart.setVisibility(View.VISIBLE);
                tvPredictEmpty.setVisibility(View.GONE);

//                List<DataEntry> data = new ArrayList<>();
                List<DataEntry> data = new ArrayList<>();
                ArrayList<String> colorList = new ArrayList<>();
//                String color[] = new String[3];
                for (Chart chart : response.data.getChart())
                {
                    try
                    {
                        if (chart.getChartPrediction() == 0) //0 = مساوی
                        {
                            colorList.add("#de9b89");
//                            PredictDataEntry item = new PredictDataEntry("مساوی", chart.getTotalUser(), getPersianChar(String.valueOf(chart.getTotalUser())));
////                            item.setValue("مساوی", getPersianChar(String.valueOf(chart.getTotalUser())));
//                            data.add(item);

                            data.add(new ValueDataEntry("مساوی", chart.getTotalUser()));
                        }
                        else if (chart.getChartPrediction() == 1) //1 = میزبان برنده
                        {
                            colorList.add(response.data.getHomeTeamColorCode());
//                            PredictDataEntry item = new PredictDataEntry("برد " + response.data.getHomeTeamName(),
//                                    chart.getTotalUser(),
//                                    getPersianChar(String.valueOf(chart.getTotalUser())));
////                            item.setValue("برد " + response.data.getHomeTeamName(),
////                                    getPersianChar(String.valueOf(chart.getTotalUser())));
//                            data.add(item);

                            data.add(new ValueDataEntry("برد " + response.data.getHomeTeamName(),
                                    chart.getTotalUser()));
                        }
                        else if (chart.getChartPrediction() == 2) //2 = مهمان برنده
                        {
                            colorList.add(response.data.getAwayTeamColorCode());
//                            PredictDataEntry item = new PredictDataEntry("برد " + response.data.getAwayTeamName(),
//                                    chart.getTotalUser(),
//                                    getPersianChar(String.valueOf(chart.getTotalUser())));
////                            item.setValue("برد " + response.data.getAwayTeamName(),
////                                    getPersianChar(String.valueOf(chart.getTotalUser())));
//                            data.add(item);

                            data.add(new ValueDataEntry("برد " + response.data.getAwayTeamName(),
                                    chart.getTotalUser()));
                        }
                    }
                    catch (Exception e)
                    {

                    }
                }
                pieChart.data(data);

                String color[] = new String[colorList.size()];
//                String color[] = {"#de9b89", response.data.getHomeTeamColorCode(), response.data.getAwayTeamColorCode()};
                for (int i = 0; i < colorList.size(); i++)
                {
                    color[i] = colorList.get(i);
                }
                pieChart.palette(color);

                pieChart.labels().position("outside");
                pieChart.labels().fontColor("#000");
//                Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/iran_sans_normal.ttf");
                Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/IRANSansMobile_Bold.ttf");
                pieChart.labels().fontFamily(face.toString());
//                File font = new File("file:///android_asset/fonts/iran_sans_normal.ttf");

                pieChart.legend()
//                        .position("center-bottom")
                        .position("right")
                        .itemsLayout(LegendLayout.VERTICAL)
                        .textDirection(Direction.RTL)
                        .align(Align.CENTER);

                pieChart.tooltip()
//                        .title().align("right")
                        .enabled(false);
//                pieChart.tooltip()
//                        .format("درصد پیش بینی: " + )
//                        .enabled(true);
//                showError(getActivity(), pieChart.tooltip().getJsBase());
//                pieChart.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"})
//                {
//                    @Override
//                    public void onClick(Event event)
//                    {
//                        pieChart.tooltip().format(event.getData().put("ارزش: ", event.getData().get("value")));
//                    }
//                });

                chartViewPie.setChart(pieChart);

                if (response.data.getPredict().isEmpty())
                {
                    llChartLinear.setVisibility(View.GONE);
                }

                List<Predict> predictList = response.data.getPredict();
//                Collections.reverse(predictList);

                if (predictList.size() >= 1)
                {
                    LinearLayout.LayoutParams paramsSpace = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    params.weight = Float.valueOf(predictList.get(0).getTotalUser().toString());
                    paramsSpace.weight = Float.valueOf(String.valueOf(100 - predictList.get(0).getTotalUser()));
                    rlChartPredictOne.setLayoutParams(params);
                    spChartPredictOne.setLayoutParams(paramsSpace);

                    llChart1.setVisibility(View.VISIBLE);
                    tvChartTotalUserOne.setText(predictList.get(0).getTotalUser().toString() + " %");
                    vColorHomeOne.setBackgroundColor(Color.parseColor(response.data.getHomeTeamColorCode()));
                    vColorAwayOne.setBackgroundColor(Color.parseColor(response.data.getAwayTeamColorCode()));
                    String result[] = predictList.get(0).getPredict().split("\\|");
                    tvChartPredictOne.setText(Integer.parseInt(result[1]) + "-" + Integer.parseInt(result[0]));
                }
//                else
//                {
//                    llChart2.setVisibility(View.GONE);
//                    llChart3.setVisibility(View.GONE);
//                }

                if (predictList.size() >= 2)
                {
                    LinearLayout.LayoutParams paramsSpace = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    params.weight = Float.valueOf(predictList.get(1).getTotalUser().toString());
                    paramsSpace.weight = Float.valueOf(String.valueOf(100 - predictList.get(1).getTotalUser()));
                    rlChartPredictTwo.setLayoutParams(params);
                    spChartPredictTwo.setLayoutParams(paramsSpace);

                    llChart2.setVisibility(View.VISIBLE);
                    tvChartTotalUserTwo.setText(predictList.get(1).getTotalUser().toString() + " %");
                    vColorHomeTwo.setBackgroundColor(Color.parseColor(response.data.getHomeTeamColorCode()));
                    vColorAwayTwo.setBackgroundColor(Color.parseColor(response.data.getAwayTeamColorCode()));
                    String result[] = predictList.get(1).getPredict().split("\\|");
                    tvChartPredictTwo.setText(Integer.parseInt(result[1]) + "-" + Integer.parseInt(result[0]));
                }
                else
                {
                    llChart2.setVisibility(View.GONE);
                    llChart3.setVisibility(View.GONE);
                }

                if (predictList.size() == 3)
                {
                    LinearLayout.LayoutParams paramsSpace = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
                    params.weight = Float.valueOf(predictList.get(2).getTotalUser().toString());
                    paramsSpace.weight = Float.valueOf(String.valueOf(100 - predictList.get(2).getTotalUser()));
                    rlChartPredictThree.setLayoutParams(params);
                    spChartPredictThree.setLayoutParams(paramsSpace);

                    llChart3.setVisibility(View.VISIBLE);
                    tvChartTotalUserThree.setText(predictList.get(2).getTotalUser().toString() + " %");
                    vColorHomeThree.setBackgroundColor(Color.parseColor(response.data.getHomeTeamColorCode()));
                    vColorAwayThree.setBackgroundColor(Color.parseColor(response.data.getAwayTeamColorCode()));
                    String result[] = predictList.get(2).getPredict().split("\\|");
                    tvChartPredictThree.setText(Integer.parseInt(result[1]) + "-" + Integer.parseInt(result[0]));
                }
                else if (predictList.size() < 3)
                {
                    llChart3.setVisibility(View.GONE);
                }

                vColorHomeFour.setBackgroundColor(Color.parseColor(response.data.getHomeTeamColorCode()));
                vColorAwayFour.setBackgroundColor(Color.parseColor(response.data.getAwayTeamColorCode()));

                tvHomeChartTitle.setText(response.data.getHomeTeamName());
                tvAwayChartTitle.setText(response.data.getAwayTeamName());

            }

        }

    }

    public static String getPersianChar(String charSequence)
    {
        String number = String.valueOf(charSequence);
        return number.replace("1", "۱")
                .replace("2", "۲")
                .replace("3", "۳")
                .replace("4", "۴")
                .replace("5", "۵")
                .replace("6", "۶")
                .replace("7", "۷")
                .replace("8", "۸")
                .replace("9", "۹")
                .replace("0", "۰");
    }

    @Override
    public void onError(String message)
    {
        mainView.hideLoading();
        Logger.e("-onError-", "Error: " + message);
        showErrorAndBackToMain("خطا در دریافت اطلاعات از سرور!");
    }

    private void setImageIntoIV(ImageView imageView, String link)
    {
        Picasso.with(getActivity()).load(link).into(imageView);
    }

    private RelativeLayout getWinLoseListView(String itemStr)
    {
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_win_lose, null, false);

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

    private void showErrorAndBackToMain(String message)
    {
        MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), getResources().getString(R.string.error),
                message, false, new MessageAlertDialog.OnConfirmListener()
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
        dialog.show(getActivity().getFragmentManager(), "dialog");
    }

    @Override
    public void onAnimationEnd()
    {
        btnSendPredict.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.background_button_login));
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

}
