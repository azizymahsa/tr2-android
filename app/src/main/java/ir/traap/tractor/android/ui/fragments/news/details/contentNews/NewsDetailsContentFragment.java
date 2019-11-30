package ir.traap.tractor.android.ui.fragments.news.details.contentNews;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
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

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.news.details.getContent.response.GetNewsDetailsResponse;
import ir.traap.tractor.android.apiServices.model.news.details.sendRate.LikeNewsDetailResponse;
import ir.traap.tractor.android.ui.adapters.news.NewsDetailsImageAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.utilities.JustifiedTextView;
import ir.traap.tractor.android.utilities.Logger;
import ir.traap.tractor.android.utilities.Tools;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;


@SuppressLint("newsDetailsContentFragment")
public class NewsDetailsContentFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<LikeNewsDetailResponse>>
{
    private View rootView;
    private Context context;

    private TextView tvTitle, tvLikeCounter, tvDateTime, tvSource;
    private JustifiedTextView tvSubTitle, tvBody;
    private ImageView imgLike;

    private NewsDetailsImageAdapter adapter;
    private GetNewsDetailsResponse content;

    private RelativeLayout rlArrowLeft, rlArrowRight;

    private LinearLayoutManager layoutManager;;
    private RecyclerView rcImageGallery;
    private ScrollingPagerIndicator indicator;

    public NewsDetailsContentFragment() { }

    public static NewsDetailsContentFragment newInstance(GetNewsDetailsResponse content)
    {
        NewsDetailsContentFragment f = new NewsDetailsContentFragment();

        Bundle arg = new Bundle();
        arg.putParcelable("content", (Parcelable) content);

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
            content = getArguments().getParcelable("content");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_news_details_content, container, false);

        tvTitle = rootView.findViewById(R.id.tvTitle);
        tvSubTitle = rootView.findViewById(R.id.tvSubTitle);
        tvBody = rootView.findViewById(R.id.tvBody);
        tvLikeCounter = rootView.findViewById(R.id.tvLikeCounter);
        imgLike = rootView.findViewById(R.id.imgLike);
        tvSource = rootView.findViewById(R.id.tvSource);
        tvDateTime = rootView.findViewById(R.id.tvDate);

        rlArrowLeft = rootView.findViewById(R.id.rlArrowLeft);
        rlArrowRight = rootView.findViewById(R.id.rlArrowRight);

        tvTitle.setText(content.getTitle());
        tvLikeCounter.setText(String.valueOf(content.getLikeCounter()));

        try
        {
            if (content.getLiked())
            {
                imgLike.setColorFilter(getResources().getColor(R.color.imageLikedTintColor));
                tvLikeCounter.setTextColor(getResources().getColor(R.color.imageLikedTintColor));
            }
            else
            {
                imgLike.setColorFilter(getResources().getColor(R.color.gray));
                tvLikeCounter.setTextColor(getResources().getColor(R.color.gray));
            }
        }
        catch (Exception e)
        {
            imgLike.setColorFilter(getResources().getColor(R.color.gray));
            tvLikeCounter.setTextColor(getResources().getColor(R.color.gray));
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

        rootView.findViewById(R.id.rlLike).setOnClickListener(v ->
        {
            SingletonService.getInstance().getNewsService().likeNews(content.getId(), this);
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
                imgLike.setColorFilter(getResources().getColor(R.color.imageLikedTintColor));
                tvLikeCounter.setTextColor(getResources().getColor(R.color.imageLikedTintColor));
                int likeCounter = content.getLikeCounter() + 1;
                tvLikeCounter.setText(String.valueOf(likeCounter));
            }
            else
            {
                imgLike.setColorFilter(getResources().getColor(R.color.gray));
                tvLikeCounter.setTextColor(getResources().getColor(R.color.gray));
                int likeCounter = content.getLikeCounter() - 1;
                tvLikeCounter.setText(String.valueOf(likeCounter));
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
