package com.test;

import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.scraper.Bank;
import com.scraper.Currency;
import com.scraper.BankScrap;
import com.util.JsonIO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Haythem on 20/10/2015.
 */
public class MainTest {
/*
    public void readBankList() throws IOException, ParseException {
        // Read JSON from file through the JSONIO class
        JsonIO jsonIO = new JsonIO();
        // open the json file and read the banklist
        JSONObject jsonObject = jsonIO.read(".\\data\\bankList.json");
        // read the bank list.
        JSONArray viableBankList = (JSONArray) jsonObject.get("viableBankList");

        for (Object item: viableBankList){
            JSONObject map = (JSONObject) item;
            String code = map.get("bankCode").toString();

            Bank bank1 = new Bank(map.get("name").toString(), map.get("bankCode").toString());

            // map.get("name");
            System.out.println(map.get("name"));
        }
    }*/

    public void getCurrency() throws NotFound, ResponseException, IOException {
        BankScrap bankScrap = new BankScrap();


    }

    public static void main(String[] args) throws Exception {

        MainTest test = new MainTest();
        test.getCurrency();









    }
}
