package ir.traap.tractor.android.ui.fragments.videos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.ui.activities.main.MainActivity;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;

/**
 * Created by MahtabAzizi on 11/30/2019.
 */
public class VideosMainFragment  extends BaseFragment
{

    private View rootView;
    private MainActionView mainView;

    private Toolbar mToolbar;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    public VideosMainFragment()
    {

    }

    public static VideosMainFragment newInstance(MainActionView mainView)
    {
        VideosMainFragment f = new VideosMainFragment();
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
        rootView = inflater.inflate(R.layout.fragment_videos_main, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(v -> mainView.openDrawer());
        mToolbar.findViewById(R.id.imgBack).setOnClickListener(rootView -> mainView.backToMainFragment());
        TextView tvUserName = mToolbar.findViewById(R.id.tvUserName);
        TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
        tvTitle.setText("فیلم");
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);

        fragmentManager = getChildFragmentManager();

        fragment = VideosFragment.newInstance(  mainView);
        transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.container, fragment, "videosFragment")
                .commit();


        return rootView;
    }

}
