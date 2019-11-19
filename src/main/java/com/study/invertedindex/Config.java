package com.study.invertedindex;

import com.study.invertedindex.model.Index;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {

    @Bean
    public Map<String, Index> inMemoryIndex() {
        return new HashMap<>();
    }
}
