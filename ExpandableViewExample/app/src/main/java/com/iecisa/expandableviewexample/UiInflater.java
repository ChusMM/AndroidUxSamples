package com.iecisa.expandableviewexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import com.iecisa.expandableviewexample.customcontrols.AutoTextView;
import com.iecisa.expandableviewexample.customcontrols.DateControl;
import com.iecisa.expandableviewexample.customcontrols.InputText;
import com.iecisa.expandableviewexample.customcontrols.NumberInput;
import com.iecisa.expandableviewexample.customcontrols.SpinnerInput;
import com.iecisa.expandableviewexample.customcontrols.Switcher;

import java.util.List;

/**
 * Created by Jesús Manuel Muñoz on 10/06/16.
 */

public class UiInflater {

    public static void inflatePage(LinearLayout layout, Page page, Context context) {
        List<Section> sections = page.getSections();

        for (Section section : sections) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout parent = (LinearLayout) inflater.inflate(R.layout.section_container, layout, false);

            if (section.isMulti()) {
                MultiSection multiSection = (MultiSection) section;

                ViewGroup multiSectionLayout = UiInflater.inflateMultiSection(parent, context);

                ExpandableListView expListView = (ExpandableListView) multiSectionLayout.findViewById(R.id.expandable_list);
                ExpandableListAdapter listAdapter = new ExpandableListAdapter(context, section.getName(), multiSection.getGroupIdList(), multiSection.getGroups(), expListView);

                UiInflater.inflateMultiSectionHeader(multiSectionLayout, multiSection.getGroups().size(), listAdapter, context);
                expListView.setAdapter(listAdapter);
                setListViewHeightBasedOnChildren(expListView);
            } else {
                MonoSection monoSection = (MonoSection) section;
                ViewGroup monoSectionLayout = UiInflater.inflateMonoSection(parent, context);
                UiInflater.inflateMonoSectionHeader(monoSectionLayout, monoSection, context);

                ListView listView = (ListView) monoSectionLayout.findViewById(R.id.list_view);
                ListViewAdapter adapter = new ListViewAdapter(context, monoSection.getControls());
                listView.setAdapter(adapter);
                setListViewHeightBasedOnChildren(listView);
            }
            layout.addView(parent);
        }
    }

    private static ViewGroup inflateMultiSection(ViewGroup parent, Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup child = (ViewGroup) inflater.inflate(R.layout.multi_section, parent, false);
        parent.addView(child);

        return child;
    }

    private static void inflateMultiSectionHeader(ViewGroup multiSectionLayout, int groupCount, ExpandableListAdapter listAdapter, Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FrameLayout headerContainer = (FrameLayout) multiSectionLayout.findViewById(R.id.section_header_container);

        View child = inflater.inflate(R.layout.section_header_multi, headerContainer, false);
        headerContainer.addView(child);

        if (groupCount > 1) {
            HorizontalScrollView scroll = (HorizontalScrollView) child.findViewById(R.id.scroll_index_header);
            TableRow tRowSections = (TableRow) scroll.findViewById(R.id.index_header);

            for (int i = 0; i < groupCount; i ++) {
                UiInflater.inflateMultiSectionIndexButton(tRowSections, i, listAdapter, context);
            }
            listAdapter.setIndexHeaderView(tRowSections);
        }

        // Botón de nuevo
        TableRow newGroup = (TableRow) child.findViewById(R.id.bt_new_offer);
        if (newGroup != null) {
            newGroup.setOnClickListener(listAdapter);
        }
    }

    public static void inflateMultiSectionIndexButton(TableRow tRowSections, int pos, ExpandableListAdapter listAdapter, Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Button sectionButton = (Button) inflater.inflate(R.layout.section_button, tRowSections, false);

        sectionButton.setText(String.valueOf(pos + 1));
        sectionButton.setTag(pos);
        sectionButton.setOnClickListener(listAdapter);

        tRowSections.addView(sectionButton);
    }

    private static ViewGroup inflateMonoSection(ViewGroup parent, Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup child = (ViewGroup) inflater.inflate(R.layout.mono_section, parent, false);
        parent.addView(child);

        return child;
    }

    private static void inflateMonoSectionHeader(ViewGroup monoSectionLayout, MonoSection monoSection, Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FrameLayout headerContainer = (FrameLayout) monoSectionLayout.findViewById(R.id.section_header_container);

        View child = inflater.inflate(R.layout.section_header_mono, headerContainer, false);
        headerContainer.addView(child);

        TextView txSectionName = (TextView) child.findViewById(R.id.txSectionName);
        txSectionName.setText(monoSection.getName());
    }

    public static void inflateEditTextSectionRow(TableRow parent, ControlRow controlRow, Context context) {
        parent.setWeightSum(3.0f);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        UiInflater.inflateControlLabel(inflater, parent, controlRow, 2.0f);

        InputText inputText = (InputText) inflater.inflate(R.layout.input_text_item, parent, false);
        inputText.setControlRow(controlRow);

        inputText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        parent.addView(inputText);
    }


    public static void inflateSwitchSectionRow(TableRow parent, ControlRow controlRow, Context context) {
        parent.setWeightSum(3.0f);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        UiInflater.inflateControlLabel(inflater, parent, controlRow, 2.0f);

        Switcher switcher = (Switcher) inflater.inflate(R.layout.switch_item, parent, false);
        switcher.setControlRow(controlRow);

        switcher.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        parent.addView(switcher);
    }

    public static void inflateAutoTextViewSectionRow(TableRow parent, ControlRow controlRow, Context context) {
        parent.setWeightSum(3.0f);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        UiInflater.inflateControlLabel(inflater, parent, controlRow, 2.0f);

        AutoTextView autoTxView = (AutoTextView) inflater.inflate(R.layout.auto_text_view_item, parent, false);
        autoTxView.setControlRow(controlRow);

        autoTxView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        parent.addView(autoTxView);
    }

    public static void inflateNumberRow(TableRow parent, ControlRow controlRow, Context context) {
        parent.setWeightSum(3.0f);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        UiInflater.inflateControlLabel(inflater, parent, controlRow, 2.0f);

        NumberInput numberInput = (NumberInput) inflater.inflate(R.layout.number_inc_item, parent, false);
        numberInput.setControlRow(controlRow);

        numberInput.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        parent.addView(numberInput);
    }

    public static void inflateSpinnerRow(TableRow parent, ControlRow controlRow, Context context) {
        parent.setWeightSum(3.0f);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        UiInflater.inflateControlLabel(inflater, parent, controlRow, 2.0f);

        SpinnerInput spinnerInput = (SpinnerInput) inflater.inflate(R.layout.spinner_item, parent, false);
        spinnerInput.setControlRow(controlRow);

        spinnerInput.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        parent.addView(spinnerInput);
    }

    public static void inflateDatePicker(TableRow parent, ControlRow controlRow, Context context) {
        parent.setWeightSum(3.0f);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        UiInflater.inflateControlLabel(inflater, parent, controlRow, 2.0f);

        DateControl dateControl = (DateControl) inflater.inflate(R.layout.date_picker_item, parent, false);
        dateControl.setControlRow(controlRow);

        dateControl.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        parent.addView(dateControl);
    }

    private static void inflateControlLabel(LayoutInflater inflater, TableRow parent, ControlRow controlRow, float weightSum) {
        TextView txlabel = (TextView) inflater.inflate(R.layout.text_view_item, parent, false);
        txlabel.setText(controlRow.getLabel());
        txlabel.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, weightSum));
        parent.addView(txlabel);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup) {
                listItem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            }
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
