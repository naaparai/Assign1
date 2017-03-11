package com.dogpo.assign1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Netforce on 7/12/2016.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    ArrayList<ExpandHeaderData> expandHeaderDatas = new ArrayList<>();
    HashMap<ExpandHeaderData, List<TeamListData>> expandChildDatas = new HashMap<>();
    private clickListner click;

    public ExpandableListAdapter(Context context, ArrayList<ExpandHeaderData> expandHeaderDatas,
                                 HashMap<ExpandHeaderData, List<TeamListData>> expandChildDatas) {
        this._context = context;
        this.expandChildDatas = expandChildDatas;
        this.expandHeaderDatas = expandHeaderDatas;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.expandChildDatas.get(this.expandHeaderDatas.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_child, parent, false);
        }
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.textViewChild);
        TextView textViewOccurance = (TextView) convertView.findViewById(R.id.textViewOccurance);
        //final String childText = (String) getChild(groupPosition, childPosition);
        String childText = expandChildDatas.get(expandHeaderDatas.get(groupPosition)).get(childPosition).word;
        int occurance = expandChildDatas.get(expandHeaderDatas.get(groupPosition)).get(childPosition).occurance;
        txtListChild.setText(childText);
        textViewOccurance.setText(occurance + "");


        final View finalConvertView = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.itemClicked(finalConvertView, groupPosition, childPosition);
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.expandChildDatas.get(this.expandHeaderDatas.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.expandHeaderDatas.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandHeaderDatas.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = expandHeaderDatas.get(groupPosition).occurrance;
        LayoutInflater infalInflater = (LayoutInflater) this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = infalInflater.inflate(R.layout.list_group, parent, false);
        TextView textViewCompetitionName = (TextView) convertView.findViewById(R.id.textViewHeader);
        textViewCompetitionName.setText(headerTitle);


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

    public interface clickListner {
        void itemClicked(View view, int groupview, int childview);
    }

    public void setClickListner(clickListner click) {
        this.click = click;
    }
}