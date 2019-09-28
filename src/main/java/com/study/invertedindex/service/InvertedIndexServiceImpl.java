package com.study.invertedindex.service;

import com.study.invertedindex.model.Index;
import com.study.invertedindex.model.Text;
import com.study.invertedindex.repository.IndexRepository;
import com.study.invertedindex.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvertedIndexServiceImpl implements InvertedIndexService {

    @Autowired
    IndexRepository indexRepository;

    @Autowired
    TextRepository textRepository;

    public List<Text> findTexts(String word) {
        Index index = indexRepository.findByWord(word);
        if (index == null) return Collections.EMPTY_LIST;
        return index.getTextIds().stream()
                .map(x -> textRepository.findTextByTextId(x))
                .collect(Collectors.toList());
    }

    public void addText(Text text) {
        text = textRepository.save(text);
        String[] words = text.getText().split(" ");

        for (String word : words) {
            Index index = indexRepository.findByWord(word);
            if (index == null) {
                index = new Index(word, new ArrayList<>());
            }

            List<String> textIds = index.getTextIds();
            if (!textIds.contains(text.getTextId())) {
                textIds.add(text.getTextId());
                index.setTextIds(textIds);
            }

            indexRepository.save(index);
        }
    }

    public List<Text> findTexts(List<String> words) {
        List<Text> texts = new ArrayList<>();
        for (String word : words) {
            texts.addAll(findTexts(word));
        }
        return texts;
    }
}
