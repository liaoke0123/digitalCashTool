package com.bishijie.alert.service.impl;

import com.bishijie.alert.asset.CommonUrl;
import com.bishijie.alert.bean.Timestamp;
import com.bishijie.alert.service.IUnderReadNum;
import com.bishijie.alert.util.HttpUtil;
import com.bishijie.alert.util.JSONUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.HashMap;


@Service
public class UnderReadNumImpl implements IUnderReadNum {

    @Resource
    Timestamp timestamp;


    @Override
    public int getUnderReadNum(long timestamp) {
        String req = HttpUtil.get(MessageFormat.format(CommonUrl.underReadNum, timestamp + ""));
        return this.getNumFromMap(this.jsonToMap(req));
    }

    @Override
    public int getUnderReadNum() {
        String req = HttpUtil.get(MessageFormat.format(CommonUrl.underReadNum, this.timestamp.getTimestamp() + ""));
        return this.getNumFromMap(this.jsonToMap(req));
    }


    /////////////////////////////////////////////////////////// private ///////////////////////////////////////////////////////////

    private HashMap jsonToMap(String req) {
        HashMap<String, Object> reqMap = new HashMap<>();
        try {
            reqMap =  JSONUtil.toObject(req, HashMap.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reqMap;

    }

    private int getNumFromMap(HashMap reqMap){
        int tempNum = 0;
        if(reqMap.get("data")!=null){
            HashMap<String,Integer> dataMap= (HashMap<String, Integer>) reqMap.get("data");
            tempNum = dataMap.get("num");
        }
        return tempNum;
    }

}
