package com.bishijie.alert.service;

import com.bishijie.alert.bean.UnderReadMessage;

import java.util.List;

public interface IUnderReadMessage {

    List<UnderReadMessage> getUnderReadMessage(int underReadNum);

    String getUnderReadMessagePage();

}
