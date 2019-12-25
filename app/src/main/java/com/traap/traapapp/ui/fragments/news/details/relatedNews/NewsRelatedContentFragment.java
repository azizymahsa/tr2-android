package com.traap.traapapp.ui.fragments.news.details.relatedNews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.moeidbannerlibrary.banner.BannerLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.news.details.getContent.response.RelatedNews;
import com.traap.traapapp.models.otherModels.mediaModel.MediaDetailsPositionIdsModel;
import com.traap.traapapp.models.otherModels.newsModel.NewsDetailsFromRelatedNews;
import com.traap.traapapp.ui.activities.news.NewsDetailsAction;
import com.traap.traapapp.ui.adapters.news.NewsDetailsRelatedAdapter;
import com.traap.traapapp.ui.base.BaseFragment;


@SuppressLint("newsRelatedContentFragment")
public class NewsRelatedContentFragment extends BaseFragment
{
    private View rootView;
    private Context context;

    private List<RelatedNews> relatedNewList = new ArrayList<>();
    private List<MediaDetailsPositionIdsModel> positionIdsList;

    private NewsDetailsRelatedAdapter adapter;

    private BannerLayout bRelatedNews;
    private NewsDetailsAction actionView;

    public NewsRelatedContentFragment() { }

    public static NewsRelatedContentFragment newInstance(NewsDetailsAction actionView, List<RelatedNews> relatedNews)
    {
        NewsRelatedContentFragment f = new NewsRelatedContentFragment();
        f.setActionView(actionView);

        Bundle arg = new Bundle();
        arg.putParcelableArrayList("relatedNewList", (ArrayList<? extends Parcelable>) relatedNews);

        f.setArguments(arg);
        return f;
    }

    private void setActionView(NewsDetailsAction actionView)
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
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            relatedNewList = getArguments().getParcelableArrayList("relatedNewList");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_news_related, container, false);

        adapter = new NewsDetailsRelatedAdapter(context, relatedNewList);
        bRelatedNews = rootView.findViewById(R.id.banRelatedNews);
        bRelatedNews.setAdapter(adapter);

        positionIdsList = new ArrayList<>();
//        for (RelatedNews news : relatedNewList)
        for (int i = 0 ; i < relatedNewList.size(); i++)
        {
            MediaDetailsPositionIdsModel model = new MediaDetailsPositionIdsModel();
            model.setId(relatedNewList.get(i).getId());
            model.setPosition(i);

            positionIdsList.add(model);
        }

        adapter.SetOnItemClickListener((view, id, position) ->
        {
            NewsDetailsFromRelatedNews relatedNews = new NewsDetailsFromRelatedNews();
            relatedNews.setCurrentId(id);
            relatedNews.setCurrentPosition(position);
            relatedNews.setPositionIdsList(positionIdsList);

            EventBus.getDefault().post(relatedNews);
        });

        return rootView;
    }

}
