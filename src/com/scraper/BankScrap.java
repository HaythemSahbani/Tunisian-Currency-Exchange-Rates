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

    public ArrayList<Bank> getBankList() {
        return bankList;
    }

    public void setBankList(ArrayList<Bank> bankList) {
        this.bankList = bankList;
    }

    ArrayList<Bank> bankList = new ArrayList<>();

    UserAgent userAgent = new UserAgent();
    Regex regex = new Regex();
    int columnNumber;
    int currencyListSize;
    private static ArrayList<String> currencyDataList = new ArrayList<>();
    int visitCounter = 0;



    public void initBankList(String jsonFilePath) throws ResponseException, NotFound, IOException, InterruptedException{
        JNode bankJson = userAgent.openJSON(new File(jsonFilePath));
        bankJson.forEach(node -> {
            try {
                bankList.add(new Bank(
                        node.getName(),
                        node.get("name").toString(),
                        node.get("currencyLink").toString().replaceAll("\\\\", "")));
            } catch (NotFound notFound) {
                notFound.printStackTrace();
            }
        });
    }
    // get the url of the bank then call the scrap method depending on the bank name
    public BankScrap() throws ResponseException, NotFound, IOException, InterruptedException {

    }


    private void setCurrencyListSize(){
        this.currencyListSize = currencyDataList.size();
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

    public boolean visit(UserAgent userAgent, String url) throws InterruptedException {
        try{ userAgent.visit(url);
            return true;
        } catch (ResponseException e) {
            visitCounter++;
            System.out.println("Connection error, sleeping 5 sec then trying again");
            if (visitCounter == 5){ // avoid an infinite loop.
                System.out.println("+++++++++ Bank server unavailable, URL: "+ url);
                visitCounter = 0;
                return false;
            }
            Thread.sleep(5000);
            visit(userAgent, url);
        } return false;
    }

    public void scrap(Bank bank) throws NotFound, ResponseException, InterruptedException {
        switch (bank.getCode()) {
            case "Baraka":
                if(visit(userAgent, bank.getUrl())) {
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
                }
                break;

            case "ATB":
                if(visit(userAgent, bank.getUrl())) {
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
                }
                break;

            case "Attijari":
                if(visit(userAgent, bank.getUrl())) {
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
                }
                break;

            case "BH": // add the substring to the code getter. && .replaceAll("&nbsp;", "")) // Get the three first letters in the codeIndex
                if(visit(userAgent, "http://www.bh.com.tn")) { // to avoid a security issue with the bank server
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
                            true);
                }
                break;

            case "BT": //replaceAll("\\s+", "")));
                if(visit(userAgent, bank.getUrl())) {
                    scrapBank(bank,
                            "no data",
                            userAgent.doc.
                                    findFirst("<table id=\"devise-table\">").
                                    findEvery("<td>").
                                    toList(),
                            1,
                            3,
                            4,
                            true,
                            false);
                }
                break;

            case "BTE": //.replaceAll("&nbsp;", "").replaceAll("\\s+", "")));
                if(visit(userAgent, bank.getUrl())) {
                    scrapBank(bank,
                            regex.getDate(userAgent.doc.
                                    findFirst("<table>").
                                    findEvery("<th>").
                                    getElement(1). // get the second <th> containing the date data
                                    innerText()),
                            userAgent.doc.
                                    findFirst("<table>").
                                    findEvery("<td class = \"bottomline\">").
                                    toList(),
                            1,
                            3,
                            4,
                            true,
                            false);
                }
                break;

            case "BIAT":
                if(visit(userAgent, bank.getUrl())) {
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
                }
                break;
            case "BNA": // .replaceAll("\\s+", ""));
                if(visit(userAgent, bank.getUrl())) {
                    scrapBank(bank,
                            regex.getDate(
                                    userAgent.doc.
                                            findFirst("<td class=\"textevert\">").
                                            innerText()),
                            userAgent.doc.
                                    findEvery("<table id=\"devise\">").
                                    findEvery("<table>").
                                    toList().
                                    subList(1,  // jump the first list item
                                            userAgent.doc.
                                                    findEvery("<table class=\"btn\" id=\"devise\">").
                                                    findEvery("<table>").
                                                    toList().
                                                    size()),
                            0,
                            2,
                            3,
                            true,
                            false);
                }
                break;
            case "BTK": //.replaceAll("\\s+", ""))); // get the three first letters of the codeIndex
                if(visit(userAgent, bank.getUrl())) {
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
                }
                break;
            case "Zitouna":
                if(visit(userAgent, bank.getUrl())) {
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
                }
                break;
            case "Poste":
                if(visit(userAgent, bank.getUrl())) {
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
                }
                break;
            case "QNB": //.replaceAll("&nbsp;", "")));
                if(visit(userAgent, bank.getUrl())) {
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
                }
                break;
            case "STB":
                if(visit(userAgent, bank.getUrl())){
                    scrapBank(bank,
                            regex.getDate(userAgent.doc.
                                    findFirst("<td class =\"date-change\">").
                                    innerText()),
                            userAgent.doc.
                                    findFirst("<table class=\"cours-de-change\">").
                                    findEvery("<td>").
                                    toList(),
                            0,
                            2,
                            1,
                            false,
                            false);
                }
                break;
            case "Stusid":
                if(visit(userAgent, bank.getUrl())) {
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
                }
                break;
            default:
                System.out.println(" no bank data about " + bank.getCode());

        }
    }

    public String mapToCode(String str){

        switch (str){
            case "RyalSaoudien":
                str = "SAR";
                break;
            case "DollarCanadien":
                str = "CAD";
                break;
            case "CouronneDanoise":
                str = "DKK";
                break;
            case "DirhamdesE.A.U":
                str = "AED";
                break;
            case "DollardesEtats-Unis":
                str = "USD";
                break;
            case "LivreSterling":
                str = "GBP";
                break;
            case "YenJaponais":
                str = "JPY";
                break;
            case "DinarKoweitien":
                str = "KWD";
                break;
            case "CouronneNorv�gienne":
                str = "NOK";
                break;
            case "RyalQuatari":
                str = "QAR";
                break;
            case "CouronneSu�doise":
                str = "SEK";
                break;
            case "FrancSuisse":
                str = "CHF";
                break;
            case "Euro":
                str = "EUR";
                break;
            case "DinarBahra�n":
                str = "BHD";
                break;
            case "DinarLybien":
                str = "LYD";
                break;
            default:
                break;
        }

        return str;
    }

    private void scrapBank(Bank bank,
                          String lastUpdate,
                          List<Element> htmlElementsList,
                          int codeIndex,
                          int buyIndex,
                          int sellIndex,
                          boolean specialCharacters,
                          boolean jumpFirstRow)
            throws ResponseException, NotFound {

            bank.setUpdateTime(lastUpdate);
            String code;
            Float buy, sell;
            int counterStart = 0;

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

            setCurrencyListSize();
            setColumnNumber(currencyDataList);


            if (jumpFirstRow) {
                codeIndex += columnNumber;
                buyIndex += columnNumber;
                sellIndex += columnNumber;
                counterStart = columnNumber;
            }

            for (int i = counterStart; i < currencyListSize; i += columnNumber) {
                if (currencyDataList.get(codeIndex).length() > 3)
                    code = mapToCode(currencyDataList.get(codeIndex)).substring(0, 3); //get code
                else code = currencyDataList.get(codeIndex);      //get code

                buy = Float.valueOf(regex.toFloat(currencyDataList.get(buyIndex)).replaceAll("[^\\d.]+|\\.(?!\\d)", ""));  //get buy value
                sell = Float.valueOf(currencyDataList.get(sellIndex).replaceAll("[^\\d.]+|\\.(?!\\d)", "")); //get sell value
                bank.getCurrencyList().add(new Currency(code, buy, sell));
                codeIndex += columnNumber;
                buyIndex += columnNumber;
                sellIndex += columnNumber;
            }
            currencyDataList.clear();

    }


}
