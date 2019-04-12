package Protocol;

public class GetRezervariRequest implements  Request{
    private String cursaId;

    public String getCursaId() {
        return cursaId;
    }

    public GetRezervariRequest(String cursaId) {
        this.cursaId = cursaId;
    }
}
