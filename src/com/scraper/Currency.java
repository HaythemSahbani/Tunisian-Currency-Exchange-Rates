package com.scraper;

/**
 * Created by Haythem on 25/10/2015.
 */
public class Currency {

    private String name;
    private String isoCode;
    private String unit;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getSellValue() {
        return sellValue;
    }

    public void setSellValue(Float sellValue) {
        this.sellValue = sellValue;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public Currency(){}

    public Currency(String name, String isoCode, String unit, Float buyValue, Float sellValue){
        this.name = name;
        this.isoCode = isoCode;
        this.buyValue = buyValue;
        this.sellValue = sellValue;
        this.unit = unit;
    }

    public Currency(String isoCode, Float buyValue, Float sellValue){

        setIsoCode(isoCode);
        setBuyValue(buyValue);
        setSellValue(sellValue);

    }

    public String toString(){

        return  getIsoCode() + " " + getBuyValue() + " " + getSellValue();
    }
}


