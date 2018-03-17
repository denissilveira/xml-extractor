package com.xml.extractor;

public class Teste {

    public static void main(String[] args) {

        try {
            Object value =Extractor.extract("C:\\Users\\denis\\Desktop\\teste.xml");
            
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
