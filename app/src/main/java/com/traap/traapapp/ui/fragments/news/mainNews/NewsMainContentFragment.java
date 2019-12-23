package com.traap.traapapp.ui.fragments.news.mainNews;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.moeidbannerlibrary.banner.BannerLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.news.main.Categories;
import com.traap.traapapp.apiServices.model.news.main.News;
import com.traap.traapapp.apiServices.model.news.main.NewsMainResponse;
import com.traap.traapapp.enums.MediaArchiveCategoryCall;
import com.traap.traapapp.enums.NewsParent;
import com.traap.traapapp.models.otherModels.mediaModel.MediaDetailsPositionIdsModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.login.LoginActivity;
import com.traap.traapapp.ui.activities.main.MainActivity;
import com.traap.traapapp.ui.activities.news.details.NewsDetailsActivity;
import com.traap.traapapp.ui.adapters.news.NewsMainFavoriteAdapter;
import com.traap.traapapp.ui.adapters.news.NewsMainNewestAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.news.NewsActionView;
import com.traap.traapapp.ui.fragments.news.archive.NewsArchiveCategoryFragment;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.MyCustomViewPager;
import com.traap.traapapp.utilities.Tools;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;


@SuppressLint("newsMainContentFragment")
public class NewsMainContentFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<NewsMainResponse>>
{
    private View rootView;
    private NewsActionView mainView;

    private ScrollingPagerIndicator indicatorNewestNews;

    private NewsMainFavoriteAdapter favNewsAdapter;
    private NewsMainNewestAdapter newestNewsAdapter;

    private TextView tvNewsArchive, tvMyFavoriteNews;
    private NewsParent parent;

    private NewsMainResponse newsMainResponse;
    private ArrayList<Categories> categoriesList;
    private List<News> latestNewsList;
    private List<News> favoriteNewsList;

    private RelativeLayout rlArrowLeft, rlArrowRight;

    private LinearLayoutManager favLayoutManager;;
    private RecyclerView favRecyclerView;
    private ScrollingPagerIndicator indicator;

    private BannerLayout bNewestNews;

    public NewsMainContentFragment() { }

    public static NewsMainContentFragment newInstance(NewsParent parent, NewsMainResponse newsMainResponse, NewsActionView mainView)
    {
        NewsMainContentFragment f = new NewsMainContentFragment();
        f.setMainView(mainView);
        f.setParent(parent);

        Bundle arg = new Bundle();
        arg.putParcelable("newsMainResponse", newsMainResponse);

        f.setArguments(arg);

        return f;
    }

    private void setParent(NewsParent parent)
    {
        this.parent = parent;
    }

    private void setMainView(NewsActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            newsMainResponse = getArguments().getParcelable("newsMainResponse");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if (newsMainResponse != null)
        {
            categoriesList = newsMainResponse.getCategories();
            latestNewsList = newsMainResponse.getLatestNews();
            favoriteNewsList = newsMainResponse.getFavoriteNews();

            if (parent == NewsParent.MediaFragment)
            {
                Logger.e("-categoriesList size-", "size: " +  categoriesList.size());
                Collections.reverse(categoriesList);
            }

            setContent();
        }
        else
        {
            mainView.showLoading();
            SingletonService.getInstance().getNewsService().getNewsMain(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_news_main_content, container, false);

        initView(rootView);

        return rootView;
    }

    private void initView(View rootView)
    {
        rlArrowLeft = rootView.findViewById(R.id.rlArrowLeft);
        rlArrowRight = rootView.findViewById(R.id.rlArrowRight);

        rlArrowLeft.setOnClickListener(v ->
        {
            onSlideLeft();
        });

        rlArrowRight.setOnClickListener(v ->
        {
            onSlideRight();
        });

        favRecyclerView = rootView.findViewById(R.id.favRecyclerView);
        indicator = rootView.findViewById(R.id.indicator);

//        indicatorNewestNews = rootView.findViewById(R.id.indicatorNewestNews);
        bNewestNews = rootView.findViewById(R.id.bNewestNews);
//        bNewestNews.setAdapter();


        tvNewsArchive = rootView.findViewById(R.id.tvNewsArchive);
        tvNewsArchive.setOnClickListener(v ->
        {
            mainView.onNewsArchiveFragment(parent);
        });

        tvMyFavoriteNews = rootView.findViewById(R.id.tvMyFavoriteNews);
        tvMyFavoriteNews.setOnClickListener(v ->
        {
            mainView.onNewsFavoriteFragment(parent);
        });


    }


    private void onSlideRight()
    {
        if (favLayoutManager.findFirstCompletelyVisibleItemPosition() == Objects.requireNonNull(favRecyclerView.getAdapter()).getItemCount())
        {
            return;
        }
        int newPos = favLayoutManager.findFirstCompletelyVisibleItemPosition() + 1;

        favRecyclerView.smoothScrollToPosition(newPos);

//        if (newPos == Objects.requireNonNull(favRecyclerView.getAdapter()).getItemCount())
//        {
//            rlArrowRight.setVisibility(View.INVISIBLE);
//        }
//        else
//        {
//            rlArrowRight.setVisibility(View.VISIBLE);
//        }
    }

    private void onSlideLeft()
    {
        if (favLayoutManager.findFirstCompletelyVisibleItemPosition() == 0)
            return;

        int newPos = favLayoutManager.findFirstCompletelyVisibleItemPosition() - 1;

        favRecyclerView.smoothScrollToPosition(newPos);

//        if (newPos == 0)
//        {
//            rlArrowLeft.setVisibility(View.INVISIBLE);
//        }
//        else
//        {
//            rlArrowLeft.setVisibility(View.VISIBLE);
//        }
    }



    @Override
    public void onReady(WebServiceClass<NewsMainResponse> response)
    {
        mainView.hideLoading();

        if (response == null || response.info == null)
        {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();

            return;
        }
        if (response.info.statusCode != 200)
        {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();

            return;
        }
        else
        {
            newsMainResponse = response.data;

            categoriesList = newsMainResponse.getCategories();
            latestNewsList = newsMainResponse.getLatestNews();
            favoriteNewsList = newsMainResponse.getFavoriteNews();

            MainActivity.newsMainResponse = newsMainResponse;

//            setSlider();
            setContent();
        }
    }

    private void setContent()
    {
        newestNewsAdapter = new NewsMainNewestAdapter(getActivity(), latestNewsList);
//        newestLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        newestRecyclerView.setLayoutManager(newestLayoutManager);
        bNewestNews.setAdapter(newestNewsAdapter);
//        for (int i = 0; i < bNewestNews.getChildCount(); i++)
//        {
//            if (bNewestNews.getChildAt(i) instanceof RecyclerView)
//            {
//                RecyclerView recyclerView = (RecyclerView) bNewestNews.getChildAt(i);
//                indicatorNewestNews.attachToRecyclerView(recyclerView);
//            }
//        }

        newestNewsAdapter.SetOnItemClickListener((view, id, position) ->
        {
            List<MediaDetailsPositionIdsModel> positionIdsList = new ArrayList<>();
            for (int i = 0 ; i < latestNewsList.size(); i++)
            {
                MediaDetailsPositionIdsModel model = new MediaDetailsPositionIdsModel();
                model.setId(latestNewsList.get(i).getId());
                model.setPosition(i);

                positionIdsList.add(model);
            }

            Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
            intent.putExtra("currentId", id);
            intent.putExtra("currentPosition", position);
            intent.putParcelableArrayListExtra("positionIdsList", (ArrayList<? extends Parcelable>) positionIdsList);
            startActivity(intent);
        });

        favNewsAdapter = new NewsMainFavoriteAdapter(getActivity(), favoriteNewsList);
        favLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        favRecyclerView.setLayoutManager(favLayoutManager);
        favRecyclerView.setAdapter(favNewsAdapter);
        indicator.attachToRecyclerView(favRecyclerView);

//        SnapHelper snapHelper = new StartSnapHelper();
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(favRecyclerView);

        favNewsAdapter.SetOnItemClickListener((view, id, position) ->
        {
            List<MediaDetailsPositionIdsModel> positionIdsList = new ArrayList<>();
            for (int i = 0 ; i < favoriteNewsList.size(); i++)
            {
                MediaDetailsPositionIdsModel model = new MediaDetailsPositionIdsModel();
                model.setId(favoriteNewsList.get(i).getId());
                model.setPosition(i);

                positionIdsList.add(model);
            }

            Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
            intent.putExtra("currentId", id);
            intent.putExtra("currentPosition", position);
            intent.putParcelableArrayListExtra("positionIdsList", (ArrayList<? extends Parcelable>) positionIdsList);
            startActivity(intent);
        });

//        favRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
//        {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState)
//            {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE)
//                {
//                    if (favLayoutManager.findFirstVisibleItemPosition() == 0)
//                    {
//                        rlArrowLeft.setVisibility(View.INVISIBLE);
//                    }
//                    else
//                    {
//                        rlArrowLeft.setVisibility(View.VISIBLE);
//                    }
//
//                    if (favLayoutManager.findFirstVisibleItemPosition() == favLayoutManager.getItemCount() )
//                    {
//                        rlArrowRight.setVisibility(View.INVISIBLE);
//                    }
//                    else
//                    {
//                        rlArrowRight.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//        });

        setPager();
    }

    @Override
    public void onError(String message)
    {
        mainView.hideLoading();

        if (Tools.isNetworkAvailable(getActivity()))
        {
            Logger.e("-OnError-", "Error: " + message);
            showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
        } else
        {
            showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
        }
    }

    private void setPager()
    {
        Collections.reverse(categoriesList);

        MyCustomViewPager pager = rootView.findViewById(R.id.view_pager);
        SamplePagerAdapter adapter = new SamplePagerAdapter(getFragmentManager(), categoriesList);

        pager.setAdapter(adapter);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {
//            pager.setPageTransformer(true, (ViewPager.PageTransformer) new RotateUpTransformer());
//        }

        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
//        tabLayout.setSelectedTabIndicator(getResources().getDrawable());
        tabLayout.setupWithViewPager(pager);

        for (int i = 0; i < tabLayout.getTabCount(); i++)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        pager.setCurrentItem(categoriesList.size()-1);
    }

    private class SamplePagerAdapter extends FragmentStatePagerAdapter
    {
        private ArrayList<Categories> categoriesList;
        private Context context = SingletonContext.getInstance().getContext();

        @SuppressLint("WrongConstant")
        public SamplePagerAdapter(@NonNull FragmentManager fm, ArrayList<Categories> categoriesList)
        {
            super(fm, 0);
            this.categoriesList = categoriesList;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position)
        {
            return categoriesList.get(position).getTitle();
        }

        @NonNull
        @Override
        public Fragment getItem(int position)
        {
//            Collections.reverse(categoriesList);
            int Id =  categoriesList.get(position).getId();
            Logger.e("-getNews " + position +" size-", categoriesList.get(position).getTitle() + " size: " +  categoriesList.get(position).getNews().size());

            return NewsArchiveCategoryFragment.newInstance("",
                    MediaArchiveCategoryCall.FROM_SINGLE_CONTENT,
                    null,
                    categoriesList.get(position).getNews()
            );
        }

        @Override
        public int getCount()
        {
            return categoriesList.size();
        }

        public View getTabView(int position)
        {
            // Given you have a custom layout in `res/layout/tab_category_content.xml` with a TextView
            View v = LayoutInflater.from(context).inflate(R.layout.tab_category_content, null, false);

            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_normal.ttf");

            TextView tv = v.findViewById(R.id.textView);
            tv.setText(getPageTitle(position));
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextColor(context.getResources().getColorStateList(R.color.textColorSecondary));
            tv.setTypeface(font);
            return v;
        }

    }

}
