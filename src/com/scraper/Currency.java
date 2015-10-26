package com.scraper;

/**
 * Created by Haythem on 25/10/2015.
 */
public class Currency {

    private String name;
    private String isoCode;
    private String unit;
    private String buyValue;
    private String sellValue;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getBuyValue() {
        return buyValue;
    }

    public void setBuyValue(String buyValue) {
        this.buyValue = buyValue;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSellValue() {
        return sellValue;
    }

    public void setSellValue(String sellValue) {
        this.sellValue = sellValue;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }


    Boolean englishName, frenchName;

    public Currency(){}

    public Currency(String name, String isoCode, String unit, String buyValue, String sellValue){
        this.name = name;
        this.isoCode = isoCode;
        this.buyValue = buyValue;
        this.sellValue = sellValue;
        this.unit = unit;
    }

    public String mapCurrencyCode(Boolean englishName){
        return this.isoCode;
    }

    public String toString(){

        return getName() + " " + getIsoCode() + " " + getUnit() + " " + getBuyValue() + " " + getSellValue();
    }
}


