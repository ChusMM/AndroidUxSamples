package com.iecisa.multispinner;


public class OptionValor {

    private String id;
    private String text;

    public OptionValor() { }

    public OptionValor(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String valor) {
        this.text = valor;
    }

    @Override
    public String toString() {
        return this.text != null ? this.text : "";
    }
}