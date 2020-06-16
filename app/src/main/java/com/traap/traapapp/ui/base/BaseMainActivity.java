package com.traap.traapapp.ui.base;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class BaseMainActivity extends BaseActivity
{
    protected List<Fragment> fragmentList = new ArrayList<>();

    @Getter @Setter
    public Fragment fragment, mainFragment;

    @Getter @Setter
    public FragmentManager myFragmentManager;

    @Getter @Setter
    public FragmentTransaction transaction;

    @Getter @Setter
    public   @IdRes int containerViewId;


    public void addFragment(Fragment fragment, String tag)
    {
        transaction = myFragmentManager.beginTransaction();
        transaction.add(getContainerViewId(), fragment, tag)
                .commitAllowingStateLoss();
        fragmentList.add(fragment);
    }

    public void replaceFragment(Fragment fragment, String tag)
    {
        transaction = myFragmentManager.beginTransaction();
        transaction.replace(getContainerViewId(), fragment, tag)
                .commitAllowingStateLoss();
        fragmentList.add(fragment);
    }

    public void backToParentFragment()
    {
        if (fragmentList.size() > 2)
        {
            fragmentList.remove(fragmentList.size() - 1);
            Fragment lastFragment = fragmentList.get(fragmentList.size() - 1);
            transaction = myFragmentManager.beginTransaction();
            transaction.replace(getContainerViewId(), lastFragment, lastFragment.getTag())
                    .commitAllowingStateLoss();
        }
        else if (fragmentList.size() == 2)
        {
            fragmentList.remove(fragmentList.size() - 1);
            transaction = myFragmentManager.beginTransaction();
            transaction.replace(getContainerViewId(), getMainFragment(), "mainFragment")
                    .commitAllowingStateLoss();
        }
    }

    public void removeAndBackToMainFragment()
    {
        fragmentList = new ArrayList<>();
        fragmentList.add(getMainFragment());

        transaction = myFragmentManager.beginTransaction();
        transaction.replace(getContainerViewId(), getMainFragment(), "mainFragment")
                .commitAllowingStateLoss();
    }
}
