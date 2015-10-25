package com.scraper;

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

    public DateFinder(){
        pattern = Pattern.compile(DATE_PATTERN);
    }

    public String getDate(String desc) {

        String date ;
        matcher = pattern.matcher(desc);
        if(matcher.find()){date = matcher.group();}
        else{ date="no date found";}
        return date;
    }

}
