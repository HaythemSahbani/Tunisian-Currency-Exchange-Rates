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

    private void updateList(){
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
                        4,
                        false,
                        false);
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
                        3,
                        false,
                        false);
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
                        4,
                        false,
                        false);
                break;
            case "BH": // add the substring to the code getter. && .replaceAll("&nbsp;", ""))
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
                        3,
                        true,
                        false);
                break;
            case "BT": //replaceAll("\\s+", "")));
                userAgent.visit(bank.getUrl());
                scrapBank(bank,
                        "no data",
                        userAgent.doc.
                                findFirst("<table class=\"table\" id=\"devise-table\">").
                                findEvery("<td>").
                                toList(),
                        1,
                        3,
                        4,
                        true,
                        false);
                break;
            case "BTE": //.replaceAll("&nbsp;", "").replaceAll("\\s+", "")));
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
                        4,
                        true,
                        false);
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
                        4,
                        false,
                        false);
                break;
            case "BNA": // .replaceAll("\\s+", ""));
                // add removeItems(currencyDataList, 1);
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
                        3,
                        true,
                        true);
                break;
            case "BTK": //.replaceAll("\\s+", "")));
                userAgent.visit(bank.getUrl());
                userAgent.doc.
                        findEvery("<td class=\"ligne_devise_interne2\">").
                        toList().
                        forEach(e -> currencyDataList.
                                add(e.innerText().
                                        replaceAll("\\s+", "")));
                scrapBank(bank,
                        regex.getDate(userAgent.doc.
                                findFirst("<td class=\"titre_publication\">").
                                innerText()),
                        userAgent.doc.
                                findEvery("<td class= \"ligne_devise_interne\">").
                                toList(),
                        0,
                        2,
                        3,
                        true,
                        false);
                break;
            case "BTL":
                System.out.println("+++++++++++++++ no data found +++++++++++++++");
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
                        3,
                        false,
                        false);
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
                        4,
                        false,
                        false);
                break;
            case "QNB": //.replaceAll("&nbsp;", "")));
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
                        4,
                        true,
                        false);
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
                        2,
                        false,
                        false);
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
                        4,
                        false,
                        false);
                break;
            default:
                System.out.println(" no bank data about " + bank.getCode());

        }
    }

    public void scrapBank(Bank bank,
                          String lastUpdate,
                          List<Element> htmlElementsList,
                          int codeIndex,
                          int buyValueIndex,
                          int sellValueIndex,
                          boolean specialCharacters,
                          boolean firstRow)
            throws ResponseException, NotFound {

        System.out.println("------------------------------------------------");
        System.out.println("Scraping " + bank.getCode() + " last update:" + lastUpdate);
        System.out.println("------------------------------------------------");


        if (specialCharacters) {
            htmlElementsList.forEach(element -> currencyDataList.add(
                    element.
                            innerText().
                            replaceAll("&nbsp;", "").
                            replaceAll("\\s+", "")
            ));
        } else {
            htmlElementsList.forEach(element -> currencyDataList.add(
                    element.
                            innerText()
            ));
        }

        if (firstRow) {
            removeItems(currencyDataList, 1);
        }

        setCurrencyListSize();
        setColumnNumber(currencyDataList);
        for (int i = 0; i < currencyListSize / columnNumber; i++) {
            list.add(currencyDataList.get(codeIndex));      //get code
            list.add(currencyDataList.get(buyValueIndex));  //get buy value
            list.add(currencyDataList.get(sellValueIndex)); //get sell value
            updateList();
            removeItems(currencyDataList, columnNumber);

        }
        for (ArrayList<String> lis : listOfList) {
            System.out.println(" code: " + lis.get(0) + " Buy  " + lis.get(1) + " Sell  " + lis.get(2));

        }
        listOfList.clear();

    }
}
