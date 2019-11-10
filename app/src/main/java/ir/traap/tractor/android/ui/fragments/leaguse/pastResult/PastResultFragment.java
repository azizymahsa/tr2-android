package ir.traap.tractor.android.ui.fragments.leaguse.pastResult;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.league.pastResult.request.RequestPastResult;
import ir.traap.tractor.android.apiServices.model.league.pastResult.response.ResponsePastResult;
import ir.traap.tractor.android.singleton.SingletonContext;
import ir.traap.tractor.android.ui.adapters.Leaguse.DataBean;
import ir.traap.tractor.android.ui.adapters.Leaguse.pastResult.PastResultAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;


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


    public static PastResultFragment newInstance(MainActionView mainView, String teamId, String logoPath)
    {
        PastResultFragment f = new PastResultFragment();

        Bundle args = new Bundle();
        args.putString("teamId", teamId);
        args.putString("logoPath", logoPath);

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

            tvUserName.setText(Prefs.getString("mobile", ""));

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

            tvTitle.setText("برنامه بازی");
            tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", ""));

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
