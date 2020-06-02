package com.traap.traapapp.ui.fragments.headCoach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.techHistory.GetTechsHistoryResponse;
import com.traap.traapapp.ui.adapters.headTech.HistoryTechsAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

/**
 * Created by MahtabAzizi on 6/1/2020.
 */
public class HistoryHeadCoachFragment extends BaseFragment
{
    private MainActionView mainView;
    private Integer coachId;
    private View rootView;
    private RecyclerView rv;
    private NestedScrollView nested;
    public static Integer height;


    /*public HistoryHeadCoachFragment(MainActionView mainView, Integer coachId)
    {
        HeadCoachFragment f = new HeadCoachFragment();
        f.setMainView(mainView,title);
        f.setCoachId(coachId);
        return f;

        this.coachId = coachId;
        this.mainView=mainView;
    }
*/
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
        rootView = inflater.inflate(R.layout.history_head_coach_fragment, container, false);
        getCup();
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
    }
    public void getCup(){

        SingletonService.getInstance().tractorTeamService().getTechsHistory(coachId,new OnServiceStatus<WebServiceClass<GetTechsHistoryResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetTechsHistoryResponse> response)
            {
                try
                {
                    if (response.info.statusCode==200)
                    {

                        rv.setAdapter(new HistoryTechsAdapter(response.data.getResults()));


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


