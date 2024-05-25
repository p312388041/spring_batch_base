package com.chong.batch.base.reader;

import java.io.StringReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.core.io.FileSystemResource;
import com.opencsv.CSVReader;

public class CsvItemReader extends FlatFileItemReader<String[]> {
    private LineMapper<String[]> CsvLineMapper() {
        return (line, lineNumber) -> {
            CSVReader csvReader = new CSVReader(new StringReader(line));
            String[] fileds = csvReader.readAll().get(0);
            csvReader.close();
            return fileds;
        };
    }

    public CsvItemReader(String filePath) {
        setResource(new FileSystemResource(filePath));
        setLineMapper(CsvLineMapper());
    }
}