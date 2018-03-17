package com.xml.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class Extractor {

    public static final Object extract(final String urlFile) throws FileNotFoundException, IOException, IllegalArgumentException, IllegalAccessException {
        final File file = new File(urlFile);
        return extract(file);
    }

    public static final Object extract(final File file) throws FileNotFoundException, IOException, IllegalArgumentException, IllegalAccessException {

        final XmlMapper xmlMapper = new XmlMapper();
        final String xml = inputStreamToString(new FileInputStream(file));
        final Object value = xmlMapper.readValue(xml, Object.class);
        
        parseObject(value);

        return value;
    }

    public static void parseObject(final Object value) throws IllegalArgumentException, IllegalAccessException {

        if (value instanceof LinkedHashMap) {
            LinkedHashMap lhm = (LinkedHashMap) value;
            parseList(lhm);

        } else {
            System.out.println(value);
        }
    }

    @SuppressWarnings("rawtypes")
    public static void parseList(LinkedHashMap<String, Serializable> lhm) {

        lhm.entrySet().stream().forEach(node -> {

            Object teste = node.getValue();
            if (teste instanceof LinkedHashMap) {
                LinkedHashMap sub = (LinkedHashMap) teste;
                parseList(sub);
            } else {

                String regexInt = "\\d+";
                String decimalPattern = "([0-9]*)\\.([0-9]*)";

                if (node.getValue().toString().matches(regexInt)) {
                    lhm.put(node.getKey().toString(), new Integer(node.getValue().toString()));
                } else if (node.getValue().toString().matches(decimalPattern)) {
                    lhm.put(node.getKey().toString(), new Double(node.getValue().toString()));
                } else if (new DateValidator().validate(node.getValue().toString())) {
                    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate localDate = LocalDate.parse(node.getValue().toString(), formatador);
                    lhm.put(node.getKey().toString(), localDate);
                } else if ("true".equalsIgnoreCase(node.getValue().toString())) {
                    lhm.put(node.getKey().toString(), true);
                } else if ("false".equalsIgnoreCase(node.getValue().toString())) {
                    lhm.put(node.getKey().toString(), false);
                } else {
                    lhm.put(node.getKey().toString(), node.getValue().toString());
                }

            }
        });
    }

    public static final String inputStreamToString(final InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}
