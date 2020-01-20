package com.traap.traapapp.ui.activities.points;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.traap.traapapp.R;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.fragments.points.PointMainFragment;
import com.traap.traapapp.ui.fragments.traapMarket.MarketFragment;

public class PointsActivity extends BaseActivity
{
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        fragmentManager = getSupportFragmentManager();
        //set fragment in container id
        fragment = PointMainFragment.newInstance();
        transaction = fragmentManager.beginTransaction();

        transaction.add(R.id.container, fragment, "pointsFragment")
                .commit();
    }
}
