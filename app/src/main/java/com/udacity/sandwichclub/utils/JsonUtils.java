package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            //String mainName, List<String> alsoKnownAs, String placeOfOrigin, String description, String image, List<String> ingredients
            List<String> ingredients = new ArrayList<>();
            JSONArray jsonIngredients = jsonObject.getJSONArray("ingredients");
            for (int i = 0; i < jsonIngredients.length(); i++) {
                ingredients.add(jsonIngredients.get(i).toString());
            }

            List<String> akaList = new ArrayList<>();
            JSONArray jsonAKA = jsonObject.getJSONObject("name").getJSONArray("alsoKnownAs");
            for (int i = 0; i < jsonAKA.length(); i++) {
                akaList.add(jsonAKA.get(i).toString());
            }

            Sandwich sandwich = new Sandwich(
                    jsonObject.getJSONObject("name").getString("mainName"),
                    akaList,
                    jsonObject.getString("placeOfOrigin"),
                    jsonObject.getString("description"),
                    jsonObject.getString("image"),
                    ingredients);
            return  sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
