package ir.trap.tractor.android.ui.activities.main;

//import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.adapter.ImagePagerAdapter;
import ir.trap.tractor.android.ui.drawer.MenuDrawer;
import ir.trap.tractor.android.ui.fragments.main.MainFragment;
import ir.trap.tractor.android.utilities.Logger;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

//import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MenuDrawer.FragmentDrawerListener
{

    private Toolbar mToolbar;
    private MenuDrawer drawerFragment;

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;

    private Bundle mSavedInstanceState;

    private FragmentTransaction transaction;

    @Override
    protected void attachBaseContext(Context context)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSavedInstanceState = savedInstanceState;

        //--------------------------------bottomBar----------------------------------
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);

        fragmentManager = getSupportFragmentManager();
        fragment = MainFragment.newInstance();

        transaction = fragmentManager.beginTransaction();

        if (mSavedInstanceState == null)
        {
            transaction.add(R.id.main_container, fragment)
                    .commit();
        } else
        {
            transaction.replace(R.id.main_container, fragment)
                    .commit();
        }

//
        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem ->
        {
            Logger.e("--item--", menuItem.getTitle().toString());
            int itemId = menuItem.getItemId();
            //switch fragment
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
                    if (!bottomNavigationView.getMenu().getItem(2).isChecked())
                    {
                        setCheckedBNV(bottomNavigationView, 2);
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
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        try
        {
            if (mSavedInstanceState == null)
            {
//            transaction.add(R.id.main_container, fragment)
//                    .
            } else
            {
                transaction.replace(R.id.main_container, fragment)
                        .commit();
            }

        } catch (IllegalStateException e)
        {
            e.printStackTrace();
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
        } else
        {
            super.onBackPressed();
        }

    }

    @Override
    public void onDrawerItemSelected(View view, int position)
    {

    }
}
