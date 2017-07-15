package com.iecisa.expandableviewexample.customcontrols;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableRow;

import com.iecisa.expandableviewexample.ControlRow;
import com.iecisa.expandableviewexample.R;

/**
 * Created by Jesús Manuel Muñoz on 14/6/16.
 */

public class SpinnerInput extends TableRow implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "com.iecisa.expandableviewexample.customcontrols.SpinnerInput";

    private Spinner spinner = null;
    private ArrayAdapter<String> adapter = null;
    private ControlRow controlRow = null;

    public SpinnerInput(Context context) {
        super(context);
    }

    public SpinnerInput(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Spinner getSpinner() {
        if (this.spinner == null) {
            this.spinner = (Spinner) findViewById(R.id.spinner);
            this.spinner.setOnItemSelectedListener(this);
        }
        return spinner;
    }

    public ControlRow getControlRow() {
        return controlRow;
    }

    public void setControlRow(ControlRow controlRow) {
        this.controlRow = controlRow;
        setAdapterValues(controlRow.getSpinnerChoices());
        setSelected(controlRow.getSpinnerSelection());
    }

    private void setAdapterValues(String[] values) {
        this.adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_selection_item, values);
        this.adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getSpinner().setAdapter(adapter);
    }

    private void setSelected(String value) {
        if (adapter != null && controlRow != null) {
            String[] choices = this.controlRow.getSpinnerChoices();
            for (int i = 0; i < choices.length; i++) {
                if (choices[i].equals(value)) {
                    getSpinner().setSelection(i);
                }
            }
        } else {
            Log.e(TAG, "SpinnerInput has no any ControlRow object or adapter related");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (adapter != null && controlRow != null) {
            controlRow.setSpinnerSelection(adapter.getItem(position));
        } else {
            Log.e(TAG, "SpinnerInput has no any ControlRow object or adapter related");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}
