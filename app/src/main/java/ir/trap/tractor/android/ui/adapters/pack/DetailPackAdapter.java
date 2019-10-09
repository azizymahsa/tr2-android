package ir.trap.tractor.android.ui.adapters.pack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.model.getRightelPack.response.Detail;
import library.android.eniac.utility.Utility;

/**
 * Created by Javad.Abadi on 8/13/2018.
 */
public class DetailPackAdapter extends RecyclerView.Adapter<DetailPackAdapter.ViewHolder> {

    private final List<Detail> data;
    private Context context;
    private GetPackInAdapter getPackInAdapter;

    public DetailPackAdapter(final List<Detail> data, GetPackInAdapter getPackInAdapter) {
        this.data = data;
        this.getPackInAdapter = getPackInAdapter;

    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.recycler_view_rightel_detail_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Detail item = data.get(position);
        String type = "";
/*        if (item.getPackageType() != null) {
            if (item.getPackageType().equals("2"))
                type = "دائمی";

            else
                type = "اعتباری";*/

            holder.tvTitle.setText(item.getTitle() + " ( " + item.getTitlePackageType() + " ) ");

   /*     } else {
            holder.tvTitle.setText(item.getTitle());

        }*/


        holder.tvAmount.setText("قیمت : " + Utility.priceFormat(item.getAmount()) + " ریال ");
        holder.container.setOnClickListener(view -> {
            getPackInAdapter.getPackRightel(item);
        });


    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvAmount;
        public LinearLayout container;

        public ViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvAmount = v.findViewById(R.id.tvAmount);
            container = v.findViewById(R.id.container);
        }
    }

    public interface GetPackInAdapter {
        void getPackRightel(Detail o);
    }


}
