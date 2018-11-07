package models;


public class Point2D {
    private float x;
    private float y;
    private String name;


    public Point2D(String name, float x, float y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public double calcDistance(Point2D p) {
        double x = Math.pow(this.x - p.getX(), 2);
        double y = Math.pow(this.y - p.getY(), 2);
        return Math.sqrt(x + y);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "(" + name + "|" + x + "," + y + ")";
    }
}
