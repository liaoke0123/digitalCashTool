package com.bishijie.alert.bean;


public class UnderReadMessage {

    private String title;

    private String body;

    public UnderReadMessage(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "UnderReadMessage{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}'+"\n";
    }
}
