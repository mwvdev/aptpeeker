package mwvdev.aptpeeker.model;

public class NotificationResult {

    private final boolean success;
    private final NotificationError error;

    public NotificationResult(boolean success) {
        this.success = success;
        this.error = null;
    }

    public NotificationResult(NotificationError error) {
        this.success = false;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public NotificationError getError() {
        return error;
    }

}
