package vn.edu.usth.clothesapp.Chat;

public class Message {
    private String id;
    private String text;
    private boolean sentByUser;

    public Message() {
        // Default constructor required for Firebase
    }

    public Message(String id, String text, boolean sentByUser) {
        this.id = id;
        this.text = text;
        this.sentByUser = sentByUser;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isSentByUser() {
        return sentByUser;
    }
}
