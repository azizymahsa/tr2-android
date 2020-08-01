package com.traap.traapapp.ui.fragments.cardManagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jakewharton.rxbinding3.view.RxView;
import com.pixplicity.easyprefs.library.Prefs;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.card.CardBankItem;
import com.traap.traapapp.apiServices.model.card.getCardList.GetCardListResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.card.add.AddCardActivity;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.adapters.cardManagement.CardManagementAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.KeyboardUtils;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.NestedScrollableViewHelper;
import com.traap.traapapp.utilities.Tools;
import com.traap.traapapp.utilities.Utility;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import kotlin.Unit;


public class CardManagementFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<GetCardListResponse>>,
        CardManagementAdapter.onCardActionListener
{
    private CompositeDisposable disposable;

    private List<CardBankItem> cardBankList;
    private CardManagementAdapter adapter;

    private Context context;
    private Toolbar mToolbar;

    private TextView tvUserName, tvHeaderPopularNo;
    private SlidingUpPanelLayout slidingUpPanelLayout;

    private RecyclerView rcCardList;

    private LinearLayout layEditCard, layFunctionCard;
    private ImageButton btnAdd;
    private Button btnConfirmEdit, btnCancelEdit, btnDefaultCard, btnDeleteCard, btnEditCard;
    private ImageView imgCloseEditCard, imgCloseFunction;
    private EditText edtNumberCardEdit, edtFullName, edtExpYear, edtExpMound;

    private View rootView;

    private MainActionView mainView;
    private CardBankItem selectedCardBankItem = null;

    public CardManagementFragment()
    {
    }

    public static CardManagementFragment newInstance(MainActionView mainView)
    {
        CardManagementFragment f = new CardManagementFragment();

        Bundle args = new Bundle();
        f.setArguments(args);
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
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
//        if (getArguments() != null)
//        {
////            teamId = getArguments().getString("teamId");
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_card_management, container, false);

        disposable = new CompositeDisposable();

//        getTypeTransactionList();

        initView();

        mainView.showLoading();
        SingletonService.getInstance().getCardListService().getCardList(this);

        EventBus.getDefault().register(this);
        return rootView;
    }

//    private void getTypeTransactionList()
//    {
//        mainView.showLoading();
//
//        SingletonService.getInstance().getTransactionService().getTypeTransactionList(new OnServiceStatus<WebServiceClass<ArrayList<TypeCategory>>>()
//        {
//            @Override
//            public void onReady(WebServiceClass<ArrayList<TypeCategory>> response)
//            {
//                try
//                {
//                    mainView.hideLoading();
//
//                    if (response.info.statusCode != 200)
//                    {
//                        showAlertAndFinish(response.info.message);
//                    }
//                    else
//                    {
//                        if (response.data.isEmpty())
//                        {
//                            showAlertAndFinish("خطا در دریافت اطلاعات از سرور!");
//                        }
//                        else
//                        {
//                            typeCategoryList = response.data;
//                            getData(false);
//                        }
//                    }
//                }
//                catch (Exception e)
//                {
//                    showAlertAndFinish(response.info.message);
//                }
//            }
//
//            @Override
//            public void onError(String message)
//            {
//                mainView.hideLoading();
//
//                if (Tools.isNetworkAvailable(Objects.requireNonNull(getActivity())))
//                {
//                    CardManagementFragment.this.onError(message);
//                }
//                else
//                {
//                    CardManagementFragment.this.onError(getString(R.string.networkErrorMessage));
//                }
//            }
//        });
//    }

    public void initView()
    {
        try
        {
            mToolbar = rootView.findViewById(R.id.toolbar);

            try
            {
                ((TextView) mToolbar.findViewById(R.id.tvTitle)).setText("مدیریت کارت ها");
            }
            catch (Throwable tx)
            {
            }

            disposable.add(RxView.clicks(mToolbar.findViewById(R.id.imgBack))
                    .subscribe(v ->
                    {
                        if (slidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED)
                        {
                            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        }
                        else
                        {
                            getActivity().onBackPressed();
                        }
                    })
            );

            disposable.add(RxView.clicks(mToolbar.findViewById(R.id.flLogoToolbar))
                    .subscribe(v ->
                    {
                        mainView.backToMainFragment();
                    })
            );

            disposable.add(RxView.clicks(mToolbar.findViewById(R.id.rlShirt))
                    .subscribe(v ->
                    {
                        startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class), 100);
                    })
            );

            disposable.add(RxView.clicks(mToolbar.findViewById(R.id.imgMenu))
                    .subscribe(v ->
                    {
                        mainView.openDrawer();
                    })
            );

            tvUserName = mToolbar.findViewById(R.id.tvUserName);
            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            tvHeaderPopularNo = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvHeaderPopularNo.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));

            rcCardList = rootView.findViewById(R.id.rcCardList);

            rcCardList.setLayoutManager(new LinearLayoutManager(getContext()));
            hideKeyboard((Activity) context);

            slidingUpPanelLayout = rootView.findViewById(R.id.slidingLayout);
//            slidingUpPanelLayout.setScrollableViewHelper(new NestedScrollableViewHelper());

            layEditCard = rootView.findViewById(R.id.layEditCard);
            layFunctionCard = rootView.findViewById(R.id.layFunctionCard);
            btnAdd = rootView.findViewById(R.id.btnAdd);
            btnCancelEdit = rootView.findViewById(R.id.btnCancelEdit);
            btnConfirmEdit = rootView.findViewById(R.id.btnConfirmEdit);
            btnDefaultCard = rootView.findViewById(R.id.btnDefaultCard);
            btnDeleteCard = rootView.findViewById(R.id.btnDeleteCard);
            btnEditCard = rootView.findViewById(R.id.btnEditCard);
            edtNumberCardEdit = rootView.findViewById(R.id.edtNumberCardEdit);
            edtFullName = rootView.findViewById(R.id.edtFullName);
            edtExpYear = rootView.findViewById(R.id.edtExpYear);
            edtExpMound = rootView.findViewById(R.id.edtExpMound);
            imgCloseEditCard = rootView.findViewById(R.id.imgCloseEditCard);
            imgCloseFunction = rootView.findViewById(R.id.imgCloseFunction);

            btnConfirmEdit.setText("تأیید");
            btnCancelEdit.setText("انصراف");
            btnDeleteCard.setText("حذف");
            btnEditCard.setText("ویرایش");
            btnDefaultCard.setText("کارت پیش فرض");
            layFunctionCard.setVisibility(View.GONE);
            layEditCard.setVisibility(View.GONE);

//            slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener()
//            {
//                @Override
//                public void onPanelSlide(View panel, float slideOffset) { }
//
//                @Override
//                public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState)
//                {
//                    if (previousState != SlidingUpPanelLayout.PanelState.COLLAPSED)
//                    {
//                        new Handler().postDelayed(() ->
//                        {
//                            layFunctionCard.setVisibility(View.GONE);
//                            layEditCard.setVisibility(View.GONE);
//
//                        }, 250);
//
//                    }
//                }
//            });

            disposable.add(RxView.clicks(btnCancelEdit)
                    .mergeWith(RxView.clicks(imgCloseEditCard))
                    .mergeWith(RxView.clicks(imgCloseFunction))
                    .subscribe(unit -> slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED))
            );

            disposable.add(RxView.clicks(btnConfirmEdit).map(unit -> "btnConfirmEdit")
//                    .mergeWith(RxView.clicks(btnCancelEdit).map(unit -> "btnCancelEdit"))
//                    .mergeWith(RxView.clicks(imgCloseEditCard).map(unit -> "imgCloseEditCard"))
//                    .mergeWith(RxView.clicks(imgCloseFunction).map(unit -> "imgCloseFunction"))
                    .mergeWith(RxView.clicks(btnDeleteCard).map(unit -> "btnDeleteCard"))
                    .mergeWith(RxView.clicks(btnEditCard).map(unit -> "btnEditCard"))
                    .mergeWith(RxView.clicks(btnDefaultCard).map(unit -> "btnDefaultCard"))
                    .mergeWith(RxView.clicks(btnAdd).map(unit -> "btnAdd"))
                    .subscribe(tagButton ->
                    {
                        switch (tagButton)
                        {
                            case "btnConfirmEdit":
                            {
                                KeyboardUtils.forceCloseKeyboard(rootView);
                                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                                new Handler().postDelayed(() ->
                                {
                                    layFunctionCard.setVisibility(View.GONE);
                                    layEditCard.setVisibility(View.GONE);

                                }, 200);

                                if (setErrorEditCard())
                                {

                                }
                                break;
                            }
//                            case "imgCloseFunction":
//                            case "imgCloseEditCard":
//                            case "btnCancelEdit":
//                            {
////                                KeyboardUtils.forceCloseKeyboard(btnCancelEdit);
//                                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
//                                new Handler().postDelayed(() ->
//                                {
//                                    layFunctionCard.setVisibility(View.GONE);
//                                    layEditCard.setVisibility(View.GONE);
//
//                                }, 250);
//
//                                selectedCardBankItem = null;
//                                break;
//                            }
                            case "btnDeleteCard":
                            {
//                                KeyboardUtils.forceCloseKeyboard(btnDeleteCard);
                                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                                new Handler().postDelayed(() ->
                                {
                                    layFunctionCard.setVisibility(View.GONE);
                                    layEditCard.setVisibility(View.GONE);

                                }, 200);

                                break;
                            }
                            case "btnEditCard":
                            {
//                                KeyboardUtils.forceCloseKeyboard(btnEditCard);
                                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                                new Handler().postDelayed(() ->
                                {
                                    layFunctionCard.setVisibility(View.GONE);
                                    layEditCard.setVisibility(View.VISIBLE);

                                }, 200);

                                edtNumberCardEdit.setText(selectedCardBankItem.getCardNumber());
                                edtFullName.setText(selectedCardBankItem.getFullName());

                                new Handler().postDelayed(() -> slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED), 200);


//                                edtExpMound.setText(selectedCardBankItem.get());
//                                edtExpYear.setText(selectedCardBankItem.get());

                                break;
                            }
                            case "btnDefaultCard":
                            {
//                                KeyboardUtils.forceCloseKeyboard(btnDefaultCard);
                                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                                new Handler().postDelayed(() ->
                                {
                                    layFunctionCard.setVisibility(View.GONE);
                                    layEditCard.setVisibility(View.GONE);

                                }, 250);
                                break;
                            }
                            case "btnAdd":
                            {
//                                startActivityForResult(new Intent(context, AddCardActivity.class), 1400);
                                startActivity(new Intent(context, AddCardActivity.class));
                                break;
                            }
                        }

                        layFunctionCard.setVisibility(View.GONE);
                        layEditCard.setVisibility(View.GONE);

                        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
//                        if (isFilterEnable)
//                        {
//                            Logger.e("getFilterId", idFilteredList + " #List size:" + filteredCategoryList.size());
//                            llDeleteFilter.setVisibility(View.VISIBLE);
//                        }
//                        else
//                        {
//                            Logger.e("getFilterId", "Empty, " + idFilteredList);
//                            llDeleteFilter.setVisibility(View.GONE);
//                            filteredCategoryList = new ArrayList<>();
//                        }
//                        if (filteredCategoryList.isEmpty())
//                        {
//                            filteredCategoryList = new ArrayList<>();
//                            for (TypeCategory item: typeCategoryList)
//                            {
//                                FilterItem filterItem = new FilterItem();
//                                filterItem.setId(item.getId());
//                                filterItem.setTitle(item.getTitle());
//                                filterItem.setChecked(false);
//
//                                filteredCategoryList.add(filterItem);
//                            }
//                            Collections.reverse(filteredCategoryList);
//                        }
//
//                        tempFilteredCategoryList = new ArrayList<>();
//                        tempFilteredCategoryList.addAll(filteredCategoryList);
//                        adapter = new FilterArchiveAdapter(getActivity(), tempFilteredCategoryList);
//                        adapter.notifyDataSetChanged();
//                        rcFilterCategory.setAdapter(adapter);
//                        rangeBar.setProgress(rangeLeft, rangeRight);
//                        adapter.SetOnItemCheckedChangeListener(this);
//                        rcFilterCategory.setLayoutManager(new GridLayoutManager(getActivity(), 5, RecyclerView.HORIZONTAL, true));
//
//                        //----------------------------------------------
//                        Logger.e("--rangeBar--", rangeBar.getMinProgress() + " , " + rangeBar.getMaxProgress());
//                        Logger.e("--rangeBar2--", rangeBar.getLeftSeekBar().getProgress() + " , " + rangeBar.getRightSeekBar().getProgress());
//                        Logger.e("--rangeBar4--", rangeLeft + " , " + rangeRight);
//                        //----------------------------------------------
                    })
            );

            slidingUpPanelLayout.setFadeOnClickListener(v ->
            {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                new Handler().postDelayed(() ->
                {
                    layFunctionCard.setVisibility(View.GONE);
                    layEditCard.setVisibility(View.GONE);

                }, 200);

                selectedCardBankItem = null;
            });

        }
        catch (Exception e)
        {
            Logger.e("-Exception-", "message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean setErrorEditCard()
    {
        boolean err = true;
        String message = "";
        if (edtNumberCardEdit.getText().toString().replaceAll("-", "").length() != 16)
        {
            message = message + "شماره کارت نامعتبر است." + '\n';
            err = false;
        }
        else if (edtNumberCardEdit.getText().toString().replaceAll("-", "").contains("*"))
        {
            message = message + "شماره کارت صحیح نمی باشد." + '\n';
            err = false;
        }
        else if (!Utility.CheckCartDigit(edtNumberCardEdit.getText().toString().replaceAll("-", "").trim()))
        {
            message = message + "شماره کارت صحیح نمی باشد." + '\n';
            err = false;
        }
        if (TextUtils.isEmpty(edtFullName.getText().toString()))
        {
            message = message + "نام و نام خانوادگی نمیتواند خالی باشد." + '\n';
            err = false;
        }
        else if (edtFullName.getText().toString().length() < 2 || Utility.containsNumber(edtFullName.getText().toString()))
        {
            message = message + "نام و نام خانوادگی نامعتبر است." + '\n';
            err = false;
        }

        if (!err)
        {
            showError(context, message);
        }

        return err;
    }

    private void getData(boolean isFiltered)
    {
        mainView.showLoading();

//        if (!isFiltered)
//        {
//            SingletonService.getInstance().getTransactionService().getTransactionList(this);
//        }
//        else
//        {
//            if (chbFailedPayment.isChecked() != chbSuccessPayment.isChecked())
//            {
//                boolean status = false;
//                if (chbSuccessPayment.isChecked())
//                {
//                    status = true;
//                }
//                else if (chbFailedPayment.isChecked())
//                {
//                    status = false;
//                }
//
//                SingletonService.getInstance().getTransactionService().getTransactionListByFilter(
//                        idFilteredList,
//                        minPrice,
//                        maxPrice,
//                        tvStartDate.getText().toString().trim().equalsIgnoreCase("") ? "" : Utility.getGrgDate(tvStartDate.getText().toString()),
//                        tvEndDate.getText().toString().trim().equalsIgnoreCase("") ? "" : Utility.getGrgDate(tvEndDate.getText().toString()),
//                        status,
////                        edtSearchText.getText().toString().trim(),
//                        this
//                );
//            }
//            else
//            {
//                SingletonService.getInstance().getTransactionService().getTransactionListByFilterForAllStatus(
//                        idFilteredList,
//                        minPrice,
//                        maxPrice,
//                        tvStartDate.getText().toString().trim().equalsIgnoreCase("") ? "" : Utility.getGrgDate(tvStartDate.getText().toString()),
//                        tvEndDate.getText().toString().trim().equalsIgnoreCase("") ? "" : Utility.getGrgDate(tvEndDate.getText().toString()),
////                        edtSearchText.getText().toString().trim(),
//                        this
//                );
//            }
//        }
    }

    private void showAlertAndFinish(String message)
    {
        try
        {
            MessageAlertDialog dialog = new MessageAlertDialog((Activity) context, getString(R.string.error), message,
                    false, "تایید", "", MessageAlertDialog.TYPE_ERROR, new MessageAlertDialog.OnConfirmListener()
            {
                @Override
                public void onConfirmClick()
                {
                    getActivity().onBackPressed();
                }

                @Override
                public void onCancelClick()
                {

                }
            });
            dialog.setCancelable(false);
            dialog.show(((Activity) context).getFragmentManager(), "dialog");
        }
        catch (Exception e)
        {

        }
    }


    @Subscribe
    public void getHeaderContent(HeaderModel headerModel)
    {
        if (headerModel.getPopularNo() != 0)
        {
            tvHeaderPopularNo.setText(String.valueOf(headerModel.getPopularNo()));
        }
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
    }

    @Subscribe
    public void addCard(CardBankItem cardBankItem)
    {
        if (cardBankList == null)
        {
            cardBankList = new ArrayList<>();
        }
        cardBankList.add(cardBankItem);
        adapter = new CardManagementAdapter(cardBankList, this);
        rcCardList.setAdapter(adapter);
    }

    @Override
    public void onReady(WebServiceClass<GetCardListResponse> response)
    {
        try
        {
            mainView.hideLoading();

            if (response.info.statusCode != 200)
            {
                showAlertAndFinish(response.info.message);
            }
            else
            {
                if (!response.data.getCardBankItems().isEmpty())
                {
                    cardBankList = response.data.getCardBankItems();
                    adapter = new CardManagementAdapter(cardBankList, this);
                    rcCardList.setAdapter(adapter);
                }
            }
        }
        catch (Exception e)
        {
            Logger.e("-Exception GetData-", e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void onError(String message)
    {
        mainView.hideLoading();
        if (Tools.isNetworkAvailable((Activity) context))
        {
            Logger.e("-OnError-", "Error: " + message);
            showAlertAndFinish("خطا در دریافت اطلاعات از سرور!");
        }
        else
        {
            showAlertAndFinish(getString(R.string.networkErrorMessage));
        }
    }

    @Override
    public void onMenuItemClick(int position, CardBankItem cardBankItem)
    {
        layFunctionCard.setVisibility(View.VISIBLE);
        layEditCard.setVisibility(View.GONE);
        selectedCardBankItem = cardBankItem;
        new Handler().postDelayed(() -> slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED), 200);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1400 && resultCode == Activity.RESULT_OK)
        {
            Gson gson = new Gson();
            CardBankItem cardBankItem = gson.fromJson(data.getStringExtra("CardBankItem"), CardBankItem.class);
            if (cardBankList == null)
            {
                cardBankList = new ArrayList<>();
            }
            cardBankList.add(cardBankItem);
            adapter = new CardManagementAdapter(cardBankList, this);
            rcCardList.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView()
    {
        disposable.clear();
        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        disposable.clear();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
