package models;

import java.util.Map;

public class Point {

    private String name;
    private Map<String, Integer> costsList;

    public Point(String name, int distance, Map<String, Integer> attributeList) {
        this.name = name;
        this.costsList = attributeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Map<String, Integer> getAttributeList() {
        return costsList;
    }

    public void setAttributeList(Map<String, Integer> attributeList) {
        this.costsList = attributeList;
    }
}

