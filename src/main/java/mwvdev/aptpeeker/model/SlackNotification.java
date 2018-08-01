package mwvdev.aptpeeker.model;

public class SlackNotification {

    private final String text;

    public SlackNotification(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
