package com.bishijie.alert.bean;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Timestamp {

    long timestamp = initTimestamp();

    public long getTimestamp() {
        return timestamp;
    }

    public long initTimestamp(){
        this.timestamp = getSecondTimestamp(new Date());
        return getSecondTimestamp(new Date());
    }

    private long getSecondTimestamp(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime());
        int length = timestamp.length();
        if (length > 3) {
            return Integer.valueOf(timestamp.substring(0,length-3));
        } else {
            return 0;
        }
    }

}
