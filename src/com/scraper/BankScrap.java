package com.scraper;

import com.jaunt.*;
import com.jaunt.component.Table;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by Haythem on 16/10/2015.
 */
public class BankScrap {
    public BankScrap(){}

    UserAgent userAgent = new UserAgent();
    String url = "" ;
    Element element;
    String lastUpdate;

    public String getLastUpdate(){return this.lastUpdate;}

    public void scrapBTE() throws ResponseException, NotFound {
        String url = "http://www.bte.com.tn/?idart=8";//"http://www.bt.com.tn/change" ;

            UserAgent userAgent = new UserAgent();
            userAgent.visit(url);
            //Table table = userAgent.doc.getTable("<table id=devise-table>");   //get Table component via search query
            Element table1 = userAgent.doc.findFirst("<table align=\"center\" cellpadding=\"2\" cellspacing=\"2\">");
            Elements tds = table1.findEach("<td|th>");
            for(Element td: tds){System.out.println(td.innerText());}
    }

    public ArrayList<ArrayList<String>> scrapBIAT() throws ResponseException, NotFound {

        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        ArrayList<String> list = new ArrayList<String>();

        url = "http://www.biat.com.tn/biat/cours_devise.jsp";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
        element = userAgent.doc.findEach("<p align=\"center\" class=\"couseDaysDevise\">");
        lastUpdate = new DateFinder().getDate(element.innerText());

        // find the currencies data
        Elements tds;

        element = userAgent.doc.findEach("<tr class=\"fontdevise1\">");
        for(Element tr : element.findEach("<tr class=\"fontdevise1\">")){
            tds = tr.findEach("<td>");
            for(Element tt : tds){
                tt.findEvery("<td>");
                list.add(tt.innerText());
            }
            lists.add((ArrayList<String>) list.clone());
            list.clear();


        }
        return lists;

    }

    public ArrayList<ArrayList<String>> scrapBaraka() throws ResponseException, NotFound{
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        ArrayList<String> list = new ArrayList<String>();

        url = "http://www.albarakabank.com.tn/CoursConvertisseurDevise.aspx";
        userAgent.visit(url);

        //find the lastUpdate of the currency table
       element = userAgent.doc.findFirst("<span id=\"ctl00_ContentPlaceHolder1_Label9\">");
        lastUpdate = new DateFinder().getDate(element.innerText());

        // find the currencies data
        element = userAgent.doc.findFirst("<div dir=\"ltr\">");
        // find the table of currencies
        element = element.findFirst("<table border=\"0\" width=\"100%\">");
        for(Element tr : element.findEach("<tr class=\"FontCoursDevise\">")){
            for(Element span : tr.findEvery("<span>")){
            list.add(span.innerText());}

            lists.add((ArrayList<String>) list.clone());
            list.clear();
        }
        return lists;
    }

    public ArrayList<ArrayList<String>> scrapATB() throws ResponseException, NotFound{
        ArrayList<ArrayList<String>> lists = new ArrayList<ArrayList<String>>();
        ArrayList<String> list = new ArrayList<String>(5);

        url = "http://www.atb.com.tn/convertisseur";
        userAgent.visit(url);
        //find the lastUpdate of the currency table
        element = userAgent.doc.findFirst("<div class =\"txt\" >");
        lastUpdate = new DateFinder().getATBDate(element.getText());


        // find the currencies data
        Elements currencyData;

        element = userAgent.doc.findFirst("<table id=\"devises\">");
        currencyData = element.findEach("<td class=\"devisesvalue Style1\">");
        Elements currencyName = element.findEach("<td class=\"devises Style1\" width=\"39%\">");
        List<Element> currencyNameList = currencyName.toList();

        List<Element> currencyDataList = currencyData.toList();



        for(int i = 0; i < currencyNameList.size(); i++ ){
            list.add(currencyNameList.get(i).innerText());
            for(int j = 0; j < 4; j++) {
                list.add(currencyDataList.get(j).innerText());
            }
            lists.add((ArrayList<String>) list.clone());
            list.clear();
            currencyDataList.remove(0);
            currencyDataList.remove(0);
            currencyDataList.remove(0);
            currencyDataList.remove(0);
        }





        return lists;
    }











    public void scrapBNA()throws ResponseException, NotFound {

        Bank bna = new Bank("BNA", "Banque Nationale Agricole");
        ArrayList<Currency> currencyArrayList = new ArrayList<Currency>();


        url = "http://www.bna.com.tn/devise.asp";
        userAgent.visit(url);
        //find the lastUpdate
        element = userAgent.doc.findEach("<TD class=textevert colSpan=7>");

        //ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
        LinkedHashMap<String, ArrayList<String>> table = new LinkedHashMap<String, ArrayList<String>>();
        ArrayList<String> column = new ArrayList<String>();

         ArrayList<String> firstRow = new ArrayList<String>() ;
        firstRow.add("Monnaie");
        firstRow.add("Unit&eacute;s");
        firstRow.add("Achat");
        firstRow.add("Vente");
        table.put("Achat", firstRow);


    lastUpdate = new DateFinder().getDate(element.innerText());
        System.out.println(lastUpdate);

        // find first raw containing the titles of the columns
        Table htmlTable = userAgent.doc.getTable("<table width=\"100%\" border=\"0\" cellspacing=\"0\" bordercolor=\"#CCCCCC\" class=\"btn\" id=\"devise\">");
        for(String el : table.keySet()){
            //System.out.println(el);
            //column.add(el);
            Elements elements = htmlTable.getRow(1);

            for(Element element : elements){
                String str = element.innerText().replaceAll("\\s+", " ");
                str = str.replaceAll("&nbsp;", "");
                str = str.replaceAll(",", "");
               System.out.println(str);

                firstRow.add(str);



               // table.put(el, str);

                // System.out.println(table.size());



            }
            //System.out.println(table.values());

            // column.clear();
            //System.out.println("table size = "+ table.size());
            //System.out.println("column size = "+ column.size());
        }
        /*for (ArrayList<String> col : table){
            System.out.println(col.get(0));
            for (String l : col){
                System.out.println(l);

            }
        }*/



        /*element = userAgent.doc.findFirst("<table width=\"100%\" border=\"0\" cellspacing=\"0\" bordercolor=\"#CCCCCC\" class=\"btn\" id=\"devise\">");

        Elements tds = element.findEach("<td>");
        for(Element td: tds){System.out.println(td.innerText());}
        // find the currencies data
        element = userAgent.doc.findEach("<tr class=\"fontdevise1\">");
        //System.out.println(element.innerHTML());
        tds = element.findEach("<td>");
        for(Element td: tds){System.out.println(td.innerText());}*/


    }


    public static void main(String[] args){
        try{

            new BankScrap().scrapBIAT();


        }
        catch(JauntException e){
            System.err.println(e);
        }
    }
}
