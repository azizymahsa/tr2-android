package com.traap.traapapp.ui.fragments.matchSchedule.leaguse.pastResult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.traap.traapapp.apiServices.model.league.pastResult.request.RequestPastResult;
import com.traap.traapapp.apiServices.model.league.pastResult.response.ResponsePastResult;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.adapters.Leaguse.DataBean;
import com.traap.traapapp.ui.adapters.Leaguse.pastResult.PastResultAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;


public class PastResultFragment
        extends BaseFragment implements OnAnimationEndListener, View.OnClickListener,
        OnServiceStatus<WebServiceClass<ResponsePastResult>>//, OnBackPressed
{
    private String teamId = "";
    private View rootView;

    private MainActionView mainView;

    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName,tvPopularPlayer;
    private View imgBack, imgMenu;

    /*scroll view*/
    public List<DataBean> data = new ArrayList<>();
    private RecyclerView leagRecycler;
    private PastResultAdapter fixTableAdapter;


    private ImageView imgLogo;
    private String logoPath;
    private TextView tvNameLeage;
    private String logoTitle="";

    public PastResultFragment()
    {
    }

    /*@Override
    public void onBackPressed() {
        final Myfragment fragment = (Myfragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);

        if (fragment.allowBackPressed()) { // and then you define a method allowBackPressed with the logic to allow back pressed or not
            super.onBackPressed();
        }
    }
*/

    public PastResultFragment(MainActionView mainView, String teamId)
    {
        this.mainView = mainView;
        this.teamId = teamId;

    }


    public static PastResultFragment newInstance(MainActionView mainView, String teamId, String logoPath,String logoTitle)
    {
        PastResultFragment f = new PastResultFragment();

        Bundle args = new Bundle();
        args.putString("teamId", teamId);
        args.putString("logoPath", logoPath);
        args.putString("logoTitle", logoTitle);

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
            logoPath = getArguments().getString("logoPath");
            logoTitle = getArguments().getString("logoTitle");
        }

    }

    public void initView()
    {
        try
        {
            leagRecycler = rootView.findViewById(R.id.leagRecycler);
            //Toolbar Create
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

            tvNameLeage=rootView.findViewById(R.id.tvNameLeage);
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
        RequestPastResult request = new RequestPastResult();
        request.setTeam(teamId);
        SingletonService.getInstance().getLiveScoreService().PastResultService(PastResultFragment.this, request);
    }


    @Override
    public void onReady(WebServiceClass<ResponsePastResult> response)
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


                leagRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                fixTableAdapter = new PastResultAdapter(response.data.getResults(), getActivity());
                //fixTableAdapter.setClickListener(this);
                leagRecycler.setAdapter(fixTableAdapter);

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
        mainView.showError(message);
        mainView.hideLoading();
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


 /*   @Override
    public void onBackPressed()
    {
        getActivity().getSupportFragmentManager().popBackStack();
    }*/
}
