package main.java.simulator.display;

import java.util.HashMap;

public class Telemetry {

    HashMap<String, Double> data = new HashMap<>();
    Field field;

    public Telemetry(Field field) {
        this.field = field;
    }

    public void addData(String data, double value) {
        this.data.put(data, value);
    }

    public void update() {
        field.updateTelemetry(data);
    }
}
