package Utils;

import Domain.Rezervare;

public class RezervareChangeEvent implements Event {
    private ChangeEventType type;
    private Rezervare data, oldData;

    public RezervareChangeEvent() {}

    public RezervareChangeEvent(ChangeEventType type, Rezervare data) {
        this.type = type;
        this.data = data;
    }
    public RezervareChangeEvent(ChangeEventType type, Rezervare data, Rezervare oldData) {
        this.type = type;
        this.data = data;
        this.oldData=oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Rezervare getData() {
        return data;
    }

    public Rezervare getOldData() {
        return oldData;
    }
}

