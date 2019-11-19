package ir.traap.tractor.android.ui.fragments.matchSchedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.league.getLeagues.request.GetLeagueRequest;
import ir.traap.tractor.android.apiServices.model.league.getLeagues.response.ResponseLeage;
import ir.traap.tractor.android.apiServices.model.league.getLeagues.response.Result;
import ir.traap.tractor.android.apiServices.model.matchList.MatchItem;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.ui.adapters.Leaguse.DataBean;
import ir.traap.tractor.android.ui.adapters.Leaguse.FixTableAdapter;
import ir.traap.tractor.android.ui.adapters.Leaguse.matchResult.MatchAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.leaguse.LeagueTableFragment;
import ir.traap.tractor.android.ui.fragments.leaguse.pastResult.PastResultFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;

/**
 * Created by MahtabAzizi on 11/16/2019.
 */
public class PastMatchesFragment extends Fragment implements OnAnimationEndListener, View.OnClickListener
{
    private View rootView;

    private MainActionView mainView;

    /*scroll view*/
    public List<DataBean> data = new ArrayList<>();
    private RecyclerView recyclerView;
    private List<MatchItem> pastMatchesList = new ArrayList<>();
    private MatchAdapter mAdapter;


    public PastMatchesFragment()
    {

    }


    public static PastMatchesFragment newInstance(List<MatchItem> pastMatchesList, MainActionView mainActionView)
    {
        PastMatchesFragment fragment = new PastMatchesFragment();
        Bundle args = new Bundle();

        fragment.setMainView(mainActionView);
        fragment.setData(pastMatchesList);
        return fragment;
    }

    private void setData(List<MatchItem> pastMatchesList)
    {
        this.pastMatchesList = pastMatchesList;
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
            recyclerView = rootView.findViewById(R.id.rclPast);


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

        rootView = inflater.inflate(R.layout.past_match_fragment, container, false);
        initView();
        addDataRecyclerList();

        return rootView;
    }

    private void addDataRecyclerList()
    {

        mAdapter = new MatchAdapter(pastMatchesList,getContext());
        recyclerView.setAdapter(mAdapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MatchAdapter(pastMatchesList, getActivity());
        recyclerView.setAdapter(mAdapter);
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
