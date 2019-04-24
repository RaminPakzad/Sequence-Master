package com.sadad.sw.sequencemaster.model;

import java.util.List;

/**
 * @author Ramin Pakzad
 */
public class Sequence {
    private String name;
    private List<Record> recordList;

    public Sequence() {
    }

    public Sequence(String name, List<Record> recordList) {
        this.name = name;
        this.recordList = recordList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }
}

