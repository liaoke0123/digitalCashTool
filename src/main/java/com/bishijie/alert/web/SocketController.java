package com.bishijie.alert.web;


import com.bishijie.alert.bean.ServerMesList;
import com.bishijie.alert.bean.SocketMessage;
import com.bishijie.alert.service.IUnderReadMessage;
import com.bishijie.alert.service.IUnderReadNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@EnableScheduling
@Controller
public class SocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Resource
    IUnderReadNum iUnderReadNum;

    @Resource
    IUnderReadMessage iUnderReadMessage;

    @Resource
    ServerMesList mesList;


    @Deprecated
	@MessageMapping("/send")
	@SendTo("/topic/send")
	public SocketMessage send(SocketMessage message) throws Exception {
		System.out.println(message.date);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		message.date = df.format(new Date());
		return message;
	}

    @Scheduled(fixedRate = 500)
    @Deprecated //no send socket to customer
    public void sendUnderReadNum() throws Exception {
        int num = iUnderReadNum.getUnderReadNum();
        if (num != 0) {
            mesList.setListMes(iUnderReadMessage.getUnderReadMessage(num));
            sendUnderReadMessage();
            messagingTemplate.convertAndSend("/coinWorld/underReadNum", num);
            System.out.println(new Date()+"####Send Msg####The Num is "+num);
        }
    }

    //core method
    private void sendUnderReadMessage() {
        messagingTemplate.convertAndSend("/coinWorld/underReadMessage", mesList);
    }





}
