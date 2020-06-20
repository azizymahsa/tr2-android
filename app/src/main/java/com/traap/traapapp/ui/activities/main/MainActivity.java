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
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.pixplicity.easyprefs.library.Prefs;
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
import com.traap.traapapp.apiServices.model.lottery.Winner;
import com.traap.traapapp.apiServices.model.matchList.MachListResponse;
import com.traap.traapapp.apiServices.model.matchList.MatchItem;
import com.traap.traapapp.apiServices.model.news.main.NewsMainResponse;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.enums.BarcodeType;
import com.traap.traapapp.enums.LeagueTableParent;
import com.traap.traapapp.enums.MatchScheduleParent;
import com.traap.traapapp.enums.MediaPosition;
import com.traap.traapapp.enums.PredictPosition;
import com.traap.traapapp.enums.SubMediaParent;
import com.traap.traapapp.models.dbModels.BankDB;
import com.traap.traapapp.models.otherModels.newsModel.NewsArchiveClickModel;
import com.traap.traapapp.models.otherModels.paymentInstance.SimChargePaymentInstance;
import com.traap.traapapp.models.otherModels.paymentInstance.SimPackPaymentInstance;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.singleton.SingletonLastPredictItem;
import com.traap.traapapp.singleton.SingletonNewsArchiveClick;
import com.traap.traapapp.ui.activities.points.PointsActivity;
import com.traap.traapapp.ui.activities.card.add.AddCardActivity;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.activities.paymentResult.PaymentResultChargeActivity;
import com.traap.traapapp.ui.activities.paymentResult.PaymentResultIncreaseInventoryActivity;
import com.traap.traapapp.ui.activities.points.PointsActivity;
import com.traap.traapapp.ui.activities.ticket.BuyTicketsActivity;
import com.traap.traapapp.ui.base.BaseMainActivity;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.dialogs.MessageAlertPermissionDialog;
import com.traap.traapapp.ui.drawer.MenuDrawerFragment;
import com.traap.traapapp.ui.fragments.Introducing_the_team.IntroducingTeamFragment;
import com.traap.traapapp.ui.fragments.barcodeReader.BarcodeReaderFragment;
import com.traap.traapapp.ui.fragments.about.AboutFragment;
import com.traap.traapapp.ui.fragments.allMenu.AllMenuFragment;
import com.traap.traapapp.ui.fragments.barcodeReader.BarcodeReaderFragment;
import com.traap.traapapp.ui.fragments.billPay.BillFragment;
import com.traap.traapapp.ui.fragments.competitions.CompationsFragment;
import com.traap.traapapp.ui.fragments.competitions.QuestionCompationFragment;
import com.traap.traapapp.ui.fragments.events.EventsFragment;
import com.traap.traapapp.ui.fragments.gateWay.WalletFragment;
import com.traap.traapapp.ui.fragments.headCoach.HeadCoachFragment;
import com.traap.traapapp.ui.fragments.inviteFriend.InviteFriendsFragment;
import com.traap.traapapp.ui.fragments.lastPast5Match.Last5PastMatchFragment;
import com.traap.traapapp.ui.fragments.leagueTable.LeagueTableMainFragment;
import com.traap.traapapp.ui.fragments.lotteryWinnerList.LotteryWinnerDetailsFragment;
import com.traap.traapapp.ui.fragments.main.BuyTicketAction;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.main.MainFragment;
import com.traap.traapapp.ui.fragments.matchSchedule.MatchScheduleFragment;
import com.traap.traapapp.ui.fragments.matchSchedule.pastResult.PastResultFragment;
import com.traap.traapapp.ui.fragments.media.MediaFragment;
import com.traap.traapapp.ui.fragments.moneyTransfer.MainMoneyTransferFragment;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.fragments.news.NewsArchiveActionView;
import com.traap.traapapp.ui.fragments.news.NewsMainActionView;
import com.traap.traapapp.ui.fragments.news.archive.NewsArchiveFragment;
import com.traap.traapapp.ui.fragments.news.mainNews.NewsMainFragment;
import com.traap.traapapp.ui.fragments.paymentGateWay.SelectPaymentGatewayFragment;
import com.traap.traapapp.ui.fragments.paymentWithoutCard.PaymentWithoutCardFragment;
import com.traap.traapapp.ui.fragments.performanceEvaluation.PerformanceEvaluationFragment;
import com.traap.traapapp.ui.fragments.performanceEvaluation.setEvaluation.PlayerSetEvaluationFragment;
import com.traap.traapapp.ui.fragments.performanceEvaluation.showResult.PlayerEvaluationResultFragment;
import com.traap.traapapp.ui.fragments.photo.archive.PhotosArchiveActionView;
import com.traap.traapapp.ui.fragments.photo.archive.PhotosArchiveFragment;
import com.traap.traapapp.ui.fragments.predict.PredictFragment;
import com.traap.traapapp.ui.fragments.predict.predictResult.PredictResultResultFragment;
import com.traap.traapapp.ui.fragments.simcardCharge.ChargeFragment;
import com.traap.traapapp.ui.fragments.simcardCharge.OnClickContinueSelectPayment;
import com.traap.traapapp.ui.fragments.simcardPack.PackFragment;
import com.traap.traapapp.ui.fragments.suggestions.SuggestionsFragment;
import com.traap.traapapp.ui.fragments.support.SupportFragment;
import com.traap.traapapp.ui.activities.ticket.BuyTicketsActivity;
import com.traap.traapapp.ui.fragments.survey.SurveyFragment;
import com.traap.traapapp.ui.fragments.ticket.SelectPositionFragment;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;
import io.realm.exceptions.RealmException;

//import com.adpdigital.push.AdpPushClient;
//import com.traap.traapapp.ui.fragments.matchSchedule.MatchScheduleFragment2;

public class MainActivity extends BaseMainActivity implements MainActionView, MenuDrawerFragment.FragmentDrawerListener,
        OnServiceStatus
                <WebServiceClass<GetMenuResponse>>, KeyboardUtils.SoftKeyboardToggleListener
        , SelectPositionFragment.OnListFragmentInteractionListener
{
    private Boolean isMainFragment = true;
//    private Boolean isNewsFragment = false;
    private Boolean isFirst = true;
    private ImageView indicator_0, indicator_1, indicator_2;
    private CompositeDisposable disposable = new CompositeDisposable();

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private Toolbar mToolbar;
    private MenuDrawerFragment drawerFragment;
    public static ArrayList<MatchItem> matchList;
    public static ArrayList<GetMenuItemResponse> allServiceList;
    public static NewsMainResponse newsMainResponse;

    private Realm realm;

    private Integer backState = 0;
    private BottomNavigationView bottomNavigationView;

    private Bundle mSavedInstanceState;

    private ArrayList<GetMenuItemResponse> footballServiceList, chosenServiceList, drawerMenu;
    private boolean hasPaymentTicket = false;
    private String refrenceNumber;
    private Boolean isCompleteThreadMatch = false;
    private Boolean isCompleteThreadAllServices = false;
    private ArrayList<MatchItem> matchBuyable;
    private String typeTransaction;
    private boolean hasPaymentCharge = false;
    private boolean hasPaymentPackageSimcard = false;
    private int PAYMENT_STATUS = 0;
    private boolean hasPaymentIncreaseWallet = false;
    private boolean hasPaymentBill=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();

        mSavedInstanceState = savedInstanceState;

        setContainerViewId(R.id.main_container);

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
                }else if (Integer.valueOf(typeTransaction)==TrapConfig.PAYMENT_STATUS_BILL){
                    hasPaymentBill=true;
                }

            }
            catch (Exception e)
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
        }
        else
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

                        setFragment(AllMenuFragment.newInstance(this, allServiceList, 0));
                        replaceFragment(getFragment(), "allMenuFragment");
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
//                        setFragment(MediaPlayersFragment.newInstance(MediaPosition.News, this));
                        setFragment(MediaFragment.newInstance(MediaPosition.News, this));
                        // setFragment(HeadCoachFragment.newInstance(this));
                        replaceFragment(getFragment(), "mediaFragment");
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

        // onIntroduceTeam();

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

        }
        catch (Exception e)
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

    @Override
    public void onBackPressed()
    {
        Logger.e("-LeagueTableParent-", Prefs.getInt("LeagueTableParent", 0) + "");
        try
        {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.END))
            {
                drawer.closeDrawer(GravityCompat.END);
            }
            else if (getFragment() instanceof WalletFragment && !Prefs.getBoolean("isMainWalletFragment", true))
            {
                fragmentList.remove(fragmentList.size() - 1); //remove WalletFragment
//                fragmentList.remove(fragmentList.size()-1); //remove child Fragment and add it again.
                setFragment(WalletFragment.newInstance(this, 0));
                replaceFragment(getFragment(), "DetailsCartFragment");
            }
            else if ((getFragment() instanceof PastResultFragment) &&
                    Prefs.getInt("LeagueTableParent", 0) == LeagueTableParent.MatchScheduleFragment.ordinal())
            {
                Logger.e("-PastResultFragment back-", "-onBackToMatch-");
                onBackToMatch();
            }
            else if (getFragment() instanceof Last5PastMatchFragment || getFragment() instanceof LeagueTableMainFragment)
            {
                onBackToPredict(SingletonLastPredictItem.getInstance().getPredictPosition(),
                        SingletonLastPredictItem.getInstance().getMatchId(),
                        SingletonLastPredictItem.getInstance().getIsPredictable()
                );
            }
            else if (getFragment() instanceof SelectPaymentGatewayFragment)
            {
                fragmentList.remove(fragmentList.size()-1); //remove SelectPaymentGatewayFragment
                fragmentList.remove(fragmentList.size()-1); //remove ChargeFragment and add it again.

                onBackToChargFragment(Prefs.getInt("PAYMENT_STATUS", PAYMENT_STATUS),  Prefs.getInt("ID_BILL",0));
            }
            else if (getFragment() instanceof ChargeFragment && backState == 2 || getFragment() instanceof PackFragment && backState == 2)
            {
//                        setCheckedBNV(bottomNavigationView, 3);
                setCheckedBNV(bottomNavigationView, 2);

                isMainFragment = false;

                fragmentList.remove(fragmentList.size() - 1); //remove ChargeFragment || PackFragment
                fragmentList.remove(fragmentList.size() - 1); //remove AllMenuFragment and add it again.

                setFragment(AllMenuFragment.newInstance(this, allServiceList, backState));
                replaceFragment(getFragment(), "allMenuFragment");

            }
            else if (getFragment() instanceof BillFragment  )
            {
//                        setCheckedBNV(bottomNavigationView, 3);
                setCheckedBNV(bottomNavigationView, 2);

                isMainFragment = false;

                fragmentList.remove(fragmentList.size()-1); //remove BillFragment
                fragmentList.remove(fragmentList.size()-1); //remove AllMenuFragment and add it again.

                setFragment(AllMenuFragment.newInstance(this, allServiceList, backState));
                replaceFragment(getFragment(), "allMenuFragment");

            }
           /* else if (fragment instanceof BuyTicketsActivity && ((BuyTicketsActivity) fragment).getViewpager().getCurrentItem() != 0)
            {
                ((BuyTicketsActivity) fragment).onBackClicked();
            }*/
            else
            {
                if (isMainFragment && fragmentList.size() == 1)
                {
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
                                    }
                                    else
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
                }
                else
                {
//                    setCheckedBNV(bottomNavigationView, 2);

                    if (fragmentList.size() > 2)
                    {
                        Logger.e("-OnBackPressed-", "backToParentFragment");
                        backToParentFragment();
                    }
                    else
                    {
                        Logger.e("-OnBackPressed-", "backToMainFragment");
                        backToMainFragment();
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.getMessage();
            Logger.e("--OnBackPressed Exception--", e.getMessage());
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

        switch (itemNumber)
        {
            case 82: //لیست تراکنش ها
            {
                //  showToast(this, "لیست تراکنش ها", R.color.green);
                isMainFragment = false;

                setFragment(TransactionsListFragment.newInstance(this));
                replaceFragment(getFragment(), "transactionsListFragment");

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

                setFragment(WalletFragment.newInstance(this));
                replaceFragment(getFragment(), "walletFragment");

                break;
            }
            case 85: //مدیریت کارت ها
            {
//                showToast(this, "مدیریت کارت ها", R.color.green);
                isMainFragment = false;

                break;
            }
            case 87: //دعوت از دوستان
            {
                isMainFragment = false;
                setFragment(InviteFriendsFragment.newInstance(this));
                replaceFragment(getFragment(), "inviteFriendsFragment");

                break;
            }
            case 92: //انتقادات و پیشنهادات
            {
                isMainFragment = false;

                setFragment(SuggestionsFragment.newInstance(this));
                replaceFragment(getFragment(), "SuggestionsFragment");
                break;
            }
            case 91: //درباره ما
            {
                isMainFragment = false;

                setFragment(AboutFragment.newInstance(this));
                replaceFragment(getFragment(), "aboutFragment");

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

                //setFragment(QuestionCompationFragment.newInstance(this));
                setFragment(SupportFragment.newInstance(this));

                replaceFragment(getFragment(), "SupportFragment");
                break;
            }
            case 54:  //نظرسنجی
            {

                isMainFragment = false;
                setFragment(SurveyFragment.newInstance(this));
                replaceFragment(getFragment(), "QuestionCompationFragment");
                break;
            }

            case 100:
            {
                onIntroduceTeam();

                break;

            }
            case 89: //راهنما
            {
                if (getFragment() instanceof MainFragment)
                {
                    ((MainFragment) getFragment()).requestGetHelpMenu();
                }
                else
                {
                    backToMainFragment();
                    ((MainFragment) getMainFragment()).requestGetHelpMenu();
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

            case 93:
                setFragment(EventsFragment.newInstance(this));
                replaceFragment(getFragment(), "EventsFragment");
                break;
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
        if (isCompleteThreadAllServices && isCompleteThreadMatch)
        {
//            Logger.e("isCompleteThreadAllServices", String.valueOf(isCompleteThreadAllServices));
//            Logger.e("isCompleteThreadMatch", String.valueOf(isCompleteThreadMatch));

            if (isFirst)
            {
                isFirst = false;
                new Handler().postDelayed(() -> findViewById(R.id.rlLoading).setVisibility(View.GONE), 1200);
            }
            else
            {
                findViewById(R.id.rlLoading).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onBill(String title,Integer idBillType)
    {
        isMainFragment = false;
        String titleBill = title;
        idBillType = idBillType;
        setFragment(BillFragment.newInstance(this, titleBill, idBillType));
        replaceFragment(getFragment(), "billFragment");

    }

    public void onChargeSimCard(Integer backState)
    {
        this.backState = backState;
        isMainFragment = false;

        setFragment(ChargeFragment.newInstance(this, backState));
        replaceFragment(getFragment(), "chargeFragment");

    }

    public void onIntroduceTeam()
    {
        isMainFragment = false;

        setFragment(IntroducingTeamFragment.newInstance(this));
        replaceFragment(getFragment(), "chargeFragment");

    }

    public void onCompationTeam()
    {
        isMainFragment = false;

        setFragment(CompationsFragment.newInstance(this));
        replaceFragment(getFragment(), "CompationsFragment");

    }

    @Override
    public void onHeadCoach(Integer coachId, String title, boolean flagFavorit)
    {
        setFragment(HeadCoachFragment.newInstance(this, coachId, title, flagFavorit));
        replaceFragment(getFragment(), "HeadCoachFragment");

    }

    @Override
    public void onMediaPlayersFragment()
    {

    }

    @Override
    public void openBillPaymentFragment(String url, String textBillPayment, String number, Integer idSelectedBillType,String amount,int
            PAYMENT_STATUS_BILL)
    {
        isMainFragment = false;
        Prefs.putInt("PAYMENT_STATUS", PAYMENT_STATUS_BILL);
        Prefs.putInt("ID_BILL",idSelectedBillType);
        setFragment(SelectPaymentGatewayFragment.newInstance( url, this,
                textBillPayment, number, idSelectedBillType,amount,PAYMENT_STATUS_BILL));
        replaceFragment(getFragment(), "selectPaymentGatewayFragment");

    }

    @Override
    public void onPerformanceEvaluation(Integer matchId, MatchItem matchItem)
    {
        setFragment(PerformanceEvaluationFragment.newInstance(this, matchId, matchItem));
        replaceFragment(getFragment(), "PerformanceEvaluationFragment");
    }

    @Override
    public void onSetPlayerPerformanceEvaluation(Integer matchId, Integer playerId)
    {
        setFragment(PlayerSetEvaluationFragment.newInstance(this, matchId, playerId));
        replaceFragment(getFragment(), "PlayerSetEvaluationFragment");
    }

    @Override
    public void onPlayerPerformanceEvaluationResult(Integer matchId, Integer playerId)
    {
        setFragment(PlayerEvaluationResultFragment.newInstance(this, matchId, playerId));
        replaceFragment(getFragment(), "PlayerEvaluationResultFragment");
    }

    public void onPackSimCard(Integer status)
    {
        isMainFragment = false;
        this.backState = status;
        setFragment(PackFragment.newInstance(this, backState));
        replaceFragment(getFragment(), "packFragment");
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
                        }
                        catch (Exception e)
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

        setFragment(BarcodeReaderFragment.newInstance(this));
        replaceFragment(getFragment(), "barcodeReaderFragment");

    }

    @Override
    public void onPaymentWithoutCard()
    {
        isMainFragment = false;

        setFragment(PaymentWithoutCardFragment.newInstance(this));
        replaceFragment(getFragment(), "paymentWithoutCardFragment");
    }

    @Override
    public void doTransferMoney()
    {
        isMainFragment = false;

        setFragment(MainMoneyTransferFragment.newInstance(this));
        replaceFragment(getFragment(), "moneyTransferFragment");
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
                {
                    hasPhone = "true";
                }
                else
                {
                    hasPhone = "false";
                }

                if (Boolean.parseBoolean(hasPhone))
                {
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (phones.moveToNext())
                    {

                        OnSelectContact onSelectContact = new OnSelectContact();
                        onSelectContact.setName(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) == null ? "" : phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                        onSelectContact.setNumber(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll(" ", "").replace("0098", "0").replace(getString(R.string.plus) + "98", "0"));
                        if (getFragment() instanceof ChargeFragment)
                        {
                            ((ChargeFragment) getFragment()).onSelectContact(onSelectContact);

                        }
                        else if (getFragment() instanceof PackFragment)
                        {
                            ((PackFragment) getFragment()).onSelectContact(onSelectContact);
                        }
                        else if (getFragment() instanceof BillFragment)
                        {
                            ((BillFragment) getFragment()).onSelectContact(onSelectContact);
                        }
                        else if (getFragment() instanceof WalletFragment)
                        {
                            ((WalletFragment) getFragment()).onSelectContact(onSelectContact);

                        }

                    }
                    phones.close();
                }


            }
            cursor.close();

        }
        else if (resultCode == Activity.RESULT_OK && requestCode == 22)
        {
            showToast(this, "کارت جدید با موفقیت ذخیره شد.", R.color.green);

        } else if (resultCode == Activity.RESULT_OK && requestCode == 33)
        {
            fragmentList.remove(fragmentList.size() - 1); //remove SelectPaymentGatewayFragment
            fragmentList.remove(fragmentList.size() - 1); //remove ChargeFragment and add it again.
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == 33)
        {
            fragmentList.remove(fragmentList.size() - 1); //remove SelectPaymentGatewayFragment
            fragmentList.remove(fragmentList.size() - 1); //remove ChargeFragment and add it again.
            int PAYMENTstatus = data.getIntExtra("PaymentStatus", 0);

            onBackToChargFragment(PAYMENTstatus, 0);
        }else if ( resultCode == Activity.RESULT_OK && requestCode ==44){


            onBackToHomeWallet(0);
        }
        else if (resultCode == Activity.RESULT_OK)
        {
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
        try
        {
            showToast(this, message, R.color.red);
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public void backToMainFragment()
    {
//        setCheckedBNV(bottomNavigationView, 2);
        setCheckedBNV(bottomNavigationView, 1);

        isMainFragment = true;
////        isNewsFragment = false;

//        if (getMainFragment() != null)
//        {
//            setFragment(getMainFragment());
//        }
//        else
//        {
//            Logger.e("--mainFragment--", "--null--");
//            setFragment(MainFragment.newInstance(MainActivity.this, footballServiceList, chosenServiceList, matchList));
//        }
//        replaceFragment(getFragment(), "mainFragment");
        removeAndBackToMainFragment();
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
        setFragment(MatchScheduleFragment.newInstance(this, MatchScheduleParent.MainActivity, matchBuyable, 0));
        replaceFragment(getFragment(), "leagueTableFragment");
    }

    @Override
    public void onPredict(PredictPosition position, Integer matchId, Boolean isPredictable)
    {
        isMainFragment = false;
        setFragment(PredictFragment.newInstance(this, position, matchId, isPredictable));
        replaceFragment(getFragment(), "predictFragment");
    }

    @Override
    public void onBackToPredict(PredictPosition position, Integer matchId, Boolean isPredictable)
    {
        fragmentList.remove(fragmentList.size() - 1); //remove current Fragment
        fragmentList.remove(fragmentList.size() - 1); //remove Predict Fragment

        isMainFragment = false;
        setFragment(PredictFragment.newInstance(this, position, matchId, isPredictable));
        replaceFragment(getFragment(), "predictFragment");
    }

    @Override
    public void onPredictLeagueTable(Integer teamId, Integer matchId, Boolean isPredictable)
    {
        isMainFragment = false;
        setFragment(LeagueTableMainFragment.newInstance(this, teamId, matchId, isPredictable));
        replaceFragment(getFragment(), "leagueTableMainFragment");
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
        setCheckedBNV(bottomNavigationView, 0);

        isMainFragment = false;
        setFragment(NewsMainFragment.newInstance(new NewsMainActionView()
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
        }));
        replaceFragment(getFragment(), "newsMainFragment");
    }

    private void openMainNewsFragment()
    {
        isMainFragment = false;
        setFragment(NewsMainFragment.newInstance(new NewsMainActionView()
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
        }));
        replaceFragment(getFragment(), "newsMainFragment");
    }

    @Override
    public void onFootBallServiceTwo()
    {
        setCheckedBNV(bottomNavigationView, 0);

        isMainFragment = false;
        setFragment(VideosMainFragment.newInstance(new VideosMainActionView()
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
        }));

        replaceFragment(getFragment(), "videosMainFragment");
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
        setFragment(NewsArchiveFragment.newInstance(parent, mediaPosition, false, new NewsArchiveActionView()
        {
            @Override
            public void backToMediaFragment(MediaPosition mediaPosition)
            {
                onBackPressed();
//                setFragment(MediaFragment.newInstance(mediaPosition, MainActivity.this));
//                replaceFragment(getFragment(), "mediaFragment");
            }

            @Override
            public void backToMainFragment()
            {
//                MainActivity.this.backToMainFragment();
                onBackPressed();
            }

            @Override
            public void backToMainNewsFragment()
            {
//                openMainNewsFragment();
                onBackPressed();
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
        }));

        replaceFragment(getFragment(), "newsArchiveCategoryFragment");
    }

    @Override
    public void onNewsFavoriteClick(SubMediaParent parent, MediaPosition mediaPosition)
    {
        isMainFragment = false;
        setFragment(NewsArchiveFragment.newInstance(parent, mediaPosition, true, new NewsArchiveActionView()
        {
            @Override
            public void backToMediaFragment(MediaPosition mediaPosition)
            {
                onBackPressed();
//                setFragment(MediaFragment.newInstance(mediaPosition, MainActivity.this));
//                replaceFragment(getFragment(), "mediaFragment");
            }

            @Override
            public void backToMainFragment()
            {
//                MainActivity.this.backToMainFragment();
                onBackPressed();
            }

            @Override
            public void backToMainNewsFragment()
            {
//                openMainNewsFragment();
                onBackPressed();
            }

            @Override
            public void onNewsArchiveFragment(SubMediaParent parent)
            {
//                onNewsArchiveClick(SubMediaParent.MainFragment, MediaPosition.News);
                onBackPressed();
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
        }));

        replaceFragment(getFragment(), "newsArchiveCategoryFragment");
    }

    @Override
    public void onPhotosArchiveClick(SubMediaParent parent, MediaPosition mediaPosition)
    {
        isMainFragment = false;
        setFragment(PhotosArchiveFragment.newInstance(this, parent, mediaPosition, false, new PhotosArchiveActionView()
        {
            @Override
            public void backToMediaFragment(MediaPosition mediaPosition)
            {
//                setFragment(MediaFragment.newInstance(MediaPosition.ImageGallery, MainActivity.this));
//                replaceFragment(getFragment(), "mediaFragment");
                onBackPressed();
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
        }));

        replaceFragment(getFragment(), "photosArchiveCategoryFragment");
    }

    @Override
    public void onPhotosFavoriteClick(SubMediaParent parent, MediaPosition mediaPosition)
    {
        isMainFragment = false;
        setFragment(PhotosArchiveFragment.newInstance(this, parent, mediaPosition, true, new PhotosArchiveActionView()
        {
            @Override
            public void backToMediaFragment(MediaPosition mediaPosition)
            {
//                setFragment(MediaFragment.newInstance(MediaPosition.ImageGallery, MainActivity.this));
//                replaceFragment(getFragment(), "mediaFragment");
                onBackPressed();
            }

            @Override
            public void backToMainPhotosFragment()
            {
                onBackPressed();
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
        }));
        replaceFragment(getFragment(), "photosArchiveCategoryFragment");
    }

    @Override
    public void onVideosArchiveClick(SubMediaParent parent, MediaPosition mediaPosition)
    {
        isMainFragment = false;
        setFragment(VideosArchiveFragment.newInstance(parent, mediaPosition, false, new VideosArchiveActionView()
        {
            @Override
            public void backToMainVideosFragment()
            {
//                onMainVideoClick();
                onBackPressed();
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
//                setFragment(MediaFragment.newInstance(MediaPosition.VideoGallery, MainActivity.this));
//                replaceFragment(getFragment(), "mediaFragment");
                onBackPressed();
            }

            @Override
            public void backToMainFragment()
            {
//                MainActivity.this.backToMainFragment();
                onBackPressed();
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
        }));

        replaceFragment(getFragment(), "videosArchiveCategoryFragment");
    }

    @Override
    public void onVideosFavoriteClick(SubMediaParent parent, MediaPosition mediaPosition)
    {
        isMainFragment = false;
        setFragment(VideosArchiveFragment.newInstance(parent, mediaPosition, true, new VideosArchiveActionView()
        {
            @Override
            public void backToMainVideosFragment()
            {
//                onMainVideoClick();
                onBackPressed();
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
//                setFragment(MediaFragment.newInstance(MediaPosition.VideoGallery, MainActivity.this));
//                replaceFragment(getFragment(), "mediaFragment");
                onBackPressed();
            }

            @Override
            public void backToMainFragment()
            {
//                MainActivity.this.backToMainFragment();
                onBackPressed();
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
        }));

        replaceFragment(getFragment(), "videosArchiveCategoryFragment");
    }

    @Override
    public void onMainVideoClick()
    {
        isMainFragment = false;
        setFragment(VideosMainFragment.newInstance(new VideosMainActionView()
        {
            @Override
            public void backToMainFragment()
            {
//                MainActivity.this.backToMainFragment();
                onBackPressed();
            }

            @Override
            public void backToMainVideosFragment()
            {
//                onMainVideoClick();
                onBackPressed();
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
        }));
        replaceFragment(getFragment(), "videosMainFragment");
    }

    @Override
    public void onMainQuestionClick(Integer idQuees)
    {
        isMainFragment = false;
        setFragment(QuestionCompationFragment.newInstance(this, idQuees));
        replaceFragment(getFragment(), "QuestionCompationFragment");
    }

    @Override
    public void openPastResultFragment(LeagueTableParent parent, String matchId, Boolean isPredictable, String teamId, String imageLogo, String logoTitle)
    {
        isMainFragment = false;
        setFragment(PastResultFragment.newInstance(parent, this, matchId, isPredictable, teamId, imageLogo, logoTitle));
        replaceFragment(getFragment(), "pastResultFragment");
    }

    @Override
    public void openPackPaymentFragment(OnClickContinueSelectPayment onClickContinueSelectPayment, String urlPayment, int imageDrawable, String title, String amount, SimPackPaymentInstance paymentInstance, String mobile, int PAYMENT_STATUS)
    {

        isMainFragment = false;
        Prefs.putInt("PAYMENT_STATUS", PAYMENT_STATUS);
        setFragment(SelectPaymentGatewayFragment.newInstance(PAYMENT_STATUS, onClickContinueSelectPayment, urlPayment, this, imageDrawable,
                title, amount, paymentInstance, mobile));
        replaceFragment(getFragment(), "selectPaymentGatewayFragment");

    }

    @Override
    public void openChargePaymentFragment(OnClickContinueSelectPayment onClickContinueSelectPayment, String urlPayment, int icon_payment, String title, String amount, SimChargePaymentInstance paymentInstance, String mobile, int PAYMENT_STATUS)
    {

        isMainFragment = false;
        Prefs.putInt("PAYMENT_STATUS", PAYMENT_STATUS);
        setFragment(SelectPaymentGatewayFragment.newInstance(PAYMENT_STATUS, onClickContinueSelectPayment, urlPayment, this, icon_payment,
                title, amount, paymentInstance, mobile));
        replaceFragment(getFragment(), "selectPaymentGatewayFragment");
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
        setFragment(SelectPaymentGatewayFragment.newInstance(PAYMENT_STATUS, onClickContinueSelectPayment, urlPayment, this, imageDrawable,
                title, amount, paymentInstance, mobile));
        replaceFragment(getFragment(), "selectPaymentGatewayFragment");
       /* SelectPaymentGatewayFragment fragment2 = new SelectPaymentGatewayFragment(urlPayment, mainView, R.drawable.icon_payment_ticket,
                title, Utility.priceFormat(amount));
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment2);
        fragmentTransaction.commit();*/

    }

    @Override
    public void openWebView(MainActionView mainView, String uRl, String gds_token, String title)
    {
        isMainFragment = false;
        setFragment(WebFragment.newInstance(mainView, uRl, gds_token, title));
        replaceFragment(getFragment(), "WebFragment");

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
                        }
                        else
                        {
                            showAlert(MainActivity.this, response.info.message, 0);
                        }
                    }
                    else
                    {
                        showAlert(MainActivity.this, response.info.message, 0);
                    }
                    buyTicketAction.onEndListener();
                }
                catch (Exception e)
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
                }
                else
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
                fragmentList.remove(fragmentList.size() - 1); //remove current Fragment
//                setFragment(PredictFragment.newInstance(MainActivity.this, matchId, isPredictable));
//                replaceFragment(getFragment(), "predictFragment");
                onPredict(PredictPosition.PredictResult, matchId, isPredictable);
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
    public void onBackToChargFragment(int PAYMENT_STATUS, Integer idBill)
    {

        if (PAYMENT_STATUS == 3)
        {
            isMainFragment = false;
            setFragment(ChargeFragment.newInstance(this, backState));
            replaceFragment(getFragment(), "ChargeFragment");
        }
        else if (PAYMENT_STATUS == 4)
        {
            isMainFragment = false;
            setFragment(PackFragment.newInstance(this, backState));
            replaceFragment(getFragment(), "PackFragment");
        }
        else if (PAYMENT_STATUS == 13)
        {
            isMainFragment = false;
            setFragment(WalletFragment.newInstance(this, 1));//IncreaseInventoryFragment
            replaceFragment(getFragment(), "IncreaseInventoryFragment");
        }else if (PAYMENT_STATUS==TrapConfig.PAYMENT_STATUS_BILL)
        {
            isMainFragment = false;
            setFragment(BillFragment.newInstance(this,idBill));
            replaceFragment(getFragment(), "BillFragment");
        }
    }

    @Override
    public void backToAllServicePackage(Integer backState)
    {

        if (backState == 2)
        {
            setCheckedBNV(bottomNavigationView, 2);

            isMainFragment = false;

            setFragment(AllMenuFragment.newInstance(this, allServiceList, backState));
            replaceFragment(getFragment(), "allMenuFragment");
        }
        else
        {
            backToMainFragment();
        }
    }

    @Override
    public void onBackToHomeWallet(int i)
    {
        fragmentList.remove(fragmentList.size() - 1); //remove current Fragment
        // fragmentList.remove(fragmentList.size()-1); //remove IncreaseInventoryFragment and add it again.

        isMainFragment = false;
        setFragment(WalletFragment.newInstance(this, i));//IncreaseInventoryFragment
        replaceFragment(getFragment(), "IncreaseInventoryFragment");
    }

    @Override
    public void onBackToMatch()
    {
        fragmentList.remove(fragmentList.size() - 1); //remove current Fragment
        fragmentList.remove(fragmentList.size() - 1); //remove leagueTableFragment and add it again.

        isMainFragment = false;
//        setFragment(MatchScheduleFragment2.newInstance(this, MatchScheduleParent.MainActivity, matchBuyable,
        setFragment(MatchScheduleFragment.newInstance(this, MatchScheduleParent.MainActivity, matchBuyable,
                Prefs.getInt("selectedTab", 0)));
        replaceFragment(getFragment(), "leagueTableFragment");

    }

    @Override
    public void onChangeMediaPosition(MediaPosition mediaPosition)
    {
//        showDebugToast(this, mediaPosition + " # " + fragmentList.get(fragmentList.size()-1).getTag());
        fragmentList.remove(fragmentList.size() - 1);
        setFragment(MediaFragment.newInstance(mediaPosition, this));
        fragmentList.add(getFragment());
//        showDebugToast(this, mediaPosition + " # " + fragmentList.get(fragmentList.size()-1).getTag());
    }

    @Override
    public void onShowDetailWinnerList(List<Winner> winnerList)
    {
        isMainFragment = false;

        setFragment(LotteryWinnerDetailsFragment.newInstance(MainActivity.this, winnerList));
        replaceFragment(getFragment(), "lotteryWinnerDetailsFragment");
    }

    @Override
    public void onShowLast5PastMatch(Integer teamLiveScoreId)
    {
        isMainFragment = false;
        setFragment(Last5PastMatchFragment.newInstance(MainActivity.this, teamLiveScoreId));
        replaceFragment(getFragment(), "last5PastMatchFragment");
    }


    @Override
    public void onReady(WebServiceClass<GetMenuResponse> response)
    {
        try
        {
            if (response == null || response.info == null)
            {
                MessageAlertDialog dialog = new MessageAlertDialog(MainActivity.this, "", "خظایی رخ داده است.",
                        false, "تلاش مجدد", "", false, MessageAlertDialog.TYPE_MESSAGE, new MessageAlertDialog.OnConfirmListener()
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
                        false, "تلاش مجدد", "", false, MessageAlertDialog.TYPE_MESSAGE, new MessageAlertDialog.OnConfirmListener()
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

            }
            else
            {
                drawerMenu = response.data.getDrawerMenu();
                chosenServiceList = response.data.getChosenServiceList();
                footballServiceList = response.data.getFootballServiceList();

                Logger.e("--List size--", "chosenServiceList: " + chosenServiceList.size() +
                        "footballServiceList: " + footballServiceList.size());

                EventBus.getDefault().post(drawerMenu);

                getBankList();
            }
        }
        catch (Exception e)
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
                    }
                    else
                    {
                        matchList = (ArrayList<MatchItem>) responseMatchList.data.getMatchList();

                        setMyFragmentManager(getSupportFragmentManager());

                        setFragment(MainFragment.newInstance(MainActivity.this, footballServiceList, chosenServiceList, matchList));

                        setMainFragment(getFragment());
                        if (mSavedInstanceState == null)
                        {
                            addFragment(getFragment(), "mainFragment");
                        }
                        else
                        {
                            replaceFragment(getFragment(), "mainFragment");
                        }

                    }
                    showPymentResults();
                }
                catch (Exception e)
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
      /*      setFragment(ShowTicketsFragment.newInstance(this, refrenceNumber));
            replaceFragment(getFragment(), "ShowTicketsFragment");*/

            Intent intent = new Intent(this, PaymentResultChargeActivity.class);
            intent.putExtra("RefrenceNumber", refrenceNumber);

           /* Intent intent = new Intent(MainActivity.this, ShowTicketActivity.class);

            intent.putExtra("RefrenceNumber", refrenceNumber);
            intent.putExtra("isTransactionList", false);

            startActivity(intent);*/

        }
        else if (hasPaymentCharge || hasPaymentPackageSimcard)
        {
            Intent intent = new Intent(this, PaymentResultChargeActivity.class);
            Log.d("refrencee", refrenceNumber);
            intent.putExtra("RefrenceNumber", refrenceNumber);
            //intent.putExtra("StatusPayment", true);
            startActivity(intent);
        }
        else if (hasPaymentIncreaseWallet)
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
                  /*  startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();*/

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
                        }
                        catch (RealmException ex)
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
                                }
                                catch (RealmException e)
                                {
                                    e.printStackTrace();
                                }
                            });
                        }

                        Logger.e("--BankDB size after delete--", "size: " + realm.where(BankDB.class).findAll().size());

                        getMatchList();
                        getAllServicesList();
//                        getNewsMainContent();
                    }
                }
                catch (Exception e)
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

                if (Tools.isNetworkAvailable(MainActivity.this))
                {
                    Logger.e("-OnError-", "Error: " + message);
                    showError(MainActivity.this, "خطا در دریافت اطلاعات از سرور!");
                }
                else
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
        }
        else
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
            //   Animation animation = AnimationUtils.loadAnimation(this, R.anim.movedown);
            //    findViewById(R.id.llBottomNavigation).startAnimation(animation);
            findViewById(R.id.llBottomNavigation).setVisibility(View.GONE);
        } else
        {
            //  Animation animation = AnimationUtils.loadAnimation(this, R.anim.moveup);
            //   findViewById(R.id.llBottomNavigation).startAnimation(animation);
            findViewById(R.id.llBottomNavigation).setVisibility(View.VISIBLE);
        }
//        showDebugToast(this, "Keyboard Visibility: " + isVisible);
    }

//    @Override
//    public void onPointerCaptureChanged(boolean hasCapture)
//    {
//
//    }


    @Override
    protected void onDestroy()
    {
        disposable.dispose();
        super.onDestroy();
    }
}
