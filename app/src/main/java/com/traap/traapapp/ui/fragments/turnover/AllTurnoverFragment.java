package com.traap.traapapp.ui.fragments.turnover;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.getReport.request.GetReportRequest;
import com.traap.traapapp.apiServices.model.getReport.response.GetReportResponse;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class AllTurnoverFragment extends BaseFragment {
    private RecyclerView rcAllList;


    private View rootView;
    private MainActionView mainView;
    private ProgressBar pb;
    private TextView tvError;

    public static AllTurnoverFragment newInstance(MainActionView mainView) {
        AllTurnoverFragment f = new AllTurnoverFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_all_turn_over, container, false);

        initView();
        getAllReport(null, null, null, null);

        return rootView;
    }


    private void initView() {
        rcAllList = rootView.findViewById(R.id.rcAllList);
        pb = rootView.findViewById(R.id.pb);
        tvError = rootView.findViewById(R.id.tvError);


        rcAllList.setLayoutManager(new LinearLayoutManager(getContext()));


    }


    public void getAllReport(String toDate, String fromDate, Integer toAmount, Integer fromAmount) {
        pb.setVisibility(View.VISIBLE);
        rcAllList.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);

        GetReportRequest request = new GetReportRequest();
        request.setIsWallet(true);
        request.setPageNumber(1);
        request.setPageSize(10);
        if (toDate != null)
            request.setToDate(Utility.getGrgDate(toDate));
        if (fromDate != null)
            request.setFromDate(Utility.getGrgDate(fromDate));
        if (toAmount != null)
            request.setToAmount(toAmount);
        if (fromAmount != null){

            if (fromAmount==0)
            request.setFromAmount(1);
            else

                request.setFromAmount(fromAmount);

        }


        SingletonService.getInstance().doTransferCardService().getReport(new OnServiceStatus<GetReportResponse>() {
            @Override
            public void onReady(GetReportResponse response) {
                pb.setVisibility(View.GONE);

                try {
                    if (response.getInfo().getCode() == 200) {
                        rcAllList.setAdapter(new TurnoverAdapter(getActivity(), response.getData().getListTransaction()));
                    } else {
                        tvError.setVisibility(View.VISIBLE);
                        rcAllList.setVisibility(View.GONE);

                        tvError.setText(response.getInfo().getMessage());

                    }
                } catch (Exception e) {
                    mainView.showError(e.getMessage());

                }
            }

            @Override
            public void onError(String message) {
                pb.setVisibility(View.GONE);
                if (Tools.isNetworkAvailable(getActivity())) {
                    showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                } else {
                    showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                }

            }
        }, request);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventTurnoverModel event) {
        if (event.getRemove()){
            getAllReport(null, null, null, null);

            return;
        }
        getAllReport(event.getTo_date(), event.getFrom_date(), event.getTo_amount(), event.getFrom_amount() );


    }
}
