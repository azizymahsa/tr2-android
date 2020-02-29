package com.traap.traapapp.ui.adapters.monyTransfer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;

import java.util.ArrayList;

public class AddCartAdapter extends RecyclerView.Adapter<AddCartAdapter.ViewHolder> {

    private ArrayList<AddCartItem> itemList;
    // Constructor of the class
    public AddCartAdapter( ArrayList<AddCartItem> itemList) {
        this.itemList = itemList;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }


    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_add_cart, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        holder.tvCartName.setText(itemList.get(listPosition).getName());
        holder.tvCartNumber.setText(itemList.get(listPosition).getNumber());
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvCartName,tvCartNumber;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvCartName = (TextView) itemView.findViewById(R.id.tvCartName);
            tvCartNumber = (TextView) itemView.findViewById(R.id.tvCartNumber);
           // tvCartName = (TextView) itemView.findViewById(R.id.tvCartName);
        }
        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + tvCartName.getText());
        }
    }
}