package ir.trap.tractor.android.ui.drawer;

import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.models.otherModels.MenuItems;
import ir.trap.tractor.android.ui.adapters.MenuDrawerAdapter;

public class MenuDrawer extends Fragment
{
//    private SharedPref pref;

    private static String TAG = MenuDrawer.class.getSimpleName();

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private MenuDrawerAdapter adapter;
    private View containerView;

    private FragmentDrawerListener drawerListener;

    private List<MenuItems> dataList;
    private TextView userName;
    private TextView userMobile;
    private TextView user_invite_code;
    private TextView user_score;
    private TextView user_sub_state;
    private LinearLayout lay_user_subState;

    public MenuDrawer()
    {

    }

    public void setDrawerListener(FragmentDrawerListener listener)
    {
        this.drawerListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.menudrawer_fragment, container, false);

//        pref = new SharedPref(getActivity());

        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        userName = (TextView) layout.findViewById(R.id.user_name);
        userMobile = (TextView) layout.findViewById(R.id.user_mobile);
        user_invite_code = (TextView) layout.findViewById(R.id.user_invite_code);
        user_score = (TextView) layout.findViewById(R.id.user_score);
//        user_sub_state = (TextView) layout.findViewById(R.id.user_sub_state);
//        lay_user_subState = (LinearLayout) layout.findViewById(R.id.lay_user_subState);

        dataList = new ArrayList<MenuItems>();

//        dataList.add(new MenuItems(1, "مشاهده لیست خرید", R.string.shopping_cart_icons));
//        dataList.add(new MenuItems("مشاهده لیست دانلودهای قبلی", R.string.download_icons));
//        if (!pref.getToken().isEmpty())
//        {

            dataList.add(new MenuItems(2, "مشاهده میزان موجودی", R.drawable.img_failure));
//        dataList.add(new MenuItems(3, "شارژ حساب با کوپن تخفیف", R.string.add_lib_icons));
            dataList.add(new MenuItems(3, "اشتراک امانات", R.drawable.img_failure));
            dataList.add(new MenuItems(4, "لیست امانات کتاب", R.drawable.img_failure));
            dataList.add(new MenuItems(5, "شارژ حساب از درگاه بانکی", R.drawable.img_failure));
            dataList.add(new MenuItems(6, "ویرایش اطلاعات کاربری", R.drawable.img_failure));
//        }
//        else
//        {
//            dataList.add(new MenuItems(1, "ورود/عضویت", R.string.person_icons));
//            userMobile.setVisibility(View.GONE);
//            user_invite_code.setVisibility(View.GONE);
//            user_score.setVisibility(View.GONE);
//            lay_user_subState.setVisibility(View.GONE);
//        }

//        dataList.add(new MenuItems(7,"پیشنهاد به دوستان", R.string.add_person_icons));
//        dataList.add(new MenuItems("آگه ساز", R.string.notification_bell_icons, 0));
//        dataList.add(new MenuItems("تنظیمات نرم افزار", R.string.settings_icons));
//        if (!pref.getToken().isEmpty())
//        {
//            dataList.add(new MenuItems(10, "خروج از حساب کاربری", R.string.cancel_icons));
//        }

        adapter = new MenuDrawerAdapter(getActivity(), dataList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new ClickListener()
        {
            @Override
            public void onClick(View view, int position)
            {
                drawerListener.onDrawerItemSelected(view, dataList.get(position).getId());
                if (position != 0)
                {
                    mDrawerLayout.closeDrawer(containerView);
                }

            }

            @Override
            public void onLongClick(View view, int position)
            {

            }
        }));

//        EventBus.getDefault().register(this);
        return layout;
    }

//    @Subscribe
//    public void userLoaded(UserProfile userProfile)
//    {
//        if ((userProfile.getFirst_name() ==  null) && (userProfile.getLast_name() == null))
//        {
//            userName.setText("UserName");
//        }
//        else
//        {
//            userName.setText(userProfile.getFullName());
//        }
//        if (userProfile.getMobile() != null)
//        {
//            userMobile.setText(userProfile.getMobile());
//            user_invite_code.setText("کد دعوت شما: " + userProfile.getInvite_code());
//            user_score.setText("امتیاز شما: " + userProfile.getScore());
//
//            if (userProfile.isMember())
//            {
//                user_sub_state.setText("فعال");
//                user_sub_state.setTextColor(getResources().getColor(R.color.light_green));
//            }
//            else
//            {
//                user_sub_state.setText("غیرفعال");
//                user_sub_state.setTextColor(getResources().getColor(R.color.red));
//            }
//        }
//        else
//        {
//            userName.setText("کاربر مهمان");
//            userMobile.setVisibility(View.GONE);
//            user_invite_code.setVisibility(View.GONE);
//            user_score.setVisibility(View.GONE);
//            lay_user_subState.setVisibility(View.GONE);
//
//            dataList = new ArrayList<MenuItems>();
//            dataList.add(new MenuItems(1, "ورود/عضویت", R.string.person_icons));
//            dataList.add(new MenuItems(7,"پیشنهاد به دوستان", R.string.add_person_icons));
//
//            adapter = new MenuDrawerAdapter(getActivity(), dataList);
//            recyclerView.setAdapter(adapter);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        }
//
//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        EventBus.getDefault().unregister(this);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),
                drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
//                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
//                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }
    }

}
