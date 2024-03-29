package com.traap.traapapp.ui.fragments.points.groupBy;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.points.groupBy.PointGroupBy;
import com.traap.traapapp.apiServices.model.points.groupBy.PointsGroupByResponse;
import com.traap.traapapp.apiServices.model.points.guide.PointGuide;
import com.traap.traapapp.ui.activities.points.PointActionView;
import com.traap.traapapp.ui.adapters.points.PointGroupByParentAdapter;
import com.traap.traapapp.ui.adapters.points.PointGuidesAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


@SuppressLint("pointsRecordFragment")
public class PointsGroupByFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<PointsGroupByResponse>>
{
    private Context context;
    private PointActionView actionView;
    private View rootView;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private LinearLayout llEmpty;
    private CircularProgressButton btnInviteFriend;
    private PointGroupByParentAdapter adapter;
    private List<PointGroupBy> groupByList;

    public PointsGroupByFragment()
    {

    }

    public static PointsGroupByFragment newInstance(PointActionView actionView)
    {
        PointsGroupByFragment f = new PointsGroupByFragment();
        f.setActionView(actionView);
        return f;
    }

    private void setActionView(PointActionView actionView)
    {
        this.actionView = actionView;
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;
        }

        rootView = inflater.inflate(R.layout.fragment_point_group_by, container, false);

        initView();

        return rootView;
    }


    public void initView()
    {
        progressBar = rootView.findViewById(R.id.progressbar);

        llEmpty = rootView.findViewById(R.id.llEmpty);
        btnInviteFriend = rootView.findViewById(R.id.btnInviteFriend);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        layoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(layoutManager);

        btnInviteFriend.setOnClickListener(v -> actionView.onInviteFriend());

        SingletonService.getInstance().getPointsService().getPointGroupBy(this);

//        //-------test------
//        progressBar.setVisibility(View.GONE);
//        rootView.findViewById(R.id.tvEmpty).setVisibility(View.VISIBLE);
//        //-------test------
    }

    @Override
    public void onReady(WebServiceClass<PointsGroupByResponse> response)
    {
        progressBar.setVisibility(View.GONE);
        try
        {
            if (response.info.statusCode != 200)
            {
                showAlertFailure(context, response.info.message, "", true);
            }
            else
            {
                groupByList = response.data.getGroupByList();
                if (!groupByList.isEmpty())
                {
                    adapter = new PointGroupByParentAdapter(context, groupByList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    //show Empty
                    llEmpty.setVisibility(View.VISIBLE);
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            onError(e.getMessage());
        }
    }

    @Override
    public void onError(String message)
    {
        progressBar.setVisibility(View.GONE);

        if (Tools.isNetworkAvailable((Activity) context))
        {
            Logger.e("-PointsRecord onError-", "message: " + message);
            showAlertFailure(context, "خطای دسترسی به سرور!", "", true);
        }
        else
        {
            showAlertFailure(context, getString(R.string.networkErrorMessage), getString(R.string.networkError), true);
        }
    }
}
