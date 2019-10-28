package ir.trap.tractor.android.ui.fragments.ticket.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import ir.trap.tractor.android.ui.fragments.ticket.CompeletInfoFragment;
import ir.trap.tractor.android.ui.fragments.ticket.CountTicketFragment;
import ir.trap.tractor.android.ui.fragments.ticket.SelectPositionFragment;

public class PagerAdapter
        extends FragmentStatePagerAdapter
{

    private int numTabs;
   /* private SubMenuModel[] gdsModel1;
    private SubMenuModel[] insuranceModel2;
    private SubMenuModel[] aloparkModel3;
    private SubMenuModel[] shargModel4;
    private SubMenuModel[] ghabzModel6;*/

    public PagerAdapter(FragmentManager fm, int numTabs)
    {
        super(fm);
        this.numTabs = numTabs;

      /*  this.gdsModel1 = gdsModel1;
        this.insuranceModel2 = insuranceModel2;
        this.aloparkModel3 = aloparkModel3;
        this.shargModel4 = shargModel4;
        this.ghabzModel6 = ghabzModel6;*/
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0:
                CountTicketFragment tab1 = CountTicketFragment.newInstance("TAB1");
                return tab1;
            case 1:
                SelectPositionFragment tab2 = SelectPositionFragment.newInstance("TAB2");
                return tab2;
            case 2:
                CompeletInfoFragment tab3 = CompeletInfoFragment.newInstance("TAB3");
                return tab3;
            case 3:
                SelectPositionFragment tab4 = SelectPositionFragment.newInstance("TAB4");
                return tab4;


            default:
                throw new RuntimeException("Tab position invalid " + position);
        }
    }

    @Override
    public int getCount()
    {
        return numTabs;
    }

}
