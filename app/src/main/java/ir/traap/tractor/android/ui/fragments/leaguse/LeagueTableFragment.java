package ir.traap.tractor.android.ui.fragments.leaguse;

import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.feng.fixtablelayout.FixTableLayout;
import com.app.feng.fixtablelayout.inter.ILoadMoreListener;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.league.request.GetLeagueRequest;
import ir.traap.tractor.android.apiServices.model.league.response.ResponseLeage;
import ir.traap.tractor.android.apiServices.model.league.response.Result;
import ir.traap.tractor.android.ui.adapters.Leaguse.FixTableAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.ui.adapters.Leaguse.DataBean;



public class LeagueTableFragment
        extends BaseFragment implements OnAnimationEndListener, View.OnClickListener,
        OnServiceStatus<WebServiceClass<ResponseLeage>>
{
    private View rootView;

    private MainActionView mainView;

    private Toolbar mToolbar;
    private TextView tvUserName;

    /*scroll view*/
    public String[] title = {"تیم","بازی","برد","مساوی","باخت","گل زده","گل خورده",
            "تفاضل","امتیاز"};

    public List<DataBean> data = new ArrayList<>();



    int currentPage = 1;
    int totalPage = 5;
    private RecyclerView leagRecycler;


    public LeagueTableFragment()
    {

    }


    public static LeagueTableFragment newInstance(MainActionView mainView)
    {
        LeagueTableFragment f = new LeagueTableFragment();
        Bundle args = new Bundle();
//        args.putParcelableArrayList("chosenServiceList", chosenServiceList);
//        args.putParcelableArrayList("footballServiceList", footballServiceList);

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

    }

    public void initView()
    {
      //  mToolbar = rootView.findViewById(R.id.toolbar);
        leagRecycler = rootView.findViewById(R.id.leagRecycler);
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

        //toolbars
        toolbarCreate();



        return rootView;
    }

    private void toolbarCreate()
    {
        mToolbar = rootView.findViewById(R.id.toolbar);
        tvUserName = mToolbar.findViewById(R.id.tvUserName);

        tvUserName.setText(Prefs.getString("mobile", ""));

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mainView.openDrawer();
            }
        });

    }

    private void sendRequest()
    {
        GetLeagueRequest request = new GetLeagueRequest();
        request.setLeague("24");
        SingletonService.getInstance().getLiveScoreService().LeaguesService(LeagueTableFragment.this, request);
    }


    @Override
    public void onReady(WebServiceClass<ResponseLeage> response)
    {
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
            for (int i = 0; i < response.data.getResults().size(); i++) {
                Result responseLeage = response.data.getResults().get(i);
                data.add(new DataBean(
                        //title
                        responseLeage.getName(),
                        responseLeage.getMatches(),
                        responseLeage.getWon(),
                        responseLeage.getDrawn(),
                        responseLeage.getLost(),
                        responseLeage.getGoalsScored(),
                        responseLeage.getGoalsConceded(),
                        responseLeage.getGoalDiff(),
                        responseLeage.getPoints(),
                        responseLeage.getTeamLogo()));
            }
            FixTableAdapter fixTableAdapter = new FixTableAdapter(title,data,this.getContext());
            leagRecycler.setAdapter(fixTableAdapter);


           // RecyclerView recyclerView = findViewById(R.id.rvAnimals);
            leagRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            fixTableAdapter = new FixTableAdapter(title,data,getActivity());
            //fixTableAdapter.setClickListener(this);
            leagRecycler.setAdapter(fixTableAdapter);

        }
    }

    @Override
    public void onError(String message)
    {

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


}
