package ir.trap.tractor.android.ui.fragments.ticket;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.match.ResponseMatch;
import library.android.calendar.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

public class CountTicketFragment
        extends Fragment implements View.OnClickListener
{

    private static final String KEY_MODEL = "KEY_MODEL";
    private OnClickContinueBuyTicket onClickContinueBuyTicketListener;
    private View btnBackToDetail;
    private View btnPaymentConfirm;

    private View view;
    private TextView tvStadiumName, tvDateTime, tvCount, tvM, tvP;
    private ImageView imgHost, imgGuest;
    private ProgressBar progress;
    private RelativeLayout llHeaderWeekNo;
    private int count = 1;

    public CountTicketFragment()
    {
    }

    /**
     * Receive the model list
     */
    public static CountTicketFragment newInstance(String s,OnClickContinueBuyTicket onClickContinueBuyTicket) {
        CountTicketFragment fragment = new CountTicketFragment();
        fragment.setOnClickContinueBuyTicket(onClickContinueBuyTicket);
       Bundle args = new Bundle();
        args.putString(KEY_MODEL, s);
        fragment.setArguments(args);

        return fragment;
    }

    private void setOnClickContinueBuyTicket(OnClickContinueBuyTicket onClickContinueBuyTicket)
    {
        this.onClickContinueBuyTicketListener=onClickContinueBuyTicket;
    }

    private void setImageColor(ImageView imageView, String link)
    {
        try
        {
            Picasso.with(getContext()).load(Uri.parse(link)).centerCrop().resize(imageView.getMeasuredWidth(), imageView.getMeasuredHeight()).into(imageView, new Callback()
            {
                @Override
                public void onSuccess()
                {
                }

                @Override
                public void onError()
                {
                    Picasso.with(getContext()).load(R.drawable.img_failure).into(imageView);
                }
            });
        } catch (NullPointerException e)
        {
            Picasso.with(getContext()).load(R.drawable.img_failure).into(imageView);
        }
    }
/*
public static SelectPositionFragment newInstance(SubMenuModel[] subMenuModels) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putParcelableArray(KEY_MODEL, subMenuModels);
        fragment.setArguments(args);
        return fragment;
    }
*/

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() == null)
        {
            throw new RuntimeException("You must to send a subMenuModels ");
        }

        SingletonService.getInstance().getTicketServices().getMatch(new OnServiceStatus<WebServiceClass<ResponseMatch>>()
        {
            @Override
            public void onReady(WebServiceClass<ResponseMatch> response)
            {
                progress.setVisibility(View.GONE);
                tvStadiumName.setText(response.data.getResults().get(0).getStadium().getName());

                setImageColor(imgHost, response.data.getResults().get(0).getTeamHome().getLogo());
                setImageColor(imgGuest, response.data.getResults().get(0).getTeamAway().getLogo());

                tvDateTime.setText(getDate(response.data.getResults().get(0).getMatchDatetime()));
            }

            @Override
            public void onError(String message)
            {
                progress.setVisibility(View.GONE);


            }
        });
    }

    private String getDate(Double matchDatetime)
    {
        String shamsi = "";
        Date d = new Date((new Double(matchDatetime)).longValue());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd " + " " + "hh:mm:ss");
        String date = dateFormat.format(d);  // formatted date in string

        PersianCalendar persianCalendar = new PersianCalendar();
        return date;
    }

    private void initView()
    {
        tvCount = view.findViewById(R.id.tvCount);
        tvM = view.findViewById(R.id.tvM);
        tvP = view.findViewById(R.id.tvP);
        llHeaderWeekNo = view.findViewById(R.id.llHeaderWeekNo);
        llHeaderWeekNo.setVisibility(View.INVISIBLE);
        tvStadiumName = view.findViewById(R.id.tvStadiumName);
        tvDateTime = view.findViewById(R.id.tvDateTime);
        imgHost = view.findViewById(R.id.imgHost);
        imgGuest = view.findViewById(R.id.imgGuest);
        progress = view.findViewById(R.id.progress);
        btnPaymentConfirm = view.findViewById(R.id.btnPaymentConfirm);
        btnBackToDetail=view.findViewById(R.id.btnBackToDetail);

        btnBackToDetail.setOnClickListener(this);
        tvP.setOnClickListener(this);
        tvM.setOnClickListener(this);
        btnPaymentConfirm.setOnClickListener(this);


        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.count_ticket_fragment, container, false);
        initView();
        Context context = view.getContext();


        return view;
    }


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.btnPaymentConfirm:
                onClickContinueBuyTicketListener.onContinueClicked();
                break;
            case R.id.btnBackToDetail:
                onClickContinueBuyTicketListener.onBackClicked();
                break;

            case R.id.tvM:
                if (count > 1)
                    count--;
                break;
            case R.id.tvP:
                if (count < 5)
                    count++;
                break;
        }
        tvCount.setText(String.valueOf(count));

    }
}

