package com.bishijie.alert.bean;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServerMesList {

    List<UnderReadMessage> listMes;

    public List<UnderReadMessage> getListMes() {
        return listMes;
    }

    public void setListMes(List<UnderReadMessage> listMes) {
        this.listMes = listMes;
    }

}
