package com.sadad.sw.sequencemaster.model;

/**
 * @author Ramin Pakzad
 */
public class Record {
    private String step;
    private String next;
    private String type;//[send,receive,event]
    private String content;

    public Record() {
    }

    public Record(String step, String next, String type, String content) {
        this.step = step;
        this.next = next;
        this.type = type;
        this.content = content;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
