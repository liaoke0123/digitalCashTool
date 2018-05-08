package com.bishijie.alert.util;

import java.util.Date;

public class LogUtil {

    private String method;

    private String msg;

    public static LogUtil builder(){
        return new LogUtil();
    }

    public LogUtil method(String method){
        this.method = method;
        return this;
    }

    public LogUtil msg(String msg){
        this.msg = msg;
        return this;
    }

    public String build(){
        StringBuilder stringBuilder =  new StringBuilder();
        stringBuilder.append(new Date().toString());
        stringBuilder.append("------");
        stringBuilder.append("Method:");
        stringBuilder.append(this.method);
        stringBuilder.append("------");
        stringBuilder.append("Msg:");
        stringBuilder.append(this.msg);
        return stringBuilder.toString();
    }
}
