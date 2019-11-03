package ir.trap.tractor.android.ui.fragments.about;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getHistory.PlayersCurrent;
import ir.trap.tractor.android.apiServices.model.getHistory.ResponseHistory;
import ir.trap.tractor.android.ui.activities.main.MainActivity;
import ir.trap.tractor.android.ui.adapters.aboutUs.ExpandableListHistoryAdapter;
import ir.trap.tractor.android.ui.adapters.aboutUs.ExpandableListPlayerHistoryAdapter;
import ir.trap.tractor.android.ui.adapters.aboutUs.NoScrollExListView;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;


public class AboutFragment
        extends Fragment implements View.OnClickListener
{


    private View view;
    private Toolbar mToolbar;
    private TextView tvUserName,tvPhone;
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
        tvUserName = view.findViewById(R.id.tvUserName);

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
                if (tvPhone.getText().toString() == null) {//44890412
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

