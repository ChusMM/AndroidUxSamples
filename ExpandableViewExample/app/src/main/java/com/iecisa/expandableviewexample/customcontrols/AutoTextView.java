package com.iecisa.expandableviewexample.customcontrols;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TableRow;
import android.widget.TextView;

import com.iecisa.expandableviewexample.ControlRow;
import com.iecisa.expandableviewexample.R;

/**
 * Created by Jesus Manuel Munoz on 13/06/16.
 */
public class AutoTextView extends TableRow implements TextWatcher {
    private static final String TAG = "com.iecisa.expandableviewexample.customcontrols.AutoTextView";

    private TextView textView = null;
    private ControlRow controlRow = null;

    public AutoTextView(Context context) {
        super(context);
    }

    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextView getTextView() {
        if (this.textView == null) {
            this.textView = (TextView) findViewById(R.id.text_view);
            this.textView.addTextChangedListener(this);
        }
        return this.textView;
    }

    public ControlRow getControlRow() {
        return this.controlRow;
    }

    public void setControlRow(ControlRow controlRow) {
        this.controlRow = controlRow;
        getTextView().setText(controlRow.getTextViewValue());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //TODO:
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (controlRow != null) {
            this.controlRow.setTextViewValue(s.toString());
        } else {
            Log.e(TAG, "AutoTextView has no any ControlRow object related");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        //TODO:
    }
}
