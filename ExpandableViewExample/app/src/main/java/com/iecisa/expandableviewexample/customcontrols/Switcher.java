package com.iecisa.expandableviewexample.customcontrols;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TableRow;

import com.iecisa.expandableviewexample.ControlRow;
import com.iecisa.expandableviewexample.R;

/**
 * Created by Jesús Manuel Muñoz on 13/06/16.
 */

public class Switcher extends TableRow implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "com.iecisa.expandableviewexample.customcontrols.Switcher";

    private Switch switcher = null;
    private ControlRow controlRow = null;

    public Switcher(Context context) {
        super(context);
    }

    public Switcher(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Switch getSwitcher() {
        if (this.switcher == null) {
            this.switcher = (Switch) findViewById(R.id.switcher);
            this.switcher.setOnCheckedChangeListener(this);
        } return this.switcher;
    }

    public ControlRow getControlRow() {
        return this.controlRow;
    }

    public void setControlRow(ControlRow controlRow) {
        this.controlRow = controlRow;
        getSwitcher().setChecked(controlRow.getSwitchStatus());

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean newValue) {
        if (controlRow != null) {
            this.controlRow.setSwitchStatus(newValue);
        } else {
            Log.e(TAG, "Switcher has no any ControlRow object related");
        }
    }
}
