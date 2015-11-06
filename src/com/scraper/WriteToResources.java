package com.scraper;

import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import org.json.simple.JSONObject;

import java.io.IOException;

/**
 * Created by Haythem on 04/11/2015.
 */
public class WriteToResources {

    JSONObject jsonObject = new JSONObject();

    public WriteToResources() throws InterruptedException, ResponseException, NotFound, IOException {
        BankScrap bankScrap = new BankScrap();
        jsonObject.put("resourceData", "value");
        JSONObject currencyObj = new JSONObject();
        JSONObject currdataObj = new JSONObject();

        bankScrap.getBankList().forEach(bank -> {
            jsonObject.put("bankCode", bank.getCode());
            jsonObject.put("lastUpdate", bank.getUpdateTime());
            jsonObject.put("currencies", currencyObj);
            bank.getCurrencyList().forEach(currency -> {
                // write to the resourse.json file
                currencyObj.put(currency.getIsoCode(),currdataObj);
                currdataObj.put("sellValue",currency.getSellValue());
                currdataObj.put("buyValue",currency.getBuyValue());
                currdataObj.put("unit",currency.getUnit());
            });
        });

        System.out.println(jsonObject.toJSONString());
    }





}
