package ir.trap.tractor.android.ui.fragments.ticket;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.model.showTicket.ShowTicketItem;
import ir.trap.tractor.android.ui.adapters.ticket.ShowTicketAdapter;
import ir.trap.tractor.android.utilities.Tools;

/**
 * Created by MahtabAzizi on 10/28/2019.
 */
public class ShowTicketsFragment  extends Fragment implements View.OnClickListener
{

    private static final String KEY_MODEL = "KEY_MODEL";
    private View view;
    private OnClickContinueBuyTicket onClickContinueBuyTicketListener;
    private View btnShareTicket,btnPaymentConfirm;
    private TextView tvDescTicket;
    private String firstName,lastName;
    private RecyclerView rvTickets;
    private LinearLayoutManager linearLayoutManager;
    private ShowTicketAdapter showTicketAdapter;
    private ImageView ivBarcode;
    private ArrayList<ShowTicketItem> ticketItems = new ArrayList<>();;


    public ShowTicketsFragment()
    {
    }

    /**
     * Receive the model list
     */
    public static ShowTicketsFragment newInstance(String s,OnClickContinueBuyTicket onClickContinueBuyTicket)
    {
        ShowTicketsFragment fragment = new ShowTicketsFragment();
        fragment.setOnClickContinueBuyTicket(onClickContinueBuyTicket);
        Bundle args = new Bundle();
        args.putString(KEY_MODEL, s);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() == null)
        {
            throw new RuntimeException("You must to send a subMenuModels ");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.show_tickets_fragment, container, false);

        Context context = view.getContext();
        initView();
        return view;
    }

    private void initView()
    {
        ivBarcode=view.findViewById(R.id.ivBarcode);
        ivBarcode.setVisibility(View.GONE);

        tvDescTicket=view.findViewById(R.id.tvDescTicket);
        btnShareTicket=view.findViewById(R.id.btnShareTicket);
        btnPaymentConfirm=view.findViewById(R.id.btnPaymentConfirm);
        rvTickets=view.findViewById(R.id.rvTickets);
        if (Prefs.getString("firstName", "").isEmpty())
        {
           firstName ="کاربر";
        }else {
            firstName=Prefs.getString("firstName", "");
        }
        lastName=Prefs.getString("lastName", "");
        tvDescTicket.setText(firstName+" "+lastName+"عزیز؛");
        btnShareTicket.setOnClickListener(this);
        btnPaymentConfirm.setOnClickListener(this);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTickets.setLayoutManager(linearLayoutManager);

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvListCard.getContext(),
//                linearLayoutManager.getOrientation());
//        rvListCard.addItemDecoration(dividerItemDecoration);
        ticketItems.add(new ShowTicketItem("mahtab", "0480489999"));
        ticketItems.add(new ShowTicketItem("test", "0480000000"));
        ticketItems.add(new ShowTicketItem("ticket", "0015478963"));

        showTicketAdapter = new ShowTicketAdapter(ticketItems);
        rvTickets.setAdapter(showTicketAdapter);

        setBarcode();
    }

    private void setBarcode()
    {
        String text="0480759294"; // Whatever you need to encode in the QR code
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.CODE_128,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ivBarcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    private void setOnClickContinueBuyTicket(OnClickContinueBuyTicket onClickContinueBuyTicket)
    {
        this.onClickContinueBuyTicketListener=onClickContinueBuyTicket;
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnPaymentConfirm:

                onClickContinueBuyTicketListener.goBuyTicket();

                break;
            case R.id.btnShareTicket:
               // onClickContinueBuyTicketListener.onBackClicked();
                Tools.showToast(getContext(),"share");
                break;
        }
    }
}
