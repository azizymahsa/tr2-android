package com.traap.traapapp.ui.fragments.videos.archive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.archiveVideo.ArchiveVideoResponse;
import com.traap.traapapp.apiServices.model.mainVideos.Category;
import com.traap.traapapp.apiServices.model.news.main.News;
import com.traap.traapapp.enums.MediaArchiveCategoryCall;
import com.traap.traapapp.ui.activities.video.VideoDetailActivity;
import com.traap.traapapp.ui.adapters.video.VideosArchiveAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import java.util.ArrayList;
import java.util.List;

public class VideosArchiveCategoryFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<ArchiveVideoResponse>>
    , VideosArchiveAdapter.ArchiveVideoListener
{
    private View rootView;
    private Context context;

    private String Ids, filterStartDate, filterEndDate, filterSearchText;

    private ProgressBar progressBar;
    private VideosArchiveAdapter adapter;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private TextView tvEmpty;
    private MediaArchiveCategoryCall callFrom;


    private ArrayList<Category> videosContentList = new ArrayList<>();

    public VideosArchiveCategoryFragment()
    {
    }

    public static VideosArchiveCategoryFragment newInstance(String IDs,
                                                            MediaArchiveCategoryCall callFrom,
                                                            @Nullable String filterStartDate,
                                                            @Nullable String filterEndDate,
                                                            @Nullable String filterSearchText,
                                                            @Nullable List<News> videosContentList)
    {
        VideosArchiveCategoryFragment fragment = new VideosArchiveCategoryFragment();
        fragment.setCallFrom(callFrom);

        Bundle arg = new Bundle();
        arg.putString("Ids", IDs);
        arg.putString("filterStartDate", filterStartDate != null ? filterStartDate : "");
        arg.putString("filterEndDate", filterEndDate != null ? filterEndDate : "");
        arg.putString("filterSearchText", filterSearchText != null ? filterSearchText : "");
        arg.putBoolean("pagerWithFilter", false);

        if (callFrom == MediaArchiveCategoryCall.FROM_SINGLE_CONTENT)
        {
            arg.putParcelableArrayList("videosContentList", (ArrayList<? extends Parcelable>) videosContentList);
        }

        Logger.e("-Ids 0-", IDs);

        fragment.setArguments(arg);
        return fragment;
    }

    private void setCallFrom(MediaArchiveCategoryCall callFrom)
    {
        this.callFrom = callFrom;
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            Ids = getArguments().getString("Ids");

            filterStartDate = getArguments().getString("filterStartDate");
            filterEndDate = getArguments().getString("filterEndDate");
            filterSearchText = getArguments().getString("filterSearchText");

            videosContentList = getArguments().getParcelableArrayList("videosContentList");

//            Logger.e("-Ids 1-", Ids + " # " + pagerWithFilter);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;
        }
        rootView = inflater.inflate(R.layout.fragment_videos_archive_category, container, false);

        progressBar = rootView.findViewById(R.id.progressbar);
        tvEmpty = rootView.findViewById(R.id.tvEmpty);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        layoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(layoutManager);

        if (callFrom == MediaArchiveCategoryCall.FROM_ID)
        {
            Logger.e("-Ids 2-", Ids);
            SingletonService.getInstance().getArchiveVideoService().getArchiveVideo(Ids, this);
        }
        if (callFrom == MediaArchiveCategoryCall.FROM_FILTER_IDs)
        {
            Logger.e("-Ids 2-", Ids);
            SingletonService.getInstance().getArchiveVideoService().getVideosArchiveCategoryByIds(
                    Ids,
                    filterStartDate,
                    filterEndDate,
                    filterSearchText,
                    this
            );
        }
        else if (callFrom == MediaArchiveCategoryCall.FROM_FAVORITE)
        {
            SingletonService.getInstance().getArchiveVideoService().getBookMarkVideo(this);
        }
        else if (callFrom == MediaArchiveCategoryCall.FROM_SINGLE_CONTENT)
        {
            progressBar.setVisibility(View.GONE);
            Logger.e("--videosContentList size--", "Size: " + videosContentList.size());

            layoutManager = new GridLayoutManager(context, 1);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new VideosArchiveAdapter(videosContentList, false, this);
            recyclerView.setAdapter(adapter);

//            adapter.SetOnItemClickListener((id, newsArchiveContent, position) ->
//            {
//                List<MediaDetailsPositionIdsModel> positionIdsList = new ArrayList<>();
//                for (int i = 0; i < videosContentList.size(); i++)
//                {
//                    MediaDetailsPositionIdsModel model = new MediaDetailsPositionIdsModel();
//                    model.setId(videosContentList.get(i).getId());
//                    model.setPosition(i);
//
//                    positionIdsList.add(model);
//                }
//
//                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
//                intent.putExtra("currentId", id);
//                intent.putExtra("currentPosition", position);
//                intent.putParcelableArrayListExtra("positionIdsList", (ArrayList<? extends Parcelable>) positionIdsList);
//                startActivityForResult(intent,100);
//            });

            adapter.notifyDataSetChanged();

            if (videosContentList.isEmpty())
            {
                tvEmpty.setVisibility(View.VISIBLE);
            }
        }

        return rootView;
    }

    @Override
    public void onReady(WebServiceClass<ArchiveVideoResponse> response)
    {
        try{
            if (response.info.statusCode != 200)
            {
                showError(context, response.info.message);
            }
            else
            {
                videosContentList = response.data.getResults();

                Boolean FLAG_Favorite = false;
                if (callFrom == MediaArchiveCategoryCall.FROM_FAVORITE)
                {
                    FLAG_Favorite = true;
                }

                adapter = new VideosArchiveAdapter(videosContentList, FLAG_Favorite, this);
                recyclerView.setAdapter(adapter);

//            adapter.SetOnItemClickListener((id, newsArchiveContent, position) ->
//            {
//                List<MediaDetailsPositionIdsModel> positionIdsList = new ArrayList<>();
//                for (int i = 0; i < videosContentList.size(); i++)
//                {
//                    MediaDetailsPositionIdsModel model = new MediaDetailsPositionIdsModel();
//                    model.setId(videosContentList.get(i).getId());
//                    model.setPosition(i);
//
//                    positionIdsList.add(model);
//                }
//
//                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
//                intent.putExtra("currentId", id);
//                intent.putExtra("currentPosition", position);
//                intent.putParcelableArrayListExtra("positionIdsList", (ArrayList<? extends Parcelable>) positionIdsList);
//                startActivityForResult(intent,100);
//            });

                adapter.notifyDataSetChanged();

                if (videosContentList.isEmpty())
                {
                    tvEmpty.setVisibility(View.VISIBLE);
                }
            }

            progressBar.setVisibility(View.GONE);
        }catch (Exception e){}


    }

    @Override
    public void onError(String message)
    {
        progressBar.setVisibility(View.GONE);

        if (Tools.isNetworkAvailable((Activity) context))
        {
            Logger.e("-OnError-", "Error: " + message);
            showError(context, "خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            showAlert(context, R.string.networkErrorMessage, R.string.networkError);
        }
    }


    @Override
    public void onItemArchiveVideoClick(int position, Category category, ArrayList<Category> recent)
    {
        openVideoDetail(recent, position, category.getCategoryId(), category.getId());
    }

    private void openVideoDetail(ArrayList<Category> categoriesList, int position, Integer idVideo, Integer id)
    {
        Intent intent = new Intent(context, VideoDetailActivity.class);

        intent.putParcelableArrayListExtra("Videos", categoriesList);
        intent.putExtra("IdVideoCategory", idVideo);
        intent.putExtra("IdVideo", id);
        intent.putExtra("positionVideo", position);

        startActivityForResult(intent,100);
    }

}
