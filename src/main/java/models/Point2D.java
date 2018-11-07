package models;


public class Point2D {
    private int x;
    private int y;
    private String name;


    public Point2D(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public double calcDistance(Point2D p) {
        double x = Math.pow(this.x - p.getX(), 2);
        double y = Math.pow(this.y - p.getY(), 2);
        return Math.sqrt(x + y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
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
        return "Point2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
