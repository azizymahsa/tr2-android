package com.traap.traapapp.ui.fragments.survey;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.traap.traapapp.apiServices.model.survey.listSurvey.Result;
import com.traap.traapapp.apiServices.model.survey.listSurvey.SurveyListResponse;
import com.traap.traapapp.apiServices.model.survey.putSurvey.Answers;
import com.traap.traapapp.apiServices.model.survey.putSurvey.PutSurveyRequest;
import com.traap.traapapp.apiServices.model.survey.putSurvey.PutSurveyResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.adapters.survey.SurveyDetailAdapter;
import com.traap.traapapp.ui.adapters.survey.SurveyDetailRadioGroupAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import library.android.eniac.flight.adapter.PassengerAdapter;
import library.android.service.model.flight.reservation.request.TravelerList;

/**
 * Created by MahtabAzizi on 5/4/2020.
 */
public class SurveyFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<SurveyDetailResponse>>, View.OnClickListener
{

    private MainActionView mainView;
    private View view;
    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvPopularPlayer,tvTitleFill;
    private View imgBack, imgMenu, rlShirt;
    private CircularProgressButton btnConfirm;
    private ArrayList<Question> surveyDetailList= new ArrayList<>();
    private RecyclerView rvQuestion;
    private SurveyDetailAdapter detailAdapter;
    private Question item;
    private ArrayList<Answers> answers;
    private LinearLayout llFill,llEmpty;
    private Integer idSurvey=0;


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
            tvTitle.setText("نظرسنجی");
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

            llFill=view.findViewById(R.id.llFill);
            llEmpty=view.findViewById(R.id.llEmpty);
            tvTitleFill=view.findViewById(R.id.tvTitleFill);
            llEmpty.setVisibility(View.VISIBLE);
            llFill.setVisibility(View.GONE);

            btnConfirm=view.findViewById(R.id.btnConfirm);
            btnConfirm.setText("ثبت نظرسنجی");
            btnConfirm.setOnClickListener(this);

            rvQuestion=view.findViewById(R.id.rvQuestion);

            detailAdapter = new SurveyDetailAdapter(surveyDetailList,this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

            rvQuestion.setLayoutManager(linearLayoutManager);
            rvQuestion.setItemAnimator(new DefaultItemAnimator());
            rvQuestion.setAdapter(detailAdapter);


            requestGetSurveyList();


        }catch (Exception e){
        }
    }

    private void requestGetSurveyList()
    {
        SingletonService.getInstance().getSurveyDetailService().getSurveyList(new OnServiceStatus<WebServiceClass<SurveyListResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<SurveyListResponse> response)
            {
                try
                {

                    if (response.info.statusCode == 200)
                    {
                        for (int i =0;i<response.data.getResults().size();i++)
                        {
                            Result surveyList = response.data.getResults().get(i);
                            if (surveyList.getIsActive()){
                                llEmpty.setVisibility(View.GONE);
                                llFill.setVisibility(View.VISIBLE);
                                tvTitleFill.setText(surveyList.getTitle());
                                idSurvey=surveyList.getId();

                                SingletonService.getInstance().getSurveyDetailService().getSurveyDetail(
                                        idSurvey,
                                        SurveyFragment.this
                                );

                            }
                        }

                    } else
                    {

                    }
                } catch (Exception e)
                {

                }
            }

            @Override
            public void onError(String message)
            {
                hideLoading();
                if (!Tools.isNetworkAvailable(getActivity()))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(getContext(), "خطا در دریافت اطلاعات از سرور!");
                } else
                {
                    showAlert(getContext(), R.string.networkErrorMessage, R.string.networkError);
                }
            }
        });
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
        surveyDetailList.clear();
        surveyDetailList.addAll(data.getQuestions());

        detailAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String message)
    {
        hideLoading();
        if (!Tools.isNetworkAvailable(this.getActivity()))
        {
            Logger.e("-OnError-", "Error: " + message);
            showError(this.getContext(), "خطا در دریافت اطلاعات از سرور!");
        } else
        {
            showAlert(this.getContext(), R.string.networkErrorMessage, R.string.networkError);
        }
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.btnConfirm:
                answers = new ArrayList<Answers>();

                    boolean valid = true;

                    for (int i = 0; i < surveyDetailList.size(); i++)
                    {
                        item = surveyDetailList.get(i);
                        SurveyDetailAdapter holders = ((SurveyDetailAdapter) rvQuestion.getAdapter());
                        SurveyDetailAdapter.ViewHolder holder = holders.getMyViewHolders().get(i);

                        if (item.getQuestionType()==1){
                            SurveyDetailRadioGroupAdapter groupAdapterHolders=
                                    ((SurveyDetailRadioGroupAdapter) holder.rvQuestionRadioGroup.getAdapter());
                            for (int j = 0; j < surveyDetailList.get(i).getOptions().size(); j++)
                            {
                                SurveyDetailRadioGroupAdapter.SurveyDetailViewHolder detailViewHolder = groupAdapterHolders.getMyViewHolders().get(j);
                                if (surveyDetailList.get(i).getOptions().get(j).getCheck()){
                                    answers.add(new Answers(surveyDetailList.get(i).getId().toString()
                                            ,surveyDetailList.get(i).getOptions().get(j).getId().toString()
                                            ,"" ));
                                }

                            }

                        }else if (item.getQuestionType()==2){
                            SurveyDetailRadioGroupAdapter groupAdapterHolders=
                                    ((SurveyDetailRadioGroupAdapter) holder.rvQuestionRadioGroup.getAdapter());
                            for (int k = 0; k < surveyDetailList.get(i).getOptions().size(); k++)
                            {
                                SurveyDetailRadioGroupAdapter.SurveyDetailViewHolder detailViewHolder = groupAdapterHolders.getMyViewHolders().get(k);
                                if (surveyDetailList.get(i).getOptions().get(k).getCheck()){
                                    answers.add(new Answers(surveyDetailList.get(i).getId().toString()
                                            ,surveyDetailList.get(i).getOptions().get(k).getId().toString()
                                            ,"" ));

                                }

                            }
                        }

                        else if (item.getQuestionType() == 3)
                        {
                            if (!TextUtils.isEmpty(holder.etAnswer.getText()) )
                            {
                                answers.add(new Answers(surveyDetailList.get(i).getId().toString()
                                        ,""
                                        ,holder.etAnswer.getText().toString() ));

                            } else if (item.isMandatory())
                            {
                                valid = false;
                                showAlertFailure(getContext(),"پاسخ به سوالات مشخص شده اجباری می باشد.","",false);

                            }
                        }

                     //   break;
                    }
                if (valid)
                    putSurvey(answers);
        }
    }

    private void putSurvey(ArrayList<Answers> answers)
    {
        showLoading();
        PutSurveyRequest putSurveyRequest = new PutSurveyRequest();
        putSurveyRequest.setAnswers(answers);

        SingletonService.getInstance().getSurveyDetailService().putSurvey(
                idSurvey,putSurveyRequest,new OnServiceStatus<WebServiceClass<PutSurveyResponse>>()
                {
                    @Override
                    public void onReady(WebServiceClass<PutSurveyResponse> response)
                    {
                        hideLoading();
                        try
                        {

                            if (response.info.statusCode == 200)
                            {

                                showAlertSuccess(getContext(),"پاسخ نظرسنجی شما با موفقیت ثبت شد.","",true);

                            } else
                            {

                            }
                        } catch (Exception e)
                        {

                        }
                    }

                    @Override
                    public void onError(String message)
                    {
                        hideLoading();
                        if (!Tools.isNetworkAvailable(getActivity()))
                        {
                            Logger.e("-OnError-", "Error: " + message);
                            showError(getContext(), "خطا در دریافت اطلاعات از سرور!");
                        } else
                        {
                             showAlertSuccess(getContext(),"پاسخ نظرسنجی شما با موفقیت ثبت شد.","",true);

                            //  showAlert(getContext(), R.string.networkErrorMessage, R.string.networkError);
                        }
                    }
                });

                }

 }
