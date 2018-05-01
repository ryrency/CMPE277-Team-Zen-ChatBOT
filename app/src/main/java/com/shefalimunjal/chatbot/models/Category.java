package com.shefalimunjal.chatbot.models;

import com.shefalimunjal.chatbot.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Category {
    UNKNOWN(-1, new ArrayList<Suggestion>()),
    INSTRUCTOR_EMAIL(0, Arrays.asList(Suggestion.INSTRUCTOR_PHONE_NO, Suggestion.INSTRUCTOR_OFFICE_HOURS,Suggestion.INSTRUCTOR_OFFICE_LOCATION,Suggestion.INSTRUCTOR_NAME)),
    INSTRUCTOR_NAME(0, Arrays.asList(Suggestion.INSTRUCTOR_EMAIL,Suggestion.INSTRUCTOR_PHONE_NO, Suggestion.INSTRUCTOR_OFFICE_HOURS,Suggestion.INSTRUCTOR_OFFICE_LOCATION)),
    INSTRUCTOR_CONTACT(1, Arrays.asList(Suggestion.INSTRUCTOR_NAME,Suggestion.INSTRUCTOR_PHONE_NO, Suggestion.INSTRUCTOR_OFFICE_HOURS,Suggestion.INSTRUCTOR_OFFICE_LOCATION)),
    INSTRUCTOR_PHONE_NO(2, Arrays.asList(Suggestion.INSTRUCTOR_NAME,Suggestion.INSTRUCTOR_EMAIL, Suggestion.INSTRUCTOR_OFFICE_HOURS,Suggestion.INSTRUCTOR_OFFICE_LOCATION)),
    INSTRUCTOR_OFFICE_LOCATION(3, Arrays.asList(Suggestion.INSTRUCTOR_EMAIL,Suggestion.INSTRUCTOR_PHONE_NO,Suggestion.INSTRUCTOR_NAME, Suggestion.INSTRUCTOR_OFFICE_HOURS)),
    INSTRUCTOR_OFFICE_HOURS(4, Arrays.asList(Suggestion.INSTRUCTOR_EMAIL,Suggestion.INSTRUCTOR_PHONE_NO, Suggestion.INSTRUCTOR_OFFICE_LOCATION)),
    COURSE_NAME(5,Arrays.asList(Suggestion.COURSE_PRE_REQUIREMENTS,Suggestion.COURSE_GRADING,Suggestion.CLASS_TIMINGS,Suggestion.CLASS_LOCATION,
            Suggestion.COURSE_OBJECTIVE,Suggestion.COURSE_WEBSITE,Suggestion.INSTRUCTOR_OFFICE_HOURS));

    public final List<Suggestion> suggestions;
    private final int categoryType;
    private Category(int categoryType, List<Suggestion> suggestions) {
        this.categoryType = categoryType;
        this.suggestions = suggestions;
    }

    public static Category fromCategoryTypeInt(int categoryTypeInt) {
        if (categoryTypeInt == INSTRUCTOR_EMAIL.categoryType) {
            return INSTRUCTOR_EMAIL;
        } if (categoryTypeInt == INSTRUCTOR_CONTACT.categoryType) {
            return INSTRUCTOR_CONTACT;
        } if (categoryTypeInt == INSTRUCTOR_PHONE_NO.categoryType) {
            return INSTRUCTOR_PHONE_NO;
        } if (categoryTypeInt == INSTRUCTOR_OFFICE_LOCATION.categoryType) {
            return INSTRUCTOR_OFFICE_LOCATION;
        } if (categoryTypeInt == INSTRUCTOR_OFFICE_HOURS.categoryType) {
            return INSTRUCTOR_OFFICE_HOURS;
        }

        return UNKNOWN;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }
}
