package com.study.invertedindex.model;

public class Text {

    private String textId;
    private String text;

    public Text(){}

    public Text(String text){
        this.text = text;
    }

    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return "{textId=" + textId + "}";
    }

}
