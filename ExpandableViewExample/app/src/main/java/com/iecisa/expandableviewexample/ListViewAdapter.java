package com.iecisa.expandableviewexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableRow;

import java.util.List;

/**
 * Created by Jesús Manuel Muñoz on 15/06/16.
 */

public class ListViewAdapter extends ArrayAdapter<ControlRow> {

    private Context context;
    private List<ControlRow> controlList;

    public ListViewAdapter(Context context, List<ControlRow> controlList) {
        super(context, R.layout.section_item_container, controlList);

        this.context = context;
        this.controlList = controlList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ControlRow childControl = controlList.get(position);

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TableRow tableRow = (TableRow) inflater.inflate(R.layout.section_item_container, null);

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
}
