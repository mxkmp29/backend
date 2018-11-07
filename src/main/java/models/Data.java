package models;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public static List<Point> cities = new ArrayList<>();
    public static List<Point2D> cities2d = new ArrayList<>();
    public static int startingPointIndex = 0;
    public static String SOCKET_URL = "localhost";
    public static int SOCKET_PORT = 8888;
    public static SocketConnector socketConnector = new SocketConnector();
}
