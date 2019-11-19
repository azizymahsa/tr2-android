package ir.traap.tractor.android.ui.activities.main;

//import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.adpdigital.push.AdpPushClient;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.exceptions.RealmException;
import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.contact.OnSelectContact;
import ir.traap.tractor.android.apiServices.model.getAllMenuServices.response.GetAllMenuResponse;
import ir.traap.tractor.android.apiServices.model.getBankList.response.Bank;
import ir.traap.tractor.android.apiServices.model.getBankList.response.BankListResponse;
import ir.traap.tractor.android.apiServices.model.getMenu.request.GetMenuRequest;
import ir.traap.tractor.android.apiServices.model.getMenu.response.GetMenuItemResponse;
import ir.traap.tractor.android.apiServices.model.getMenu.response.GetMenuResponse;
import ir.traap.tractor.android.apiServices.model.matchList.MachListResponse;
import ir.traap.tractor.android.apiServices.model.matchList.MatchItem;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.enums.BarcodeType;
import ir.traap.tractor.android.models.dbModels.BankDB;
import ir.traap.tractor.android.notification.NotificationJobService;
import ir.traap.tractor.android.notification.PushMessageReceiver;
import ir.traap.tractor.android.singleton.SingletonContext;
import ir.traap.tractor.android.ui.activities.card.add.AddCardActivity;
import ir.traap.tractor.android.ui.activities.login.LoginActivity;
import ir.traap.tractor.android.ui.base.BaseActivity;
import ir.traap.tractor.android.ui.dialogs.MessageAlertDialog;
import ir.traap.tractor.android.ui.drawer.MenuDrawer;
import ir.traap.tractor.android.ui.fragments.barcodeReader.BarcodeReaderFragment;
import ir.traap.tractor.android.ui.fragments.about.AboutFragment;
import ir.traap.tractor.android.ui.fragments.allMenu.AllMenuFragment;
import ir.traap.tractor.android.ui.fragments.billPay.BillFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;
import ir.traap.tractor.android.ui.fragments.main.MainFragment;
import ir.traap.tractor.android.ui.fragments.matchSchedule.MatchScheduleFragment;
import ir.traap.tractor.android.ui.fragments.media.MediaFragment;
import ir.traap.tractor.android.ui.fragments.moneyTransfer.MoneyTransferFragment;
import ir.traap.tractor.android.ui.fragments.myProfile.MyProfileFragment;
import ir.traap.tractor.android.ui.fragments.paymentWithoutCard.PaymentWithoutCardFragment;
import ir.traap.tractor.android.ui.fragments.predict.PredictFragment;
import ir.traap.tractor.android.ui.fragments.simcardCharge.ChargeFragment;
import ir.traap.tractor.android.ui.fragments.simcardPack.PackFragment;
import ir.traap.tractor.android.ui.fragments.ticket.BuyTicketsFragment;
import ir.traap.tractor.android.ui.activities.ticket.ShowTicketActivity;
import ir.traap.tractor.android.ui.fragments.ticket.SelectPositionFragment;
import ir.traap.tractor.android.ui.fragments.traapMarket.MarketFragment;
import ir.traap.tractor.android.ui.fragments.transaction.TransactionsListFragment;
import ir.traap.tractor.android.utilities.Logger;
import ir.traap.tractor.android.utilities.Utility;

public class MainActivity extends BaseActivity implements MainActionView, MenuDrawer.FragmentDrawerListener,
        OnServiceStatus<WebServiceClass<GetMenuResponse>>
        , SelectPositionFragment.OnListFragmentInteractionListener
{
    private Boolean isMainFragment = true;

    private Toolbar mToolbar;
    private MenuDrawer drawerFragment;
    public static ArrayList<MatchItem> matchList;
    public static ArrayList<GetMenuItemResponse> allServiceList;

    private Realm realm;

    private Fragment fragment, mainFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
//    private View btnBuyTicket;

    private BottomNavigationView bottomNavigationView;

    private Bundle mSavedInstanceState;

    private ArrayList<GetMenuItemResponse> footballServiceList, chosenServiceList;
    private boolean hasPaymentTicket = false;
    private String refrenceNumber;
    private Boolean isCompleteThreadMatch = false, isCompleteThreadAllServices = false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        mSavedInstanceState = savedInstanceState;

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP)
        {
            AdpPushClient.get().register(Prefs.getString("mobile", ""));
            Intent myIntent = new Intent(this, PushMessageReceiver.class);
            PendingIntent.getBroadcast(this, 0, myIntent, 0);
        }
        else
        {
            startService(new Intent(this, NotificationJobService.class));
        }

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);

        //------------------test------------------------
//        Intent intent = getIntent();
//        String action = intent.getAction();
//        Uri data = intent.getData();
//
//        try
//        {
//            if (intent != null)
//            {
//                showAlert(this, "action= " + action + "\n" + "data= " + data.getQuery(), 0);
//            }
//            else
//            {
//                showError(this, "Null");
//            }
//        }
//        catch (NullPointerException e)
//        {
//            showError(this, "Null");
//        }

        //-----------------------test------------------------

        Intent intent = getIntent();

        if (Intent.ACTION_VIEW.equals(intent.getAction()))
        {
            Uri uri = intent.getData();
            refrenceNumber = uri.getQueryParameter("refrencenumber").replace("/", "");
            //String refrenceNumber = uri.getQueryParameter("RefrenceNumber");
            hasPaymentTicket = true;

            /*showLoading();
            isMainFragment = false;
            MatchItem matchBuyable = new MatchItem();
            this.fragment = BuyTicketsFragment.newInstance(this, matchBuyable,refrenceNumber);

            transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            transaction.replace(R.id.main_container, this.fragment)
                    .commit();*/
        }

        if (!Prefs.getString("FULLName", "").trim().replace(" ", "").equalsIgnoreCase(""))
        {
            TrapConfig.HEADER_USER_NAME = Prefs.getString("FULLName", "");
        }
        else
        {
            TrapConfig.HEADER_USER_NAME = Prefs.getString("mobile", "");
        }

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
            //switch fragment
            switch (itemId)
            {
                case R.id.tab_market:
                {
                    if (!bottomNavigationView.getMenu().getItem(4).isChecked())
                    {
                        setCheckedBNV(bottomNavigationView, 4);
                        isMainFragment = false;

                        fragment = MarketFragment.newInstance(this);
                        transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                        transaction.replace(R.id.main_container, fragment, "marketFragment")
                                .commit();
                    }
                    break;
                }
                case R.id.tab_all_services:
                {
                    if (!bottomNavigationView.getMenu().getItem(3).isChecked())
                    {
                        setCheckedBNV(bottomNavigationView, 3);

                        isMainFragment = false;

                        fragment = AllMenuFragment.newInstance(this, allServiceList);

                        transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        transaction.replace(R.id.main_container, fragment, "allMenuFragment")
                                .commit();
                    }
                    break;
                }
                case R.id.tab_home:
                {
                    if (!bottomNavigationView.getMenu().getItem(2).isChecked() || !isMainFragment)
                    {
//                        setCheckedBNV(bottomNavigationView, 2);

                        backToMainFragment();
                    }
                    break;
                }
                case R.id.tab_media:
                {
                    if (!bottomNavigationView.getMenu().getItem(1).isChecked())
                    {
                        setCheckedBNV(bottomNavigationView, 1);
                        isMainFragment = false;

                        fragment = MediaFragment.newInstance(this);
                        transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                        transaction.replace(R.id.main_container, fragment, "mediaFragment")
                                .commit();
                    }
                    break;
                }
                case R.id.tab_payment:
                {
                    if (!bottomNavigationView.getMenu().getItem(0).isChecked())
                    {
                        setCheckedBNV(bottomNavigationView, 0);
                        isMainFragment = false;

                        fragment = PaymentWithoutCardFragment.newInstance(this);
                        transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                        transaction.replace(R.id.main_container, fragment, "paymentWithoutCardFragment")
                                .commit();
                    }
                    break;
                }
            }
            return true;
        });
        //--------------------------------bottomBar----------------------------------
        showLoading();

        GetMenuRequest request = new GetMenuRequest();
        request.setDeviceType(TrapConfig.AndroidDeviceType);
        request.setDensity(SingletonContext.getInstance().getContext().getResources().getDisplayMetrics().density);
        SingletonService.getInstance().getMenuService().getMenu(this, request);
//
//        fragmentManager = getSupportFragmentManager();
//        fragment = MainFragment.newInstance(this, chosenServiceList, footballServiceList);
//
//        transaction = fragmentManager.beginTransaction();
//
//        if (mSavedInstanceState == null)
//        {
//            transaction.add(R.id.main_container, fragment)
//                    .commit();
//        }
//        else
//        {
//            transaction.replace(R.id.main_container, fragment)
//                    .commit();
//        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (fragment == null)
        {
//            fragment = MainFragment.newInstance(this, chosenServiceList, footballServiceList);
//            transaction = fragmentManager.beginTransaction();
//            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//            try
//            {
//                if (mSavedInstanceState == null)
//                {
//                    transaction.add(R.id.main_container, fragment)
//                            .commit();
////
//                } else
//                {
//                    transaction.replace(R.id.main_container, fragment)
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
            } else if (fragment instanceof BuyTicketsFragment && ((BuyTicketsFragment) fragment).getViewpager().getCurrentItem()!=0 ){

                ((BuyTicketsFragment) fragment).onBackClicked();

            } else{
                if (isMainFragment)
                {
                    super.onBackPressed();
                } else
                {
                    setCheckedBNV(bottomNavigationView, 2);

                    backToMainFragment();
                }
            }

        }
    /*    DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
//                setCheckedBNV(bottomNavigationView, 2);

                backToMainFragment();
            }
        }*/



    @Override
    public void onDrawerItemSelected(View view, int itemNumber)
    {
        Logger.e("-onDrawerItemSelected-", "selected " + itemNumber);

        final Intent intent = new Intent();
        switch (itemNumber)
        {
            case 1:
            {
              //  showToast(this, "لیست تراکنش ها", R.color.green);
                isMainFragment = false;

                fragment = TransactionsListFragment.newInstance(this);
                transaction = fragmentManager.beginTransaction();
//                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                transaction.replace(R.id.main_container, fragment, "transactionsListFragment")
                        .commit();

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.END))
                {
                    drawer.closeDrawer(GravityCompat.END);
                }
                break;
            }
            case 2:
            {
                showToast(this, "امتیازات", R.color.green);
                break;
            }
            case 3:
            {
                showToast(this, "جشنواره", R.color.green);

                break;
            }
            case 4:
            {
                showToast(this, "کیف پول", R.color.green);

                break;
            }
            case 5:
            {
                showToast(this, "مدیریت کارت ها", R.color.green);

                break;
            }
            case 6:
            {
                showToast(this, "دعوت از دوستان", R.color.green);

                break;
            }
            case 7:
            {
                // showToast(this, "درباره ما", R.color.green);
                isMainFragment = false;

                fragment = AboutFragment.newInstance(this);
                transaction = fragmentManager.beginTransaction();
//                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                transaction.replace(R.id.main_container, fragment, "aboutFragment")
                        .commit();

                break;
            }
            case 8:
            {
                showToast(this, "تنظیمات", R.color.green);

                break;
            }
            case 9:
            {
                showToast(this, "ارتباط با پشتیبانی", R.color.green);

                break;
            }
            case 10:
            {
                showToast(this, "راهنما", R.color.green);

                break;
            }
            case 11:
            {
                showToast(this, "انتقادات و پیشنهادات", R.color.green);

                break;
            }
            case 12:
            {
                MessageAlertDialog dialog = new MessageAlertDialog(this, "", "آیا می خواهید از حساب کاربری خود خارج شوید؟",
                        true, "خروج", "انصراف", new MessageAlertDialog.OnConfirmListener()
                {
                    @Override
                    public void onConfirmClick()
                    {
                        String mobile = Prefs.getString("mobile", "");
                        Prefs.clear();
                        Prefs.putString("mobile", mobile);
                        finish();
                        intent.setClass(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelClick()
                    {

                    }
                });
                dialog.show(getFragmentManager(), "messageDialog");

                return;

            }
            case 13:
            {
                showToast(this, "جدول لیگ برتر", R.color.green);

                break;
            }

        }
    }

    @Override
    public void onUserProfileClick()
    {
//        showToast(this, "حساب کاربری من", R.color.green);

        isMainFragment = false;
        fragment = MyProfileFragment.newInstance(this);
        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, fragment, "myProfileFragment")
                .commit();
    }

    @Override
    public void showLoading()
    {
        findViewById(R.id.rlLoading).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading()
    {
//        new Handler().postDelayed(() -> findViewById(R.id.rlLoading).setVisibility(View.GONE), 3000);
        if (isCompleteThreadAllServices && isCompleteThreadMatch)
        {
            Logger.e("isCompleteThreadAllServices", String.valueOf(isCompleteThreadAllServices));
            Logger.e("isCompleteThreadMatch", String.valueOf(isCompleteThreadMatch));

            findViewById(R.id.rlLoading).setVisibility(View.GONE);
        }
    }

    @Override
    public void onBill()
    {
        isMainFragment = false;
        String titleBill = "قبض تلفن همراه";
        int idBillType = 5;
        fragment = BillFragment.newInstance(this, titleBill, idBillType);
        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, fragment, "billFragment")
                .commit();

    }

    @Override
    public void onChargeSimCard()
    {
        isMainFragment = false;

        fragment = ChargeFragment.newInstance(this);

        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, fragment, "chargeFragment")
                .commit();

    }

    @Override
    public void onPackSimCard()
    {
        isMainFragment = false;

        fragment = PackFragment.newInstance(this);
        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, fragment, "packFragment")
                .commit();
    }


    @Override
    public void openBarcode(BarcodeType bill)
    {
        new TedPermission(this)
                .setPermissionListener(new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted()
                    {
                        try
                        {
                            onBarcodReader();
                        } catch (Exception e)
                        {
                            e.getMessage();
                        }

                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions)
                    {

                    }
                })
                .setDeniedMessage("If you reject permission,you can not use this application, Please turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA)
                .check();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
//        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBarcodReader()
    {
        isMainFragment = false;

        fragment = BarcodeReaderFragment.newInstance(this);
        //  transaction.commitAllowingStateLoss();
        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, fragment, "barcodeReaderFragment")
                .commit();

    }

    @Override
    public void onPaymentWithoutCard()
    {
        isMainFragment = false;

        // fragment = PaymentWithoutCardFragment2.newInstance(this);
        fragment = PaymentWithoutCardFragment.newInstance(this);
        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, fragment, "paymentWithoutCardFragment")
                .commit();
    }

    @Override
    public void doTransferMoney()
    {
        isMainFragment = false;

        fragment = MoneyTransferFragment.newInstance(this);
        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, fragment, "moneyTransferFragment")
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
    protected void onStop()
    {
        super.onStop();
        EventBus.getDefault().unregister(this);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OnSelectContact event)
    {
        showToast(this, "hoooraaa", R.color.gray);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

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

                        OnSelectContact onSelectContact = new OnSelectContact();
                        onSelectContact.setName(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) == null ? "" : phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                        onSelectContact.setNumber(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll(" ", "").replace("0098", "0").replace(getString(R.string.plus) + "98", "0"));
                        if (fragment instanceof ChargeFragment)
                        {
                            ((ChargeFragment) fragment).onSelectContact(onSelectContact);

                        } else if (fragment instanceof PackFragment)
                        {
                            ((PackFragment) fragment).onSelectContact(onSelectContact);
                        } else if (fragment instanceof BillFragment)
                        {
                            ((BillFragment) fragment).onSelectContact(onSelectContact);
                        }

                    }
                    phones.close();
                }


            }
            cursor.close();

        }

        if (resultCode == Activity.RESULT_OK && requestCode == 22)
        {
            showToast(this, "کارت جدید با موفقیت ذخیره شد.", R.color.green);

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
        setCheckedBNV(bottomNavigationView, 2);

        isMainFragment = true;
        transaction = fragmentManager.beginTransaction();

        if (mainFragment != null)
        {
            Logger.e("--mainFragment--", "not null");

//            Fragment currentFragment = fragmentManager.findFragmentById(R.id.main_container);
//            transaction.hide(currentFragment);
//
////            Fragment existingFragment = fragmentManager.findFragmentByTag("mainFragment");
//            transaction.show(mainFragment);
//            transaction.commit();

            fragment = mainFragment;
//            fragment = existingFragment;
        }
        else
        {
            Logger.e("--mainFragment--", "--null--");
            fragment = MainFragment.newInstance(MainActivity.this, footballServiceList, chosenServiceList, matchList);
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        }
        transaction.replace(R.id.main_container, fragment,"mainFragment")
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END))
        {
            drawer.closeDrawer(GravityCompat.END);
        }
//        showToast(this, "closeDrawer", R.color.gray);
//
//        View containerView = findViewById(R.id.fragment_navigation_menudrawer);
//        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
//        mDrawerLayout.closeDrawer(containerView);
    }

    @Override
    public void startAddCardActivity()
    {
        startActivityForResult(new Intent(this, AddCardActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), 22);
    }

    @Override
    public void onBuyTicketClick(MatchItem matchBuyable)
    {
        showLoading();
        isMainFragment = false;
        this.fragment = BuyTicketsFragment.newInstance(this, matchBuyable);

        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, this.fragment,"buyTicketsFragment")
                .commit();
    }

    @Override
    public void onLeageClick(ArrayList<MatchItem> matchBuyable)
    {

        isMainFragment = false;
        fragment = MatchScheduleFragment.newInstance(this,matchBuyable);
        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, fragment,"leagueTableFragment")
                .commit();
    }

    @Override
    public void onPredict(MatchItem matchPredict, Boolean isPredictable)
    {
        isMainFragment = false;
        this.fragment = PredictFragment.newInstance(this, matchPredict, isPredictable);

        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, this.fragment,"predictFragment")
                .commit();
    }

    @Override
    public void onCash()
    {
       /* tvTitle.setText("موجودی");

        changeFragment(fragments.get(1), "1");
        new Handler().postDelayed(() -> {
            layoutBehavior();
            tvTurnover.setVisibility(View.GONE);

        }, 200);
*/
    }

    @Override
    public void onReady(WebServiceClass<GetMenuResponse> response)
    {
        if (hasPaymentTicket)
        {
           /* showLoading();
            isMainFragment = false;
            this.fragment = ShowTicketsFragment.newInstance(this);

            transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            transaction.replace(R.id.main_container, this.fragment)
                    .commit();*/
            Intent intent = new Intent(MainActivity.this, ShowTicketActivity.class);

            intent.putExtra("RefrenceNumber", refrenceNumber);
            intent.putExtra("isTransactionList",false);

            startActivity(intent);

        }
        if (response == null || response.info == null)
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        if (response.info.statusCode != 200)
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();

            return;
        }
        else
        {
            chosenServiceList = response.data.getChosenServiceList();
            footballServiceList = response.data.getFootballServiceList();

            Logger.e("--List size--", "chosenServiceList: " + chosenServiceList.size() +
                    "footballServiceList: " + footballServiceList.size());

            getBankList();

        }

    }

    private Boolean getMatchList()
    {
        isCompleteThreadMatch = false;

        SingletonService.getInstance().getMatchListService().getMatchList(new OnServiceStatus<WebServiceClass<MachListResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<MachListResponse> responseMatchList)
            {
                isCompleteThreadMatch = true;

                hideLoading();

                if (responseMatchList == null || responseMatchList.info == null)
                {
//                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                    finish();

                    return;
                }
                if (responseMatchList.info.statusCode != 200)
                {
//                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                    finish();

                    return;
                }
                else
                {
                    matchList = (ArrayList<MatchItem>) responseMatchList.data.getMatchList();

                    fragmentManager = getSupportFragmentManager();
                    fragment = MainFragment.newInstance(MainActivity.this, footballServiceList, chosenServiceList, matchList);

                    mainFragment = fragment;

                    transaction = fragmentManager.beginTransaction();
//            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                    if (mSavedInstanceState == null)
                    {
                        transaction.add(R.id.main_container, fragment, "mainFragment")
                                .commit();
                    }
                    else
                    {
                        transaction.replace(R.id.main_container, fragment, "mainFragment")
                                .commit();
                    }

                }

            }

            @Override
            public void onError(String message)
            {
                isCompleteThreadMatch = true;

                hideLoading();

//                showError(MainActivity.this, "خطا در دریافت اطلاعات از سرور!");
                Logger.e("--onError--", message);
            }
        });

        return isCompleteThreadMatch;
    }

    private void getBankList()
    {
        SingletonService.getInstance().getBankListService().getBankListService(new OnServiceStatus<WebServiceClass<BankListResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<BankListResponse> responseBankList)
            {
//                hideLoading();

                if (responseBankList == null || responseBankList.info == null)
                {
//                    Prefs.clear();
                    isCompleteThreadAllServices = true;
                    isCompleteThreadMatch = true;
                    hideLoading();

                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();

                    return;
                }
                if (responseBankList.info.statusCode != 200)
                {
//                    Prefs.clear();
                    isCompleteThreadAllServices = true;
                    isCompleteThreadMatch = true;
                    hideLoading();

                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();

                    return;
                }
                else
                {
                    Logger.e("--BankDB size before delete--", "size: " + realm.where(BankDB.class).findAll().size());

                    try
                    {
                        realm.executeTransaction(realm1 ->
                        {
                            realm1.delete(BankDB.class);
                        });
                    } catch (RealmException ex)
                    {
                        Logger.e("--BankDB Delete--", "false");

                        ex.printStackTrace();
                    }

                    for (Bank bank : responseBankList.data.getBankList())
                    {
                        realm.executeTransaction(realm ->
                        {
                            try
                            {
                                BankDB bankDB = realm.createObject(BankDB.class);
                                int maxId = 0;
                                if (realm.where(BankDB.class).findAll().size() != 0)
                                {
                                    maxId = realm.where(BankDB.class).max("_ID").intValue();
                                }
                                bankDB.set_ID(maxId + 1);
                                bankDB.setId(bank.getId());
                                bankDB.setBankBin(bank.getBankBin());
                                bankDB.setBankName(bank.getBankName());
                                bankDB.setColorNumber(bank.getColorNumber());
                                bankDB.setColorText(bank.getColorText());
                                bankDB.setImageCard(bank.getImageCard());
                                bankDB.setImageCardBack(bank.getImageCardBack());
                                bankDB.setOrderItem(bank.getOrderItem());
                            } catch (RealmException e)
                            {
                                e.printStackTrace();
                            }
                        });
                    }

                    Logger.e("--BankDB size after delete--", "size: " + realm.where(BankDB.class).findAll().size());

                    getMatchList();
                    getAllServicesList();
                }
            }

            @Override
            public void onError(String message)
            {
                hideLoading();

                showError(MainActivity.this, "خطا در دریافت اطلاعات از سرور!");
            }
        });
    }

    private Boolean getAllServicesList()
    {
        isCompleteThreadAllServices = false;


        GetMenuRequest request = new GetMenuRequest();
        request.setDeviceType(TrapConfig.AndroidDeviceType);
        request.setDensity(SingletonContext.getInstance().getContext().getResources().getDisplayMetrics().density);
        SingletonService.getInstance().getMenuService().getMenuAll(new OnServiceStatus<WebServiceClass<GetAllMenuResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetAllMenuResponse> response)
            {
                try
                {
                    isCompleteThreadAllServices = true;
                    hideLoading();


                    if (response == null || response.info == null)
                    {
                        // startActivity(new Intent(this, A.class));
                        return;
                    }
                    if (response.info.statusCode != 200)
                    {
                        // startActivity(new Intent(this, LoginActivity.class));
                        //  finish();

                        return;
                    }
                    if (response.info.statusCode == 200)
                    {
                        allServiceList = response.data.getResults();
                    }
                }
                catch (Exception e)
                {
//                    mainView.showError(e.getMessage());
                    e.printStackTrace();
                    hideLoading();

                }
            }

            @Override
            public void onError(String message)
            {
                isCompleteThreadAllServices = true;
                hideLoading();

                Logger.e("--onError--", message);
            }
        }, request);


        return isCompleteThreadAllServices;
    }

    @Override
    public void onError(String message)
    {
        hideLoading();

        showError(this, "خطا در دریافت اطلاعات از سرور!");

        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

//    @Override
//    public void onClick(View v)
//    {
//        switch (v.getId()){
//            case R.id.btnBuyTicket:
//
//                isMainFragment = false;
//                fragment = BuyTicketsFragment.newInstance(this);
//                transaction = fragmentManager.beginTransaction();
//                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//                transaction.replace(R.id.main_container, fragment)
//                        .commit();
//                break;
//        }
//    }
}
