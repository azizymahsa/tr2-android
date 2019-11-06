package ir.traap.tractor.android.ui.fragments.about;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.pixplicity.easyprefs.library.Prefs;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import ir.traap.tractor.android.R;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;


public class AboutFragment
        extends BaseFragment implements View.OnClickListener
{


    private View view;
    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName,tvPhone;
    private View imgBack, imgMenu;
    private MainActionView mainView;
    private ImageView ivInsta, ivTwit, ivTele;
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
            ivTwit = view.findViewById(R.id.ivTwit);
            ivTele = view.findViewById(R.id.ivTele);
            ivInsta = view.findViewById(R.id.ivInsta);
            btnHistory = view.findViewById(R.id.btnHistory);
            tvPhone = view.findViewById(R.id.tvPhone);

            ivTwit.setOnClickListener(this);
            ivTele.setOnClickListener(this);
            ivInsta.setOnClickListener(this);
            btnHistory.setOnClickListener(this);
            tvPhone.setOnClickListener(this);
            //toolbar
            tvTitle = view.findViewById(R.id.tvTitle);
            tvTitle.setText("درباره ما");
            imgMenu=view.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
            imgBack = view.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });


        } catch (Exception e)
        {
            e.getMessage();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.about_fragment, container, false);
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
        mToolbar = view.findViewById(R.id.toolbar);
        tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("درباره تراپ");
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(v -> mainView.backToMainFragment());

        tvUserName.setText(Prefs.getString("mobile", ""));

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mainView.openDrawer();
            }
        });

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
            case R.id.btnHistory:
                HistoryFragment historyFragment = new HistoryFragment(mainView);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, historyFragment).commit();
                //mainView.onPaymentWithoutCard();
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

