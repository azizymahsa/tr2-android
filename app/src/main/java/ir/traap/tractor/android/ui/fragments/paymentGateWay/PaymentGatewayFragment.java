package ir.traap.tractor.android.ui.fragments.paymentGateWay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.model.matchList.MatchItem;
import ir.traap.tractor.android.ui.adapters.Leaguse.DataBean;
import ir.traap.tractor.android.ui.adapters.Leaguse.matchResult.MatchAdapter;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;

/**
 * Created by MahsaAzizi on 11/20/2019.
 */
public class PaymentGatewayFragment extends Fragment implements OnAnimationEndListener, View.OnClickListener,MatchAdapter.ItemClickListener
{
    private View rootView;

    private MainActionView mainView;

    /*scroll view*/
    public List<DataBean> data = new ArrayList<>();
  //  private List<MatchItem> pastMatchesList = new ArrayList<>();
  //  private MatchAdapter mAdapter;


    public PaymentGatewayFragment()
    {

    }


    public static PaymentGatewayFragment newInstance( MainActionView mainActionView)
    {
        PaymentGatewayFragment fragment = new PaymentGatewayFragment();
        Bundle args = new Bundle();

        fragment.setMainView(mainActionView);
        return fragment;
    }



    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    public void initView()
    {
        try
        {
          //  recyclerView = rootView.findViewById(R.id.rclPast);


        } catch (Exception e)
        {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            rootView = null;
        }

        rootView = inflater.inflate(R.layout.fragment_payment_gateway, container, false);
        initView();
        addDataRecyclerList();

        return rootView;
    }

    private void addDataRecyclerList()
    {

       /* mAdapter = new MatchAdapter(pastMatchesList,getContext(),this);
        recyclerView.setAdapter(mAdapter);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MatchAdapter(pastMatchesList, getActivity(),this);
        recyclerView.setAdapter(mAdapter);*/
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }

    @Override
    public void onStop()
    {
        super.onStop();


    }

    @Override
    public void onResume()
    {
        super.onResume();

    }

    @Override
    public void onPause()
    {
        super.onPause();
    }


    @Override
    public void onAnimationEnd()
    {


    }

    @Override
    public void onClick(View view)
    {
       /* switch (view.getId())
        {
            case R.id.btnConfirm:

                break;


        }*/

    }


    @Override
    public void onItemClick(View view, int position, MatchItem matchItem)
    {

    }

    @Override
    public void onItemPredictClick(View view, int position, MatchItem matchItem)
    {

    }
}
