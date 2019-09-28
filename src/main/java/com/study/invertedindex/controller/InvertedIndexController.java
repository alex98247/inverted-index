package com.study.invertedindex.controller;

import com.study.invertedindex.model.Text;
import com.study.invertedindex.service.InvertedIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/invertedindex")
public class InvertedIndexController {

    @Autowired
    InvertedIndexService invertedIndexService;

    @GetMapping
    public List<Text> getTexts(String word){
        return invertedIndexService.findTexts(word);
    }

}
