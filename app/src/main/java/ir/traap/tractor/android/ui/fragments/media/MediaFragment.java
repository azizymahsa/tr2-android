package ir.traap.tractor.android.ui.fragments.media;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.allService.response.SubMenu;
import ir.traap.tractor.android.apiServices.model.getDecQrCode.DecryptQrRequest;
import ir.traap.tractor.android.apiServices.model.getDecQrCode.DecryptQrResponse;
import ir.traap.tractor.android.apiServices.model.news.category.response.NewsArchiveCategory;
import ir.traap.tractor.android.apiServices.model.paymentPrintPos.PaymentPrintPosRequest;
import ir.traap.tractor.android.apiServices.model.paymentPrintPos.PaymentPrintPosResponse;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.enums.BarcodeType;
import ir.traap.tractor.android.models.otherModels.mainService.MainServiceModelItem;
import ir.traap.tractor.android.models.otherModels.mediaModel.MediaModel;
import ir.traap.tractor.android.singleton.SingletonContext;
import ir.traap.tractor.android.ui.adapters.allMenu.AllMenuServiceModelAdapter;
import ir.traap.tractor.android.ui.adapters.media.MediaAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.dialogs.MessageAlertDialog;
import ir.traap.tractor.android.ui.dialogs.PaymentResultDialog;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.ui.fragments.news.NewsActionView;
import ir.traap.tractor.android.ui.fragments.news.archive.NewsArchiveCategoryFragment;
import ir.traap.tractor.android.ui.fragments.news.archive.NewsArchiveFragment;
import ir.traap.tractor.android.ui.fragments.traapMarket.MarketFragment;
import ir.traap.tractor.android.ui.fragments.videos.VideosFragment;
import ir.traap.tractor.android.utilities.Logger;
import ir.traap.tractor.android.utilities.NumberTextWatcher;
import ir.traap.tractor.android.utilities.Utility;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;


@SuppressLint("ValidFragment")
public class MediaFragment extends BaseFragment implements MediaAdapter.OnItemAllMenuClickListener, NewsActionView
{
    private View rootView;
    private MainActionView mainView;

    private Toolbar mToolbar;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MediaAdapter adapter;

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    public MediaFragment()
    {

    }

    public static MediaFragment newInstance(MainActionView mainView)
    {
        MediaFragment f = new MediaFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_media, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> mainView.backToMainFragment());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
       // tvTitle.setText("رسانه");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        initView();

        return rootView;
    }


    public void initView()
    {

        fragmentManager = getChildFragmentManager();

//        MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), "", "این سرویس بزودی راه اندازی میگردد", false,
//                new MessageAlertDialog.OnConfirmListener()
//                {
//                    @Override
//                    public void onConfirmClick()
//                    {
//                        mainView.backToMainFragment();
//                    }
//
//                    @Override
//                    public void onCancelClick()
//                    {
//
//                    }
//                });
//        dialog.setCancelable(false);
//        dialog.show(getActivity().getFragmentManager(), "messageDialog");

        recyclerView = rootView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutManager);

        List<MediaModel> list = new ArrayList<>();

        MediaModel item = new MediaModel();
        item.setId(1);
        item.setTitle("اخبار");
        item.setIconDrawable(R.drawable.ic_news);
        item.setIconDrawableSelected(R.drawable.ic_news_selected);
        list.add(item);

        item = new MediaModel();
        item.setId(2);
        item.setTitle("عکس");
        item.setIconDrawable(R.drawable.ic_image);
        item.setIconDrawableSelected(R.drawable.ic_image_selected);
        list.add(item);

        item = new MediaModel();
        item.setId(3);
        item.setTitle("فیلم");
        item.setIconDrawable(R.drawable.ic_movie);
        item.setIconDrawableSelected(R.drawable.ic_movie_selected);
        list.add(item);

        adapter = new MediaAdapter(getActivity(), list, MediaFragment.this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void OnItemAllMenuClick(View view, Integer id)
    {
        switch (id)
        {
            case 1://اخبار
            {
                fragment = NewsArchiveFragment.newInstance(this);
                transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                transaction.replace(R.id.main_container, fragment, "newsArchiveCategoryFragment")
                        .commit();
                break;
            }
            case 2://عکس
            {
//                fragment = MarketFragment.newInstance(this);
//                transaction = fragmentManager.beginTransaction();
////                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//
//                transaction.replace(R.id.main_container, fragment, "marketFragment")
//                        .commit();
                break;
            }
            case 3://فیلم
            {
                fragment = VideosFragment.newInstance(mainView);
               transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                transaction.replace(R.id.main_container, fragment, "videosFragment")
                        .commit();
                break;
            }
        }
    }

    @Override
    public void backToMainNewsFragment()
    {

    }

    @Override
    public void openDrawer()
    {

    }

    @Override
    public void closeDrawer()
    {

    }

    @Override
    public void showLoading()
    {

    }

    @Override
    public void hideLoading()
    {

    }
}
