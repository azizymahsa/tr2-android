package ir.traap.tractor.android.ui.fragments.news.details.commentNews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moeidbannerlibrary.banner.BannerLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.news.details.getComment.response.GetNewsCommentResponse;
import ir.traap.tractor.android.apiServices.model.news.details.getContent.response.RelatedNews;
import ir.traap.tractor.android.apiServices.model.news.details.sendComment.request.SendCommentNewsRequest;
import ir.traap.tractor.android.models.otherModels.newsModel.NewsDetailsFromRelatedNews;
import ir.traap.tractor.android.models.otherModels.newsModel.NewsDetailsPositionIdsModel;
import ir.traap.tractor.android.singleton.SingletonContext;
import ir.traap.tractor.android.ui.activities.news.NewsDetailsAction;
import ir.traap.tractor.android.ui.adapters.news.NewsCommentListAdapter;
import ir.traap.tractor.android.ui.adapters.news.NewsDetailsRelatedAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.predict.PredictFragment;
import ir.traap.tractor.android.utilities.ClearableEditText;
import ir.traap.tractor.android.utilities.Logger;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

@SuppressLint("newsCommentFragment")
public class NewsDetailsCommentFragment extends BaseFragment implements OnAnimationEndListener,
        OnServiceStatus<WebServiceClass<ArrayList<GetNewsCommentResponse>>>, NewsCommentListAdapter.OnItemClickListener
{
    private View rootView;
    private Context context;
    private int Id;

    private ProgressBar progress;
    private RecyclerView rcCommentList;
    private GridLayoutManager layoutManager;
    private NewsCommentListAdapter adapter;


    private ClearableEditText edtComment;

    private CircularProgressButton btnSendComment;

//    private List<RelatedNews> relatedNewList = new ArrayList<>();
//    private List<NewsDetailsPositionIdsModel> positionIdsList;

    private NewsDetailsAction actionView;

    public NewsDetailsCommentFragment() { }

    public static NewsDetailsCommentFragment newInstance(NewsDetailsAction actionView, int id)
    {
        NewsDetailsCommentFragment f = new NewsDetailsCommentFragment();
        f.setActionView(actionView);

        Bundle arg = new Bundle();
        arg.putInt("id", id);

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
            Id = getArguments().getInt("id", 0);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_news_comment, container, false);

        edtComment = rootView.findViewById(R.id.edtComment);
        btnSendComment = rootView.findViewById(R.id.btnSendComment);
        progress = rootView.findViewById(R.id.progress);
        rcCommentList = rootView.findViewById(R.id.rcCommentList);

        layoutManager = new GridLayoutManager(getActivity(), 1);
        rcCommentList.setLayoutManager(layoutManager);

        SingletonService.getInstance().getNewsService().getNewsComment(Id, this);

        btnSendComment.setOnClickListener(v ->
        {
            if (edtComment.getText().toString().equalsIgnoreCase(""))
            {
                //do noThing
            }
            else if (edtComment.getText().toString().trim().length() > 400)
            {
                showAlert(context, "حداکثر تعداد کاراکترهای مجاز 400 کاراکتر است!", 0);
            }
            else
            {
                btnSendComment.startAnimation();
                btnSendComment.setClickable(false);

                SendCommentNewsRequest request = new SendCommentNewsRequest();
                request.setBody(edtComment.getText().toString());

                SingletonService.getInstance().getNewsService().sendNewsComment(Id, request, new OnServiceStatus<WebServiceClass<Object>>()
                {
                    @Override
                    public void onReady(WebServiceClass<Object> response)
                    {
                        btnSendComment.revertAnimation(NewsDetailsCommentFragment.this);
                        btnSendComment.setClickable(true);
                        if (response.info.statusCode == 200)
                        {
                            showAlert(context, response.info.message, 0);
                            edtComment.setText("");
                        }
                        else
                        {
                            showAlert(context, response.info.message, 0);
                        }
                    }

                    @Override
                    public void onError(String message)
                    {
                        btnSendComment.revertAnimation(NewsDetailsCommentFragment.this);
                        btnSendComment.setClickable(true);

                        showAlert(context, "خطای دسترسی به سرور!", 0);
                    }
                });
            }
        });

        return rootView;
    }

    @Override
    public void onAnimationEnd()
    {
        btnSendComment.setBackground(ContextCompat.getDrawable(SingletonContext.getInstance().getContext(), R.drawable.background_button_login));
    }

    @Override
    public void onReady(WebServiceClass<ArrayList<GetNewsCommentResponse>> response)
    {
        progress.setVisibility(View.GONE);

        if (response.info.statusCode != 200)
        {
            rootView.findViewById(R.id.llCommentList).setVisibility(View.GONE);
        }
        else
        {
            if (response.data.isEmpty())
            {
                rootView.findViewById(R.id.llCommentList).setVisibility(View.GONE);
            }
            else
            {
                rootView.findViewById(R.id.llCommentList).setVisibility(View.VISIBLE);

                //--------------
                adapter = new NewsCommentListAdapter(context, response.data, this);
                rcCommentList.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onError(String message)
    {
        progress.setVisibility(View.GONE);
        rootView.findViewById(R.id.llCommentList).setVisibility(View.GONE);

        Logger.e("-onError CommentList-", "Error: " + message);
    }

    @Override
    public void onLikeItemClick(View view, Integer id, Integer position)
    {

    }

    @Override
    public void onDislikeItemClick(View view, Integer id, Integer position)
    {

    }

    @Override
    public void onReplayItemClick(View view, Integer id, Integer position)
    {

    }
}
