package ir.traap.tractor.android.ui.adapters.ticket;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.getTicketInfo.ResultTicketInfo;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;

/**
 * Created by MahtabAzizi on 10/28/2019.
 */
public class ShowTicketAdapter extends RecyclerView.Adapter<ShowTicketAdapter.ViewHolder>
{
    private final MainActionView mainView;
    private Context context;
    private String nationalCode;
    private List<ResultTicketInfo> ticketItems;


    public ShowTicketAdapter(List<ResultTicketInfo> results, MainActionView mainView)
    {
        this.ticketItems = results;
        this.mainView=mainView;
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
        nationalCode=ticketItems.get(position).getNationalCode();
        setBarcode(nationalCode,holder);
        holder.tvName.setText(ticketItems.get(position).getFirstName()+ "  "+ticketItems.get(position).getLastName());
        holder.tvNationalCode.setText("کد ملی: "+ticketItems.get(position).getNationalCode());
        holder.tvPosition.setText("جایگاه "+ticketItems.get(position).getBoxName());
        holder.tvClubName.setText(ticketItems.get(position).getStadiumName());

        holder.tvHome.setText(ticketItems.get(position).getHomeName());
        holder.tvGuest.setText(ticketItems.get(position).getAwayName());

        holder.tvDate.setText(ticketItems.get(position).getMatchDate());

        if (ticketItems.get(position).getHomeLogo() != null)
        {
            setImageBackground(holder.imgHost, ticketItems.get(position).getHomeLogo());
        }

        if (ticketItems.get(position).getAwayLogo() != null)
        {
            setImageBackground(holder.imgGuest, ticketItems.get(position).getAwayLogo());
        }

    }

    private void setImageBackground(ImageView image, String link)
    {
        try
        {
            Picasso.with(context).load(Uri.parse(link)).into(image, new Callback()
            {
                @Override
                public void onSuccess() { }

                @Override
                public void onError()
                {
                    Picasso.with(context).load(R.drawable.img_failure).into(image);
                }
            });
        }
        catch (NullPointerException e)
        {
            Picasso.with(context).load(R.drawable.img_failure).into(image);
        }
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
        public ImageView ivBarcode,imgGuest,imgHost;
        public TextView tvName,tvNationalCode,tvPosition,tvClubName,tvDate,tvHome,tvGuest;

        public ViewHolder(View v)
        {
            super(v);
            ivBarcode = v.findViewById(R.id.ivBarcode);
            tvName = v.findViewById(R.id.tvName);
            tvPosition=v.findViewById(R.id.tvPosition);
            tvNationalCode = v.findViewById(R.id.tvNationalCode);
            tvClubName=v.findViewById(R.id.tvClubName);
            tvDate=v.findViewById(R.id.tvDate);
            imgGuest=v.findViewById(R.id.imgGuest);
            imgHost=v.findViewById(R.id.imgHost);
            tvHome=v.findViewById(R.id.tvHome);
            tvGuest=v.findViewById(R.id.tvGuest);
           /* imgArrow = v.findViewById(R.id.imgArrow);
            tvTitle = v.findViewById(R.id.tvTitle);
            buttonLayout = v.findViewById(R.id.buttonLayout);*/
        }
    }

}
