package ir.traap.tractor.android.ui.fragments.myProfile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.pixplicity.easyprefs.library.Prefs;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.singleton.SingletonContext;
import ir.traap.tractor.android.ui.activities.userProfile.UserProfileActionProfileActivity;
import ir.traap.tractor.android.ui.activities.userProfile.UserProfileActionView;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.dialogs.MessageAlertDialog;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;


@SuppressLint("ValidFragment")
public class MyProfileFragment extends BaseFragment
{
    private View rootView;
    private MainActionView mainView;

    private Toolbar mToolbar;
    private TextView tvFullName, tvMobile, tvInviteCode;

    private ImageView imgEditProfile;

    private RelativeLayout rlMyPredict, rlSoccerFavorite, rlMyShirt;


    public MyProfileFragment()
    {

    }

    public static MyProfileFragment newInstance(MainActionView mainView)
    {
        MyProfileFragment f = new MyProfileFragment();
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
        rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> mainView.backToMainFragment());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("حساب کاربری من");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        initView();

        return rootView;
    }


    public void initView()
    {
        tvFullName = rootView.findViewById(R.id.tvFullName);
        tvMobile = rootView.findViewById(R.id.tvMobile);
        tvInviteCode = rootView.findViewById(R.id.tvInviteCode);
        imgEditProfile = rootView.findViewById(R.id.imgEditProfile);
        rlMyPredict = rootView.findViewById(R.id.rlMyPredict);
        rlSoccerFavorite = rootView.findViewById(R.id.rlSoccerFavorite);
        rlMyShirt = rootView.findViewById(R.id.rlMyShirt);

        tvFullName.setText(Prefs.getString("FULLName", ""));
        tvMobile.setText(Prefs.getString("mobile", ""));
        tvInviteCode.setText(Prefs.getString("keyInvite", ""));

        imgEditProfile.setOnClickListener(v ->
        {
            startActivity(new Intent(SingletonContext.getInstance().getContext(), UserProfileActionProfileActivity.class));
        });

        rlMyPredict.setOnClickListener(v ->
        {

        });

        rlSoccerFavorite.setOnClickListener(v ->
        {

        });

        rlMyShirt.setOnClickListener(v ->
        {

        });

    }

}
