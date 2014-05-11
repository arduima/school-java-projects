package edu.jkwsql.json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by dimitrikoshkin on 4/18/14.
 * Dependency GSON: https://code.google.com/p/google-gson/
 * Static class to read and write JSON files to and from Java Objects
 */
public class JSONProcessor {

    private JSONProcessor() {
    }

    public static Object fromJSON(String filePath, Object object) {
        Gson gson = new Gson();

        Object dataObject = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            //convert the json string back to object
            dataObject = gson.fromJson(br, object.getClass());

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Check if " + filePath + " is present in the main project repository.",
                    "JSON File Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "The file " + filePath + " is badly formatted.",
                    "JSON File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return dataObject;
    }

    public static void toJSON(String filePath, Object obj) {
        Gson gson = new Gson();

//        Convert java object to JSON format and return string
        String json = gson.toJson(obj);
        try {
            //write converted json data to a file named "file.json"
            FileWriter writer = new FileWriter(filePath);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "The file " + filePath + " cannot be saved.",
                    "JSON File Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
