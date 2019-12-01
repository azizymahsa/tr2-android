package com.traap.traapapp.ui.fragments.news.details.contentNews;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.Objects;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.news.details.getContent.response.GetNewsDetailsResponse;
import com.traap.traapapp.apiServices.model.news.details.sendLike.request.LikeNewsDetailRequest;
import com.traap.traapapp.apiServices.model.news.details.sendLike.response.LikeNewsDetailResponse;
import com.traap.traapapp.models.otherModels.newsModel.NewsArchiveClickModel;
import com.traap.traapapp.singleton.SingletonNewsArchiveClick;
import com.traap.traapapp.ui.activities.news.NewsDetailsAction;
import com.traap.traapapp.ui.adapters.news.NewsDetailsImageAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.utilities.JustifiedTextView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;

import org.greenrobot.eventbus.EventBus;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;


@SuppressLint("newsDetailsContentFragment")
public class NewsDetailsContentFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<LikeNewsDetailResponse>>
{
    private View rootView;
    private Context context;

    private Boolean like = false;

    private TextView tvTitle, tvLikeCounter, tvDateTime, tvSource, tvNewsArchive;
    private JustifiedTextView tvSubTitle, tvBody;
    private ImageView imgLike, imgBookmark;

    private NewsDetailsImageAdapter adapter;
    private GetNewsDetailsResponse content;

    private RelativeLayout rlArrowLeft, rlArrowRight;

    private LinearLayoutManager layoutManager;;
    private RecyclerView rcImageGallery;
    private ScrollingPagerIndicator indicator;

    private NewsDetailsAction actionView;

    public NewsDetailsContentFragment() { }

    public static NewsDetailsContentFragment newInstance(NewsDetailsAction actionView, GetNewsDetailsResponse content)
    {
        NewsDetailsContentFragment f = new NewsDetailsContentFragment();
        f.setActionView(actionView);

        Bundle arg = new Bundle();
        arg.putParcelable("content", content);

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
            content = getArguments().getParcelable("content");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_news_details_content, container, false);

        tvTitle = rootView.findViewById(R.id.tvTitle);
        tvNewsArchive = rootView.findViewById(R.id.tvNewsArchive);
        tvSubTitle = rootView.findViewById(R.id.tvSubTitle);
        tvBody = rootView.findViewById(R.id.tvBody);
        tvLikeCounter = rootView.findViewById(R.id.tvLikeCounter);
        imgLike = rootView.findViewById(R.id.imgLike);
        imgBookmark = rootView.findViewById(R.id.imgBookmark);
        tvSource = rootView.findViewById(R.id.tvSource);
        tvDateTime = rootView.findViewById(R.id.tvDate);

        rlArrowLeft = rootView.findViewById(R.id.rlArrowLeft);
        rlArrowRight = rootView.findViewById(R.id.rlArrowRight);

        tvTitle.setText(content.getTitle());
        tvLikeCounter.setText(String.valueOf(content.getLikeCounter()));

        if (content.getBookmarked())
        {
            imgBookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark_gold));
        }
        else
        {
            imgBookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark_border));
        }

        try
        {
            like = content.getLiked();
            if (content.getLiked())
            {
                imgLike.setColorFilter(getResources().getColor(R.color.imageLikedNewsTintColor));
                tvLikeCounter.setTextColor(getResources().getColor(R.color.imageLikedNewsTintColor));
            }
            else
            {
                imgLike.setColorFilter(getResources().getColor(R.color.textHint));
                tvLikeCounter.setTextColor(getResources().getColor(R.color.textHint));
            }
        }
        catch (Exception e)
        {
            imgLike.setColorFilter(getResources().getColor(R.color.textHint));
            tvLikeCounter.setTextColor(getResources().getColor(R.color.textHint));
        }

        tvSource.setText("منبع: " + content.getSource());
        tvDateTime.setText(content.getCreateDate());

        tvSubTitle.setText(content.getSubtitle());
        tvBody.setText(content.getBody().replace(".", ".\r\n"));

        tvSubTitle.setTypeFace(Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_normal.ttf"));
        tvBody.setTypeFace(Typeface.createFromAsset(context.getAssets(), "fonts/iran_sans_normal.ttf"));

        tvSubTitle.setLineSpacing(10);
        tvBody.setLineSpacing(10);

        tvSubTitle.setTextSize(getResources().getDimension(R.dimen.textSize_14dp));
        tvBody.setTextSize(getResources().getDimension(R.dimen.textSize_16dp));

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
//            tvSubTitle.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
//            tvBody.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
//        }
//        else
//        {
//
//        }
        tvNewsArchive.setOnClickListener(v ->
        {
            NewsArchiveClickModel fromNewsDetails = new NewsArchiveClickModel();
            fromNewsDetails.setFromNewsDetails(true);

            //onNewsArchiveClick in MainActivity
            SingletonNewsArchiveClick.getInstance().setNewsArchiveClickModel(fromNewsDetails);
            getActivity().finish();

        });

        rootView.findViewById(R.id.rlLike).setOnClickListener(v ->
        {
            LikeNewsDetailRequest request = new LikeNewsDetailRequest();
            like = !like;
            request.setLike(like);
            SingletonService.getInstance().getNewsService().likeNews(content.getId(), request, this);
        });

        imgBookmark.setOnClickListener(v ->
        {
            SingletonService.getInstance().getNewsService().setBookmarkNews(content.getId(), new OnServiceStatus<WebServiceClass<Object>>()
            {
                @Override
                public void onReady(WebServiceClass<Object> response)
                {
                    if (response.info.statusCode == 200)
                    {
                        imgBookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark_gold));
                    }
                    else
                    {
                        showAlert(context, response.info.message, R.string.error);
                    }
                }

                @Override
                public void onError(String message)
                {
                    showAlert(context, "خطای ارتباط با سرور!", R.string.error);
                }
            });
        });

        adapter = new NewsDetailsImageAdapter(context, content.getImages());

        rlArrowLeft.setOnClickListener(v ->
        {
            onSlideLeft();
        });

        rlArrowRight.setOnClickListener(v ->
        {
            onSlideRight();
        });

        rcImageGallery = rootView.findViewById(R.id.rcImageGallery);
        indicator = rootView.findViewById(R.id.indicator);

        if (content.getImages().isEmpty())
        {
            rootView.findViewById(R.id.rlImeges).setVisibility(View.GONE);
            rootView.findViewById(R.id.llIndicator).setVisibility(View.GONE);
        }
        else
        {
            rootView.findViewById(R.id.rlImeges).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.llIndicator).setVisibility(View.VISIBLE);

            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            rcImageGallery.setLayoutManager(layoutManager);
            rcImageGallery.setAdapter(adapter);
            indicator.attachToRecyclerView(rcImageGallery);

//        SnapHelper snapHelper = new StartSnapHelper();
            SnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(rcImageGallery);
        }

        return rootView;
    }

    private void onSlideRight()
    {
        if (layoutManager.findFirstCompletelyVisibleItemPosition() == Objects.requireNonNull(rcImageGallery.getAdapter()).getItemCount())
        {
            return;
        }
        int newPos = layoutManager.findFirstCompletelyVisibleItemPosition() + 1;

        rcImageGallery.smoothScrollToPosition(newPos);

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
        if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0)
            return;

        int newPos = layoutManager.findFirstCompletelyVisibleItemPosition() - 1;

        rcImageGallery.smoothScrollToPosition(newPos);

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
    public void onReady(WebServiceClass<LikeNewsDetailResponse> response)
    {
        if (response.info.statusCode == 200)
        {
            if (response.data.getIsLiked())
            {
                imgLike.setColorFilter(getResources().getColor(R.color.imageLikedNewsTintColor));
                tvLikeCounter.setTextColor(getResources().getColor(R.color.imageLikedNewsTintColor));
                int likeCounter = response.data.getLikeCount();
                tvLikeCounter.setText(String.valueOf(likeCounter));
                content.setLikeCounter(likeCounter);
            }
            else
            {
                imgLike.setColorFilter(getResources().getColor(R.color.textHint));
                tvLikeCounter.setTextColor(getResources().getColor(R.color.textHint));
                int likeCounter = response.data.getLikeCount();
                tvLikeCounter.setText(String.valueOf(likeCounter));
                content.setLikeCounter(likeCounter);
            }
        }
    }

    @Override
    public void onError(String message)
    {
        if (Tools.isNetworkAvailable((Activity) context))
        {
            Logger.e("-OnError Like-", "Error: " + message);
            showError(context, "خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            showAlert(context, R.string.networkErrorMessage, R.string.networkError);
        }
    }
}
