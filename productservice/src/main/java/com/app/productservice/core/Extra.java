package com.app.productservice.core;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Extra implements Serializable {
    private static final long serialVersionUID = -1245628983620899620L;
    private Map<String, Object> extraName= new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getExtraName() {
        return extraName;
    }

    @JsonAnySetter
    public void setExtraName(String name, Object value) {
        extraName.put(name, value);
    }

    @Override
    public String toString() {
        return "Extra{" +
            "extraName=" + extraName +
            '}';
    }
}
