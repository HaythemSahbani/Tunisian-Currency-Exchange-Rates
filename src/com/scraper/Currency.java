package com.scraper;

/**
 * Created by Haythem on 25/10/2015.
 */
public class Currency {

    private String name;
    private String code;
    private int unit;
    private Float buyValue;
    private Float sellValue;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Float getBuyValue() {
        return buyValue;
    }

    public void setBuyValue(Float buyValue) {
        this.buyValue = buyValue;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public Float getSellValue() {
        return sellValue;
    }

    public void setSellValue(Float sellValue) {
        this.sellValue = sellValue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Currency(){}

    public Currency(String name, String code, int unit, Float buyValue, Float sellValue){
        this.name = name;
        this.code = code;
        this.buyValue = buyValue;
        this.sellValue = sellValue;
        this.unit = unit;
    }

    public Currency(String code, Float buyValue, Float sellValue){

        setCode(code);
        setBuyValue(buyValue);
        setSellValue(sellValue);

    }

    public void setUnit(){
        switch (this.getCode()){
            case "USD": setUnit(1);
                break;



        }
    }

    public String toString(){

        return  getCode() + " " + getBuyValue() + " " + getSellValue();
    }
}


