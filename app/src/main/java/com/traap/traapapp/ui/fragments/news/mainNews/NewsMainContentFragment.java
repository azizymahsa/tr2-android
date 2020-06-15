package com.traap.traapapp.ui.fragments.news.mainNews;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
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
import java.util.concurrent.TimeUnit;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.news.main.Categories;
import com.traap.traapapp.apiServices.model.news.main.News;
import com.traap.traapapp.apiServices.model.news.main.NewsMainResponse;
import com.traap.traapapp.enums.MediaArchiveCategoryCall;
import com.traap.traapapp.enums.SubMediaParent;
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

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;


@SuppressLint("newsMainContentFragment")
public class NewsMainContentFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<NewsMainResponse>>
{
    private View rootView;
    private NewsActionView mainView;
    private boolean FLAG_IS_FIRST_LOAD = true;

    private MyCustomViewPager pager;
    private TabLayout tabLayout;

    private Context context;
    private NestedScrollView nestedScrollView;


    private NewsMainFavoriteAdapter favNewsAdapter;
    private NewsMainNewestAdapter newestNewsAdapter;

    private TextView tvNewsArchive, tvMyFavoriteNews;
    private SubMediaParent parent;

    private NewsMainResponse newsMainResponse;
    private ArrayList<Categories> categoriesList;
    private List<News> latestNewsList;
    private List<News> favoriteNewsList;

    private RelativeLayout rlArrowLeft, rlArrowRight;

    private LinearLayoutManager favLayoutManager;

    private RecyclerView favRecyclerView;
    private ScrollingPagerIndicator indicator,indicatorNewestNews;

    private BannerLayout bNewestNews;

    public NewsMainContentFragment()
    {
    }

    public static NewsMainContentFragment newInstance(SubMediaParent parent, NewsMainResponse newsMainResponse, NewsActionView mainView)
    {
        NewsMainContentFragment f = new NewsMainContentFragment();
        f.setMainView(mainView);
        f.setParent(parent);

        Bundle arg = new Bundle();
        arg.putParcelable("newsMainResponse", newsMainResponse);

        f.setArguments(arg);

        return f;
    }

    private void setParent(SubMediaParent parent)
    {
        this.parent = parent;
    }

    private void setMainView(NewsActionView mainView)
    {
        this.mainView = mainView;
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

            if (parent == SubMediaParent.MediaFragment)
            {
//                Logger.e("-categoriesList size-", "size: " + categoriesList.size());
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
        nestedScrollView = rootView.findViewById(R.id.nested);

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
        indicatorNewestNews = rootView.findViewById(R.id.indicatorNewestNews);

        bNewestNews = rootView.findViewById(R.id.bNewestNews);

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
        try{
            mainView.hideLoading();

            if (response == null || response.info == null)
            {
                startActivity(new Intent(context, LoginActivity.class));
                startActivityForResult(new Intent(getActivity(), LoginActivity.class),100);
                getActivity().finish();

                return;
            }
            if (response.info.statusCode != 200)
            {
                startActivity(new Intent(context, LoginActivity.class));
                startActivityForResult(new Intent(getActivity(), LoginActivity.class),100);
                getActivity().finish();

                return;
            }
            else
            {
                newsMainResponse = response.data;

                categoriesList = newsMainResponse.getCategories();
                latestNewsList = newsMainResponse.getLatestNews();
                favoriteNewsList = newsMainResponse.getFavoriteNews();

//                MainActivity.newsMainResponse = newsMainResponse;

                setContent();
            }
        }catch (Exception e){

        }

    }

    private void setContent()
    {
        newestNewsAdapter = new NewsMainNewestAdapter(context, latestNewsList);
        bNewestNews.setAdapter(newestNewsAdapter);
        indicatorNewestNews.attachToRecyclerView(bNewestNews.getmRecyclerView());
        bNewestNews.setAutoPlaying(true);
        newestNewsAdapter.SetOnItemClickListener((view, id, position) ->
        {
            List<MediaDetailsPositionIdsModel> positionIdsList = new ArrayList<>();
            for (int i = 0; i < latestNewsList.size(); i++)
            {
                MediaDetailsPositionIdsModel model = new MediaDetailsPositionIdsModel();
                model.setId(latestNewsList.get(i).getId());
                model.setPosition(i);

                positionIdsList.add(model);
            }

            Intent intent = new Intent(context, NewsDetailsActivity.class);
            intent.putExtra("currentId", id);
            intent.putExtra("currentPosition", position);
            intent.putParcelableArrayListExtra("positionIdsList", (ArrayList<? extends Parcelable>) positionIdsList);
            startActivityForResult(intent,100);
        });

        favNewsAdapter = new NewsMainFavoriteAdapter(context, favoriteNewsList);
        favLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        favRecyclerView.setLayoutManager(favLayoutManager);
        favRecyclerView.setAdapter(favNewsAdapter);
        indicator.attachToRecyclerView(favRecyclerView);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(favRecyclerView);

        favNewsAdapter.SetOnItemClickListener((view, id, position) ->
        {
            List<MediaDetailsPositionIdsModel> positionIdsList = new ArrayList<>();
            for (int i = 0; i < favoriteNewsList.size(); i++)
            {
                MediaDetailsPositionIdsModel model = new MediaDetailsPositionIdsModel();
                model.setId(favoriteNewsList.get(i).getId());
                model.setPosition(i);

                positionIdsList.add(model);
            }

            Intent intent = new Intent(context, NewsDetailsActivity.class);
            intent.putExtra("currentId", id);
            intent.putExtra("currentPosition", position);
            intent.putParcelableArrayListExtra("positionIdsList", (ArrayList<? extends Parcelable>) positionIdsList);
            startActivityForResult(intent,100);
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

        if (Tools.isNetworkAvailable((Activity) context))
        {
            Logger.e("-OnError-", "Error: " + message);
            showError(context, "خطا در دریافت اطلاعات از سرور!");
        } else
        {
            showAlert(context, R.string.networkErrorMessage, R.string.networkError);
        }
    }

    private void setPager()
    {
        Collections.reverse(categoriesList);
        int pagerHeight = (int) (getResources().getDimension(R.dimen.margin_news_main_archive_pager) +
                getResources().getDimension(R.dimen._80dp));
//
        LinearLayout.LayoutParams params;
        int height = 20;
        if (!categoriesList.isEmpty())
        {
            height = pagerHeight * categoriesList.size() / 2;
        }
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
//
//        showDebugToast((Activity) context, "height: " + height);
//
        pager = rootView.findViewById(R.id.view_pager);
        pager.setLayoutParams(params);
//        nestedScrollView.scrollTo(pager.getScrollX(), pager.getScrollY() - height);

        SamplePagerAdapter adapter = new SamplePagerAdapter(getFragmentManager(), categoriesList);

        pager.setAdapter(adapter);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
//        {
//            pager.setPageTransformer(true, (ViewPager.PageTransformer) new RotateUpTransformer());
//        }

        tabLayout = rootView.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(pager);

        for (int i = 0; i < tabLayout.getTabCount(); i++)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                Logger.e("--position--", "pos: " + tab.getPosition() + ", size: " +
                        categoriesList.get(tab.getPosition()).getNews().size() + ", title: " + tab.getText());

                setPagerHeight(categoriesList.get(tab.getPosition()).getNews().size());
//                Observable.just(categoriesList.get(tab.getPosition()).getNews().size())
//                        .repeat(2)
//                        .subscribe(new Consumer<Integer>()
//                        {
//                            @Override
//                            public void accept(Integer integer) throws Exception
//                            {
//                                Logger.e("-+ repeat +-", ":) ##########################");
//                                setPagerHeight(integer);
//                            }
//                        });

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {
                setPagerHeight(categoriesList.get(tab.getPosition()).getNews().size());
            }
        });

        pager.setCurrentItem(categoriesList.size() - 1);
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
            int Id = categoriesList.get(position).getId();
//            Logger.e("-getNews " + position + " size-", categoriesList.get(position).getTitle()
//                    + " size: " + categoriesList.get(position).getNews().size());

//            setPagerHeight(categoriesList.get(position).getNews().size());

            return NewsArchiveCategoryFragment.newInstance("",
                    MediaArchiveCategoryCall.FROM_SINGLE_CONTENT,
                    null,
                    null,
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


    @SuppressLint("CheckResult")
    private void setPagerHeight(int itemHeightSize)
    {

        LinearLayout.LayoutParams params;
        int height = 20;

        int pagerHeight = (int) (getResources().getDimension(R.dimen.margin_news_main_archive_pager) +
                getResources().getDimension(R.dimen._80dp));

        if (itemHeightSize != 0)
        {
            height = pagerHeight * itemHeightSize;
        }
        else
        {
            height = pagerHeight;
        }
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);

//        showDebugToast((Activity) context, "height: " + height);

        pager = rootView.findViewById(R.id.view_pager);
        pager.setLayoutParams(params);

        try
        {
            if (!FLAG_IS_FIRST_LOAD)
            {
                Observable.just(tabLayout.getTop())
//                        .repeat(2)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(scrollTo ->
                        {
                            nestedScrollView.post(() -> nestedScrollView.smoothScrollTo(0, scrollTo));
                        });
            }
            else
            {
                FLAG_IS_FIRST_LOAD = false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e("--Exception--", "message: " + e.getMessage());
        }

//        setPagerHeight(itemHeightSize);
    }

}
