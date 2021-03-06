package models;

import java.util.HashMap;
import java.util.Objects;

public class Point {

    private String name;
    private HashMap<String, Integer> costsList = new HashMap<String, Integer>();

    public Point(String name) {
        this.name = name;
    }

    public Point(String name, HashMap<String, Integer> costsList) {
        this.name = name;
        this.costsList = costsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Integer> getCostsList() {
        return costsList;
    }

    public void setCostsList(HashMap<String, Integer> costsList) {
        this.costsList = costsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(name, point.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name + '\'';
    }
}

