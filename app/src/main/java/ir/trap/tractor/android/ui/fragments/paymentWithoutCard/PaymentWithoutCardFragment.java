package ir.trap.tractor.android.ui.fragments.paymentWithoutCard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.ui.base.BaseFragment;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;

public class PaymentWithoutCardFragment extends BaseFragment
{

    private Toolbar mToolbar;

    private MainActionView mainView;

    public PaymentWithoutCardFragment()
    {
    }

    public static PaymentWithoutCardFragment newInstance(MainActionView mainActionView)
    {
        PaymentWithoutCardFragment fragment = new PaymentWithoutCardFragment();
        fragment.setMainView(mainActionView);

        Bundle args = new Bundle();
//        args.putParcelableArrayList("chosenServiceList", chosenServiceList);
//        args.putParcelableArrayList("footballServiceList", footballServiceList);

        fragment.setArguments(args);


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
        if (getArguments() != null)
        {

        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_payment_without_card, container, false);

        mToolbar = rootView.findViewById(R.id.toolbar);

        mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mainView.openDrawer();
            }
        });




        return rootView;
    }
}
