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


    public void getCurrency() throws NotFound, ResponseException, IOException, InterruptedException {
         new BankScrap();


    }

    public static void main(String[] args) throws Exception {

        MainTest test = new MainTest();
        test.getCurrency();









    }
}
