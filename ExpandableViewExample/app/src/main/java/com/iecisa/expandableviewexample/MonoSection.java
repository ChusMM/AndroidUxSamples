package com.iecisa.expandableviewexample;

import java.util.List;

/**
 * Created by Jesús Manuel Muñoz on 10/6/16.
 */
public class MonoSection extends Section {
    List<ControlRow> controls;

    public MonoSection(String name, List<ControlRow> controls) {
        super(name);
        this.controls = controls;
    }

    public List<ControlRow> getControls() {
        return controls;
    }
}
