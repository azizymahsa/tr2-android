package com.traap.traapapp.ui.adapters.aboutUs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import com.traap.traapapp.R;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class ExpandableListHistoryAdapter
        extends BaseExpandableListAdapter
{

    private Context context;
    private Activity activity;

    private List<String> listDataGroup;

    private HashMap<String, String> listDataChild;


    public ExpandableListHistoryAdapter(Context context, List<String> listDataGroup,
                                        HashMap<String, String> listChildData,Activity activity) {
        this.context = context;
        this.activity = activity;
        this.listDataGroup = listDataGroup;
        this.listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
       // return this.listDataChild.get(this.listDataGroup.get(groupPosition)).get(childPosititon);
        return this.listDataChild.get(this.listDataGroup.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_row_child, null);
        }

       TextView textViewChild = convertView
                .findViewById(R.id.textViewChild);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
           textViewChild.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        textViewChild.setText(childText);




      /*  WebView webView=convertView.findViewById(R.id.webview);
        String youtContentStr = String.valueOf(Html
                .fromHtml("<![CDATA[<body style=\"text-align:justify;color:#484848; \">"
                        + childText
                        + "</body>]]>"));
        webView.loadData(youtContentStr, "text/html; charset=utf-8", "utf-8");
        webView.setVerticalScrollBarEnabled(false);*/

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
      //  return this.listDataChild.get(this.listDataGroup.get(groupPosition)).size();
        return 1;
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

        TextView textViewGroup = convertView
                .findViewById(R.id.textViewGroup);
       // textViewGroup.setTypeface(null, Typeface.BOLD);
        if(isExpanded)
        {
            textViewGroup.setTextColor(Color.WHITE);
            textViewGroup.setBackgroundColor(Color.RED);
        }else{
            textViewGroup.setTextColor(Color.parseColor("#484848"));
            textViewGroup.setBackgroundColor(Color.parseColor("#eaeaea"));
        }
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

