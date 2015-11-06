package com.scraper;

import java.util.ArrayList;

/**
 * Created by Haythem on 25/10/2015.
 */
public class Bank {

    private String code;
    private String name;
    private String updateTime;
    private String url;
    private ArrayList<Currency> currencyList = new ArrayList<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdateTime() {
        return updateTime;
    }



    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Currency> getCurrencyList() {
        return currencyList;
    }

    public void setCurrencyList(ArrayList<Currency> currencyList) {
        this.currencyList = currencyList;
    }


public Bank(){}


    public Bank(String code, String name, String url){
        setCode(code);
        setName(name);
        setUrl(url);

    }

    public Bank(String code, String name, String updateTime, String url){
        setCode(code);
        setName(name);
        setUpdateTime(updateTime);
        setUrl(url);
    }
}
