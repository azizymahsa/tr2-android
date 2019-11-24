package ir.traap.tractor.android.ui.fragments.transaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import ir.traap.tractor.android.R;
import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.getTransaction.ResponseTransaction;
import ir.traap.tractor.android.conf.TrapConfig;
import ir.traap.tractor.android.ui.adapters.Leaguse.DataBean;
import ir.traap.tractor.android.ui.adapters.transaction.TransactionListAdapter;
import ir.traap.tractor.android.ui.base.BaseFragment;
import ir.traap.tractor.android.ui.fragments.main.MainActionView;


public class TransactionsListFragment
        extends BaseFragment implements OnAnimationEndListener, View.OnClickListener//, OnBackPressed
{
    private String teamId = "";
    private View rootView;

    private MainActionView mainView;

    private Toolbar mToolbar;
    private TextView tvTitle, tvUserName,tvPopularPlayer,tvCount;
    private View imgBack, imgMenu;

    /*scroll view*/
    public List<DataBean> data = new ArrayList<>();
    private RecyclerView transactionRecycler;
    private TransactionListAdapter fixTableAdapter;





    public TransactionsListFragment()
    {
    }




    public static TransactionsListFragment newInstance(MainActionView mainView)
    {
        TransactionsListFragment f = new TransactionsListFragment();

        Bundle args = new Bundle();

        f.setArguments(args);
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            teamId = getArguments().getString("teamId");
        }

    }

    public void initView()
    {
        try
        {
            transactionRecycler = rootView.findViewById(R.id.transactionRecycler);
            tvCount = rootView.findViewById(R.id.tvCount);
            //Toolbar Create
            mToolbar = rootView.findViewById(R.id.toolbar);
            tvUserName = mToolbar.findViewById(R.id.tvUserName);

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);

            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mainView.openDrawer();
                }
            });
            tvTitle = rootView.findViewById(R.id.tvTitle);
            imgMenu = rootView.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
            imgBack = rootView.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvTitle.setText(" گزارشات خرید و تراکنش");
            tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(Prefs.getString("PopularPlayer", ""));


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

        rootView = inflater.inflate(R.layout.transaction_list_fragment, container, false);
        initView();
        sendRequest();


        return rootView;
    }



    private void sendRequest()
    {
        mainView.showLoading();

        SingletonService.getInstance().getTransactionService().getTransactionList(new OnServiceStatus<WebServiceClass<ResponseTransaction>>()
        {
            @Override
            public void onReady(WebServiceClass<ResponseTransaction> response)
            {
                try
                {
                    mainView.hideLoading();
                    if (response == null || response.info == null)
                    {
                        return;
                    }
                    if (response.info.statusCode != 200)
                    {


                        return;
                    }
                    if (response.info.statusCode == 200)
                    {

                        tvCount.setText(response.data.getResults().size()+ "مورد خرید بلیت یافت شد.");
                        transactionRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                        fixTableAdapter = new TransactionListAdapter(response.data.getResults(), getActivity());
                        //fixTableAdapter.setClickListener(this);
                        transactionRecycler.setAdapter(fixTableAdapter);

                    }
                } catch (Exception e)
                {
                    mainView.showError(e.getMessage());
                    mainView.hideLoading();
                }
            }

            @Override
            public void onError(String message)
            {
                mainView.showError(message);
                mainView.hideLoading();
            }
        });
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


 /*   @Override
    public void onBackPressed()
    {
        getActivity().getSupportFragmentManager().popBackStack();
    }*/
}
