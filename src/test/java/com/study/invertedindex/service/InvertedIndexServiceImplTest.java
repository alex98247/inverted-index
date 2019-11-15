package com.study.invertedindex.service;

import com.study.invertedindex.model.Text;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InvertedIndexServiceImplTest {

    @Mock
    private InvertedIndexService invertedIndexService;

    @Test
    public void findTexts() {
        List<String> words = new ArrayList<>(Arrays.asList("hello", "1"));
        when(invertedIndexService.findTexts(words)).thenReturn(Collections.EMPTY_LIST);
        List<Text> result = invertedIndexService.findTexts(words);

        assertThat(result, IsEmptyCollection.empty());
    }

    @Test
    public void addText() throws Exception {
        String str = "lalalala";
        Text text= new Text();
        text.setText(str);
        invertedIndexService.addText(text);
    }
}
