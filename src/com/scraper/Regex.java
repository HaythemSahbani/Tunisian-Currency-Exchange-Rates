package com.scraper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Haythem on 25/10/2015.
 */


public class Regex {

    private Pattern pattern;
    private Matcher matcher;

    // private static final String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

    private static final String DATE_PATTERN ="(\\d.*\\d)";
    private static final String ONLY_LETTERS_PATTERN ="[^\\W|\\d]+";
    private static final String BEGIN_WITH_CHARACTER ="^[a-zA-Z].*";
    private static final String FIND_FLOAT ="[0-9]*\\\\.?,?[0-9]+";

    private static final ArrayList<String> monthList = new ArrayList<String>(
            Arrays.asList("janvier", "fevrier", "mars", "avril", "mai", "juin", "juillet", "aout", "septembre", "octobre", "novembre", "decembre"));

    public Regex(){}

    public String getDate(String date){

        pattern = Pattern.compile(DATE_PATTERN);
        matcher = pattern.matcher(date);
        // Find a date regex (DD-MM-YYYY or DD MONTH YYYY )
        if(matcher.find()){
            date = matcher.group();

            // Extract a month string and change it with its respective number
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

    public boolean beginWithChar(String str){
        pattern = Pattern.compile(BEGIN_WITH_CHARACTER);
        matcher = pattern.matcher(str);
        if(matcher.find()){
            return true;
        }else return false;
    }

    public String toFloat(String str){
        pattern = Pattern.compile(FIND_FLOAT);
        matcher = pattern.matcher(str);
        if(matcher.find()){
            return matcher.group();
        }else
            return str;
    }

}
