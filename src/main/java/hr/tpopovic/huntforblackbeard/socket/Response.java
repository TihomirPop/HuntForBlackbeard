package hr.tpopovic.huntforblackbeard.socket;

public abstract class Response extends Message {

    private final Result result;

    protected Response(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public boolean isSuccess() {
        return result == Result.SUCCESS;
    }

    public boolean isFailure() {
        return result == Result.FAILURE;
    }

    public enum Result {
        SUCCESS,
        FAILURE
    }

}
