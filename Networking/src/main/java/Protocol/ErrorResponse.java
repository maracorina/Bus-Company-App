package Protocol;


public class ErrorResponse implements Response {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
