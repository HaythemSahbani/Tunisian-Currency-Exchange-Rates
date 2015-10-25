package com.scraper;

import java.util.ArrayList;

/**
 * Created by Haythem on 25/10/2015.
 */
public class Bank {

    private String code, name, lastUpdate;
    private ArrayList<Currency> currencyList = new ArrayList<Currency>();



    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
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



    public Bank(String code, String name, String lastUpdate){
        setCode(code);
        setName(name);
        setLastUpdate(lastUpdate);
    }

    public Bank(String code, String name){
        setCode(code);
        setName(name);

    }
}