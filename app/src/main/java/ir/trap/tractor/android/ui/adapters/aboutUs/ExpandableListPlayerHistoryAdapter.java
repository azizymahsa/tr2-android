package ir.trap.tractor.android.ui.adapters.aboutUs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.model.getHistory.PlayersCurrent;

public class ExpandableListPlayerHistoryAdapter
        extends BaseExpandableListAdapter
{

    private Context context;

    // group titles
    private List<String> listDataGroup;

    // child data
    private HashMap<String, List<PlayersCurrent>> listDataChild;

    public ExpandableListPlayerHistoryAdapter(Context context, List<String> listDataGroup,
                                              HashMap<String,  List<PlayersCurrent>> listChildData) {
        this.context = context;
        this.listDataGroup = listDataGroup;
        this.listDataChild = listChildData;
    }


    @Override
    public PlayersCurrent getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataGroup.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

       // final String childText = getChild(groupPosition, childPosition).getFirstName();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_row_player_history, null);
        }

        TextView tvNumber = convertView.findViewById(R.id.tvNumber);
        TextView tvNationality = convertView.findViewById(R.id.tvNationality);
        TextView tvPosition = convertView.findViewById(R.id.tvPosition);
        TextView tvName = convertView.findViewById(R.id.tvName);


        tvName.setText(getChild(groupPosition, childPosition).getFirstName()+" "+getChild(groupPosition, childPosition).getLastName());
        tvPosition.setText(getChild(groupPosition, childPosition).getPosition());
        tvNationality.setText(getChild(groupPosition, childPosition).getNationality());
        tvNumber.setText(getChild(groupPosition, childPosition).getNumber()+"");
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataGroup.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataGroup.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataGroup.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_row_group, null);
        }

        TextView textViewGroup = convertView.findViewById(R.id.textViewGroup);
        if(isExpanded)
        {
            textViewGroup.setTextColor(Color.WHITE);
            textViewGroup.setBackgroundColor(Color.RED);
        }else{
            textViewGroup.setTextColor(Color.parseColor("#484848"));
            textViewGroup.setBackgroundColor(Color.parseColor("#eaeaea"));
        }
        //textViewGroup.setTypeface(null, Typeface.BOLD);
        textViewGroup.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
