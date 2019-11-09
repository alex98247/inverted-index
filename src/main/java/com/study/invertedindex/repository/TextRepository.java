package com.study.invertedindex.repository;

import com.study.invertedindex.model.Text;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TextRepository extends MongoRepository<Text, String> {

    Text findTextByTextId(String id);
}
