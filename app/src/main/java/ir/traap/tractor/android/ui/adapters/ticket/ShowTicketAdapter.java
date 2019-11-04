package ir.traap.tractor.android.ui.adapters.ticket;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.showTicket.ShowTicketItem;

/**
 * Created by MahtabAzizi on 10/28/2019.
 */
public class ShowTicketAdapter extends RecyclerView.Adapter<ShowTicketAdapter.ViewHolder>
{
    private Context context;
    private String nationalCode;
    private List<ShowTicketItem> ticketItems;
    /*private final List<RightelPackModel> data;

    private Context context;
    private DetailPackAdapter detailPackAdapter;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private DetailPackAdapter.GetPackInAdapter getPackInAdapter;
    private String type;*/

   //public ShowTicketAdapter(final List<RightelPackModel> data, DetailPackAdapter.GetPackInAdapter getPackInAdapter, String type)
   public ShowTicketAdapter(final List<ShowTicketItem> ticketItems)
    {
       /* this.data = data;
        this.getPackInAdapter = getPackInAdapter;
        this.type = type;*/
        this.ticketItems = ticketItems;
    }


    @Override
    public ShowTicketAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        this.context = parent.getContext();
        return new ShowTicketAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item_show_ticket, parent, false));
    }


    @Override
    public void onBindViewHolder(final ShowTicketAdapter.ViewHolder holder, final int position)
    {
        nationalCode="0480759294";
        setBarcode(nationalCode,holder);
        holder.tvName.setText(ticketItems.get(position).getName());
        holder.tvNationalCode.setText(ticketItems.get(position).getNationalCode());
        holder.tvStation.setText(ticketItems.get(position).getStation());
       /* final RightelPackModel item = data.get(position);

        holder.tvTitle.setText(item.getTitle());
        List<Detail> details = new ArrayList<>();

        holder.buttonLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                onClickButton(holder.expandableLayout);
            }
        });*/
    }
    private void setBarcode(String nationalCode, ViewHolder holder)
    {
        //String text="0480759294"; // Whatever you need to encode in the QR code
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(nationalCode, BarcodeFormat.CODE_128, convertDpToPx(context.getResources().getDimension(R.dimen._200dp))
                    , convertDpToPx(context.getResources().getDimension(R.dimen._40dp)));
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            holder.ivBarcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public int convertDpToPx(float dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }
    @Override
    public int getItemCount()
    {

        return ticketItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView ivBarcode;
        public TextView tvName,tvNationalCode,tvStation;

        public ViewHolder(View v)
        {
            super(v);
            ivBarcode = v.findViewById(R.id.ivBarcode);
            tvName = v.findViewById(R.id.tvName);
            tvNationalCode = v.findViewById(R.id.tvNationalCode);
            tvStation = v.findViewById(R.id.tvStation);
           /* imgArrow = v.findViewById(R.id.imgArrow);
            tvTitle = v.findViewById(R.id.tvTitle);
            buttonLayout = v.findViewById(R.id.buttonLayout);*/
        }
    }

}
