package eu.javaspecialists.tjsn.issue188;

public class VerifyResult {
    private final boolean success;
    private final String failReason;

    private VerifyResult(boolean success, String failReason) {
        this.success = success;
        this.failReason = failReason;
    }

    public VerifyResult(String failReason) {
        this(false, failReason);
    }

    public VerifyResult() {
        this(true, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFailReason() {
        return failReason;
    }

    public String toString() {
        return success ? "Success" : "Failure - " + failReason;
    }
}
