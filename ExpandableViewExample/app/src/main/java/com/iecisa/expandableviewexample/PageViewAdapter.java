package com.iecisa.expandableviewexample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by Jesús Manuel Muñoz on 15/06/16.
 */

public class PageViewAdapter extends ArrayAdapter<LinearLayout> {

    private Context context;
    private List<LinearLayout> sectionList;

    public PageViewAdapter(Context context, List<LinearLayout> sectionList) {
        super(context, R.layout.section_container, sectionList);

        this.context = context;
        this.sectionList = sectionList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LinearLayout row = sectionList.get(position);

        //LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LinearLayout row = (LinearLayout) inflater.inflate(R.layout.section_container, null);

        /*if (convertView == null) {
        }*/
        return row;
    }
}
