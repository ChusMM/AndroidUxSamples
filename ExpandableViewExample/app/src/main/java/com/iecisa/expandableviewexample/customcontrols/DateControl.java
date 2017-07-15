package com.iecisa.expandableviewexample.customcontrols;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TableRow;
import android.widget.TextView;

import com.iecisa.expandableviewexample.ControlRow;
import com.iecisa.expandableviewexample.R;

import java.util.Locale;

/**
 * Created by Jesús Manuel Muñoz on 13/06/16.
 */

public class DateControl extends TableRow implements DatePickerDialog.OnDateSetListener, View.OnClickListener {
    private static final String TAG = "com.iecisa.expandableviewexample.customcontrols.DateControl";

    private TextView txDate = null;
    private ControlRow controlRow = null;

    public DateControl(Context context) {
        super(context);
    }

    public DateControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextView getTxDate() {
        if (this.txDate == null) {
            this.txDate = (TextView) findViewById(R.id.tx_date_picker);
            this.txDate.setOnClickListener(this);
        } return txDate;
    }

    public ControlRow getControlRow() {
        return this.controlRow;
    }

    public void setControlRow(ControlRow controlRow) {
        this.controlRow = controlRow;
        this.setTxDateContent();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tx_date_picker) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this, controlRow.getYear(), controlRow.getMonth(), controlRow.getDay());
            datePickerDialog.setTitle("Selección de fecha");
            datePickerDialog.show();
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        this.controlRow.setYear(year);
        this.controlRow.setMonth(month);
        this.controlRow.setDay(day);
        this.setTxDateContent();
    }

    private void setTxDateContent() {
        if (controlRow != null) {
            String day = String.format(Locale.getDefault(), "%02d", controlRow.getDay());
            String month = String.format(Locale.getDefault(), "%02d", controlRow.getMonth() + 1);
            String year = String.format(Locale.getDefault(), "%02d", controlRow.getYear());
            String date =  day + "/" + month + "/" + year;
            getTxDate().setText(date);
        } else {
            Log.e(TAG, "DateControl has no any ControlRow object related");
        }
    }
}
