package com.study.invertedindex.controller;

import com.study.invertedindex.model.Text;
import com.study.invertedindex.service.InvertedIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invertedindex")
public class InvertedIndexController {

    private Logger logger = LoggerFactory.getLogger(InvertedIndexController.class);

    /**
     * The inverted index service
     */
    @Autowired
    InvertedIndexService invertedIndexService;

    /**
     * @param words
     * @return All texts found by words.
     */
    @GetMapping
    public List<Text> getTexts(@RequestParam List<String> words) {
        logger.info("GET request with param: " + words);
        return invertedIndexService.findTexts(words);
    }

    /**
     * @param text
     *
     * Add text.
     */
    @PostMapping
    public void addText(@RequestBody Text text) {
        logger.info("POST request with body: " + text);
        try {
            invertedIndexService.addText(text);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
