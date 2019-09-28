package com.study.invertedindex.repository;

import com.study.invertedindex.model.Index;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IndexRepository extends MongoRepository<Index, String> {
    Index findByWord(String word);
}
