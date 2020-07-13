package com.traap.traapapp.ui.fragments.charity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.charity.CharityModel;
import com.traap.traapapp.apiServices.model.news.main.ImageName;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.adapters.charity.CharityListAdapter;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


@SuppressLint("ValidFragment")
public class CharityFragment extends BaseFragment implements CharityListAdapter.OnCharityItemClickListener, TextWatcher
{
    private View rootView;
    private Context context;
    private MainActionView mainView;
    private RecyclerView recyclerView;
    private CircularProgressButton btnConfirm;
    private ImageView imgCharityClose, imageLogo;
    private ProgressBar progress;
    private TextView tvTitlePanel, tvSubTitlePanel, tvPriceTitlePanel;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private EditText edtPricePanel;

    private List<CharityModel> charityList;

    private CharityListAdapter adapter;


    private Toolbar mToolbar;


    public CharityFragment()
    {

    }

    public static CharityFragment newInstance(MainActionView mainView)
    {
        CharityFragment f = new CharityFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_charity, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView ->
        {
            if (slidingUpPanelLayout.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED)
            {
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
            else
            {
                getActivity().onBackPressed();
            }
        });
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("نیکوکاری");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        FrameLayout flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
        flLogoToolbar.setOnClickListener(v -> {
            mainView.backToMainFragment();
        });

        initView();

        return rootView;
    }


    public void initView()
    {
        slidingUpPanelLayout = rootView.findViewById(R.id.slidingLayout);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        btnConfirm = rootView.findViewById(R.id.btnConfirm);
        tvTitlePanel = rootView.findViewById(R.id.tvTitlePanel);
        tvSubTitlePanel = rootView.findViewById(R.id.tvSubTitlePanel);
        tvPriceTitlePanel = rootView.findViewById(R.id.tvPriceTitlePanel);
        imageLogo = rootView.findViewById(R.id.imageLogo);
        imgCharityClose = rootView.findViewById(R.id.imgCharityClose);
        progress = rootView.findViewById(R.id.progress);
        edtPricePanel = rootView.findViewById(R.id.edtPricePanel);

        edtPricePanel.addTextChangedListener(this);

        btnConfirm.setText("ادامه");

        slidingUpPanelLayout.setFadeOnClickListener(v ->
        {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            edtPricePanel.setText("");
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        //-----------------for test--------------------
        charityList = new ArrayList<>();
        for (int i=0; i<3; i++)
        {
            CharityModel model = new CharityModel();
            model.setCharityId(0);
            model.setTitle("سازمان بهزیستی کشور");
            model.setSubTitle("محور مسئولیت و سلامت اجتماعی");
            ImageName imageName = new ImageName();
            imageName.setThumbnailLarge("");
            imageName.setThumbnailSmall("");
            model.setImageUrl(imageName);
            charityList.add(model);
        }
        //-----------------for test--------------------
        recyclerView.setAdapter(new CharityListAdapter(context, charityList, this));

        btnConfirm.setOnClickListener(view ->
        {

        });
    }

    @Override
    public void onCharityItemClick(CharityModel charityModel)
    {
        tvSubTitlePanel.setText(charityModel.getSubTitle());

        tvTitlePanel.setText(charityModel.getTitle());

        if (charityModel.getImageUrl() != null)
        {
            setImageBackground(progress, imageLogo, charityModel.getImageUrl().getThumbnailSmall());
        }

        tvPriceTitlePanel.setText(new StringBuilder("جهت پرداخت به ")
                .append(charityModel.getTitle())
                .append("، مبلغ مورد نظرتان را وارد نمایید.")
        );
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    private void setImageBackground(ProgressBar progress, ImageView imageView, String link)
    {
        try
        {
            Picasso.with(context).load(Uri.parse(link)).into(imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onError()
                {
                    Picasso.with(context).load(R.drawable.img_failure).into(imageView);
                    progress.setVisibility(View.GONE);
                }
            });
        }
        catch (NullPointerException e)
        {
            Picasso.with(context).load(R.drawable.img_failure).into(imageView);
            progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
    {

    }

    @Override
    public void afterTextChanged(Editable editable)
    {
        edtPricePanel.removeTextChangedListener(this);

        String s = edtPricePanel.getText().toString();

        s = s.replace(",", "");
        if (s.length() > 0)
        {
            DecimalFormat sdd = new DecimalFormat("#,###");
            Double doubleNumber = Double.parseDouble(s);

            String format = sdd.format(doubleNumber);
            edtPricePanel.setText(format);
            edtPricePanel.setSelection(format.length());

        }
        edtPricePanel.addTextChangedListener(this);

//        txtChrAmount.setText(ConvertPersianNumberToString.getNumberConvertToString(BigDecimal.valueOf(Integer.parseInt(edtPrice.getText().toString().replaceAll(",", ""))), "ریال"));
    }
}
