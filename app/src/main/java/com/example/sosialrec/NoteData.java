package com.example.sosialrec;

public class NoteData {

    private String title;
    private String description;
    private int picture;
    private boolean like;

    public NoteData(String title, String description, int picture, boolean like) {
        this.title = title;
        this.description = description;
        this.picture = picture;
        this.like = like;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPicture() {
        return picture;
    }

    public boolean isLike() {
        return like;
    }
}
