package Protocol;

public class GetCursaIdResponse implements Response {
    private String id;

    public String getId() {
        return id;
    }

    public GetCursaIdResponse(String id) {
        this.id = id;
    }
}
