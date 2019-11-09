package com.study.invertedindex.service;

import com.study.invertedindex.model.Index;
import com.study.invertedindex.model.Text;
import com.study.invertedindex.repository.IndexRepository;
import com.study.invertedindex.repository.TextRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvertedIndexServiceImpl implements InvertedIndexService {

    private Logger logger = LoggerFactory.getLogger(InvertedIndexService.class);

    /**
     * The index repository
     */
    @Autowired
    IndexRepository indexRepository;

    /**
     * The text repository
     */
    @Autowired
    TextRepository textRepository;

    /**
     * @param words
     * @return Find texts by words. If none text would be found return empty list.
     */
    @Override
    public List<Text> findTexts(List<String> words) {
        logger.info("Find texts by words " + words);
        words = words.stream().map(String::toLowerCase).distinct().collect(Collectors.toList());
        List<Index> indexes = indexRepository.findByWordIn(words);
        if (indexes == null || indexes.isEmpty()) return Collections.EMPTY_LIST;

        logger.info("Found indexes " + indexes);
        Set<String> textIds = new HashSet<>();
        indexes.forEach(x -> textIds.addAll(x.getTextIds()));

        return textIds.stream()
                .map(x -> textRepository.findTextByTextId(x))
                .collect(Collectors.toList());
    }

    /**
     * @param text
     *
     * Add text to DB.
     */
    @Override
    public void addText(Text text) {
        text = textRepository.save(text);
        String[] words = text.getText().replaceAll("\\p{P}", "").split("\\s+");
        words = Arrays.stream(words).map(String::toLowerCase).distinct().toArray(String[]::new);

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
}
