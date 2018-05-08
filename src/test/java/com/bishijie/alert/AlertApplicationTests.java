package com.bishijie.alert;

import com.bishijie.alert.asset.CommonUrl;
import com.bishijie.alert.bean.UnderReadMessage;
import com.bishijie.alert.service.IUnderReadMessage;
import com.bishijie.alert.service.IUnderReadNum;
import com.bishijie.alert.util.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AlertApplication.class)
@WebAppConfiguration
public class AlertApplicationTests {


    @Resource
    IUnderReadNum iUnderReadNum;


    @Resource
    IUnderReadMessage iUnderReadMessage;

    @Test
    public void contextLoads() {
        System.out.println(iUnderReadNum.getUnderReadNum());
    }

    @Test
    public void testDocument() {
        String req = HttpUtil.get(CommonUrl.coinWorld+"/?time="+new Date().getTime());
        Document doc = Jsoup.parse(req);
        Elements lis = doc.select("li.lh32");
        for(Element li :lis){
            System.out.println(li.select("h2").first());
            System.out.println(li.select("div").first());
        }
    }

    @Test
    public void testMesService(){
        for(UnderReadMessage msg:iUnderReadMessage.getUnderReadMessage(5)){
            System.out.println(msg.toString());
        }
    }

}
