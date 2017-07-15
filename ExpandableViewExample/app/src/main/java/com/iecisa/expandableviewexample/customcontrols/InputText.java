package com.iecisa.expandableviewexample.customcontrols;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.TableRow;

import com.iecisa.expandableviewexample.ControlRow;
import com.iecisa.expandableviewexample.R;

/**
 * Created by Jesús Manuel Muñoz on 13/06/16.
 */

public class InputText extends TableRow implements TextWatcher {
    private static final String TAG = "com.iecisa.expandableviewexample.customcontrols.InputText";

    private EditText editText = null;
    private ControlRow controlRow = null;

    public InputText(Context context) {
        super(context);
    }

    public InputText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditText getEditText() {
        if (editText == null) {
            this.editText = (EditText) findViewById(R.id.edit_text);
            this.editText.addTextChangedListener(this);
        } return this.editText;
    }

    public ControlRow getControlRow() {
        return this.controlRow;
    }

    public void setControlRow(ControlRow controlRow) {
        this.controlRow = controlRow;
        getEditText().setText(controlRow.getEditTextValue());
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //TODO:
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (controlRow != null) {
            this.controlRow.setEditTextValue(s.toString());
        } else {
            Log.e(TAG, "InputText has no any ControlRow object related");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        //TODO:
    }
}
