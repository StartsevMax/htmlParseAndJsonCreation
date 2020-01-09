package core;


import lombok.Getter;

import java.util.ArrayList;



public class Line implements Comparable {
    @Getter private final String name;
    @Getter public ArrayList<String> stations;

    public Line(String name) {
        this.name = name;
        this.stations = new ArrayList<>();
    }

    public int getStationsCount() { return stations.size(); }

    @Override
    public int compareTo(Object o) {
        return this.name.compareTo(((Line) o).getName());
    }

    @Override
    public String toString() {
        return name;
    }
}