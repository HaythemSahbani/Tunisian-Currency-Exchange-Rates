package com.scraper;

import com.jaunt.*;
import com.jaunt.component.Table;
import com.util.JsonIO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by Haythem on 16/10/2015.
 */
public class BankScrap {

    // get the url of the bank then call the scrap method depending on the bank name
    public BankScrap() {
        /*
        // Read JSON from file through the JSONIO class
        JsonIO jsonIO = new JsonIO();
        // open the json file and read the banklist
        JSONObject jsonObject = jsonIO.read(".\\data\\bankList.json");
        // read the bank list.
        JSONArray viableBankList = (JSONArray) jsonObject.get("viableBankList");

        for (Object item: viableBankList){
            JSONObject map = (JSONObject) item;
            String code = map.get("bankCode").toString();
            String url = map.get("currencyLink").toString();




        }*/

    }

    private final ArrayList<ArrayList<String>> listOfList = new ArrayList<>();
    private final ArrayList<String> list = new ArrayList<>(3);

    UserAgent userAgent = new UserAgent();
    String url = "" ;
    String lastUpdate;

    int currencyListSize;
    private static ArrayList<Element> currencyDataList;

    private void setCurrencyListSize(){
        this.currencyListSize = currencyDataList.size();
    }

    public ArrayList<ArrayList<String>> getListOfList() {
        return listOfList;
    }

    private final void updateList(){
        listOfList.add((ArrayList<String>) list.clone());
        list.clear();
    }
    public  void removeItems(List list, int n ){
        for (int i=0; i<n; i++) list.remove(0);
    }

    public String getLastUpdate(){return this.lastUpdate;}

    public ArrayList<ArrayList<String>> scrapBaraka() throws ResponseException, NotFound{
        url = "http://www.albarakabank.com.tn/CoursConvertisseurDevise.aspx";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = new DateFinder().getATBDate(userAgent.doc.findFirst("<span id=\"ctl00_ContentPlaceHolder1_Label9\">").getText());
        // find the table of currencies
        currencyDataList = (ArrayList<Element>) userAgent.doc.
                findFirst("<div dir=\"ltr\">").
                findFirst("<table border=\"0\" width=\"100%\">").
                findEvery("<tr class=\"FontCoursDevise\">").
                findEvery("<span>").
                toList();

        setCurrencyListSize();
        for(int i = 0; i < currencyListSize/5; i++ ){
            list.add(currencyDataList.get(1).innerText()); //get code
            list.add(currencyDataList.get(3).innerText()); //get sell value
            list.add(currencyDataList.get(4).innerText()); //get buy value
            updateList();
            removeItems(currencyDataList, 5);
        }
        return listOfList;
    }

    public ArrayList<ArrayList<String>> scrapATB() throws ResponseException, NotFound{
        url = "http://www.atb.com.tn/convertisseur";
        userAgent.visit(url);
        //find the lastUpdate of the currency table

        lastUpdate = new DateFinder().getATBDate(userAgent.doc.findFirst("<div class =\"txt\" >").getText());
        // find the table of currencies
        currencyDataList = (ArrayList<Element>) userAgent.doc.
                findFirst("<table id=\"devises\">").
                findEvery("<td class=\"devisesvalue Style1\">").
                toList();

        setCurrencyListSize();
        for(int i = 0; i < currencyListSize/4; i++ ){
            list.add(currencyDataList.get(0).innerText()); //get code
            list.add(currencyDataList.get(2).innerText()); //get sell value
            list.add(currencyDataList.get(3).innerText()); //get buy value
            updateList();
            removeItems(currencyDataList, 4);
        }
        return listOfList;
    }

    public ArrayList<ArrayList<String>> scrapAttijari() throws ResponseException, NotFound{
        url = "http://www.attijaribank.com.tn/Fr/Cours_de_change__59_205";
        userAgent.visit(url);
        //find the lastUpdate of the currency table
        lastUpdate = new DateFinder().getATBDate(userAgent.doc.findFirst("<div class=\"center_page\">").findFirst("<b>").innerText());
        // find the currencies data

        currencyDataList = (ArrayList<Element>) userAgent.doc.findFirst("<div class=\"center_page\">").
                findFirst("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">").
                findEvery("<p>").toList();

        setCurrencyListSize();
        for(int i = 0; i < currencyListSize/6; i++ ){
            list.add(currencyDataList.get(1).innerText()); //get code
            list.add(currencyDataList.get(3).innerText()); //get sell value
            list.add(currencyDataList.get(4).innerText()); //get buy value
            updateList();
            removeItems(currencyDataList, 6);
        }
        return listOfList;
    }


    public ArrayList<ArrayList<String>> scrapBH() throws ResponseException, NotFound{
        userAgent.visit("http://www.bh.com.tn");
        url = "http://www.bh.com.tn/devise.asp";
        userAgent.visit(url);

        //find the lastUpdate of the currency table


        lastUpdate = new DateFinder().getATBDate(userAgent.doc.findEvery("<p>").innerText());
        // find the currencies data
        currencyDataList = (ArrayList<Element>) userAgent.doc.
                findEvery("<table class=\"CorpsDeTexte\">").
                getElement(2). // get the third table == containing the currency data.
                findEvery("<tr>").
                findEvery("<td>").
                toList();
        removeItems(currencyDataList, 4); // remove the table headers
        setCurrencyListSize();
       for(int i = 0; i < currencyListSize/4; i++ ){
            list.add(currencyDataList.get(0).innerText().replaceAll("&nbsp;", "").replaceAll("\\s+", " ").substring(0,3)); //get code
            list.add(currencyDataList.get(3).innerText().replaceAll("&nbsp;", "")); //get sell value
            list.add(currencyDataList.get(2).innerText().replaceAll("&nbsp;", "")); //get buy value
            updateList();
            removeItems(currencyDataList, 4);
        }
        return listOfList;
    }

    public ArrayList<ArrayList<String>> scrapBT() throws ResponseException, NotFound{
        url = "http://www.bt.com.tn/change";
        userAgent.visit(url);
        //find the lastUpdate of the currency table
        // find the currencies data
        currencyDataList = (ArrayList<Element>) userAgent.doc.findFirst("<table class=\"table\" id=\"devise-table\">").findEvery("<td>").toList();

        setCurrencyListSize();
        for(int i = 0; i < currencyListSize/5; i++ ){
            list.add(currencyDataList.get(1).innerText()); //get code
            list.add(currencyDataList.get(3).innerText()); //get sell value
            list.add(currencyDataList.get(4).innerText()); //get buy value
            updateList();
            removeItems(currencyDataList, 5);
        }
        return listOfList;
    }

    public ArrayList<ArrayList<String>> scrapBTE() throws ResponseException, NotFound{
        url = "http://www.bte.com.tn/?idart=8";
        userAgent.visit(url);
        //find the lastUpdate of the currency table
        lastUpdate = new DateFinder().getATBDate(userAgent.doc.
                findFirst("<table align=\"center\" cellpadding=\"2\" cellspacing=\"2\">").
                findFirst("<tr>").
                innerText());

        // find the currencies data
        currencyDataList = (ArrayList<Element>) userAgent.doc.
                findFirst("<table align=\"center\" cellpadding=\"2\" cellspacing=\"2\">").
                findEvery("<td>").
                toList();

        // currencyDataList.forEach(e -> System.out.println(e.innerText()));
        setCurrencyListSize();
        for(int i = 0; i < currencyListSize/5; i++ ){
            list.add(currencyDataList.get(1).innerText().replaceAll("&nbsp;", "")); //get code
            list.add(currencyDataList.get(3).innerText().replaceAll("&nbsp;", "")); //get sell value
            list.add(currencyDataList.get(4).innerText().replaceAll("&nbsp;", "")); //get buy value
            updateList();
            removeItems(currencyDataList, 5);
        }
        return listOfList;
    }

    public ArrayList<ArrayList<String>> scrapBIAT() throws ResponseException, NotFound {
        url = "http://www.biat.com.tn/biat/cours_devise.jsp";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = new DateFinder().getDate(userAgent.doc.findFirst("<p align=\"center\" class=\"couseDaysDevise\">").innerText());

        // find the currencies data
        currencyDataList = (ArrayList<Element>) userAgent.doc.
                findEvery("<tr class=\"fontdevise1\">").
                findEvery("<td>").
                toList();

        //currencyDataList.forEach(e -> System.out.println(e.innerText()));
        setCurrencyListSize();
        for(int i = 0; i < currencyListSize/5; i++ ){
            list.add(currencyDataList.get(1).innerText()); //get code
            list.add(currencyDataList.get(3).innerText()); //get sell value
            list.add(currencyDataList.get(4).innerText()); //get buy value
            updateList();
            removeItems(currencyDataList, 5);
        }
        return listOfList;

    }

    public ArrayList<ArrayList<String>> scrapBNA()throws ResponseException, NotFound {
        url = "http://www.bna.com.tn/devise.asp";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = new DateFinder().getDate(userAgent.doc.findFirst("<td class=\"textevert\" colspan=\"7\">").innerText());

        // find the currencies data
        currencyDataList = (ArrayList<Element>) userAgent.doc.
                findEvery("<table width=\"100%\" border=\"0\" cellspacing=\"0\" bordercolor=\"#CCCCCC\" class=\"btn\" id=\"devise\">").
                findEvery("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">").
                toList();

        setCurrencyListSize();
        for(int i = 0; i < currencyListSize/4; i++ ){
            list.add(currencyDataList.get(0).innerText().replaceAll("\\s+", " ")); //get name
            list.add(currencyDataList.get(2).innerText().replaceAll("\\s+", " ")); //get sell value
            list.add(currencyDataList.get(3).innerText().replaceAll("\\s+", " ")); //get buy value
            updateList();
            removeItems(currencyDataList, 4);
        }
        return listOfList;

    }

    public ArrayList<ArrayList<String>> scrapBTK() throws ResponseException, NotFound {
        url = "http://www.btknet.com/site/fr/convertisseur.php?id_article=33";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = new DateFinder().getATBDate(userAgent.doc.findFirst("<td class=\"titre_publication\">").innerText());

        // find the currencies data

        currencyDataList = (ArrayList<Element>) userAgent.doc.
                findEvery("<td class= \"ligne_devise_interne\">").
                toList();
        currencyDataList.addAll(userAgent.doc.
                findEvery("<td class=\"ligne_devise_interne2\">").
                toList());

        setCurrencyListSize();
        for(int i = 0; i < currencyListSize/4; i++ ){
            list.add(currencyDataList.get(0).innerText().replaceAll("\\s+", "").substring(0, 3)); //get code
            list.add(currencyDataList.get(2).innerText()); //get sell value
            list.add(currencyDataList.get(3).innerText()); //get buy value
            updateList();
            removeItems(currencyDataList, 4);
        }
        return listOfList;

    }

    //no data found
    public ArrayList<ArrayList<String>> scrapBTL() throws ResponseException, NotFound {
        url = "http://www.btl.com.tn/portal/page?_dad=portal&_schema=PORTAL&_pageid=37%2C391598";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = new DateFinder().getATBDate(userAgent.doc.findFirst("<td class=\"titre_publication\">").innerText());

        // find the currencies data
        currencyDataList = (ArrayList<Element>) userAgent.doc.
                findEvery("<td class=\"ligne_devise_interne\">").
                toList();
        currencyDataList.addAll(userAgent.doc.
                findEvery("<td class=\"ligne_devise_interne2\">").
                toList());

        //currencyDataList.forEach(e -> System.out.println(e.innerText()));
        setCurrencyListSize();
        for(int i = 0; i < currencyListSize/4; i++ ){
            list.add(currencyDataList.get(0).innerText().replaceAll("\\s+", "").substring(0, 3)); //get code
            list.add(currencyDataList.get(2).innerText()); //get sell value
            list.add(currencyDataList.get(3).innerText()); //get buy value
            updateList();
            removeItems(currencyDataList, 4);
        }
        return listOfList;

    }

    public ArrayList<ArrayList<String>> scrapZitouna() throws ResponseException, NotFound {
        url = "http://www.banquezitouna.com/Fr/convertisseur-de-devise_64_103";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = new DateFinder().getATBDate(userAgent.doc.findFirst("<div class=\"caption_tabchange\">").innerText());

        // find the currencies data
        currencyDataList = (ArrayList<Element>) userAgent.doc.
                findFirst("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" class=\"tab_change\">").
                findEvery("<td>").
                toList();

        //currencyDataList.forEach(e -> System.out.println(e.innerText()));
        setCurrencyListSize();
        for(int i = 0; i < currencyListSize/4; i++ ){
            list.add(currencyDataList.get(1).innerText()); //get code
            list.add(currencyDataList.get(2).innerText()); //get sell value
            list.add(currencyDataList.get(3).innerText()); //get buy value
            updateList();
            removeItems(currencyDataList, 4);
        }
        return listOfList;

    }

    public ArrayList<ArrayList<String>> scrapPoste() throws ResponseException, NotFound {
        url = "http://www.poste.tn/change.php";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = new DateFinder().getATBDate(userAgent.doc.findFirst("<td height=\"30\" colspan=\"5\" class=\"tableau\">").innerText());

        // find the currencies data
        currencyDataList = (ArrayList<Element>) userAgent.doc.
                findEvery("<td class=\"cel_contenu\">").
                toList();

        //currencyDataList.forEach(e -> System.out.println(e.innerText()));
        setCurrencyListSize();
        for(int i = 0; i < currencyListSize/5; i++ ){
            list.add(currencyDataList.get(1).innerText()); //get code
            list.add(currencyDataList.get(3).innerText()); //get sell value
            list.add(currencyDataList.get(4).innerText()); //get buy value
            updateList();
            removeItems(currencyDataList, 5);
        }
        return listOfList;

    }

    public ArrayList<ArrayList<String>> scrapQNB() throws ResponseException, NotFound {
        url = "http://www.qnb.com.tn/en/financial-tools.html?view=financialtools";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = new DateFinder().getATBDate(userAgent.doc.findFirst("<table class=\"tabData\" id=\"currencyRates\">").findFirst("<th>").innerText());

        // find the currencies data
        currencyDataList = (ArrayList<Element>) userAgent.doc.
                findEvery("<td class=\"bottomline\">").
                toList();
        setCurrencyListSize();
        for(int i = 0; i < currencyListSize/5; i++ ){
            list.add(currencyDataList.get(1).innerText().replaceAll("&nbsp;", "").replaceAll("\\s+", "")); //get code
            list.add(currencyDataList.get(3).innerText().replaceAll("&nbsp;", "")); //get sell value
            list.add(currencyDataList.get(4).innerText().replaceAll("&nbsp;", "")); //get buy value
            updateList();
            removeItems(currencyDataList, 5);
        }
        return listOfList;

    }

    public ArrayList<ArrayList<String>> scrapSTB() throws ResponseException, NotFound {
        url = "http://www.stb.com.tn/fr/site/bourse-change/cours-de-change";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = new DateFinder().getATBDate(userAgent.doc.findFirst("<td class =\"date-change\">").innerText());

        // find the currencies data
        currencyDataList = (ArrayList<Element>) userAgent.doc.
                findFirst("<table class=\"cours-de-change\">").
                findEvery("<td>").
                toList();
        setCurrencyListSize();
        for(int i = 0; i < currencyListSize/4; i++ ){
            list.add(currencyDataList.get(0).innerText()); //get code
            list.add(currencyDataList.get(1).innerText()); //get sell value
            list.add(currencyDataList.get(2).innerText()); //get buy value
            updateList();
            removeItems(currencyDataList, 4);
        }
        return listOfList;

    }

    public ArrayList<ArrayList<String>> scrapStusid() throws ResponseException, NotFound {
        url = "http://www.stusidbank.com.tn/site/publish/content/article.asp?id=78";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = new DateFinder().getATBDate(userAgent.doc.findFirst("<span class =\"texte6\">").innerText());

        // find the currencies data
        currencyDataList = (ArrayList<Element>) userAgent.doc.
                findEvery("<td class=\"CelTab2-2\">").
                toList();

        setCurrencyListSize();
        for(int i = 0; i < currencyListSize/5; i++ ){
            list.add(currencyDataList.get(1).innerText()); //get code
            list.add(currencyDataList.get(4).innerText()); //get sell value
            list.add(currencyDataList.get(3).innerText()); //get buy value
            updateList();
            removeItems(currencyDataList, 5);
        }
        return listOfList;

    }


}
