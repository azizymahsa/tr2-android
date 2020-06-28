package com.traap.traapapp.ui.fragments.headCoach;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import com.google.android.material.tabs.TabLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pixplicity.easyprefs.library.Prefs;
import com.traap.traapapp.R;
import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.getAllComments.ResponseComments;
import com.traap.traapapp.apiServices.model.getAllComments.Result;
import com.traap.traapapp.apiServices.model.sendComment.RequestSendComment;
import com.traap.traapapp.apiServices.model.techs.GetTechsIdResponse;
import com.traap.traapapp.apiServices.model.techs.RequestSetFavoritePlayer;
import com.traap.traapapp.conf.TrapConfig;
import com.traap.traapapp.models.otherModels.headerModel.HeaderModel;
import com.traap.traapapp.singleton.SingletonContext;
import com.traap.traapapp.ui.activities.myProfile.MyProfileActivity;
import com.traap.traapapp.ui.base.BaseFragment;
import com.traap.traapapp.ui.fragments.Introducing_the_team.adapter.FragmentsPagerAdapter;
import com.traap.traapapp.ui.fragments.headCoach.adapter.CommentAdapter;
import com.traap.traapapp.ui.fragments.galleryIntroPlayer.MediaPlayersFragment;
import com.traap.traapapp.ui.fragments.headCoach.model.CoachCommentModel;
import com.traap.traapapp.ui.fragments.headCoach.profileHeadCoach.ProfileHeadCoahFragment;
import com.traap.traapapp.ui.fragments.main.MainActionView;
import com.traap.traapapp.utilities.Logger;
import com.traap.traapapp.utilities.ScreenShot;
import com.traap.traapapp.utilities.Tools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import static com.traap.traapapp.utilities.Utility.changeFontInViewGroup;
import static com.traap.traapapp.utilities.Utility.hideSoftKeyboard;


/**
 * Created by MahtabAzizi on 5/26/2020.
 */
public class HeadCoachFragment extends BaseFragment implements View.OnClickListener, CommentAdapter.CommentAdapterEvents
{
    private View rootView;
    private Toolbar mToolbar;
    private View rlShirt, imgMenu, imgBack;
    private MainActionView mainView;
    private TextView tvUserName, tvPopularPlayer;
    private TabLayout tabLayout;
    private com.traap.traapapp.utilities.WrapContentHeightViewPager view_pager;
    private FragmentsPagerAdapter adapter;
    private Integer coachId = 4;
    private GetTechsIdResponse headProfileData;
    private RoundedImageView imgProfile;
    private ProfileHeadCoahFragment profileHeadCoahFragment;
    private HistoryHeadCoachFragment historyHeadCoachFragment;
    private NewsHeadCoachFragment newsHeadCoachFragment;
    private RecyclerView rvComment;
    private View btnShare, llInfoFromShare, llGallery;
    private ImageView btnFavorit;
    private NestedScrollView nested;
    private LinearLayout llCoach;
    private FrameLayout flFragment;
    private ProgressBar pb;
    private ArrayList<CoachCommentModel> coachCommentList = new ArrayList<>();
    private CircularProgressButton btnSendCommentCoach;
    private AppCompatEditText etComment;
    private LinearLayout llSendComment;
    private CommentAdapter commentAdapter;
    private Boolean isEdit = false;
    private String title = "";
    private boolean flagFavorite = false;
    private Result result;

    public void setCoachId(Integer coachId)
    {
        this.coachId = coachId;
    }

    public static HeadCoachFragment newInstance(MainActionView mainView, Integer coachId, String title, boolean flagFavorite)
    {
        HeadCoachFragment f = new HeadCoachFragment();
        f.setMainView(mainView, title, flagFavorite);
        f.setCoachId(coachId);
        return f;
    }

    private void setMainView(MainActionView mainView, String title, boolean flagFavorite)
    {
        this.mainView = mainView;
        this.title = title;
        this.flagFavorite = flagFavorite;
    }

    public HeadCoachFragment()
    {
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //   EventBus.getDefault().register(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener((v, keyCode, event) ->
        {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
            {
                if (flFragment.getVisibility() == View.VISIBLE)
                {
                    llCoach.setVisibility(View.VISIBLE);
                    btnShare.setVisibility(View.VISIBLE);
                    flFragment.setVisibility(View.GONE);
                } else
                {
                    getActivity().onBackPressed();

                }
                return true;
            }
            return false;
        });
    }

    private void initViews()
    {
        try
        {
            //  showLoading();

            //toolbar
            mToolbar = rootView.findViewById(R.id.toolbar);
            flFragment = rootView.findViewById(R.id.flFragment);
            llCoach = rootView.findViewById(R.id.llCoach);
            nested = rootView.findViewById(R.id.nested);
            pb = rootView.findViewById(R.id.pb);
            etComment = rootView.findViewById(R.id.etComment);
            llSendComment = rootView.findViewById(R.id.llSendComment);
            btnSendCommentCoach = rootView.findViewById(R.id.btnSendCommentCoach);
            rvComment = rootView.findViewById(R.id.rvComment);


            getAllComments(this);
          //  commentAdapter = new CommentAdapter(coachCommentList, this);
            //rvComment.setAdapter(commentAdapter);

            tvUserName = mToolbar.findViewById(R.id.tvUserName);
            TextView tvTitle = mToolbar.findViewById(R.id.tvTitle);
            tvTitle.setText(title);//("معرفی سرمربی");
            mToolbar.findViewById(R.id.imgBack).setOnClickListener(v -> mainView.backToMainFragment());

            tvUserName.setText(TrapConfig.HEADER_USER_NAME);
            rlShirt = mToolbar.findViewById(R.id.rlShirt);
            rlShirt.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    startActivityForResult(new Intent(SingletonContext.getInstance().getContext(), MyProfileActivity.class), 100);
                }
            });
            mToolbar.findViewById(R.id.imgMenu).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mainView.openDrawer();
                }
            });
            FrameLayout flLogoToolbar = mToolbar.findViewById(R.id.flLogoToolbar);
            flLogoToolbar.setOnClickListener(v ->
            {
                if (flFragment.getVisibility() == View.VISIBLE)
                {
                    llCoach.setVisibility(View.VISIBLE);
                    btnShare.setVisibility(View.VISIBLE);
                    flFragment.setVisibility(View.GONE);
                } else
                {
                    mainView.backToMainFragment();

                }

            });
            imgMenu = rootView.findViewById(R.id.imgMenu);

            imgMenu.setOnClickListener(v -> mainView.openDrawer());
            imgBack = rootView.findViewById(R.id.imgBack);
            imgBack.setOnClickListener(v ->
            {
                getActivity().onBackPressed();
            });

            tvPopularPlayer = mToolbar.findViewById(R.id.tvPopularPlayer);
            tvPopularPlayer.setText(String.valueOf(Prefs.getInt("popularPlayer", 12)));


            tabLayout = rootView.findViewById(R.id.tabLayout);
            view_pager = rootView.findViewById(R.id.view_pager);
            imgProfile = rootView.findViewById(R.id.imgProfile);
            btnShare = rootView.findViewById(R.id.btnShare);
            btnFavorit = rootView.findViewById(R.id.btnFavorit);
            btnShare.setOnClickListener(this);
            btnFavorit.setOnClickListener(this);
            if (flagFavorite)
            {
                btnFavorit.setVisibility(View.VISIBLE);
            } else
            {
                btnFavorit.setVisibility(View.GONE);

            }
            llInfoFromShare = rootView.findViewById(R.id.llInfoFromShare);
            llGallery = rootView.findViewById(R.id.llGallery);
            llGallery.setOnClickListener(this);
            btnSendCommentCoach.setOnClickListener(this);
            ViewCompat.setNestedScrollingEnabled(rvComment, true);


            FragmentManager childFragMan = getChildFragmentManager();
            FragmentTransaction childFragTrans = childFragMan.beginTransaction();
            MediaPlayersFragment fragB = MediaPlayersFragment.newInstance(null, mainView);
            childFragTrans.add(R.id.flFragment, fragB);
            childFragTrans.commit();
        } catch (Exception e)
        {
            e.getMessage();
        }
    }
    private void getAllComments(CommentAdapter.CommentAdapterEvents events)
    {

        pb.setVisibility(View.VISIBLE);
        SingletonService.getInstance().tractorTeamService().getCommentsTechsId(coachId, new OnServiceStatus<WebServiceClass<ResponseComments>>()
        {
            @Override
            public void onReady(WebServiceClass<ResponseComments> response)
            {
                pb.setVisibility(View.GONE);

                try
                {
                    if (response.info.statusCode == 200)
                    {

                        for (int i = 0; i < response.data.getResults().size(); i++)
                        {
                            coachCommentList.add(new CoachCommentModel("", false,response.data.getResults().get(i)));

                        }

                        commentAdapter = new CommentAdapter(coachCommentList, events);
                        rvComment.setAdapter(commentAdapter);


                    } else
                    {
                        showToast(getActivity(), response.info.message, R.color.red);

                    }

                } catch (Exception e)
                {
                    showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");

                }

            }

            @Override
            public void onError(String message)
            {
                pb.setVisibility(View.GONE);
                try
                {

                    if (Tools.isNetworkAvailable(getActivity()))
                    {
                        Logger.e("-OnError-", "Error: " + message);
                        showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                    } else
                    {
                        showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                    }
                } catch (Exception e)
                {
                }
            }
        });

    }
    private void getTechsId()
    {

        pb.setVisibility(View.VISIBLE);
        SingletonService.getInstance().tractorTeamService().getTechsId(coachId, new OnServiceStatus<WebServiceClass<GetTechsIdResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetTechsIdResponse> response)
            {
                pb.setVisibility(View.GONE);

                try
                {
                    if (response.info.statusCode == 200)
                    {

                        headProfileData = response.data;
                        if (headProfileData.getIs_favorite())
                        {
                            btnFavorit.setColorFilter(getContext().getResources().getColor(R.color.maryGold));
                        } else
                        {
                            btnFavorit.setColorFilter(getContext().getResources().getColor(R.color.shadowColor));

                        }
                        profileHeadCoahFragment.setData(headProfileData);

                    } else
                    {
                        showToast(getActivity(), response.info.message, R.color.red);

                    }
                } catch (Exception e)
                {
                    showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");

                }
            }

            @Override
            public void onError(String message)
            {
                pb.setVisibility(View.GONE);
                try
                {

                    if (Tools.isNetworkAvailable(getActivity()))
                    {
                        Logger.e("-OnError-", "Error: " + message);
                        showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                    } else
                    {
                        showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                    }
                } catch (Exception e)
                {
                }
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (rootView != null)
        {
            return rootView;

        }
        rootView = inflater.inflate(R.layout.fragment_head_coach, container, false);

        initViews();
        profileHeadCoahFragment = new ProfileHeadCoahFragment();
        historyHeadCoachFragment = new HistoryHeadCoachFragment();
        newsHeadCoachFragment = new NewsHeadCoachFragment();
        newsHeadCoachFragment.setCoachId(coachId);
        historyHeadCoachFragment.setCoachId(coachId);
        getTechsId();
        initViewPager();

        return rootView;
    }

    private void initViewPager()
    {
        addTabs(view_pager);
        view_pager.setOffscreenPageLimit(4);

        tabLayout.setupWithViewPager(view_pager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.borderColorRed));
        //tabLayout.getTabAt(4).select();
        new Handler().postDelayed(() -> view_pager.setCurrentItem(2, false), 50);

        changeFontInViewGroup(tabLayout, "fonts/iran_sans_normal.ttf");
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            public void onPageScrollStateChanged(int state)
            {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }

            public void onPageSelected(int position)
            {

            }
        });

    }

    private void addTabs(ViewPager viewPager)
    {
        adapter = new FragmentsPagerAdapter(getChildFragmentManager());

       /* adapter.addFrag("امتیازات", new CurrentPlayersFragment());
        adapter.addFrag("اتفاقات مهم", new TechnicalTeamFragment());
        adapter.addFrag("قراردادها", new TopPlayersFragment());*/
        adapter.addFrag("اتفاقات مهم", newsHeadCoachFragment);
        adapter.addFrag("سابقه", historyHeadCoachFragment);
        adapter.addFrag("مشخصات", profileHeadCoahFragment);

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

    }

    @Subscribe
    public void getHeaderContent(HeaderModel headerModel)
    {
        if (headerModel.getPopularNo() != 0)
        {
            tvPopularPlayer.setText(String.valueOf(headerModel.getPopularNo()));
        }
        tvUserName.setText(TrapConfig.HEADER_USER_NAME);
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
            case R.id.btnShare:
                new ScreenShot(llInfoFromShare, getActivity(), false, "برای ارسال، اخذ این مجوز الزامی است.");
                break;
            case R.id.btnFavorit:
                sendRequestFavorit();
                break;
            case R.id.llGallery:
                llCoach.setVisibility(View.GONE);
                btnShare.setVisibility(View.GONE);
                flFragment.setVisibility(View.VISIBLE);
                nested.scrollTo(0, 0);
                break;
            case R.id.btnSendCommentCoach:
                if (TextUtils.isEmpty(etComment.getText().toString()))
                {
                    etComment.setError("لطفا نظر خود را وارد نمایید.");
                    return;

                }
                btnSendCommentCoach.setEnabled(false);
                btnSendCommentCoach.startAnimation();
                hideSoftKeyboard(rootView, rootView.getContext());
                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        sendComment(coachId,etComment.getText().toString());

                        if (isEdit)
                        {
                            coachCommentList.get(0).setComment(etComment.getText().toString());
                            commentAdapter.notifyItemChanged(0);
                        } else
                        {
                            coachCommentList.add(0, new CoachCommentModel(etComment.getText().toString(), true,result));
                            commentAdapter.notifyItemInserted(0);
                        }


                        btnSendCommentCoach.setEnabled(true);
                        btnSendCommentCoach.revertAnimation();
                        llSendComment.setVisibility(View.GONE);

                    }
                }, 2000);


                break;

        }

    }

    private void sendComment(Integer coachId, String commentText)
    {

        pb.setVisibility(View.VISIBLE);
        RequestSendComment requestSendComment=new RequestSendComment();
        requestSendComment.setBody(commentText);
        SingletonService.getInstance().tractorTeamService().postCommentTechsId(coachId,requestSendComment, new OnServiceStatus<WebServiceClass<ResponseComments>>()
        {
            @Override
            public void onReady(WebServiceClass<ResponseComments> response)
            {
                pb.setVisibility(View.GONE);

                try
                {
                    if (response.info.statusCode == 200)
                    {

                        showToast(getActivity(), response.info.message, R.color.gray);


                    } else
                    {
                        showToast(getActivity(), response.info.message, R.color.red);

                    }

                } catch (Exception e)
                {
                    showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");

                }

            }

            @Override
            public void onError(String message)
            {
                pb.setVisibility(View.GONE);
                try
                {

                    if (Tools.isNetworkAvailable(getActivity()))
                    {
                        Logger.e("-OnError-", "Error: " + message);
                        showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                    } else
                    {
                        showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                    }
                } catch (Exception e)
                {
                }
            }
        });

    }
    private void sendReply(Integer coachId, String commentText)
    {

        pb.setVisibility(View.VISIBLE);
        RequestSendComment requestSendComment=new RequestSendComment();
        requestSendComment.setBody(commentText);
        SingletonService.getInstance().tractorTeamService().postReplyId(coachId,requestSendComment, new OnServiceStatus<WebServiceClass<ResponseComments>>()
        {
            @Override
            public void onReady(WebServiceClass<ResponseComments> response)
            {
                pb.setVisibility(View.GONE);

                try
                {
                    if (response.info.statusCode == 200)
                    {
                       // coachCommentList.add(0, new CoachCommentModel("", false,result));
                        commentAdapter.notifyDataSetChanged();//ItemInserted(0);
                        showToast(getActivity(), response.info.message, R.color.gray);


                    } else
                    {
                        showToast(getActivity(), response.info.message, R.color.red);

                    }

                } catch (Exception e)
                {
                    showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");

                }

            }

            @Override
            public void onError(String message)
            {
                pb.setVisibility(View.GONE);
                try
                {

                    if (Tools.isNetworkAvailable(getActivity()))
                    {
                        Logger.e("-OnError-", "Error: " + message);
                        showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                    } else
                    {
                        showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                    }
                } catch (Exception e)
                {
                }
            }
        });

    }
    private void sendRequestFavorit()
    {
//movaghati
        if (headProfileData.getIs_favorite())
        {
            btnFavorit.setColorFilter(getContext().getResources().getColor(R.color.shadowColor));
        } else
        {
            btnFavorit.setColorFilter(getContext().getResources().getColor(R.color.maryGold));

        }
        RequestSetFavoritePlayer requestSetFavoritePlayer = new RequestSetFavoritePlayer();

        requestSetFavoritePlayer.setPlayerId(coachId);
        pb.setVisibility(View.VISIBLE);
        SingletonService.getInstance().doTransferCardService().potFavoritPlayer(new OnServiceStatus<WebServiceClass<GetTechsIdResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<GetTechsIdResponse> response)
            {
                pb.setVisibility(View.GONE);


                try
                {

                    if (response.info.statusCode == 200)
                    {
                        btnFavorit.setColorFilter(getContext().getResources().getColor(R.color.maryGold));

                        showAlertSuccess(getContext(), response.info.message, "", false);

                    } else
                    {
                        btnFavorit.setColorFilter(getContext().getResources().getColor(R.color.shadowColor));

                    }
                } catch (Exception e)
                {

                }
            }

            @Override
            public void onError(String message)
            {
                pb.setVisibility(View.GONE);
                try
                {

                    if (Tools.isNetworkAvailable(getActivity()))
                    {
                        Logger.e("-OnError-", "Error: " + message);
                        showError(getActivity(), "خطا در دریافت اطلاعات از سرور!");
                    } else
                    {
                        showAlert(getActivity(), R.string.networkErrorMessage, R.string.networkError);
                    }
                } catch (Exception e)
                {
                }
            }
        }, requestSetFavoritePlayer);
    }

    @Override
    public void onDeleteCommentAdapter(Integer position)
    {
        coachCommentList.remove(0);
        commentAdapter.notifyItemRemoved(0);
        llSendComment.setVisibility(View.VISIBLE);
        isEdit = false;
    }

    @Override
    public void onEditCommentAdapter(Integer position)
    {
        isEdit = true;
        llSendComment.setVisibility(View.VISIBLE);
        etComment.setText(coachCommentList.get(position).getComment());

    }
}
