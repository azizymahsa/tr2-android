package com.traap.traapapp.ui.activities.main;

//import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

//import com.adpdigital.push.AdpPushClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pixplicity.easyprefs.library.Prefs;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Objects;

import io.realm.Realm;
import io.realm.exceptions.RealmException;

import com.traap.traapapp.BuildConfig;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.contact.OnSelectContact;
import com.traap.traapapp.apiServices.model.getAllMenuServices.response.GetAllMenuResponse;
import com.traap.traapapp.apiServices.model.getBankList.response.Bank;
import com.traap.traapapp.apiServices.model.getBankList.response.BankListResponse;
import com.traap.traapapp.apiServices.model.getMenu.request.GetMenuRequest;
import com.traap.traapapp.apiServices.model.getMenu.response.GetMenuItemResponse;
import com.traap.traapapp.apiServices.model.getMenu.response.GetMenuResponse;
import com.traap.traapapp.apiServices.model.matchList.MachListResponse;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.apiServices.model.news.main.NewsMainResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.BarcodeType;
import com.traap.traapapp.enums.MediaPosition;
import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.models.dbModels.BankDB;
import com.traap.traapapp.models.otherModels.newsModel.NewsArchiveClickModel;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.models.otherModels.paymentInstance.SimPackPaymentInstance;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.singleton.SingletonNewsArchiveClick;
import com.traap.traapapp.ui.activities.points.PointsActivity;
import com.traap.traapapp.ui.activities.card.add.AddCardActivity;
import com.traap.traapapp.ui.activities.paymentResult.PaymentResultChargeActivity;
import com.traap.traapapp.ui.activities.paymentResult.PaymentResultIncreaseInventoryActivity;
import com.traap.traapapp.ui.base.BaseActivity;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.dialogs.MessageAlertPermissionDialog;
import com.traap.traapapp.ui.drawer.MenuDrawerFragment;
import com.traap.traapapp.ui.fragments.barcodeReader.BarcodeReaderFragment;
import com.traap.traapapp.ui.fragments.about.AboutFragment;
import com.traap.traapapp.ui.fragments.allMenu.AllMenuFragment;
import com.traap.traapapp.ui.fragments.billPay.BillFragment;
import com.traap.traapapp.ui.fragments.gateWay.WalletFragment;
import com.traap.traapapp.ui.fragments.main.BuyTicketAction;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.main.MainFragment;
import com.traap.traapapp.ui.fragments.matchSchedule.MatchScheduleFragment;
import com.traap.traapapp.ui.fragments.matchSchedule.leaguse.pastResult.PastResultFragment;
import com.traap.traapapp.ui.fragments.media.MediaFragment;
import com.traap.traapapp.ui.fragments.moneyTransfer.MainMoneyTransferFragment;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.fragments.news.NewsArchiveActionView;
import com.traap.traapapp.ui.fragments.news.NewsMainActionView;
import com.traap.traapapp.ui.fragments.news.archive.NewsArchiveFragment;
import com.traap.traapapp.ui.fragments.news.mainNews.NewsMainFragment;
import com.traap.traapapp.ui.fragments.paymentGateWay.SelectPaymentGatewayFragment;
import com.traap.traapapp.ui.fragments.paymentWithoutCard.PaymentWithoutCardFragment;
import com.traap.traapapp.ui.fragments.photo.archive.PhotosArchiveActionView;
import com.traap.traapapp.ui.fragments.photo.archive.PhotosArchiveFragment;
import com.traap.traapapp.ui.fragments.predict.PredictFragment;
import com.traap.traapapp.ui.fragments.simcardCharge.ChargeFragment;
import com.traap.traapapp.ui.fragments.simcardCharge.OnClickContinueSelectPayment;
import com.traap.traapapp.ui.fragments.simcardPack.PackFragment;
import com.traap.traapapp.ui.fragments.support.SupportFragment;
import com.traap.traapapp.ui.activities.ticket.BuyTicketsActivity;
import com.traap.traapapp.ui.fragments.ticket.SelectPositionFragment;
import com.traap.traapapp.ui.fragments.ticket.ShowTicketsFragment;
import com.traap.traapapp.ui.fragments.transaction.TransactionsListFragment;
import com.traap.traapapp.ui.fragments.videos.VideosMainActionView;
import com.traap.traapapp.ui.fragments.videos.VideosMainFragment;
import com.traap.traapapp.ui.fragments.videos.archive.VideosArchiveActionView;
import com.traap.traapapp.ui.fragments.videos.archive.VideosArchiveFragment;
import com.traap.traapapp.ui.fragments.webView.WebFragment;
import com.traap.traapapp.utilities.KeyboardUtils;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.Tools;
import com.yandex.metrica.push.YandexMetricaPush;

public class MainActivity extends BaseActivity implements MainActionView, MenuDrawerFragment.FragmentDrawerListener,
        OnServiceStatus<WebServiceClass<GetMenuResponse>>, KeyboardUtils.SoftKeyboardToggleListener
        , SelectPositionFragment.OnListFragmentInteractionListener
{
    private Boolean isMainFragment = true;
    private Boolean isNewsFragment = false;
    private Boolean isFirst = true;
    private ImageView indicator_0, indicator_1, indicator_2;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private Toolbar mToolbar;
    private MenuDrawerFragment drawerFragment;
    public static ArrayList<MatchItem> matchList;
    public static ArrayList<GetMenuItemResponse> allServiceList;
    public static NewsMainResponse newsMainResponse;

    private Realm realm;

    private Fragment fragment, mainFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    //    private View btnBuyTicket;
    private Integer backState = 0;
    private BottomNavigationView bottomNavigationView;

    private Bundle mSavedInstanceState;

    private ArrayList<GetMenuItemResponse> footballServiceList, chosenServiceList, drawerMenu;
    private boolean hasPaymentTicket = false;
    private String refrenceNumber;
    private Boolean isCompleteThreadMatch = false;
    private Boolean isCompleteThreadAllServices = false;
    private Boolean isCompleteThreadNews = false;
    private ArrayList<MatchItem> matchBuyable;
    private String typeTransaction;
    private boolean hasPaymentCharge = false;
    private boolean hasPaymentPackageSimcard = false;
    private int PAYMENT_STATUS = 0;
    private boolean hasPaymentIncreaseWallet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        mSavedInstanceState = savedInstanceState;

//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP)
////        {
////            AdpPushClient.get().register(Prefs.getString("mobile", ""));
////            Intent myIntent = new Intent(this, PushMessageReceiver.class);
////            PendingIntent.getBroadcast(this, 0, myIntent, 0);
////        }
////        else
////        {
////            startService(new Intent(this, NotificationJobService.class));
////        }

//        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout()
//            {
//
//                Rect r = new Rect();
//                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
//                int screenHeight = getWindow().getDecorView().getRootView().getHeight();
//
//                int keypadHeight = screenHeight - r.bottom;
//
//                //Log.d(TAG, "keypadHeight = " + keypadHeight);
//
//                if (keypadHeight > screenHeight * 0.15)
//                {
//                    //Keyboard is opened
//                    hideNavBar();
//                }
//                else {
//                    // keyboard is closed
//                    showNavBar();
//                }
//            }
//        });

        mToolbar = findViewById(R.id.toolbar);

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

        KeyboardUtils.addKeyboardToggleListener(this, this);

        Intent intent = getIntent();

        if (Intent.ACTION_VIEW.equals(intent.getAction()))
        {
            Uri uri = intent.getData();
            refrenceNumber = uri.getQueryParameter("refrencenumber").replace("/", "");
            typeTransaction = uri.getQueryParameter("typetransaction").replace("/", "");
            try
            {
                if (Integer.valueOf(typeTransaction) == TrapConfig.PAYMENT_STAUS_ChargeSimCard)
                {
                    hasPaymentCharge = true;
                } else if (Integer.valueOf(typeTransaction) == TrapConfig.PAYMENT_STAUS_PackSimCard)
                {
                    hasPaymentPackageSimcard = true;
                } else if (Integer.valueOf(typeTransaction) == TrapConfig.PAYMENT_STATUS_STADIUM_TICKET)
                {
                    hasPaymentTicket = true;
                } else if (Integer.valueOf(typeTransaction) == TrapConfig.PAYMENT_STATUS_INCREASE_WALLET)
                {
                    hasPaymentIncreaseWallet = true;
                }

            } catch (Exception e)
            {
                showToast(MainActivity.this, "شماره پیگیری: " + refrenceNumber, 0);
            }

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
        } else
        {
            TrapConfig.HEADER_USER_NAME = Prefs.getString("mobile", "");
        }

        drawerFragment = (MenuDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_menudrawer);
        drawerFragment.setUp(R.id.fragment_navigation_menudrawer, findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

//        ImageButton btnDrawer = mToolbar.findViewById(R.id.imgMenu);
//
//        btnDrawer.setOnClickListener(v ->
//        {
//            View containerView = findViewById(R.id.fragment_navigation_menudrawer);
//            DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
//            mDrawerLayout.openDrawerVideos(containerView);
//
//        });

        indicator_0 = findViewById(R.id.indicator_0);
        indicator_1 = findViewById(R.id.indicator_1);
        indicator_2 = findViewById(R.id.indicator_2);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setItemIconTintList(null);

//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
//        {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
//            {
////                return bottomNavigationView.getMenu().getItem(bottomNavigationView.getChildAt());
//            }
//        });

//        bottomNavigationView.getMenu().getItem(2).setChecked(true);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        setIndicator(1);

//        bottomNavigationView.getMenu().getItem(0).getActionView().setBackground();

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem ->
        {
            Logger.e("--item--", menuItem.getTitle().toString());
            int itemId = menuItem.getItemId();
            //switch fragment
            switch (itemId)
            {
//                case R.id.tab_market:
//                {
//                    if (!bottomNavigationView.getMenu().getItem(4).isChecked())
//                    {
//                        setCheckedBNV(bottomNavigationView, 4);
//                        isMainFragment = false;
//
//                        fragment = MarketFragment.newInstance(this);
//                        transaction = fragmentManager.beginTransaction();
////                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//
//                        transaction.replace(R.id.main_container, fragment, "marketFragment")
//                                .commit();
//                    }
//                    break;
//                }
                case R.id.tab_all_services:
                {
//                    if (!bottomNavigationView.getMenu().getItem(3).isChecked())
                    if (!bottomNavigationView.getMenu().getItem(2).isChecked())
                    {
//                        setCheckedBNV(bottomNavigationView, 3);
                        setCheckedBNV(bottomNavigationView, 2);

                        isMainFragment = false;

                        fragment = AllMenuFragment.newInstance(this, allServiceList, 0);

                        transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        transaction.replace(R.id.main_container, fragment, "allMenuFragment")
                                .commit();
                    }
                    break;
                }
                case R.id.tab_home:
                {
//                    if (!bottomNavigationView.getMenu().getItem(2).isChecked() || !isMainFragment)
                    if (!bottomNavigationView.getMenu().getItem(1).isChecked() || !isMainFragment)
                    {
//                        setCheckedBNV(bottomNavigationView, 2);

                        backToMainFragment();
                    }
                    break;
                }
                case R.id.tab_media:
                {
//                    if (!bottomNavigationView.getMenu().getItem(1).isChecked())
                    if (!bottomNavigationView.getMenu().getItem(0).isChecked())
                    {
//                        setCheckedBNV(bottomNavigationView, 1);
                        setCheckedBNV(bottomNavigationView, 0);
                        isMainFragment = false;

                        fragment = MediaFragment.newInstance(MediaPosition.News, this);
                        transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                        transaction.replace(R.id.main_container, fragment, "mediaFragment")
                                .commit();
                    }
                    break;
                }
//                case R.id.tab_payment:
//                {
//                    if (!bottomNavigationView.getMenu().getItem(0).isChecked())
//                    {
//                        setCheckedBNV(bottomNavigationView, 0);
//                        isMainFragment = false;
//
//                        fragment = PaymentWithoutCardFragment.newInstance(this);
//                        transaction = fragmentManager.beginTransaction();
////                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//
//                        transaction.replace(R.id.main_container, fragment, "paymentWithoutCardFragment")
//                                .commit();
//                    }
//                    break;
//                }
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

//        mRegistrationBroadcastReceiver = new BroadcastReceiver()
//        {
//            @Override
//            public void onReceive(Context context, Intent intent)
//            {
//                // checking for type intent filter
//                if (Objects.requireNonNull(intent.getAction()).equals(TrapConfig.REGISTRATION_COMPLETE))
//                {
//                    // gcm successfully registered
//                    // now subscribe to `global` topic to receive app wide notifications
//                    FirebaseMessaging.getInstance().subscribeToTopic(TrapConfig.TOPIC_GLOBAL);
//
////                        displayFirebaseRegId();
//                }
//                else if (intent.getAction().equals(TrapConfig.PUSH_NOTIFICATION))
//                {
//                    // new push notification is received
//
//                    String message = intent.getStringExtra("message");
//
//                    showAlert(MainActivity.this, message, 0);
//                }
//            }
//        };

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task ->
                {
                    if (!task.isSuccessful())
                    {
                        Logger.e("-FireBaseInstanceId-", "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = Objects.requireNonNull(task.getResult()).getToken();

                    // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
                    String msg = "token: " + token;
                    Logger.e("-FireBaseInstanceId-", msg);
//                        showToast(MainActivity.this, msg, R.color.gray);
                });

    }

    private void setIndicator(int index)
    {
        for (int i = 0; i < 3; i++)
        {
            switch (index)
            {
                case 0:
                {
                    indicator_0.setImageDrawable(getResources().getDrawable(R.drawable.img_indicator));
                    indicator_0.setColorFilter(null);
                    indicator_1.setColorFilter(Color.argb(255, 255, 255, 255));
                    indicator_2.setColorFilter(Color.argb(255, 255, 255, 255));
                    break;
                }
                case 1:
                {
                    indicator_0.setColorFilter(Color.argb(255, 255, 255, 255));
                    indicator_1.setImageDrawable(getResources().getDrawable(R.drawable.img_indicator));
                    indicator_1.setColorFilter(null);
                    indicator_2.setColorFilter(Color.argb(255, 255, 255, 255));
                    break;
                }
                case 2:
                {
                    indicator_0.setColorFilter(Color.argb(255, 255, 255, 255));
                    indicator_1.setColorFilter(Color.argb(255, 255, 255, 255));
                    indicator_2.setImageDrawable(getResources().getDrawable(R.drawable.img_indicator));
                    indicator_2.setColorFilter(null);
                    break;
                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        // Handle your payload.
        String payload = intent.getStringExtra(YandexMetricaPush.EXTRA_PAYLOAD);
    }

    @Override
    protected void onPause()
    {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        try
        {
            NewsArchiveClickModel fromNewsDetails = SingletonNewsArchiveClick.getInstance().getNewsArchiveClickModel();
            if (fromNewsDetails != null)
            {
                if (fromNewsDetails.getFromNewsDetails())
                {
                    fromNewsDetails.setFromNewsDetails(false);
                    SingletonNewsArchiveClick.getInstance().setNewsArchiveClickModel(fromNewsDetails);
                    onNewsArchiveClick(SubMediaParent.MainFragment, MediaPosition.News);
                }
            }

        } catch (Exception e)
        {
//            showError("Error " );
        }


//        // register GCM registration complete receiver
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(TrapConfig.REGISTRATION_COMPLETE));
//
//        // register new push message receiver
//        // by doing this, the activity will be notified each time a new message arrives
//        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
//                new IntentFilter(TrapConfig.PUSH_NOTIFICATION));
//
//        // clear the notification area when the app is opened
//        NotificationUtils.clearNotifications(getApplicationContext());
    }

    //

    private void setCheckedBNV(BottomNavigationView bottomNavigationView, int index)
    {
        for (int i = 0; i < 3; i++)
        {
            if (i == index)
            {
                bottomNavigationView.getMenu().getItem(index).setChecked(true);
            }
            else
            {
                bottomNavigationView.getMenu().getItem(index).setChecked(false);
            }
        }

        setIndicator(index);
    }


    public void onBackPressed()
    {
        Log.e("backStateBack", backState + "");

        try
        {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.END))
            {
                drawer.closeDrawer(GravityCompat.END);
            } /*else if (fragment instanceof SelectPaymentGatewayFragment && ((SelectPaymentGatewayFragment) fragment).getViewpager().getCurrentItem()!=0 ){

                //((SelectPaymentGatewayFragment) fragment).onBackClicked();
                backToMainFragment();

            }*/ else if (fragment instanceof WalletFragment && Prefs.getInt("DetailCartStatus", 2) == 0)
            {
                isMainFragment = false;
                if (Prefs.getInt("DetailCartStatus", 2) == 0)
                {
                    fragment = WalletFragment.newInstance(this, 0);

                    transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.main_container, fragment, "DetailsCartFragment")
                            .commit();
                }

                //  Prefs.putInt("DetailCartStatus",1);
            } else if (fragment instanceof PastResultFragment)
            {
                onLeageClick(matchBuyable);
            } else if (fragment instanceof SelectPaymentGatewayFragment)
            {
                onBackToChargFragment(Prefs.getInt("PAYMENT_STATUS", PAYMENT_STATUS)
                );
            } else if (fragment instanceof ChargeFragment && backState == 2 || fragment instanceof PackFragment && backState == 2)
            {

//                        setCheckedBNV(bottomNavigationView, 3);
                setCheckedBNV(bottomNavigationView, 2);

                isMainFragment = false;

                fragment = AllMenuFragment.newInstance(this, allServiceList, backState);

                transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                transaction.replace(R.id.main_container, fragment, "allMenuFragment")
                        .commit();


            }


           /* else if (fragment instanceof BuyTicketsActivity && ((BuyTicketsActivity) fragment).getViewpager().getCurrentItem() != 0)
            {
                ((BuyTicketsActivity) fragment).onBackClicked();
            }*/
            else
            {
                if (isMainFragment)
                {
//                    super.onBackPressed();
                    MessageAlertDialog exitDialog = new MessageAlertDialog(this, "", "آیا بابت خروج از برنامه اطمینان دارید؟",
                            true, "بله", "خیر", MessageAlertDialog.TYPE_MESSAGE,
                            new MessageAlertDialog.OnConfirmListener()
                            {
                                @Override
                                public void onConfirmClick()
                                {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                                    {
                                        finishAndRemoveTask();
                                    } else
                                    {
                                        Intent intent = new Intent(Intent.ACTION_MAIN);
                                        intent.addCategory(Intent.CATEGORY_HOME);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        startActivityForResult(intent, 100);

//                                ActivityManager am = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);
//                                am.killBackgroundProcesses(getPackageName());
//                                android.os.Process.killProcess(android.os.Process.myPid());
//
                                        System.exit(0);
                                    }
                                }

                                @Override
                                public void onCancelClick()
                                {
                                }
                            });
                    exitDialog.show(getFragmentManager(), "exitDialog");
                } else
                {
                    setCheckedBNV(bottomNavigationView, 2);

                    backToMainFragment();
                }
            }
        } catch (Exception e)
        {
            e.getMessage();
        }
    }
    /*    DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END))
        {
            drawer.closeDrawerVideos(GravityCompat.END);
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
            case 82: //لیست تراکنش ها
            {
                //  showToast(this, "لیست تراکنش ها", R.color.green);
                isMainFragment = false;

                fragment = TransactionsListFragment.newInstance(this);
//                fragment = TransactionsListFragment2.newInstance(this);
                transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.main_container, fragment, "transactionsListFragment")
                        .commit();

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.END))
                {
                    drawer.closeDrawer(GravityCompat.END);
                }
                break;
            }
            case 83: //امتیازات
            {
//                showToast(this, "امتیازات", R.color.green);
                closeDrawer();
                startActivityForResult(new Intent(this, PointsActivity.class), 100);
                break;
            }
            case 81: //حساب کاربری من
            {
//                showToast(this, "حساب کاربری من", R.color.green);
                onUserProfileClick();

                break;
            }
            case 84: //کیف پول
            {
                // showToast(this, "کیف پول", R.color.green);
                isMainFragment = false;

                fragment = WalletFragment.newInstance(this);
                transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                transaction.replace(R.id.main_container, fragment, "walletFragment")
                        .commit();

                break;
            }
            case 85: //مدیریت کارت ها
            {
//                showToast(this, "مدیریت کارت ها", R.color.green);

                break;
            }
            case 87: //دعوت از دوستان
            {
//                showToast(this, "دعوت از دوستان", R.color.green);

                break;
            }
            case 91: //درباره ما
            {
                // showToast(this, "درباره ما", R.color.green);
                isMainFragment = false;

                fragment = AboutFragment.newInstance(this);
                transaction = fragmentManager.beginTransaction();
//                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                transaction.replace(R.id.main_container, fragment, "aboutFragment")
                        .commit();
               /* isMainFragment = false;

                fragment = PaymentGateWayDialog.newInstance(this);
                transaction = fragmentManager.beginTransaction();
//                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                transaction.replace(R.id.main_container, fragment, "aboutFragment")
                        .commit();*/
                break;
            }
            case 88: //تنظیمات
            {
//                showToast(this, "تنظیمات", R.color.green);

                break;
            }
            case 90: //ارتباط با پشتیبانی
            {
                // showToast(this, "ارتباط با پشتیبانی", R.color.green);
                isMainFragment = false;

                fragment = SupportFragment.newInstance(this);
                transaction = fragmentManager.beginTransaction();
//                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                transaction.replace(R.id.main_container, fragment, "SupportFragment")
                        .commit();
                break;
            }
            case 89: //راهنما
            {
                if (fragment instanceof MainFragment)
                {
                    ((MainFragment) fragment).requestGetHelpMenu();
                } else
                {
                    backToMainFragment();
                    ((MainFragment) fragment).requestGetHelpMenu();
                }
                break;
            }
            case 86: //جدول لیگ برتر
            {
//                showToast(this, "جدول لیگ برتر", R.color.green);

                /*isMainFragment = false;
                //fragment = MatchScheduleFragment.newInstance(this,matchBuyable);
                fragment = LeagueTableFragment.newInstance(this);
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment,"leagueTableFragment")
                        .commit();*/
                break;
            }
//            case 12:
//            {
//                MessageAlertDialog dialog = new MessageAlertDialog(this, "", "آیا می خواهید از حساب کاربری خود خارج شوید؟",
//                        true, "خروج", "انصراف", new MessageAlertDialog.OnConfirmListener()
//                {
//                    @Override
//                    public void onConfirmClick()
//                    {
//                        String mobile = Prefs.getString("mobile", "");
//                        Prefs.clear();
//                        Prefs.putString("mobile", mobile);
//                        finish();
//                        intent.setClass(MainActivity.this, LoginActivity.class);
//                        startActivityForResult(intent, 100);
//                    }
//
//                    @Override
//                    public void onCancelClick()
//                    {
//
//                    }
//                });
//                dialog.show(getFragmentManager(), "messageDialog");
//
//                break;

//            }
        }
    }

    @Override
    public void onUserProfileClick()
    {
//        showToast(this, "حساب کاربری من", R.color.green);
        startActivityForResult(new Intent(this, MyProfileActivity.class), 100);
       /* isMainFragment = false;
        fragment = MyProfileFragment.newInstance(this);
        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, fragment, "myProfileFragment")
                .commit();*/
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
        if (isCompleteThreadAllServices && isCompleteThreadMatch && isCompleteThreadNews)
        {
//            Logger.e("isCompleteThreadAllServices", String.valueOf(isCompleteThreadAllServices));
//            Logger.e("isCompleteThreadMatch", String.valueOf(isCompleteThreadMatch));

            if (isFirst)
            {
                isFirst = false;
                new Handler().postDelayed(() -> findViewById(R.id.rlLoading).setVisibility(View.GONE), 1200);
            } else
            {
                findViewById(R.id.rlLoading).setVisibility(View.GONE);
            }
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

    public void onChargeSimCard(Integer backState)
    {
        this.backState = backState;
        isMainFragment = false;

        fragment = ChargeFragment.newInstance(this, backState);

        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, fragment, "chargeFragment")
                .commit();

    }


    public void onPackSimCard(Integer status)
    {
        isMainFragment = false;
        this.backState = status;
        fragment = PackFragment.newInstance(this, backState);
        transaction = fragmentManager.beginTransaction();

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
                //  .setDeniedMessage("If you reject permission,you can not use this application, Please turn on permissions at [Setting] > [Permission]")
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

        fragment = MainMoneyTransferFragment.newInstance(this);
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
                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                MessageAlertPermissionDialog dialog = new MessageAlertPermissionDialog(MainActivity.this, "",
                                        "برای استفاده از دفترچه تلفن، اخذ این مجوز الزامی است.  ",
                                        true, "نمایش دوباره دسترسی", "انصراف", MessageAlertDialog.TYPE_MESSAGE, new MessageAlertDialog.OnConfirmListener()
                                {
                                    @Override
                                    public void onConfirmClick()
                                    {


                                        getPermission();
                                    }

                                    @Override
                                    public void onCancelClick()
                                    {

                                    }
                                }
                                );
                                dialog.show(MainActivity.this.getFragmentManager(), "dialogMessage");
                            }
                        }, 500);


                    }
                })
                .setPermissions(Manifest.permission.READ_CONTACTS)
                .check();
    }

    private void getPermission()
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

                        new Handler().postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {

                                MessageAlertPermissionDialog dialog = new MessageAlertPermissionDialog(MainActivity.this, "",
                                        "برای استفاده از دفترچه تلفن، اخذ این مجوز الزامی است.  ",
                                        true, "نمایش دوباره دسترسی", "انصراف", MessageAlertDialog.TYPE_MESSAGE, new MessageAlertDialog.OnConfirmListener()
                                {
                                    @Override
                                    public void onConfirmClick()
                                    {


                                        getPermission();
                                    }

                                    @Override
                                    public void onCancelClick()
                                    {

                                    }
                                }
                                );
                                dialog.show(MainActivity.this.getFragmentManager(), "dialogMessage");
                            }
                        }, 500);

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
        if (BuildConfig.DEBUG)
        {
            showToast(this, "", R.color.gray);
        }
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
                        } else if (fragment instanceof WalletFragment)
                        {
                            ((WalletFragment) fragment).onSelectContact(onSelectContact);

                        }

                    }
                    phones.close();
                }


            }
            cursor.close();

        } else if (resultCode == Activity.RESULT_OK && requestCode == 22)
        {
            showToast(this, "کارت جدید با موفقیت ذخیره شد.", R.color.green);

        } else if (resultCode == Activity.RESULT_OK)
        {
            Log.e("12321", "onActivityResult:22222 ");
            backToMainFragment();
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
//        setCheckedBNV(bottomNavigationView, 2);
        setCheckedBNV(bottomNavigationView, 1);

        isMainFragment = true;
//        isNewsFragment = false;
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
        } else
        {
            Logger.e("--mainFragment--", "--null--");
            fragment = MainFragment.newInstance(MainActivity.this, footballServiceList, chosenServiceList, matchList);
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        }
        transaction.replace(R.id.main_container, fragment, "mainFragment")
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
//        showToast(this, "closeDrawerVideos", R.color.gray);
//
//        View containerView = findViewById(R.id.fragment_navigation_menudrawer);
//        DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
//        mDrawerLayout.closeDrawerVideos(containerView);
    }

    @Override
    public void startAddCardActivity()
    {
        startActivityForResult(new Intent(this, AddCardActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK), 22);
    }

    @Override
    public void onBuyTicketClick(MatchItem matchBuyable)
    {

       /* showLoading();
        isMainFragment = false;
        this.fragment = BuyTicketsActivity.newInstance(this, matchBuyable);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, this.fragment, "buyTicketsFragment")
                .commit();*/
/*        this.fragment = BuyTicketsActivity.newInstance(this, matchBuyable);
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, this.fragment, "buyTicketsFragment")
                .commit();*/
        Intent intent = new Intent(this, BuyTicketsActivity.class);
        intent.putExtra("MatchBuyable", (Parcelable) matchBuyable);
        //intent.putExtra("StatusPayment", true);
        startActivityForResult(intent, 100);

    }

    @Override
    public void onLeageClick(ArrayList<MatchItem> matchBuyable)
    {
        this.matchBuyable = matchBuyable;
        isMainFragment = false;
        fragment = MatchScheduleFragment.newInstance(this, matchBuyable, 1);
        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, fragment, "leagueTableFragment").commit();
    }

    @Override
    public void onPredict(MatchItem matchPredict, Boolean isPredictable)
    {
        isMainFragment = false;
        this.fragment = PredictFragment.newInstance(this, matchPredict.getId(), isPredictable);

        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, this.fragment, "predictFragment")
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
    public void onFootBallServiceOne()
    {
        isMainFragment = false;
        fragment = NewsMainFragment.newInstance(new NewsMainActionView()
        {
            @Override
            public void backToMainFragment()
            {
                MainActivity.this.backToMainFragment();
            }

            @Override
            public void backToMainNewsFragment()
            {
                openMainNewsFragment();
            }

            @Override
            public void onNewsArchiveFragment(SubMediaParent parent)
            {
                onNewsArchiveClick(parent, MediaPosition.News);
            }

            @Override
            public void onNewsFavoriteFragment(SubMediaParent parent)
            {
                onNewsFavoriteClick(parent, MediaPosition.News);
            }


            @Override
            public void openDrawerNews()
            {
                openDrawer();
            }

            @Override
            public void closeDrawerNews()
            {
                closeDrawer();
            }

            @Override
            public void showLoading()
            {
                MainActivity.this.showLoading();
            }

            @Override
            public void hideLoading()
            {
                MainActivity.this.hideLoading();
            }
        });
        transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, fragment, "newsMainFragment")
                .commit();
    }

    private void openMainNewsFragment()
    {
        isMainFragment = false;
        fragment = NewsMainFragment.newInstance(new NewsMainActionView()
        {
            @Override
            public void backToMainFragment()
            {
                MainActivity.this.backToMainFragment();
            }

            @Override
            public void backToMainNewsFragment()
            {
                openMainNewsFragment();
            }

            @Override
            public void onNewsArchiveFragment(SubMediaParent parent)
            {
                onNewsArchiveClick(parent, MediaPosition.News);
            }

            @Override
            public void onNewsFavoriteFragment(SubMediaParent parent)
            {
                onNewsFavoriteClick(parent, MediaPosition.News);
            }

            @Override
            public void openDrawerNews()
            {
                openDrawer();
            }

            @Override
            public void closeDrawerNews()
            {
                closeDrawer();
            }

            @Override
            public void showLoading()
            {
                MainActivity.this.showLoading();
            }

            @Override
            public void hideLoading()
            {
                MainActivity.this.hideLoading();
            }
        });
        transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, fragment, "newsMainFragment")
                .commit();
    }

    @Override
    public void onFootBallServiceTwo()
    {
        isMainFragment = false;
        this.fragment = VideosMainFragment.newInstance(new VideosMainActionView()
        {
            @Override
            public void backToMainFragment()
            {
                MainActivity.this.backToMainFragment();
            }

            @Override
            public void backToMainVideosFragment()
            {
                onMainVideoClick();
            }

            @Override
            public void onVideosArchiveFragment(SubMediaParent parent)
            {
                onVideosArchiveClick(parent, MediaPosition.VideoGallery);
            }

            @Override
            public void onVideosFavoriteFragment(SubMediaParent parent)
            {
                onVideosFavoriteClick(parent, MediaPosition.VideoGallery);
            }

            @Override
            public void openDrawerVideos()
            {
                openDrawer();
            }

            @Override
            public void closeDrawerVideos()
            {
                closeDrawer();
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
        });

        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, this.fragment, "videosMainFragment")
                .commit();
    }

    @Override
    public void onFootBallServiceThree()
    {

    }

    @Override
    public void onFootBallServiceFour()
    {

    }

    @Override
    public void onFootBallServiceFive()
    {

    }

    @Override
    public void onFootBallServiceSix()
    {

    }

    @Override
    public void onNewsArchiveClick(SubMediaParent parent, MediaPosition mediaPosition)
    {
        isMainFragment = false;
        this.fragment = NewsArchiveFragment.newInstance(parent, mediaPosition, false, new NewsArchiveActionView()
        {
            @Override
            public void backToMediaFragment(MediaPosition mediaPosition)
            {
                fragment = MediaFragment.newInstance(mediaPosition, MainActivity.this);
                transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                transaction.replace(R.id.main_container, fragment, "mediaFragment")
                        .commit();
            }

            @Override
            public void backToMainFragment()
            {
                MainActivity.this.backToMainFragment();
            }

            @Override
            public void backToMainNewsFragment()
            {
                openMainNewsFragment();
            }

            @Override
            public void onNewsArchiveFragment(SubMediaParent parent)
            {
//                onNewsArchiveClick(SubMediaParent.MainFragment);
            }

            @Override
            public void onNewsFavoriteFragment(SubMediaParent parent)
            {

            }

            @Override
            public void openDrawerNews()
            {
                openDrawer();
            }

            @Override
            public void closeDrawerNews()
            {
                closeDrawer();
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
        });

        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, this.fragment, "newsArchiveCategoryFragment")
                .commit();
    }

    @Override
    public void onNewsFavoriteClick(SubMediaParent parent, MediaPosition mediaPosition)
    {
        isMainFragment = false;
        this.fragment = NewsArchiveFragment.newInstance(parent, mediaPosition, true, new NewsArchiveActionView()
        {
            @Override
            public void backToMediaFragment(MediaPosition mediaPosition)
            {
                fragment = MediaFragment.newInstance(mediaPosition, MainActivity.this);
                transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                transaction.replace(R.id.main_container, fragment, "mediaFragment")
                        .commit();
            }

            @Override
            public void backToMainFragment()
            {
                MainActivity.this.backToMainFragment();
            }

            @Override
            public void backToMainNewsFragment()
            {
                openMainNewsFragment();
            }

            @Override
            public void onNewsArchiveFragment(SubMediaParent parent)
            {
                onNewsArchiveClick(SubMediaParent.MainFragment, MediaPosition.News);
            }

            @Override
            public void onNewsFavoriteFragment(SubMediaParent parent)
            {

            }

            @Override
            public void openDrawerNews()
            {
                openDrawer();
            }

            @Override
            public void closeDrawerNews()
            {
                closeDrawer();
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
        });

        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, this.fragment, "newsArchiveCategoryFragment")
                .commit();
    }

    @Override
    public void onPhotosArchiveClick(SubMediaParent parent, MediaPosition mediaPosition)
    {
        isMainFragment = false;
        this.fragment = PhotosArchiveFragment.newInstance(this, parent, mediaPosition, false, new PhotosArchiveActionView()
        {
            @Override
            public void backToMediaFragment(MediaPosition mediaPosition)
            {
                fragment = MediaFragment.newInstance(MediaPosition.ImageGallery, MainActivity.this);
                transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.main_container, fragment, "mediaFragment")
                        .commit();
            }

            @Override
            public void backToMainPhotosFragment()
            {
            }

            @Override
            public void onPhotosArchiveFragment(SubMediaParent parent)
            {
            }

            @Override
            public void onPhotosFavoriteFragment(SubMediaParent parent)
            {
                onPhotosFavoriteClick(parent, MediaPosition.ImageGallery);
            }

            @Override
            public void openDrawerPhotos()
            {
                openDrawer();
            }

            @Override
            public void closeDrawerPhotos()
            {
                closeDrawer();
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
        });

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, this.fragment, "photosArchiveCategoryFragment")
                .commit();
    }

    @Override
    public void onPhotosFavoriteClick(SubMediaParent parent, MediaPosition mediaPosition)
    {
        isMainFragment = false;
        this.fragment = PhotosArchiveFragment.newInstance(this, parent, mediaPosition, true, new PhotosArchiveActionView()
        {
            @Override
            public void backToMediaFragment(MediaPosition mediaPosition)
            {
                fragment = MediaFragment.newInstance(MediaPosition.ImageGallery, MainActivity.this);
                transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                transaction.replace(R.id.main_container, fragment, "mediaFragment")
                        .commit();
            }

            @Override
            public void backToMainPhotosFragment()
            {

            }

            @Override
            public void onPhotosArchiveFragment(SubMediaParent parent)
            {
            }

            @Override
            public void onPhotosFavoriteFragment(SubMediaParent parent)
            {
                onPhotosFavoriteClick(parent, MediaPosition.ImageGallery);
            }

            @Override
            public void openDrawerPhotos()
            {
                openDrawer();
            }

            @Override
            public void closeDrawerPhotos()
            {
                closeDrawer();
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
        });
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, this.fragment, "photosArchiveCategoryFragment")
                .commit();
    }

    @Override
    public void onVideosArchiveClick(SubMediaParent parent, MediaPosition mediaPosition)
    {
        isMainFragment = false;
        this.fragment = VideosArchiveFragment.newInstance(parent, mediaPosition, false, new VideosArchiveActionView()
        {
            @Override
            public void backToMainVideosFragment()
            {
                onMainVideoClick();
            }

            @Override
            public void onVideosArchiveFragment(SubMediaParent parent)
            {

            }

            @Override
            public void onVideosFavoriteFragment(SubMediaParent parent)
            {
                onVideosFavoriteClick(parent, MediaPosition.VideoGallery);
            }

            @Override
            public void openDrawerVideos()
            {
                openDrawer();
            }

            @Override
            public void closeDrawerVideos()
            {
                closeDrawer();
            }

            @Override
            public void backToMediaFragment(MediaPosition mediaPosition)
            {
                fragment = MediaFragment.newInstance(MediaPosition.VideoGallery, MainActivity.this);
                transaction = fragmentManager.beginTransaction();

                transaction.replace(R.id.main_container, fragment, "mediaFragment")
                        .commit();
            }

            @Override
            public void backToMainFragment()
            {
                MainActivity.this.backToMainFragment();
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
        });

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, this.fragment, "videosArchiveCategoryFragment")
                .commit();
    }

    @Override
    public void onVideosFavoriteClick(SubMediaParent parent, MediaPosition mediaPosition)
    {
        isMainFragment = false;
        this.fragment = VideosArchiveFragment.newInstance(parent, mediaPosition, true, new VideosArchiveActionView()
        {
            @Override
            public void backToMainVideosFragment()
            {
                onMainVideoClick();
            }

            @Override
            public void onVideosArchiveFragment(SubMediaParent parent)
            {
                onMainVideoClick();
            }

            @Override
            public void onVideosFavoriteFragment(SubMediaParent parent)
            {
                onVideosFavoriteClick(parent, MediaPosition.VideoGallery);
            }

            @Override
            public void openDrawerVideos()
            {
                openDrawer();
            }

            @Override
            public void closeDrawerVideos()
            {
                closeDrawer();
            }

            @Override
            public void backToMediaFragment(MediaPosition mediaPosition)
            {
                fragment = MediaFragment.newInstance(MediaPosition.VideoGallery, MainActivity.this);
                transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                transaction.replace(R.id.main_container, fragment, "mediaFragment")
                        .commit();
            }

            @Override
            public void backToMainFragment()
            {
                MainActivity.this.backToMainFragment();
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
        });

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, this.fragment, "videosArchiveCategoryFragment")
                .commit();
    }

    @Override
    public void onMainVideoClick()
    {
        isMainFragment = false;
        this.fragment = VideosMainFragment.newInstance(new VideosMainActionView()
        {
            @Override
            public void backToMainFragment()
            {
                MainActivity.this.backToMainFragment();
            }

            @Override
            public void backToMainVideosFragment()
            {
                onMainVideoClick();
            }

            @Override
            public void onVideosArchiveFragment(SubMediaParent parent)
            {
                onVideosArchiveClick(parent, MediaPosition.VideoGallery);
            }

            @Override
            public void onVideosFavoriteFragment(SubMediaParent parent)
            {
                onVideosFavoriteClick(parent, MediaPosition.VideoGallery);
            }

            @Override
            public void openDrawerVideos()
            {
                openDrawer();
            }

            @Override
            public void closeDrawerVideos()
            {
                closeDrawer();
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
        });

        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, this.fragment, "videosMainFragment")
                .commit();

    }

    @Override
    public void openPastResultFragment(String teamId, String imageLogo, String logoTitle)
    {
        isMainFragment = false;
        this.fragment = PastResultFragment.newInstance(this, teamId, imageLogo, logoTitle);

        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, this.fragment, "pastResultFragment")
                .commit();
    }

    @Override
    public void openPackPaymentFragment(OnClickContinueSelectPayment onClickContinueSelectPayment, String urlPayment, int imageDrawable, String title, String amount, SimPackPaymentInstance paymentInstance, String mobile, int PAYMENT_STATUS)
    {

        isMainFragment = false;
        Prefs.putInt("PAYMENT_STATUS", PAYMENT_STATUS);
        this.fragment = SelectPaymentGatewayFragment.newInstance(PAYMENT_STATUS, onClickContinueSelectPayment, urlPayment, this, imageDrawable,
                title, amount, paymentInstance, mobile);

        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, this.fragment, "selectPaymentGatewayFragment")
                .commit();

    }

    @Override
    public void openChargePaymentFragment(OnClickContinueSelectPayment onClickContinueSelectPayment, String urlPayment, int icon_payment, String title, String amount, SimChargePaymentInstance paymentInstance, String mobile, int PAYMENT_STATUS)
    {

        isMainFragment = false;
        Prefs.putInt("PAYMENT_STATUS", PAYMENT_STATUS);
        this.fragment = SelectPaymentGatewayFragment.newInstance(PAYMENT_STATUS, onClickContinueSelectPayment, urlPayment, this, icon_payment,
                title, amount, paymentInstance, mobile);

        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, this.fragment, "selectPaymentGatewayFragment")
                .commit();
       /* SelectPaymentGatewayFragment fragment2 = new SelectPaymentGatewayFragment(urlPayment, mainView, R.drawable.icon_payment_ticket,
                title, Utility.priceFormat(amount));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment2);
        fragmentTransaction.commit();*/
    }

    @Override
    public void openIncreaseWalletPaymentFragment(OnClickContinueSelectPayment onClickContinueSelectPayment, String urlPayment, int imageDrawable, String title, String amount, SimChargePaymentInstance paymentInstance, String mobile, int PAYMENT_STATUS)
    {
        isMainFragment = false;
        Prefs.putInt("PAYMENT_STATUS", PAYMENT_STATUS);
        this.fragment = SelectPaymentGatewayFragment.newInstance(PAYMENT_STATUS, onClickContinueSelectPayment, urlPayment, this, imageDrawable,
                title, amount, paymentInstance, mobile);

        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, this.fragment, "selectPaymentGatewayFragment")
                .commit();
       /* SelectPaymentGatewayFragment fragment2 = new SelectPaymentGatewayFragment(urlPayment, mainView, R.drawable.icon_payment_ticket,
                title, Utility.priceFormat(amount));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment2);
        fragmentTransaction.commit();*/

    }

    @Override
    public void openWebView(MainActionView mainView, String uRl, String gds_token)
    {
      /*  WebFragment fragment =  WebFragment.newInstance(mainView,URl,Prefs.getString("gds_token", ""));

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();*/
        isMainFragment = false;
        this.fragment = WebFragment.newInstance(mainView, uRl, gds_token);

        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, this.fragment, "WebFragment")
                .commit();


    }


    @Override
    public void getBuyEnable(BuyTicketAction buyTicketAction)
    {
        showLoading();
        SingletonService.getInstance().getReservation().getTicketBuyEnableService(new OnServiceStatus<WebServiceClass<MatchItem>>()
        {
            @Override
            public void onReady(WebServiceClass<MatchItem> response)
            {
                try
                {

                    hideLoading();
                    if (response.info.statusCode == 200)
                    {
                        if (response.data != null)
                        {
                            onBuyTicketClick(response.data);
                        } else
                        {
                            showAlert(MainActivity.this, response.info.message, 0);
                        }
                    } else
                    {
                        showAlert(MainActivity.this, response.info.message, 0);
                    }
                    buyTicketAction.onEndListener();

                } catch (Exception e)
                {
                }

            }

            @Override
            public void onError(String message)
            {
                hideLoading();

                if (Tools.isNetworkAvailable(MainActivity.this))
                {
                    showAlert(MainActivity.this, "درحال حاضر مسابقه ای جهت خرید بلیت موجود نیست.", 0);
                    Logger.e("--showErrorMessage--", message);
                } else
                {
                    showAlert(MainActivity.this, R.string.networkErrorMessage, R.string.networkError);
                }
                buyTicketAction.onEndListener();
            }
        });
    }

    @Override
    public void onSetPredictCompleted(Integer matchId, Boolean isPredictable, String message)
    {
        MessageAlertDialog dialog = new MessageAlertDialog(this, "", message, false,
                "تایید", "", MessageAlertDialog.TYPE_SUCCESS, new MessageAlertDialog.OnConfirmListener()
        {
            @Override
            public void onConfirmClick()
            {
                isMainFragment = false;
                fragment = PredictFragment.newInstance(MainActivity.this, matchId, isPredictable);

                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment, "predictFragment")
                        .commit();
            }

            @Override
            public void onCancelClick()
            {

            }
        });
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "dialogAlert");

    }

    @Override
    public void onBackToChargFragment(int PAYMENT_STATUS)
    {

        if (PAYMENT_STATUS == 3)
        {
            isMainFragment = false;
            this.fragment = ChargeFragment.newInstance(this, backState);

            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, this.fragment, "ChargeFragment")
                    .commit();
        } else if (PAYMENT_STATUS == 4)
        {
            isMainFragment = false;
            this.fragment = PackFragment.newInstance(this, backState);

            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, this.fragment, "PackFragment")
                    .commit();
        }
        else if (PAYMENT_STATUS == 13)
        {
            isMainFragment = false;
            this.fragment = WalletFragment.newInstance(this, 1);//IncreaseInventoryFragment

            transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            transaction.replace(R.id.main_container, this.fragment, "IncreaseInventoryFragment")
                    .commit();
        }
    }

    @Override
    public void backToAllServicePackage(Integer backState)
    {

        if (this.backState == 2)
        {

            setCheckedBNV(bottomNavigationView, 2);

            isMainFragment = false;

            fragment = AllMenuFragment.newInstance(this, allServiceList, this.backState);

            transaction = fragmentManager.beginTransaction();
//                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            transaction.replace(R.id.main_container, fragment, "allMenuFragment")
                    .commit();

        } else
        {
            backToMainFragment();
        }

    }

    @Override
    public void onBackToHomeWallet(int i)
    {
        isMainFragment = false;
        this.fragment = WalletFragment.newInstance(this, i);//IncreaseInventoryFragment

        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, this.fragment, "IncreaseInventoryFragment")
                .commit();
    }

    @Override
    public void onBackToMatch()
    {
        isMainFragment = false;
        fragment = MatchScheduleFragment.newInstance(this, matchBuyable,0);
        transaction = fragmentManager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, fragment, "leagueTableFragment").commit();

    }


    @Override
    public void onReady(WebServiceClass<GetMenuResponse> response)
    {
        try
        {
            if (response == null || response.info == null)
            {
                MessageAlertDialog dialog = new MessageAlertDialog(MainActivity.this, "", "خظایی رخ داده است.",
                        false, "تلاش مجدد", "", false,MessageAlertDialog.TYPE_MESSAGE, new MessageAlertDialog.OnConfirmListener()
                {
                    @Override
                    public void onConfirmClick()
                    {
                        GetMenuRequest request = new GetMenuRequest();
                        request.setDeviceType(TrapConfig.AndroidDeviceType);
                        request.setDensity(SingletonContext.getInstance().getContext().getResources().getDisplayMetrics().density);
                        SingletonService.getInstance().getMenuService().getMenu(MainActivity.this, request);
                    }

                    @Override
                    public void onCancelClick()
                    {

                    }
                });
                dialog.setCancelable(false);
                dialog.show(getFragmentManager(), "dialogAlert");
                return;
            }
            if (response.info.statusCode != 200)
            {
                MessageAlertDialog dialog = new MessageAlertDialog(MainActivity.this, "", "خظایی رخ داده است.",
                        false, "تلاش مجدد", "", false,MessageAlertDialog.TYPE_MESSAGE, new MessageAlertDialog.OnConfirmListener()
                {
                    @Override
                    public void onConfirmClick()
                    {
                        GetMenuRequest request = new GetMenuRequest();
                        request.setDeviceType(TrapConfig.AndroidDeviceType);
                        request.setDensity(SingletonContext.getInstance().getContext().getResources().getDisplayMetrics().density);
                        SingletonService.getInstance().getMenuService().getMenu(MainActivity.this, request);


                    }

                    @Override
                    public void onCancelClick()
                    {

                    }
                });
                dialog.setCancelable(false);
                dialog.show(getFragmentManager(), "dialogAlert");
          /*  startActivity(new Intent(this, LoginActivity.class));
            finish();*/
            } else
            {
                drawerMenu = response.data.getDrawerMenu();
                chosenServiceList = response.data.getChosenServiceList();
                footballServiceList = response.data.getFootballServiceList();

                Logger.e("--List size--", "chosenServiceList: " + chosenServiceList.size() +
                        "footballServiceList: " + footballServiceList.size());

                EventBus.getDefault().post(drawerMenu);

                getBankList();
            }
        } catch (Exception e)
        {
            //    onError(e.getMessage());

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
                try
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
                    } else
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
                        } else
                        {
                            transaction.replace(R.id.main_container, fragment, "mainFragment")
                                    .commit();
                        }

                    }
                    showPymentResults();
                } catch (Exception e)
                {
                    //onError(e.getMessage());
                }

            }

            @Override
            public void onError(String message)
            {
                isCompleteThreadMatch = true;

                hideLoading();

//                showError(MainActivity.this, "خطا در دریافت اطلاعات از سرور!");
                Logger.e("--showErrorMessage--", message);
            }
        });

        return isCompleteThreadMatch;
    }

    private void showPymentResults()
    {
        if (hasPaymentTicket)
        {
            isMainFragment = false;
            this.fragment = ShowTicketsFragment.newInstance(this, refrenceNumber);

            transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            transaction.replace(R.id.main_container, this.fragment)
                    .commit();
           /* Intent intent = new Intent(MainActivity.this, ShowTicketActivity.class);

            intent.putExtra("RefrenceNumber", refrenceNumber);
            intent.putExtra("isTransactionList", false);

            startActivity(intent);*/

        } else if (hasPaymentCharge || hasPaymentPackageSimcard)
        {
            Intent intent = new Intent(this, PaymentResultChargeActivity.class);
            intent.putExtra("RefrenceNumber", refrenceNumber);
            //intent.putExtra("StatusPayment", true);
            startActivity(intent);
        } else if (hasPaymentIncreaseWallet)
        {
            Intent intent = new Intent(this, PaymentResultIncreaseInventoryActivity.class);
            intent.putExtra("RefrenceNumber", refrenceNumber);
            //intent.putExtra("StatusPayment", true);
            startActivity(intent);
        }
    }

    private void getBankList()
    {
        SingletonService.getInstance().getBankListService().getBankListService(new OnServiceStatus<WebServiceClass<BankListResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<BankListResponse> responseBankList)
            {
//                hideLoading();
                try
                {
                    if (responseBankList == null || responseBankList.info == null)
                    {
//                    Prefs.clear();
                        isCompleteThreadAllServices = true;
                        isCompleteThreadMatch = true;
                        isCompleteThreadNews = true;
                        hideLoading();
                        MessageAlertDialog dialog = new MessageAlertDialog(MainActivity.this, "", "خظایی رخ داده است.",
                                false, "تلاش مجدد", "", false, MessageAlertDialog.TYPE_MESSAGE, new MessageAlertDialog.OnConfirmListener()
                        {
                            @Override
                            public void onConfirmClick()
                            {
                                getBankList();

                            }

                            @Override
                            public void onCancelClick()
                            {

                            }
                        });

               /*     startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();*/

                        return;
                    }
                    if (responseBankList.info.statusCode != 200)
                    {
//                    Prefs.clear();
                        isCompleteThreadAllServices = true;
                        isCompleteThreadMatch = true;
                        isCompleteThreadNews = true;
                        hideLoading();
                        MessageAlertDialog dialog = new MessageAlertDialog(MainActivity.this, "", "خظایی رخ داده است.",
                                false, "تلاش مجدد", "", false, MessageAlertDialog.TYPE_MESSAGE,new MessageAlertDialog.OnConfirmListener()
                        {
                            @Override
                            public void onConfirmClick()
                            {
                                getBankList();

                            }

                            @Override
                            public void onCancelClick()
                            {

                            }
                        });
                  /*  startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();*/

                        return;
                    } else
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
                        getNewsMainContent();
                    }
                } catch (Exception e)
                {
                    //  onError(e.getMessage());
                }


            }

            @Override
            public void onError(String message)
            {
                hideLoading();

                if (Tools.isNetworkAvailable(MainActivity.this))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(MainActivity.this, "خطا در دریافت اطلاعات از سرور!");
                } else
                {
                    showError(MainActivity.this, getString(R.string.networkErrorMessage));

                }

            }
        });
    }

    private Boolean getNewsMainContent()
    {
        isCompleteThreadNews = false;

        SingletonService.getInstance().getNewsService().getNewsMain(new OnServiceStatus<WebServiceClass<NewsMainResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<NewsMainResponse> response)
            {
                try
                {
                    isCompleteThreadNews = true;
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
                        newsMainResponse = response.data;
                        return;
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                    hideLoading();
                }
            }

            @Override
            public void onError(String message)
            {
                isCompleteThreadNews = true;
                hideLoading();

                if (Tools.isNetworkAvailable(MainActivity.this))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(MainActivity.this, "خطا در دریافت اطلاعات از سرور!");
                } else
                {
                    showError(MainActivity.this, getString(R.string.networkErrorMessage));

                }

            }
        });

        return isCompleteThreadNews;
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
                        // startActivityForResult(new Intent(this, LoginActivity.class));
                        //  finish();

                        return;
                    }
                    if (response.info.statusCode == 200)
                    {
                        allServiceList = response.data.getResults();
                    }
                } catch (Exception e)
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

                if (Tools.isNetworkAvailable(MainActivity.this))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(MainActivity.this, "خطا در دریافت اطلاعات از سرور!");
                } else
                {
                    showError(MainActivity.this, getString(R.string.networkErrorMessage));

                }

            }
        }, request);


        return isCompleteThreadAllServices;
    }

    @Override
    public void onError(String message)
    {
        hideLoading();
        String error;


        if (Tools.isNetworkAvailable(this))
        {
            error = "خطا در دریافت اطلاعات از سرور!";
        } else
        {
            error = getString(R.string.networkErrorMessage);
        }


        MessageAlertDialog dialog = new MessageAlertDialog(this, "", error,
                false, "تلاش مجدد", "", false, MessageAlertDialog.TYPE_MESSAGE,
                new MessageAlertDialog.OnConfirmListener()
        {
            @Override
            public void onConfirmClick()
            {
                getAllServicesList();

            }

            @Override
            public void onCancelClick()
            {

            }
        });
        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "messageDialog");
/*
        startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), 100);
        finish();*/
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

//    public void setData(String selectPositionId, int count, int amountForPay, int amountOneTicket, List<Integer> results, Integer stadiumId)
//    {
//
//        this.selectPositionId = selectPositionId;
//        this.count = count;
//        this.amountForPay = amountForPay;
//        this.amountOneTicket = amountOneTicket;
//        this.ticketIdList = results;
//        this.stadiumId = stadiumId;
//    }

    @Override
    public void onToggleSoftKeyboard(boolean isVisible)
    {
        if (isVisible)
        {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.movedown);
            findViewById(R.id.llBottomNavigation).startAnimation(animation);
            findViewById(R.id.llBottomNavigation).setVisibility(View.GONE);
        } else
        {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.moveup);
            findViewById(R.id.llBottomNavigation).startAnimation(animation);
            findViewById(R.id.llBottomNavigation).setVisibility(View.VISIBLE);
        }
//        showDebugToast(this, "Keyboard Visibility: " + isVisible);
    }
}
