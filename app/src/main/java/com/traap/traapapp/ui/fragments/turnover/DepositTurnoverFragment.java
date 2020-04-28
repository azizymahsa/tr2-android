package com.traap.traapapp.ui.fragments.turnover;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getReport.request.GetReportRequest;
import com.traap.traapapp.apiServices.model.getReport.response.GetReportResponse;
import com.traap.traapapp.apiServices.model.getReport.response.ListTransaction;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Authors:
 * Reza Nejati <reza.n.j.t.i@gmail.com>
 * Copyright © 2017
 */
public class DepositTurnoverFragment extends BaseFragment
{
    private RecyclerView rcAllList;


    private View rootView;
    private MainActionView mainView;
    private ProgressBar pb;
    private TextView tvError;
    private List<ListTransaction> listTransaction = new ArrayList<>();

    @Override
    public void onStart()
    {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public static DepositTurnoverFragment newInstance(MainActionView mainView)
    {
        DepositTurnoverFragment f = new DepositTurnoverFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_all_turn_over, container, false);

        initView();
        getAllReport(null, null, null, null);

        return rootView;
    }


    private void initView()
    {
        rcAllList = rootView.findViewById(R.id.rcAllList);
        pb = rootView.findViewById(R.id.pb);
        tvError = rootView.findViewById(R.id.tvError);


        rcAllList.setLayoutManager(new LinearLayoutManager(getContext()));


    }


    public void getAllReport(String toDate, String fromDate, Integer toAmount, Integer fromAmount)
    {
        pb.setVisibility(View.VISIBLE);
        rcAllList.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
        GetReportRequest request = new GetReportRequest();
        request.setIsWallet(true);
        request.setPageNumber(1);
        request.setPageSize(10);
        request.setOperationType(2);
        if (toDate != null)
            request.setToDate(Utility.getGrgDate(toDate));
        if (fromDate != null)
            request.setFromDate(Utility.getGrgDate(fromDate));
        if (toAmount != null)
            request.setToAmount(toAmount);
        if (fromAmount != null)
        {

            if (fromAmount == 0)
                request.setFromAmount(1);
            else

                request.setFromAmount(fromAmount);

        }

        SingletonService.getInstance().doTransferCardService().getReport(new OnServiceStatus<WebServiceClass<GetReportResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetReportResponse> response)
            {
                pb.setVisibility(View.GONE);

                try
                {
                    if (response.info.statusCode == 200)
                    {
                        listTransaction.clear();
                        listTransaction.addAll(response.data.getListTransaction());
                        rcAllList.setAdapter(new TurnoverAdapter(getActivity(), response.data.getListTransaction()));
                    }
                    else
                    {
                        tvError.setVisibility(View.VISIBLE);
                        rcAllList.setVisibility(View.GONE);

                        tvError.setText(response.info.message);
                    }
                } catch (Exception e)
                {
                    mainView.showError(e.getMessage());

                }
            }

            @Override
            public void onError(String message)
            {
                pb.setVisibility(View.GONE);
                if (Tools.isNetworkAvailable(getActivity()))
                {
                    showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                } else
                {
                    showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                }

            }
        }, request);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventTurnoverModel event)
    {
        if (event.getRemove())
        {
            getAllReport(null, null, null, null);

            return;
        }
        getAllReport(event.getTo_date(), event.getFrom_date(), event.getTo_amount(), event.getFrom_amount());


    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ClickTurnOverEvent event)
    {
        if (event.getSearch().equals(""))
        {
            rcAllList.removeAllViews();
            tvError.setVisibility(View.GONE);
            rcAllList.setVisibility(View.VISIBLE);
            rcAllList.setAdapter(new TurnoverAdapter(getActivity(), listTransaction));
            Log.e("ssd2", listTransaction.size() + "");

            return;
        }
        filter(event.getSearch());
    }


    public void filter(String text)
    {
        Observable
                .fromIterable(listTransaction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .filter(x ->
                        {
                            return x.getDesc().contains(text);


                        }

                )
                .toList()
                .subscribe(new SingleObserver<List<ListTransaction>>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                    }

                    @Override
                    public void onSuccess(List<ListTransaction> results)
                    {
                        tvError.setVisibility(View.GONE);
                        rcAllList.setVisibility(View.VISIBLE);
                        rcAllList.removeAllViews();

                        rcAllList.setAdapter(new TurnoverAdapter(getActivity(), results));
                        if (results.size() == 0)
                        {
                            tvError.setVisibility(View.VISIBLE);
                            rcAllList.setVisibility(View.GONE);
                            Log.e("DepositTurnoverFragment", "onSuccess=> " + listTransaction.size() + "");

                        }


                    }

                    @Override
                    public void onError(Throwable e)
                    {
                    }
                });
    }


}
