package com.iecisa.expandableviewexample;

import java.util.List;

/**
 * Created by Jesus Manuel Muñoz Mazuecos on 10/6/16.
 */

public class Page {
    String name;
    List<Section> sections;

    public Page(String name, List<Section> sections) {
        this.name = name;
        this.sections = sections;
    }

    public List<Section> getSections() {
        return this.sections;
    }
}
