package Utils;

import Domain.Cursa;

public class CursaChangeEvent implements Event{
    private ChangeEventType type;
    private Cursa data, oldData;

    public CursaChangeEvent() {}

    public CursaChangeEvent(ChangeEventType type, Cursa data) {
        this.type = type;
        this.data = data;
    }
    public CursaChangeEvent(ChangeEventType type, Cursa data, Cursa oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Cursa getData() {
        return data;
    }

    public Cursa getOldData() {
        return oldData;
    }
}
