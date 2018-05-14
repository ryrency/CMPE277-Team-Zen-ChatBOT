package edu.sjsu.zen.models;

//import edu.sjsu.zen.R;

import android.icu.lang.UScript;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Category {
    UNKNOWN(-1, new ArrayList<Suggestion>()),
    INSTRUCTOR_EMAIL(4, Arrays.asList(Suggestion.EMAIL_INSTRUCTOR, Suggestion.INSTRUCTOR_PHONE_NO, Suggestion.INSTRUCTOR_OFFICE_HOURS,
            Suggestion.INSTRUCTOR_OFFICE_LOCATION,Suggestion.INSTRUCTOR_NAME)),
    INSTRUCTOR_NAME(5, Arrays.asList(Suggestion.INSTRUCTOR_EMAIL,Suggestion.INSTRUCTOR_PHONE_NO,
            Suggestion.INSTRUCTOR_OFFICE_HOURS,Suggestion.INSTRUCTOR_OFFICE_LOCATION)),
    INSTRUCTOR_CONTACT(3, Arrays.asList(Suggestion.INSTRUCTOR_NAME,Suggestion.INSTRUCTOR_PHONE_NO,
            Suggestion.INSTRUCTOR_OFFICE_HOURS,Suggestion.INSTRUCTOR_OFFICE_LOCATION)),
    INSTRUCTOR_PHONE_NO(8, Arrays.asList(Suggestion.INSTRUCTOR_NAME,Suggestion.INSTRUCTOR_EMAIL,
            Suggestion.INSTRUCTOR_OFFICE_HOURS,Suggestion.INSTRUCTOR_OFFICE_LOCATION)),
    INSTRUCTOR_OFFICE_LOCATION(7, Arrays.asList(Suggestion.INSTRUCTOR_EMAIL,Suggestion.INSTRUCTOR_PHONE_NO,
            Suggestion.INSTRUCTOR_NAME, Suggestion.INSTRUCTOR_OFFICE_HOURS)),
    INSTRUCTOR_OFFICE_HOURS(6, Arrays.asList(Suggestion.INSTRUCTOR_EMAIL,Suggestion.INSTRUCTOR_PHONE_NO,
            Suggestion.INSTRUCTOR_OFFICE_LOCATION)),
    COURSE_NAME(9,Arrays.asList(Suggestion.COURSE_PRE_REQUIREMENTS,
            Suggestion.CLASS_TIMINGS,Suggestion.CLASS_LOCATION,Suggestion.INSTRUCTOR_NAME,
            Suggestion.COURSE_WEBSITE,Suggestion.INSTRUCTOR_OFFICE_HOURS)),
    COURSE_PRE_REQUIREMENTS(10,Arrays.asList(Suggestion.COURSE_NAME, Suggestion.INSTRUCTOR_NAME,
            Suggestion.INSTRUCTOR_EMAIL, Suggestion.FINAL_EXAM_WEIGHTAGE,Suggestion.PROJECT_WEIGHTAGE)),
    CLASS_TIMINGS(11,Arrays.asList(Suggestion.SET_REMINDER, Suggestion.CLASS_LOCATION, Suggestion.COURSE_PRE_REQUIREMENTS,
            Suggestion.REFERENCE_MATERIALS)),
    CLASS_LOCATION(13,Arrays.asList(Suggestion.CLASS_TIMINGS,Suggestion.INSTRUCTOR_CONTACT, Suggestion.INSTRUCTOR_NAME,
            Suggestion.COURSE_WEBSITE)),
    COURSE_WEBSITE(12, Arrays.asList(Suggestion.CLASS_TIMINGS,  Suggestion.CLASS_LOCATION, Suggestion.INSTRUCTOR_CONTACT,
            Suggestion.REFERENCE_MATERIALS)),
    COURSE_GRADING(21,Arrays.asList(Suggestion.CLASS_TIMINGS, Suggestion.CLASS_LOCATION, Suggestion.COURSE_WEBSITE,
            Suggestion.REFERENCE_MATERIALS, Suggestion.COURSE_PRE_REQUIREMENTS)),
    COURSE_OBJECTIVE(14, Arrays.asList(Suggestion.COURSE_WEBSITE,
            Suggestion.REFERENCE_MATERIALS,Suggestion.CLASS_LOCATION,Suggestion.CLASS_TIMINGS)),
    FINAL_EXAM_WEIGHTAGE(21, Arrays.asList(Suggestion.FINAL_EXAM_DUE_DATE, Suggestion.PROJECT_WEIGHTAGE,
            Suggestion.ASSIGNMENT_WEIGHTAGE,Suggestion.QUIZZ_WEIGHTAGE, Suggestion.LAB_WEIGHTAGE)),
    MID_TERM_WEIGHTAGE(21,Arrays.asList(Suggestion.MID_TERM_DUE_DATE,Suggestion.FINAL_EXAM_WEIGHTAGE,
            Suggestion.ASSIGNMENT_WEIGHTAGE, Suggestion.PROJECT_WEIGHTAGE, Suggestion.QUIZZ_WEIGHTAGE,
            Suggestion.LAB_WEIGHTAGE)),
    ASSIGNMENT_WEIGHTAGE(21,Arrays.asList(Suggestion.FINAL_EXAM_WEIGHTAGE, Suggestion.MID_TERM_WEIGHTAGE,
            Suggestion.PROJECT_WEIGHTAGE, Suggestion.QUIZZ_WEIGHTAGE,Suggestion.LAB_WEIGHTAGE)),
    PROJECT_WEIGHTAGE(21,Arrays.asList(Suggestion.PROJECT_DUE_DATE,Suggestion.FINAL_EXAM_WEIGHTAGE, Suggestion.MID_TERM_WEIGHTAGE,
            Suggestion.ASSIGNMENT_WEIGHTAGE, Suggestion.QUIZZ_WEIGHTAGE,Suggestion.LAB_WEIGHTAGE)),
    LAB_WEIGHTAGE(21,Arrays.asList(Suggestion.FINAL_EXAM_WEIGHTAGE, Suggestion.MID_TERM_WEIGHTAGE,
            Suggestion.ASSIGNMENT_WEIGHTAGE, Suggestion.QUIZZ_WEIGHTAGE,Suggestion.PROJECT_WEIGHTAGE)),
    QUIZZ_WEIGHTAGE(21,Arrays.asList(Suggestion.FINAL_EXAM_WEIGHTAGE, Suggestion.MID_TERM_WEIGHTAGE,
            Suggestion.ASSIGNMENT_WEIGHTAGE, Suggestion.PROJECT_WEIGHTAGE,Suggestion.LAB_WEIGHTAGE)),
    FINAL_EXAM_DUE_DATE(20,Arrays.asList(Suggestion.SET_REMINDER,Suggestion.FINAL_EXAM_WEIGHTAGE,Suggestion.PROJECT_DUE_DATE,
            Suggestion.PROJECT_WEIGHTAGE, Suggestion.QUIZZ_WEIGHTAGE)),
    MID_TERM_DUE_DATE(19,Arrays.asList(Suggestion.SET_REMINDER,Suggestion.MID_TERM_WEIGHTAGE,Suggestion.ASSIGNMENT_WEIGHTAGE,
            Suggestion.LAB_WEIGHTAGE, Suggestion.QUIZZ_WEIGHTAGE)),
    PROJECT_DUE_DATE(18,Arrays.asList(Suggestion.SET_REMINDER, Suggestion.PROJECT_WEIGHTAGE, Suggestion.FINAL_EXAM_WEIGHTAGE,
            Suggestion.FINAL_EXAM_DUE_DATE)),
    REFERENCE_MATERIALS(23,Arrays.asList(Suggestion.COURSE_WEBSITE,
            Suggestion.CLASS_LOCATION, Suggestion.CLASS_TIMINGS))
    ;


    public final List<Suggestion> suggestions;
    private final int categoryType;
    private Category(int categoryType, List<Suggestion> suggestions) {
        this.categoryType = categoryType;
        this.suggestions = suggestions;
    }

    public static Category fromCategoryTypeInt(int categoryTypeInt) {

        switch(categoryTypeInt){
            case 3:  return INSTRUCTOR_CONTACT;
            case 4:  return INSTRUCTOR_EMAIL;
            case 5:  return INSTRUCTOR_NAME;
            case 6:  return INSTRUCTOR_OFFICE_HOURS;
            case 7:  return INSTRUCTOR_OFFICE_LOCATION;
            case 8:  return INSTRUCTOR_PHONE_NO;
            case 9:  return COURSE_NAME;
            case 10: return COURSE_PRE_REQUIREMENTS;
            case 11: return CLASS_TIMINGS;
            case 12: return COURSE_WEBSITE;
            case 13: return CLASS_LOCATION;
            case 14: return COURSE_OBJECTIVE;
            case 18: return PROJECT_DUE_DATE;
            case 19: return MID_TERM_DUE_DATE;
            case 20: return FINAL_EXAM_DUE_DATE;
            case 21: return COURSE_GRADING ;
            default: return UNKNOWN;
        }

    }

    public int getCategoryType() {
        return categoryType;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }


}
