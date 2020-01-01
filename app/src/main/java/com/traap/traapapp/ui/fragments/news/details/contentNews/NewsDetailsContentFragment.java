package com.traap.traapapp.ui.fragments.news.details.contentNews;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
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

import com.makeramen.roundedimageview.RoundedImageView;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.news.details.getContent.response.GetNewsDetailsResponse;
import com.traap.traapapp.apiServices.model.news.details.putBookmark.response.NewsBookmarkResponse;
import com.traap.traapapp.apiServices.model.news.details.sendLike.request.LikeNewsDetailRequest;
import com.traap.traapapp.apiServices.model.news.details.sendLike.response.LikeNewsDetailResponse;
import com.traap.traapapp.apiServices.model.news.main.ImageName;
import com.traap.traapapp.apiServices.model.photo.response.Content;
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
public class NewsDetailsContentFragment extends BaseFragment implements NewsDetailsImageAdapter.OnItemNewsClickListener
//        , View.OnClickListener
        , OnServiceStatus<WebServiceClass<LikeNewsDetailResponse>>
{
    private View rootView;
    private Context context;

    private Boolean like = false;

    private TextView tvTitle, tvLikeCounter, tvDateTime, tvSource, tvNewsArchive;
    private JustifiedTextView tvSubTitle, tvBody;
    private ImageView imgLike, imgBookmark;

    private NewsDetailsImageAdapter adapter;
    private GetNewsDetailsResponse content;

    private RelativeLayout rlArrowLeft, rlArrowRight, rlImeges;

    private LinearLayoutManager layoutManager;

    private RecyclerView rcImageGallery;
    private ScrollingPagerIndicator indicator;

    private NewsDetailsAction actionView;
    private RoundedImageView ivBigLike;

    private static final long DOUBLE_CLICK_TIME_DELTA = 300;
    long lastClickTime = 0;
    private boolean doubleClick = false;
    private boolean isMoving = false;

    public NewsDetailsContentFragment()
    {
    }

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

        ivBigLike = rootView.findViewById(R.id.ivBigLike);
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

        if (content.getImages().size() == 1)
        {
            rlArrowLeft.setVisibility(View.GONE);
            rlArrowRight.setVisibility(View.GONE);
        }

        try
        {
            if (content.getBookmarked())
            {
                imgBookmark.setColorFilter(getResources().getColor(R.color.backgroundButton));

                //  imgBookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark_gold));
            } else
            {
                imgBookmark.setColorFilter(getResources().getColor(R.color.borderBackgroundColor));
                // imgBookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark_border));
            }
        } catch (NullPointerException e)
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
            } else
            {
                imgLike.setColorFilter(getResources().getColor(R.color.textHint));
                tvLikeCounter.setTextColor(getResources().getColor(R.color.textHint));
            }
        } catch (Exception e)
        {
            imgLike.setColorFilter(getResources().getColor(R.color.textHint));
            tvLikeCounter.setTextColor(getResources().getColor(R.color.textHint));
        }

        tvSource.setText("منبع: " + content.getSource());
//        tvDateTime.setText(content.getPublishDate());
        tvDateTime.setText(content.getPublishDateStr());

        if (content.getSubtitle().equalsIgnoreCase(""))
        {
            tvSubTitle.setVisibility(View.GONE);
        } else
        {
            tvSubTitle.setText(content.getSubtitle());
        }
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
            ivBigLike.setVisibility(View.VISIBLE);
            sendRequestLike();

        });

        imgBookmark.setOnClickListener(v ->
        {
            SingletonService.getInstance().getNewsService().setBookmarkNews(content.getId(), new OnServiceStatus<WebServiceClass<NewsBookmarkResponse>>()
            {
                @Override
                public void onReady(WebServiceClass<NewsBookmarkResponse> response)
                {
                    if (response.info.statusCode == 200)
                    {
                        if (response.data.getIsBookmarked())
                        {
                            imgBookmark.setColorFilter(getResources().getColor(R.color.backgroundButton));
                            //imgBookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark_gold));
                        } else
                        {
                            imgBookmark.setColorFilter(getResources().getColor(R.color.borderBackgroundColor));
                            // imgBookmark.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bookmark_border));
                        }
                    } else
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

        adapter = new NewsDetailsImageAdapter(context, content.getImages(),this);

        rlArrowLeft.setOnClickListener(v ->
        {
            onSlideLeft();
        });

        rlArrowRight.setOnClickListener(v ->
        {
            onSlideRight();
        });

        rcImageGallery = rootView.findViewById(R.id.rcImageGallery);
//        rcImageGallery.setOnClickListener(this);
        rlImeges = rootView.findViewById(R.id.rlImegess);
//        rlImeges.setOnClickListener(this);
        indicator = rootView.findViewById(R.id.indicator);

        if (content.getImages().isEmpty())
        {
            rlImeges.setVisibility(View.GONE);
            rootView.findViewById(R.id.llIndicator).setVisibility(View.GONE);
        } else
        {
            rlImeges.setVisibility(View.VISIBLE);
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

    private void sendRequestLike()
    {
        LikeNewsDetailRequest request = new LikeNewsDetailRequest();
        like = !like;
        request.setLike(like);
        SingletonService.getInstance().getNewsService().likeNews(content.getId(), request, this);
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
            animateHeart(ivBigLike);
            if (response.data.getIsLiked())
            {
                imgLike.setColorFilter(getResources().getColor(R.color.imageLikedNewsTintColor));
                tvLikeCounter.setTextColor(getResources().getColor(R.color.imageLikedNewsTintColor));
                int likeCounter = response.data.getLikeCount();
                tvLikeCounter.setText(String.valueOf(likeCounter));
                content.setLikeCounter(likeCounter);
            } else
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
        } else
        {
            showAlert(context, R.string.networkErrorMessage, R.string.networkError);
        }
    }

    public void animateHeart(final ImageView view)
    {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        prepareAnimation(scaleAnimation);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        prepareAnimation(alphaAnimation);

        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(alphaAnimation);
        animation.addAnimation(scaleAnimation);
        animation.setDuration(700);
        animation.setFillAfter(true);

        view.startAnimation(animation);

    }

    private Animation prepareAnimation(Animation animation)
    {
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
        return animation;
    }

//    @Override
//    public void onClick(View v)
//    {
//        switch (v.getId())
//        {
//
//            case R.id.rlImegess:
//
//
//
//                break;
//
//
//        }
//    }

    @Override
    public void OnItemNewsClick(View v, ImageName content)
    {
        v.setAlpha((float) 1.0);
        if (!isMoving)
        {
            long clickTime = System.currentTimeMillis();
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA)
            {
                doubleClick = true;
                System.out.println("-----------doubleClick");
                lastClickTime = 0;
                ivBigLike.setVisibility(View.VISIBLE);
                sendRequestLike();


            } else
            {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (!doubleClick)
                        {
                            System.out.println("--------------singleClick");

                        } else
                            doubleClick = false;
                    }
                }, 350);
            }
            lastClickTime = clickTime;
        }
    }
}
