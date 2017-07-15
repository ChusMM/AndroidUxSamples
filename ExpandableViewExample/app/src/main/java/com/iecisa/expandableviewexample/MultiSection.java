package com.iecisa.expandableviewexample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jesús Manuel Muñoz Mazuecos on 10/06/16.
 */

public class MultiSection extends Section {
    Map<Integer, List<ControlRow>> groups;

    public MultiSection(String name, Map<Integer, List<ControlRow>> groups) {
        super(name);
        this.groups = groups;
    }

    public HashMap<Integer, List<ControlRow>> getGroups() {
        return (HashMap<Integer, List<ControlRow>>) this.groups;
    }

    public List<ControlRow> getControlsAt(int index) throws IndexOutOfBoundsException {
        List<ControlRow> controls = groups.get(index);
        if (controls != null) {
            return controls;
        } else {
            throw new IndexOutOfBoundsException("Group index out of bounds within section");
        }
    }

    public List<Integer> getGroupIdList() {
        Set<Integer> keySet = groups.keySet();
        List<Integer> keys = new ArrayList<>();
        for (Integer key : keySet) {
            keys.add(key);
        } return keys;
    }
}
