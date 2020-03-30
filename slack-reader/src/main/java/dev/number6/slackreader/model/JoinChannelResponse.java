package dev.number6.slackreader.model;

public class JoinChannelResponse {

    private final boolean ok;

    public JoinChannelResponse(boolean ok) {
        this.ok = ok;
    }

    public static JoinChannelResponse failed() {
        return new JoinChannelResponse(false);
    }

    public static JoinChannelResponse ok() {
        return new JoinChannelResponse(true);
    }

    public boolean getOk() {
        return ok;
    }

    @Override
    public String toString() {
        return "JoinChannelResponse{" +
                "ok=" + ok +
                '}';
    }
}
