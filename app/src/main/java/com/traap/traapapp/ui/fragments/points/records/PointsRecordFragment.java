package com.traap.traapapp.ui.fragments.points.records;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.points.records.PointRecord;
import com.traap.traapapp.apiServices.model.points.records.PointsRecordResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.points.PointScoreModel;
import com.traap.traapapp.ui.adapters.points.PointRecordsAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SuppressLint("pointsRecordFragment")
public class PointsRecordFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<PointsRecordResponse>>
{
    private Context context;
    private View rootView;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
//    private TextView tvEmpty;
    private PointRecordsAdapter adapter;
    private List<PointRecord> recordList;
    private Integer maxHeightView;
    private List<Integer> heightList = new ArrayList<>();

    public PointsRecordFragment()
    {

    }

    public static PointsRecordFragment newInstance()
    {
        PointsRecordFragment f = new PointsRecordFragment();
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
        rootView = inflater.inflate(R.layout.fragment_point_record, container, false);

        initView();

        return rootView;
    }


    public void initView()
    {
        progressBar = rootView.findViewById(R.id.progressbar);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);

        //SingletonService.getInstance().getNewsService().getNewsArchiveCategoryBySingleId(Ids, this);
        SingletonService.getInstance().getPointsService().getPointRecord(this);
    }

    @Override
    public void onReady(WebServiceClass<PointsRecordResponse> response)
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
                maxHeightView = 0;

                recordList = response.data.getRecordList();
//                Logger.e("-recordList size-", "size: " + recordList.size());
                if (!recordList.isEmpty())
                {
                    adapter = new PointRecordsAdapter(context, recordList, heightList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    //show Empty
                }
//                maxHeightView = adapter.getMaxHeightViewItem() * (adapter.getItemCount() > 12 ? 12: adapter.getItemCount());
////                maxHeightView = Collections.max(heightList) * adapter.getItemCount();
                EventBus.getDefault().post(new PointScoreModel(response.data.getBalance(), maxHeightView));
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
