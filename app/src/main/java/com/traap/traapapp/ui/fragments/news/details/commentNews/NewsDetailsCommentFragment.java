package com.traap.traapapp.ui.fragments.news.details.commentNews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.news.details.getComment.response.GetNewsCommentResponse;
import com.traap.traapapp.apiServices.model.news.details.sendComment.request.SendCommentNewsRequest;
import com.traap.traapapp.apiServices.model.news.details.sendLike.request.LikeNewsDetailRequest;
import com.traap.traapapp.apiServices.model.news.details.sendLike.response.LikeNewsDetailResponse;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.news.NewsDetailsAction;
import com.traap.traapapp.ui.adapters.news.NewsCommentListAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.utilities.Logger;

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

    private ArrayList<GetNewsCommentResponse> list;

    private EditText edtComment;

    private CircularProgressButton btnSendComment;

//    private List<RelatedNews> relatedNewList = new ArrayList<>();
//    private List<MediaDetailsPositionIdsModel> positionIdsList;

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

        edtComment.setFilters(new InputFilter[] { new InputFilter.LengthFilter(399) });

        layoutManager = new GridLayoutManager(getActivity(), 1);
        rcCommentList.setLayoutManager(layoutManager);

        SingletonService.getInstance().getNewsService().getNewsComment(Id, this);
        //---------------------test----------------------
//        progress.setVisibility(View.GONE);
//        rootView.findViewById(R.id.llCommentList).setVisibility(View.GONE);
        //---------------------test----------------------

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
                        try{
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
                        }catch (Exception e){}

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
        try{
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
                    list = new ArrayList<>();
                    list = response.data;
                    rootView.findViewById(R.id.llCommentList).setVisibility(View.VISIBLE);
                    Logger.e("-Comment List Size-", "Size: " + list.size());
                    //--------------
                    adapter = new NewsCommentListAdapter(context, list, this);
                    rcCommentList.setAdapter(adapter);
                }
            }
        }catch (Exception e){}

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
        onLikeDislikeClick(true, id, position);
    }

    @Override
    public void onDislikeItemClick(View view, Integer id, Integer position)
    {
        onLikeDislikeClick(false, id, position);
    }

    @Override
    public void onReplayItemClick(View view, Integer id, Integer position)
    {

    }

    private void onLikeDislikeClick(Boolean likeOrDislike, Integer id, Integer position)
    {
        LikeNewsDetailRequest request = new LikeNewsDetailRequest();
        request.setLike(likeOrDislike);

        SingletonService.getInstance().getNewsService().setLikeDislikeComment(id, request, new OnServiceStatus<WebServiceClass<LikeNewsDetailResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<LikeNewsDetailResponse> response)
            {
                try{
                    if (response.info.statusCode == 200)
                    {
                        int type = likeOrDislike ? 1 : 0;

//                    list.get(position).setRated(response.data.getIsLiked() ? type : 0);
//                    adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        showToast(context, "خطا در ثبت رخداد!", R.color.red);
                    }
                }catch (Exception e){}

            }

            @Override
            public void onError(String message)
            {
                showToast(context, "خطای ارتباط با سرور!", R.color.red);
            }
        });
    }
}
