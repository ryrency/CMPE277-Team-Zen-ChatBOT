package edu.sjsu.zen.models;

import android.support.annotation.Nullable;
import android.util.Log;

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
    private String getString(String key) {
        JSONObject data = getData();
        try{
            return data.getString(key);
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getDisplayText() {
        Category category = getCategory();
        if (category == Category.INSTRUCTOR_OFFICE_LOCATION) {
           return "Office Location for " + getString("instructor_name") + " is " + getString("office_location");

        }
        if(category == Category.INSTRUCTOR_NAME) {
            return getString("instructor_name");
        }
        if(category == Category.COURSE_NAME) {
            return getString("course_name") + " is about " + getString("description");
        }
        return "";

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
