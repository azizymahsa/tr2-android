package com.traap.traapapp.ui.fragments.competitions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getQuestionCompat.Question;
import com.traap.traapapp.apiServices.model.getQuestionCompat.ResponseGetQuestions;

import com.traap.traapapp.apiServices.model.setAnswerQuestions.RequestSetAnswer;
import com.traap.traapapp.apiServices.model.survey.putSurvey.Answers;
import com.traap.traapapp.apiServices.model.survey.putSurvey.PutSurveyRequest;
import com.traap.traapapp.apiServices.model.survey.putSurvey.PutSurveyResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.adapters.compations.questions.CompationDetailAdapter;
import com.traap.traapapp.ui.adapters.compations.questions.CompationDetailRadioGroupAdapter;
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

/**
 * Created by MahsaAzizi on 5/4/2020.
 */
public class QuestionCompationFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<ResponseGetQuestions>>, View.OnClickListener
{

    private MainActionView mainView;
    private View view;
    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvPopularPlayer,tvTitleFill;
    private View imgBack, imgMenu, rlShirt;
    private CircularProgressButton btnConfirm;
    private ArrayList<Question> surveyDetailList= new ArrayList<>();
    private RecyclerView rvQuestion;
    private CompationDetailAdapter detailAdapter;
    private Question item;
    private ArrayList<com.traap.traapapp.apiServices.model.setAnswerQuestions.Question> answers;
    private LinearLayout llFill,llEmpty;
    private Integer idSurvey=0;


    public static QuestionCompationFragment newInstance(MainActionView mainView,Integer idQuees)
    {
        QuestionCompationFragment f = new QuestionCompationFragment();
        f.setMainView(mainView,idQuees);
        return f;
    }

    private void setMainView(MainActionView mainView,Integer idSurvey)
    {
        this.mainView = mainView;
        this.idSurvey=idSurvey;

    }

    public QuestionCompationFragment()
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
            tvTitle.setText("سوالات مسابقه");
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
            llEmpty.setVisibility(View.GONE);
            llFill.setVisibility(View.GONE);

            btnConfirm=view.findViewById(R.id.btnConfirm);
            btnConfirm.setText("ثبت ");
            btnConfirm.setOnClickListener(this);

            rvQuestion=view.findViewById(R.id.rvQuestion);

            detailAdapter = new CompationDetailAdapter(surveyDetailList,this);
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

        SingletonService.getInstance().tractorTeamService().getQuestionCompation(
                idSurvey,
                QuestionCompationFragment.this
        );

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
    public void onReady(WebServiceClass<ResponseGetQuestions> response)
    {
       hideLoading();
        try
        {

            if (response.info.statusCode == 200)
            {
                llEmpty.setVisibility(View.GONE);
                llFill.setVisibility(View.VISIBLE);
                ////  tvTitleFill.setText(surveyList.getTitle());
                // idSurvey=surveyList.getId();

                setQuestions(response.data);

            } else
            {

            }
        } catch (Exception e)
        {

        }
    }

    private void setQuestions(ResponseGetQuestions data)
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
                answers = new ArrayList<com.traap.traapapp.apiServices.model.setAnswerQuestions.Question>();

                    boolean valid = true;

                    for (int i = 0; i < surveyDetailList.size(); i++)
                    {
                        item = surveyDetailList.get(i);
                        CompationDetailAdapter holders = ((CompationDetailAdapter) rvQuestion.getAdapter());
                        CompationDetailAdapter.ViewHolder holder = holders.getMyViewHolders().get(i);

                       // if (item.getQuestionType()==1){
                        CompationDetailRadioGroupAdapter groupAdapterHolders=
                                    ((CompationDetailRadioGroupAdapter) holder.rvQuestionRadioGroup.getAdapter());
                            for (int j = 0; j < surveyDetailList.get(i).getOptions().size(); j++)
                            {
                                CompationDetailRadioGroupAdapter.SurveyDetailViewHolder detailViewHolder = groupAdapterHolders.getMyViewHolders().get(j);
                                if (surveyDetailList.get(i).getOptions().get(j).getCheck()){
                                    answers.add(new com.traap.traapapp.apiServices.model.setAnswerQuestions.Question
                                            (surveyDetailList.get(i).getId().toString()
                                            ,surveyDetailList.get(i).getOptions().get(j).getId().toString()
                                             ));
                                }

                            }

                        //}
                    /*else if (item.getQuestionType()==2){
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
                        }*/



                     //   break;
                    }
                if (valid)
                    putSurvey(answers);
        }
    }

    private void putSurvey(ArrayList<com.traap.traapapp.apiServices.model.setAnswerQuestions.Question> answers)
    {
        showLoading();
       // PutSurveyRequest putSurveyRequest = new PutSurveyRequest();
        RequestSetAnswer putSurveyRequest = new RequestSetAnswer();
        putSurveyRequest.setQuestions(answers);

        SingletonService.getInstance().getSurveyDetailService().putAnswerQu(
                idSurvey,putSurveyRequest,new OnServiceStatus<WebServiceClass<PutSurveyResponse>>()
                {
                    @Override
                    public void onReady(WebServiceClass<PutSurveyResponse> response)
                    {
                        hideLoading();
                        try
                        {

                            if (response.info.statusCode == 201)
                            {

                                showAlertSuccess(getContext(),"پاسخ شما با موفقیت ثبت شد.","",true);

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
                             showAlertSuccess(getContext(),"پاسخ شما با موفقیت ثبت شد.","",true);

                            //  showAlert(getContext(), R.string.networkErrorMessage, R.string.networkError);
                        }
                    }
                });

                }

 }
