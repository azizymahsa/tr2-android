package ir.trap.tractor.android.ui.activities.main;

//import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.getMenu.request.GetMenuRequest;
import ir.trap.tractor.android.apiServices.model.getMenu.response.GetMenuItemResponse;
import ir.trap.tractor.android.apiServices.model.getMenu.response.GetMenuResponse;
import ir.trap.tractor.android.conf.TrapConfig;
import ir.trap.tractor.android.singleton.SingletonContext;
import ir.trap.tractor.android.ui.base.BaseActivity;
import ir.trap.tractor.android.ui.drawer.MenuDrawer;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;
import ir.trap.tractor.android.ui.fragments.main.MainFragment;
import ir.trap.tractor.android.ui.fragments.moneyTransfer.MoneyTransferFragment;
import ir.trap.tractor.android.ui.fragments.simcardCharge.ChargeFragment;
import ir.trap.tractor.android.ui.fragments.simcardPack.PackFragment;
import ir.trap.tractor.android.utilities.Logger;

public class MainActivity extends BaseActivity implements MainActionView, MenuDrawer.FragmentDrawerListener,
        OnServiceStatus<WebServiceClass<GetMenuResponse>>
{
    private Boolean isMainFragment = true;

    private Toolbar mToolbar;
    private MenuDrawer drawerFragment;

    private Fragment curentFragment;
    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;

    private Bundle mSavedInstanceState;

    private FragmentTransaction transaction;

    private ArrayList<GetMenuItemResponse> footballServiceList, chosenServiceList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSavedInstanceState = savedInstanceState;

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        drawerFragment = (MenuDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_menudrawer);
        drawerFragment.setUp(R.id.fragment_navigation_menudrawer, findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        ImageButton btnDrawer = mToolbar.findViewById(R.id.imgMenu);

        btnDrawer.setOnClickListener(v ->
        {
            View containerView = findViewById(R.id.fragment_navigation_menudrawer);
            DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
            mDrawerLayout.openDrawer(containerView);

        });


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem ->
        {
            Logger.e("--item--", menuItem.getTitle().toString());
            int itemId = menuItem.getItemId();
            //switch curentFragment
            switch (itemId)
            {
                case R.id.tab_shop:
                {
                    if (!bottomNavigationView.getMenu().getItem(0).isChecked())
                    {
                        setCheckedBNV(bottomNavigationView, 0);
                    }
                    break;
                }
                case R.id.tab_all_services:
                {
                    if (!bottomNavigationView.getMenu().getItem(1).isChecked())
                    {
                        setCheckedBNV(bottomNavigationView, 1);
                    }
                    break;
                }
                case R.id.tab_home:
                {
                    if (!bottomNavigationView.getMenu().getItem(2).isChecked() || !isMainFragment)
                    {
                        setCheckedBNV(bottomNavigationView, 2);

                        backToMainFragment();
                    }
                    break;
                }
                case R.id.tab_media:
                {
                    if (!bottomNavigationView.getMenu().getItem(3).isChecked())
                    {
                        setCheckedBNV(bottomNavigationView, 3);
                    }
                    break;
                }
                case R.id.tab_profile:
                {
                    if (!bottomNavigationView.getMenu().getItem(4).isChecked())
                    {
                        setCheckedBNV(bottomNavigationView, 4);
                    }
                    break;
                }
            }
            return true;
        });
        //--------------------------------bottomBar----------------------------------
        GetMenuRequest request = new GetMenuRequest();
        request.setDeviceType(TrapConfig.AndroidDeviceType);
        request.setDensity(SingletonContext.getInstance().getContext().getResources().getDisplayMetrics().density);
        SingletonService.getInstance().getMenuService().getMenu(this, request);
//
//        fragmentManager = getSupportFragmentManager();
//        curentFragment = MainFragment.newInstance(this, chosenServiceList, footballServiceList);
//
//        transaction = fragmentManager.beginTransaction();
//
//        if (mSavedInstanceState == null)
//        {
//            transaction.add(R.id.main_container, curentFragment)
//                    .commit();
//        }
//        else
//        {
//            transaction.replace(R.id.main_container, curentFragment)
//                    .commit();
//        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (curentFragment == null)
        {
//            curentFragment = MainFragment.newInstance(this, chosenServiceList, footballServiceList);
//            transaction = fragmentManager.beginTransaction();
//            try
//            {
//                if (mSavedInstanceState == null)
//                {
//                    transaction.add(R.id.main_container, curentFragment)
//                            .commit();
////
//                } else
//                {
//                    transaction.replace(R.id.main_container, curentFragment)
//                            .commit();
//                }
//            }
//            catch (IllegalStateException e)
//            {
//                e.printStackTrace();
//            }

        }
    }

    private void setCheckedBNV(BottomNavigationView bottomNavigationView, int index)
    {
        for (int i = 0; i < 5; i++)
        {
            if (i == index)
            {
                bottomNavigationView.getMenu().getItem(index).setChecked(true);
            } else
            {
                bottomNavigationView.getMenu().getItem(index).setChecked(false);
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END))
        {
            drawer.closeDrawer(GravityCompat.END);
        }
        else
        {
            if (isMainFragment)
            {
                super.onBackPressed();
            }
            else
            {
                backToMainFragment();
            }
        }

    }

    @Override
    public void onDrawerItemSelected(View view, int position)
    {

    }

    @Override
    public void showLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.GONE);
    }

    @Override
    public void onBill()
    {

    }

    @Override
    public void onChargeSimCard()
    {
        isMainFragment = false;

        curentFragment = ChargeFragment.newInstance(this);
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.main_container, curentFragment)
                .commit();
    }

    @Override
    public void onPackSimCard()
    {
        isMainFragment = false;

        curentFragment = PackFragment.newInstance(this);
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.main_container, curentFragment)
                .commit();
    }

    @Override
    public void doTransferMoney()
    {
        isMainFragment = false;

        curentFragment = MoneyTransferFragment.newInstance(this);
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.main_container, curentFragment)
                .commit();
    }

    @Override
    public void onContact()
    {

    }

    @Override
    public void onInternetAlert()
    {

    }

    @Override
    public void showError(String message)
    {
        showToast(this, message, R.color.red);
    }

    @Override
    public void backToMainFragment()
    {
        isMainFragment = true;

        curentFragment = MainFragment.newInstance(this, footballServiceList, chosenServiceList);
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.main_container, curentFragment)
                .commit();
    }

    @Override
    public void onReady(WebServiceClass<GetMenuResponse> response)
    {
        if (response.statusCode == 200)
        {
            chosenServiceList = response.data.getChosenServiceList();
            footballServiceList = response.data.getFootballServiceList();
        }

        Logger.e("--List size--", "chosenServiceList: " + chosenServiceList.size() +
                "footballServiceList: " + footballServiceList.size());

        fragmentManager = getSupportFragmentManager();
        curentFragment = MainFragment.newInstance(this, footballServiceList, chosenServiceList);

        transaction = fragmentManager.beginTransaction();

        if (mSavedInstanceState == null)
        {
            transaction.add(R.id.main_container, curentFragment)
                    .commit();
        }
        else
        {
            transaction.replace(R.id.main_container, curentFragment)
                    .commit();
        }
    }

    @Override
    public void onError(String message)
    {

    }
}
