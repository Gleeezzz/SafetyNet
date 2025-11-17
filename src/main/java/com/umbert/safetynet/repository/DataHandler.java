package com.umbert.safetynet.repository;

import com.jsoniter.JsonIterator;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
public class DataHandler {

    private final Data data;

    public DataHandler() throws IOException {
        String temp = getFromResources("data.json");
        this.data = JsonIterator.deserialize(temp, Data.class);
    }
    private String getFromResources(String s) throws IOException {
        InputStream is = new ClassPathResource(s).getInputStream();
        return IOUtils.toString(is, StandardCharsets.UTF_8);
    }

    public Data getData() { return data; }
}







