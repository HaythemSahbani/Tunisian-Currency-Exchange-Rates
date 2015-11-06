package com.scraper;

import com.jaunt.JNode;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Haythem on 04/11/2015.
 */
public class WriteToResources {

    JSONObject jsonObject = new JSONObject();
    JSONObject bankObj = new JSONObject();
    JSONObject bankDataObj = new JSONObject();
    JSONObject currencyObj = new JSONObject();
    JSONObject currencyDataObj = new JSONObject();

    public WriteToResources() throws InterruptedException, ResponseException, NotFound, IOException {
        BankScrap bankScrap = new BankScrap();
       // jsonObject.put("resourceData", "value");



        bankScrap.getBankList().forEach(bank -> {

            bankDataObj.put("lastUpdate", bank.getUpdateTime());

            bank.getCurrencyList().forEach(currency -> {
                // write to the resourse.json file

                currencyDataObj.put("sellValue",currency.getSellValue());
                currencyDataObj.put("buyValue",currency.getBuyValue());

                currencyObj.put(currency.getCode(), currencyDataObj.clone());
                currencyDataObj.clear();
            });
            bankDataObj.put("currencies", currencyObj.clone());
            currencyObj.clear();
            bankObj.put(bank.getCode(), bankDataObj.clone());
            bankDataObj.clear();
            //jsonObject.put("resourceData",bankObj.clone() );
            jsonObject.putAll((Map) bankObj.clone());

            bankObj.clear();
        });

       // System.out.println(jsonObject.toJSONString());
         UserAgent userAgent = new UserAgent();
         JNode jNode = userAgent.openJSON(jsonObject.toJSONString());
         System.out.println(jNode.toString());

    }





}
