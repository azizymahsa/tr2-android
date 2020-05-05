package com.traap.traapapp.ui.fragments.survey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.survey.Question;
import com.traap.traapapp.apiServices.model.survey.SurveyDetailResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.adapters.survey.SurveyDetailAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

/**
 * Created by MahtabAzizi on 5/4/2020.
 */
public class SurveyFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<SurveyDetailResponse>>
{

    private MainActionView mainView;
    private View view;
    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvPopularPlayer;
    private View imgBack, imgMenu, rlShirt;
    private CircularProgressButton btnConfirm;
    private Integer Id=1;
    private ArrayList<Question> surveyDetailList;
    private RecyclerView rvQuestion;


    public static SurveyFragment newInstance(MainActionView mainView)
    {
        SurveyFragment f = new SurveyFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    public SurveyFragment()
    {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_survey, container, false);

        initViews();

        return view;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }
    private void initViews()
    {
        try
        {
            showLoading();

            mToolbar = view.findViewById(R.id.toolbar);
            tvUserName = mToolbar.findViewById(R.id.tvUserName);
            TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
            tvTitle.setText("ارتباط با پشتیبانی");
            mToolbar.findViewById(R.id.imgBack).setOnClickListener(v -> mainView.backToMainFragment());

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            rlShirt = mToolbar.findViewById(R.id.rlShirt);
            rlShirt.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class),100);
                }
            });
            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mainView.openDrawer();
                }
            });
            FrameLayout flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
            flLogoToolbar.setOnClickListener(v -> {
                mainView.backToMainFragment();

            });
            imgMenu = view.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
            imgBack = view.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

            btnConfirm=view.findViewById(R.id.btnConfirm);
            btnConfirm.setText("ثبت نظرسنجی");

            rvQuestion=view.findViewById(R.id.rvQuestion);

            SurveyDetailAdapter detailAdapter = new SurveyDetailAdapter(surveyDetailList,this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

            rvQuestion.setLayoutManager(linearLayoutManager);
            rvQuestion.setItemAnimator(new DefaultItemAnimator());
            rvQuestion.setAdapter(detailAdapter);


            SingletonService.getInstance().getSurveyDetailService().getSurveyDetail(
                    Id,
                    this
            );

            //showAlert();
            //showError();

        }catch (Exception e){
        }
    }
    private void hideLoading()
    {
        mainView.hideLoading();
    }


    private void showLoading()
    {
        mainView.showLoading();
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

    @Override
    public void onReady(WebServiceClass<SurveyDetailResponse> response)
    {
       hideLoading();
        try
        {

            if (response.info.statusCode == 200)
            {
                setQuestions(response.data);

            } else
            {

            }
        } catch (Exception e)
        {

        }
    }

    private void setQuestions(SurveyDetailResponse data)
    {

    }

    @Override
    public void onError(String message)
    {
        if (!Tools.isNetworkAvailable(this.getActivity()))
        {
            Logger.e("-OnError-", "Error: " + message);
            showError(this.getContext(), "خطا در دریافت اطلاعات از سرور!");
        } else
        {
            showAlert(this.getContext(), R.string.networkErrorMessage, R.string.networkError);
        }
    }









}
