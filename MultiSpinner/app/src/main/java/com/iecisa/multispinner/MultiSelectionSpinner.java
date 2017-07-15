package com.iecisa.multispinner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jesus Manuel Muñoz Mazuecos on 29/6/16.
 */

public class MultiSelectionSpinner extends Spinner implements OnMultiChoiceClickListener {

    public interface OnMultipleItemsSelectedListener{
        void selectedIndices(List<Integer> indices);
        void selectedOptionValor(List<OptionValor> strings);
    }

    private OptionValor[] items = null;
    private boolean[] mSelection = null;
    private boolean[] mSelectionAtStart = null;
    private List<OptionValor> itemsAtStart = null;

    private ArrayAdapter<OptionValor> adapter;
    private OnMultipleItemsSelectedListener listener;

    public MultiSelectionSpinner(Context context) {
        super(context);

        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        super.setAdapter(adapter);
    }

    public MultiSelectionSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);

        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        super.setAdapter(adapter);
    }

    public void setListener(OnMultipleItemsSelectedListener listener){
        this.listener = listener;
    }

    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (mSelection != null && which < mSelection.length) {
            mSelection[which] = isChecked;
            adapter.clear();
            adapter.addAll(getSelectedItems());
        } else {
            throw new IllegalArgumentException("Argument 'which' is out of bounds.");
        }
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Multiselector dialog");
        builder.setMultiChoiceItems(getItemTextArray(), mSelection, this);

        itemsAtStart = getSelectedItems();
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.arraycopy(mSelection, 0, mSelectionAtStart, 0, mSelection.length);
                listener.selectedIndices(getSelectedIndices());
                listener.selectedOptionValor(getSelectedStrings());
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.clear();
                adapter.addAll(itemsAtStart);
                System.arraycopy(mSelectionAtStart, 0, mSelection, 0, mSelectionAtStart.length);
            }
        });
        builder.show();
        return true;
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        throw new RuntimeException(
                "setAdapter is not supported by MultiSelectSpinner.");
    }

    public void setItems(OptionValor[] items) {
        this.items = items;
        mSelection = new boolean[this.items.length];
        mSelectionAtStart = new boolean[this.items.length];
        adapter.clear();
        adapter.add(this.items[0]);
        Arrays.fill(mSelection, false);
        mSelection[0] = true;
        mSelectionAtStart[0] = true;
    }

    public void setItems(List<OptionValor> items) {
        this.items = items.toArray(new OptionValor[items.size()]);
        mSelection = new boolean[this.items.length];
        mSelectionAtStart  = new boolean[this.items.length];
        adapter.clear();
        adapter.add(this.items[0]);
        Arrays.fill(mSelection, false);
        mSelection[0] = true;
    }

    public void setSelection(OptionValor[] selection) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (OptionValor cell : selection) {
            for (int j = 0; j < items.length; ++j) {
                if (items[j].equals(cell)) {
                    mSelection[j] = true;
                    mSelectionAtStart[j] = true;
                }
            }
        }
        adapter.clear();
        adapter.addAll(getSelectedItems());
    }

    public void setSelection(List<OptionValor> selection) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (OptionValor sel : selection) {
            for (int j = 0; j < items.length; ++j) {
                if (items[j].equals(sel)) {
                    mSelection[j] = true;
                    mSelectionAtStart[j] = true;
                }
            }
        }
        adapter.clear();
        adapter.addAll(getSelectedItems());
    }

    public void setSelection(int index) throws IllegalArgumentException {
        for (int i = 0; i < mSelection.length; i ++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }

        if (index >= 0 && index < mSelection.length) {
            mSelection[index] = true;
            mSelectionAtStart[index] = true;
        } else {
            throw new IllegalArgumentException("Index " + index + " is out of bounds.");
        }
        adapter.clear();
        adapter.addAll(getSelectedItems());
    }

    public void setSelection(String selection) {
        String[] splitted = selection.split("\\|");
        List<Integer> selectedIndices = new ArrayList<>();

        for (int i = 0; i < items.length; i ++) {
            for (String id : splitted) {
                if(items[i].getId().equals(id)) {
                    selectedIndices.add(i);
                }
            }
        }

        setSelection(selectedIndices.toArray(new Integer[selectedIndices.size()]));
    }

    public void setSelection(Integer[] selectedIndices) {
        for (int i = 0; i < mSelection.length; i++) {
            mSelection[i] = false;
            mSelectionAtStart[i] = false;
        }
        for (int index : selectedIndices) {
            if (index >= 0 && index < mSelection.length) {
                mSelection[index] = true;
                mSelectionAtStart[index] = true;
            } else {
                throw new IllegalArgumentException("Index " + index + " is out of bounds.");
            }
        }
        adapter.clear();
        adapter.addAll(getSelectedItems());
    }

    public List<OptionValor> getSelectedStrings() {
        List<OptionValor> selection = new LinkedList<>();
        for (int i = 0; i < items.length; ++i) {
            if (mSelection[i]) {
                selection.add(items[i]);
            }
        }
        return selection;
    }

    public List<Integer> getSelectedIndices() {
        List<Integer> selection = new LinkedList<>();
        for (int i = 0; i < items.length; ++i) {
            if (mSelection[i]) {
                selection.add(i);
            }
        }
        return selection;
    }

    private List<OptionValor> getSelectedItems() {
        List<OptionValor> selected = new ArrayList<>();

        for (int i = 0; i < items.length; ++i) {
            if (mSelection[i]) {
                selected.add(items[i]);
            }
        } return selected;
    }

    private String[] getItemTextArray() {
        String[] textItems = new String[items.length];
        for (int i = 0; i < textItems.length; i ++) {
            textItems[i] = items[i].getText();
        } return textItems;
    }
}
