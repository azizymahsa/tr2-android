package ir.trap.tractor.android.ui.fragments.favoriteCard;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.card.getCardList.GetCardListResponse;
import ir.trap.tractor.android.apiServices.model.card.getCardList.Result;
import ir.trap.tractor.android.ui.adapters.favoriteCard.CardViewPagerAdapter;
import ir.trap.tractor.android.ui.adapters.favoriteCard.ViewPagerAdapterAction;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class FavoriteCardFragment extends Fragment implements FavoriteCardActionView, ViewPagerAdapterAction,
        OnServiceStatus<WebServiceClass<GetCardListResponse>>
{
    private ImageView ivLeft, ivRight;
    private ScrollingPagerIndicator indicator;

    private RecyclerView rvListCard;
    private LinearLayoutManager linearLayoutManager;
    private CardViewPagerAdapter adapter;

    private FavoriteCardParentActionView parentView;

    private List<Result> cardList = new ArrayList<>();;

    public FavoriteCardFragment()
    {
        // Required empty public constructor
    }

    public static FavoriteCardFragment newInstance(FavoriteCardParentActionView parentView)
    {
        FavoriteCardFragment fragment = new FavoriteCardFragment();
        fragment.setParentView(parentView);

        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    private void setParentView(FavoriteCardParentActionView parentView)
    {
        this.parentView = parentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_favorite_card, container, false);

        rvListCard = rootView.findViewById(R.id.rvListCard);
        ivRight = rootView.findViewById(R.id.ivRight);
        ivLeft = rootView.findViewById(R.id.ivLeft);
        indicator = rootView.findViewById(R.id.indicator);

        ivLeft.setOnClickListener(clickListener);
        ivRight.setOnClickListener(clickListener);

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvListCard.setLayoutManager(linearLayoutManager);
        adapter = new CardViewPagerAdapter(cardList, this, this);
        rvListCard.setAdapter(adapter);

        indicator.attachToRecyclerView(rvListCard);

        rvListCard.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);

                if (linearLayoutManager.findFirstVisibleItemPosition() == 0)
                {
//                    presenter.viewPagerPosition(linearLayoutManager.findFirstVisibleItemPosition());
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    if (linearLayoutManager.findFirstVisibleItemPosition() == 0)
                    {
                        ivLeft.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        ivLeft.setVisibility(View.VISIBLE);
                    }

                    if (linearLayoutManager.findFirstVisibleItemPosition() == linearLayoutManager.getItemCount() - 1)
                    {
                        ivRight.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        ivRight.setVisibility(View.VISIBLE);
                    }

                    new Handler().postDelayed(() ->
                    {
//                        presenter.viewPagerPosition(linearLayoutManager.findFirstVisibleItemPosition());
                        EventBus.getDefault().post(cardList.get(linearLayoutManager.findFirstVisibleItemPosition()));
                    }, 200);
                }
            }
        });

        getData();

        return rootView;
    }

    private void getData()
    {
        parentView.showFavoriteCardParentLoading();

        SingletonService.getInstance().getCardListService().getMenu(this);
    }

    View.OnClickListener clickListener = view ->
    {
        if (view.getId() == R.id.ivLeft)
        {

        }
        else if (view.getId() == R.id.ivRight)
        {

        }
    };

    @Override
    public void onReady(WebServiceClass<GetCardListResponse> response)
    {
        parentView.hideFavoriteCardParentLoading();

        //fill adapter
        if (response.info.statusCode == 200)
        {
            cardList = response.data.getResults();
//            adapter = new CardViewPagerAdapter(cardList, this, this);
            adapter.notifyDataSetChanged();

            rvListCard.setAdapter(adapter);

            indicator.attachToRecyclerView(rvListCard);


        }
        else
        {

        }


    }

    @Override
    public void onError(String message)
    {
        parentView.hideFavoriteCardParentLoading();


    }

    @Override
    public void onSlideRight()
    {
        if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == Objects.requireNonNull(rvListCard.getAdapter()).getItemCount())
            return;
        int newPos = linearLayoutManager.findFirstCompletelyVisibleItemPosition() + 1;

        rvListCard.smoothScrollToPosition(newPos);

        if (newPos == Objects.requireNonNull(rvListCard.getAdapter()).getItemCount())
        {
            ivRight.setVisibility(View.INVISIBLE);
        }
        else
        {
            ivRight.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSlideLeft()
    {
        if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0)
            return;

        int newPos = linearLayoutManager.findFirstCompletelyVisibleItemPosition() - 1;

        rvListCard.smoothScrollToPosition(newPos);

        if (newPos == 0)
        {
            ivLeft.setVisibility(View.INVISIBLE);
        }
        else
        {
            ivLeft.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onShowEditDialog(Result result, int Position)
    {

    }

    @Override
    public void onShowPasswordChangeDialog(Result result, int Position)
    {

    }

    @Override
    public void showConfirmDeleteCard(Result result, int Position)
    {

    }

    @Override
    public void onFavorite(int position)
    {

    }

    @Override
    public void onDelete(int position)
    {

    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener)
//        {
//            mListener = (OnFragmentInteractionListener) context;
//        } else
//        {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
//        mListener = null;
    }

}
