package ir.trap.tractor.android.ui.activities.main;

//import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.contact.OnSelectContact;
import ir.trap.tractor.android.apiServices.model.getMenu.request.GetMenuRequest;
import ir.trap.tractor.android.apiServices.model.getMenu.response.GetMenuItemResponse;
import ir.trap.tractor.android.apiServices.model.getMenu.response.GetMenuResponse;
import ir.trap.tractor.android.conf.TrapConfig;
import ir.trap.tractor.android.singleton.SingletonContext;
import ir.trap.tractor.android.ui.activities.login.LoginActivity;
import ir.trap.tractor.android.ui.base.BaseActivity;
import ir.trap.tractor.android.ui.drawer.MenuDrawer;
import ir.trap.tractor.android.ui.fragments.allMenu.AllMenuFragment;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;
import ir.trap.tractor.android.ui.fragments.main.MainFragment;
import ir.trap.tractor.android.ui.fragments.moneyTransfer.MoneyTransferFragment;
import ir.trap.tractor.android.ui.fragments.simcardCharge.ChargeFragment;
import ir.trap.tractor.android.ui.fragments.simcardPack.PackFragment;
import ir.trap.tractor.android.utilities.Logger;
import library.android.eniac.utility.CustomAlert;

public class MainActivity extends BaseActivity implements MainActionView, MenuDrawer.FragmentDrawerListener,
        OnServiceStatus<WebServiceClass<GetMenuResponse>>
{
    private Boolean isMainFragment = true;

    private Toolbar mToolbar;
    private MenuDrawer drawerFragment;

    private Fragment currentFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private BottomNavigationView bottomNavigationView;

    private Bundle mSavedInstanceState;

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

//        ImageButton btnDrawer = mToolbar.findViewById(R.id.imgMenu);
//
//        btnDrawer.setOnClickListener(v ->
//        {
//            View containerView = findViewById(R.id.fragment_navigation_menudrawer);
//            DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
//            mDrawerLayout.openDrawer(containerView);
//
//        });


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem ->
        {
            Logger.e("--item--", menuItem.getTitle().toString());
            int itemId = menuItem.getItemId();
            //switch currentFragment
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
                    isMainFragment = false;

                    currentFragment = AllMenuFragment.newInstance(this);
                    //  transaction = fragmentManager.beginTransaction();
                    transaction = getSupportFragmentManager().beginTransaction();

                    transaction.replace(R.id.main_container, currentFragment)
                            .commit();
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
//        currentFragment = MainFragment.newInstance(this, chosenServiceList, footballServiceList);
//
//        transaction = fragmentManager.beginTransaction();
//
//        if (mSavedInstanceState == null)
//        {
//            transaction.add(R.id.main_container, currentFragment)
//                    .commit();
//        }
//        else
//        {
//            transaction.replace(R.id.main_container, currentFragment)
//                    .commit();
//        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (currentFragment == null)
        {
//            currentFragment = MainFragment.newInstance(this, chosenServiceList, footballServiceList);
//            transaction = fragmentManager.beginTransaction();
//            try
//            {
//                if (mSavedInstanceState == null)
//                {
//                    transaction.add(R.id.main_container, currentFragment)
//                            .commit();
////
//                } else
//                {
//                    transaction.replace(R.id.main_container, currentFragment)
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
        } else
        {
            if (isMainFragment)
            {
                super.onBackPressed();
            } else
            {
                backToMainFragment();
            }
        }

    }

    @Override
    public void onDrawerItemSelected(View view, int itemNumber)
    {
        final Intent intent = new Intent();
        switch (itemNumber)
        {
            case 7:
            {
                CustomAlert ca = new CustomAlert(this);
                ca.setCustomTitle(R.string.app_name);
                ca.setCustomMessage("آیا می خواهید از حساب کاربری خود خارج شوید؟");
                ca.setPositiveButton("خروج", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Prefs.clear();
                        finish();
                        intent.setClass(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });
                ca.setNegativeButton("انصراف", null);
                ca.create();
                ca.show();

                return;

            }

        }
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

        currentFragment = ChargeFragment.newInstance(this);

        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.main_container, currentFragment)
                .commit();

    }

    @Override
    public void onPackSimCard()
    {
        isMainFragment = false;

        currentFragment = PackFragment.newInstance(this);
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.main_container, currentFragment)
                .commit();
    }

    @Override
    public void doTransferMoney()
    {
        isMainFragment = false;

        currentFragment = MoneyTransferFragment.newInstance(this);
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.main_container, currentFragment)
                .commit();
    }

    @Override
    public void onContact()
    {
        new TedPermission(SingletonContext.getInstance().getContext())
                .setPermissionListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted()
                    {

                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, 8080);
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions)
                    {
                    }
                })
                .setPermissions(Manifest.permission.READ_CONTACTS)
                .check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK && requestCode == 8080)
        {

            Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
            while (cursor.moveToNext())
            {
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if (hasPhone.equalsIgnoreCase("1"))
                    hasPhone = "true";
                else
                    hasPhone = "false";

                if (Boolean.parseBoolean(hasPhone))
                {
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (phones.moveToNext())
                    {

                        OnSelectContact onSelectContact =new OnSelectContact();
                        onSelectContact.setName( phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) == null ? "" : phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                        onSelectContact.setNumber(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll(" ", "").replace("0098", "0").replace(getString(R.string.plus) + "98", "0"));

                        EventBus.getDefault().post(onSelectContact);
                         }
                    phones.close();
                }


            }
            cursor.close();

        }
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

        currentFragment = MainFragment.newInstance(this, footballServiceList, chosenServiceList);
        transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.main_container, currentFragment)
                .commit();
    }

    @Override
    public void openDrawer()
    {
        View containerView = findViewById(R.id.fragment_navigation_menudrawer);
        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.openDrawer(containerView);
    }

    @Override
    public void closeDrawer()
    {

    }

    @Override
    public void onReady(WebServiceClass<GetMenuResponse> response)
    {
        if (response == null || response.info == null)
        {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        if (response.info.statusCode != 200)
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();

            return;
        }
        if (response.info.statusCode == 200)
        {
            chosenServiceList = response.data.getChosenServiceList();
            footballServiceList = response.data.getFootballServiceList();

            Logger.e("--List size--", "chosenServiceList: " + chosenServiceList.size() +
                    "footballServiceList: " + footballServiceList.size());

            fragmentManager = getSupportFragmentManager();
            currentFragment = MainFragment.newInstance(this, footballServiceList, chosenServiceList);

            transaction = fragmentManager.beginTransaction();

            if (mSavedInstanceState == null)
            {
                transaction.add(R.id.main_container, currentFragment)
                        .commit();
            } else
            {
                transaction.replace(R.id.main_container, currentFragment)
                        .commit();
            }
        }

    }

    @Override
    public void onError(String message)
    {

    }
}
