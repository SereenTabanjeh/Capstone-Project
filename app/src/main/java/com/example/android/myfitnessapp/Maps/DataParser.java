package com.example.android.myfitnessapp.Maps;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    public List<HashMap<String, String>> parse(String jsonData) {
        if (jsonData != null) {
            JSONArray jsonArray = null;
            JSONObject jsonObject;

            try {

                jsonObject = new JSONObject((String) jsonData);
                jsonArray = jsonObject.getJSONArray("results");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return getPlaces(jsonArray);
        }
        return null;
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray) {
        if (jsonArray != null) {
            int placesCount = jsonArray.length();
            List<HashMap<String, String>> placesList = new ArrayList<>();
            HashMap<String, String> placeMap = null;

            for (int i = 0; i < placesCount; i++) {
                try {
                    placeMap = getPlace((JSONObject) jsonArray.get(i));
                    placesList.add(placeMap);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return placesList;
        }
        return  null;
    }

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
        if (googlePlaceJson != null) {
            HashMap<String, String> googlePlaceMap = new HashMap<String, String>();
            String placeName = "-NA-";
            String vicinity = "-NA-";
            String latitude = "";
            String longitude = "";
            String reference = "";


            try {
                if (!googlePlaceJson.isNull("name")) {
                    placeName = googlePlaceJson.getString("name");
                }
                if (!googlePlaceJson.isNull("vicinity")) {
                    vicinity = googlePlaceJson.getString("vicinity");
                }
                latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
                longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
                reference = googlePlaceJson.getString("reference");
                googlePlaceMap.put("place_name", placeName);
                googlePlaceMap.put("vicinity", vicinity);
                googlePlaceMap.put("lat", latitude);
                googlePlaceMap.put("lng", longitude);
                googlePlaceMap.put("reference", reference);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return googlePlaceMap;
        }
        return null;
    }
}
