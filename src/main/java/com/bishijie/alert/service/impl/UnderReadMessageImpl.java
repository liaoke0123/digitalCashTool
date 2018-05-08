package com.bishijie.alert.service.impl;

import com.bishijie.alert.asset.CommonUrl;
import com.bishijie.alert.bean.Timestamp;
import com.bishijie.alert.bean.UnderReadMessage;
import com.bishijie.alert.service.IUnderReadMessage;
import com.bishijie.alert.util.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UnderReadMessageImpl implements IUnderReadMessage{

    @Resource
    Timestamp timestamp;

    @Override
    public List<UnderReadMessage> getUnderReadMessage(int underReadNum) {
        this.timestamp.initTimestamp();//reset timestamp
        List<UnderReadMessage> mesList = new ArrayList<>();
        String req = HttpUtil.get(CommonUrl.coinWorld+"/?time="+new Date().getTime());
        Document doc = Jsoup.parse(req);
        Elements lis = doc.select("li.lh32");
        for(int i = 0;i<underReadNum;i++){
            mesList.add(new UnderReadMessage(
                    lis.get(i).select("h2").first().toString().replace("<h2>","").replace("</h2>","").replace("<h2 style=\"color:#ff0000;\">",""),
                    lis.get(i).select("div").first().toString().replace("<div>","").replace("</div>","")));
        }
        return mesList;
    }

    @Override
    public String getUnderReadMessagePage() {
        String req = HttpUtil.get(CommonUrl.coinWorld+"/?time="+new Date().getTime());
        return req;
    }


}
