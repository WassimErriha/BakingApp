package com.example.wassim.bakingapp.Utils;

import android.net.Uri;
import com.example.wassim.bakingapp.Objects.Ingredient;
import com.example.wassim.bakingapp.Objects.Recipe;
import com.example.wassim.bakingapp.Objects.Step;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class JsonUtils {

    private static URL buildUrl(String string) {
        Uri builtURL = Uri.parse(string).buildUpon().build();
        URL url = null;
        try {
            url = new URL(builtURL.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String getResponseFromHttpUrl() throws IOException {
        URL JSON_URL = buildUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
        HttpURLConnection urlConnection = (HttpURLConnection) JSON_URL.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            }
            return null;
        } finally {
            urlConnection.disconnect();
        }
    }

    public static ArrayList<Recipe> fetchRecipe() {
        ArrayList<Recipe> recipes = null;
        String httpResponse = null;
        try {
            httpResponse = getResponseFromHttpUrl();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (httpResponse != null) {
            recipes = extractRecipes(httpResponse);
        }
        return recipes;
    }

    private static ArrayList<Recipe> extractRecipes(String jsonResponse) {
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();

        try {
            JSONArray root = new JSONArray(jsonResponse);
            for (int i = 0; i < root.length(); i++) {
                ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
                ArrayList<Step> stepArrayList = new ArrayList<>();
                JSONObject recipe = root.getJSONObject(i);
                String name = recipe.getString("name");
                JSONArray ingredientsArray = recipe.getJSONArray("ingredients");
                for (int ingredientIndex = 0 ; ingredientIndex < ingredientsArray.length() ; ingredientIndex++ ){
                    JSONObject ingredientObject = ingredientsArray.getJSONObject(ingredientIndex);
                    String quantity = ingredientObject.getString("quantity");
                    String measure = ingredientObject.getString("measure");
                    String ingredient = ingredientObject.getString("ingredient");
                    ingredientArrayList.add( new Ingredient (quantity,measure,ingredient));
                }
                JSONArray stepsArray = recipe.getJSONArray("steps");
                for (int stepIndex = 0 ; stepIndex < stepsArray.length() ; stepIndex++ ){
                    JSONObject stepObject = stepsArray.getJSONObject(stepIndex);
                    int stepId = stepObject.getInt("id");
                    String shortDescription = stepObject.getString("description");
                    String description = stepObject.getString("shortDescription");
                    String videoUrl = stepObject.getString("videoURL");
                    String thumbnailUrl = stepObject.getString("thumbnailURL");
                    stepArrayList.add(new Step(stepId,shortDescription,description,videoUrl,thumbnailUrl));
                }
                recipeArrayList.add(new Recipe(name,ingredientArrayList,stepArrayList));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeArrayList;
    }
}
