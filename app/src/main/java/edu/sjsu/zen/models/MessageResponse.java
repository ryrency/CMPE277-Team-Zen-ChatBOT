package edu.sjsu.zen.models;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageResponse {
    public static final String INSTRUCTOR_NAME = "instructor_name";
    public static final String INSTRUCTOR_EMAIL = "instructor_email";
    public static final String INSTRUCTOR_PHNO = "instructor_phoneNo";
    public static final String DAY_OF_CLASS = "day";
    public static final String CLASS_START_TIME = "class_start_time";
    public static final String CLASS_END_TIME = "class_end_time";
    public static final String DUE_DATE = "due_date";
    public static final String OFFICE_LOCATION = "office_location";
    public static final String COURSE_NAME = "course_name";


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
            case INSTRUCTOR_OFFICE_LOCATION: return "Office Location for " + getString(INSTRUCTOR_NAME) + " is " + getString(OFFICE_LOCATION);
            case INSTRUCTOR_NAME:return getString(INSTRUCTOR_NAME);
            case INSTRUCTOR_PHONE_NO:
                if (!getString("phn_no").equals("Office Phone No not shared"))
                    return getString(INSTRUCTOR_NAME) +"'s Phone No. is "+getString("phn_no");
                else
                    return getString(INSTRUCTOR_NAME) +"'s Phone No. has not been shared";
            case INSTRUCTOR_EMAIL:return getString(INSTRUCTOR_EMAIL);
            case INSTRUCTOR_OFFICE_HOURS: return  getString(INSTRUCTOR_NAME)+"'s Office Hours are from - "+
                    getString("office_hours_start_time") + " - "
                    +getString("office_hours_end_time");
            case COURSE_NAME: return getString(COURSE_NAME) + " - " + getString("description");
            case COURSE_PRE_REQUIREMENTS: return getString(COURSE_NAME)+" pre-requirements are "+getString("pre_requirement");
            case CLASS_TIMINGS: return "Classes are on "+getString(DAY_OF_CLASS)+" from "+getString(CLASS_START_TIME)
                    + " to "+ getString(CLASS_END_TIME);
            case CLASS_LOCATION:return "The course is held at "+getString("class location");
            case COURSE_WEBSITE:return "The course website is "+getString("course_website");
            case PROJECT_DUE_DATE:
                if (getString(DUE_DATE) != null)
                    return "Project due date is "+getString(DUE_DATE).replace("T"," at ");
                else
                    return "Project due date has not been announced!";

            case MID_TERM_DUE_DATE:
                if (getString(DUE_DATE) != null)
                    return "Mid Term is on "+getString(DUE_DATE).replace("T"," at ");
                else
                    return "Mid term date has not been announced!";
            case FINAL_EXAM_DUE_DATE:
                if (getString(DUE_DATE) != null)
                    return "Final Exam is on "+getString(DUE_DATE.replace("T"," at "));
                else
                return "Final Exam date has not been announced!";
            case COURSE_GRADING: return getString("activity")+" weightage is "+getString("weightage");
            case REFERENCE_MATERIALS: return "References for "+getString(COURSE_NAME)+ " are -\n"+getString("references");
            case UNKNOWN:return "Sorry, I do not understand your question";
            default: return "Sorry, I do not understand your question";
        }
    }

    public String getDisplayEmailAddress() {
        return getString(INSTRUCTOR_EMAIL);
    }
    public String getDisplayPhnNo() {
        return getString(INSTRUCTOR_PHNO);

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

    public List<Suggestion> getValidSuggestions() {
        List<Suggestion> filteredSuggestions = new ArrayList<>();
        Category category = getCategory();
        for (Suggestion suggestion  : category.getSuggestions()) {
            if (category == Category.INSTRUCTOR_EMAIL && suggestion == Suggestion.EMAIL_INSTRUCTOR){
                if (getString(INSTRUCTOR_EMAIL) != null) {
                    filteredSuggestions.add(suggestion);
                }
            } else if (category == Category.PROJECT_DUE_DATE && suggestion == Suggestion.SET_REMINDER){
                if (getString(DUE_DATE) != null) {
                    filteredSuggestions.add(suggestion);
                }
            } else {
                filteredSuggestions.add(suggestion);
            }
        }
        return filteredSuggestions;
    }
}
