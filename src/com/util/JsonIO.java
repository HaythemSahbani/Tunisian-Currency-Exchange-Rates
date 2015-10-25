package com.util;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;




/**
 * Created by Haythem on 17/10/2015.
 */
public class JsonIO {

    private JSONParser jsonParser;

    public JsonIO () throws IOException, ParseException{
        this.jsonParser = new JSONParser();
    }

    public JSONObject read(String filePath) throws  IOException, ParseException{
        // Open file then parse it through the JSONParser class. return a JSONObject.
        return (JSONObject) jsonParser.parse(new FileReader(filePath));
    }

    public void write(String filePath, JSONObject jsonObject) throws IOException{
            FileWriter file = new FileWriter(filePath);
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
    }
}
