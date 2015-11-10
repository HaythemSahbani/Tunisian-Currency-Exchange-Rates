package com.util;

import com.jaunt.JNode;
import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.jaunt.UserAgent;
import com.scraper.Bank;
import com.scraper.BankScrap;
import com.util.JsonIO;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
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


    public JSONObject setJsonResource(ArrayList<Bank> bankList) {
        bankList.forEach(bank -> {

            bankDataObj.put("lastUpdate", bank.getUpdateTime());

            bank.getCurrencyList().forEach(currency -> {
                // write to the resourse.json file
                currencyDataObj.put("sellValue", currency.getSellValue());
                currencyDataObj.put("buyValue", currency.getBuyValue());
                currencyObj.put(currency.getCode(), currencyDataObj.clone());
                currencyDataObj.clear();
            });
            bankDataObj.put("currencies", currencyObj.clone());
            currencyObj.clear();
            bankObj.put(bank.getCode(), bankDataObj.clone());
            bankDataObj.clear();
            jsonObject.putAll((Map) bankObj.clone());
            bankObj.clear();
        });
        return jsonObject;

    }

    public WriteToResources() {
    }

}
