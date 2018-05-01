package edu.sjsu.zen.models;

public class MessageQuery {
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public MessageQuery(String query) {
        setQuery(query);
    }
}

