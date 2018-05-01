package edu.sjsu.zen.models;

public class MessageResponse {
    private int categoryType;

    private String responseText;

    public Category getCategory() {
        return Category.fromCategoryTypeInt(categoryType);
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}
