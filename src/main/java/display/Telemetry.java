package main.java.display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
