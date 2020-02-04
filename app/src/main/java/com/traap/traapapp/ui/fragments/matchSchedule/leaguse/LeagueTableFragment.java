package com.traap.traapapp.ui.fragments.matchSchedule.leaguse;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.league.getLeagues.request.GetLeagueRequest;
import com.traap.traapapp.apiServices.model.league.getLeagues.response.ResponseLeage;
import com.traap.traapapp.apiServices.model.league.getLeagues.response.Result;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.adapters.Leaguse.FixTableAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.adapters.Leaguse.DataBean;
import com.traap.traapapp.ui.fragments.matchSchedule.MatchScheduleFragment;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;


public class LeagueTableFragment
        extends BaseFragment implements OnAnimationEndListener, View.OnClickListener,
        OnServiceStatus<WebServiceClass<ResponseLeage>>, FixTableAdapter.ItemClickListener
{
    private View rootView;

    private MainActionView mainView;

    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu;

    /*scroll view*/
    public List<DataBean> data = new ArrayList<>();
    private RecyclerView leagRecycler;
    private FixTableAdapter fixTableAdapter;


    public LeagueTableFragment()
    {

    }


    public static LeagueTableFragment newInstance(MainActionView mainView)
    {
        LeagueTableFragment f = new LeagueTableFragment();
        Bundle args = new Bundle();


        f.setArguments(args);
        f.setMainView(mainView);
        return f;
    }

    public static LeagueTableFragment newInstance(String tab3, MatchScheduleFragment matchScheduleFragment, MainActionView mainActionView)
    {
        LeagueTableFragment fragment = new LeagueTableFragment();


        return fragment;

    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    public void initView()
    {
        try
        {
            leagRecycler = rootView.findViewById(R.id.leagueRecycler);

            mToolbar = rootView.findViewById(R.id.toolbar);
            tvUserName = mToolbar.findViewById(R.id.tvUserName);

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mainView.openDrawer();
                }
            });
            //toolbar
            tvTitle = rootView.findViewById(R.id.tvTitle);
            imgMenu = rootView.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
            imgBack = rootView.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvTitle.setText("برنامه بازی ها");
            tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", "12"));
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

        rootView = inflater.inflate(R.layout.league_table_fragment, container, false);
        initView();
        sendRequest();


        return rootView;
    }


    private void sendRequest()
    {
        try
        {
            mainView.showLoading();
            GetLeagueRequest request = new GetLeagueRequest();
            request.setLeague("24");
            SingletonService.getInstance().getLiveScoreService().LeaguesService(LeagueTableFragment.this, request);
        }catch (Exception e){
            Logger.e("-sendRequest-", "Error: " + e.getMessage());

        }

    }


    @Override
    public void onReady(WebServiceClass<ResponseLeage> response)
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
                //scrollView
                for (int i = 0; i < response.data.getResults().size(); i++)
                {
                    Result responseLeague = response.data.getResults().get(i);
                    data.add(new DataBean(
                            responseLeague.getTeamId(),
                            //title
                            responseLeague.getName(),
                            responseLeague.getMatches(),
                            responseLeague.getWon(),
                            responseLeague.getDrawn(),
                            responseLeague.getLost(),
                            responseLeague.getGoalsScored(),
                            responseLeague.getGoalsConceded(),
                            responseLeague.getGoalDiff(),
                            responseLeague.getPoints(),
                            responseLeague.getTeamLogo()));
                }
                fixTableAdapter = new FixTableAdapter(data, this.getContext());
                leagRecycler.setAdapter(fixTableAdapter);


                // RecyclerView recyclerView = findViewById(R.id.rvAnimals);
                leagRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                fixTableAdapter = new FixTableAdapter(data, getActivity());
                fixTableAdapter.setClickListener(this);
                leagRecycler.setAdapter(fixTableAdapter);

            }
        } catch (Exception e)
        {
            e.getMessage();
            mainView.showError(e.getMessage());
            mainView.hideLoading();
        }
    }

    @Override
    public void onError(String message)
    {
       // mainView.showError(message);
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

    @Override
    public void onDestroy()
    {
        super.onDestroy();

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

    @Override
    public void onClick(View view)
    {
       /* switch (view.getId())
        {
            case R.id.btnConfirm:

                break;


        }*/

    }


    @Override
    public void onItemClick(View view, int position, String imageLogo, String logoTitle)

    {
        mainView.openPastResultFragment(fixTableAdapter.getItem(position).teamId,imageLogo, logoTitle);
      //  PastResultFragment pastResultFragment = PastResultFragment.newInstance(mainView, fixTableAdapter.getItem(position).teamId,imageLogo, logoTitle);
      // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, pastResultFragment,"pastResult").commit();
    }
}
