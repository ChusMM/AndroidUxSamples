package com.iecisa.expandableviewexample;

/**
 * Created by jesusmanuelmunoz on 10/6/16.
 */
public abstract class Section {
    private String name;

    public Section(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isMulti() {
        return this instanceof MultiSection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Section section = (Section) o;

        return name.equals(section.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
