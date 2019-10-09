package ir.trap.tractor.android.ui.base;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;

public class BaseFragment extends Fragment
{

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

}
