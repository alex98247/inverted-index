package com.study.invertedindex.service;

import com.study.invertedindex.model.Index;
import com.study.invertedindex.model.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InvertedIndexServiceImpl implements InvertedIndexService {

    private Logger logger = LoggerFactory.getLogger(InvertedIndexService.class);

    /**
     * The in memory index storage
     */
    @Autowired
    Map<String, Index> inMemoryIndex;

    /**
     * @param words
     * @return Find texts by words. If none text would be found return empty list.
     */
    @Override
    public List<Text> findTexts(List<String> words) {
        logger.info("Find texts by words " + words);

        words = words.stream().map(String::toLowerCase).distinct().collect(Collectors.toList());
        List<Index> indexes = findIndex(words);
        if (indexes == null || indexes.isEmpty()) return Collections.EMPTY_LIST;

        logger.info("Found indexes " + indexes);
        Set<String> textIds = new HashSet<>();
        indexes.forEach(x -> textIds.addAll(x.getTextIds()));

        return textIds.stream()
                .map(this::readFile)
                .filter(Objects::nonNull)
                .map(Text::new)
                .collect(Collectors.toList());
    }

    /**
     * @param text Add text to DB.
     */
    @Override
    public void addText(Text text) {
        String fileName = UUID.randomUUID() + ".txt";
        text.setTextId(fileName);

        //Save to file
        try (PrintWriter out = new PrintWriter(fileName)) {
            out.println(text.getText());
        } catch (FileNotFoundException e) {
            logger.error("Can not create file: " + fileName);
            e.printStackTrace();
        }

        String[] words = text.getText().replaceAll("\\p{P}", "").split("\\s+");
        words = Arrays.stream(words).map(String::toLowerCase).distinct().toArray(String[]::new);

        for (String word : words) {
            Index index = inMemoryIndex.get(word);
            if (index == null) {
                index = new Index(word, new ArrayList<>());
            }

            List<String> textIds = index.getTextIds();
            if (!textIds.contains(text.getTextId())) {
                textIds.add(text.getTextId());
            }

            inMemoryIndex.put(index.getWord(), index);
        }
    }

    private List<Index> findIndex(List<String> words) {
        return words.stream().map(word -> inMemoryIndex.get(word))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private String readFile(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            logger.error("Path " + path + " not found");
            e.printStackTrace();
            return null;
        }
    }
}
