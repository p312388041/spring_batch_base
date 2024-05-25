package com.chong.batch.base.writer;

import java.util.List;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.io.FileSystemResource;

public class CsvItemWriter extends FlatFileItemWriter<List<String>> {

    public CsvItemWriter(String filePath) {
        setResource(new FileSystemResource(filePath));
        setShouldDeleteIfExists(true);
        setLineAggregator(new LineAggregator<List<String>>() {
            @Override
            public String aggregate(List<String> item) {
                StringBuilder stringBuilder = new StringBuilder();
                item.stream().forEach(data -> {
                    stringBuilder.append(data);
                    stringBuilder.append("\n");
                });
                return stringBuilder.toString();
            }
        });
    }
}
