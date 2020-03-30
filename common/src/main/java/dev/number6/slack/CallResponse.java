package dev.number6.slack;

public class CallResponse {

    private final boolean isSuccess;
    private final String value;

    public CallResponse(String value) {
        this.isSuccess = true;
        this.value = value;
    }

    public CallResponse(Exception e) {
        this.isSuccess = false;
        this.value = e.getMessage();
    }

    public String body() {
        return value;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
