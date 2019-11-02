package ir.trap.tractor.android.ui.activities.main;

//import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
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
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.exceptions.RealmException;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.contact.OnSelectContact;
import ir.trap.tractor.android.apiServices.model.getBankList.response.Bank;
import ir.trap.tractor.android.apiServices.model.getBankList.response.BankListResponse;
import ir.trap.tractor.android.apiServices.model.getMenu.request.GetMenuRequest;
import ir.trap.tractor.android.apiServices.model.getMenu.response.GetMenuItemResponse;
import ir.trap.tractor.android.apiServices.model.getMenu.response.GetMenuResponse;
import ir.trap.tractor.android.apiServices.model.matchList.MatchItem;
import ir.trap.tractor.android.conf.TrapConfig;
import ir.trap.tractor.android.enums.BarcodeType;
import ir.trap.tractor.android.models.dbModels.BankDB;
import ir.trap.tractor.android.singleton.SingletonContext;
import ir.trap.tractor.android.ui.activities.card.add.AddCardActivity;
import ir.trap.tractor.android.ui.activities.login.LoginActivity;
import ir.trap.tractor.android.ui.base.BaseActivity;
import ir.trap.tractor.android.ui.dialogs.MessageAlertDialog;
import ir.trap.tractor.android.ui.drawer.MenuDrawer;
import ir.trap.tractor.android.ui.fragments.BarcodeReaderFragment;
import ir.trap.tractor.android.ui.fragments.allMenu.AllMenuFragment;
import ir.trap.tractor.android.ui.fragments.billPay.BillFragment;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;
import ir.trap.tractor.android.ui.fragments.main.MainFragment;
import ir.trap.tractor.android.ui.fragments.moneyTransfer.MoneyTransferFragment;
import ir.trap.tractor.android.ui.fragments.paymentWithoutCard.PaymentWithoutCardFragment;
import ir.trap.tractor.android.ui.fragments.simcardCharge.ChargeFragment;
import ir.trap.tractor.android.ui.fragments.simcardPack.PackFragment;
import ir.trap.tractor.android.ui.fragments.ticket.BuyTickets;
import ir.trap.tractor.android.ui.fragments.ticket.SelectPositionFragment;
import ir.trap.tractor.android.utilities.Logger;

public class MainActivity extends BaseActivity implements MainActionView, MenuDrawer.FragmentDrawerListener,
        OnServiceStatus<WebServiceClass<GetMenuResponse>>
        , SelectPositionFragment.OnListFragmentInteractionListener
{
    private Boolean isMainFragment = true;

    private Toolbar mToolbar;
    private MenuDrawer drawerFragment;

    private Realm realm;

    private Fragment matchBuyable;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
//    private View btnBuyTicket;

    private BottomNavigationView bottomNavigationView;

    private Bundle mSavedInstanceState;

    private ArrayList<GetMenuItemResponse> footballServiceList, chosenServiceList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSavedInstanceState = savedInstanceState;

        realm = Realm.getDefaultInstance();

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
            //switch matchBuyable
            switch (itemId)
            {
                case R.id.tab_market:
                {
                    if (!bottomNavigationView.getMenu().getItem(0).isChecked())
                    {
                        /*setCheckedBNV(bottomNavigationView, 0);
                        isMainFragment = false;

                        matchBuyable = HistoryFragment.newInstance(this);
                        transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                        transaction.replace(R.id.main_container, matchBuyable)
                                .commit();*/
                    }
                    break;
                }
                case R.id.tab_all_services:
                {
                    if (!bottomNavigationView.getMenu().getItem(3).isChecked())
                    {
                        setCheckedBNV(bottomNavigationView, 3);

                        isMainFragment = false;

                        matchBuyable = AllMenuFragment.newInstance(this);

                        transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        transaction.replace(R.id.main_container, matchBuyable)
                                .commit();
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
                    if (!bottomNavigationView.getMenu().getItem(1).isChecked())
                    {
                        setCheckedBNV(bottomNavigationView, 1);
                    }
                    break;
                }
                case R.id.tab_payment:
                {
                    if (!bottomNavigationView.getMenu().getItem(0).isChecked())
                    {
                        setCheckedBNV(bottomNavigationView, 0);

                        isMainFragment = false;

                        matchBuyable = PaymentWithoutCardFragment.newInstance(this);
                        transaction = fragmentManager.beginTransaction();
                        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

                        transaction.replace(R.id.main_container, matchBuyable)
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
//        matchBuyable = MainFragment.newInstance(this, chosenServiceList, footballServiceList);
//
//        transaction = fragmentManager.beginTransaction();
//
//        if (mSavedInstanceState == null)
//        {
//            transaction.add(R.id.main_container, matchBuyable)
//                    .commit();
//        }
//        else
//        {
//            transaction.replace(R.id.main_container, matchBuyable)
//                    .commit();
//        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (matchBuyable == null)
        {
//            matchBuyable = MainFragment.newInstance(this, chosenServiceList, footballServiceList);
//            transaction = fragmentManager.beginTransaction();
//            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//            try
//            {
//                if (mSavedInstanceState == null)
//                {
//                    transaction.add(R.id.main_container, matchBuyable)
//                            .commit();
////
//                } else
//                {
//                    transaction.replace(R.id.main_container, matchBuyable)
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
                setCheckedBNV(bottomNavigationView, 2);

                backToMainFragment();
            }
        }

    }

    @Override
    public void onDrawerItemSelected(View view, int itemNumber)
    {
        Logger.e("-onDrawerItemSelected-", "selected " + itemNumber);

        final Intent intent = new Intent();
        switch (itemNumber)
        {
            case 1:
            {
                showToast(this, "لیست تراکنش ها", R.color.green);
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
                showToast(this, "درباره ما", R.color.green);

                break;
            }
            case 8:
            {
                showToast(this, "تنظیمات", R.color.green);

                break;
            }
            case 9:
            {
                showToast(this, "پشتیبانی", R.color.green);

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
                        Prefs.clear();
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

        }
    }

    @Override
    public void onUserProfileClick()
    {
        showToast(this, "حساب کاربری من", R.color.green);
        //go to UserProfileActivity
    }

    @Override
    public void showLoading()
    {
//        findViewById(R.id.llLoading).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading()
    {
//        findViewById(R.id.llLoading).setVisibility(View.GONE);
//        runOnUiThread(() ->
//        {
//        });
    }

    @Override
    public void onBill()
    {
        isMainFragment = false;
        String titleBill = "قبض تلفن همراه";
        int idBillType = 5;
        matchBuyable = BillFragment.newInstance(this, titleBill, idBillType);
        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, matchBuyable)
                .commit();
    }

    @Override
    public void onChargeSimCard()
    {
        isMainFragment = false;

        matchBuyable = ChargeFragment.newInstance(this);

        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, matchBuyable)
                .commit();

    }

    @Override
    public void onPackSimCard()
    {
        isMainFragment = false;

        matchBuyable = PackFragment.newInstance(this);
        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, matchBuyable)
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

        matchBuyable = BarcodeReaderFragment.newInstance(this);
        //  transaction.commitAllowingStateLoss();
        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, matchBuyable)
                .commit();

    }

    @Override
    public void onPaymentWithoutCard()
    {
        isMainFragment = false;

        // matchBuyable = PaymentWithoutCardFragment2.newInstance(this);
        matchBuyable = PaymentWithoutCardFragment.newInstance(this);
        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, matchBuyable)
                .commit();
    }

    @Override
    public void doTransferMoney()
    {
        isMainFragment = false;

        matchBuyable = MoneyTransferFragment.newInstance(this);
        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, matchBuyable)
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
                        if (matchBuyable instanceof ChargeFragment)
                        {
                            ((ChargeFragment) matchBuyable).onSelectContact(onSelectContact);

                        } else if (matchBuyable instanceof PackFragment)
                        {
                            ((PackFragment) matchBuyable).onSelectContact(onSelectContact);
                        } else if (matchBuyable instanceof BillFragment)
                        {
                            ((BillFragment) matchBuyable).onSelectContact(onSelectContact);
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
        isMainFragment = true;

        matchBuyable = MainFragment.newInstance(this, footballServiceList, chosenServiceList);
        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

        transaction.replace(R.id.main_container, matchBuyable)
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
        isMainFragment = false;
        this.matchBuyable = BuyTickets.newInstance(this, matchBuyable);

        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        transaction.replace(R.id.main_container, this.matchBuyable)
                .commit();
    }

    @Override
    public void onReady(WebServiceClass<GetMenuResponse> response)
    {
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

            fragmentManager = getSupportFragmentManager();
            matchBuyable = MainFragment.newInstance(this, footballServiceList, chosenServiceList);

            transaction = fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

            if (mSavedInstanceState == null)
            {
                transaction.add(R.id.main_container, matchBuyable)
                        .commit();
            } else
            {
                transaction.replace(R.id.main_container, matchBuyable)
                        .commit();
            }

            SingletonService.getInstance().getBankListService().getBankListService(new OnServiceStatus<WebServiceClass<BankListResponse>>()
            {
                @Override
                public void onReady(WebServiceClass<BankListResponse> responseBankList)
                {
                    hideLoading();

                    if (responseBankList == null || responseBankList.info == null)
                    {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();

                        return;
                    }
                    if (responseBankList.info.statusCode != 200)
                    {
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
//                matchBuyable = BuyTickets.newInstance(this);
//                transaction = fragmentManager.beginTransaction();
//                transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
//                transaction.replace(R.id.main_container, matchBuyable)
//                        .commit();
//                break;
//        }
//    }
}
