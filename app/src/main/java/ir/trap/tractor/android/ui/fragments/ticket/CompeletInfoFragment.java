package ir.trap.tractor.android.ui.fragments.ticket;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
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

public class CompeletInfoFragment
        extends Fragment implements View.OnClickListener
{

    private static final String KEY_MODEL = "KEY_MODEL";
    private View view;
    private TextView  tvCount, tvM, tvP,tvStation;
    private View btnBackToDetail,btnPaymentConfirm;
    private int count = 1;
    private OnClickContinueBuyTicket onClickContinueBuyTicketListener;

    public CompeletInfoFragment()
    {
    }

    /**
     * Receive the model list
     */
    public static CompeletInfoFragment newInstance(String s,OnClickContinueBuyTicket onClickContinueBuyTicket)
    {
        CompeletInfoFragment fragment = new CompeletInfoFragment();
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

            }

            @Override
            public void onError(String message)
            {

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
        tvStation = view.findViewById(R.id.tvStation);
        tvCount = view.findViewById(R.id.tvCount);
        tvM = view.findViewById(R.id.tvM);
        tvP = view.findViewById(R.id.tvP);

        btnBackToDetail=view.findViewById(R.id.btnBackToDetail);
        btnPaymentConfirm=view.findViewById(R.id.btnPaymentConfirm);
        btnBackToDetail.setOnClickListener(this);
        btnPaymentConfirm.setOnClickListener(this);

        tvP.setOnClickListener(this);
        tvM.setOnClickListener(this);
        /*RotateAnimation rotate= (RotateAnimation) AnimationUtils.loadAnimation(getContext(),R.anim.rotate_animation);
        tvStation.setAnimation(rotate);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.complete_info_fragment, container, false);
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
    public void onDetach()
    {
        super.onDetach();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
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

