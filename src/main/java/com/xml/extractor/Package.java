package com.xml.extractor;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Package implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    @JsonProperty("package")
    private Object pack;
    
    public Package() {
    }

    public Package(String id, Object pack) {
        this.id = id;
        this.pack = pack;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Object getPack() {
        return pack;
    }
    public void setPack(Object pack) {
        this.pack = pack;
    }
    
}