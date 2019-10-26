package com.study.invertedindex.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "index")
public class Index {
    @Id
    private String id;
    private String word;
    private List<String> textIds;

    public Index(){}
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
