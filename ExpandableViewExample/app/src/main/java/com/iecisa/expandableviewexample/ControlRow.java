package com.iecisa.expandableviewexample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jesús Manuel Muñoz on 10/06/16.
 */

public class ControlRow {
    private static final String TAG = "com.iecisa.expandableviewexample";

    public enum FieldType {
        TEXT_EDITABLE,
        SWITCH,
        TEXT_VIEW,
        NUMBER,
        SPINNER,
        DATE,
    }

    private String label = null;
    private FieldType type = null;
    private String editTextContent = null;
    private boolean switchStatus;
    private String textViewValue = null;
    private boolean editable;
    private int number;
    private String spinnerSelection = null;
    private String[] spinnerChoices = null;
    private int day;
    private int month;
    private int year;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy", Locale.getDefault());

    public ControlRow(String label, String editTextContent, boolean editable) {
        this.label = label;
        this.editTextContent = editTextContent;
        this.editable = editable;
        this.type = FieldType.TEXT_EDITABLE;
    }

    public ControlRow(String label, boolean switchStatus) {
        this.label = label;
        this.switchStatus = switchStatus;
        this.type = FieldType.SWITCH;
    }

    public ControlRow(String label, String textViewValue) {
        this.label = label;
        this.textViewValue = textViewValue;
        this.type = FieldType.TEXT_VIEW;
    }

    public ControlRow(String label, int number) {
        this.label = label;
        this.number = number;
        this.type = FieldType.NUMBER;
        this.editable = false;
    }

    public ControlRow(String label, String spinnerSelection, String[] spinnerChoices) {
        this.label = label;
        this.spinnerSelection = spinnerSelection;
        this.spinnerChoices = spinnerChoices;
        this.type = FieldType.SPINNER;
    }

    public ControlRow(String label, Date date) {
        this.label = label;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.day = cal.get(Calendar.DAY_OF_MONTH);
        this.month = cal.get(Calendar.MONTH);
        this.year = cal.get(Calendar.YEAR);
        this.type = FieldType.DATE;
    }

    /**
     * Getters *
     **/
    public String getLabel() {
        return this.label;
    }

    public FieldType getType() {
        return this.type;
    }

    public String getEditTextValue() {
        return this.editTextContent;
    }

    public boolean getSwitchStatus() {
        return this.switchStatus;
    }

    public String getTextViewValue() {
        return this.textViewValue;
    }

    public int getNumber() {
        return this.number;
    }

    public String[] getSpinnerChoices() {
        return this.spinnerChoices;
    }

    public String getSpinnerSelection() {
        if (this.spinnerSelection == null) {
            this.spinnerSelection = spinnerChoices != null && spinnerChoices.length > 0 ? spinnerChoices[0] : "";
        }
        return this.spinnerSelection;
    }

    public int getDay() {
        return this.day;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }

    /**
     * Setters *
     **/
    public void setLabel(String label) {
        this.label = label;
    }

    public void setEditTextValue(String editTextContent) {
        this.editTextContent = editTextContent;
    }

    public void setSwitchStatus(boolean switchStatus) {
        this.switchStatus = switchStatus;
    }

    public void setTextViewValue(String textViewValue) {
        this.textViewValue = textViewValue;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isEditable() {
        return this.editable;
    }

    public void setSpinnerChoices(String[] spinnerChoices) {
        this.spinnerChoices = spinnerChoices;
    }

    public void setSpinnerSelection(String spinnerSelection) {
        this.spinnerSelection = spinnerSelection;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ControlRow getEmptyCopy() {
        switch (type) {
            case TEXT_EDITABLE:
                return new ControlRow(label, "", editable);
            case SWITCH:
                return new ControlRow(label, false);
            case TEXT_VIEW:
                return new ControlRow(label, "");
            case NUMBER:
                return new ControlRow(label, 0);
            case SPINNER:
                return new ControlRow(label, "", spinnerChoices);
            case DATE:
                Calendar cal = Calendar.getInstance();
                return new ControlRow(label, cal.getTime());
            default:
                return null;
        }
    }
}
