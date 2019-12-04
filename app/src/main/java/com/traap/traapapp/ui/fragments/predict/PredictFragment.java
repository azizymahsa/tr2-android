package com.traap.traapapp.ui.fragments.predict;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.anychart.graphics.vector.text.Direction;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
import com.traap.traapapp.apiServices.model.predict.sendPredict.request.SendPredictRequest;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
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

    private View vHome, vHome2, vAway, vAway2;

    private CircularProgressButton btnSendPredict;

    private Pie pieChart;
    private Cartesian cartesian;

    private Boolean isPredictable;

    private MainActionView mainView;

    private AnyChartView chartViewPie, chartViewColumn;

    private Toolbar mToolbar;

    private View rootView;


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

        llAwayResultList = rootView.findViewById(R.id.llAwayResultList);
        llHomeResultList = rootView.findViewById(R.id.llHomeResultList);

        llChart = rootView.findViewById(R.id.llChart);

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

        imgAwayHeader = rootView.findViewById(R.id.imgAwayHeader);
        imgAway = rootView.findViewById(R.id.imgAway);
        imgAwayPredict = rootView.findViewById(R.id.imgAway3);
        imgHomeHeader = rootView.findViewById(R.id.imgHomeHeader);
        imgHome = rootView.findViewById(R.id.imgHome);
        imgHomePredict = rootView.findViewById(R.id.imgHome3);

        vHome = rootView.findViewById(R.id.vHome);
        vHome2 = rootView.findViewById(R.id.vHome2);
        vAway = rootView.findViewById(R.id.vAway);
        vAway2 = rootView.findViewById(R.id.vAway2);

        pieChart = AnyChart.pie();

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
                                showAlert(getActivity(),response.info.message, 0);
                            }
                            else
                            {
                                showAlert(getActivity(),response.info.message, 0);
                            }
                        }
                        catch (NullPointerException e)
                        {
//                        showAlert(getActivity(),response.info.message, R.string.error);
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
        SingletonService.getInstance().getPredictService().getPredictService(matchPredict.getId(), this);
    }

    @Override
    public void onReady(WebServiceClass<GetPredictResponse> response)
    {
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

            for (String itemStr: response.data.getAwayLastPlays())
            {
                llAwayResultList.addView(getWinLoseListView(itemStr));
            }
            for (String itemStr: response.data.getHomeLastPlays())
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

                    edtHomePredict.setVisibility(View.GONE);
                    edtAwayPredict.setVisibility(View.GONE);
                    btnSendPredict.setVisibility(View.GONE);
                }
            }

            tvMatchResult.setText(response.data.getAwayScore() + " - " + response.data.getHomeScore());

            if (response.data.getPredict() == null || response.data.getPredict().isEmpty() ||
                    response.data.getChart() == null || response.data.getChart().isEmpty())
            {
                llChart.setVisibility(View.GONE);
                tvPredictEmpty.setVisibility(View.VISIBLE);
            }
            else
            {
                llChart.setVisibility(View.VISIBLE);
                tvPredictEmpty.setVisibility(View.GONE);

                List<DataEntry> data = new ArrayList<>();

                for (Chart chart: response.data.getChart())
                {
                    try
                    {
                        if (chart.getChartPrediction() == 0) //0 = مساوی
                        {
                            data.add(new ValueDataEntry( "مساوی" , chart.getTotalUser()));
                        }
                        else if (chart.getChartPrediction() == 1) //1 = میزبان برنده
                        {
                            data.add(new ValueDataEntry( "برد " + response.data.getHomeTeamName(), chart.getTotalUser()));
                        }
                        else if (chart.getChartPrediction() == 2) //2 = مهمان برنده
                        {
                            data.add(new ValueDataEntry( "برد " + response.data.getAwayTeamName(), chart.getTotalUser()));
                        }
                    }
                    catch (Exception e)
                    {

                    }
                }

                pieChart.data(data);

                pieChart.labels().position("outside");
                pieChart.labels().fontColor("#000");
//                Typeface face = Typeface.createFromAsset(getActivity().getAssets(),"fonts/iran_sans_normal.ttf");
//                pieChart.labels().fontFamily(face.toString());

                pieChart.legend()
//                        .position("center-bottom")
                        .position("right")
                        .itemsLayout(LegendLayout.VERTICAL)
                        .textDirection(Direction.RTL)
                        .align(Align.CENTER);

//                pieChart.legend().background()

                chartViewPie.setChart(pieChart);

//                cartesian = AnyChart.column();
//                data = new ArrayList<>();
//
//                for (Predict predict: response.data.getPredict())
//                {
//                    String pSplit[] = predict.getPredict().split("\\|");
//                    data.add(new ValueDataEntry(Integer.parseInt(pSplit[1]) + "-" + Integer.parseInt(pSplit[0]), predict.getTotalUser()));
//                }
//                Column column = cartesian.column(data);
//                column.tooltip()
//                        .titleFormat("{%X}")
//                        .position(Position.CENTER_BOTTOM)
//                        .anchor(Anchor.CENTER_BOTTOM)
//                        .offsetX(0d)
//                        .offsetY(5d)
//                        .format("%{%Value}{groupsSeparator: }");
//
//                cartesian.animation(true);
//                cartesian.yScale().minimum(0d);
//                cartesian.yScale().maximum(100d);
//
//                cartesian.yAxis(0).labels().format("%{%Value}{groupsSeparator: }");
////                cartesian.yAxis(0).labels().format();
//
//                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
//                cartesian.interactivity().hoverMode(HoverMode.BY_X);
//
////                cartesian.xAxis(0).title("Product");
////                cartesian.yAxis(0).title("Revenue");
//
//                chartViewColumn.setChart(cartesian);

            }

        }

    }

    @Override
    public void onError(String message)
    {
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
        params.setMargins(magSize,magSize,magSize,magSize);
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
