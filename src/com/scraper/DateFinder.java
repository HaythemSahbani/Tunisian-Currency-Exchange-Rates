package com.scraper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Haythem on 25/10/2015.
 */


public class DateFinder {


    private Pattern pattern;
    private Matcher matcher;

    private static final String DATE_PATTERN =
            "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

    private static final String DATE_PATTERN_ATB ="(\\d.*\\d)";
    private static final String ONLY_LETTERS_PATTERN ="[^\\W|\\d]+";

    private static final ArrayList<String> monthList = new ArrayList<String>(
            Arrays.asList("janvier", "fevrier", "mars", "avril", "mai", "juin", "juillet", "aout", "septembre", "octobre", "novembre", "decembre"));

    public DateFinder(){}

    public String getDate(String date) {
        pattern = Pattern.compile(DATE_PATTERN);
        matcher = pattern.matcher(date);
        if(matcher.find()){date = matcher.group();}
        else{ date="no date found";}
        return date;
    }
    public String getATBDate(String date){

        pattern = Pattern.compile(DATE_PATTERN_ATB);
        matcher = pattern.matcher(date);
        if(matcher.find()){
            date = matcher.group();
            pattern = Pattern.compile(ONLY_LETTERS_PATTERN);
            matcher = pattern.matcher(date);
                if(matcher.find()){
                    String str = matcher.group();
                    if (monthList.contains(str)){
                        Integer monthIndex =  monthList.indexOf(str)+1;
                        date = date.replaceAll(monthList.get(monthIndex-1), monthIndex.toString());
                    }
                }
        }
        else{ date="no date found";}
        return date;
    }

}
