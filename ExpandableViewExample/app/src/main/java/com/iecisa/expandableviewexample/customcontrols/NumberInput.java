package com.iecisa.expandableviewexample.customcontrols;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.iecisa.expandableviewexample.ControlRow;
import com.iecisa.expandableviewexample.R;

/**
 * Created by Jesús Manuel Muñoz on 13/06/16.
 */

public class NumberInput extends TableRow implements TextWatcher, View.OnClickListener {
    private static final String TAG = "com.iecisa.expandableviewexample.customcontrols.NumberInput";
    private static final int MIN = 0;
    private static final int MAX = 1000;

    private TextView txValue = null;
    private Button btPlus = null;
    private Button btMinus = null;
    private ControlRow controlRow = null;

    public NumberInput(Context context) {
        super(context);
    }

    public NumberInput(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextView getTextView() {
        if (txValue == null) {
            txValue = (TextView) findViewById(R.id.num_value);
            this.txValue.addTextChangedListener(this);
        } return this.txValue;
    }

    private Button getBtMinus() {
        if (btMinus == null) {
            this.btMinus = (Button) findViewById(R.id.bt_minus);
        } return this.btMinus;
    }

    private Button getBtPlus() {
        if (btPlus == null) {
            this.btPlus = (Button) findViewById(R.id.bt_plus);
        } return this.btPlus;
    }

    public ControlRow getControlRow() {
        return this.controlRow;
    }

    public void setControlRow(ControlRow controlRow) {
        this.controlRow = controlRow;

        getTextView().setText(String.valueOf(controlRow.getNumber()));
        getBtMinus().setOnClickListener(this);
        getBtPlus().setOnClickListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //TODO:
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try {
            int val = Integer.parseInt(s.toString());
            if (this.controlRow != null) {
                this.controlRow.setNumber(val);
            } else {
                Log.e(TAG, "NumberInput has no any ControlRow object related");
            }
        } catch (NumberFormatException e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        //TODO:
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        try {
            switch(id) {
                case R.id.bt_minus:
                    try {
                        int val = Integer.parseInt(getTextView().getText().toString());
                        if (val > MIN) {
                            val--;
                        }
                        getTextView().setText(String.valueOf(val));
                    } catch (NumberFormatException e) {
                        Log.e(TAG, e.toString());
                    }
                    break;

                case R.id.bt_plus:
                    try {
                        int val = Integer.parseInt(getTextView().getText().toString());
                        if (val < MAX) {
                            val++;
                        }
                        getTextView().setText(String.valueOf(val));
                    } catch (NumberFormatException e) {
                        Log.e(TAG, e.toString());
                    }
                    break;
            }
        } catch (NullPointerException e) {
            Log.e(TAG, e.toString());
        }
    }
}
