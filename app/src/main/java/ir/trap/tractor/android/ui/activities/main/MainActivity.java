package ir.trap.tractor.android.ui.activities.main;

//import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
//import android.support.v7.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.ui.drawer.MenuDrawer;
import ir.trap.tractor.android.utilities.Logger;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements MenuDrawer.FragmentDrawerListener
{

    private Toolbar mToolbar;
    private MenuDrawer drawerFragment;

    private Fragment fragment;
    private FragmentManager fragmentManager;

    private BottomNavigationView bottomNavigationView;


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

        //--------------------------------bottomBar----------------------------------
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);

        fragmentManager = getSupportFragmentManager();
//        fragment = new MainFragment();

        final FragmentTransaction transaction = fragmentManager.beginTransaction();

//        if (savedInstanceState == null)
//        {
//            transaction.add(R.id.main_container, fragment)
//                    .commit();
//        }
//        else
//        {
//            transaction.replace(R.id.main_container, fragment)
//                    .commit();
//        }

//
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
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
                            bottomNavigationView.getMenu().getItem(0).setChecked(true);
                            bottomNavigationView.getMenu().getItem(1).setChecked(false);
                            bottomNavigationView.getMenu().getItem(2).setChecked(false);
                            bottomNavigationView.getMenu().getItem(3).setChecked(false);
                            bottomNavigationView.getMenu().getItem(4).setChecked(false);
                        }
                        break;
                    }
                    case R.id.tab_all_services:
                    {
                        if (!bottomNavigationView.getMenu().getItem(1).isChecked())
                        {
                            bottomNavigationView.getMenu().getItem(0).setChecked(false);
                            bottomNavigationView.getMenu().getItem(1).setChecked(true);
                            bottomNavigationView.getMenu().getItem(2).setChecked(false);
                            bottomNavigationView.getMenu().getItem(3).setChecked(false);
                            bottomNavigationView.getMenu().getItem(4).setChecked(false);
                        }
                        break;
                    }
                    case R.id.tab_home:
                    {
                        if (!bottomNavigationView.getMenu().getItem(2).isChecked())
                        {
                            bottomNavigationView.getMenu().getItem(0).setChecked(false);
                            bottomNavigationView.getMenu().getItem(1).setChecked(false);
                            bottomNavigationView.getMenu().getItem(2).setChecked(true);
                            bottomNavigationView.getMenu().getItem(3).setChecked(false);
                            bottomNavigationView.getMenu().getItem(4).setChecked(false);
                        }
                        break;
                    }
                    case R.id.tab_media:
                    {
                        if (!bottomNavigationView.getMenu().getItem(3).isChecked())
                        {
                            bottomNavigationView.getMenu().getItem(0).setChecked(false);
                            bottomNavigationView.getMenu().getItem(1).setChecked(false);
                            bottomNavigationView.getMenu().getItem(2).setChecked(false);
                            bottomNavigationView.getMenu().getItem(3).setChecked(true);
                            bottomNavigationView.getMenu().getItem(4).setChecked(false);
                        }
                        break;
                    }
                    case R.id.tab_profile:
                    {
                        if (!bottomNavigationView.getMenu().getItem(4).isChecked())
                        {
                            bottomNavigationView.getMenu().getItem(0).setChecked(false);
                            bottomNavigationView.getMenu().getItem(1).setChecked(false);
                            bottomNavigationView.getMenu().getItem(2).setChecked(false);
                            bottomNavigationView.getMenu().getItem(3).setChecked(false);
                            bottomNavigationView.getMenu().getItem(4).setChecked(true);
                        }
                        break;
                    }
                }
                return true;
            }
        });


        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        drawerFragment = (MenuDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_menudrawer);
        drawerFragment.setUp(R.id.fragment_navigation_menudrawer, findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        ImageButton btnDrawer = mToolbar.findViewById(R.id.imgMenu);
        btnDrawer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                View containerView = findViewById(R.id.fragment_navigation_menudrawer);
                DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
                mDrawerLayout.openDrawer(containerView);

            }
        });
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
            super.onBackPressed();
        }

    }

    @Override
    public void onDrawerItemSelected(View view, int position)
    {

    }
}
