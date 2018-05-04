package edu.sjsu.zen.models;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageResponse {
    private int categoryType;
    private JSONObject data;

    public Category getCategory() {
        return Category.fromCategoryTypeInt(categoryType);
    }

    private JSONObject getData() {
        return data;
    }

    @Nullable
    public String getString(String key) {
        JSONObject data = getData();
        try{
            return data.getString(key);
        }
        catch (JSONException e) {
            return null;
        }
    }

    private MessageResponse(int categoryType, JSONObject data) {
        this.categoryType = categoryType;
        this.data = data;
    }

    @Nullable
    public static MessageResponse fromJSONObjectResponse(JSONObject response) {
        try {
            int categoryType = response.getInt("categoryType");
            return new MessageResponse(categoryType, response);
        } catch (JSONException e) {
            return null;
        }
    }
}
