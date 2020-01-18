package com.traap.traapapp.ui.drawer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.getMenu.response.GetMenuItemResponse;
import com.traap.traapapp.models.otherModels.menuItems.MenuItems;
import com.traap.traapapp.ui.adapters.menuDrawer.MenuDrawerAdapter;
import com.traap.traapapp.utilities.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MenuDrawerFragment extends Fragment
{
    private RelativeLayout cardProfile;
    private TextView tvMyProfile;

    private Context mContext;
    private ArrayList<GetMenuItemResponse> drawerListMenu = new ArrayList<>();

    private static String TAG = MenuDrawerFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private MenuDrawerAdapter adapter;
    private View containerView;

    private FragmentDrawerListener drawerListener;

    private List<MenuItems> dataList;
    private ImageView imgMenu;

    public MenuDrawerFragment()
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
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.menudrawer_fragment, container, false);

        recyclerView = layout.findViewById(R.id.drawerList);
        tvMyProfile = layout.findViewById(R.id.tvMyProfile);

        cardProfile = layout.findViewById(R.id.cardProfile);
        cardProfile.setOnClickListener(v ->
        {
            drawerListener.onUserProfileClick();
            mDrawerLayout.closeDrawer(containerView);
        });

        imgMenu = layout.findViewById(R.id.imgMenu);
        imgMenu.setOnClickListener(v ->
        {
            mDrawerLayout.closeDrawer(containerView);
        });

        setContent();

        EventBus.getDefault().register(this);
        return layout;
    }

    private void setContent()
    {
        dataList = new ArrayList<>();

        if (!drawerListMenu.isEmpty())
        {
            for (GetMenuItemResponse item: drawerListMenu)
            {
                if (item.getKeyId() == 81)
                {
                    tvMyProfile.setText(item.getTitle());
                }
                else
                {
                    MenuItems menuItem = new MenuItems();
                    menuItem.setId(item.getKeyId());
                    menuItem.setItemName(item.getTitle());
                    menuItem.setIsActive(item.getIsVisible());
                    menuItem.setImgURL(item.getImageName());
                    dataList.add(menuItem);

//                dataList.add(new MenuItems(1, "سوابق خرید و تراکنش ها", R.drawable.ic_transaction_list, true));
//                dataList.add(new MenuItems(4, "کیف پول", R.drawable.ic_wallet, true));
//                dataList.add(new MenuItems(10, "راهنما", R.drawable.ic_help, true));
//                dataList.add(new MenuItems(9, "ارتباط با پشتیبانی", R.drawable.ic_support, true));
//                dataList.add(new MenuItems(7, "درباره ما", R.drawable.ic_about_us, true));
                }
            }

            adapter = new MenuDrawerAdapter(mContext, dataList);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(mContext,
                    recyclerView, new ClickListener()
            {
                @Override
                public void onClick(View view, int position)
                {
                    Logger.e("--click menu Drawer--", "Clicked");

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
        }

    }

    @Subscribe
    public void setData(ArrayList<GetMenuItemResponse> drawerListMenu)
    {
        if (!Objects.requireNonNull(drawerListMenu).isEmpty())
        {
            this.drawerListMenu = drawerListMenu;
        }
        else
        {
            this.drawerListMenu = new ArrayList<>();
        }
        setContent();
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar)
    {
        containerView = ((Activity) mContext).findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(((Activity) mContext),
                drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
        {
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable()
        {
            @Override
            public void run()
            {
                mDrawerToggle.syncState();
            }
        });

    }

    public static interface ClickListener
    {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    public interface FragmentDrawerListener
    {
        public void onDrawerItemSelected(View view, int position);

        public void onUserProfileClick();
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener
    {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener)
        {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
            {
                @Override
                public boolean onSingleTapUp(MotionEvent e)
                {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e)
                {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null)
                    {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e)
        {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e))
            {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e)
        {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b)
        {

        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
