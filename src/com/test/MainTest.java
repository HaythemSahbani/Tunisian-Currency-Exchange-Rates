package com.test;


import com.jaunt.NotFound;
import com.jaunt.ResponseException;
import com.scraper.BankScrap;
import com.util.JsonIO;
import com.util.WriteToResources;

/**
 * Created by Haythem on 20/10/2015.
 */
public class MainTest {

    public static void main(String[] args) throws Exception {
       BankScrap bankScraper = new BankScrap();
        JsonIO jsonIO = new JsonIO();
        bankScraper.initBankList(".\\data\\bankList.json");
        bankScraper.getBankList().forEach(bank -> {
            try {
                bankScraper.scrap(bank);
            } catch (NotFound notFound) {
                notFound.printStackTrace();
            } catch (ResponseException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });




        jsonIO.write("test1.json", new WriteToResources().setJsonResource(bankScraper.getBankList()));

    }
}
