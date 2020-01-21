package com.traap.traapapp.ui.fragments.points.guide;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.points.guide.PointGuide;
import com.traap.traapapp.apiServices.model.points.guide.PointsGuideResponse;
import com.traap.traapapp.ui.adapters.points.PointGuidesAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import java.util.List;


@SuppressLint("pointsRecordFragment")
public class PointsGuideFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<PointsGuideResponse>>
{
    private LinearLayout llHeader;
    private Context context;
    private View rootView;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
//    private TextView tvEmpty;
    private PointGuidesAdapter adapter;
    private List<PointGuide> guideList;

    public PointsGuideFragment()
    {

    }

    public static PointsGuideFragment newInstance()
    {
        PointsGuideFragment f = new PointsGuideFragment();
        return f;
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
        rootView = inflater.inflate(R.layout.fragment_point_guide, container, false);

        initView();

        return rootView;
    }


    public void initView()
    {
        llHeader = rootView.findViewById(R.id.llHeader);
        progressBar = rootView.findViewById(R.id.progressbar);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);

        //SingletonService.getInstance().getNewsService().getNewsArchiveCategoryBySingleId(Ids, this);
        SingletonService.getInstance().getPointsService().getPointGuide(this);
    }

    @Override
    public void onReady(WebServiceClass<PointsGuideResponse> response)
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
                guideList = response.data.getGuideList();
//                Logger.e("-guideList size-", "size: " + guideList.size());
                if (!guideList.isEmpty())
                {
                    llHeader.setVisibility(View.VISIBLE);
                    adapter = new PointGuidesAdapter(context, guideList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    llHeader.setVisibility(View.GONE);
                    //show Empty
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
