package ir.trap.tractor.android.ui.fragments.predict;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.squareup.picasso.Picasso;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.matchList.MatchItem;
import ir.trap.tractor.android.apiServices.model.predict.getPredict.response.GetPredictResponse;
import ir.trap.tractor.android.apiServices.model.predict.sendPredict.request.SendPredictRequest;
import ir.trap.tractor.android.ui.activities.login.LoginActivity;
import ir.trap.tractor.android.ui.base.BaseFragment;
import ir.trap.tractor.android.ui.dialogs.MessageAlertDialog;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;
import ir.trap.tractor.android.utilities.Logger;

/**
 * Created by Javad.Abadi on 7/14/2018.
 */
@SuppressLint("ValidFragment")
public class PredictFragment extends BaseFragment implements OnServiceStatus<WebServiceClass<GetPredictResponse>>,
        OnAnimationEndListener
{
    private MatchItem matchPredict;

    private LinearLayout llAwayResultList, llHomeResultList;
    private TextView tvAwayHeader, tvHomeHeader, tvAway, tvHome, tvCupTitle, tvDate, tvMatchResult;
    private ImageView imgHomeHeader, imgAwayHeader, imgHome, imgAway, imgHomePredict, imgAwayPredict;

    private EditText edtAwayPredict, edtHomePredict;

    private View vHome, vHome2, vAway, vAway2;

    private CircularProgressButton btnSendPredict;


    private MainActionView mainView;


    public PredictFragment()
    {

    }

    public static PredictFragment newInstance(MainActionView mainView, MatchItem matchPredict)
    {
        PredictFragment f = new PredictFragment();
        f.setMainView(mainView);
        Bundle arg = new Bundle();
        arg.putParcelable("matchPredict", matchPredict);

        f.setArguments(arg);

        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            matchPredict = getArguments().getParcelable("matchPredict");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;
        }

        rootView = inflater.inflate(R.layout.fragment_predict, container, false);

        ((TextView) rootView.findViewById(R.id.tvTitle)).setText("پیش بینی بازی");
        rootView.findViewById(R.id.imgBack).setOnClickListener(v ->
        {
            mainView.backToMainFragment();
        });

        initView();


        return rootView;
    }

    private void initView()
    {
        btnSendPredict = rootView.findViewById(R.id.btnSendPredict);

        llAwayResultList = rootView.findViewById(R.id.llAwayResultList);
        llHomeResultList = rootView.findViewById(R.id.llHomeResultList);

        edtAwayPredict = rootView.findViewById(R.id.edtAwayPredict);
        edtHomePredict = rootView.findViewById(R.id.edtHomePredict);

        tvAwayHeader = rootView.findViewById(R.id.tvAwayHeader);
        tvHomeHeader = rootView.findViewById(R.id.tvHomeHeader);
        tvHome = rootView.findViewById(R.id.tvHome);
        tvAway = rootView.findViewById(R.id.tvAway);
        tvCupTitle = rootView.findViewById(R.id.tvCupTitle);
        tvDate = rootView.findViewById(R.id.tvDate);
        tvMatchResult = rootView.findViewById(R.id.tvMatchResult);

        imgAwayHeader = rootView.findViewById(R.id.imgAwayHeader);
        imgAway = rootView.findViewById(R.id.imgAway);
        imgAwayPredict = rootView.findViewById(R.id.imgAway3);
        imgHomeHeader = rootView.findViewById(R.id.imgHomeHeader);
        imgHome = rootView.findViewById(R.id.imgHome);
        imgHomePredict = rootView.findViewById(R.id.imgHome3);

        vHome = rootView.findViewById(R.id.vHome);
        vHome2 = rootView.findViewById(R.id.vHome2);
        vAway = rootView.findViewById(R.id.vAway);
        vAway2 = rootView.findViewById(R.id.vAway2);

        getBaseData();

        btnSendPredict.setOnClickListener(v ->
        {
            btnSendPredict.startAnimation();
            btnSendPredict.setClickable(false);

            SendPredictRequest request = new SendPredictRequest();
            request.setMatchId(matchPredict.getId());
            request.setAwayTeamDcore(Integer.parseInt(edtAwayPredict.getText().toString().trim()));
            request.setHomeTeamDcore(Integer.parseInt(edtHomePredict.getText().toString().trim()));

            SingletonService.getInstance().sendPredictService().sendPredictService(request, new OnServiceStatus<WebServiceClass<Object>>()
            {
                @Override
                public void onReady(WebServiceClass<Object> response)
                {
                    btnSendPredict.revertAnimation(PredictFragment.this);
                    btnSendPredict.setClickable(true);

                    try
                    {
                        if (response.info.statusCode != 201)
                        {
                            showAlert(getActivity(),response.info.message, R.string.error);
                        }
                        else
                        {
                            showAlert(getActivity(),response.info.message, R.string.error);
                        }
                    }
                    catch (NullPointerException e)
                    {
//                        showAlert(getActivity(),response.info.message, R.string.error);
                        showAlert(getActivity(), "خطای ارتباط با سرور!" + "\n" + "لطفا مجددا اقدام نمایید", R.string.error);
                    }

                }

                @Override
                public void onError(String message)
                {
                    btnSendPredict.revertAnimation(PredictFragment.this);
                    btnSendPredict.setClickable(true);

                    showAlert(getActivity(), "خطای ارتباط با سرور!" + "\n" + "لطفا مجددا اقدام نمایید", R.string.error);
                }
            });
        });
    }

    private void getBaseData()
    {
//        SendPredictRequest request = new SendPredictRequest();
//        request.setMatchId();
//        request.setHomeTeamDcore();
//        request.setAwayTeamDcore();

        SingletonService.getInstance().getPredictService().getPredictService(matchPredict.getId(), this);
    }

    @Override
    public void onReady(WebServiceClass<GetPredictResponse> response)
    {
        if (response == null || response.data == null)
        {
            showErrorAndBackToMain("خطا در دریافت اطلاعات از سرور!");
            return;
        }
        if (response.info.statusCode != 200)
        {
            showErrorAndBackToMain(response.info.message);
            return;
        }
        else
        {
            llAwayResultList.removeAllViews();
            llHomeResultList.removeAllViews();

            for (String itemStr: response.data.getAwayLastPlays())
            {
                llAwayResultList.addView(getWinLoseListView(itemStr));
            }
            for (String itemStr: response.data.getHomeLastPlays())
            {
                llHomeResultList.addView(getWinLoseListView(itemStr));
            }

            setImageIntoIV(imgAwayHeader, response.data.getAwayTeamLogo());
            setImageIntoIV(imgHomeHeader, response.data.getHomeTeamLogo());

            tvHomeHeader.setText(response.data.getHomeTeamName());
            tvAwayHeader.setText(response.data.getAwayTeamName());

            setImageIntoIV(imgHome, response.data.getHomeTeamLogo());
            setImageIntoIV(imgAway, response.data.getAwayTeamLogo());

            setImageIntoIV(imgHomePredict, response.data.getHomeTeamLogo());
            setImageIntoIV(imgAwayPredict, response.data.getAwayTeamLogo());

            tvHome.setText(response.data.getHomeTeamName());
            tvAway.setText(response.data.getAwayTeamName());

            tvCupTitle.setText(response.data.getCup());
            tvDate.setText(response.data.getMatchDatetimeStr());

            vAway.setBackgroundColor(Color.parseColor(response.data.getAwayTeamColorCode()));
            vAway2.setBackgroundColor(Color.parseColor(response.data.getAwayTeamColorCode()));
            vHome.setBackgroundColor(Color.parseColor(response.data.getHomeTeamColorCode()));
            vHome2.setBackgroundColor(Color.parseColor(response.data.getHomeTeamColorCode()));

            tvMatchResult.setText(response.data.getAwayScore() + " : " + response.data.getHomeScore());
        }

    }

    @Override
    public void onError(String message)
    {
        Logger.e("-onError-", "Error: " + message);
        showErrorAndBackToMain("خطا در دریافت اطلاعات از سرور!");
    }

    private void setImageIntoIV(ImageView imageView, String link)
    {
        Picasso.with(getActivity()).load(link).into(imageView);
    }

    private RelativeLayout getWinLoseListView(String itemStr)
    {
        RelativeLayout rl = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.item_win_lose, null, false);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ((int) getResources().getDimension(R.dimen._35dp)),
                ((int) getResources().getDimension(R.dimen._35dp))
        );
        int magSize = (int) getResources().getDimension(R.dimen.margin_static);
        params.setMargins(magSize,magSize,magSize,magSize);
        rl.setLayoutParams(params);
        TextView tv = rl.findViewById(R.id.tvResult);

        switch (itemStr)
        {
            case "-1":
                rl.setBackground(getResources().getDrawable(R.drawable.background_rectangle_red));
                tv.setText("باخت");
                break;
            case "1":
                rl.setBackground(getResources().getDrawable(R.drawable.background_rectangle_green));
                tv.setText("برد");
                break;
            case "0":
                rl.setBackground(getResources().getDrawable(R.drawable.background_rectangle_red_a));
                tv.setText("مساوی");
                break;
        }
        return rl;
    }

    private void showErrorAndBackToMain(String message)
    {
        MessageAlertDialog dialog = new MessageAlertDialog(getActivity(), getResources().getString(R.string.error),
                message, false, new MessageAlertDialog.OnConfirmListener()
        {
            @Override
            public void onConfirmClick()
            {
                mainView.backToMainFragment();
            }

            @Override
            public void onCancelClick()
            {

            }
        });
        dialog.show(getActivity().getFragmentManager(), "dialog");
    }

    @Override
    public void onAnimationEnd()
    {
        btnSendPredict.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.background_button_login));
    }
}
