package com.e.goodcheif.data;

public class Note {
    String Message,type,name;

    public Note(String message, String type, String name) {
        Message = message;
        this.type = type;
        this.name = name;
    }

    public Note() {
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
