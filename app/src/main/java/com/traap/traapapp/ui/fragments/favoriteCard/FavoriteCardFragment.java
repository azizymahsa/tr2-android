package com.traap.traapapp.ui.fragments.favoriteCard;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.card.editCard.request.EditCardRequest;
import com.traap.traapapp.apiServices.model.card.getCardList.GetCardListResponse;
import com.traap.traapapp.apiServices.model.card.Result;
import com.traap.traapapp.apiServices.model.shetacChangePass2.request.ShetacChangePass2Request;
import com.traap.traapapp.apiServices.model.shetacForgotPass2.request.ShetacForgotPass2Request;
import com.traap.traapapp.models.otherModels.addCard.AddCardModel;
import com.traap.traapapp.ui.adapters.favoriteCard.CardViewPagerAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.base.GoToActivity;
import com.traap.traapapp.ui.dialogs.ChangePasswordDialog;
import com.traap.traapapp.ui.dialogs.DialogDeleteCard;
import com.traap.traapapp.ui.dialogs.DialogEditCard;
import com.traap.traapapp.utilities.LinearLayoutManagerWithSmoothScroller;
import com.traap.traapapp.utilities.ScreenShot;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class FavoriteCardFragment extends BaseFragment implements FavoriteCardActionView,
        OnServiceStatus<WebServiceClass<GetCardListResponse>>
{
    private ImageView ivLeft, ivRight;
    private ScrollingPagerIndicator indicator;

    private ProgressBar progress;
    private LinearLayout llCardList, llIndicator;

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
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_favorite_card, container, false);

        llCardList = rootView.findViewById(R.id.llCardList);
        llIndicator = rootView.findViewById(R.id.llIndicator);
        progress = rootView.findViewById(R.id.progress);

        rvListCard = rootView.findViewById(R.id.rvListCard);
        ivRight = rootView.findViewById(R.id.ivRight);
        ivLeft = rootView.findViewById(R.id.ivLeft);
        indicator = rootView.findViewById(R.id.indicator);

        ivLeft.setOnClickListener(clickListener);
        ivRight.setOnClickListener(clickListener);

//        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManagerWithSmoothScroller.HORIZONTAL, false);
        rvListCard.setLayoutManager(linearLayoutManager);

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvListCard.getContext(),
//                linearLayoutManager.getOrientation());
//        rvListCard.addItemDecoration(dividerItemDecoration);

        adapter = new CardViewPagerAdapter(cardList, this);
        rvListCard.setAdapter(adapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(rvListCard);

        indicator.attachToRecyclerView(rvListCard);

        rvListCard.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);

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
                        EventBus.getDefault().post(cardList.get(linearLayoutManager.findFirstVisibleItemPosition()));
                    }, 200);
                }
//                if (newState == RecyclerView.n )
//                recyclerView.smoothScrollToPosition(linearLayoutManager.findFirstVisibleItemPosition());
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
            onSlideLeft();
        }
        else if (view.getId() == R.id.ivRight)
        {
            onSlideRight();
        }
    };

    @Override
    public void onReady(WebServiceClass<GetCardListResponse> response)
    {
        parentView.hideFavoriteCardParentLoading();

        progress.setVisibility(View.GONE);
        llCardList.setVisibility(View.VISIBLE);
        llIndicator.setVisibility(View.VISIBLE);

        //fill adapter
        if (response.info.statusCode == 200)
        {
            Result result = new Result();
            result.setBankBin("");
            cardList.add(result);
            cardList.addAll(response.data.getResults());
//            adapter = new CardViewPagerAdapter(cardList, this, this);
            adapter.notifyDataSetChanged();

            rvListCard.setAdapter(adapter);
            indicator.attachToRecyclerView(rvListCard);

            if (cardList.size() > 2)
            {
                int favorittePos = 0;
                for (int i = 1 ; i <= response.data.getResults().size() ; i++)
                {
                    if (response.data.getResults().get(i-1).getIsFavorite())
                    {
                        favorittePos = i;
                    }
                }
                rvListCard.smoothScrollToPosition(favorittePos == 0 ? 1 : favorittePos);
            }
            else
            {
                rvListCard.smoothScrollToPosition(1);
            }
        }
        else
        {

        }


    }

    @Override
    public void onError(String message)
    {
        parentView.hideFavoriteCardParentLoading();

        progress.setVisibility(View.GONE);

    }

    @Override
    public void onSlideRight()
    {
        if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == Objects.requireNonNull(rvListCard.getAdapter()).getItemCount())
        {
            return;
        }
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
    public void onShowEditDialog(Result result, int position)
    {
        DialogEditCard editCardDialog = new DialogEditCard(getActivity(), result, this, position);
        editCardDialog.show(getActivity().getFragmentManager(), "editCard");
    }

    @Override
    public void onShowChangePasswordDialog(Result result, int Position)
    {
        ChangePasswordDialog dialog = new ChangePasswordDialog(getActivity(), this, result);
        dialog.show(getActivity().getFragmentManager(), "changePasswordDialog");
    }

    @Override
    public void onShowConfirmDeleteDialog(Result result, int position)
    {
        new DialogDeleteCard(getActivity(), "آیا از حذف کارت " + result.getCardNumber() + " اطمینان دارید؟",
                this, result.getCardId(), position).show(getActivity().getFragmentManager(), "deleteCard");
    }

    @Override
    public void onEditCard(Result cardDetail, int position)
    {
        parentView.showFavoriteCardParentLoading();

        EditCardRequest request = new EditCardRequest();
        request.setCardNumber(cardDetail.getCardNumber());
        request.setFullName(cardDetail.getFullName());
        request.setIsMainCard(false);
        request.setOrderList(2);

        SingletonService.getInstance().editCardService().editCardService(cardDetail.getCardId(), request, new OnServiceStatus<WebServiceClass<Result>>()
        {
            @Override
            public void onReady(WebServiceClass<Result> response)
            {
                parentView.hideFavoriteCardParentLoading();

                if (response == null)
                {
                    showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                    return;
                }
                if (response.info.statusCode != 200)
                {
                    showError(getActivity(), response.info.message);
                    return;
                }

                showToast(getActivity(), "کارت با موفقیت ویرایش و بروزرسانی شد.", R.color.green);
                cardList.set(position, cardDetail);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message)
            {
                parentView.hideFavoriteCardParentLoading();

                showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
            }
        });
    }

    @Override
    public void onDeleteCard(Integer cardId, int position)
    {
        parentView.showFavoriteCardParentLoading();

        SingletonService.getInstance().deleteCardService().deleteCardService(cardId, new OnServiceStatus<WebServiceClass<Object>>()
        {
            @Override
            public void onReady(WebServiceClass<Object> response)
            {
                parentView.hideFavoriteCardParentLoading();

                if (response == null)
                {
                    showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                    return;
                }
                if (response.info.statusCode != 204)
                {
                    showError(getActivity(), response.info.message);
                    return;
                }

                showToast(getActivity(), "کارت با موفقیت حذف شد.", R.color.green);
                cardList.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message)
            {
                parentView.hideFavoriteCardParentLoading();

                showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
            }
        });
    }

    @Override
    public void onChangePasswordCard(Integer cardId, String oldPin2, String newPin2)
    {
        parentView.showFavoriteCardParentLoading();

        ShetacChangePass2Request request = new ShetacChangePass2Request();
        request.setCardId(cardId);
        request.setPin2Old(oldPin2);
        request.setPin2New(newPin2);

        SingletonService.getInstance().doChangePassService().sheatcChangePassService(request, new OnServiceStatus<WebServiceClass<Object>>()
        {
            @Override
            public void onReady(WebServiceClass<Object> response)
            {
                parentView.hideFavoriteCardParentLoading();

                showAlert(getActivity(), response.info.message, 0);
            }

            @Override
            public void onError(String message)
            {
                parentView.hideFavoriteCardParentLoading();

                showAlert(getActivity(), "خطا در دریافت اطلاعات از سرور!", 0);
            }
        });
    }

    @Override
    public void onForgotPasswordCard(Integer cardId)
    {
        parentView.showFavoriteCardParentLoading();

        ShetacForgotPass2Request request = new ShetacForgotPass2Request();
        request.setCardId(cardId);
        request.setMobile(Prefs.getString("mobile", ""));

        SingletonService.getInstance().doForgotPassService().sheatcForgotPassService(request,
                new OnServiceStatus<WebServiceClass<Object>>()
        {
            @Override
            public void onReady(WebServiceClass<Object> response)
            {
                parentView.hideFavoriteCardParentLoading();

                showAlert(getActivity(), response.info.message, 0);
            }

            @Override
            public void onError(String message)
            {
                parentView.hideFavoriteCardParentLoading();

                showAlert(getActivity(), "خطا در دریافت اطلاعات از سرور!", 0);

            }
        });
    }

    @Override
    public void onShareCard(View view)
    {
        new ScreenShot(view,getActivity());
    }

    @Override
    public void startActivity(GoToActivity activity)
    {
        if (activity == GoToActivity.AddCardActivity)
        {
            parentView.startAddCardActivity();
        }
    }

    @Subscribe
    public void addCard(AddCardModel cardModel)
    {
        cardList.add(cardModel.getCard());
        adapter.notifyDataSetChanged();
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
        EventBus.getDefault().unregister(this);
    }

}