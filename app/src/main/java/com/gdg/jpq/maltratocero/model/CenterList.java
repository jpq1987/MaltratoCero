package com.gdg.jpq.maltratocero.model;

import java.util.List;

import com.gdg.jpq.maltratocero.model.CenterParty;

public class CenterList {
    private String state;
    private String message;
    private List<CenterParty> items;

    public CenterList(String state, String message, List<CenterParty> items){
        this.state = state;
        this.message = message;
        this.items = items;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setState(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }

    public void setItems(List<CenterParty> items){
        this.items = items;
    }

    public List<CenterParty> getItems(){
        return items;
    }
}
