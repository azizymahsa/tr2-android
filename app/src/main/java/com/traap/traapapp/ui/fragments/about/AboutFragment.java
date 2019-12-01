package com.traap.traapapp.ui.fragments.about;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.pixplicity.easyprefs.library.Prefs;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;


public class AboutFragment
        extends BaseFragment implements View.OnClickListener
{


    private View view;
    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName, tvPhone, tvPopularPlayer;
    private View imgBack, imgMenu;
    private MainActionView mainView;
    private ImageView ivInsta, ivTwit, ivTele, ivWeb;
    private CircularProgressButton btnHistory;


    public static AboutFragment newInstance(MainActionView mainView)
    {
        AboutFragment f = new AboutFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    public AboutFragment()
    {
    }


    public static AboutFragment newInstance()
    {
        AboutFragment fragment = new AboutFragment();


        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }


    private void initViews()
    {
        try
        {
            ivWeb = view.findViewById(R.id.ivWeb);
            ivTwit = view.findViewById(R.id.ivTwit);
            ivTele = view.findViewById(R.id.ivTele);
            ivInsta = view.findViewById(R.id.ivInsta);
            btnHistory = view.findViewById(R.id.btnHistory);
            tvPhone = view.findViewById(R.id.tvPhone);

            ivWeb.setOnClickListener(this);
            ivTwit.setOnClickListener(this);
            ivTele.setOnClickListener(this);
            ivInsta.setOnClickListener(this);
            btnHistory.setOnClickListener(this);
            tvPhone.setOnClickListener(this);
            //toolbar
            mToolbar = view.findViewById(R.id.toolbar);
            tvUserName = mToolbar.findViewById(R.id.tvUserName);
            TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
            tvTitle.setText("درباره تراپ");
            mToolbar.findViewById(R.id.imgBack).setOnClickListener(v -> mainView.backToMainFragment());

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mainView.openDrawer();
                }
            });

            imgMenu = view.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
            imgBack = view.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", "12"));
        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_about, container, false);
        PackageInfo pInfo = null;
        try
        {
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        ((TextView) view.findViewById(R.id.tvVersion)).setText("نسخه " + pInfo.versionName);

        // initializing the views
        initViews();


        return view;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {

            case R.id.ivWeb:

                try
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.traap.ir"));
                    startActivity(browserIntent);
                } catch (Exception e)
                {
                    e.getMessage();
                }
                break;
            case R.id.ivTwit:
                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + "traapapp")));
                } catch (Exception e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + "traapapp")));
                }
                break;
            case R.id.ivTele:
                try
                {
                    Intent telegram = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/@traapapp"));
                    startActivity(telegram);
                } catch (Exception e)
                {
                    e.getMessage();
                }
                break;
            case R.id.ivInsta:
                Uri uri = Uri.parse("http://instagram.com/traapapp");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try
                {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/traapapp")));
                }
                break;
            case R.id.btnHistory:
                HistoryFragment historyFragment = new HistoryFragment(mainView);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, historyFragment).commit();
                break;
            case R.id.tvPhone:
                if (tvPhone.getText().toString() == null)
                {//44890412
                    Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "021-44890412", null));
                    startActivity(intent2);
                } else
                {
                    Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "021-44890412", null));
                    startActivity(intent2);
                }/*else {
                    String[] phone = tvPhone.getText().toString().split("\r\n");
                    String phoneCall = phone[0];
                    Log.e("phone", phoneCall+"" );
                    Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",phoneCall, null));
                    startActivity(intent2);
                }*/
                break;

        }
    }
}

