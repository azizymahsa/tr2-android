package com.traap.traapapp.ui.adapters.increaseinventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.AddValueList;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Utility;

import java.util.List;

/**
 * Created by MahtabAzizi on 2/3/2020.
 */
public class IncreaseInventoryAmountsAdapter  extends RecyclerView.Adapter<IncreaseInventoryAmountsAdapter.MyViewHolder> {

    private final MainActionView mainView;
    private List<AddValueList> listBalance;
    private IncreaseInventoryAmounts increaseInventory;


    public IncreaseInventoryAmountsAdapter(List<AddValueList> listBalance, MainActionView mainView
            , IncreaseInventoryAmounts increaseInventory) {

        this.listBalance = listBalance;
        this.mainView = mainView;
        this.increaseInventory = increaseInventory;

    }

    @NonNull
    @Override
    public IncreaseInventoryAmountsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_increase_inventory_amount, parent, false);

        return new IncreaseInventoryAmountsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IncreaseInventoryAmountsAdapter.MyViewHolder holder, int position) {
        AddValueList balance = listBalance.get(position);


        holder.tvBalance.setText(" "+ Utility.priceFormat(balance.getPrice().toString()) + "ریال ");

        holder.tvBalance.setOnClickListener(view -> {
            increaseInventory.onBalanceClicked(balance.getPrice().toString());


        });


    }


    @Override
    public int getItemCount() {
        return listBalance.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvBalance;


        private MyViewHolder(View convertView) {
            super(convertView);

            tvBalance = convertView.findViewById(R.id.tvBalance);


        }
    }

    public interface IncreaseInventoryAmounts {
        void onBalanceClicked(String balance);

    }

}