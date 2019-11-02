package ir.trap.tractor.android.ui.fragments.predict;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.textfield.TextInputLayout;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Arrays;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.model.contact.OnSelectContact;
import ir.trap.tractor.android.apiServices.model.mobileCharge.response.MobileChargeResponse;
import ir.trap.tractor.android.conf.TrapConfig;
import ir.trap.tractor.android.ui.activities.main.OnContactClick;
import ir.trap.tractor.android.ui.base.BaseFragment;
import ir.trap.tractor.android.ui.fragments.main.MainActionView;
import ir.trap.tractor.android.ui.fragments.payment.PaymentFragment;
import ir.trap.tractor.android.ui.fragments.payment.PaymentParentActionView;
import ir.trap.tractor.android.ui.fragments.simcardCharge.ChargeFragmentInteractor;
import ir.trap.tractor.android.ui.fragments.simcardCharge.imp.irancell.IrancellBuyImpl;
import ir.trap.tractor.android.ui.fragments.simcardCharge.imp.mci.MciBuyImpl;
import ir.trap.tractor.android.ui.fragments.simcardCharge.imp.mci.MciBuyInteractor;
import ir.trap.tractor.android.ui.fragments.simcardCharge.imp.rightel.RightelBuyImpl;
import ir.trap.tractor.android.utilities.ClearableEditText;
import ir.trap.tractor.android.utilities.Logger;
import ir.trap.tractor.android.utilities.NumberTextWatcher;
import library.android.eniac.utility.Utility;

/**
 * Created by Javad.Abadi on 7/14/2018.
 */
@SuppressLint("ValidFragment")
public class PredictFragment extends BaseFragment
{

    private Fragment pFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private MainActionView mainView;


    public PredictFragment()
    {

    }

    public static PredictFragment newInstance(MainActionView mainView)
    {
        PredictFragment f = new PredictFragment();
        f.setMainView(mainView);
        return f;
    }

    private void setMainView(MainActionView mainView)
    {
        this.mainView = mainView;
    }

    private View rootView;

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



        return rootView;
    }
}
