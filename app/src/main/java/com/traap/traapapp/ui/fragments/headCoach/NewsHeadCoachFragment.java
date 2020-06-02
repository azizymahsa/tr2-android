package com.traap.traapapp.ui.fragments.headCoach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.techHistory.GetTechsHistoryResponse;
import com.traap.traapapp.apiServices.model.techs.news.GetTechNewsResponse;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

/**
 * Created by MahtabAzizi on 6/2/2020.
 */
public class NewsHeadCoachFragment extends BaseFragment
{
    private Integer coachId;
    private View rootView;
    private RecyclerView rv;
    private NestedScrollView nested;
    public static Integer height;
    private TextView tvDesc;


    public void setCoachId(Integer coachId)
    {
        this.coachId = coachId;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;

        }
        rootView = inflater.inflate(R.layout.news_head_coach_fragment, container, false);
        getNews();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                height=rv.getHeight();
            }
        });
    }

    private void initView(){
        rv=rootView.findViewById(R.id.rv);
        nested=rootView.findViewById(R.id.nested);
        ViewCompat.setNestedScrollingEnabled(nested,false);
        tvDesc=rootView.findViewById(R.id.tvDesc);
    }
    public void getNews(){

        SingletonService.getInstance().tractorTeamService().getTechNews(coachId,new OnServiceStatus<WebServiceClass<GetTechNewsResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetTechNewsResponse> response)
            {
                try
                {
                    if (response.info.statusCode==200)
                    {

                     //   rv.setAdapter(new HistoryTechsAdapter(response.data.getResults()));

                        tvDesc.setText(response.data.getResults().get(1).getSubtitle());

                    }else{
                        showToast(getActivity(), response.info.message, R.color.red);

                    }
                }
                catch (Exception e){
                    showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");

                }

            }

            @Override
            public void onError(String message)
            {
                try{

                    if (Tools.isNetworkAvailable(getActivity()))
                    {
                        Logger.e("-OnError-", "Error: " + message);
                        showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                    }
                    else
                    {
                        showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                    }
                }catch (Exception e){}
            }
        });

    }
}