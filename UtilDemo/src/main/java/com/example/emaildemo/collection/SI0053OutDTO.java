package com.example.emaildemo.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SI0053OutDTO implements Serializable {

    private List<SI0053OutListDTO> jsonlist = new ArrayList<SI0053OutListDTO>();

    public List<SI0053OutListDTO> getJsonlist() {
        return jsonlist;
    }

    public void setJsonlist(List<SI0053OutListDTO> jsonlist) {
        this.jsonlist = jsonlist;
    }

}
