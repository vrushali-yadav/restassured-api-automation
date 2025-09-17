package utils;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class JsonReader {

    public static String getTestData(String testKey) throws IOException, ParseException {
        String testValue;
        return testValue = (String) getJsonData().get(testKey);
    }

    public static JSONObject getJsonData() throws IOException, ParseException {

        // pass the path of the testdata.json file
        File file = new File("resources/TestData/testdata.json");

        // convert json file into string
        String json = FileUtils.readFileToString(file, "UTF-8");

        // parse the string into object
        Object obj = new JSONParser().parse(json);

        // give JSON object so that it can be returned to the function everytime it get called
        JSONObject jsonObject = (JSONObject) obj;

        return jsonObject;
    }

    public static JSONArray getJsonArray(String key) {
        JSONObject jsonObject;
        try {
            jsonObject = getJsonData();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray jsonArray = (JSONArray) jsonObject.get(key);
        return jsonArray;
    }

    public static Object getJsonArrayData(String key, int index) {
        JSONArray array = getJsonArray(key);
        return array.get(index);
    }

}
