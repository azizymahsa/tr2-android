package com.traap.traapapp.ui.fragments.news.archive;

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

import java.util.ArrayList;
import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.news.archive.NewsArchiveListByIdResponse;
import com.traap.traapapp.apiServices.model.news.main.News;
import com.traap.traapapp.enums.MediaArchiveCategoryCall;
import com.traap.traapapp.models.otherModels.mediaModel.MediaDetailsPositionIdsModel;
import com.traap.traapapp.ui.activities.news.details.NewsDetailsActivity;
import com.traap.traapapp.ui.adapters.news.NewsArchiveAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

public class NewsArchiveCategoryFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<NewsArchiveListByIdResponse>>
{
    private View rootView;

    private String Ids, filterStartDate, filterEndDate, filterSearchText;

    private ProgressBar progressBar;
    private NewsArchiveAdapter adapter;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private TextView tvEmpty;
    private MediaArchiveCategoryCall callFrom;


    private ArrayList<News> newsContentList = new ArrayList<>();

    public NewsArchiveCategoryFragment()
    {
    }

    public static NewsArchiveCategoryFragment newInstance(String IDs,
                                                          MediaArchiveCategoryCall callFrom,
                                                          @Nullable String filterStartDate,
                                                          @Nullable String filterEndDate,
                                                          @Nullable String filterSearchText,
                                                          @Nullable List<News> newsContentList)
    {
        NewsArchiveCategoryFragment fragment = new NewsArchiveCategoryFragment();
        fragment.setCallFrom(callFrom);

        Bundle arg = new Bundle();
        arg.putString("Ids", IDs);
        arg.putString("filterStartDate", filterStartDate != null ? filterStartDate : "");
        arg.putString("filterEndDate", filterEndDate != null ? filterEndDate : "");
        arg.putString("filterSearchText", filterSearchText != null ? filterSearchText : "");
        arg.putBoolean("pagerWithFilter", false);

        if (callFrom == MediaArchiveCategoryCall.FROM_SINGLE_CONTENT)
        {
            arg.putParcelableArrayList("newsContentList", (ArrayList<? extends Parcelable>) newsContentList);
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
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            Ids = getArguments().getString("Ids");

            filterStartDate = getArguments().getString("filterStartDate");
            filterEndDate = getArguments().getString("filterEndDate");
            filterSearchText = getArguments().getString("filterSearchText");

            newsContentList = getArguments().getParcelableArrayList("newsContentList");

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
        rootView = inflater.inflate(R.layout.fragment_news_archive_category, container, false);

        progressBar = rootView.findViewById(R.id.progressbar);
        tvEmpty = rootView.findViewById(R.id.tvEmpty);

        recyclerView = rootView.findViewById(R.id.recyclerView);

        layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);

        if (callFrom == MediaArchiveCategoryCall.FROM_ID)
        {
            Logger.e("-Ids 2-", Ids);
            SingletonService.getInstance().getNewsService().getNewsArchiveCategoryBySingleId(Ids, this);
        }
        if (callFrom == MediaArchiveCategoryCall.FROM_FILTER_IDs)
        {
            Logger.e("-Ids 2-", Ids);
            SingletonService.getInstance().getNewsService().getNewsArchiveCategoryByIds(
                    Ids,
                    filterStartDate,
                    filterEndDate,
                    filterSearchText,
                    this
            );
        }
        else if (callFrom == MediaArchiveCategoryCall.FROM_FAVORITE)
        {
            SingletonService.getInstance().getNewsService().getNewsBookmarks(this);
        }
        else if (callFrom == MediaArchiveCategoryCall.FROM_SINGLE_CONTENT)
        {
            progressBar.setVisibility(View.GONE);
            Logger.e("--newsContentList size--", "Size: " + newsContentList.size());

            layoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new NewsArchiveAdapter(getActivity(), newsContentList);
            recyclerView.setAdapter(adapter);

            adapter.SetOnItemClickListener((id, newsArchiveContent, position) ->
            {
                List<MediaDetailsPositionIdsModel> positionIdsList = new ArrayList<>();
                for (int i = 0 ; i < newsContentList.size(); i++)
                {
                    MediaDetailsPositionIdsModel model = new MediaDetailsPositionIdsModel();
                    model.setId(newsContentList.get(i).getId());
                    model.setPosition(i);

                    positionIdsList.add(model);
                }

                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                intent.putExtra("currentId", id);
                intent.putExtra("currentPosition", position);
                intent.putParcelableArrayListExtra("positionIdsList", (ArrayList<? extends Parcelable>) positionIdsList);

                getActivity().startActivityForResult(intent,100);
            });

            adapter.notifyDataSetChanged();

            if (newsContentList.isEmpty())
            {
                tvEmpty.setVisibility(View.VISIBLE);
            }
        }


        return rootView;
    }

    @Override
    public void onReady(WebServiceClass<NewsArchiveListByIdResponse> response)
    {
        try{
            if (response.info.statusCode != 200)
            {
                showError(getActivity(), response.info.message);
            }
            else
            {
                newsContentList = response.data.getNewsArchiveListById();

                adapter = new NewsArchiveAdapter(getActivity(), newsContentList);
                recyclerView.setAdapter(adapter);

                adapter.SetOnItemClickListener((id, newsArchiveContent, position) ->
                {
                    List<MediaDetailsPositionIdsModel> positionIdsList = new ArrayList<>();
                    for (int i = 0 ; i < newsContentList.size(); i++)
                    {
                        MediaDetailsPositionIdsModel model = new MediaDetailsPositionIdsModel();
                        model.setId(newsContentList.get(i).getId());
                        model.setPosition(i);

                        positionIdsList.add(model);
                    }

                    Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                    intent.putExtra("currentId", id);
                    intent.putExtra("currentPosition", position);
                    intent.putParcelableArrayListExtra("positionIdsList", (ArrayList<? extends Parcelable>) positionIdsList);
                    getActivity().startActivityForResult(intent,100);
                });

                adapter.notifyDataSetChanged();

                if (newsContentList.isEmpty())
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

        if (Tools.isNetworkAvailable(getActivity()))
        {
            Logger.e("-OnError-", "Error: " + message);
            showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
        }
    }


}
