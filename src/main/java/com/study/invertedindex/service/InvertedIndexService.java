package com.study.invertedindex.service;

import com.study.invertedindex.model.Text;

import java.util.List;

public interface InvertedIndexService {
    List<Text> findTexts(List<String> word);
    void addText(Text text) throws Exception;
}
