package models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class SocketProcessConfig {
    @SerializedName("enumNumber")
    private int enumNumber;
    @SerializedName("description")
    private String description;
    @SerializedName("name")
    private String name;
    @SerializedName("className")
    private String className; //defines the class of the configuration

    public SocketProcessConfig(int enumNumber, String description, String name, String className) {
        this.enumNumber = enumNumber;
        this.description = description;
        this.name = name;
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getEnumNumber() {
        return enumNumber;
    }

    public void setEnumNumber(int enumNumber) {
        this.enumNumber = enumNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SocketProcessConfig that = (SocketProcessConfig) o;
        return enumNumber == that.enumNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(enumNumber);
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
