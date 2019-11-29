package ir.traap.tractor.android.ui.fragments.news.details.relatedNews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import com.example.moeidbannerlibrary.banner.BannerLayout;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.news.details.getContent.response.RelatedNews;
import ir.traap.tractor.android.apiServices.model.news.main.Categories;
import ir.traap.tractor.android.apiServices.model.news.main.NewsMainResponse;
import ir.traap.tractor.android.models.otherModels.newsModel.NewsDetailsFromRelatedNews;
import ir.traap.tractor.android.models.otherModels.newsModel.NewsDetailsPositionIdsModel;
import ir.traap.tractor.android.singleton.SingletonContext;
import ir.traap.tractor.android.ui.activities.login.LoginActivity;
import ir.traap.tractor.android.ui.activities.main.MainActivity;
import ir.traap.tractor.android.ui.adapters.news.NewsDetailsRelatedAdapter;
import ir.traap.tractor.android.ui.adapters.news.NewsMainFavoriteAdapter;
import ir.traap.tractor.android.ui.adapters.news.NewsMainNewestAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.news.archive.NewsArchiveCategoryFragment;
import ir.traap.tractor.android.utilities.Logger;
import ir.traap.tractor.android.utilities.MyCustomViewPager;


@SuppressLint("newsRelatedContentFragment")
public class NewsRelatedContentFragment extends BaseFragment
{
    private View rootView;
    private Context context;

    private List<RelatedNews> relatedNewList = new ArrayList<>();
    private List<NewsDetailsPositionIdsModel> positionIdsList;

    private NewsDetailsRelatedAdapter adapter;

    private BannerLayout bRelatedNews;

    public NewsRelatedContentFragment() { }

    public static NewsRelatedContentFragment newInstance(List<RelatedNews> relatedNews)
    {
        NewsRelatedContentFragment f = new NewsRelatedContentFragment();

        Bundle arg = new Bundle();
        arg.putParcelableArrayList("relatedNewList", (ArrayList<? extends Parcelable>) relatedNews);

        f.setArguments(arg);
        return f;
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
            NewsDetailsPositionIdsModel model = new NewsDetailsPositionIdsModel();
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
