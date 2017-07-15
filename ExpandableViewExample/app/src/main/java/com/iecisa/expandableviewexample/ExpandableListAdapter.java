package com.iecisa.expandableviewexample;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Jesus Manuel Muñoz @ Ieci on 9/6/16.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter implements View.OnClickListener, ExpandableListView.OnGroupExpandListener, ExpandableListView.OnGroupCollapseListener {

    private Context context;
    private String sectionName;
    private List<Integer> listDataHeader; // header titles
    private HashMap<Integer, List<ControlRow>> listDataChild;
    private TableRow indexHeader = null;
    private ExpandableListView listView = null;

    public ExpandableListAdapter(Context context, String sectionName, List<Integer> listDataHeader, HashMap<Integer, List<ControlRow>> listChildData, ExpandableListView listView) {
        this.context = context;
        this.sectionName = sectionName;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
        this.listView = listView;
        this.listView.setOnGroupExpandListener(this);
        this.listView.setOnGroupCollapseListener(this);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ControlRow childControl = (ControlRow) getChild(groupPosition, childPosition);

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TableRow tableRow = (TableRow) inflater.inflate(R.layout.section_item_container, null);

        /*if (convertView == null) {
        }*/

        switch (childControl.getType()) {
            case TEXT_EDITABLE:
                UiInflater.inflateEditTextSectionRow(tableRow, childControl, context);
                break;
            case SWITCH:
                UiInflater.inflateSwitchSectionRow(tableRow, childControl, context);
                break;
            case TEXT_VIEW:
                UiInflater.inflateAutoTextViewSectionRow(tableRow, childControl, context);
                break;
            case NUMBER:
                UiInflater.inflateNumberRow(tableRow, childControl, context);
                break;
            case SPINNER:
                UiInflater.inflateSpinnerRow(tableRow, childControl, context);
                break;
            case DATE:
                UiInflater.inflateDatePicker(tableRow, childControl, context);
                break;
        }
        return tableRow;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = this.sectionName + " - " + String.valueOf((int) getGroup(groupPosition) + 1);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.expandable_section_header, null);
        }

        TextView txListHeader = (TextView) convertView.findViewById(R.id.tx_list_header);
        txListHeader.setTypeface(null, Typeface.BOLD);
        txListHeader.setText(headerTitle);

        if (isExpanded) {
            convertView.setBackgroundResource(R.color.background_corporativo);
        } else {
            convertView.setBackgroundResource(R.color.text_dark_grey);
        }

        ImageView trashImg = (ImageView) convertView.findViewById(R.id.trash_img);
        trashImg.setTag(groupPosition);
        trashImg.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.trash_img) {
            int pos = (int) view.getTag();

            if (listDataHeader.remove(pos) != null) {
                listDataChild.remove(pos);

                this.reorderData();
                this.removeGroupInHeader(pos);
                this.notifyDataSetChanged();
                UiInflater.setListViewHeightBasedOnChildren(listView);
            }

        } else if (id == R.id.section_button) {
            int pos = (int) view.getTag();
            this.highlightGroupInHeader(pos, true);
            if (this.listView != null) {
                listView.expandGroup(pos);
            }
        } else if (id == R.id.bt_new_offer) {
            int listSize = listDataHeader.size();
            if (listSize > 0) {
                int lastId = listDataHeader.get(listSize - 1);
                int newId = lastId + 1;

                List<ControlRow> lastControls = listDataChild.get(lastId);
                List<ControlRow> newControls = new ArrayList<>();
                if (lastControls != null) {
                    for (int i = 0; i < lastControls.size(); i ++) {
                        newControls.add(lastControls.get(i).getEmptyCopy());
                    }
                    listDataHeader.add(newId);
                    listDataChild.put(newId, newControls);
                    this.notifyDataSetChanged();
                    listView.setAdapter(this);
                    UiInflater.inflateMultiSectionIndexButton(indexHeader, newId, this, context);
                    UiInflater.setListViewHeightBasedOnChildren(listView);
                }
            }
        }
    }

    @Override
    public void onGroupExpand(int pos) {
        this.highlightGroupInHeader(pos, true);
        UiInflater.setListViewHeightBasedOnChildren(listView);
    }

    @Override
    public void onGroupCollapse(int pos) {
        this.highlightGroupInHeader(pos, false);
        UiInflater.setListViewHeightBasedOnChildren(listView);
    }

    public void setIndexHeaderView(TableRow indexHeader) {
        this.indexHeader = indexHeader;
    }

    private void highlightGroupInHeader(int pos, boolean highlight) {
        if (this.indexHeader != null) {
            for (int i = 0; i < indexHeader.getChildCount(); i ++) {
                View child = indexHeader.getChildAt(i);
                int tag = (Integer) child.getTag();

                if (tag == pos) {
                    int res = highlight ? R.drawable.rounded_button_selected : R.drawable.rounded_button_unselected;
                    child.setBackgroundResource(res);
                }
            }
        }
    }

    private void removeGroupInHeader(int pos) {
        if (this.indexHeader != null) {
            for (int i = 0; i < indexHeader.getChildCount(); i++) {
                View child = indexHeader.getChildAt(i);
                int tag = (Integer) child.getTag();

                if (tag == pos) {
                    this.indexHeader.removeViewAt(i);
                }
            }

            for (int i = 0; i < indexHeader.getChildCount(); i++) {
                Button sectionButton = (Button) indexHeader.getChildAt(i);
                sectionButton.setText(String.valueOf(i + 1));
                sectionButton.setTag(i);
            }
        }
    }

    private void reorderData() {
        for (int i = 0; i < listDataHeader.size(); i ++) {
            if (listDataHeader.get(i) != i) {
                listDataHeader.set(i, i);
            }
        }

        Set<Integer> keySet = listDataChild.keySet();
        Integer[] keys = keySet.toArray(new Integer[keySet.size()]);
        for (int i = 0; i < keys.length; i ++) {
            if (keys[i] != i) {
                List<ControlRow> controls = listDataChild.get(keys[i]);
                if (controls != null) {
                    listDataChild.remove(keys[i]);
                    listDataChild.put(i, controls);
                }
            }
        }
    }
}