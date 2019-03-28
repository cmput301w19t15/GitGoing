package com.example.cmput301w19t15.Objects;

public class Rating {
    private String value = "";
    private String comment = "";

    public Rating(String value, String comment) {
        this.value = value;
        this.comment = comment;
    }

    public Rating() {
    }

    public Rating(Rating rating) {
        this.value = rating.value;
        this.comment = rating.comment;
    }

    public void setValues(String value) {
        this.value = value;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getValues() {
        return this.value;
    }

    public String getComment() {
        return this.value;
    }
}
