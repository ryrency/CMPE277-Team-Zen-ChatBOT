package edu.sjsu.zen.models;
import edu.sjsu.zen.R;

/**
 * Suggestions to show in response to a query.
 */
public enum Suggestion {
    INSTRUCTOR_EMAIL("instructor email address", 0),
    EMAIL_INSTRUCTOR("Email Instructor", R.drawable.email_icon),
    SET_REMINDER("set reminder", R.drawable.schedule),
    INSTRUCTOR_NAME("who is instructor?", 0),
    INSTRUCTOR_CONTACT("instructor contact", 0),
    INSTRUCTOR_PHONE_NO("instructor phone number",0),
    INSTRUCTOR_OFFICE_LOCATION("instructor office location", 0),
    INSTRUCTOR_OFFICE_HOURS("instructor office hours", 0),
    COURSE_NAME("about course",0),
    COURSE_PRE_REQUIREMENTS("course pre-requisites",0),
    CLASS_TIMINGS("class timings",0),
    CLASS_LOCATION("class location",0),
    COURSE_WEBSITE("course website",0),
    COURSE_GRADING("course grading",0),
    COURSE_OBJECTIVE("course objective",0),
    FINAL_EXAM_WEIGHTAGE("final exam weightage",0),
    MID_TERM_WEIGHTAGE("mid term weightage",0),
    ASSIGNMENT_WEIGHTAGE("assignments weightage",0),
    PROJECT_WEIGHTAGE("project weightage",0),
    LAB_WEIGHTAGE("labs weightage",0),
    QUIZZ_WEIGHTAGE("quizzes weightage",0),
    FINAL_EXAM_DUE_DATE("when is final exam?",0),
    MID_TERM_DUE_DATE("when is mid-term?",0),
    PROJECT_DUE_DATE("when is project due?",0),
    REFERENCE_MATERIALS("reference materials",0);

    private final String name;
    private final int iconResId;
    private Suggestion(String name, int iconResId) {
        this.name = name;
        this.iconResId = iconResId;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getName() {
        return name;
    }
}
