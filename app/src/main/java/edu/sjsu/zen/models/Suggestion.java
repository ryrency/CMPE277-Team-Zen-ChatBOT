package edu.sjsu.zen.models;
import edu.sjsu.zen.R;

/**
 * Suggestions to show in response to a query.
 */
public enum Suggestion {
    INSTRUCTOR_EMAIL("instructor email address", R.drawable.office_hours),
    INSTRUCTOR_NAME("who is instructor?", R.drawable.office_hours),
    INSTRUCTOR_CONTACT("instructor contact", R.drawable.office_hours),
    INSTRUCTOR_PHONE_NO("instructor phone number", R.drawable.office_hours ),
    INSTRUCTOR_OFFICE_LOCATION("instructor office location", R.drawable.office_hours),
    INSTRUCTOR_OFFICE_HOURS("instructor office hours", R.drawable.office_hours),
    COURSE_NAME("about course",R.drawable.office_hours),
    COURSE_PRE_REQUIREMENTS("course pre-requisites",R.drawable.office_hours),
    CLASS_TIMINGS("class timings",R.drawable.office_hours),
    CLASS_LOCATION("class location",R.drawable.office_hours),
    COURSE_WEBSITE("course website",R.drawable.office_hours),
    COURSE_GRADING("course grading",R.drawable.office_hours),
    COURSE_OBJECTIVE("course objective",R.drawable.office_hours),
    FINAL_EXAM_WEIGHTAGE("final exam weightage",R.drawable.office_hours),
    MID_TERM_WEIGHTAGE("mid term weightage",R.drawable.office_hours),
    ASSIGNMENT_WEIGHTAGE("assignments weightage",R.drawable.office_hours),
    PROJECT_WEIGHTAGE("project weightage",R.drawable.office_hours),
    LAB_WEIGHTAGE("labs weightage",R.drawable.office_hours),
    QUIZZ_WEIGHTAGE("quizzes weightage",R.drawable.office_hours),
    FINAL_EXAM_DUE_DATE("when is final exam?",R.drawable.office_hours),
    MID_TERM_DUE_DATE("when is mid-term?",R.drawable.office_hours),
    PROJECT_DUE_DATE("when is project due?",R.drawable.office_hours),
    REFERENCE_MATERIALS("reference materials",R.drawable.office_hours),
    UNKNOWN("Sorry did not understand your question!!",R.drawable.office_hours);

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
