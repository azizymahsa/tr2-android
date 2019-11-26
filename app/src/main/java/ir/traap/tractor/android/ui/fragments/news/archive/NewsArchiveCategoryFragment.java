package ir.traap.tractor.android.ui.fragments.news.archive;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.news.archive.response.NewsArchiveListById;
import ir.traap.tractor.android.apiServices.model.news.archive.response.NewsArchiveListByIdResponse;
import ir.traap.tractor.android.apiServices.model.news.category.response.NewsArchiveCategory;
import ir.traap.tractor.android.ui.adapters.news.NewsArchiveAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.utilities.Logger;
import ir.traap.tractor.android.utilities.Tools;

public class NewsArchiveCategoryFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<NewsArchiveListByIdResponse>>
{
    private View rootView;
    private NewsArchiveCategory archiveCategory;
    private int Id;
    private boolean pagerWithFilter = false;

    private ProgressBar progressBar;
    private NewsArchiveAdapter adapter;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;

    private ArrayList<NewsArchiveListById> newsArchiveContent = new ArrayList<>();

    public NewsArchiveCategoryFragment()
    {
    }

    public static NewsArchiveCategoryFragment newInstance(int Id)
    {
        NewsArchiveCategoryFragment fragment = new NewsArchiveCategoryFragment();

        Bundle arg = new Bundle();
        arg.putInt("Id", Id);
        arg.putBoolean("pagerWithFilter", false);

        Logger.e("-Id 0-", String.valueOf(Id));

        fragment.setArguments(arg);
        return fragment;
    }

    public static NewsArchiveCategoryFragment newInstance(boolean pagerWithFilter)
    {
        NewsArchiveCategoryFragment fragment = new NewsArchiveCategoryFragment();

        Bundle arg = new Bundle();
//        arg.putParcelable("NewsArchiveCategory", archiveCategory);

        arg.putBoolean("pagerWithFilter", pagerWithFilter);

        fragment.setArguments(arg);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            Id = getArguments().getInt("Id", 0);
            pagerWithFilter = getArguments().getBoolean("pagerWithFilter");

            Logger.e("-Id 1-", Id + " # " + pagerWithFilter);
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

        recyclerView = rootView.findViewById(R.id.recyclerView);

        layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);

//        adapter = new NewsArchiveAdapter(getActivity(), newsArchiveContent);
//        recyclerView.setAdapter(adapter);
//
//        adapter.SetOnItemClickListener((id, newsArchiveContent, position) ->
//        {
//            //Go To Details
//        });

        if (pagerWithFilter)
        {

        }
        else
        {
            Logger.e("-Id 2-", String.valueOf(Id));
            SingletonService.getInstance().getNewsService().getNewsArchiveCategoryById(String.valueOf(Id), this);
        }

        return rootView;
    }

    private void initContentView()
    {

    }

    private void initContentFilteredView()
    {

    }


    @Override
    public void onReady(WebServiceClass<NewsArchiveListByIdResponse> response)
    {
        if (response.info.statusCode != 200)
        {
            showError(getActivity(), response.info.message);
        }
        else
        {
            newsArchiveContent = response.data.getNewsArchiveListById();

            adapter = new NewsArchiveAdapter(getActivity(), newsArchiveContent);
            recyclerView.setAdapter(adapter);

            adapter.SetOnItemClickListener((id, newsArchiveContent, position) ->
            {
                //Go To Details
            });

            adapter.notifyDataSetChanged();

            if (newsArchiveContent.isEmpty())
            {

            }
        }

        progressBar.setVisibility(View.GONE);

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
