package com.traap.traapapp.ui.fragments.predict.predictSystemTeam;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.traap.traapapp.R;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.dialogs.MessageAlertDialog;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.ui.fragments.predict.PredictActionView;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;


@SuppressLint("ValidFragment")
public class PredictSystemTeamFragment extends BaseFragment
{
    private View rootView;
    private PredictActionView mainView;
    private TextView tvAwayHeader, tvHomeHeader, tvMatchDate;
    private ImageView imgHomeHeader, imgAwayHeader, imgCupLogo;
    private LinearLayout llPredict;
    private FloatingActionButton fabSelectSystem;

    private Integer matchId;
    private Boolean isPredictable;
    private Context context;


    public PredictSystemTeamFragment()
    {

    }

    public static PredictSystemTeamFragment newInstance(PredictActionView mainView, Integer matchId, Boolean isPredictable)
    {
        PredictSystemTeamFragment f = new PredictSystemTeamFragment();
        f.setMainView(mainView);

        Bundle arg = new Bundle();
        arg.putInt("matchId", matchId);
        arg.putBoolean("isPredictable", isPredictable);

        f.setArguments(arg);

        return f;
    }

    private void setMainView(PredictActionView mainView)
    {
        this.mainView = mainView;
    }


    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            matchId = getArguments().getInt("matchId");
            isPredictable = getArguments().getBoolean("isPredictable");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_predict_system_team, container, false);

        initView();

        return rootView;
    }


    public void initView()
    {
        tvMatchDate = rootView.findViewById(R.id.tvMatchDate);

        tvAwayHeader = rootView.findViewById(R.id.tvAwayHeader);
        tvHomeHeader = rootView.findViewById(R.id.tvHomeHeader);

        imgAwayHeader = rootView.findViewById(R.id.imgAwayHeader);
        imgHomeHeader = rootView.findViewById(R.id.imgHomeHeader);

        imgCupLogo = rootView.findViewById(R.id.imgCupLogo);

        llPredict = rootView.findViewById(R.id.llPredict);
        fabSelectSystem = rootView.findViewById(R.id.fabSelectSystem);

        fabSelectSystem.setImageBitmap(textAsBitmap("ثبت", 14f, Color.WHITE));

    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor)
    {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }


}
