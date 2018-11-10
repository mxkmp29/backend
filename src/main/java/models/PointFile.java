package models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PointFile<T> {

    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("point")
    private ArrayList<T> point = new ArrayList<>();
    @SerializedName("path")
    private String path;

    public PointFile() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<T> getPoint() {
        return point;
    }

    public void setPoint(ArrayList<T> point) {
        this.point = point;
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "PointFile{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", point=" + point +
                ", path='" + path + '\'' +
                '}';
    }
}
