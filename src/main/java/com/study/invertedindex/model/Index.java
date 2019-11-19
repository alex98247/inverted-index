package com.study.invertedindex.model;

import java.util.List;

public class Index {
    private String word;
    private List<String> textIds;

    public Index(String word, List<String> textIds){
        this.word = word;
        this.textIds = textIds;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getTextIds() {
        return textIds;
    }

    public void setTextIds(List<String> textIds) {
        this.textIds = textIds;
    }

    @Override
    public String toString() {
        return "{world=" + word +  "textId=" + textIds + "}";
    }
}
