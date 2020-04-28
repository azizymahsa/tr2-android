package com.traap.traapapp.ui.fragments.matchSchedule.pastResult;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getLast5PastMatch.request.Last5PastMatchRequest;
import com.traap.traapapp.apiServices.model.getLast5PastMatch.response.Last5PastMatchResponse;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.LeagueTableParent;
import com.traap.traapapp.enums.MatchScheduleParent;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.adapters.Leaguse.DataBean;
import com.traap.traapapp.ui.adapters.Leaguse.pastResult.PastResultAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.matchSchedule.MatchScheduleFragment;
//import com.traap.traapapp.ui.fragments.matchSchedule.MatchScheduleFragment2;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class PastResultFragment extends BaseFragment implements OnAnimationEndListener,
//        OnServiceStatus<WebServiceClass<ResponsePastResult>>//, OnBackPressed
        OnServiceStatus<WebServiceClass<Last5PastMatchResponse>>//, OnBackPressed
{
    private String teamId = "0", matchId = "0";
    private Boolean isPredictable = false;
    private View rootView;

    private LeagueTableParent parent;

    private Context context;

    private MainActionView mainView;

    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu;

    /*scroll view*/
    public List<DataBean> data = new ArrayList<>();
    private RecyclerView leagRecycler;
    private PastResultAdapter fixTableAdapter;


    private ImageView imgLogo;
    private String logoPath;
    private TextView tvNameLeage;
    private String logoTitle = "";

    public PastResultFragment()
    {
    }


    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    public static PastResultFragment newInstance(LeagueTableParent parent,
                                                 MainActionView mainView,
                                                 String matchId,
                                                 Boolean isPredictable,
                                                 String teamId,
                                                 String logoPath,
                                                 String logoTitle)
    {
        PastResultFragment f = new PastResultFragment();

        Bundle args = new Bundle();
        args.putString("teamId", teamId);
        args.putString("matchId", matchId);
        args.putString("logoPath", logoPath);
        args.putString("logoTitle", logoTitle);
        args.putBoolean("isPredictable", isPredictable);

        f.setArguments(args);
        f.setMainView(mainView);
        f.setParent(parent);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }


    private void setParent(LeagueTableParent parent)
    {
        this.parent = parent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            teamId = getArguments().getString("teamId");
            matchId = getArguments().getString("matchId");
            logoPath = getArguments().getString("logoPath");
            logoTitle = getArguments().getString("logoTitle");
            isPredictable = getArguments().getBoolean("isPredictable", false);
        }

        EventBus.getDefault().register(this);
    }

    public void initView()
    {
        try
        {
            leagRecycler = rootView.findViewById(R.id.leagueRecycler);
            //Toolbar Create
            mToolbar = rootView.findViewById(R.id.toolbar);
            tvUserName = mToolbar.findViewById(R.id.tvUserName);

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
            tvTitle = rootView.findViewById(R.id.tvTitle);

            imgBack = rootView.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                if (parent == LeagueTableParent.MatchScheduleFragment)
                {
                    Prefs.putInt("LeagueTableParent",LeagueTableParent.MatchScheduleFragment.ordinal());
//                    mainView.onBackToMatch();
                    getActivity().onBackPressed();
                }
                else if (parent == LeagueTableParent.PredictFragment)
                {
//                    mainView.onPredictLeagueTable(Integer.parseInt(teamId), Integer.parseInt(matchId), isPredictable);
                    Prefs.putInt("LeagueTableParent",LeagueTableParent.PredictFragment.ordinal());
                    getActivity().onBackPressed();
                }
//                getActivity().onBackPressed();
            });

            tvTitle.setText("برنامه بازی ها");
            tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", "12"));

            tvNameLeage = rootView.findViewById(R.id.tvNameLeage);
            tvNameLeage.setText(logoTitle);
            imgLogo = rootView.findViewById(R.id.imgLogo);
            Picasso.with(SingletonContext.getInstance().getContext()).load(logoPath).into(imgLogo);

        } catch (Exception e)
        {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            rootView = null;
        }

        rootView = inflater.inflate(R.layout.past_result_fragment, container, false);
        initView();
        sendRequest();

        return rootView;
    }


    private void sendRequest()
    {
        mainView.showLoading();
//        RequestPastResult request = new RequestPastResult();
//        request.setTeamLiveScoreId(teamId);
//        SingletonService.getInstance().getLiveScoreService().PastResultService(PastResultFragment.this, request);

        Last5PastMatchRequest request1 = new Last5PastMatchRequest();
        request1.setLiveScoreId(teamId);
        SingletonService.getInstance().getLiveScoreService().getPastResult_v2_Service(request1, this);
    }


    @Override
    public void onReady(WebServiceClass<Last5PastMatchResponse> response)
    {
        try
        {
            mainView.hideLoading();
            if (response == null || response.info == null)
            {
                showMyAlertFailure("خطا در دریافت اطلاعات از سرور!");
                return;
            }
            if (response.info.statusCode != 200)
            {
                showMyAlertFailure(response.info.message);
                return;
            }
            else
            {
                leagRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                fixTableAdapter = new PastResultAdapter(response.data.getLastMatchList(), getActivity());
                //fixTableAdapter.setClickListener(this);
                leagRecycler.setAdapter(fixTableAdapter);
            }
        } catch (Exception e)
        {
            mainView.showError(e.getMessage());
            mainView.hideLoading();
        }
    }

    private void showMyAlertFailure(String message)
    {
        MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, "", message, false,
                "بازگشت به خانه", "", true,
                MessageAlertDialog.TYPE_ERROR, new MessageAlertDialog.OnConfirmListener()
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
        dialog.setCancelable(false);
        dialog.show(((Activity) context).getFragmentManager(), "dialog");
    }

    @Override
    public void onError(String message)
    {
        // mainView.showError(message);
        mainView.hideLoading();
        if (Tools.isNetworkAvailable((Activity) context))
        {
            Logger.e("-OnError-", "Error: " + message);
            mainView.showError("خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            mainView.showError(String.valueOf(R.string.networkErrorMessage));

            //showAlert(getApplicationContext(), R.string.networkErrorMessage, R.string.networkError);
        }
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
            tvPopularPlayer.setText(String.valueOf(headerModel.getPopularNo()));
        }
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

//    public void onBackClicked(ArrayList<MatchItem> matchBuyable)
//    {
//
////        MatchScheduleFragment2 matchScheduleFragment2 = MatchScheduleFragment.newInstance(mainView, MatchScheduleParent.MainActivity, matchBuyable, 1);
//        MatchScheduleFragment matchScheduleFragment = MatchScheduleFragment.newInstance(mainView, MatchScheduleParent.MainActivity, matchBuyable, 2);
//
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, matchScheduleFragment, "leagueTableFragment").commit();
//    }

}
