package com.scraper;

/**
 * Created by Haythem on 25/10/2015.
 */
public class Currency {
    private String isoCode;
    private String unit;

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

    private String buyValue;
    private String sellValue;
    Boolean englishName, frenchName;

    public Currency(){}

    public Currency(String isoCode, String unit, String buyValue, String sellValue){
        this.isoCode = isoCode;
        this.buyValue = buyValue;
        this.sellValue = sellValue;
        this.unit = unit;
    }

    public String mapCurrencyCode(Boolean englishName){
        return this.isoCode;
    }
}
