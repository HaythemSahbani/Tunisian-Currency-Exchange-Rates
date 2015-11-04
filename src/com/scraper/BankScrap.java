package com.scraper;

import com.jaunt.*;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Haythem on 16/10/2015.
 */
public class BankScrap {

    ArrayList<Bank> bankList = new ArrayList<>();
    private final ArrayList<ArrayList<String>> listOfList = new ArrayList<>();
    private final ArrayList<String> list = new ArrayList<>(3);

    UserAgent userAgent = new UserAgent();
    String url = "" ;
    String lastUpdate;
    Regex regex = new Regex();
    int columnNumber;
    int currencyListSize;
    private static ArrayList<String> currencyDataList = new ArrayList<>();


    // get the url of the bank then call the scrap method depending on the bank name
    public BankScrap() throws ResponseException, NotFound, IOException {
        userAgent.openJSON(new File(".\\data\\bankList.json"));
        JNode bankJson = userAgent.json.findFirst("viableBankList");
        userAgent.openJSON(new File(".\\data\\currencies.json"));
        JNode currencyJson = userAgent.json.findFirst("currencies");

        bankJson.forEach(node -> {
            try {
                bankList.add(new Bank(node.getName(), node.get("currencyLink").toString().replaceAll("\\\\", "")));
            } catch (NotFound notFound) {
                notFound.printStackTrace();
            }
    });

        for (Bank bank : bankList) {
            scrap(bank);
        }



    }




    private void setCurrencyListSize(){
        this.currencyListSize = currencyDataList.size();
    }

    public String getLastUpdate(){return this.lastUpdate;}

    public ArrayList<ArrayList<String>> getListOfList() {
        return listOfList;
    }

    private final void updateList(){
        listOfList.add((ArrayList<String>) list.clone());
        list.clear();
    }

    /**
     *
     * @param list
     * @param n
     */
    public  void removeItems(List list, int n ){
        for (int i=0; i<n; i++) list.remove(0);
    }

    /**
     * Gets the column number of the currency table.
     * @param list
     * @return
     */
    private int setColumnNumber(ArrayList<String> list) {
        for (int j = list.size() - 1; j >= 0; j--) {
            if (regex.beginWithChar(list.get(j))) {
                if (regex.beginWithChar(list.get(j - 1))) {
                    columnNumber = list.size() - j + 1;
                    break;
                } else {
                    columnNumber = list.size() - j;
                    break;
                }
            }
        }
        return columnNumber;
    }

    public void scrap(Bank bank) throws NotFound, ResponseException {

        switch (bank.getCode()) {
            case "Baraka":
                userAgent.visit(bank.getUrl());
                scrapBank(bank,
                        regex.getDate(userAgent.doc.
                                findFirst("<span id=\"ctl00_ContentPlaceHolder1_Label9\">").
                                getText()),
                        userAgent.doc.
                                findFirst("<div dir=\"ltr\">").
                                findFirst("<table border=\"0\" width=\"100%\">").
                                findEvery("<tr class=\"FontCoursDevise\">").
                                findEvery("<span>").
                                toList(),
                        1,
                        3,
                        4);
                break;
            case "ATB":
                userAgent.visit(bank.getUrl());
                scrapBank(bank,
                        regex.getDate(userAgent.doc.
                                findFirst("<div class=\"txt\">").
                                getText()),
                        userAgent.doc.
                                findFirst("<table id=\"devises\">").
                                findEvery("<td class=\"devisesvalue Style1\">").
                                toList(),
                        0,
                        2,
                        3);
                break;
            case "Attijari":
                userAgent.visit(bank.getUrl());
                scrapBank(bank,
                        regex.getDate(userAgent.doc.
                                findFirst("<div class=\"center_page\">").
                                findFirst("<b>").
                                innerText()),
                        userAgent.doc.
                                findFirst("<div class=\"center_page\">").
                                findFirst("<table>").
                                findEvery("<p>").
                                toList(),
                        1,
                        3,
                        4);
                break;
            case "BH":
                userAgent.visit("http://www.bh.com.tn"); // to avoid a security issue with the bank server
                userAgent.visit(bank.getUrl());
                scrapBank(bank,
                        regex.getDate(userAgent.doc.
                                findEvery("<p>").
                                innerText()),
                        userAgent.doc.
                                findEvery("<table class=\"CorpsDeTexte\">").
                                getElement(2). // get the third table == containing the currency data.
                                findEvery("<tr>").
                                findEvery("<td>").
                                toList(),
                        0,
                        2,
                        3);
                break;
            case "BT":
                userAgent.visit(bank.getUrl());
                scrapBank(bank,
                        "no data",
                        userAgent.doc.
                                findFirst("<table class=\"table\" id=\"devise-table\">").
                                findEvery("<td>").
                                toList(),
                        1,
                        3,
                        4);
                break;
            case "BTE":
                userAgent.visit(bank.getUrl());
                scrapBank(bank,
                        regex.getDate(userAgent.doc.
                                findFirst("<table>").
                                findFirst("<tr>").
                                innerText()),
                        userAgent.doc.
                                findFirst("<table>").
                                findEvery("<td>").
                                toList(),
                        1,
                        3,
                        4);
                break;
            case "BIAT":
                userAgent.visit(bank.getUrl());
                scrapBank(bank,
                        regex.getDate(userAgent.doc.
                                findFirst("<p class=\"couseDaysDevise\">").
                                innerText()),
                        userAgent.doc.
                                findEvery("<tr class=\"fontdevise1\">").
                                findEvery("<td>").
                                toList(),
                        1,
                        3,
                        4);
                break;
            case "BNA":
                userAgent.visit(bank.getUrl());
                scrapBank(bank,
                        regex.getDate(
                                userAgent.doc.
                                        findFirst("<td class=\"textevert\">").
                                        innerText()),
                        userAgent.doc.
                                findEvery("<table class=\"btn\" id=\"devise\">").
                                findEvery("<table>").
                                toList(),
                        0,
                        2,
                        3);
                break;
            case "BTK":
                userAgent.visit(bank.getUrl());
                userAgent.doc.
                        findEvery("<td class=\"ligne_devise_interne2\">").
                        toList().forEach(e -> currencyDataList.add(e.innerText().replaceAll("\\s+", "")));
                scrapBank(bank,
                        regex.getDate(userAgent.doc.
                                findFirst("<td class=\"titre_publication\">").
                                innerText()),
                        userAgent.doc.
                                findEvery("<td class= \"ligne_devise_interne\">").
                                toList(),
                        0,
                        2,
                        3);
                break;
            case "Zitouna":
                userAgent.visit(bank.getUrl());
                scrapBank(bank,
                        regex.getDate(userAgent.doc.
                                findFirst("<div class=\"caption_tabchange\">").
                                innerText()),
                        userAgent.doc.
                                findFirst("<table class=\"tab_change\">").
                                findEvery("<td>").
                                toList(),
                        1,
                        2,
                        3);
                break;
            case "Poste":
                userAgent.visit(bank.getUrl());
                scrapBank(bank,
                        regex.getDate(userAgent.doc.
                                findFirst("<td class=\"tableau\">").
                                innerText()),
                        userAgent.doc.
                                findEvery("<td class=\"cel_contenu\">").
                                toList(),
                        1,
                        3,
                        4);
                break;
            case "QNB":
                userAgent.visit(bank.getUrl());
                scrapBank(bank,
                        regex.getDate(userAgent.doc.
                                findFirst("<table class=\"tabData\" id=\"currencyRates\">").
                                findFirst("<th>").
                                innerText()),
                        userAgent.doc.
                                findEvery("<td class=\"bottomline\">").
                                toList(),
                        1,
                        3,
                        4);
                break;
            case "STB":
                userAgent.visit(bank.getUrl());
                scrapBank(bank,
                        regex.getDate(userAgent.doc.
                                findFirst("<td class =\"date-change\">").
                                innerText()),
                        userAgent.doc.
                                findFirst("<table class=\"cours-de-change\">").
                                findEvery("<td>").
                                toList(),
                        0,
                        1,
                        2);
                break;
            case "Stusid":
                userAgent.visit(bank.getUrl());
                scrapBank(bank,
                        regex.getDate(userAgent.doc.
                                findFirst("<span class =\"texte6\">").
                                innerText()),
                        userAgent.doc.
                                findEvery("<td class=\"CelTab2-2\">").
                                toList(),
                        1,
                        3,
                        4);
                break;
            default:
                System.out.println(" no bank data about " + bank.getCode());

        }
    }



    public ArrayList<ArrayList<String>> scrapBank(Bank bank,
                                                  String lastUpdate,
                                                  List<Element> htmlElementsList,
                                                  int codeIndex,
                                                  int buyValueIndex,
                                                  int sellValueIndex)
            throws ResponseException, NotFound{


        this.lastUpdate = lastUpdate;
        //if(!bank.getLastUpdate().equals(lastUpdate) | lastUpdate.equals(null)){
        htmlElementsList.forEach(element -> currencyDataList.add(element.innerText()));
        System.out.println("Scraping "+ bank.getCode());
        setCurrencyListSize();
        setColumnNumber(currencyDataList);
        for(int i = 0; i < currencyListSize/columnNumber; i++ ){
            list.add(currencyDataList.get(codeIndex).replaceAll("&nbsp;", "").replaceAll("\\s+", "")); //get code
            list.add(currencyDataList.get(buyValueIndex).replaceAll("&nbsp;", "").replaceAll("\\s+", "")); //get buy value
            list.add(currencyDataList.get(sellValueIndex).replaceAll("&nbsp;", "").replaceAll("\\s+", "")); //get sell value
            updateList();
            removeItems(currencyDataList, columnNumber);
        //}
    }

        for (ArrayList<String> lis : listOfList ){
            //update the currency list
            bank.getCurrencyList().add(
                    new Currency(
                            lis.get(0),
                            lis.get(1),
                            lis.get(2)
                    ));
        }

        System.out.println("last update:"+ bank.getLastUpdate());
        System.out.println("-------------------------------------");

        for (Currency cur : bank.getCurrencyList()){
            System.out.println(cur.toString());

        }
        listOfList.clear();


        return listOfList;
    }




    public ArrayList<ArrayList<String>> scrapBaraka() throws ResponseException, NotFound{
        url = "http://www.albarakabank.com.tn/CoursConvertisseurDevise.aspx";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = regex.getDate(userAgent.doc.findFirst("<span id=\"ctl00_ContentPlaceHolder1_Label9\">").getText());
        // find the table of currencies
         userAgent.doc.
                findFirst("<div dir=\"ltr\">").
                findFirst("<table border=\"0\" width=\"100%\">").
                findEvery("<tr class=\"FontCoursDevise\">").
                findEvery("<span>").
                toList().forEach(e -> currencyDataList.add(e.innerText()));

        setCurrencyListSize();
        setColumnNumber(currencyDataList);
        for(int i = 0; i < currencyListSize/columnNumber; i++ ){
            list.add(currencyDataList.get(1)); //get code
            list.add(currencyDataList.get(3)); //get buy value
            list.add(currencyDataList.get(4)); //get sell value
            updateList();
            removeItems(currencyDataList, columnNumber);
        }
        return listOfList;
    }

    public ArrayList<ArrayList<String>> scrapATB() throws ResponseException, NotFound{
        url = "http://www.atb.com.tn/convertisseur";
        userAgent.visit(url);
        //find the lastUpdate of the currency table

        lastUpdate = regex.getDate(userAgent.doc.findFirst("<div class =\"txt\" >").getText());
        // find the table of currencies
        userAgent.doc.
                findFirst("<table id=\"devises\">").
                findEvery("<td class=\"devisesvalue Style1\">").
                toList().forEach(e -> currencyDataList.add(e.innerText()));

        setCurrencyListSize();
        setColumnNumber(currencyDataList);
        for(int i = 0; i < currencyListSize/columnNumber; i++ ){
            list.add(currencyDataList.get(0)); //get code
            list.add(currencyDataList.get(2)); //get sell value
            list.add(currencyDataList.get(3)); //get buy value
            updateList();
            removeItems(currencyDataList, columnNumber);
        }
        return listOfList;
    }

    public ArrayList<ArrayList<String>> scrapAttijari() throws ResponseException, NotFound{
        url = "http://www.attijaribank.com.tn/Fr/Cours_de_change__59_205";
        userAgent.visit(url);
        //find the lastUpdate of the currency table
        lastUpdate = regex.getDate(userAgent.doc.findFirst("<div class=\"center_page\">").findFirst("<b>").innerText());
        // find the currencies data

        userAgent.doc.findFirst("<div class=\"center_page\">").
                findFirst("<table>").
                findEvery("<p>").
                toList().
                forEach(e -> currencyDataList.add(e.innerText()));

        // currencyDataList.forEach(e -> System.out.println(e.innerText()));

        setCurrencyListSize();
        setColumnNumber(currencyDataList);
        for(int i = 0; i < currencyListSize/columnNumber; i++ ){
            list.add(currencyDataList.get(1)); //get code
            list.add(currencyDataList.get(3)); //get sell value
            list.add(currencyDataList.get(4)); //get buy value
            updateList();
            removeItems(currencyDataList, columnNumber);
        }
        return listOfList;
    }

    public ArrayList<ArrayList<String>> scrapBH() throws ResponseException, NotFound{
        userAgent.visit("http://www.bh.com.tn");
        url = "http://www.bh.com.tn/devise.asp";
        userAgent.visit(url);

        //find the lastUpdate of the currency table


        lastUpdate = regex.getDate(userAgent.doc.findEvery("<p>").innerText());
        // find the currencies data
        userAgent.doc.
                findEvery("<table class=\"CorpsDeTexte\">").
                getElement(2). // get the third table == containing the currency data.
                findEvery("<tr>").
                findEvery("<td>").
                toList().forEach(e -> currencyDataList.add(e.innerText().replaceAll("&nbsp;", "")));
        setCurrencyListSize();
        setColumnNumber(currencyDataList);
        removeItems(currencyDataList, columnNumber); // remove the table headers
        for(int i = 0; i < currencyListSize/columnNumber; i++ ){
            list.add(currencyDataList.get(0).replaceAll("\\s+", " ").substring(0,3)); //get code
            list.add(currencyDataList.get(3)); //get sell value
            list.add(currencyDataList.get(2)); //get buy value
            updateList();
            removeItems(currencyDataList, columnNumber);
        }
        return listOfList;
    }

    public ArrayList<ArrayList<String>> scrapBT() throws ResponseException, NotFound{
        url = "http://www.bt.com.tn/change";
        userAgent.visit(url);
        //find the lastUpdate of the currency table
        // find the currencies data
        userAgent.doc.findFirst("<table class=\"table\" id=\"devise-table\">").
                findEvery("<td>").
                toList().
                forEach(e -> currencyDataList.add(e.innerText().replaceAll("\\s+", "")));

        setCurrencyListSize();
        setColumnNumber(currencyDataList);
        for(int i = 0; i < currencyListSize/columnNumber; i++ ){
            list.add(currencyDataList.get(1)); //get code
            list.add(currencyDataList.get(3)); //get buy value
            list.add(currencyDataList.get(4)); //get sell value
            updateList();
            removeItems(currencyDataList, columnNumber);
        }
        return listOfList;
    }

    public ArrayList<ArrayList<String>> scrapBTE() throws ResponseException, NotFound{
        url = "http://www.bte.com.tn/?idart=8";
        userAgent.visit(url);
        //find the lastUpdate of the currency table
        lastUpdate = regex.getDate(userAgent.doc.
                findFirst("<table align=\"center\" cellpadding=\"2\" cellspacing=\"2\">").
                findFirst("<tr>").
                innerText());

        // find the currencies data
        userAgent.doc.
                findFirst("<table >").
                findEvery("<td>").
                toList().
                forEach(e -> currencyDataList.add(e.innerText().replaceAll("&nbsp;", "").replaceAll("\\s+", "")));

        // currencyDataList.forEach(e -> System.out.println(e.innerText()));
        setCurrencyListSize();
        setColumnNumber(currencyDataList);
        for(int i = 0; i < currencyListSize/columnNumber; i++ ){
            list.add(currencyDataList.get(1)); //get code
            list.add(currencyDataList.get(3)); //get buy value
            list.add(currencyDataList.get(4)); //get sell value
            updateList();
            removeItems(currencyDataList, columnNumber);
        }
        return listOfList;
    }

    public ArrayList<ArrayList<String>> scrapBIAT() throws ResponseException, NotFound {
        url = "http://www.biat.com.tn/biat/cours_devise.jsp";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = regex.getDate(userAgent.doc.findFirst("<p align=\"center\" class=\"couseDaysDevise\">").innerText());
        // find the currencies data
        userAgent.doc.
                findEvery("<tr class=\"fontdevise1\">").
                findEvery("<td>").
                toList().
                forEach(e -> currencyDataList.add(e.innerText()));

        //currencyDataList.forEach(e -> System.out.println(e.innerText()));
        setCurrencyListSize();
        setColumnNumber(currencyDataList);
        for(int i = 0; i < currencyListSize/columnNumber; i++ ){
            list.add(currencyDataList.get(1)); //get code
            list.add(currencyDataList.get(3)); //get sell value
            list.add(currencyDataList.get(4)); //get buy value
            updateList();
            removeItems(currencyDataList, columnNumber);
        }
        return listOfList;

    }

    public ArrayList<ArrayList<String>> scrapBNA()throws ResponseException, NotFound {
        url = "http://www.bna.com.tn/devise.asp";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = regex.getDate(userAgent.doc.findFirst("<td class=\"textevert\">").innerText());

        // find the currencies data
        userAgent.doc.
                findEvery("<table class=\"btn\" id=\"devise\">").
                findEvery("<table>").
                toList().
                forEach(e ->{
                        currencyDataList.add(e.innerText().replaceAll("\\s+", ""));
                });

        removeItems(currencyDataList, 1);
        setCurrencyListSize();
        setColumnNumber(currencyDataList);
        for(int i = 0; i < currencyListSize/columnNumber; i++ ){
            list.add(currencyDataList.get(0)); //get name
            list.add(currencyDataList.get(2)); //get sell value
            list.add(currencyDataList.get(3)); //get buy value
            updateList();
            removeItems(currencyDataList, columnNumber);
        }
        return listOfList;

    }

    public ArrayList<ArrayList<String>> scrapBTK() throws ResponseException, NotFound {
        url = "http://www.btknet.com/site/fr/convertisseur.php?id_article=33";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = regex.getDate(userAgent.doc.findFirst("<td class=\"titre_publication\">").innerText());

        // find the currencies data

        userAgent.doc.
                findEvery("<td class= \"ligne_devise_interne\">").
                toList().
                forEach(e -> currencyDataList.add(e.innerText().replaceAll("\\s+", "")));
        userAgent.doc.
                findEvery("<td class=\"ligne_devise_interne2\">").
                toList().forEach(e -> currencyDataList.add(e.innerText().replaceAll("\\s+", "")));


        setCurrencyListSize();
        setColumnNumber(currencyDataList);
        for(int i = 0; i < currencyListSize/columnNumber; i++ ){
            list.add(currencyDataList.get(0).substring(0, 3)); //get code
            list.add(currencyDataList.get(2)); //get buy value
            list.add(currencyDataList.get(3)); //get sell value
            updateList();
            removeItems(currencyDataList, columnNumber);
        }
        return listOfList;

    }

    //no data found
    public ArrayList<ArrayList<String>> scrapBTL() throws ResponseException, NotFound {
        url = "http://www.btl.com.tn/portal/page?_dad=portal&_schema=PORTAL&_pageid=37%2C391598";
        userAgent.visit(url);
        return listOfList;
    }

    public ArrayList<ArrayList<String>> scrapZitouna() throws ResponseException, NotFound {
        url = "http://www.banquezitouna.com/Fr/convertisseur-de-devise_64_103";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = regex.getDate(userAgent.doc.findFirst("<div class=\"caption_tabchange\">").innerText());

        // find the currencies data
        userAgent.doc.
                findFirst("<table class=\"tab_change\">").
                findEvery("<td>").
                toList()
                .forEach(e -> currencyDataList.add(e.innerText()));

        //currencyDataList.forEach(e -> System.out.println(e.innerText()));
        setCurrencyListSize();
        setColumnNumber(currencyDataList);
        for(int i = 0; i < currencyListSize/columnNumber; i++ ){
            list.add(currencyDataList.get(1)); //get code
            list.add(currencyDataList.get(2)); //get sell value
            list.add(currencyDataList.get(3)); //get buy value
            updateList();
            removeItems(currencyDataList, columnNumber);
        }
        return listOfList;

    }

    public ArrayList<ArrayList<String>> scrapPoste() throws ResponseException, NotFound {
        url = "http://www.poste.tn/change.php";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = regex.getDate(userAgent.doc.findFirst("<td height=\"30\" colspan=\"5\" class=\"tableau\">").innerText());

        // find the currencies data
        userAgent.doc.
                findEvery("<td class=\"cel_contenu\">").
                toList()
                .forEach(e -> currencyDataList.add(e.innerText()));

        //currencyDataList.forEach(e -> System.out.println(e.innerText()));
        setCurrencyListSize();
        setColumnNumber(currencyDataList);
        for(int i = 0; i < currencyListSize/columnNumber; i++ ){
            list.add(currencyDataList.get(1)); //get code
            list.add(currencyDataList.get(3)); //get sell value
            list.add(currencyDataList.get(4)); //get buy value
            updateList();
            removeItems(currencyDataList, columnNumber);
        }
        return listOfList;

    }

    public ArrayList<ArrayList<String>> scrapQNB() throws ResponseException, NotFound {
        url = "http://www.qnb.com.tn/en/financial-tools.html?view=financialtools";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = regex.getDate(userAgent.doc.findFirst("<table class=\"tabData\" id=\"currencyRates\">").findFirst("<th>").innerText());

        // find the currencies data
        userAgent.doc.
                findEvery("<td class=\"bottomline\">").
                toList()
                .forEach(e -> currencyDataList.add(e.innerText().replaceAll("&nbsp;", "")));
        setCurrencyListSize();
        setColumnNumber(currencyDataList);
        for(int i = 0; i < currencyListSize/columnNumber; i++ ){
            list.add(currencyDataList.get(1).replaceAll("\\s+", "")); //get code
            list.add(currencyDataList.get(3)); //get sell value
            list.add(currencyDataList.get(4)); //get buy value
            updateList();
            removeItems(currencyDataList, columnNumber);
        }
        return listOfList;

    }

    public ArrayList<ArrayList<String>> scrapSTB() throws ResponseException, NotFound {
        url = "http://www.stb.com.tn/fr/site/bourse-change/cours-de-change";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = regex.getDate(userAgent.doc.findFirst("<td class =\"date-change\">").innerText());

        // find the currencies data
        userAgent.doc.
                findFirst("<table class=\"cours-de-change\">").
                findEvery("<td>").
                toList()
                .forEach(e -> currencyDataList.add(e.innerText()));
        setCurrencyListSize();
        setColumnNumber(currencyDataList);
        for(int i = 0; i < currencyListSize/columnNumber; i++ ){
            list.add(currencyDataList.get(0)); //get code
            list.add(currencyDataList.get(1)); //get sell value
            list.add(currencyDataList.get(2)); //get buy value
            updateList();
            removeItems(currencyDataList, columnNumber);
        }
        return listOfList;

    }

    public ArrayList<ArrayList<String>> scrapStusid() throws ResponseException, NotFound {
        url = "http://www.stusidbank.com.tn/site/publish/content/article.asp?id=78";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        lastUpdate = regex.getDate(userAgent.doc.findFirst("<span class =\"texte6\">").innerText());

        // find the currencies data
        userAgent.doc.
                findEvery("<td class=\"CelTab2-2\">").
                toList()
                .forEach(e -> currencyDataList.add(e.innerText()));

        setCurrencyListSize();
        setColumnNumber(currencyDataList);
        for(int i = 0; i < currencyListSize/columnNumber; i++ ){
            list.add(currencyDataList.get(1)); //get code
            list.add(currencyDataList.get(4)); //get sell value
            list.add(currencyDataList.get(3)); //get buy value
            updateList();
            removeItems(currencyDataList, columnNumber);
        }
        return listOfList;

    }


}
