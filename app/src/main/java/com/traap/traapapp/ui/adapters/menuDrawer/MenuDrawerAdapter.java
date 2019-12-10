package com.traap.traapapp.ui.adapters.menuDrawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import com.traap.traapapp.R;
import com.traap.traapapp.models.otherModels.menuItems.MenuItems;

public class MenuDrawerAdapter extends RecyclerView.Adapter<MenuDrawerAdapter.MyViewHolder>
{
    List<MenuItems> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public MenuDrawerAdapter(Context context, List<MenuItems> data)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position)
    {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.menudrawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        MenuItems current = data.get(position);

//        holder.lyItem.setVisibility(View.VISIBLE);

        holder.MenuIcon.setImageDrawable(context.getResources().getDrawable(current.getImgResID()));
        holder.MenuItem.setText(current.getItemName());

        if (!current.getIsActive())
        {
            holder.lyItem.setAlpha(0.3f);
            holder.lyItem.setClickable(false);
        }
//        if (current.getItemNumber() >= 0)
//        {
//            holder.MenuItemNumber.setVisibility(View.VISIBLE);
//            holder.MenuItemNumber.setText(current.getItemNumber() + "");
//        } else
//            holder.MenuItemNumber.setVisibility(View.GONE);

//        if (current.getId() == 3)
//        {
////            holder.switchItem.setVisibility(View.VISIBLE);
//        }

//        if (current.getId() == 2)
//        {
//            holder.MenuItemDrop.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView MenuIcon;
        TextView MenuItem;
        //        TextView MenuItemNumber;
        TextView MenuItemDrop;
//        SwitchCompat switchItem;
        RelativeLayout lyItem;


        public MyViewHolder(View view)
        {
            super(view);

            MenuIcon = view.findViewById(R.id.menu_dr_icon);
            MenuItem = view.findViewById(R.id.menu_dr_item);
//            MenuItemNumber = view.findViewById(R.id.menu_dr_number);
//            MenuItemDrop = view.findViewById(R.id.menu_dr_drop);
//            switchItem = view.findViewById(R.id.switch_item);
            lyItem =  view.findViewById(R.id.ly_item);

        }
    }

}
