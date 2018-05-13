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
    public String getString(String key) {
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
        switch (category){
            case INSTRUCTOR_OFFICE_LOCATION: return "Office Location for " + getString("instructor_name") + " is " + getString("office_location");
            case INSTRUCTOR_NAME:return getString("instructor_name");
            case INSTRUCTOR_PHONE_NO:return getString("instructor_name") +"'s Phone No. is "+getString("phn_no");
            case INSTRUCTOR_EMAIL:return getString("instructor_email");
            case INSTRUCTOR_OFFICE_HOURS: return  getString("instructor_name")+"'s Office Hours are from - "+
                    getString("office_hours_start_time") + " - "
                    +getString("office_hours_end_time");
            case COURSE_NAME: return getString("course_name") + " is about " + getString("description");
            case COURSE_PRE_REQUIREMENTS: return getString("course_name")+" pre-requirements are "+getString("pre_requirement");
            case CLASS_TIMINGS: return "Classes are on "+getString("day")+" from "+getString("class_start_time")
                    + " to "+ getString("class_end_time");
            case CLASS_LOCATION:return "The course is held at "+getString("class location");
            case COURSE_WEBSITE:return "The course website is "+getString("course_website");
            case PROJECT_DUE_DATE:return "Project due date is "+getString("due_date");
            case MID_TERM_DUE_DATE:return "Mid Term due date is "+getString("due_date");
            case FINAL_EXAM_DUE_DATE:return "Final Exam Due Date is "+getString("due_date");
            case FINAL_EXAM_WEIGHTAGE:
            case PROJECT_WEIGHTAGE:
            case MID_TERM_WEIGHTAGE:
            case ASSIGNMENT_WEIGHTAGE:
            case LAB_WEIGHTAGE:
            case COURSE_GRADING: return getString("activity")+" weightage is "+getString("weightage");
            case UNKNOWN:return "Sorry, I do not understand your question";
            default: return "";
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
